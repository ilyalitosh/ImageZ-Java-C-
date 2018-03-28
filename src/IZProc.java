import izexceptions.*;
import iztypes.*;

public final class IZProc{

    private IZProc(){

    }

    /** threshold, thresholdStart и thresholdEnd указываются для simpleBinarization1 и simpleBinarization2(пороговая обработка односторонняя и двусторонняя соответсвенно);
     *  d и t - для binarizationBradleyRot(d - сторона маски проходящей по интегральному изображению
     *                                     t - процент от суммарной яркостной составляющей в пределах этой маски) */
    public static void binarize(IZMatrix imageSrc, IZMatrix imageDest, int binarizationMode, double d, double t, int thresholdStart, int thresholdEnd){
        if((d % 2 != 0) || (d < 3) || (thresholdStart > thresholdEnd)){
            //throw new IZParameterException("Проверьте вводимые параметры(d - всегда четное, thresholdStart < thresholdEnd)");
        }
        switch (binarizationMode){
            case IZBinarizationModes.IZ_BRADLEY_ROT:
                binarize_0(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), d, t);
                break;
            case IZBinarizationModes.IZ_SIMPLE_BINARIZATION_1:
                binarize_1(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), thresholdStart);
                break;
            case IZBinarizationModes.IZ_SIMPLE_BINARIZATION_2:
                binarize_2(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), thresholdStart, thresholdEnd);
                break;
            default: throw new IZNullModeException("Указанный вид бинаризации отсутствует или не был выбран");
        }
    }

    public static void filter(IZMatrix imageSrc, IZMatrix imageDest, int filterMode, int filterSize){
        if((filterSize % 2 == 0) || (filterSize < 3)){
            throw new IZParameterException("Проверьте вводимые параметры(filterSize - всегда нечетное)");
        }
        switch (filterMode){
            case IZFilterModes.MEDIAN_FILTER:
                filter_0(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), filterSize, IZComputingType.CPU_COMPUTING);
                break;
        }
    }

    public static void filter(IZMatrix imageSrc, IZMatrix imageDest, int filterMode, int filterSize, int compType){
        switch (compType){
            case IZComputingType.CPU_COMPUTING:
                if((filterSize % 2 == 0) || (filterSize < 3) || (filterSize > 9)){
                    throw new IZParameterException("Проверьте вводимые параметры(filterSize - всегда нечетное; при использовании СPU для вычислений размер маски выбирать из IZFilterMaskSize)");
                }
                break;
            case IZComputingType.GPU_COMPUTING:
                if((filterSize % 2 == 0) || (filterSize < 3)){
                    throw new IZParameterException("Проверьте вводимые параметры(filterSize - всегда нечетное)");
                }
                break;
        }
        switch (filterMode){
            case IZFilterModes.MEDIAN_FILTER:
                filter_0(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), filterSize, compType);
                break;
        }
    }

    public static void morphology(IZMatrix imageSrc, IZMatrix imageDest, int morphologyMode, IZSize maskSize){
        switch (morphologyMode){
            case IZMorphologyModes.DILATION:
                morphology_0(imageSrc.getMatrix(), imageDest.getMatrix(), imageDest.getWidth(),
                            imageDest.getHeight(), maskSize.getWidth(), maskSize.getHeight());
                break;
            case IZMorphologyModes.EROSION:
                morphology_1(imageSrc.getMatrix(), imageDest.getMatrix(), imageDest.getWidth(),
                            imageDest.getHeight(), maskSize.getWidth(), maskSize.getHeight());
                break;
        }
    }

    public static IZImageObjects findObjects(IZMatrix imageSrc){
        return new IZImageObjects(findObjects_0(imageSrc.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight()));
    }

    public static void adaptiveClip(IZMatrix imageSrc, IZMatrix imageDest, int depth){
        adaptiveClip_0(imageSrc.getMatrix(), imageDest.getMatrix(), imageSrc.getWidth(), imageSrc.getHeight(), depth);
    }

    // C++: бинаризация Брэдли-Рота
    private static native void binarize_0(int[] image_0, int[] image_1, int width, int height, double d, double t);

    // C++: бинаризация однопороговая
    private static native void binarize_1(int[] image_0, int[] image_1, int width, int height, int thresholdStart);

    // C++: бинаризация двупороговая
    private static native void binarize_2(int[] image_0, int[] image_1, int width, int height, int thresholdStart, int thresholdEnd);

    // C++: медианный фильтр(есть возможность использовать GPU для ускорения фильтрации)
    private static native void filter_0(int[] image_0, int[] image_1, int width, int height, int filterSize, int compType);

    // C++: морфология наращивание(dilation)
    private static native void morphology_0(int[] image_0, int[] image_1, int width, int height, int maskWidth, int maskHeight);

    // C++: морфология эрозия(erosion)
    private static native void morphology_1(int[] image_0, int[] image_1, int width, int height, int maskWidth, int maskHeight);

    // С++ нахождение объектов на изображении(метод связных образов)
    private static native int[] findObjects_0(int[] image, int width, int height);

    // C++ адаптивная обрезка изображения
    private static native void adaptiveClip_0(int[] image_0, int[] image_1, int width, int height, int depth);

}
