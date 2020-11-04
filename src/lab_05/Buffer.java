package lab_05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int buf[];
    private final int size;
    int next_empty = 0;
    int first_full = 0;
    int count = 0;
    boolean consumer_waits = false;
    boolean producer_waits = false;

    private ReentrantLock buffer_lock = new ReentrantLock();
    private Condition consumer_first = buffer_lock.newCondition();
    private Condition consumer_rest = buffer_lock.newCondition();
    private Condition producer_first = buffer_lock.newCondition();
    private Condition producer_rest = buffer_lock.newCondition();

    public Buffer(int size){
        this.size = size;
        this.buf = new int[size];
    }

    public int consume(int k){
        int element = -1;

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
            while ((first_full == next_empty && count == 0) || count < k) {
                try {
                    System.out.println("consumer awaits (needs " + k + ")");

                    producer_first.signal();
                    consumer_first.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            consumer_waits = false;

            for(int i=0; i<k; i++) {
                element = buf[first_full];
                buf[first_full] = -1;
                first_full = (first_full + 1) % size;
                count -= 1;
            }

            producer_first.signal();
            consumer_rest.signal();
        } finally {
            buffer_lock.unlock();
        }

        return element;
    }

    public void produce(int element, int k){

//        System.out.println("tries to produce " + k);
        buffer_lock.lock();
        try {
            while (producer_waits) {
                try{ producer_rest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            producer_waits = true;
            while((next_empty == first_full && count != 0) ||
                    (size - count) < k){
                    try {
//                        System.out.println("producer awaits");
                        consumer_first.signal();
                        producer_first.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            producer_waits  = false;

            for(int i=0; i<k; i++) {
                buf[next_empty] = element;
                next_empty = (next_empty + 1) % size;
                count += 1;
            }

            consumer_first.signal();
            producer_rest.signal();
        } finally {
            buffer_lock.unlock();
        }

    }
}
