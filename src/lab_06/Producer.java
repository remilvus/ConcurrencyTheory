package lab_06;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread{
    Monitor buf;
    int value = 0;
    int k;
    int id;
    int capacity_halved;
    boolean random = false;

    public Producer(Monitor buf, int output, int id){
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
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        for(int i = 0; i < 100; i++){
        while(true){
            do_stuff();
            if (random) k = random_output();
            int where = buf.start_prod();
            System.out.println("(" + id + ") Producent has ticket: " + where);
            do_stuff();
            buf.produce(where, value);
            System.out.println("(" + id + ") Producent produced: " + value + " at " + where);
            buf.end_prod(where);

//            System.out.println("(" + id + ") Produced: " + value + " at " + where);

            value++;
        }
    }



}
