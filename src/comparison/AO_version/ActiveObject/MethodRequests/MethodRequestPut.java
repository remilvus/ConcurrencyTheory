package comparison.AO_version.ActiveObject.MethodRequests;

import comparison.AO_version.ActiveObject.Buffer;
import comparison.AO_version.ActiveObject.Future;

import java.util.List;

public class MethodRequestPut extends MethodRequest {
    private List<Integer> values;
    Future result_container;

    public MethodRequestPut(Buffer buffer, List<Integer> values, Future res_container){
        // future is needed to prevent producers from overfilling the buffer
        super(buffer);
        this.values = values;
        this.result_container = res_container;
    }

    public MethodRequestPut(Buffer buffer, List<Integer> values, Future res_container, Integer id){
        super(buffer, id);
        this.values = values;
        this.result_container = res_container;
    }

    @Override
    public boolean canExecute() { // guard
        return buffer.canPut(values.size());
    }

    @Override
    public void execute() {
        buffer.put(values);
        result_container.set(null);
    }

    @Override
    public String toString() {
        return "P+" + id;
    }
}
