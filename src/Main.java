import iztypes.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        System.loadLibrary(ImageZ.LIBRARY_NAME);

        BufferedImage fileImage = ImageIO.read(new FileInputStream("D:/JavaProject(intelegenciya)/NativeImageZ/test/3_2.png"));
        BufferedImage bImage = new BufferedImage(fileImage.getWidth(), fileImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bImage.getGraphics();
        Graphics g1 = fileImage.getGraphics();
        g.drawImage(fileImage, 0, 0, null);


        long start = System.currentTimeMillis();
        IZMatrix imageSrc = IZConverter.convertBImageToIZMatrix(bImage);

        IZMatrix imageBinarized = new IZMatrix(new IZSize(imageSrc.getWidth(), imageSrc.getHeight()));
        IZProc.binarize(imageSrc, imageBinarized, IZBinarizationModes.IZ_BRADLEY_ROT, 10,0.05,0,0);


        IZMatrix imageAfterFilter = new IZMatrix(new IZSize(imageBinarized.getWidth(), imageBinarized.getHeight()));
        IZProc.filter(imageBinarized, imageAfterFilter, IZFilterModes.MEDIAN_FILTER, IZFilterMaskSize.MASK_7x7, IZComputingType.CPU_COMPUTING);

        IZMatrix imageAfterDilation = new IZMatrix(new IZSize(imageAfterFilter.getWidth(), imageAfterFilter.getHeight()));
        IZProc.morphology(imageAfterFilter, imageAfterDilation, IZMorphologyModes.DILATION, new IZSize(7,27));

        IZMatrix clipedImage = new IZMatrix(new IZSize(imageAfterDilation.getWidth(), imageAfterDilation.getHeight()));
        IZProc.adaptiveClip(imageAfterDilation, clipedImage, 70);

        //BufferedImage test2;
        //for(int i = 0; i < 60; i++){
        //    test2 = IZConverter.convertIZMatrixToBImage(imageBinarized);


        BufferedImage saveImage1 = IZConverter.convertIZMatrixToBImage(clipedImage);
        ImageIO.write(saveImage1, "PNG", new FileOutputStream(new File("D:/JavaProject(intelegenciya)/NativeImageZ/test/binarized4.png")));
        //    IZMatrix imageSrc1 = IZConverter.convertBImageToIZMatrix(test2);
        //}


        /*IZMatrix imageAfterDilation = new IZMatrix(new IZSize(imageAfterFilter.getWidth(), imageAfterFilter.getHeight()));
        IZProc.morphology(imageAfterFilter, imageAfterDilation, IZMorphologyModes.DILATION, new IZSize(7,27));

        IZMatrix clipedImage = new IZMatrix(new IZSize(imageAfterDilation.getWidth(), imageAfterDilation.getHeight()));
        IZProc.adaptiveClip(imageAfterDilation, clipedImage, 70);*/

        long start1 = System.currentTimeMillis();
        IZImageObjects objects = IZProc.findObjects(clipedImage);
        long end1 = System.currentTimeMillis();
        System.out.println("Нахождение образов: " + ((end1-start1+0.0)/1000) + " сек. ");
        g1.setColor(new Color(255, 0 ,0));
        BufferedImage saveImage;
        IZImageObjects filteredImages = new IZImageObjects();
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i).getWidth() < 275 && objects.get(i).getArea() > 1100 && (objects.get(i).getHeight()/objects.get(i).getWidth()) < 5){
                if(objects.get(i).getArea() > 15000){
                    filteredImages.add(objects.get(i));
                    continue;
                }
                if((objects.get(i).getWidth()*objects.get(i).getHeight() + .0)/objects.get(i).getArea() < 1.2){
                    filteredImages.add(objects.get(i));
                }
            }
        }
        filteredImages.sortAsc();
        StringBuilder response = new StringBuilder();
        for(int i = 0; i < filteredImages.size(); i++){
            //System.out.println(currentObject.getX() + " " + currentObject.getY() + " " + currentObject.getWidth() + " " + currentObject.getHeight());
            //if(objects.getObjectsList().get(i).getWidth() < 275 && objects.getObjectsList().get(i).getArea() > 1250){
                //g1.drawRect(objects.getObjectsList().get(i).getX(), objects.getObjectsList().get(i).getY(), objects.getObjectsList().get(i).getWidth(), objects.getObjectsList().get(i).getHeight());
                /*saveImage = IZConverter.convertIZMatrixToBImage(new IZMatrix(filteredImages.get(i).getObject(),
                                                                new IZSize(filteredImages.get(i).getWidth(), filteredImages.get(i).getHeight())));
                ImageIO.write(saveImage, "PNG", new FileOutputStream(new File("D:/JavaProject(intelegenciya)/NativeImageZ/test/10" + i + ".png")));
                System.out.println("Площадь: " + filteredImages.get(i).getArea());*/
            if((filteredImages.get(i).getWidth()*filteredImages.get(i).getHeight() + .0)/filteredImages.get(i).getArea() < 1.2){
                if(filteredImages.get(i).getWidth()/filteredImages.get(i).getHeight() > 1.5){
                    response.append("-");
                }else {
                    response.append(".");
                }
            }else{
                response.append("X");
            }
            //}
            //System.out.println("Width: " + objects.getObjectsList().get(i).getWidth() + " Height: " + objects.getObjectsList().get(i).getHeight() + " X: " + objects.getObjectsList().get(i).getX() + " Y: " + objects.getObjectsList().get(i).getY());
        }
        System.out.println("Вижу: " + response);
        //System.out.println("Количество образов: " + objects.count() + " " + objects.getType(1) + " Площадь: " + object1.getArea());


        //System.out.println("Кусок обработки: " + ((end1-start1+0.0)/1000) + " сек. " + object1.getX() + " " + object1.getY() + " " + object1.getWidth() + " " + object1.getHeight());

        //BufferedImage bImageBinarized = IZConverter.convertIZMatrixToBImage(scaledImage);
        long end = System.currentTimeMillis();
        System.out.println("Фулл обработка: " + ((end-start+0.0)/1000) + " сек.");

        //ImageIO.write(bImageBinarized, "PNG", new FileOutputStream(new File("D:/JavaProject(intelegenciya)/NativeImageZ/test/13.png")));
        //System.out.println(Arrays.toString(binarizedImage));
        //System.out.println(bImageBinarized.getWidth() + " " + bImageBinarized.getHeight());
    }

}
