package comparison.AO_version;

import comparison.AO_version.ActiveObject.BufferProxy;
import comparison.AO_version.ActiveObject.Future;
import comparison.FakeRandom;
import comparison.GenericWorkers.GenericConsumer;
import comparison.monitor_with_list_buffer.Buffer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerAO extends GenericConsumer {
    BufferProxy buffer_proxy;

    public ConsumerAO(BufferProxy buffer_proxy, int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
        this.buffer_proxy = buffer_proxy;
    }

    @Override
    public void run() {
        List<Integer> values;

        while (true) {
            int need = getRandomNeed();
            if(need == -1){
                finish();
                return;
            }

            Future result_container = buffer_proxy.get(need);

            while (!result_container.isReady()) {
                doTask();
            }

            values = result_container.get();

        }
    }
}
