package wearblackallday.javautils.swing.components;

import wearblackallday.javautils.swing.SwingUtils;
import wearblackallday.javautils.util.function.TriConsumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Deprecated(forRemoval = true)
public class LPanel extends JPanel {
	private final Map<String, JTextField> textFields = new HashMap<>();
	private Dimension standardDimension = null;

	public LPanel withLayout(LayoutManager layoutManager) {
		this.setLayout(layoutManager);
		return this;
	}

	public LPanel withLayout(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		return this;
	}

	public LPanel defaultSize(int width, int height) {
		this.standardDimension = new Dimension(width, height);
		return this;
	}

	public LPanel addTextField(String text, String id, int width, int height) {
		JTextField jTextField = new JTextField(text);
		SwingUtils.setPrompt(jTextField, text);
		jTextField.setPreferredSize(new Dimension(width, height));
		this.add(jTextField);
		this.textFields.put(id, jTextField);
		return this;
	}

	public LPanel addTextField(String text, String id) {
		JTextField jTextField = new JTextField(text);
		SwingUtils.setPrompt(jTextField, text);
		jTextField.setPreferredSize(this.standardDimension);
		this.add(jTextField);
		this.textFields.put(id, jTextField);
		return this;
	}

	public String getText(String id) {
		return this.textFields.get(id).getText();
	}

	public int getInt(String id) {
		return Integer.parseInt(this.getText(id).trim());
	}

	public long getLong(String id) {
		return Long.parseLong(this.getText(id).trim());
	}

	public LPanel addButton(String text, TriConsumer<LPanel, JButton, ActionEvent> actionListener) {
		return this.addButton(text, (panel, button) -> button.addActionListener(e -> actionListener.accept(panel, button, e)));
	}

	public LPanel addButton(String text, Runnable action) {
		return this.addButton(text, (panel, button, event) -> action.run());
	}

	public LPanel addButton(String text, BiConsumer<LPanel, JButton> buttonCode) {
		JButton jButton = new JButton(text);
		jButton.setPreferredSize(this.standardDimension);
		buttonCode.accept(this, jButton);
		this.add(jButton);
		return this;
	}

	public LPanel addButton(String text, int width, int height, TriConsumer<LPanel, JButton, ActionEvent> actionListener) {
		return this.addButton(text, width, height, (panel, button) -> button.addActionListener(e -> actionListener.accept(panel, button, e)));
	}

	public LPanel addButton(String text, int width, int height, Runnable action) {
		return this.addButton(text, width, height, (panel, button, event) -> action.run());
	}

	public LPanel addButton(String text, int width, int height, BiConsumer<LPanel, JButton> buttonCode) {
		JButton jButton = new JButton(text);
		jButton.setPreferredSize(new Dimension(width, height));
		buttonCode.accept(this, jButton);
		this.add(jButton);
		return this;
	}

	public LPanel addComponent(JComponent component) {
		this.add(component);
		return this;
	}

	public <C extends JComponent> LPanel addComponent(C component, BiConsumer<LPanel, C> componentCode) {
		componentCode.accept(this, component);
		this.add(component);
		return this;
	}

	@Override
	public LPanel add(Component comp) {
		super.add(comp);
		return this;
	}
}
