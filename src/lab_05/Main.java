package lab_05;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        int capacity = 20;
        int capacity_halved = 20 / 2;
        int producers = 2;
        int consumers = 2;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        Buffer chocolate_storage = new Buffer(capacity);


        for(int i=0; i < producers; i++){
            int out = ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
            chocolate_factories.add(new Producer(chocolate_storage, out, i));
        }
        for(int i=0; i < consumers; i++){
            int need = ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
            people.add(new Consumer(chocolate_storage, need, i));
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
