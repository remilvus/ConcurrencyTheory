package active_object_test_implemetation.ActiveObject;

import active_object_test_implemetation.ActiveObject.MethodRequests.IMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Scheduler extends Thread{
    private Queue<IMethodRequest> activation_queue;

    Scheduler(){
        activation_queue = new ConcurrentLinkedQueue<>();
    }

    public void enqueue(IMethodRequest request){
        activation_queue.add(request);
    }

    @Override
    public void run(){
        while(true){
            if(! activation_queue.isEmpty()){
                IMethodRequest request = activation_queue.remove();

                if(request.can_execute()){
                    request.call();
                } else {
//                    this would be better if there were different queues for Producers and Consumers
//                    activation_queue.addFirst(request);
                    activation_queue.add(request);
                }
            }
        }
    }
}
