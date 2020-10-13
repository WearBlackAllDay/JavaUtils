package swing.components;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectionBox<E> extends JComboBox<String> {

    private final StringMapper<E> mapper;
    private final Object[] elements;

    public SelectionBox(E... elements) {
        this(Object::toString, Arrays.asList(elements));
    }

    public SelectionBox(Stream<E> elements) {
        this(Object::toString, elements.collect(Collectors.toList()));
    }

    public SelectionBox(Collection<E> elements) {
        this(Object::toString, elements);
    }

    public SelectionBox(StringMapper<E> mapper, E... elements) {
        this(mapper, Arrays.asList(elements));
    }

    public SelectionBox(StringMapper<E> mapper, Stream<E> elements) {
        this(mapper, elements.collect(Collectors.toList()));
    }

    public SelectionBox(StringMapper<E> mapper, Collection<E> elements) {
        super(elements.stream().map(mapper::map).toArray(String[]::new));

        this.mapper = mapper;
        this.elements = new Object[elements.size()];
        int i = 0;

        for(Iterator<E> iterator = elements.iterator(); iterator.hasNext(); i++) {
            this.elements[i] = iterator.next();
        }
    }

    public E getElement(int index) {
        return (E)this.elements[index];
    }

    public E getSelected() {
        return this.getElement(this.getSelectedIndex());
    }

    public String getSelectedString() {
        return this.mapper.map(this.getSelected());
    }

    public E[] getElements() {
        return (E[]) this.elements;
    }

    public interface StringMapper<E> {
        String map(E element);
    }
}