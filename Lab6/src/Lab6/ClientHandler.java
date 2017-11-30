/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamen2;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ClientHandler extends Kamen2 implements Runnable{
    private Socket mySocket;
    int kekid=1;
    public List<Socket> clients=new ArrayList<Socket>();
    int myform;
    
    
    
    
    public ClientHandler(){
    }
    public ClientHandler(Socket socket){
        this.mySocket=socket;
    }
    public ClientHandler(Socket socket, List<Socket> clients){
        this.mySocket=socket;
        this.clients=new ArrayList<Socket>(clients);
    }
    public ClientHandler(Socket socket, List<Socket> clients, int getid){
        this.mySocket=socket;
        this.clients=new ArrayList<Socket>(clients);
        myform = getid;
    }
    
    public List<?>getClients(){
    return this.clients;
    }
    public void setClients(List<Socket> clients){
    this.clients=new ArrayList<Socket>(clients);
    }
    public String XmlToString(String xmlData)
   {String ansver="Error";
   try {
ansver="";
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         StringReader sr = new StringReader(xmlData);
         InputSource is=new InputSource();
        is.setCharacterStream(sr);
         Document doc;
           doc = dBuilder.parse(is);
         doc.getDocumentElement().normalize();
         
         
         NodeList nList = doc.getElementsByTagName("game");
         
        

         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               Integer getid = new Integer(eElement.getElementsByTagName("id").item(0).getTextContent());             
               Integer getp = new Integer(eElement.getElementsByTagName("pressed").item(0).getTextContent());
               
        
  //-----------------------------------------------------------------------------                
           
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
            
               String xml="<?xml version = \"1.0\"?>"+
          "<class>"+
          "  <game rollno = \"1\">"+
          "  <id>"+getid+"</id>"+
          "  <pressed>"+getp+"</pressed>"+
          "</game>"+
          "</class>";
               //System.out.println(xml);
               return xml;
               
            }
         }
         
        
           }
    catch (Exception e)
           {
               System.out.println("Это не коорндинаты");
           }
   
       
   
   return ansver;
       
   }
    

    @Override
    public void run() {
    while(true){
            try {
                
                DataInputStream in = new DataInputStream(this.mySocket.getInputStream());
                String s = in.readUTF();
                for(int i=0; i<clients.size();i++){
                   Socket temp;
                   temp = this.clients.get(i);
                   if(temp!=this.mySocket){
                    DataOutputStream out = new DataOutputStream(temp.getOutputStream());
                     out.writeUTF(XmlToString(s));
                  }
                    
               }
                DataOutputStream out = new DataOutputStream(this.mySocket.getOutputStream());
                out.writeUTF(XmlToString(s));
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
            }    
    }
    
}
