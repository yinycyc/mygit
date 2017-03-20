package com.zero.flappybird.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.zero.flappybird.context.ClassPathConfigReaderContext;

public class Pillar {
	/**
	 * 柱子
	 */
	
	private ClassPathConfigReaderContext ctx = new ClassPathConfigReaderContext();
	
	int x;//柱子的横坐标
	
	int y;//柱子的纵坐标
	
	int width;//宽度
	
	int height;//高度
	
	int gap = 110;//柱子的间隔
	
	private final int SPEED_X = ctx.PILLAR_SPEEDX;//柱子的移动速度
	private final int SPEED_Y = ctx.PILLAR_SPEEDY;
	
	private BufferedImage image = null;//柱子的图片
	
	public Pillar(int x) {

		this.x = x;
		this.y = new Random().nextInt(140) + 140;//随即生成纵坐标

		try {
			image = ImageIO.read(new FileInputStream("images/pillar.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	/**
	 * Y轴方向移动
	 */
	public void moveY() {
		this.y = y - new Random().nextInt(20) + 10;
		if(y < 140 ) {
			y = y + SPEED_Y;;
		}else if(y > 320) {
			y = y - SPEED_Y;
		}
	}
	
	/**
	 * X轴方向移动
	 */
	public void moveX() {
		x = x - SPEED_X;//柱子移动
		
		if(x<-width){//柱子移到最左边，重新回到屏幕的右边
			x = 320;
			this.y = new Random().nextInt(140) + 140;
		}
	}
	
	/**
	 * 画柱子
	 * @param g
	 */
	public void paint(Graphics g) {
		g.drawImage(image,  x-width/2, y-height/2,null);
	}
}
