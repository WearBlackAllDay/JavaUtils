package swing.components;

import swing.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuBuilder extends JMenuBar {

    private final Map<Key<?>, JMenuItem> items = new HashMap<>();

    public MenuBuilder addTab(String title, JMenuItem... items) {
        JMenu jMenu = new JMenu(title);
        SwingUtils.addSet(jMenu, items);
        this.add(jMenu);
        return this;
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
        public Item(String title, ActionListener actionListener) {
            this.setText(title);
            this.addActionListener(actionListener);
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

        public Menu run(Consumer<JMenuItem> consumer) {
            consumer.accept(this);
            return this;
        }
    }

    public static class Key<T> {
    }
}
