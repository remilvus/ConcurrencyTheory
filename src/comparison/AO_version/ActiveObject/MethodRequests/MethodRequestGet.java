package comparison.AO_version.ActiveObject.MethodRequests;

import comparison.AO_version.ActiveObject.Buffer;
import comparison.AO_version.ActiveObject.Future;

import java.util.List;

public class MethodRequestGet extends MethodRequest {
    Future result_container;
    final int how_many;

    public MethodRequestGet(Buffer buffer, Future res_container, int how_many) {
        super(buffer);
        this.result_container = res_container;
        this.how_many = how_many;
    }

    public MethodRequestGet(Buffer buffer, Future res_container, int how_many, Integer id) {
        super(buffer, id);
        this.result_container = res_container;
        this.how_many = how_many;
    }


    @Override
    public boolean canExecute() {
        return buffer.size() >= how_many;
    }

    @Override
    public void execute() {
        List<Integer> result = buffer.get(how_many); // get result
        result_container.set(result); // return by future
    }

    @Override
    public String toString() {
        return "C" + id;
    }
}

