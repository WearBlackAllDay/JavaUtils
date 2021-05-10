package wearblackallday.swing;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SwingUtils {
	public static void setPrompt(JTextComponent jTextComponent, String text) {
		jTextComponent.setText(text);
		jTextComponent.setToolTipText(text);
		jTextComponent.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(jTextComponent.getText().equals(text)) jTextComponent.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(jTextComponent.getText().isEmpty()) jTextComponent.setText(text);
			}
		});
	}

	public static <C extends Container> C addSet(C container, JComponent... components) {
		for(JComponent c : components) {
			container.add(c);
		}
		return container;
	}
}
