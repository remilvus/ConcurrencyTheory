package comparison;

import comparison.AO_version.ActiveObject.BufferProxy;
import comparison.AO_version.ConsumerAO;
import comparison.AO_version.ProducerAO;
import comparison.monitor_with_list_buffer.Buffer;
import comparison.monitor_with_list_buffer.ConsumerM;
import comparison.monitor_with_list_buffer.ProducerM;

import java.io.File;
import java.util.Random;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        boolean AO_version = Boolean.parseBoolean(args[0]);

        int buffer_time = Integer.parseInt(args[1]);
        int task_time = Integer.parseInt(args[2]);
        int task_count = Integer.parseInt(args[3]);

        int capacity = Integer.parseInt(args[4]);

        int producers = Integer.parseInt(args[5]);
        int consumers = Integer.parseInt(args[6]);

        String not_random_path = args[7];

        Vector<Thread> chocolate_factories = new Vector<>(producers);
        Vector<Thread> people = new Vector<>(consumers);

        BufferProxy ao_chocolate_storage = null;
        if(AO_version) {
            BufferProxy chocolate_storage = new BufferProxy(capacity, buffer_time);
            ao_chocolate_storage = chocolate_storage;

            for (int i = 0; i < producers; i++) {
                FakeRandom not_random = new FakeRandom(not_random_path + File.separatorChar + "prod" + i);
                chocolate_factories.add(new ProducerAO(chocolate_storage, task_count / producers, task_time, not_random));
            }

            for (int i = 0; i < consumers; i++) {
                FakeRandom not_random = new FakeRandom(not_random_path + File.separatorChar + "cons" + i);
                people.add(new ConsumerAO(chocolate_storage, task_count / consumers, task_time, not_random));
            }
        }else{
            Buffer chocolate_storage = new Buffer(capacity, buffer_time);

            for (int i = 0; i < producers; i++) {
                FakeRandom not_random = new FakeRandom(not_random_path + File.separatorChar + "prod" + i);
                chocolate_factories.add(new ProducerM(chocolate_storage, task_count / producers, task_time, not_random));
            }

            for (int i = 0; i < consumers; i++) {
                FakeRandom not_random = new FakeRandom(not_random_path + File.separatorChar + "cons" + i);
                people.add(new ConsumerM(chocolate_storage, task_count / consumers, task_time, not_random));
            }
        }

        chocolate_factories.forEach(Thread::start);
        people.forEach(Thread::start);

        for (Thread human : people) {
            human.join();
        }
        for (Thread factory : chocolate_factories) {
            factory.join();
        }
        if(AO_version) ao_chocolate_storage.kill_scheduler();

    }

}
