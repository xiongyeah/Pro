package view;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,单元格类
 * but this class only cares how to draw Cells on ChessboardComponent只绘制单元格
 */

public class CellComponent extends JPanel {
    private Color background;

    public CellComponent(Color background, Point location, int size) {
        setLayout(null);
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    }
}
