package DrawingShip;
import DiffetentsDrawingDecks.IDifferentDecks;
import Entities.EntityWarmlyShip;

import java.awt.*;

public class DrawingWarmlyShip extends DrawingShip {
    public DrawingWarmlyShip(int speed, double weight, Color bodycolor, Color additionalcolor, boolean sheeppipes, boolean fueltank) {
        EntityShip = new EntityWarmlyShip(speed, weight, bodycolor, additionalcolor, sheeppipes, fueltank);
    }
    //добавил
    public DrawingWarmlyShip(EntityWarmlyShip entity, IDifferentDecks decks) {
        EntityShip = entity;
        drawingDecks = decks;
    }
    @Override
    public void DrawTransport(Graphics2D g) {
        if (EntityShip == null || !(EntityShip instanceof EntityWarmlyShip warmlyship) || _StartPosX == null || _StartPosY == null)
            return;
        int y = _StartPosY;
        if (warmlyship.ShipPipes) {
            //трубы
            g.setColor(warmlyship.getAdditionalColor());
            g.fillRect(_StartPosX + 70, _StartPosY, 12, 30);
            g.fillRect(_StartPosX + 90, _StartPosY, 12, 30);
            _StartPosY += 30;
        }
        super.DrawTransport(g);
        int count_decks = 0;
        if (drawingDecks != null && drawingDecks.getNumberOfDecks() != null) {
            switch (drawingDecks.getNumberOfDecks()) {
                case OneDeck:
                    count_decks = 1;
                    break;
                case TwoDecks:
                    count_decks = 2;
                    break;
                case ThreeDecks:
                    count_decks = 3;
                    break;
            }
        }
        if (warmlyship.getFuelTank()) {
            g.setColor(warmlyship.getAdditionalColor());
            g.fillRect(_StartPosX + 40, _StartPosY + count_decks * 15 + 30, 70, 10);
        }
        if (warmlyship.ShipPipes) {
            _StartPosY -= 30;
            drawingShipHeight += 30;
        }
    }
}
