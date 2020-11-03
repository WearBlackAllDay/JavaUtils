package swing.components;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectionBox<E> extends JComboBox<String> {

    private final StringMapper<E> mapper;
    private final E[] elements;

    @SafeVarargs
    public SelectionBox(E... elements) {
        this(Object::toString, Arrays.asList(elements));
    }

    public SelectionBox(Stream<E> elements) {
        this(Object::toString, elements.collect(Collectors.toList()));
    }

    public SelectionBox(Collection<E> elements) {
        this(Object::toString, elements);
    }

    @SafeVarargs
    public SelectionBox(StringMapper<E> mapper, E... elements) {
        this(mapper, Arrays.asList(elements));
    }

    public SelectionBox(StringMapper<E> mapper, Stream<E> elements) {
        this(mapper, elements.collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    public SelectionBox(StringMapper<E> mapper, Collection<E> elements) {
        super(elements.stream().map(mapper::map).toArray(String[]::new));

        this.mapper = mapper;
        this.elements = (E[])new Object[elements.size()];
        int i = 0;

        for(Iterator<E> iterator = elements.iterator(); iterator.hasNext(); i++) {
            this.elements[i] = iterator.next();
        }
    }

    public E getElement(int index) {
        return this.elements[index];
    }

    public E getSelected() {
        return this.getElement(this.getSelectedIndex());
    }

    public String getSelectedString() {
        return this.mapper.map(this.getSelected());
    }

    public E[] getElements() {
        return this.elements;
    }

    public String[] getStrings() {
        return Arrays.stream(this.elements).map(this.mapper::map).toArray(String[]::new);
    }

    @FunctionalInterface
    private interface StringMapper<E> {
        String map(E element);
    }
}