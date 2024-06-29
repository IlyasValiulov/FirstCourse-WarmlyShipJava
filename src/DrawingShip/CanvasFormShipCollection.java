package DrawingShip;

import CollectionGenericObjects.AbstractCompany;

import javax.swing.*;
import java.awt.*;

public class CanvasFormShipCollection<T> extends JComponent
{
    public AbstractCompany company = null;
    public void SetCollectionToCanvas(AbstractCompany company) {
        this.company =  company;
    }
    public CanvasFormShipCollection(){};
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (company == null || company._collection == null) {
            return;
        }
        company.DrawBackgound(g);
        for (int i = 0; i < company._collection.getCount(); i++) {
            try {
                Graphics2D g2d = (Graphics2D) g;
                T obj = (T) company._collection.Get(i);
                if (obj instanceof DrawingShip) {
                    ((DrawingShip) obj).DrawTransport(g2d);
                }
            }
            catch (Exception ex) {}
        }
        super.repaint();
    }
}
