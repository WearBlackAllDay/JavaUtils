package wearblackallday.javautils.swing;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

	public static <C extends Container> C addAll(C container, JComponent... components) {
		for(JComponent c : components) {
			container.add(c);
		}
		return container;
	}

	public static <C extends Component> C createComponent(Supplier<C> supplier, Consumer<C> code) {
		C component = supplier.get();
		code.accept(component);
		return component;
	}

	public static <C extends Component> C getClassChild(Container container, Class<C> clazz) {
		for(Component component : container.getComponents()) {
			if(clazz.isInstance(component)) {
				return (C)component;
			}
		}
		throw new NoSuchElementException(container.getName() + "\sdoes not hold Object of Type\s" + clazz.getSimpleName());
	}

	public static void deepSetEnabled(Component component, boolean enabled) {
		component.setEnabled(enabled);

		if(component instanceof Container parent) {
			for(Component child : parent.getComponents()) {
				deepSetEnabled(child, enabled);
			}
		}
	}

	public static Icon createColorIcon(int width, int height, Color color) {
		return createIcon(width, height, graphics -> {
			graphics.setColor(color);
			graphics.fillRect(0, 0, width, height);
		});
	}

	public static Icon createCrossIcon(int size, int thickness, Color color) {
		return createIcon(size, size, graphics -> {
			graphics.setColor(color);
			graphics.setStroke(new BasicStroke(thickness));
			graphics.drawLine(0, 0, size, size);
			graphics.drawLine(size, 0, 0, size);
		});
	}

	public static Icon createIcon(int width, int height, Consumer<Graphics2D> graphicsCode) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = img.createGraphics();
		graphicsCode.accept(graphics);
		graphics.dispose();

		return new ImageIcon(img);
	}
}
