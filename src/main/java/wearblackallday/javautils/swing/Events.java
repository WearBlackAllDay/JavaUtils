package wearblackallday.javautils.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.*;
import java.util.function.Consumer;

public final class Events {
	private Events() {
	}

	private enum EventType {
		MOUSE_CLICKED, MOUSE_PRESSED, MOUSE_RELEASED, MOUSE_ENTERED, MOUSE_EXITED, MOUSE_DRAGGED, MOUSE_MOVED,
		KEY_TYPED, KEY_PRESSED, KEY_RELEASED,
		MENU_SELECTED, MENU_DESELECTED, MENU_CANCELED,
		DOCUMENT_INSERTED, DOCUMENT_REMOVED, DOCUMENT_CHANGED,
		WINDOW_OPENED, WINDOW_CLOSING, WINDOW_CLOSED, WINDOW_ICONIFIED, WINDOW_DE_ICONIFIED, WINDOW_ACTIVATED, WINDOW_DEACTIVATED,
		COMPONENT_RESIZED, COMPONENT_MOVED, COMPONENT_SHOWN, COMPONENT_HIDDEN
	}
	public record Mouse(EventType type, Consumer<MouseEvent> event) implements MouseListener, MouseMotionListener {
		
		public static Mouse onClicked(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_CLICKED, event);
		}

		public static Mouse onPressed(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_PRESSED, event);
		}

		public static Mouse onReleased(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_RELEASED, event);
		}

		public static Mouse onEntered(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_ENTERED, event);
		}

		public static Mouse onExited(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_EXITED, event);
		}

		public static Mouse onDragged(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_DRAGGED, event);
		}

		public static Mouse onMoved(Consumer<MouseEvent> event) {
			return new Mouse(EventType.MOUSE_MOVED, event);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(this.type == EventType.MOUSE_CLICKED) this.event.accept(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(this.type == EventType.MOUSE_PRESSED) this.event.accept(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(this.type == EventType.MOUSE_RELEASED) this.event.accept(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if(this.type == EventType.MOUSE_ENTERED) this.event.accept(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(this.type == EventType.MOUSE_EXITED) this.event.accept(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(this.type == EventType.MOUSE_DRAGGED) this.event.accept(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(this.type == EventType.MOUSE_MOVED) this.event.accept(e);
		}
	}

	public record Keyboard(EventType type, Consumer<KeyEvent> event) implements KeyListener {
		
		public static Keyboard onTyped(Consumer<KeyEvent> event) {
			return new Keyboard(EventType.KEY_TYPED, event);
		}

		public static Keyboard onPressed(Consumer<KeyEvent> event) {
			return new Keyboard(EventType.KEY_PRESSED, event);
		}

		public static Keyboard onReleased(Consumer<KeyEvent> event) {
			return new Keyboard(EventType.KEY_RELEASED, event);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(this.type == EventType.KEY_TYPED) this.event.accept(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(this.type == EventType.KEY_PRESSED) this.event.accept(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(this.type == EventType.KEY_RELEASED) this.event.accept(e);
		}
	}

	public record Menu(EventType type, Consumer<MenuEvent> event) implements MenuListener {
		
		public static Menu onSelected(Consumer<MenuEvent> event) {
			return new Menu(EventType.MENU_SELECTED, event);
		}

		public static Menu onDeselected(Consumer<MenuEvent> event) {
			return new Menu(EventType.MENU_DESELECTED, event);
		}

		public static Menu onCanceled(Consumer<MenuEvent> event) {
			return new Menu(EventType.MENU_CANCELED, event);
		}

		@Override
		public void menuSelected(MenuEvent e) {
			if(this.type == EventType.MENU_SELECTED) this.event.accept(e);
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			if(this.type == EventType.MENU_DESELECTED) this.event.accept(e);
		}

		@Override
		public void menuCanceled(MenuEvent e) {
			if(this.type == EventType.MENU_CANCELED) this.event.accept(e);
		}
	}

	public record Document(EventType type, Consumer<DocumentEvent> event) implements DocumentListener {
		
		public static Document onInserted(Consumer<DocumentEvent> event) {
			return new Document(EventType.DOCUMENT_INSERTED, event);
		}

		public static Document onRemoved(Consumer<DocumentEvent> event) {
			return new Document(EventType.DOCUMENT_REMOVED, event);
		}

		public static Document onChanged(Consumer<DocumentEvent> event) {
			return new Document(EventType.DOCUMENT_CHANGED, event);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if(this.type == EventType.DOCUMENT_INSERTED) this.event.accept(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(this.type == EventType.DOCUMENT_REMOVED) this.event.accept(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(this.type == EventType.DOCUMENT_CHANGED) this.event.accept(e);
		}
	}

	public record Window(EventType type, Consumer<WindowEvent> event) implements WindowListener {
		
		public static Window onOpened(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_OPENED, event);
		}

		public static Window onClosing(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_CLOSING, event);
		}

		public static Window onClosed(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_CLOSED, event);
		}

		public static Window onIconified(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_ICONIFIED, event);
		}

		public static Window onDeIconified(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_DE_ICONIFIED, event);
		}

		public static Window onActivated(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_ACTIVATED, event);
		}

		public static Window onDeactivated(Consumer<WindowEvent> event) {
			return new Window(EventType.WINDOW_DEACTIVATED, event);
		}

		@Override
		public void windowOpened(WindowEvent e) {
			if(this.type == EventType.WINDOW_OPENED) this.event.accept(e);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			if(this.type == EventType.WINDOW_CLOSING) this.event.accept(e);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			if(this.type == EventType.WINDOW_CLOSED) this.event.accept(e);
		}

		@Override
		public void windowIconified(WindowEvent e) {
			if(this.type == EventType.WINDOW_ICONIFIED) this.event.accept(e);
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			if(this.type == EventType.WINDOW_DE_ICONIFIED) this.event.accept(e);
		}

		@Override
		public void windowActivated(WindowEvent e) {
			if(this.type == EventType.WINDOW_ACTIVATED) this.event.accept(e);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			if(this.type == EventType.WINDOW_DEACTIVATED) this.event.accept(e);
		}
	}

	public record Component(EventType type, Consumer<ComponentEvent> event) implements ComponentListener {
		
		public static Component onResized(Consumer<ComponentEvent> event) {
			return new Component(EventType.COMPONENT_RESIZED, event);
		}

		public static Component onMoved(Consumer<ComponentEvent> event) {
			return new Component(EventType.COMPONENT_MOVED, event);
		}

		public static Component onShown(Consumer<ComponentEvent> event) {
			return new Component(EventType.COMPONENT_SHOWN, event);
		}

		public static Component onHidden(Consumer<ComponentEvent> event) {
			return new Component(EventType.COMPONENT_HIDDEN, event);
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if(this.type == EventType.COMPONENT_RESIZED) this.event.accept(e);
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			if(this.type == EventType.COMPONENT_MOVED) this.event.accept(e);
		}

		@Override
		public void componentShown(ComponentEvent e) {
			if(this.type == EventType.COMPONENT_SHOWN) this.event.accept(e);
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			if(this.type == EventType.COMPONENT_HIDDEN) this.event.accept(e);
		}
	}
}
