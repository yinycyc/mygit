package com.zero.flappybird.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zero.flappybird.context.ClassPathConfigReaderContext;

public class Ground {
	
	/**
	 * 地面
	 */
	
	private ClassPathConfigReaderContext ctx = new ClassPathConfigReaderContext();
	
	int x;
	
	int y;
	
	int width;
	
	int height;
	
	private final  int SPEED = ctx.GROUND_SPEED;
	
	private BufferedImage image;
	
	public Ground(int y){
		try {
			image = ImageIO.read(new FileInputStream("images/ground.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this. y = y;
		width = image.getWidth();
		height = image.getHeight();
		
		x = 0;//初始X位置
	}
	
	/**
	 * 柱子移动
	 */
	public void move(){
		x= x - SPEED;
		
		if(x<= -(width-345)){
			x=0;
		}
	}
	

	/**
	 * 画柱子
	 * @param g
	 */
	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);		
	}
}
