package lab_03;

import lab_02.DiningRoom;
import lab_02.Philosopher;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        int capacity = 10;
        int producers = 100;
        int consumers = 100;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        Buffer chocolate_storage = new Buffer(capacity);

        for(int i=0; i < producers; i++){
            chocolate_factories.add(new Producer(chocolate_storage));
        }
        for(int i=0; i < consumers; i++){
            people.add(new Consumer(chocolate_storage));
        }

        chocolate_factories.forEach(Thread::start);
        people.forEach(Thread::start);

        for (Thread human : people) {
            human.join();
        }
        for (Thread factory : chocolate_factories) {
            factory.join();
        }

        long stop=System.currentTimeMillis();

        System.out.println("Running time (ms): "+(stop-start));
    }

}
