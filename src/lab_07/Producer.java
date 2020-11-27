package lab_07;

import lab_07.ActiveObject.BufferProxy;
import lab_07.ActiveObject.Future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread{
    private final BufferProxy buffer_proxy;
    private int output;
    private int maximum_output;
    private boolean random = false;
    private final int output_salt_size = 1000;
    
    final int id;
    int value = 0; // for differentiating outputs
    
    final int MAX_REQUESTS = 1;
    Future[] request_status = new Future[MAX_REQUESTS];
    
    public Producer(BufferProxy proxy, int id, int output){
        this.buffer_proxy = proxy;
        this.id = id;

        this.output = output;
        if (output <= 0) {
            random = true;
            maximum_output = buffer_proxy.buffer_size / 2;
        }
    }

    private int getRandomOutputSize(){
        return ThreadLocalRandom.current().nextInt(1, maximum_output + 1);
    }

    private List<Integer> getOutput(){
        if(random) output = getRandomOutputSize();

        List<Integer> products = new LinkedList<>();

        for (int i = 0; i < output; i++) {
            int output_value = (id + 1) * output_salt_size + value;
            products.add(output_value);

            value = (value + 1) % output_salt_size;
        }

        System.out.println("(" + id + ") Produced: " + products.toString());

        return products;
    }

    private void doStuff(){
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        List<Integer> output = getOutput();

        // create initial put requests
        for(int i=0; i < MAX_REQUESTS; i++){
            request_status[i] = buffer_proxy.put(output, id);
            output = getOutput();
            doStuff();
        }

        while(true){
            for(int i=0; i < MAX_REQUESTS; i++){
                if(request_status[i].isReady()){
                    request_status[i] = buffer_proxy.put(output, id);
                    output = getOutput();
                }
            }

            doStuff();
        }
    }
}
