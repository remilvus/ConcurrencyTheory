package active_object_test_implemetation;

import active_object_test_implemetation.ActiveObject.BufferInterface;
import active_object_test_implemetation.ActiveObject.Future;

public class Consumer extends Thread {
    BufferInterface buffer_proxy;
    int id;

    public Consumer(BufferInterface proxy, int id) {
        this.buffer_proxy = proxy;
        this.id = id;
    }

    private void do_stuff() {
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

            Future result_container = buffer_proxy.get();
            do_stuff();

            while (!result_container.isAvailable()) {
                // Consumer does things while resource is not available
                do_stuff();
            }
            int result = result_container.get();
            System.out.println(">" + id + "< Consumer consumed: " + result);
        }
    }
}
