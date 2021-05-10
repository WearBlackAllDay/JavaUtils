package wearblackallday.swing.components;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GridPanel<C extends JComponent> extends JPanel implements Iterable<C> {
	private final int gridWidth, gridHeight;
	private final C[] components;

	@SuppressWarnings("unchecked")
	public GridPanel(int width, int height, Supplier<C> componentSupplier) {
		super(new GridBagLayout());
		this.gridWidth = width;
		this.gridHeight = height;
		this.components = (C[])new JComponent[width * height];

		GridBagConstraints gbc = new GridBagConstraints();
		for(int x = 0; x < width; x++) {
			gbc.gridx = x;
			for(int y = 0; y < height; y++) {
				gbc.gridy = y;
					this.add(this.components[this.toIndex(x, y)] = componentSupplier.get(), gbc);
			}
		}
	}

	public List<C> getNeighbors(C component) {
		return this.getNeighbors(this.indexOf(component));
	}

	public List<C> getNeighbors(int index) {
		return this.getNeighbors(index % this.gridWidth, index / this.gridWidth);
	}

	public List<C> getNeighbors(int x, int y) {
		List<C> neighbors = new ArrayList<>(8);
		if(this.inBounds(x, y)) {
			if(this.inBounds(x + 1, y)) neighbors.add(this.getComponent(x + 1, y));
			if(this.inBounds(x - 1, y)) neighbors.add(this.getComponent(x - 1, y));
			if(this.inBounds(x, y + 1)) neighbors.add(this.getComponent(x, y + 1));
			if(this.inBounds(x, y - 1)) neighbors.add(this.getComponent(x, y - 1));
			if(this.inBounds(x - 1, y + 1)) neighbors.add(this.getComponent(x - 1, y + 1));
			if(this.inBounds(x + 1, y - 1)) neighbors.add(this.getComponent(x + 1, y - 1));
			if(this.inBounds(x + 1, y + 1)) neighbors.add(this.getComponent(x + 1, y + 1));
			if(this.inBounds(x - 1, y - 1)) neighbors.add(this.getComponent(x - 1, y - 1));
		}
		return neighbors;
	}

	private int toIndex(int x, int y) {
		return x + this.gridWidth * y;
	}

	private boolean inBounds(int x, int y) {
		return x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight;
	}

	public boolean anyMatch(Predicate<C> predicate) {
		for(C c : this.components) {
			if(predicate.test(c)) return true;
		}
		return false;
	}

	public boolean allMatch(Predicate<C> predicate) {
		for(C c : this.components) {
			if(!predicate.test(c)) return false;
		}
		return true;
	}

	public boolean noneMatch(Predicate<C> predicate) {
		for(C c : this.components) {
			if(predicate.test(c)) return false;
		}
		return true;
	}

	public int indexOf(C component) {
		for(int i = 0; i < this.components.length; i++) {
			if(this.components[i] == component) return i;
		}
		throw new NoSuchElementException("Component does not exist");
	}

	public C getComponent(int index) {
		return this.components[index];
	}

	public C getComponent(int x, int y) {
		return this.components[this.toIndex(x, y)];
	}

	public int getGridWidth() {
		return this.gridWidth;
	}

	public int getGridHeight() {
		return this.gridHeight;
	}

	public int getCount() {
		return this.components.length;
	}

	public Stream<C> stream() {
		return Arrays.stream(this.components);
	}

	@Override
	public void forEach(Consumer<? super C> action) {
		for(C c : this.components) {
			action.accept(c);
		}
	}

	@Override
	public Spliterator<C> spliterator() {
		return this.stream().spliterator();
	}

	@Override
	public Iterator<C> iterator() {
		return new ArrayIterator();
	}

	private class ArrayIterator implements Iterator<C> {
		private int current;

		@Override
		public boolean hasNext() {
			return this.current < GridPanel.this.components.length;
		}

		@Override
		public C next() {
			return GridPanel.this.components[this.current++];
		}
	}
}