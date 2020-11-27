package comparison.monitor_with_list_buffer;

import comparison.FakeRandom;
import comparison.GenericWorkers.GenericProducer;

import java.util.List;

public class ProducerM extends GenericProducer {
    Buffer buffer;

    public ProducerM(Buffer buffer, int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
        this.buffer = buffer;
    }


    @Override
    public void run() {
        List<Integer> values = getOutput();

        while(true){
            buffer.produce(values);

            values = getOutput();
            if(values == null) {
                finish();
                return;
            }

            doTask();
        }
    }
}
