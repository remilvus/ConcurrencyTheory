package lab_03;

import java.sql.Array;

public class Buffer {
    private final int buf[];
    private final int size;
    int next_empty = 0;
    int first_full = 0;
    int count = 0;

    public Buffer(int size){
        this.size = size;
        this.buf = new int[size];
    }

    public synchronized int consume(){
        int element;

            while(first_full == next_empty && count == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            element = buf[first_full];
            buf[first_full] = -1;
            first_full = (first_full + 1) % size;
            count -= 1;

            notify();

        return element;
    }

    public synchronized void produce(int element){
            while(next_empty == first_full && count != 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            buf[next_empty] = element;
            next_empty = (next_empty + 1) % size;
            count += 1;

            notify();
    }
}
