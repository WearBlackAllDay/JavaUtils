package swing.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonSet<B extends AbstractButton> {

    private final AbstractButton[] buttons;

    public ButtonSet(Supplier<B> buttonSupplier, String... titles) {
        this.buttons = new AbstractButton[titles.length];
        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = buttonSupplier.get();
            this.buttons[i].setText(titles[i]);
        }
    }

    public ButtonSet(Supplier<B> buttonSupplier, Dimension dimension, String... titles) {
        this(buttonSupplier, titles);
        this.forEach(abstractButton -> abstractButton.setPreferredSize(dimension));
    }

    public ButtonSet(Supplier<B> buttonSupplier, int width, int height, String... titles) {
        this(buttonSupplier, new Dimension(width, height), titles);
    }

    public void forEach(Consumer<B> consumer) {
        Arrays.stream(this.buttons).forEach(abstractButton -> consumer.accept((B)abstractButton));
    }

    public void addAll(Container container) {
        this.forEach(container::add);
    }

    public void addListeners(ActionListener... actionListeners) {
        Iterator<ActionListener> iterator = Arrays.stream(actionListeners).iterator();
        this.forEach(b -> b.addActionListener(iterator.next()));
    }

    public B buttonAt(int index) {
        return (B)this.buttons[index];
    }
}
