import izexceptions.IZNullMatrixException;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class IZConverter {

    private IZConverter(){

    }

    public static IZMatrix convertBImageToIZMatrix(BufferedImage bImage){
        IZMatrix convertedImage = new IZMatrix(new IZSize(bImage.getWidth(), bImage.getHeight()));
        int iterator = 0;
        int value;
        for(int i = 0; i < bImage.getHeight(); i++){
            for(int j = 0; j < bImage.getWidth(); j++){
                value = bImage.getRaster().getDataBuffer().getElem(iterator) + 128;
                convertedImage.setValue(value, j, i);
                iterator++;
            }
        }
        return convertedImage;
    }

    public static BufferedImage convertIZMatrixToBImage(IZMatrix image){
        if(image.getMatrix()[0] == -1){
            throw new IZNullMatrixException("Матрица пуста");
        }
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        // НОВЫЙ МЕТОД КОНВЕРТАЦИИ( БЫСТРЕЕ СТАРОГО В 9-10 РАЗ)

        int[] colorMask = {0, 0, 0, 255};
        WritableRaster wr = convertedImage.getRaster();
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                colorMask[0] = image.getMatrix()[i * image.getWidth() + j];
                colorMask[1] = image.getMatrix()[i * image.getWidth() + j];
                colorMask[2] = image.getMatrix()[i * image.getWidth() + j];
                wr.setPixel(j, i, colorMask);
            }
        }

        // СТАРЫЙ МЕТОД КОНВЕРТАЦИИ( МЕДЛЕННЕЕ В 9-10 РАЗ)

        /*Graphics g = convertedImage.getGraphics();
        for(int i = 0; i < convertedImage.getHeight(); i++){
            for(int j = 0; j < convertedImage.getWidth(); j++){
                g.setColor(new Color(image.getValue(j, i), image.getValue(j, i), image.getValue(j, i)));
                g.drawLine(j, i, j, i);
            }
        }*/

        return convertedImage;
    }


}
