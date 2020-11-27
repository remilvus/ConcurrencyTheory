package comparison.GenericWorkers;

import comparison.AO_version.ActiveObject.BufferProxy;
import comparison.AO_version.ActiveObject.Future;
import comparison.FakeRandom;

import java.util.LinkedList;
import java.util.List;

public abstract class GenericConsumer extends GenericWorker{

    public GenericConsumer(int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
    }

    protected int getRandomNeed(){
        return getRandom();
    }
}
