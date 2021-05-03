package wearblackallday.swing.components;

import wearblackallday.util.TriConsumer;

import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomMenuBar extends JMenuBar {
	public CustomMenuBar addMenu(String title, Consumer<Menu> buildCode) {
		Menu newMenu = new Menu(title);
		buildCode.accept(newMenu);
		this.add(newMenu);
		return this;
	}

	public static class Menu extends JMenu {
		public Menu(String title) {
			super(title);
		}

		public Menu withItem(String title, BiConsumer<Menu, JMenuItem> componentCode) {
			JMenuItem menuItem = new JMenuItem(title);
			componentCode.accept(this, menuItem);
			this.add(menuItem);
			return this;
		}

		public Menu withItem(String title, TriConsumer<Menu, JMenuItem, ActionEvent> action) {
			return this.withItem(title, (menu, item) -> item.addActionListener(e -> action.accept(menu, item, e)));
		}

		public Menu withCheckBox(String title, BiConsumer<Menu, JCheckBoxMenuItem> componentCode) {
			JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(title);
			componentCode.accept(this, checkBox);
			this.add(checkBox);
			return this;
		}

		public Menu withCheckBox(String title, TriConsumer<Menu, JCheckBoxMenuItem, ActionEvent> action) {
			return this.withCheckBox(title, (menu, checkBox) -> checkBox.addActionListener(e -> action.accept(menu, checkBox, e)));
		}

		public Menu withRadioBox(String title, BiConsumer<Menu, JRadioButtonMenuItem> componentCode) {
			JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem(title);
			componentCode.accept(this, radioButton);
			this.add(radioButton);
			return this;
		}

		public Menu withRadioBox(String title, TriConsumer<Menu, JRadioButtonMenuItem, ActionEvent> action) {
			return this.withRadioBox(title, (menu, radioButton) -> radioButton.addActionListener(e -> action.accept(menu, radioButton, e)));
		}

		public Menu withListener(MenuListener menuListener) {
			this.addMenuListener(menuListener);
			return this;
		}

		public Menu subMenu(String title, Consumer<Menu> buildCode) {
			Menu subMenu = new Menu(title);
			buildCode.accept(subMenu);
			this.add(subMenu);
			return this;
		}
	}
}
