import iztypes.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        System.loadLibrary(ImageZ.LIBRARY_NAME);

        BufferedImage fileImage = ImageIO.read(new FileInputStream("D:/JavaProject(intelegenciya)/NativeImageZ/test/6.png"));
        BufferedImage bImage = new BufferedImage(fileImage.getWidth(), fileImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bImage.getGraphics();
        Graphics g1 = fileImage.getGraphics();
        g.drawImage(fileImage, 0, 0, null);


        long start = System.currentTimeMillis();
        IZMatrix imageSrc = IZConverter.convertBImageToIZMatrix(bImage);

        IZMatrix imageBinarized = new IZMatrix(new IZSize(imageSrc.getWidth(), imageSrc.getHeight()));
        IZProc.binarize(imageSrc, imageBinarized, IZBinarizationModes.IZ_BRADLEY_ROT, 10,0.05,0,0);
        long start1 = System.currentTimeMillis();
        //IZMatrix scaledImage = IZProc.scale(imageBinarized, 1.0, 1.0, IZType.SCALING_NEAREST_NEIGHBOR);
        long end1 = System.currentTimeMillis();
        //System.out.println("Скэйлинг: " + ((end1-start1+0.0)/1000) + " сек. ");
        IZMatrix imageAfterFilter = new IZMatrix(new IZSize(imageBinarized.getWidth(), imageBinarized.getHeight()));

        //BufferedImage test2;
        //for(int i = 0; i < 60; i++){
        //    test2 = IZConverter.convertIZMatrixToBImage(imageBinarized);

        IZProc.filter(imageBinarized, imageAfterFilter, IZFilterModes.MEDIAN_FILTER, IZFilterMaskSize.MASK_5x5, IZComputingType.CPU_COMPUTING);

        //    IZMatrix imageSrc1 = IZConverter.convertBImageToIZMatrix(test2);
        //}


        IZMatrix imageAfterDilation = new IZMatrix(new IZSize(imageAfterFilter.getWidth(), imageAfterFilter.getHeight()));
        IZProc.morphology(imageAfterFilter, imageAfterDilation, IZMorphologyModes.DILATION, new IZSize(7,27));

        IZMatrix clipedImage = new IZMatrix(new IZSize(imageAfterDilation.getWidth(), imageAfterDilation.getHeight()));
        IZProc.adaptiveClip(imageAfterDilation, clipedImage, 70);

        IZImageObjects objects = IZProc.findObjects(clipedImage);
        g1.setColor(new Color(255, 0 ,0));
        BufferedImage saveImage;
        for(int i = 0; i < objects.count(); i++){
            //System.out.println(currentObject.getX() + " " + currentObject.getY() + " " + currentObject.getWidth() + " " + currentObject.getHeight());
            if(objects.getObjectsList().get(i).getWidth() < 275 && objects.getObjectsList().get(i).getArea() > 1250){
                //g1.drawRect(objects.getObjectsList().get(i).getX(), objects.getObjectsList().get(i).getY(), objects.getObjectsList().get(i).getWidth(), objects.getObjectsList().get(i).getHeight());
                saveImage = IZConverter.convertIZMatrixToBImage(new IZMatrix(objects.getObjectsList().get(i).getObject(),
                                                                new IZSize(objects.getObjectsList().get(i).getWidth(), objects.getObjectsList().get(i).getHeight())));
                ImageIO.write(saveImage, "PNG", new FileOutputStream(new File("D:/JavaProject(intelegenciya)/NativeImageZ/test/10" + i + ".png")));
            }
            //System.out.println("Width: " + objects.getObjectsList().get(i).getWidth() + " Height: " + objects.getObjectsList().get(i).getHeight() + " X: " + objects.getObjectsList().get(i).getX() + " Y: " + objects.getObjectsList().get(i).getY());
        }
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
