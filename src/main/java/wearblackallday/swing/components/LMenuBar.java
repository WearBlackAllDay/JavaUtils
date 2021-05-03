package wearblackallday.swing.components;

import wearblackallday.util.TriConsumer;

import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LMenuBar extends JMenuBar {
	public LMenuBar addMenu(String title, Consumer<LMenu> buildCode) {
		LMenu newMenu = new LMenu(title);
		buildCode.accept(newMenu);
		this.add(newMenu);
		return this;
	}

	public static class LMenu extends JMenu {
		public LMenu(String title) {
			super(title);
		}

		public LMenu withItem(String title, BiConsumer<LMenu, JMenuItem> componentCode) {
			JMenuItem menuItem = new JMenuItem(title);
			componentCode.accept(this, menuItem);
			this.add(menuItem);
			return this;
		}

		public LMenu withItem(String title, TriConsumer<LMenu, JMenuItem, ActionEvent> action) {
			return this.withItem(title, (menu, item) -> item.addActionListener(e -> action.accept(menu, item, e)));
		}

		public LMenu withCheckBox(String title, BiConsumer<LMenu, JCheckBoxMenuItem> componentCode) {
			JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(title);
			componentCode.accept(this, checkBox);
			this.add(checkBox);
			return this;
		}

		public LMenu withCheckBox(String title, TriConsumer<LMenu, JCheckBoxMenuItem, ActionEvent> action) {
			return this.withCheckBox(title, (menu, checkBox) -> checkBox.addActionListener(e -> action.accept(menu, checkBox, e)));
		}

		public LMenu withRadioBox(String title, BiConsumer<LMenu, JRadioButtonMenuItem> componentCode) {
			JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem(title);
			componentCode.accept(this, radioButton);
			this.add(radioButton);
			return this;
		}

		public LMenu withRadioBox(String title, TriConsumer<LMenu, JRadioButtonMenuItem, ActionEvent> action) {
			return this.withRadioBox(title, (menu, radioButton) -> radioButton.addActionListener(e -> action.accept(menu, radioButton, e)));
		}

		public LMenu withListener(MenuListener menuListener) {
			this.addMenuListener(menuListener);
			return this;
		}

		public LMenu subMenu(String title, Consumer<LMenu> buildCode) {
			LMenu subMenu = new LMenu(title);
			buildCode.accept(subMenu);
			this.add(subMenu);
			return this;
		}
	}
}
