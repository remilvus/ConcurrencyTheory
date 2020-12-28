package lab_11_homework;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final ChannelOutputInt channelRequest;
    private final ChannelInputInt channelAck;
    private final ChannelOutputInt[] channelBuffer;
    private final int total;
    private boolean log = true;

    public Producer(ChannelOutputInt request, ChannelInputInt acknowledgment,
                    ChannelOutputInt[] buffer, int total)
    {
        channelRequest = request;
        channelAck = acknowledgment;
        channelBuffer = buffer;
        this.total = total;
    } // constructor

    public void run ()
    {
        int item;

        for (int k = 0; k < total; k++) {
            item = (int) (Math.random() * 10000) + 1;

//            if(log) System.out.println("(P) Request space for " + item + "...");

            channelRequest.write(0);
            int idx = channelAck.read();

            if(log) System.out.println("(P) Inserting " + item + " at " + idx + "...");

            channelBuffer[idx].write(item);
        } // for

        System.out.println("Producer ended.");
    }
} // class Producer