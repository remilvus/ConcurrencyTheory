package lab_07.ActiveObject;

import lab_07.ActiveObject.MethodRequests.IMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread{
    private Queue<IMethodRequest> general_queue;
    private Queue<IMethodRequest> priority_queue;

    public Scheduler(){
        general_queue = new ConcurrentLinkedQueue<>();
        priority_queue = new ConcurrentLinkedQueue<>();
    }

    public void enqueue(IMethodRequest request){
        general_queue.add(request);
    }

    public void logComplete(IMethodRequest request){
        System.out.println("Servant completed request from " + request.get_id());
    }
    
    @Override
    public void run(){
        while(true){
            while(! priority_queue.isEmpty()){
                IMethodRequest request = priority_queue.peek();

                if(request.canExecute()){
                    request.execute();
                    priority_queue.remove();
                    logComplete(request);
                } else break;
            }

            if(! general_queue.isEmpty()){
                IMethodRequest request = general_queue.remove();

                if(request.canExecute()){
                    request.execute();
                    logComplete(request);
                } else {
                    isCorrectType(request);
                    priority_queue.add(request);
                }
            }
        }
    }

    private void isCorrectType(IMethodRequest request) {
        if((! priority_queue.isEmpty()) &&
              priority_queue.peek().getClass() != request.getClass()){
            throw new IllegalStateException("Requests of different types in priority queue");
        }
    }
}
