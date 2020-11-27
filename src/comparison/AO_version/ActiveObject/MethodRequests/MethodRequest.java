package comparison.AO_version.ActiveObject.MethodRequests;

import comparison.AO_version.ActiveObject.Buffer;

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
