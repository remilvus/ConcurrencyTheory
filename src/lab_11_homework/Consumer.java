package lab_11_homework;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final ChannelOutputInt channelRequest;
    private final ChannelInputInt channelAck;
    private final ChannelInputInt[] channelBuffer;
    private boolean log = true;

    public Consumer(ChannelOutputInt request, ChannelInputInt acknowledgment,
                    ChannelInputInt[] buffer)
    {
        channelRequest = request;
        channelAck = acknowledgment;
        channelBuffer = buffer;
    } // constructor

    public void run () {
        int item;

        while(true) {
            channelRequest.write(0);

            int idx = channelAck.read();

//            if(log)  System.out.println("(C) Taking from " + idx + "...");

            item = channelBuffer[idx].read();
            if(log) System.out.println("(C) Consumed " + item + " from " + idx + "...");
        } // for

//        System.out.println("Consumer ended.");
    }
} // class Consumer