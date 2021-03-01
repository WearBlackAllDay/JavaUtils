package swing.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GridPanel<C extends Component> extends JPanel implements Iterable<C> {

    private final int rows, columns;
    private final C[] components;

    @SuppressWarnings("unchecked")
    public GridPanel(int rows, int columns, Supplier<C> componentSupplier) {
        this.rows = rows;
        this.columns = columns;
        this.components = (C[])new Component[this.rows * this.columns];

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        for (int row = 0; row < rows; row++) {
            gbc.gridy = row;
            for (int column = 0; column < columns; column++) {
                gbc.gridx = column;
                this.add(this.components[column * this.rows + row] = componentSupplier.get(), gbc);
            }
        }
    }

    public ArrayList<C> getNeighbors(int index) {
        return this.getNeighbors(index / this.rows,index % this.columns);
    }

    public ArrayList<C> getNeighbors(int row, int column) {
        ArrayList<C> neighbors = new ArrayList<>(8);
        if(this.inBounds(row, column)) {
            if(this.inBounds(row + 1, column)) neighbors.add(this.getComponent(row + 1, column));
            if(this.inBounds(row - 1, column)) neighbors.add(this.getComponent(row - 1, column));
            if(this.inBounds(row, column + 1)) neighbors.add(this.getComponent(row, column + 1));
            if(this.inBounds(row, column - 1)) neighbors.add(this.getComponent(row, column - 1));
            if(this.inBounds(row - 1, column + 1)) neighbors.add(this.getComponent(row - 1, column + 1));
            if(this.inBounds(row + 1, column - 1)) neighbors.add(this.getComponent(row + 1, column - 1));
            if(this.inBounds(row + 1, column + 1)) neighbors.add(this.getComponent(row + 1, column + 1));
            if(this.inBounds(row - 1, column - 1)) neighbors.add(this.getComponent(row - 1, column - 1));
        }
        return neighbors;
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
    }

    public boolean anyMatch(Predicate<C> predicate) {
        for (C c : this) {
            if (predicate.test(c)) return true;
        }
        return false;
    }

    public boolean allMatch(Predicate<C> predicate) {
        for (C c : this) {
            if (!predicate.test(c)) return false;
        }
        return true;
    }

    public boolean noneMatch(Predicate<C> predicate) {
        for (C c : this) {
            if (predicate.test(c)) return false;
        }
        return true;
    }

    public C getComponent(int index) {
        return this.components[index];
    }

    public C getComponent(int row, int column) {
        return this.components[column * this.rows + row];
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    @Override
    public void forEach(Consumer<? super C> action) {
        Arrays.stream(this.components).forEach(action);
    }

    @Override
    public Spliterator<C> spliterator() {
        return Arrays.stream(this.components).spliterator();
    }

    @Override
    public Iterator<C> iterator() {
        return Arrays.stream(this.components).iterator();
    }
}