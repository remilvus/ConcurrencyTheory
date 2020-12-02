package comparison.AO_version.ActiveObject;

import comparison.AO_version.ActiveObject.MethodRequests.EndRequest;
import comparison.AO_version.ActiveObject.MethodRequests.IMethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler extends Thread{
    private Queue<IMethodRequest> general_queue;
    private Queue<IMethodRequest> priority_queue;
    Buffer buffer; // used only for logs
    ReentrantLock lock = new ReentrantLock();
    Condition general_cond = lock.newCondition();

    public Scheduler(Buffer buffer){
        general_queue = new ConcurrentLinkedQueue<>();
        priority_queue = new ConcurrentLinkedQueue<>();
        this.buffer = buffer;
    }

    public void enqueue(IMethodRequest request){
        lock.lock();
        try {
            general_queue.add(request);
            general_cond.signal();
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void run(){
        while(true){
            while(! priority_queue.isEmpty()){
                IMethodRequest request = priority_queue.peek();

                if(request.canExecute()){
                    request.execute();
                    priority_queue.remove();
                } else break;
            }

            waitForGeneral();

            if(! general_queue.isEmpty()){
                IMethodRequest request = general_queue.remove();
                if(request instanceof EndRequest) return;

                if(request.canExecute() && ! isClassInPriority(request)){
                    request.execute();
                } else {
                    isCorrectType(request);
                    priority_queue.add(request);
                }
            }

        }
    }

    private void waitForGeneral(){
        // waits on condition until general_queue is not empty
        if(general_queue.isEmpty()){
            lock.lock();
            try{
                while(general_queue.isEmpty()) {
                    try {
                        general_cond.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                lock.unlock();
            }
        }
    }

    private boolean isClassInPriority(IMethodRequest request){
        if (priority_queue.isEmpty()) return false;
        return priority_queue.peek().getClass() == request.getClass();
    }

    private void isCorrectType(IMethodRequest request) {
        if((! priority_queue.isEmpty()) &&
              priority_queue.peek().getClass() != request.getClass()){
            throw new IllegalStateException("Requests of different types in priority queue");
        }
    }
}
