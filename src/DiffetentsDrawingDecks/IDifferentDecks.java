package DiffetentsDrawingDecks;

import java.awt.*;

public interface IDifferentDecks {
    void setNumberOfDecks(int numberofdeck);
    NumberOfDecks getNumberOfDecks();
    void DrawDecks(Graphics2D g, int x, int y, int width, int height, Color bodyColor);
}
