package wearblackallday.swing.components;

import javax.swing.*;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.function.BiConsumer;

public class LDialog extends JDialog {
	public LDialog() {
		this("");
	}

	public LDialog(String title) {
		this.setTitle(title);
	}

	public LDialog(String title, JMenuBar menu) {
		this(title);
		this.setJMenuBar(menu);
	}

	public LDialog(String title, JMenuBar menu, Container content) {
		this(title, menu);
		this.setContentPane(content);
		this.pack();
	}

	public LDialog contentLPane(BiConsumer<LDialog, LPanel> customCode) {
		LPanel lPanel = new LPanel();
		customCode.accept(this, lPanel);
		this.setContentPane(lPanel);
		this.pack();
		return this;
	}

	public LDialog center() {
		this.setLocationRelativeTo(null);
		return this;
	}

	public LDialog fullScreen() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		return this;
	}

	public LDialog sizeLock() {
		this.setResizable(false);
		return this;
	}

	public LDialog onTop() {
		this.setAlwaysOnTop(true);
		return this;
	}

	public LDialog defaultButton(JButton button) {
		this.rootPane.setDefaultButton(button);
		return this;
	}

	public LDialog withLayout(LayoutManager layoutManager) {
		this.setLayout(layoutManager);
		return this;
	}

	public LDialog withLayout(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		return this;
	}

	public LDialog addComponent(JComponent component) {
		this.add(component);
		return this;
	}

	public <C extends JComponent> LDialog addComponent(C component, BiConsumer<LDialog, C> componentCode) {
		componentCode.accept(this, component);
		this.add(component);
		return this;
	}
}
