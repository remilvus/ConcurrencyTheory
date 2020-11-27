package comparison.AO_version.ActiveObject;

import comparison.AO_version.ActiveObject.MethodRequests.EndRequest;
import comparison.AO_version.ActiveObject.MethodRequests.MethodRequestGet;
import comparison.AO_version.ActiveObject.MethodRequests.MethodRequestPut;

import java.util.List;

public class BufferProxy {
    private Buffer buffer;
    private Scheduler scheduler;
    public final int buffer_size;

    public BufferProxy(int capacity, int wait_time){
        buffer = new Buffer(capacity, wait_time);
        buffer_size = capacity;
        scheduler = new Scheduler(buffer);
        scheduler.start();
    }

    public void kill_scheduler(){
        scheduler.enqueue(new EndRequest());
    }

    public Future put(List<Integer> values){
        return put(values, null);
    }

    public Future put(List<Integer> values, Integer id){
        Future result_container = new Future();
        MethodRequestPut put_request = new MethodRequestPut(buffer, values, result_container);

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
