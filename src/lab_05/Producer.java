package lab_05;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread{
    Buffer buf;
    int value = 0;
    int k = 10;
    int id;
    int capacity_halved;
    boolean random = false;

    public Producer(Buffer buf, int output, int id){
        this.buf = buf;
        k = output;
        if (output <= 0) {
            random = true;
            capacity_halved = buf.size / 2;
        }
        this.id = id;
    }

    private int random_output(){
        return ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
    }

    private void do_stuff(){

    }

    @Override
    public void run() {
//        for(int i = 0; i < 100; i++){
        while(true){
            do_stuff();
            if (random) k = random_output();
            buf.produce(value, k);
            System.out.println("(" + id + ") Produced " + k);
            value++;
        }
    }



}
