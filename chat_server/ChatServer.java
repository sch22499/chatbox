/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;

/**
 *
 * @author im5no
 */
public class ChatServer
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        int port = 6000;
        Server chatServer = new Server(port);
    }
}