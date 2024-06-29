package DrawingShip;

import DiffetentsDrawingDecks.DrawingDecksType1;
import DiffetentsDrawingDecks.DrawingDecksType2;
import DiffetentsDrawingDecks.DrawingDecksType3;
import DiffetentsDrawingDecks.IDifferentDecks;
import Entities.EntityShip;
import Entities.EntityWarmlyShip;

import java.util.ArrayList;
import java.util.Collections;

public class ExtentionDrawningShip {
    private static String _separatorForObjectS = ":";
    private static String _separatorForObject = "\\:";
    public static DrawingShip CreateDrawingShip(String info) {
        String[] strs = info.split(_separatorForObject);
        EntityShip ship;
        IDifferentDecks decks = null;
        if (strs.length == 8)
        {
           String s = strs[8];
           switch (s) {
               case "DrawingDecksType1":
                   decks = new DrawingDecksType1();
               case "DrawingDecksType2":
                   decks = new DrawingDecksType2();
               case "DrawingDecksType3":
                   decks = new DrawingDecksType3();
           }
           if (decks != null) decks.setNumberOfDecks(Integer.parseInt(strs[7]));
        }
        else if (strs.length == 6) {
            String s = strs[5];
            switch (s) {
                case "DrawingDecksType1":
                    decks = new DrawingDecksType1();
                case "DrawingDecksType2":
                    decks = new DrawingDecksType2();
                case "DrawingDecksType3":
                    decks = new DrawingDecksType3();
            }
            if (decks != null) decks.setNumberOfDecks(Integer.parseInt(strs[4]));
        }
        ship = EntityWarmlyShip.CreateEntityWarmlyShip(strs);
        if (ship != null)
        {
            return new DrawingWarmlyShip((EntityWarmlyShip)ship, decks);
        }
        ship = EntityShip.CreateEntityShip(strs);
        if (ship != null)
        {
            return new DrawingShip(ship, decks);
        }
        return null;
    }
    public static String GetDataForSave(DrawingShip drawningShip)
    {
        if (drawningShip == null) return "";
        String[] array1 = drawningShip.EntityShip.GetStringRepresentation();
        String[] array2 = drawningShip.GetStringRepresentationDecks();
        if (array1 == null)
        {
            return "";
        }
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, array1);
        if (array2 == null) {
            Collections.addAll(list, "0", " ");
        }
        else Collections.addAll(list, array2);
        return String.join(_separatorForObjectS, list);
    }
}
