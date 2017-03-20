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
	 * ��Ϸ��������
	 */
	private static final long serialVersionUID = 000121L;
	private BufferedImage bgImage = null;
	private BufferedImage gameoverImage = null;
	private BufferedImage startImage = null;
	
	private Bird bird = null;
	
	/**
	 * ��������
	 */
	private Pillar pillar1 = null;
	private Pillar pillar2 = null;
	private Ground ground = null;
	
	private int score;//����
	private int best;
	
	private boolean isGameOver = false;
	private boolean isStart = false;
	
	private Timer pillarXTimer = null;
	private Timer pillarYTimer = null;
	
	private static final int GAME_SPEED = 800/60;
	
	/**
	 * ��ʼ�����ݶ���
	 */
	public void initiation() {
		score = 0;
		bird = new Bird();//������
		pillar1 = new Pillar(320);//����һ������
		pillar2 = new Pillar(320+180);//��������
		ground =new Ground(480-80);//��������
		
		try {
			bgImage = ImageIO.read(new FileInputStream("images/bg.png"));//���ر���ͼƬ
			startImage = ImageIO.read(new FileInputStream("images/start.png"));//���ؿ�ʼ��ͼƬ
			gameoverImage = ImageIO.read(new FileInputStream("images/gameover.png"));//������Ϸ������ͼƬ
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������
	 */
	public void launch() {
		 initiation();
		 
		 /**
		  * �������¶��Ķ�ʱ��
		  */
		 pillarYTimer = new Timer(100,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isStart) {//��Ϸ��ʼ֮��
					pillar1.moveY();
					pillar2.moveY();
				}
			}
		});
		 
		 /**
		  * �������Ҷ��Ķ�ʱ��
		  */
		 pillarXTimer = new Timer(GAME_SPEED,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isStart) {//��Ϸ��ʼ֮��
					pillar1.moveX();
					pillar2.moveX();
					
					bird.jump();
				}else {
					bird.jumpConstant();//��ͬһ�߶�
				}
				
				ground.move();//�����ƶ�
				
				if(!bird.isHit(pillar1, pillar2, ground)) {
					if(bird.pass(pillar1, pillar2)) {
						score++;

						if(score == 5) {
							pillarYTimer.start();
						}
					}
				}
				
				if(bird.isHit(pillar1, pillar2, ground)){//��ײ��
					isGameOver = true;
					isStart = false;
					if(score > best) best = score;
					pillarXTimer.stop();
					pillarYTimer.stop();
				}
				repaint();
			}
			
		});
		
		pillarXTimer.start();//������ʱ��
		 
		/**
		 * ΪPanel����������¼�
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
		g.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);//������
		
		if(!isStart&&!isGameOver) 
		     g.drawImage(startImage, 0, 0, startImage.getWidth(), startImage.getHeight(), null);//����ʼ
		
		if(!isGameOver&&isStart) bird.paint(g);
        else bird.paintConstant(g);

		if(isStart||isGameOver) {//������
			pillar1.paint(g);
			pillar2.paint(g);
		}
		
		if(!isStart&&isGameOver) //��Ϸ����
			g.drawImage(gameoverImage,0,0, gameoverImage.getWidth(), gameoverImage.getHeight(), null);

		ground.paint(g);//������
		//������

		Font font = new Font("����",Font.BOLD,24);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("best:"+best+"", 20, 40);
		g.drawString("score:"+score+"", 120, 40);
		
	}
}
