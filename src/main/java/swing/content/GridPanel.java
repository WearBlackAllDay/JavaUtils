package swing.content;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.function.Supplier;

public class GridPanel<C extends Component> extends JPanel {

    private final int xSize, ySize;
    private final Component[][] components;


    public GridPanel(int xSize, int ySize, Supplier<Component> componentSupplier) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.components = new Component[this.xSize][this.ySize];

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        for (int x = 0; x < xSize; x++) {
            gbc.gridx = x;
            for (int y = 0; y < ySize; y++) {
                gbc.gridy = y;
                this.components[x][y] = componentSupplier.get();
                this.add(this.components[x][y], gbc);
            }
        }
    }

    public C componentAt(int x, int y) {
        return (C)this.components[x][y];
    }

    public C[][] allComponents() {
        return (C[][]) this.components;
    }

    public int xSize() {
        return this.xSize;
    }

    public int ySize() {
        return this.ySize;
    }
}
