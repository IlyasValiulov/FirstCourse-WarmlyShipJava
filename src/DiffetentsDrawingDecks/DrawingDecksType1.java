package DiffetentsDrawingDecks;

import java.awt.*;

public class DrawingDecksType1 implements IDifferentDecks {
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
        g.drawRect(x, y, width, height);
        g.setColor(bodyColor);
    }
}
