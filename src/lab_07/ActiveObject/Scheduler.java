package lab_07.ActiveObject;

import lab_07.ActiveObject.MethodRequests.IMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread{
    private Queue<IMethodRequest> general_queue;
    private Queue<IMethodRequest> priority_queue;
    Buffer buffer; // used only for logs

    public Scheduler(Buffer buffer){
        general_queue = new ConcurrentLinkedQueue<>();
        priority_queue = new ConcurrentLinkedQueue<>();
        this.buffer = buffer;
    }

    public void enqueue(IMethodRequest request){
        general_queue.add(request);
    }

    public void logComplete(IMethodRequest request){
        System.out.println("Servant completed request from " + request.get_id());
        System.out.println("Buffer state: " + buffer.size() + " / " + buffer.MAX_SIZE + "\n");
    }

    public void logQueues(String msg){
        System.out.println(msg + "General: " + general_queue.toString());
        System.out.println(msg + "Priority:" + priority_queue.toString());
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

            logQueues("1.");

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

            logQueues("2.");

            // loops for better logging
            while(general_queue.isEmpty() && ((! priority_queue.isEmpty()) && (! priority_queue.peek().canExecute()) )){}
            while(general_queue.isEmpty() && priority_queue.isEmpty()){}

        }
    }

    private void isCorrectType(IMethodRequest request) {
        if((! priority_queue.isEmpty()) &&
              priority_queue.peek().getClass() != request.getClass()){
            throw new IllegalStateException("Requests of different types in priority queue");
        }
    }
}
