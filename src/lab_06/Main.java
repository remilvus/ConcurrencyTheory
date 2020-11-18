package lab_06;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 5;
        int producers = 2;
        int consumers = 2;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        Monitor chocolate_storage = new Monitor(capacity);

        for(int i=0; i < producers; i++){
            chocolate_factories.add(new Producer(chocolate_storage, 1, i));
        }
        for(int i=0; i < consumers; i++){
            people.add(new Consumer(chocolate_storage, 1, i));
        }

        chocolate_factories.forEach(Thread::start);
        people.forEach(Thread::start);

        for (Thread human : people) {
            human.join();
        }
        for (Thread factory : chocolate_factories) {
            factory.join();
        }

    }

}
