/*
 *  @author albua
 *  created on 18/04/2021
 */

import utils.AbstractConcurrentServer;

import java.net.Socket;

public class ProtoConcurrentServer extends AbstractConcurrentServer {

    private Services chatServer;

    public ProtoConcurrentServer(int port, Services chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Proto concurent server");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoWorker worker=new ProtoWorker(chatServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
