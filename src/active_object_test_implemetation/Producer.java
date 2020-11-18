package active_object_test_implemetation;

import active_object_test_implemetation.ActiveObject.BufferInterface;
import active_object_test_implemetation.ActiveObject.Future;

public class Producer extends Thread{
    private final BufferInterface buffer_proxy;
    final int id;
    final int output_salt_size = 1000;
    int value = 0; // for differentiating outputs

    final int MAX_REQUESTS = 2;
    Future[] request_status = new Future[MAX_REQUESTS];

    public Producer(BufferInterface proxy, int id){
        this.buffer_proxy = proxy;
        this.id = id;
    }

    private int get_output(){
//        int random_value = ThreadLocalRandom.current().nextInt(1,  output_salt_size);
        int output_value = (id + 1) * output_salt_size + value;
        value = (value + 1) % output_salt_size;

        System.out.println("(" + id + ") Produced: " + output_value);

        return output_value;
    }

    private void do_stuff(){
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int output = get_output();

        // create initial put requests
        for(int i=0; i < MAX_REQUESTS; i++){
            request_status[i] = buffer_proxy.put(output);
            output = get_output();
        }

//        for(int i = 0; i < 100; i++){
        while(true){
            for(int i=0; i < MAX_REQUESTS; i++){
                if(request_status[i].isAvailable()){
                    request_status[i] = buffer_proxy.put(output);
                    output = get_output();
                }
            }

            do_stuff();
        }
    }



}
