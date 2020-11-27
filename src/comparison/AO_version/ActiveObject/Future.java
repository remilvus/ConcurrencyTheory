package comparison.AO_version.ActiveObject;

import java.util.List;

public class Future {

    private volatile boolean is_set = false;
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
