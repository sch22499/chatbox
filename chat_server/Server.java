/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author im5no
 */
public class Server extends Thread
{
    private final Collection<Socket> clientSockets;
    private final int port;
    private final ServerSocket serverSocket;
    
    public Server(int port) throws IOException
    {
        this.clientSockets = new ArrayList<>();
        this.port = port;
        
        try
        {
            this.serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.out.println("Server couldn't connect to port " + port + ".");
            throw e;
        }
    }
    
    @Override
    public void run()
    {
        System.out.println("Server on.");
        
        boolean listeningForClients = true;
        while (listeningForClients)
        {
            Socket clientSocket;
            try
            {
                clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                serveAsync(clientSocket);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                listeningForClients = false;
            }
        }
        
        System.out.println("Server off.");
    }
    
    private void serveAsync(Socket clientSocket) throws IOException
    {
        ClientSocketServer clientSocketServer =
            new ClientSocketServer(
                clientSocket,
                this);
        clientSocketServer.start();
    }
    
    public void sendMessageToAll(String message) throws IOException
    {
        for (Socket clientSocket : clientSockets)
            sendMessage(
                clientSocket,
                message);
    }
    
    public void sendMessage(
        Socket clientSocket,
        String message) throws IOException
    {
        DataOutputStream dataOutputStream =
            new DataOutputStream(clientSocket.getOutputStream());
        
        byte[] messageBytes = message.getBytes();
        
        dataOutputStream.writeInt(messageBytes.length);
        dataOutputStream.write(messageBytes);
    }
}