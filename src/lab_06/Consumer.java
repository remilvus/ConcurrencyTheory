package lab_06;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread{
    Monitor buf;
    int value;
    int k;
    int id;
    int capacity_halved;
    boolean random = false;

    public Consumer(Monitor buf, int need, int id){
        this.buf = buf;
        k = need;
        if (need <= 0) {
            random = true;
            capacity_halved = buf.size / 2;
        }
        this.id = id;
    }

    private int random_need(){
        return ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
    }

    private void do_stuff(){
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){

            int where = buf.start_consume();
            System.out.println(">" + id + "< Consumer has ticket: " + where);
            do_stuff();
            value = buf.consume(where);
            System.out.println(">" + id + "< Consumer consumed: " + value + " from " + where);
            buf.end_consume(where);

//            System.out.println(">" + id + "< consumed: " + value);
        }
    }
}
