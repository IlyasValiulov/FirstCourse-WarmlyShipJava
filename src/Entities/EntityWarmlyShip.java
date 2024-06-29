package Entities;

import java.awt.*;
import java.util.Objects;

public class EntityWarmlyShip extends EntityShip{
    public Color AdditionalColor;
    public  Color getAdditionalColor() {return AdditionalColor;}
    public void setAdditionalColor(Color color) {AdditionalColor = color;}
    public  boolean ShipPipes;
    public void setShipPipes(boolean pipes) {ShipPipes = pipes;}
    public  boolean FuelTank;
    public  boolean getFuelTank() {return FuelTank;}
    public void setFuelTank(boolean tank) {FuelTank = tank;}
    public EntityWarmlyShip(int speed, double weight, Color bodyColor, Color additionalcolor, boolean sheeppipes, boolean fueltank)
    {
        super(speed, weight, bodyColor);
        AdditionalColor = additionalcolor;
        ShipPipes = sheeppipes;
        FuelTank = fueltank;
    }
    @Override
    public String[] GetStringRepresentation()
    {
        return new String[]{"EntityWarmlyShip", getSpeed().toString(), getWeight().toString(),
                colorToHexString(getBodyColor()), colorToHexString(getAdditionalColor()),
                String.valueOf(ShipPipes), String.valueOf(FuelTank)};
    }
    public static EntityWarmlyShip CreateEntityWarmlyShip(String[] strs)
    {
        if (strs.length != 9 || !Objects.equals(strs[0], "EntityWarmlyShip"))
        {
            return null;
        }
        return new EntityWarmlyShip(Integer.parseInt(strs[1]), Double.parseDouble(strs[2]), hexStringToColor(strs[3]),
                hexStringToColor(strs[4]), Boolean.parseBoolean(strs[5]), Boolean.parseBoolean(strs[6]));
    }
}
