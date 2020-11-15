package lab_06;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private ConcurrentHashMap<Integer, Integer> buffer;
//    private HashMap<Integer, Integer> buffer;
    public final int size;

    public Buffer(int size){
        this.size = size;
        this.buffer = new ConcurrentHashMap<>(size);
//        this.buffer = new HashMap<>(size);
    }

    public void put(int where, int k){
//        System.out.println("Buffer put " + k + " at " + where);
        buffer.put(where, k);
    }

    public int get(int where){
        int val = buffer.get(where);
        System.out.println("Buffer get " + where);
        return val;
    }
}
