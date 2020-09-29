package swing.content;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.function.Supplier;

public class GridPanel<C extends Component> extends JPanel {

    private final Class<C> componentClass;
    private final C[][] components;
    private final int x, y;

    public GridPanel(int xSize, int ySize, Supplier<Component> componentSupplier) {
        @SuppressWarnings("unchecked")
        Class<C> componentSupplierClass = (Class<C>) componentSupplier.get().getClass();
        this.componentClass = componentSupplierClass;
        @SuppressWarnings("unchecked")
        final C[][] instance = (C[][]) Array.newInstance(this.componentClass, xSize, ySize);
        this.components = instance;
        this.x = xSize;
        this.y = ySize;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int x = 0; x < xSize; x++) {
            gbc.gridx = x;
            for (int y = 0; y < ySize; y++) {
                gbc.gridy = y;
                @SuppressWarnings("unchecked")
                final C c = (C) componentSupplier.get();
                this.components[x][y] = c;
                this.add(this.components[x][y], gbc);
            }
        }
    }

    public C componentAt(int x, int y) {
        return this.components[x][y];
    }

    public C[] getRow(int row) {
        C[] array = this.array(this.x);
        for (int i = 0; i < array.length; i++) {
            array[i] = this.components[i][row];
        }
        return array;
    }

    public C[] getColumn(int column) {
        C[] array = this.array(this.y);
        System.arraycopy(this.components[column], 0, array, 0, array.length);
        return array;
    }

    public C[][] allComponents() {
        return this.components;
    }

    private C[] array(int size) {
        @SuppressWarnings("unchecked")
        final C[] array = (C[]) Array.newInstance(this.componentClass, size);
        return array;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
