package lab_11;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer1 implements CSProcess {
    private One2OneChannelInt channel_prod;
    private One2OneChannelInt channel_cons;

    public Buffer1(final One2OneChannelInt prod, final One2OneChannelInt cons)
    {
        channel_prod = prod;
        channel_cons = cons;
    } // constructor

    public void run ()
    {
        int item;
        ChannelOutputInt channelOutput = channel_cons.out();
        ChannelInputInt channelInput = channel_prod.in();
        for (int k = 0; k < 100; k++) {
            item = channelInput.read();
            channelOutput.write(item);
        } // for
//        channelOutput.write(-1);
        System.out.println("Buffer ended.");
    }
}
