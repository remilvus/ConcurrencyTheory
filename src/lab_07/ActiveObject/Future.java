package lab_07.ActiveObject;

import java.util.List;

public class Future {

    private boolean is_set = false;
    private volatile List<Integer> value = null;

    public boolean isReady(){
        return is_set;
    }

    public void set(List<Integer> value){
        this.value = value; // value must be set before 'is_set' flag
        is_set = true;
    }

    public List<Integer> get(){
        return this.value;
    }
}
