package wearblackallday.swing;

import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

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

	public static void addSet(Container container, Component... components) {
		Arrays.stream(components).forEach(container::add);
	}
}
