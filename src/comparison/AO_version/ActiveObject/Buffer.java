package comparison.AO_version.ActiveObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Buffer {
    // Active Object's resource

    private Queue<Integer> buffer;
    private int wait_time;
    public final int MAX_SIZE;

    public Buffer(int size, int wait_time){
        this.MAX_SIZE = size;
        this.wait_time = wait_time;
        this.buffer = new LinkedList<>();
    }

    public void put(List<Integer> values){
        if (buffer.size() + values.size() > MAX_SIZE) {
            throw new IllegalStateException("Added to full buffer");
        } else {
            int size = values.size();
            for (int i = 0; i < size; i++) {
                buffer.add(values.remove(0));
                try {
                    Thread.sleep(wait_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Integer> get(int how_many){
        // retrieves element from buffer
        // when buffer is empty throws java.util.NoSuchElementException
        List<Integer> result = new LinkedList<>();

        for (int i = 0; i < how_many; i++) {
            result.add(buffer.remove());
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean isFull() {
        return MAX_SIZE == buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean canPut(int count) {
        return buffer.size() + count <= MAX_SIZE;
    }

    public int size() {return buffer.size();}
}
