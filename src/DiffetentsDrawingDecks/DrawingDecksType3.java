package DiffetentsDrawingDecks;

import java.awt.*;

public class DrawingDecksType3 implements IDifferentDecks{
    private NumberOfDecks numberOfDecks;
    @Override
    public void setNumberOfDecks(int numberofdeck) {
        for (NumberOfDecks numofenum : NumberOfDecks.values()) {
            if (numofenum.getNumdecks() == numberofdeck) {
                numberOfDecks = numofenum;
                return;
            }
        }
    }
    @Override
    public NumberOfDecks getNumberOfDecks() {
        return numberOfDecks;
    }
    @Override
    public void DrawDecks(Graphics2D g, int x, int y, int width, int height, Color bodyColor) {
        g.setColor(bodyColor);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(x+ 10, y+5, width - 20, height-5);
        g.setColor(bodyColor);
    }
}
