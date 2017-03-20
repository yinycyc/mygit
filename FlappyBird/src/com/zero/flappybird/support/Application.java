package com.zero.flappybird.support;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.zero.flappy.engine.ContextEngine;


public class Application {
	
	private static final int FRAME_WIDTH = 320;
	private static final int FRAME_HEIGHT = 500;
	
	public Application() {
		JFrame frame = new JFrame("flappy bird");
		ContextEngine engine = new ContextEngine();
		engine.launch();//启动引擎
		frame.add(engine);
        frame.setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        frame.setResizable(false);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation(width/2-FRAME_WIDTH/2, height/2-FRAME_HEIGHT/2);//窗口居中
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)  {
		new Application();
	}
}
