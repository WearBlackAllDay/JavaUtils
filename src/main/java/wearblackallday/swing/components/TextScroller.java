package wearblackallday.swing.components;

import wearblackallday.data.Strings;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.stream.Stream;

public class TextScroller extends JScrollPane {
	private final JTextArea textArea = new JTextArea();

	public TextScroller(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setViewportView(this.textArea);
	}

	public String getText() {
		return this.textArea.getText();
	}

	public void setText(String textArea) {
		this.textArea.setText(textArea);
	}

	public void addLine(String str) {
		this.textArea.setText(this.textArea.getText() + str + "\n");
	}

	public Stream<String> lines() {
		return Arrays.stream(Strings.splitLines(this.getText()));
	}
}
