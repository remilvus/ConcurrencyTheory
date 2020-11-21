package lab_07.ActiveObject;

import lab_07.ActiveObject.MethodRequests.MethodRequestGet;
import lab_07.ActiveObject.MethodRequests.MethodRequestPut;

import java.util.List;

public class BufferProxy {
    private Buffer buffer;
    private Scheduler scheduler = new Scheduler();
    public final int buffer_size;

    public BufferProxy(int capacity){
        buffer = new Buffer(capacity);
        buffer_size = capacity;
        scheduler.start();
    }

    public Future put(List<Integer> values){
        return put(values, null);
    }

    public Future put(List<Integer> values, Integer id){
        Future result_container = new Future();
        MethodRequestPut put_request = new MethodRequestPut(buffer, values, result_container, id);

        scheduler.enqueue(put_request);

        return result_container;
    }

    public Future get(int how_many){
        return get(how_many, null);
    }

    public Future get(int how_many, Integer id){
        Future result_container = new Future();
        MethodRequestGet put_request = new MethodRequestGet(buffer, result_container, how_many, id);

        scheduler.enqueue(put_request);

        return result_container;
    }
}
