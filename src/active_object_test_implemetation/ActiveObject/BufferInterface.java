package active_object_test_implemetation.ActiveObject;

import active_object_test_implemetation.ActiveObject.MethodRequests.IMethodRequest;
import active_object_test_implemetation.ActiveObject.MethodRequests.MethodRequestGet;
import active_object_test_implemetation.ActiveObject.MethodRequests.MethodRequestPut;

public class BufferInterface {
    Buffer buffer;
    Scheduler scheduler;

    public BufferInterface(int capacity){
        this.buffer = new Buffer(capacity);
        this.scheduler = new Scheduler();
        this.scheduler.start();
    }

    public Future put(int value){
        Future result_container = new Future();
        IMethodRequest put_request = new MethodRequestPut(buffer, value, result_container);

        scheduler.enqueue(put_request);

        return result_container;
    }

    public Future get(){
        Future result_container = new Future();
        IMethodRequest put_request = new MethodRequestGet(buffer, result_container);

        scheduler.enqueue(put_request);

        return result_container;
    }
}
