package wearblackallday.swing.components.builder;

import javax.swing.*;
import java.awt.Container;
import java.awt.Toolkit;

@Deprecated
public class FrameBuilder {

	private String title = "";
	private boolean centered, visible, sizeLocked, fullScreen;
	private JMenuBar menu;
	private Container contentPane;

	public static FrameBuilder newBuilder() {
		return new FrameBuilder();
	}

	public static FrameBuilder fromFrame(JFrame jFrame) {
		FrameBuilder fb = new FrameBuilder();
		fb.title = jFrame.getTitle();
		fb.visible = jFrame.isVisible();
		fb.sizeLocked = !jFrame.isResizable();
		fb.menu = jFrame.getJMenuBar();
		fb.contentPane = jFrame.getContentPane();
		return fb;
	}

	public FrameBuilder title(String title) {
		this.title = title;
		return this;
	}

	public FrameBuilder visible() {
		this.visible = true;
		return this;
	}

	public FrameBuilder centered() {
		this.centered = true;
		return this;
	}

	public FrameBuilder sizeLocked() {
		this.sizeLocked = true;
		return this;
	}

	public FrameBuilder fullScreen() {
		this.fullScreen = true;
		return this;
	}

	public FrameBuilder contentPane(Container contentPane) {
		this.contentPane = contentPane;
		return this;
	}

	public FrameBuilder menu(JMenuBar jMenuBar) {
		this.menu = jMenuBar;
		return this;
	}

	public JFrame create() {
		JFrame jFrame = new JFrame(this.title);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(this.visible);
		jFrame.setResizable(!this.sizeLocked);
		if(this.contentPane != null) jFrame.setContentPane(this.contentPane);
		if(this.menu != null) jFrame.setJMenuBar(this.menu);
		if(this.fullScreen) jFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		else jFrame.pack();
		if(this.centered) jFrame.setLocationRelativeTo(null);
		return jFrame;
	}
}
