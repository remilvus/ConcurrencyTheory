package lab_05;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        int capacity = 10;
        int capacity_halved = capacity / 2;
        int producers = 1;
        int consumers = 3;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        Buffer chocolate_storage = new Buffer(capacity);


        for(int i=0; i < producers; i++){
//            int out = ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
//            chocolate_factories.add(new Producer(chocolate_storage, out, i));
//            chocolate_factories.add(new Producer(chocolate_storage, capacity_halved, i));
            chocolate_factories.add(new Producer(chocolate_storage, -1, i));
        }
        for(int i=0; i < consumers; i++){
//            int need = ThreadLocalRandom.current().nextInt(1, capacity_halved + 1);
//            people.add(new Consumer(chocolate_storage, need, i));
//            people.add(new Consumer(chocolate_storage, 1, i));
            people.add(new Consumer(chocolate_storage, -1, i));
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
