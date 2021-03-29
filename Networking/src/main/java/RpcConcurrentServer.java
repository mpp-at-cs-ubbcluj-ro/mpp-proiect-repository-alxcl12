import utils.AbstractConcurrentServer;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer {

    private Services chatServer;

    public RpcConcurrentServer(int port, Services chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        ClientRpcReflectionWorker worker = new ClientRpcReflectionWorker(chatServer, client);

        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
