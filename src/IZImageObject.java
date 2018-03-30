class IZImageObject {

    private int[] object;
    private IZSize size;
    private int objectArea;
    private int x;
    private int y;

    IZImageObject(int[] objectData){
        this.objectArea = objectData.length / 2;
        int[] size = size(objectData);
        this.size = new IZSize(size[1], size[0]);
        x = size[3];
        y = size[2];
        object = get_object(objectData, size[3], size[2], size[1], size[0]);
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

    public int[] getObject() {
        return object;
    }

    // C++: определение размеров образа
    private static native int[] size(int[] objectData);

    // C++: образ размером Width x Height
    private static native int[] get_object(int[] objectData, int x, int y, int width, int height);

}
