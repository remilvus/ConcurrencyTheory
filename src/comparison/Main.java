package comparison;

import comparison.AO_version.ActiveObject.BufferProxy;
import comparison.AO_version.ConsumerAO;
import comparison.AO_version.ProducerAO;
import comparison.monitor_with_list_buffer.Buffer;
import comparison.monitor_with_list_buffer.ConsumerM;
import comparison.monitor_with_list_buffer.ProducerM;

import java.util.Random;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        boolean AO_version = true;

        int buffer_time = 10;
        int task_time = 10;
        int task_count = 10;

        int capacity = 20;

        int producers = 10;
        int consumers = 10;

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);


        BufferProxy ao_chocolate_storage = null;
        if(AO_version) {
            BufferProxy chocolate_storage = new BufferProxy(capacity, buffer_time);
            ao_chocolate_storage = chocolate_storage;

            for (int i = 0; i < producers; i++) {
                FakeRandom not_random = new FakeRandom("");
                chocolate_factories.add(new ProducerAO(chocolate_storage, task_count, task_time, not_random));
            }

            for (int i = 0; i < consumers; i++) {
                FakeRandom not_random = new FakeRandom("");
                people.add(new ConsumerAO(chocolate_storage, task_count, task_time, not_random));
            }
        }else{
            Buffer chocolate_storage = new Buffer(capacity, buffer_time);

            for (int i = 0; i < producers; i++) {
                FakeRandom not_random = new FakeRandom("");
                chocolate_factories.add(new ProducerM(chocolate_storage, task_count, task_time, not_random));
            }

            for (int i = 0; i < consumers; i++) {
                FakeRandom not_random = new FakeRandom("");
                people.add(new ConsumerM(chocolate_storage, task_count, task_time, not_random));
            }
        }

        chocolate_factories.forEach(Thread::start);
        people.forEach(Thread::start);

        System.out.println("all ended");
        for (Thread human : people) {
            human.join();
        }
        System.out.println("all ended");
        for (Thread factory : chocolate_factories) {
            factory.join();
        }
        System.out.println("all ended");
        if(AO_version) ao_chocolate_storage.kill_scheduler();

    }

}
