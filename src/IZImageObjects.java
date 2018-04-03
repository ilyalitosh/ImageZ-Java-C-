import java.util.ArrayList;
import java.util.List;

public class IZImageObjects {

    private int[] objectsData;
    private List<IZImageObject> objectsList;
    private int count;

    public IZImageObjects(int[] objectsData){
        this.objectsData = objectsData;
        this.count = get_objects_count(objectsData);
        objectsList = new ArrayList<>();
        for(int i = 1; i <= this.count; i++){
            objectsList.add(new IZImageObject(get_object_data(objectsData, i)));
        }
    }

    public int count(){
        return count;
    }

    public List<IZImageObject> getObjectsList() {
        return objectsList;
    }

    // C++: определение кол-ва найденных образов
    private static native int get_objects_count(int[] objectsData);

    // C++: определение типов всех найденных образов
    private static native int[] get_objects_types(int[] objectsData);

    // C++: получение конкретного образа по типу
    private static native int[] get_object_data(int[] objectsData, int objectType);

}
