package wearblackallday.javautils.swing.components;

import javax.swing.*;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.function.BiConsumer;

public class LFrame extends JFrame {
	public LFrame() {
		this("");
	}
	public LFrame(String title) {
		super(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public LFrame(String title, JMenuBar menu) {
		this(title);
		this.setJMenuBar(menu);
	}

	public LFrame(String title, JMenuBar menu, Container content) {
		this(title, menu);
		this.setContentPane(content);
		this.pack();
	}

	public LFrame contentLPane(BiConsumer<LFrame, LPanel> customCode) {
		LPanel lPanel = new LPanel();
		customCode.accept(this, lPanel);
		this.setContentPane(lPanel);
		this.pack();
		return this;
	}

	public LFrame center() {
		this.setLocationRelativeTo(null);
		return this;
	}

	public LFrame fullScreen() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		return this;
	}

	public LFrame sizeLock() {
		this.setResizable(false);
		return this;
	}

	public LFrame withLayout(LayoutManager layoutManager) {
		this.setLayout(layoutManager);
		return this;
	}

	public LFrame withLayout(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		return this;
	}

	public LFrame addComponent(JComponent component) {
		this.add(component);
		return this;
	}

	public <C extends JComponent> LFrame addComponent(C component, BiConsumer<LFrame, C> componentCode) {
		componentCode.accept(this, component);
		this.add(component);
		return this;
	}
}
