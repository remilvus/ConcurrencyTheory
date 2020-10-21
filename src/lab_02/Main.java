package lab_02;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();


        Vector<Thread> philosophers = new Vector<>(5);
        DiningRoom dining = new DiningRoom();

        for(int i=0; i < 5; i++){
            philosophers.add(new Thread(new Philosopher(i, dining)));
        }

        philosophers.forEach(Thread::start);

        for (Thread phil : philosophers) {
            phil.join();
        }

        long stop=System.currentTimeMillis();

        System.out.println("Running time (ms): "+(stop-start));
    }

}
