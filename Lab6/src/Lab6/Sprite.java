
package kamen2;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Sprite {
	private Image image; //изображение
	
	public Sprite(Image image) {
		this.image = image;
	}
	
	public int getWidth() { //получаем ширину картинки
		return image.getWidth(null);
	}
            

	public int getHeight() { //получаем высоту картинки
		return image.getHeight(null);
	}
	
	public void draw(Graphics g,int x,int y) { //рисуем картинку
		g.drawImage(image,x,y,null);
	}
        
      
        
        
}
