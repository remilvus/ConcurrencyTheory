package lab_07;

import lab_07.ActiveObject.BufferProxy;

import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 100;
        int producers = 2;
        int consumers = 2;
        int producer_output = -1;
        int consumer_need = -1;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        BufferProxy chocolate_storage = new BufferProxy(capacity);

        for(int i=0; i < producers; i++){
            chocolate_factories.add(new Producer(chocolate_storage, i+1, producer_output));
        }
        for(int i=0; i < consumers; i++){
            people.add(new Consumer(chocolate_storage, -i-1, consumer_need));
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
