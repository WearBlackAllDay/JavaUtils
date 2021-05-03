package wearblackallday.swing.components;

import wearblackallday.swing.SwingUtils;
import wearblackallday.util.TriConsumer;

import javax.swing.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CustomPanel extends JPanel {

	private final Map<String, JTextField> textFields = new HashMap<>();
	private final Dimension standardDimension;

	public CustomPanel(LayoutManager layoutManager, int defaultWidth, int defaultHeight) {
		this.setLayout(layoutManager);
		this.standardDimension = new Dimension(defaultWidth, defaultHeight);
	}

	public CustomPanel(LayoutManager layoutManager) {
		this.setLayout(layoutManager);
		this.standardDimension = null;
	}

	public CustomPanel(int defaultWidth, int defaultHeight) {
		this(new FlowLayout(), defaultWidth, defaultHeight);
	}

	public CustomPanel() {
		this(new FlowLayout());
	}


	public CustomPanel boxLayout(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		return this;
	}

	public CustomPanel addTextField(String text, int width, int height, String id) {
		JTextField jTextField = new JTextField(text);
		SwingUtils.setPrompt(jTextField, text);
		jTextField.setPreferredSize(new Dimension(width, height));
		this.add(jTextField);
		this.textFields.put(id, jTextField);
		return this;
	}

	public CustomPanel addTextField(String text, String id) {
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

	public JTextField getTextField(String id) {
		return this.textFields.get(id);
	}

	public CustomPanel addButton(String text, int width, int height, TriConsumer<CustomPanel, JButton, ActionEvent> actionListener) {
		JButton jButton = new JButton(text);
		jButton.setPreferredSize(new Dimension(width, height));
		jButton.addActionListener(e -> actionListener.accept(this, jButton, e));
		this.add(jButton);
		return this;
	}

	public CustomPanel addButton(String text, TriConsumer<CustomPanel, JButton, ActionEvent> actionListener) {
		JButton jButton = new JButton(text);
		jButton.setPreferredSize(this.standardDimension);
		jButton.addActionListener(e -> actionListener.accept(this, jButton, e));
		this.add(jButton);
		return this;
	}

	public CustomPanel addComponent(Component component) {
		return this.addComponent(() -> component);
	}

	public <C extends Component> CustomPanel addComponent(Supplier<C> cSupplier) {
		this.add(cSupplier.get());
		return this;
	}

	public <C extends Component> CustomPanel addComponent(Supplier<C> cSupplier, BiConsumer<CustomPanel, C> consumer) {
		C c = cSupplier.get();
		consumer.accept(this, c);
		this.add(c);
		return this;
	}
}
