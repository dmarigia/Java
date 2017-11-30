/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamen2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 *
 * @author Баюмка
 */
public class LastServer extends Thread{
    
    private ServerSocket serverSocket;
    public List<Socket> clients=new ArrayList<Socket>();
    public List<ClientHandler> clientsHandler=new ArrayList<ClientHandler>();
     
   public LastServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
     // serverSocket.setSoTimeout(10000);
   }
   
   void updateConnections(List<Socket> clients){
       for(int i=0;i<this.clientsHandler.size();i++){
                this.clientsHandler.get(i).setClients(clients);
           }
   }
   
  public String ConnectMessage(int pp)
   {
              
               
          String xml="<?xml version = \"1.0\"?>"+
          "<class>"+
          "  <game rollno = \"1\">"+
          "    <pp>"+pp+"</pp>"+
          "</game>"+
          "</class>";
           
               return xml;
                 
   }
  
   public void run() {
      while(true) {
         try {
            System.out.println("SERVER WAS STARTED!!!" + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            clients.add(server);
            
            System.out.println("[SERVER]Just connected to " + server.getPort());
            
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(ConnectMessage(server.getPort()));
            
            
            ClientHandler clientHandler=new ClientHandler(server,clients,server.getPort());
            clientsHandler.add(clientHandler);
            Thread newClient=new Thread(clientHandler);  
             newClient.start();             
             updateConnections(clients);
             
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
    
    
}

