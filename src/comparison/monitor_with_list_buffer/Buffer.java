package comparison.monitor_with_list_buffer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Queue<Integer> buffer = new LinkedList<>();
    public final int MAX_SIZE;
    private int wait_time;

    boolean consumer_waits = false;
    boolean producer_waits = false;

    private ReentrantLock buffer_lock = new ReentrantLock();
    private Condition consumer_first = buffer_lock.newCondition();
    private Condition consumer_rest = buffer_lock.newCondition();
    private Condition producer_first = buffer_lock.newCondition();
    private Condition producer_rest = buffer_lock.newCondition();

    public Buffer(int size, int wait_time){
        this.MAX_SIZE = size;
        this.wait_time = wait_time;
    }

    public List<Integer> consume(int how_many){
        List<Integer> result = new LinkedList<>();

        buffer_lock.lock();


        try {
            while (consumer_waits) {
                try{
                    consumer_rest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            consumer_waits = true;
            while (! can_consume(how_many)) {
                try {
                    consumer_first.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            consumer_waits = false;

            for (int i = 0; i < how_many; i++) {
                result.add(buffer.remove());
                Thread.sleep(wait_time);
            }

            producer_first.signal();
            consumer_rest.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            buffer_lock.unlock();
        }

        return result;
    }

    public void produce(List<Integer> elements){

        buffer_lock.lock();

        try {

            while (producer_waits) {
                try{
                    producer_rest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            producer_waits = true;
            while(! can_produce(elements.size())){
                try {
                    producer_first.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            producer_waits  = false;

            add_to_buffer(elements);

            consumer_first.signal();
            producer_rest.signal();
        } finally {
            buffer_lock.unlock();
        }

    }

    private void add_to_buffer(List<Integer> emelents){
        if (buffer.size() + emelents.size() > MAX_SIZE) {
            throw new IllegalStateException("Added to full buffer");
        } else {
            int size = emelents.size();
            for (int i = 0; i < size; i++) {
                buffer.add(emelents.remove(0));
                try {
                    Thread.sleep(wait_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private boolean can_produce(int count){
        return buffer.size() + count <= MAX_SIZE;
    }

    private boolean can_consume(int count){
        return buffer.size() >= count;
    }
}