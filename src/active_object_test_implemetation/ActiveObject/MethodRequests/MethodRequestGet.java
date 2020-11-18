package active_object_test_implemetation.ActiveObject.MethodRequests;

import active_object_test_implemetation.ActiveObject.Buffer;
import active_object_test_implemetation.ActiveObject.Future;

public class MethodRequestGet extends MethodRequest {
    Future result_container;

    public MethodRequestGet(Buffer buffer, Future res_container) {
        super(buffer);
        this.result_container = res_container;
    }

    @Override
    public boolean can_execute() {
        return ! buffer.isEmpty();
    }

    @Override
    public void call() {
        int result = buffer.get(); // get result
        result_container.set(result); // return by future
    }
}

