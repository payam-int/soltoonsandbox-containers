package ir.pint.soltoon.soltoongame.server;

import ir.pint.soltoon.soltoongame.shared.communication.command.Command;
import ir.pint.soltoon.soltoongame.shared.communication.command.CommandInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.query.Query;
import ir.pint.soltoon.soltoongame.shared.communication.query.QueryInitialize;
import ir.pint.soltoon.soltoongame.shared.communication.result.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    public HashMap<Long,ClientHandler> id2client;
    
    Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        id2client = new HashMap<>();
    }

    public CommandInitialize accept() {
        Socket socket=null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientHandler ch = new ClientHandler(socket);
        ch.send(new QueryInitialize(0l));
        CommandInitialize c = (CommandInitialize) ch.receive();
        id2client.put(c.id,ch);
        return c;
    }

    public Command sendQuery(Query query) {
        ClientHandler ch = id2client.get(query.id);
        ch.send(query);
        return (Command) ch.receive();
    }

    public void sendResult(Result result) {
        id2client.get(result.id).send(result);
    }
    
    public class ClientHandler {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        public ClientHandler(Socket socket) {
            System.out.println("Socket established...");
            this.socket=socket;
            try {
                out = new ObjectOutputStream(this.socket.getOutputStream());
                out.writeObject("SERVER::Handshake");
                out.flush();
                in = new ObjectInputStream(this.socket.getInputStream());
                in.readObject();
                
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        public void send(Object o) {
            try {
                out.reset();
                out.writeObject(o);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Object receive() {
            try {
                return in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
