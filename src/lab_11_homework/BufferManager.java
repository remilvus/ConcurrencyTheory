package lab_11_homework;

import org.jcsp.lang.*;

import java.util.Arrays;

public class BufferManager implements CSProcess {
    private final ChannelInputInt[] requestInput;
    private final ChannelOutputInt[] dataOutput;

    private final Alternative requestsAlt;

    final int bufferSize;
    int next = 0;

    public BufferManager(ChannelInputInt[] requests, ChannelOutputInt[] data, int bufferSize)
    {
        requestInput = requests;
        dataOutput = data;

        final Guard[] guards = Arrays.stream(requests).toArray(Guard[]::new);
        requestsAlt = new Alternative(guards);

        this.bufferSize = bufferSize;
    } // constructor


    @Override
    public void run() {

        while(true){
            int idx = requestsAlt.fairSelect();

            requestInput[idx].read();
            dataOutput[idx].write(next);

            next = (next + 1) % bufferSize;
        }
    }
}
