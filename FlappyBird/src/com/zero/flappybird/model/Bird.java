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
	 * 小鸟
	 */
	
	private ClassPathConfigReaderContext ctx = new ClassPathConfigReaderContext();
	
	//鸟的位置
	int x = 80;
	int y = 250;
	
	//鸟的宽高
	int height;
	int width;
	
	double v0 = ctx.BIRD_V0;//初始速度
	
	double speed;//当前速度
	
	double g =  ctx.BIRD_G;//鸟的重力加速度，虚拟的重力场
	
	double t = 0.25;//时间间隔
	
	double s;//鸟向上跳越的高度
	
	double angle;//飞行角度
	
	int area  = 5 * 5;//正方形的鸟的面积
	
	private BufferedImage image = null;
	
	/** 动画帧 */
	private BufferedImage[] images;
	private int index = 0;
	
	public Bird() {
		try {
			//加载动画帧
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
	
	//鸟开始飞行,赋予一个初速度
	public void flappy() {
		speed = v0;
	}
	
	//鸟跳跃
	public void jump() {
		double v0 = speed;
		
		double v = v0 - g*t;//计算垂直上抛运动, 经过时间t以后的速度, 
		
		speed = v;//作为下次计算的初始速度
		
		double s = v0*t - (0.5)* g * t * t;//计算垂直上抛运动的运行距离
		
		y = y - (int)s;//将计算的距离 换算为 y坐标的变化
		
		
		//计算小鸟的仰角, 
		angle = -Math.atan(s/10);
		
		index++;
		image = images[(index/10)%3];//换照片

	}
	
	int flag = 0;
	/**
	 * 鸟匀速运动
	 */
	public void jumpConstant() {
        
		index++;
		image = images[(index/10)%3];
	}
	
	//画鸟
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.rotate(angle, this.x, this.y);//旋转绘图坐标系, 绘制
		
		int x = this.x - width/2;//找到鸟中心位置的X,鸟是一个正方形
		int y = this.y - height/2;//找到鸟中心位置的Y
		
		g.drawImage(image,x,y,width,height,null);
		
		g2.rotate(-angle, this.x, this.y);//旋转回来 

	}
	
	public void paintConstant(Graphics g) {
		g.drawImage(image,x,y,width,height,null);
	}
	
	
	/*鸟的信息
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "x= " + x + "\n y=" + y + "\n g=" + g + "\n t=" + t + "\n, v0="
				+ v0 + "\n speed=" + speed + "\n s="+s+"";
	}
	
	
	/** 判断鸟是否通过柱子 */
	public boolean pass(Pillar p1, Pillar p2) {
		return p1.x==x || p2.x==x;
	}
	
	
	/** 判断鸟是否与柱子和地发生碰撞 */
	public boolean isHit(Pillar p1, Pillar p2, Ground ground) {
		//碰到地面
		if(y-area/2 >= ground.y){
			return true;
		}
		//碰到柱子
		return isHit(p1) || isHit(p2);
	}
	/** 检查当前鸟是否碰到柱子 */
	public boolean isHit(Pillar p){
		//如果鸟碰到柱子: 鸟的中心点x坐标在 柱子宽度 + 鸟的一半
		if( x> p.x-p.width/2-area/2 && x<p.x+p.width/2+area/2){
			if(y>p.y-p.gap/2+area/2 && y<p.y+p.gap/2-area/2 ){
				return false;
			}
			return true;
		}
		return false;
	}
}
