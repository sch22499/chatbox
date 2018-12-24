package chatserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author im5no
 */
public class ClientSocketServer extends Thread
{
    private final Socket clientSocket;
    private final DataInputStream inputStream;
    private final Server server;
    
    public ClientSocketServer(
        Socket clientSocket,
        Server server) throws IOException
    {
        this.clientSocket = clientSocket;
        this.inputStream = new DataInputStream(clientSocket.getInputStream());
        this.server = server;
    }
    
    @Override
    public void run()
    {
        try
        {
            listenForClientInput();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    private void listenForClientInput() throws IOException
    {
        while (true)
        {
            int length = inputStream.readInt();
            if (length > 0)
            {
                byte[] message = new byte[length];
                inputStream.readFully(
                    message,
                    0,
                    message.length);

                String receivedMessage = "";
                for (byte nextByte : message)
                {
                    receivedMessage = receivedMessage + (char) nextByte;
                }
                
                server.sendMessageToAll(receivedMessage);
            }
        }
    }
}