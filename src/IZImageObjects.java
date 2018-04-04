import java.util.LinkedList;

public class IZImageObjects extends LinkedList<IZImageObject> {

    public IZImageObjects(int[] objectsData){
        int count = get_objects_count(objectsData);
        for(int i = 1; i <= count; i++){
            add(new IZImageObject(get_object_data(objectsData, i)));
        }
    }

    public IZImageObjects(){

    }

    /**
     * сортировка по возрастанию по координате X
     *
     * */
    public void sortAsc(){
        for(int i = size() - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(get(j).getX() > get(j + 1).getX()){
                    IZImageObject buffer = get(j);
                    set(j, get(j + 1));
                    set(j + 1, buffer);
                }
            }
        }
    }

    /**
     * сортировка по убыванию по координате X
     *
     * */
    public void sortDes(){
        for(int i = size() - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(get(j).getX() < get(j + 1).getX()){
                    IZImageObject buffer = get(j + 1);
                    set(j + 1, get(j));
                    set(j, buffer);
                }
            }
        }
    }

    // C++: определение кол-ва найденных образов
    private static native int get_objects_count(int[] objectsData);

    // C++: определение типов всех найденных образов
    private static native int[] get_objects_types(int[] objectsData);

    // C++: получение конкретного образа по типу
    private static native int[] get_object_data(int[] objectsData, int objectType);

}
