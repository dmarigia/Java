/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamen2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SendMsg extends Kamen2 implements Runnable{
    Socket mySocket;
    Kamen2 myform;
    public SendMsg(){}
    public SendMsg(Socket mySocket, Kamen2 getform){
    this.mySocket=mySocket;
    myform = getform;
    }
public void setMsg(String msg){
    
}
    @Override
    public void run() {
        while(true){
        try {
              try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
            
          String xml="<?xml version = \"1.0\"?>"+
          "<class>"+
          "  <game rollno = \"1\">"+
          "    <id>"+myform.playerid+"</id>"+
          "    <pressed>"+myform.pressed+"</pressed>"+
          "</game>"+
          "</class>";
            
            OutputStream outToServer  = mySocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(xml);
           // System.out.println("Клиент отправил: id:"+myform.playerid+" X:"+myform.x+" Y:"+myform.y);
            
         /*  try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tanks2.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            
        } catch (IOException ex) {
            Logger.getLogger(SendMsg.class.getName()).log(Level.SEVERE, null, ex);
        } 
        }
    }
    
    
}

