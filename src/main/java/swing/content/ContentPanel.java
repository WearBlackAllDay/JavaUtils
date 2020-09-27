package swing.content;

import javax.swing.*;
import java.awt.*;

public class ContentPanel {

    public static JPanel gridPanel(int xSize, int ySize, Component content) throws IllegalAccessException, InstantiationException {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int x = 0; x < xSize; x++) {
            gbc.gridx = x;
            for (int y = 0; y < ySize; y++) {
                gbc.gridy = y;
                jPanel.add(content.getClass().newInstance(), gbc);
            }
        }
        return jPanel;
    }
}
