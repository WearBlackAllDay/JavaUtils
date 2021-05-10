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

	public int getGridWidth() {
		return this.gridWidth;
	}

	public int getGridHeight() {
		return this.gridHeight;
	}

	public int getCount() {
		return this.components.length;
	}

	public C getComponent(int index) {
		return this.components[index];
	}

	public C getComponent(int x, int y) {
		return this.components[this.toIndex(x, y)];
	}

	public int indexOf(C component) {
		for(int i = 0; i < this.components.length; i++) {
			if(this.components[i] == component) return i;
		}
		return -1;
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

	public GridPanel<C> subGrid(int endX, int endY) {
		return this.subGrid(0, endX, 0, endY);
	}

	@SuppressWarnings("unchecked")
	public GridPanel<C> subGrid(int startX, int endX, int startY, int endY) {
		C[] subArray = (C[])new JComponent[(endX - startX) * (endY - startY)];
		int i = 0;
		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {
				subArray[i++] = this.components[this.toIndex(x, y)];
			}
		}
		Iterator<C> iterator = Arrays.stream(subArray).iterator();
		return new GridPanel<>(endX - startX, endY - startY, iterator::next);
	}

	public Stream<C> stream() {
		return Arrays.stream(this.components);
	}

	private int toIndex(int x, int y) {
		return x + this.gridWidth * y;
	}

	private boolean inBounds(int x, int y) {
		return x >= 0 && x < this.gridWidth && y >= 0 && y < this.gridHeight;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder()
			.append("GridPanel {\n");
		for(int i = 0; i < this.components.length; i++) {
			stringBuilder
				.append(i)
				.append(":\n")
				.append(this.components[i].toString())
				.append("\n");
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
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

	protected class ArrayIterator implements Iterator<C> {
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