package wearblackallday.swing.components.builder;

import wearblackallday.swing.SwingUtils;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Deprecated
public class MenuBuilder extends JMenuBar {

	private final Map<Key<?>, JMenuItem> items = new HashMap<>();

	public MenuBuilder(Menu... menus) {
		SwingUtils.addSet(this, menus);
	}

	@SuppressWarnings("unchecked")
	public <C extends JMenuItem> C customItem(Key<C> key, Supplier<C> menuItem) {
		this.items.put(key, menuItem.get());
		return (C)this.items.get(key);
	}

	@SuppressWarnings("unchecked")
	public <C extends JMenuItem> C getItem(Key<C> key) {
		return (C)this.items.get(key);
	}

	public static class Item extends JMenuItem {
		public Item(String title, BiConsumer<Item, ActionEvent> actionListener) {
			this.setText(title);
			this.addActionListener(e -> actionListener.accept(this, e));
		}

		public Item addIcon(Icon icon) {
			this.setIcon(icon);
			return this;
		}

		public Item run(Consumer<JMenuItem> consumer) {
			consumer.accept(this);
			return this;
		}
	}

	public static class CheckBox extends JCheckBoxMenuItem {
		public CheckBox(String title, BiConsumer<CheckBox, ActionEvent> actionListener) {
			this.setText(title);
			this.addActionListener(e -> actionListener.accept(this, e));
		}

		public CheckBox addIcon(Icon icon) {
			this.setIcon(icon);
			return this;
		}

		public CheckBox run(Consumer<JMenuItem> consumer) {
			consumer.accept(this);
			return this;
		}
	}

	public static class RadioBox extends JRadioButtonMenuItem {
		public RadioBox(String title, BiConsumer<RadioBox, ActionEvent> actionListener) {
			this.setText(title);
			this.addActionListener(e -> actionListener.accept(this, e));
		}

		public RadioBox addIcon(Icon icon) {
			this.setIcon(icon);
			return this;
		}

		public RadioBox run(Consumer<JMenuItem> consumer) {
			consumer.accept(this);
			return this;
		}
	}

	public static class Menu extends JMenu {
		public Menu(String title, JMenuItem... items) {
			this.setText(title);
			SwingUtils.addSet(this, items);
		}

		public Menu addIcon(Icon icon) {
			this.setIcon(icon);
			return this;
		}

		public Menu addSelectedListener(BiConsumer<Menu, MenuEvent> actionListener) {
			this.addMenuListener(new MenuListener() {
				@Override
				public void menuSelected(MenuEvent e) {
					actionListener.accept(Menu.this, e);
				}

				@Override
				public void menuDeselected(MenuEvent e) {
				}

				@Override
				public void menuCanceled(MenuEvent e) {
				}
			});
			return this;
		}

		public Menu run(Consumer<JMenuItem> consumer) {
			consumer.accept(this);
			return this;
		}
	}

	public static class Key<T> {
	}
}
