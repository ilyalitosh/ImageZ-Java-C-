public class IZImageObject {

    private int[] objectData;
    private IZSize size;
    private int objectArea;
    private int x;
    private int y;

    IZImageObject(int[] objectData){
        this.objectData = objectData;
        this.objectArea = objectData.length / 2;
        int[] size = size(objectData);
        this.size = new IZSize(size[1], size[0]);
        x = size[3];
        y = size[2];
    }

    public int getWidth(){
        return size.getWidth();
    }

    public int getHeight(){
        return size.getHeight();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getArea(){
        return objectArea;
    }

    public int[] getObjectData() {
        return objectData;
    }

    // C++: определение размеров образа
    private static native int[] size(int[] objectData);

}
