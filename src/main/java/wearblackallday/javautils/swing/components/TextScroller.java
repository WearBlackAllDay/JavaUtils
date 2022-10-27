package wearblackallday.javautils.swing.components;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TextScroller extends JScrollPane {
	public TextScroller(int width, int height) {
		super(new JTextArea());
		this.setPreferredSize(new Dimension(width, height));
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public String getText() {
		return this.getTextArea().getText();
	}

	public void setText(String text) {
		this.getTextArea().setText(text);
	}

	public void addLine(String line) {
		this.setText(this.getText() + "\n" + line);
	}

	public Stream<String> lines() {
		return Arrays.stream(this.getText().split("\n+"));
	}

	public void editTextArea(Consumer<JTextArea> code) {
		code.accept(this.getTextArea());
	}

	private JTextArea getTextArea() {
		return (JTextArea)this.getViewport().getView();
	}
}
