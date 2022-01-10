package wearblackallday.javautils.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.*;
import java.util.function.Consumer;

public final class Events {
	private Events() {}

	public static final class Mouse implements MouseListener, MouseMotionListener {
		private final Type type;
		private final Consumer<MouseEvent> event;

		public Mouse(Type type, Consumer<MouseEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Mouse onClicked(Consumer<MouseEvent> event) {
			return new Mouse(Type.CLICKED, event);
		}

		public static Mouse onPressed(Consumer<MouseEvent> event) {
			return new Mouse(Type.PRESSED, event);
		}

		public static Mouse onReleased(Consumer<MouseEvent> event) {
			return new Mouse(Type.RELEASED, event);
		}

		public static Mouse onEntered(Consumer<MouseEvent> event) {
			return new Mouse(Type.ENTERED, event);
		}

		public static Mouse onExited(Consumer<MouseEvent> event) {
			return new Mouse(Type.EXITED, event);
		}

		public static Mouse onDragged(Consumer<MouseEvent> event) {
			return new Mouse(Type.DRAGGED, event);
		}

		public static Mouse onMoved(Consumer<MouseEvent> event) {
			return new Mouse(Type.MOVED, event);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(this.type == Type.CLICKED) this.event.accept(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(this.type == Type.PRESSED) this.event.accept(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(this.type == Type.RELEASED) this.event.accept(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if(this.type == Type.ENTERED) this.event.accept(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(this.type == Type.EXITED) this.event.accept(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(this.type == Type.DRAGGED) this.event.accept(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(this.type == Type.MOVED) this.event.accept(e);
		}

		public enum Type {
			CLICKED, PRESSED, RELEASED, ENTERED, EXITED, DRAGGED, MOVED
		}
	}

	public static final class Keyboard implements KeyListener {
		private final Type type;
		private final Consumer<KeyEvent> event;

		public Keyboard(Type type, Consumer<KeyEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Keyboard onTyped(Consumer<KeyEvent> event) {
			return new Keyboard(Type.TYPED, event);
		}

		public static Keyboard onPressed(Consumer<KeyEvent> event) {
			return new Keyboard(Type.PRESSED, event);
		}

		public static Keyboard onReleased(Consumer<KeyEvent> event) {
			return new Keyboard(Type.RELEASED, event);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(this.type == Type.TYPED) this.event.accept(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(this.type == Type.PRESSED) this.event.accept(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(this.type == Type.RELEASED) this.event.accept(e);
		}

		public enum Type {
			TYPED, PRESSED, RELEASED
		}
	}

	public static final class Menu implements MenuListener {
		private final Type type;
		private final Consumer<MenuEvent> event;

		public Menu(Type type, Consumer<MenuEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Menu onSelected(Consumer<MenuEvent> event) {
			return new Menu(Type.SELECTED, event);
		}

		public static Menu onDeselected(Consumer<MenuEvent> event) {
			return new Menu(Type.DESELECTED, event);
		}

		public static Menu onCanceled(Consumer<MenuEvent> event) {
			return new Menu(Type.CANCELED, event);
		}

		@Override
		public void menuSelected(MenuEvent e) {
			if(this.type == Type.SELECTED) this.event.accept(e);
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			if(this.type == Type.DESELECTED) this.event.accept(e);
		}

		@Override
		public void menuCanceled(MenuEvent e) {
			if(this.type == Type.CANCELED) this.event.accept(e);
		}

		public enum Type {
			SELECTED, DESELECTED, CANCELED
		}
	}

	public static final class Document implements DocumentListener {
		private final Type type;
		private final Consumer<DocumentEvent> event;

		public Document(Type type, Consumer<DocumentEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Document onInserted(Consumer<DocumentEvent> event) {
			return new Document(Type.INSERTED, event);
		}

		public static Document onRemoved(Consumer<DocumentEvent> event) {
			return new Document(Type.REMOVED, event);
		}

		public static Document onChanged(Consumer<DocumentEvent> event) {
			return new Document(Type.CHANGED, event);
		}


		@Override
		public void insertUpdate(DocumentEvent e) {
			if(this.type == Type.INSERTED) this.event.accept(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(this.type == Type.REMOVED) this.event.accept(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(this.type == Type.CHANGED) this.event.accept(e);
		}

		public enum Type {
			INSERTED, REMOVED, CHANGED
		}
	}

	public static final class Window implements WindowListener {
		private final Type type;
		private final Consumer<WindowEvent> event;

		public Window(Type type, Consumer<WindowEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Window onOpened(Consumer<WindowEvent> event) {
			return new Window(Type.OPENED, event);
		}

		public static Window onClosing(Consumer<WindowEvent> event) {
			return new Window(Type.CLOSING, event);
		}

		public static Window onClosed(Consumer<WindowEvent> event) {
			return new Window(Type.CLOSED, event);
		}

		public static Window onIconified(Consumer<WindowEvent> event) {
			return new Window(Type.ICONIFIED, event);
		}

		public static Window onDeIconified(Consumer<WindowEvent> event) {
			return new Window(Type.DE_ICONIFIED, event);
		}

		public static Window onActivated(Consumer<WindowEvent> event) {
			return new Window(Type.ACTIVATED, event);
		}

		public static Window onDeactivated(Consumer<WindowEvent> event) {
			return new Window(Type.DEACTIVATED, event);
		}

		@Override
		public void windowOpened(WindowEvent e) {
			if(this.type == Type.OPENED) this.event.accept(e);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			if(this.type == Type.CLOSING) this.event.accept(e);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			if(this.type == Type.CLOSED) this.event.accept(e);
		}

		@Override
		public void windowIconified(WindowEvent e) {
			if(this.type == Type.ICONIFIED) this.event.accept(e);
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			if(this.type == Type.DE_ICONIFIED) this.event.accept(e);
		}

		@Override
		public void windowActivated(WindowEvent e) {
			if(this.type == Type.ACTIVATED) this.event.accept(e);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			if(this.type == Type.DEACTIVATED) this.event.accept(e);
		}

		public enum Type {
			OPENED, CLOSING, CLOSED, ICONIFIED, DE_ICONIFIED, ACTIVATED, DEACTIVATED
		}
	}

	public static final class Component implements ComponentListener {
		private final Type type;
		private final Consumer<ComponentEvent> event;

		public Component(Type type, Consumer<ComponentEvent> event) {
			this.type = type;
			this.event = event;
		}

		public static Component onResized(Consumer<ComponentEvent> event) {
			return new Component(Type.RESIZED, event);
		}

		public static Component onMoved(Consumer<ComponentEvent> event) {
			return new Component(Type.MOVED, event);
		}

		public static Component onShown(Consumer<ComponentEvent> event) {
			return new Component(Type.SHOWN, event);
		}

		public static Component onHidden(Consumer<ComponentEvent> event) {
			return new Component(Type.HIDDEN, event);
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if(this.type == Type.RESIZED) this.event.accept(e);
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			if(this.type == Type.MOVED) this.event.accept(e);
		}

		@Override
		public void componentShown(ComponentEvent e) {
			if(this.type == Type.SHOWN) this.event.accept(e);
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			if(this.type == Type.HIDDEN) this.event.accept(e);
		}

		public enum Type {
			RESIZED, MOVED, SHOWN, HIDDEN
		}
	}
}
