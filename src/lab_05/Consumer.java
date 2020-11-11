package lab_05;

import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread{
    Buffer buf;
    int value;
    int k = 3;
    int id;
    int capacity_halved;
    boolean random = false;

    public Consumer(Buffer buf, int need, int id){
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
//        try {
//            sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        while(true){
            do_stuff();
            if (random) k = random_need();
            value = buf.consume(k);
            System.out.println(">" + id + "< Consumed: " + k);
        }
    }
}
