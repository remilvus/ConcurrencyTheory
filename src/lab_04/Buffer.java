package lab_04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int buf[];
    private final int size;
    int next_empty = 0;
    int first_full = 0;
    int count = 0;

    private ReentrantLock buffer_lock = new ReentrantLock();
    private Condition consumer_condition = buffer_lock.newCondition();
    private Condition producer_condition = buffer_lock.newCondition();

    public Buffer(int size){
        this.size = size;
        this.buf = new int[size];
    }

    public int consume(int k){
        int element = -1;

        buffer_lock.lock();
        try {
            while ((first_full == next_empty && count == 0) || count < k) {
                try {
                    System.out.println("consumer awaits (needs " + k + ")");
                    producer_condition.signal();
                    consumer_condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int i=0; i<k; i++) {
                element = buf[first_full];
                buf[first_full] = -1;
                first_full = (first_full + 1) % size;
                count -= 1;
            }

            producer_condition.signal();
        } finally {
            buffer_lock.unlock();
        }

        System.out.println("consumer consumed " + k + " products");

        return element;
    }

    public void produce(int element, int k){

//        System.out.println("tries to produce " + k);
        buffer_lock.lock();

        try {
            while((next_empty == first_full && count != 0) ||
                    (size - count) < k){
                    try {
//                        System.out.println("producer awaits");
                        consumer_condition.signal();
                        producer_condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

            for(int i=0; i<k; i++) {
                buf[next_empty] = element;
                next_empty = (next_empty + 1) % size;
                count += 1;
            }

            consumer_condition.signal();
        } finally {
            buffer_lock.unlock();
        }

    }
}
