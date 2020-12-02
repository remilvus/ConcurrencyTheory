package comparison.GenericWorkers;

import comparison.FakeRandom;

public abstract class GenericWorker extends Thread {
    protected int task_count;
    protected int task_size;

    FakeRandom random_generator;

    public GenericWorker(int task_count, int task_size, FakeRandom random_generator) {
        this.random_generator = random_generator;
        this.task_count = task_count;
        this.task_size = task_size;
    }

    protected int getRandom() {
        return random_generator.get();
    }

    protected void doTask() {
        if (task_count == 0) return;

//        try {
//            sleep(task_size);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < task_size; i++) {
            Math.sin(21.37);
        }
        task_count--;
    }

    protected void finish() {
        while (task_count > 0) doTask();
    }

}
