package kamen2;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Kamen2 extends Canvas implements Runnable {
        public static Kamen2 game;
	private static final long serialVersionUID = 1L;
        public static int WIDTH = 400; //ширина
        public static int HEIGHT = 300; //высота
        public static String NAME = "Камень-Ножницы-Бумага"; //заголовок окна
        public static boolean running = false; 
        
        public static int isServer = 0;
        public static Socket client;
        public static int playerid = 0;
        
        public static int pressed = 0;
        public static int epressed = 0;
        
        public static int point1 = 0;
        public static int point2 = 0;
        
        public static Sprite bum1;
        public static Sprite noj1;
        public static Sprite kam1;
        public static Sprite bum2;
        public static Sprite noj2;
        public static Sprite kam2;


        public void start() {
	running = true;
	new Thread(this).start();
}
        
	public void run() {
	long lastTime = System.currentTimeMillis();
	long delta;
	
	init();
		
	while(running) {
		delta = System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();	
		update(delta);
		render();
	}
}
        
          public Sprite getSprite(String path) {
	BufferedImage sourceImage = null;
		
	try {
		URL url = this.getClass().getClassLoader().getResource(path);
		sourceImage = ImageIO.read(url);
	} catch (IOException e) {
		e.printStackTrace();
	}

	Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
		
	return sprite;
}
	
public void init() {
  addKeyListener(new KeyInputHandler());
  noj1 = getSprite("noj1.png");
  kam1 = getSprite("kam1.png");
  bum1 = getSprite("bum1.png");
  
  noj2 = getSprite("noj2.png");
  kam2 = getSprite("kam2.png");
  bum2 = getSprite("bum2.png");
  
		
}
	
public void render() {
	BufferStrategy bs = getBufferStrategy(); 
	if (bs == null) {
		createBufferStrategy(2); //создаем BufferStrategy для нашего холста
		requestFocus();
		return;
	}
        
        try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
		
	Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
	g.setColor(Color.white); //выбрать цвет
	g.fillRect(0, 0, getWidth(), getHeight()); //заполнить прямоугольник 
        
         noj2.draw(g, 10, 50);
         bum2.draw(g, 60, 50);
         kam2.draw(g, 110, 50);
         
         if(pressed == 1) noj1.draw(g, 10, 120);
         if(pressed == 2) bum1.draw(g, 10, 120);
         if(pressed == 3) kam1.draw(g, 10, 120);
         
         
         
         
            if(pressed != 0 && epressed != 0)
         {
         if(epressed == 1) noj1.draw(g, 210, 120);
         if(epressed == 2) bum1.draw(g, 210, 120);
         if(epressed == 3) kam1.draw(g, 210, 120);
             
             if(pressed == 1)
             {
                 if(epressed == 1)
                 {
                     point1 ++;
                     point2 ++;
                 }
                 if(epressed == 2)
                 {
                     point1 ++;                    
                 }
                 if(epressed == 3)
                 {
                     point2 ++;                    
                 }
             }
             if(pressed == 2)
             {
                 if(epressed == 1)
                 {
                    
                     point2 ++;
                 }
                 if(epressed == 2)
                 {
                     point1 ++;
                     point2 ++;
                 }
                 if(epressed == 3)
                 {
                     point1 ++;                    
                 }
             }
             if(pressed == 3)
             {
                 if(epressed == 1)
                 {
                    
                     point1 ++;
                 }
                 if(epressed == 2)
                 {
                    
                     point2 ++;
                 }
                 if(epressed == 3)
                 {
                     point1 ++;  
                     point2 ++;
                 }
             }
             
             pressed = 0;
             epressed = 0;
         }
               
         
        
        g.setColor(Color.black); //выбрать цвет
	g.fillRect(195, 100, 10, 400); //заполнить прямоугольник 
        g.fillRect(0, 100, 460, 10); //заполнить прямоугольник 
       
        g.drawString("1", 35, 50);
        g.drawString("2", 85, 50);
        g.drawString("3", 135, 50);
        
        
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
        g.drawString(point1+":"+point2, 165, 100);
        
	g.dispose();
	bs.show(); //показать
}
	
public void update(long delta) {
    
		
}


public static void main(String[] args)  throws IOException {
    
    	game = new Kamen2();
	game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	JFrame frame = new JFrame(Kamen2.NAME);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //выход из приложения по нажатию клавиши ESC
	frame.setLayout(new BorderLayout());
	frame.add(game, BorderLayout.CENTER); //добавляем холст на наш фрейм
	frame.pack();
	frame.setResizable(false);
	frame.setVisible(true);
	game.start();
    
    
        try 
        {
        if(isServer == 0) 
        {
            ConnectToServer("127.0.0.1",7777);
          
        }
        }   
        catch(IOException e)
        {
         Thread t = new LastServer(7777);
         t.start();        
         
        }
        if(isServer == 0) ConnectToServer("127.0.0.1",7777); 
        
	}

public static String ReadXmlToString(String xmlData)
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
              
               try
               {
               Integer getid = new Integer(eElement.getElementsByTagName("id").item(0).getTextContent()); 
               Integer getp = new Integer(eElement.getElementsByTagName("pressed").item(0).getTextContent()); 

               
               if(getid != playerid && playerid != 0)
               {
                   epressed = getp;
               }
               
               }
               catch (Exception e)
               {
                   System.out.println("ID Получен");
                   playerid = new Integer(eElement.getElementsByTagName("pp").item(0).getTextContent());
               } 
            }
         
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   return ansver;
       
   }
         
        
        
        
         public static void ConnectToServer(String serverName,int port) throws IOException { 

     
         System.out.println("Connecting to " + serverName + " on port " + port);
         client = new Socket(serverName, port);
         
         
         Thread sendMsg=new Thread(new SendMsg(client,game));
         sendMsg.start();
         System.out.println("CLIENT WAS CONNECTED " + client.getPort());
         isServer = 1;      
         while (true){ 
             
             
             
        try {
         
        InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         String s = in.readUTF();
         ReadXmlToString(s);
         
      }catch(IOException e) {
         e.printStackTrace();
      }
        
        
      }
    }

private class KeyInputHandler extends KeyAdapter {
    
public void keyPressed(KeyEvent e) { //клавиша нажата
    if(pressed == 0)
    {
	if (e.getKeyCode() == KeyEvent.VK_1) {
		pressed = 1;              
	}
	if (e.getKeyCode() == KeyEvent.VK_2) {
		pressed = 2; 
	}
        if (e.getKeyCode() == KeyEvent.VK_3) {
		pressed = 3;              
	}
    }
	
} 	

}

   
}

