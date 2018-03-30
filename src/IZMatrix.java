
public class IZMatrix {

    private int[] matrix;
    private int width = 0;
    private int height = 0;

    public IZMatrix(IZSize size){
        matrix = new int[size.getHeight() * size.getWidth()];
        matrix[0] = -1;                                       // целка
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    public IZMatrix(int[] matrix, IZSize size){
        this.matrix = matrix;
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    public int[] getMatrix(){
        return matrix;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setValue(int value, int x, int y){
        matrix[y * width + x] = value;
    }

    public int getValue(int x, int y){
        return matrix[y * width + x];
    }

}
