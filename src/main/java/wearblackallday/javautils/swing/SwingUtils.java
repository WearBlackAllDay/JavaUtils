package wearblackallday.javautils.swing;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;

public final class SwingUtils {
	private SwingUtils() {}

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

	public static <C extends Component> C getClassChild(Container container, Class<C> clazz) {
		for(Component component : container.getComponents()) {
			if(clazz.isInstance(component)) {
				return (C)component;
			}
		}
		throw new NoSuchElementException(container.getName() + "does not hold Object of Type " + clazz.getSimpleName());
	}

	public static ImageIcon colorIcon(Color c, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		return new ImageIcon(img);
	}
}
