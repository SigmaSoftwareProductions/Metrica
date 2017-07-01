package com.sigma.metrica;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {
	public Window (int width, int height) {
		this.setSize(width, height);
		Toolkit toolkit = getToolkit ();
		Dimension size = toolkit.getScreenSize();
		this.setLocation(size.width/2 - this.getWidth()/2, size.height/2 - this.getHeight()/2);
	}
}
