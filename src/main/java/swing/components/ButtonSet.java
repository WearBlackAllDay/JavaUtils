package swing.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ButtonSet<B extends AbstractButton> implements Iterable<B> {

    private final B[] buttons;
    private final Stream<B> stream;

    @SuppressWarnings("unchecked")
    public ButtonSet(Supplier<B> buttonSupplier, String... titles) {
        this.buttons = (B[])new AbstractButton[titles.length];
        this.stream = Arrays.stream(this.buttons);
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

    public void addAll(Container container) {
        this.forEach(container::add);
    }

    public void addListeners(ActionListener... actionListeners) {
        if(actionListeners.length != this.buttons.length) System.err.println("mismatch in length");
        Iterator<ActionListener> iterator = Arrays.stream(actionListeners).iterator();
        this.forEach(abstractButton -> abstractButton.addActionListener(iterator.next()));
    }

    public B getButton(int index) {
        return this.buttons[index];
    }

    public B[] getButtons() {
        return this.buttons;
    }

    @Override
    public void forEach(Consumer<? super B> action) {
        this.stream.forEach(action);
    }

    @Override
    public Spliterator<B> spliterator() {
        return this.stream.spliterator();
    }

    @Override
    public Iterator<B> iterator() {
        return this.stream.iterator();
    }
}
