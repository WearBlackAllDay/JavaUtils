package swing.components;

import swing.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CustomPanel extends JPanel {

    private final Map<String, JTextField> textFields = new HashMap<>();

    public CustomPanel(LayoutManager layoutManager) {
        this.setLayout(layoutManager);
    }

    public CustomPanel addTextField(String text, int width, int height, String id) {
        JTextField jTextField = new JTextField(text);
        SwingUtils.setPrompt(text, jTextField);
        jTextField.setPreferredSize(new Dimension(width, height));
        this.add(jTextField);
        this.textFields.put(id, jTextField);
        return this;
    }

    public String getText(String id) {
        return this.textFields.get(id).getText();
    }

    public CustomPanel addButton(String text, int width, int height, ActionListener actionListener) {
        JButton jButton = new JButton(text);
        jButton.setPreferredSize(new Dimension(width, height));
        jButton.addActionListener(actionListener);
        return this;
    }

    public <C extends Component> CustomPanel addComponent(Supplier<C> cSupplier) {
        this.add(cSupplier.get());
        return this;
    }
}
