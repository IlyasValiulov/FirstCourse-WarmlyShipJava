package DiffetentsDrawingDecks;

import java.awt.*;

public class DrawingDecksType2 implements IDifferentDecks {
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
        g.setStroke(new BasicStroke(3.0F));
        g.setColor(bodyColor);
        //верхняя часть полубы
        g.drawLine(x, y, x + width, y);
        //опорные балки
        int countBalks = 4;
        int wayBetweenBalks = width / countBalks;
        for (int i = 0; i <= countBalks; i++) {
            int nowX = x + wayBetweenBalks * i;
            g.drawLine(nowX, y, nowX, y + height);
        }
    }
}
