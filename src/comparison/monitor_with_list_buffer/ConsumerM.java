package comparison.monitor_with_list_buffer;

import comparison.FakeRandom;
import comparison.GenericWorkers.GenericConsumer;

import java.util.List;

public class ConsumerM extends GenericConsumer {
    Buffer buffer;

    public ConsumerM(Buffer buffer, int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        List<Integer> values;
        while(true){
            int need = getRandomNeed();
            if(need == -1) {
                finish();
                return;
            }
            values = buffer.consume(need);

            doTask();
        }
    }
}
