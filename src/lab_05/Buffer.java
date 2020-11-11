package lab_05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int buf[];
    public final int size;
    int next_empty = 0;
    int first_full = 0;
    int count = 0;
    boolean consumer_waits = false;
    boolean producer_waits = false;

    // variables for log
    int consumer_rest_count = 0;
    int producer_rest_count = 0;
    int consumer_first_count = 0;
    int producer_first_count = 0;
    int iter = 0;
    boolean log_condition = false;

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
        int element = -1; // placeholder for last consumed value

        buffer_lock.lock();

        // log
        if(log_condition){System.out.println("(" + iter + ") cons lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                                                      "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
        iter += 1;
        }

        try {
            // log
            if(log_condition){
                consumer_rest_count += 1;
                System.out.println("wait rest");
                System.out.println("(" + iter + ") cons lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }
            while (consumer_waits) {
//            while (buffer_lock.hasWaiters(consumer_first)) {
                try{
                    consumer_rest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // log
            if(log_condition){
                System.out.println("no wait rest");
                consumer_rest_count -= 1;
                consumer_first_count += 1;
                System.out.println("wait first");
                System.out.println("(" + iter + ") cons lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }

            consumer_waits = true;
            while (! can_consume(k)) {
                try {
                    consumer_first.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            consumer_waits = false;

            //log
            if(log_condition){
                System.out.println("no wait first");
                consumer_first_count -= 1;
                System.out.println("(" + iter + ") cons lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }

            element = remove_from_buffer(k);

            producer_first.signal();
            consumer_rest.signal();
        } finally {
            buffer_lock.unlock();
        }

        return element;
    }

    public void produce(int element, int k){

        buffer_lock.lock();

        // log
        if(log_condition) {
            System.out.println("(" + iter + ") prod lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                    "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            iter += 1;
        }

        try {
            // log
            if(log_condition){
                producer_rest_count += 1;
                System.out.println("wait rest");
                System.out.println("(" + iter + ") prod lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }

            while (producer_waits) {
//            while (buffer_lock.hasWaiters(producer_first)) {
                try{
                    producer_rest.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // log
            if(log_condition) {
                producer_rest_count -= 1;
                System.out.println("no wait rest");
                System.out.println("wait first");
                producer_first_count += 1;
                System.out.println("(" + iter + ") prod lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }

            producer_waits = true;
            while(! can_produce(k)){
                try {
                        producer_first.await();
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            producer_waits  = false;
            // log
            if(log_condition){
                System.out.println("no wait first");
                producer_first_count -= 1;
                System.out.println("(" + iter + ") prod lock state: C(" + consumer_first_count + ", " + consumer_rest_count + ") " +
                        "P(" + producer_first_count + ", " + producer_rest_count + ")  res: " + count);
            }
            
            add_to_buffer(k, element);

            consumer_first.signal();
            producer_rest.signal();
        } finally {
            buffer_lock.unlock();
        }

    }
    
    private void add_to_buffer(int k, int element){
        for(int i=0; i<k; i++) {
            buf[next_empty] = element;
            next_empty = (next_empty + 1) % size;
            count += 1;
        }
    }
    
    private int remove_from_buffer(int k){
        int element = -1;
        for(int i=0; i<k; i++) {
            element = buf[first_full];
            buf[first_full] = -1;
            first_full = (first_full + 1) % size;
            count -= 1;
        }

        return element;
    }
    
    private boolean can_produce(int k){
        return ! ((next_empty == first_full && count != 0) || (size - count) < k);
    }

    private boolean can_consume(int k){
        return ! ((first_full == next_empty && count == 0) || count < k);
    }
}
