package comparison.AO_version;

import comparison.AO_version.ActiveObject.BufferProxy;
import comparison.AO_version.ActiveObject.Future;
import comparison.FakeRandom;
import comparison.GenericWorkers.GenericProducer;

import java.util.List;

public class ProducerAO extends GenericProducer {
    private final BufferProxy buffer_proxy;

    Future request_status;
    
    public ProducerAO(BufferProxy proxy, int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
        this.buffer_proxy = proxy;
    }

    @Override
    public void run() {
        List<Integer> output = getOutput();

        request_status = buffer_proxy.put(output);

        while(true){
            doTask();

            if(request_status.isReady()) {
                request_status = buffer_proxy.put(output);
                output = getOutput();
                if(output == null) {
                    finish();
                    return;
                };
            }
        }
    }
}
