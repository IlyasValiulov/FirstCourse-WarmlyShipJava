package CollectionGenericObjects;

import DrawingShip.DrawingShip;
import Exceptions.ObjectNotFoundException;
import Exceptions.PositionOutOfCollectionException;

import java.awt.*;

public abstract class AbstractCompany {
    protected int _placeSizeWidth = 210;
    protected  int _placeSizeHeight = 150;
    protected int _pictureWidth;
    protected  int _pictureHeight;
    public ICollectionGenericObjects<DrawingShip> _collection = null;
    private int GetMaxCount() {
        return _pictureWidth * _pictureHeight / (_placeSizeWidth * _placeSizeHeight);
    }
    public AbstractCompany(int picWidth, int picHeight, ICollectionGenericObjects<DrawingShip> collection)
    {
        _pictureWidth = picWidth;
        _pictureHeight = picHeight;
        _collection = collection;
        _collection.SetMaxCount(GetMaxCount());
    }
    //перегрузка операторов в джаве не возможна
    public DrawingShip GetRandomObject() throws PositionOutOfCollectionException, ObjectNotFoundException {
        return _collection.Get((int)(Math.random()*GetMaxCount() + 0));
    }
    public void SetPosition()
    {
        SetObjectsPosition();
    }
    public abstract void DrawBackgound(Graphics graphics);
    protected abstract void SetObjectsPosition();
}
