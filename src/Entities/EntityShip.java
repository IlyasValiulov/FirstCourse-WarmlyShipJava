package Entities;

import java.awt.*;
import java.util.Objects;

public class EntityShip {
    private Integer Speed;
    public void setSpeed(int speed) {Speed = speed;}
    public Integer getSpeed() {return Speed;}
    private Double Weight;
    public void setWeight(double weight) {Weight = weight;}
    public Double getWeight() {return Weight;}
    private Color BodyColor;
    public Color getBodyColor() {return BodyColor;}
    public void setBodyColor(Color color) {BodyColor = color;}
    public double Step;
    public EntityShip(int speed, double weight, Color bodycolor)
    {
        Speed = speed;
        Weight = weight;
        BodyColor = bodycolor;
        Step = Speed * 100 / Weight;
    }
    public String[] GetStringRepresentation()
    {
        return new String[]{"EntityShip", Speed.toString(), Weight.toString(), colorToHexString(BodyColor)};
    }
    public static EntityShip CreateEntityShip(String[] strs)
    {
        if (strs.length != 6 || !Objects.equals(strs[0], "EntityShip"))
        {
            return null;
        }
        return new EntityShip(Integer.parseInt(strs[1]), Double.parseDouble(strs[2]), hexStringToColor(strs[3]));
    }
    public static String colorToHexString(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
    public static Color hexStringToColor(String hexString) {
        return Color.decode(hexString);
    }
}
