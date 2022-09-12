package wearblackallday.javautils.swing.components;

import wearblackallday.javautils.data.ArrayUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class SelectionBox<E> extends JComboBox<String> {
	private final Function<E, String> stringMapper;
	private final E[] elements;

	public SelectionBox(E... elements) {
		this(Object::toString, Arrays.asList(elements));
	}

	public SelectionBox(Stream<E> elements) {
		this(Object::toString, elements.toList());
	}

	public SelectionBox(Collection<E> elements) {
		this(Object::toString, elements);
	}

	public SelectionBox(Function<E, String> stringMapper, E... elements) {
		this(stringMapper, Arrays.asList(elements));
	}

	public SelectionBox(Function<E, String> stringMapper, Stream<E> elements) {
		this(stringMapper, elements.toList());
	}

	public SelectionBox(Function<E, String> stringMapper, Collection<E> elements) {
		super(elements.stream().map(stringMapper).toArray(String[]::new));
		this.stringMapper = stringMapper;
		this.elements = (E[])new Object[elements.size()];
		int i = 0;
		for(E element : elements) {
			this.elements[i++] = element;
		}
	}

	public E getElement(int index) {
		return this.elements[index];
	}

	public E getSelected() {
		return this.getElement(this.getSelectedIndex());
	}
	
	public String getSelectedString() {
		return this.stringMapper.apply(this.getSelected());
	}

	public String[] getStrings() {
		return ArrayUtils.map(this.stringMapper, this.elements);
	}

	public boolean selectIfContains(E element) {
		return this.selectIfContains(element, Object::equals);
	}

	public boolean selectIfContains(E element, BiPredicate<E, E> equals) {
		for(int i = 0; i < this.elements.length; i++) {
			if(equals.test(this.getElement(i), element)) {
				this.setSelectedIndex(i);
				return true;
			}
		}
		return false;
	}
}