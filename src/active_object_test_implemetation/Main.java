package active_object_test_implemetation;

import active_object_test_implemetation.ActiveObject.BufferInterface;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 10;
        int producers = 2;
        int consumers = 2;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        BufferInterface chocolate_storage = new BufferInterface(capacity);

        for(int i=0; i < producers; i++){
            chocolate_factories.add(new Producer(chocolate_storage, i));
        }
        for(int i=0; i < consumers; i++){
            people.add(new Consumer(chocolate_storage, i));
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
