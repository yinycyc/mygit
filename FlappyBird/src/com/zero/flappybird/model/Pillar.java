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
	 * ����
	 */
	
	private ClassPathConfigReaderContext ctx = new ClassPathConfigReaderContext();
	
	int x;//���ӵĺ�����
	
	int y;//���ӵ�������
	
	int width;//���
	
	int height;//�߶�
	
	int gap = 110;//���ӵļ��
	
	private final int SPEED_X = ctx.PILLAR_SPEEDX;//���ӵ��ƶ��ٶ�
	private final int SPEED_Y = ctx.PILLAR_SPEEDY;
	
	private BufferedImage image = null;//���ӵ�ͼƬ
	
	public Pillar(int x) {

		this.x = x;
		this.y = new Random().nextInt(140) + 140;//�漴����������

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
	 * Y�᷽���ƶ�
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
	 * X�᷽���ƶ�
	 */
	public void moveX() {
		x = x - SPEED_X;//�����ƶ�
		
		if(x<-width){//�����Ƶ�����ߣ����»ص���Ļ���ұ�
			x = 320;
			this.y = new Random().nextInt(140) + 140;
		}
	}
	
	/**
	 * ������
	 * @param g
	 */
	public void paint(Graphics g) {
		g.drawImage(image,  x-width/2, y-height/2,null);
	}
}
