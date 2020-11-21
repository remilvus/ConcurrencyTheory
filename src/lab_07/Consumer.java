package lab_07;

import lab_07.ActiveObject.BufferProxy;
import lab_07.ActiveObject.Future;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {
    BufferProxy buffer_proxy;
    int id;
    int need;
    int maximum_need;
    boolean random = false;

    public Consumer(BufferProxy proxy, int id, int need) {
        this.buffer_proxy = proxy;
        this.id = id;

        this.need = need;
        if (need <= 0) {
            random = true;
            maximum_need = buffer_proxy.buffer_size / 2;
        }
    }

    private int randomNeed(){
        return ThreadLocalRandom.current().nextInt(1, maximum_need + 1);
    }

    private void doStuff() {
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if(random) need = randomNeed();

            Future result_container = buffer_proxy.get(need, id);
            doStuff();

            while (!result_container.isReady()) {
                // Consumer works while resource is not available
                doStuff();
            }
            List<Integer> result = result_container.get();

            System.out.println(">" + id + "< Consumer consumed: " + result.toString());
        }
    }
}
