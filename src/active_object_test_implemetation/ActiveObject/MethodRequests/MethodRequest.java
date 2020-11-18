package active_object_test_implemetation.ActiveObject.MethodRequests;

import active_object_test_implemetation.ActiveObject.Buffer;

public abstract class MethodRequest implements IMethodRequest{
    protected Buffer buffer;

    public MethodRequest(Buffer buffer){
        this.buffer = buffer;
    }
}
