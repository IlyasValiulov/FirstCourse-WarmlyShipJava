package DrawingShip;

import javax.swing.*;
import java.awt.*;

public class CanvasWarmlyShip extends JComponent {
    public DrawingShip _drawingShip;
    public CanvasWarmlyShip(){}
    public void paintComponent(Graphics g) {
        if (_drawingShip == null) {
            return;
        }
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        _drawingShip.DrawTransport(g2d);
        super.repaint();
    }
}

