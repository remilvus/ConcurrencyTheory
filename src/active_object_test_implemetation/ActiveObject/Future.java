package active_object_test_implemetation.ActiveObject;

public class Future {

    private  boolean is_set = false;
    private int value;

    public boolean isAvailable(){
        return is_set;
    }

    public void set(int value){
        this.value = value; // value must be set before 'is_set' flag
        is_set = true;
    }

    public int get(){
        return this.value;
    }
}
