package active_object_test_implemetation.ActiveObject;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    // Active Object's resource

    private Queue<Integer> buffer;

    public final int MAX_SIZE;

    public Buffer(int size){
        this.MAX_SIZE = size;
        this.buffer = new LinkedList<>();
    }

    public void put(int value){
        if (isFull()) {
            throw new IllegalStateException("Added to full buffer");
        } else {
            buffer.add(value);
        }
    }

    public int get(){
        // retrieves element from buffer
        // when buffer is empty throws java.util.NoSuchElementException
        return buffer.remove();
    }

    public boolean isFull() {
        return MAX_SIZE == buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}
