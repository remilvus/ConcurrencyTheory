package comparison.GenericWorkers;

import comparison.FakeRandom;

import java.util.LinkedList;
import java.util.List;

public abstract class GenericProducer extends GenericWorker{

    private int value = 0;
    
    public GenericProducer(int task_count, int task_size, FakeRandom random_generator){
        super(task_count, task_size, random_generator);
    }

    private int getRandomOutputSize(){
        return getRandom();
    }

    protected List<Integer> getOutput(){
        int output = getRandomOutputSize();
        if(output == -1) return null;

        List<Integer> products = new LinkedList<>();

        for (int i = 0; i < output; i++) {
            int output_value = value;
            products.add(output_value);
            value = value + 1;
        }

        return products;
    }
}
