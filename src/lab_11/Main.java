package lab_11;

import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class Main {
    public static void main(String[] args) {
        new Main();
    } // main

    public Main(){
//        OneProducerConsumer();
        ManyProducersConsumers();
    }

    public void OneProducerConsumer() {
            System.out.println("start");
            // Create channel object
            final One2OneChannelInt channel_prod = Channel.one2oneInt();
            final One2OneChannelInt channel_cons = Channel.one2oneInt();
            // Create and run parallel construct with a list of processes
            CSProcess[] procList = { new Producer1(channel_prod), new Consumer1(channel_cons),
                    new Buffer1(channel_prod, channel_cons)};
            // Processes
            Parallel par = new Parallel(procList); // PAR construct
            par.run(); // Execute processes in parallel
            System.out.println("end");
    }

    public void ManyProducersConsumers() {
        // Create channel objects
        final One2OneChannelInt[] prodChan = {Channel.one2oneInt(), Channel.one2oneInt()}; // Producers
        final One2OneChannelInt[] consReq = {Channel.one2oneInt(), Channel.one2oneInt()}; // Consumer requests
        final One2OneChannelInt[] consChan = {Channel.one2oneInt(), Channel.one2oneInt()}; // Consumer data
// Create parallel construct
        CSProcess[] procList = {new Producer2(prodChan[0], 0),
                new Producer2(prodChan[1], 100),
                new Buffer2(prodChan, consReq,
                        consChan),
                new Consumer2(consReq[0],
                        consChan[0]),
                new Consumer2(consReq[1],
                        consChan[1])}; // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    }
} // class Main
