package DrawingShip;
import DiffetentsDrawingDecks.DrawingDecksType2;
import DiffetentsDrawingDecks.DrawingDecksType3;
import DiffetentsDrawingDecks.IDifferentDecks;
import Entities.EntityShip;

import DiffetentsDrawingDecks.DrawingDecksType1;

import javax.swing.*;
import java.awt.*;

public class DrawingShip extends JPanel {
    public Entities.EntityShip EntityShip;
    public IDifferentDecks drawingDecks;
    private Integer picture_width;
    private Integer picture_height;
    public Integer _StartPosX;
    public Integer _StartPosY;
    private int drawingShipWidth = 150;
    protected int drawingShipHeight = 50;
    public Integer GetPosX() {return _StartPosX;}
    public Integer GetPosY() {return _StartPosY;}
    public Integer GetWidth() {return drawingShipWidth;}
    public Integer GetHeight() {return drawingShipHeight;}
    protected DrawingShip() {
        picture_width = null;
        picture_height = null;
        _StartPosX = null;
        _StartPosY = null;
    }
    public DrawingShip(int speed, double weight, Color bodycolor)  {
        super();
        EntityShip = new EntityShip(speed, weight, bodycolor);
    }
    protected DrawingShip(int drawingShipWidth, int drawingShipHeight) {
        super();
        this.drawingShipWidth = drawingShipWidth;
        this.drawingShipHeight = drawingShipHeight;
    }
    //добавил
    public DrawingShip(EntityShip entity, IDifferentDecks decks) {
        EntityShip = entity;
        drawingDecks = decks;
    }
    public boolean SetPictureSize(int width, int height) {
        if (width < drawingShipWidth || height < drawingShipHeight) return false;
        picture_width = width;
        picture_height = height;
        if (_StartPosX != null || _StartPosY != null) {
            if (_StartPosX + drawingShipWidth > picture_width)
            {
                _StartPosX = _StartPosX - (_StartPosX + drawingShipWidth - picture_width);
            }
            else if (_StartPosX < 0) _StartPosX = 0;
            if (_StartPosY + drawingShipHeight > picture_height)
            {
                _StartPosY = _StartPosY - (_StartPosY + drawingShipHeight - picture_height);
            }
            else if (_StartPosY < 0) _StartPosY = 0;
        }
        return true;
    }
    public void SetPosition(int x, int y) {
        if (!(picture_width != null && picture_height != null)) return;
        if (x + drawingShipWidth > picture_width)
        {
            _StartPosX = x - (x + drawingShipWidth - picture_width);
        }
        else if (x < 0) _StartPosX = 0;
        else _StartPosX = x;
        if (y + drawingShipHeight > picture_height)
        {
            _StartPosY = y - (y + drawingShipHeight - picture_height);
        }
        else if (y < 0) _StartPosY = 0;
        else _StartPosY = y;
    }
    public boolean MoveTransport(DirectionType direction) {
        if (EntityShip == null || _StartPosX == null || _StartPosY == null) return false;
        switch (direction) {
            case DirectionType.Left:
                if (_StartPosX - EntityShip.Step > 0) {
                    _StartPosX -= (int)EntityShip.Step;
                }
                return true;
            case DirectionType.Up:
                if (_StartPosY - EntityShip.Step > 0)
                {
                    _StartPosY -= (int)EntityShip.Step;
                }
                return true;
            case DirectionType.Right:
                if (_StartPosX + drawingShipWidth + (int)EntityShip.Step < picture_width - EntityShip.Step)
                {
                    _StartPosX += (int)EntityShip.Step;
                }
                return true;
            case DirectionType.Down:
                if (_StartPosY + drawingShipHeight + (int)EntityShip.Step < picture_height - EntityShip.Step)
                {
                    _StartPosY += (int)EntityShip.Step;
                }
                return true;
            default:
                return false;
        }
    }
    public void DrawTransport(Graphics2D g) {
        if (EntityShip == null || _StartPosX == null || _StartPosY == null) return;
        int y = _StartPosY;
        g.setColor(EntityShip.getBodyColor());
        if (drawingDecks != null && drawingDecks.getNumberOfDecks() != null) {
            switch (drawingDecks.getNumberOfDecks()) {
                case OneDeck:
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y, 100, 15, EntityShip.getBodyColor());
                    y += 15;
                    break;
                case TwoDecks:
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y, 100, 15, EntityShip.getBodyColor());
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y + 15, 100, 15, EntityShip.getBodyColor());
                    y += 30;
                    break;
                case ThreeDecks:
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y, 100, 15, EntityShip.getBodyColor());
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y + 15, 100, 15, EntityShip.getBodyColor());
                    drawingDecks.DrawDecks(g, _StartPosX + 30, y + 30, 100, 15, EntityShip.getBodyColor());
                    y += 45;
                    break;
            }
        }
        int[] arrayX = {_StartPosX, _StartPosX+150, _StartPosX+150, _StartPosX+120, _StartPosX+120, _StartPosX+30, _StartPosX+30, _StartPosX};
        int[] arrayY = {y, y, y, y + 50, y + 50, y + 50, y + 50, y};
        Polygon poly = new Polygon(arrayX, arrayY, 8);
        g.fillPolygon(poly);
        drawingShipHeight = y + 50 - _StartPosY;
    }
    public String[] GetStringRepresentationDecks() {
        if (drawingDecks instanceof DrawingDecksType1) {
            return new String[]{String.valueOf(drawingDecks.getNumberOfDecks().getNumdecks()), "DrawingDecksType1"};
        }
        else if (drawingDecks instanceof DrawingDecksType2) {
            return new String[]{String.valueOf(drawingDecks.getNumberOfDecks().getNumdecks()), "DrawingDecksType2"};
        }
        else if (drawingDecks instanceof DrawingDecksType3) {
            return new String[]{String.valueOf(drawingDecks.getNumberOfDecks().getNumdecks()), "DrawingDecksType3"};
        }
        return null;
    }
}