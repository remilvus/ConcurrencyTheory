package active_object_test_implemetation.ActiveObject.MethodRequests;

import active_object_test_implemetation.ActiveObject.Buffer;
import active_object_test_implemetation.ActiveObject.Future;

public class MethodRequestPut extends MethodRequest{
    private int value;
    Future result_container;

    public MethodRequestPut(Buffer buffer, int value, Future res_container){
        // future is needed to prevent producers from overfilling the buffer
        super(buffer);
        this.value = value;
        this.result_container = res_container;
    }

    @Override
    public boolean can_execute() { // guard
        return ! buffer.isFull();
    }

    @Override
    public void call() {
        buffer.put(value);
        result_container.set(1);
    }
}
