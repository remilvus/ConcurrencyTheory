package lab_07.ActiveObject.MethodRequests;

import lab_07.ActiveObject.Buffer;

public abstract class MethodRequest implements IMethodRequest {
    protected Buffer buffer;
    protected Integer id = null;

    public MethodRequest(Buffer buffer){
        this.buffer = buffer;
    }

    public MethodRequest(Buffer buffer, Integer id){
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public Integer get_id() {
        return id;
    }
}
