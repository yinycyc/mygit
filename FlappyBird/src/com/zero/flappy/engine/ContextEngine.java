package com.zero.flappy.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zero.flappybird.model.Bird;
import com.zero.flappybird.model.Ground;
import com.zero.flappybird.model.Pillar;

public class ContextEngine extends JPanel {
	
	/**
	 * 游戏环境引擎
	 */
	private static final long serialVersionUID = 000121L;
	private BufferedImage bgImage = null;
	private BufferedImage gameoverImage = null;
	private BufferedImage startImage = null;
	
	private Bird bird = null;
	
	/**
	 * 两个柱子
	 */
	private Pillar pillar1 = null;
	private Pillar pillar2 = null;
	private Ground ground = null;
	
	private int score;//分数
	private int best;
	
	private boolean isGameOver = false;
	private boolean isStart = false;
	
	private Timer pillarXTimer = null;
	private Timer pillarYTimer = null;
	
	private static final int GAME_SPEED = 800/60;
	
	/**
	 * 初始化数据对象
	 */
	public void initiation() {
		score = 0;
		bird = new Bird();//创建鸟
		pillar1 = new Pillar(320);//创建一个柱子
		pillar2 = new Pillar(320+180);//创建柱子
		ground =new Ground(480-80);//创建地面
		
		try {
			bgImage = ImageIO.read(new FileInputStream("images/bg.png"));//加载背景图片
			startImage = ImageIO.read(new FileInputStream("images/start.png"));//加载开始的图片
			gameoverImage = ImageIO.read(new FileInputStream("images/gameover.png"));//加载游戏结束的图片
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动引擎
	 */
	public void launch() {
		 initiation();
		 
		 /**
		  * 柱子上下动的定时器
		  */
		 pillarYTimer = new Timer(100,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isStart) {//游戏开始之后
					pillar1.moveY();
					pillar2.moveY();
				}
			}
		});
		 
		 /**
		  * 柱子左右动的定时器
		  */
		 pillarXTimer = new Timer(GAME_SPEED,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isStart) {//游戏开始之后
					pillar1.moveX();
					pillar2.moveX();
					
					bird.jump();
				}else {
					bird.jumpConstant();//鸟同一高度
				}
				
				ground.move();//地面移动
				
				if(!bird.isHit(pillar1, pillar2, ground)) {
					if(bird.pass(pillar1, pillar2)) {
						score++;

						if(score == 5) {
							pillarYTimer.start();
						}
					}
				}
				
				if(bird.isHit(pillar1, pillar2, ground)){//碰撞了
					isGameOver = true;
					isStart = false;
					if(score > best) best = score;
					pillarXTimer.stop();
					pillarYTimer.stop();
				}
				repaint();
			}
			
		});
		
		pillarXTimer.start();//启动定时器
		 
		/**
		 * 为Panel添加鼠标监听事件
		 */
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				isStart = true;
				isGameOver = false;
				
				if(!pillarXTimer.isRunning()) {
					initiation();
					pillarXTimer.start();
				}
				bird.flappy();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);//画背景
		
		if(!isStart&&!isGameOver) 
		     g.drawImage(startImage, 0, 0, startImage.getWidth(), startImage.getHeight(), null);//画开始
		
		if(!isGameOver&&isStart) bird.paint(g);
        else bird.paintConstant(g);

		if(isStart||isGameOver) {//画柱子
			pillar1.paint(g);
			pillar2.paint(g);
		}
		
		if(!isStart&&isGameOver) //游戏结束
			g.drawImage(gameoverImage,0,0, gameoverImage.getWidth(), gameoverImage.getHeight(), null);

		ground.paint(g);//画地面
		//画分数

		Font font = new Font("宋体",Font.BOLD,24);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("best:"+best+"", 20, 40);
		g.drawString("score:"+score+"", 120, 40);
		
	}
}
