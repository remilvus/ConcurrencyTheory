package lab_06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Monitor {
//    private final int occupied[];
//    private final int full[];
    private ArrayList<Integer> full;
    private ArrayList<Integer> unoccupied;

    int count_occupied = 0;
    int count_full = 0;
    int produce_idx = 0;
    int consume_idx = 0;

    // private ConcurrentHashMap<Integer, Integer> buffer;
    private Buffer buffer;
    public final int size;
    int next_empty = 0;
    int first_full = 0;

    private ReentrantLock buffer_lock = new ReentrantLock();
    private Condition consumer_blocked = buffer_lock.newCondition();
    private Condition producer_blocked = buffer_lock.newCondition();

    public Monitor(int size){
        this.size = size;
        this.unoccupied = IntStream.range(0, size).boxed().collect(Collectors.toCollection(ArrayList::new));
        this.full = new ArrayList<>();
        this.buffer = new Buffer(size);
    }

    public int start_consume(){
        int resource_idx = -1;
        buffer_lock.lock();
        try {
            while(full.isEmpty()){
                consumer_blocked.await();
            }
            consumer_blocked.signal();

            resource_idx = full.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            buffer_lock.unlock();
        }

        return resource_idx;
    }

    public int consume(int where){
        return buffer.get(where);
    }

    public void end_consume(int where){
        buffer_lock.lock();
        try{
            unoccupied.add(where);
            producer_blocked.signal();
        }finally {
            buffer_lock.unlock();
        }
        log_tickets();
    }

    public void log_tickets(){
        //System.out.println("Taken tickets: " + (size - unoccupied.size() - full.size()) + " / " + size);
    }

    public int start_prod(){
        int empty = -1;
        buffer_lock.lock();
        try {
            while(unoccupied.isEmpty()){
                producer_blocked.await();
            }
        empty = unoccupied.remove(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            buffer_lock.unlock();
        }
        return empty;
    }

    public void produce(int where, int k){
        buffer.put(where, k);
    }

    public void end_prod(int where){
        buffer_lock.lock();
        try{
            full.add(where);
            consumer_blocked.signal();
        }finally {
            buffer_lock.unlock();
        }

        log_tickets();
    }


//    private boolean can_produce(int k){
//        return ! ((next_empty == first_full && count != 0) || (size - count) < k);
//    }
//
//    private boolean can_consume(int k){
//        return ! ((first_full == next_empty && count == 0) || count < k);
//    }
}
