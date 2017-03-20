package com.zero.flappybird.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.zero.flappybird.context.ClassPathConfigReaderContext;

public class Bird {
	/**
	 * С��
	 */
	
	private ClassPathConfigReaderContext ctx = new ClassPathConfigReaderContext();
	
	//���λ��
	int x = 80;
	int y = 250;
	
	//��Ŀ��
	int height;
	int width;
	
	double v0 = ctx.BIRD_V0;//��ʼ�ٶ�
	
	double speed;//��ǰ�ٶ�
	
	double g =  ctx.BIRD_G;//����������ٶȣ������������
	
	double t = 0.25;//ʱ����
	
	double s;//��������Խ�ĸ߶�
	
	double angle;//���нǶ�
	
	int area  = 5 * 5;//�����ε�������
	
	private BufferedImage image = null;
	
	/** ����֡ */
	private BufferedImage[] images;
	private int index = 0;
	
	public Bird() {
		try {
			//���ض���֡
			images = new BufferedImage[3];
			images[0] = ImageIO.read(new FileInputStream("images/0.png"));
			images[1] = ImageIO.read(new FileInputStream("images/1.png"));
			images[2] = ImageIO.read(new FileInputStream("images/2.png"));
			image = images[0];
			
			this.width = image.getWidth();
			this.height = image.getHeight();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//��ʼ����,����һ�����ٶ�
	public void flappy() {
		speed = v0;
	}
	
	//����Ծ
	public void jump() {
		double v0 = speed;
		
		double v = v0 - g*t;//���㴹ֱ�����˶�, ����ʱ��t�Ժ���ٶ�, 
		
		speed = v;//��Ϊ�´μ���ĳ�ʼ�ٶ�
		
		double s = v0*t - (0.5)* g * t * t;//���㴹ֱ�����˶������о���
		
		y = y - (int)s;//������ľ��� ����Ϊ y����ı仯
		
		
		//����С�������, 
		angle = -Math.atan(s/10);
		
		index++;
		image = images[(index/10)%3];//����Ƭ

	}
	
	int flag = 0;
	/**
	 * �������˶�
	 */
	public void jumpConstant() {
        
		index++;
		image = images[(index/10)%3];
	}
	
	//����
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.rotate(angle, this.x, this.y);//��ת��ͼ����ϵ, ����
		
		int x = this.x - width/2;//�ҵ�������λ�õ�X,����һ��������
		int y = this.y - height/2;//�ҵ�������λ�õ�Y
		
		g.drawImage(image,x,y,width,height,null);
		
		g2.rotate(-angle, this.x, this.y);//��ת���� 

	}
	
	public void paintConstant(Graphics g) {
		g.drawImage(image,x,y,width,height,null);
	}
	
	
	/*�����Ϣ
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "x= " + x + "\n y=" + y + "\n g=" + g + "\n t=" + t + "\n, v0="
				+ v0 + "\n speed=" + speed + "\n s="+s+"";
	}
	
	
	/** �ж����Ƿ�ͨ������ */
	public boolean pass(Pillar p1, Pillar p2) {
		return p1.x==x || p2.x==x;
	}
	
	
	/** �ж����Ƿ������Ӻ͵ط�����ײ */
	public boolean isHit(Pillar p1, Pillar p2, Ground ground) {
		//��������
		if(y-area/2 >= ground.y){
			return true;
		}
		//��������
		return isHit(p1) || isHit(p2);
	}
	/** ��鵱ǰ���Ƿ��������� */
	public boolean isHit(Pillar p){
		//�������������: ������ĵ�x������ ���ӿ�� + ���һ��
		if( x> p.x-p.width/2-area/2 && x<p.x+p.width/2+area/2){
			if(y>p.y-p.gap/2+area/2 && y<p.y+p.gap/2-area/2 ){
				return false;
			}
			return true;
		}
		return false;
	}
}
