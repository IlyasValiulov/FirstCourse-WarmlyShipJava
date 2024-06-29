package CollectionGenericObjects;

import DrawingShip.DrawingShip;

import java.awt.*;

public class ShipPortService extends AbstractCompany{
    public ShipPortService(int picWidth, int picHeight, ICollectionGenericObjects<DrawingShip> collection) {
        super(picWidth, picHeight, collection);
    }
    @Override
    public void DrawBackgound(Graphics g) {
        //рисуем пристань
        int width = _pictureWidth / _placeSizeWidth;
        int height = _pictureHeight / _placeSizeHeight;
        g.setColor(Color.BLACK);
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height + 1; ++j)
            {
                g.drawLine(i * _placeSizeWidth, j * _placeSizeHeight,
                        i * _placeSizeWidth + _placeSizeWidth - 5, j * _placeSizeHeight );
            }
        }
    }
    @Override
    protected void SetObjectsPosition() {
        int width = _pictureWidth / _placeSizeWidth;
        int height = _pictureHeight / _placeSizeHeight;

        int curWidth = width - 1;
        int curHeight = 0;
        for (int i = 0; i < (_collection.getCount()); i++) {
            try {
                if (_collection.Get(i) != null) {
                    _collection.Get(i).SetPictureSize(_pictureWidth, _pictureHeight);
                    _collection.Get(i).SetPosition(_placeSizeWidth * curWidth + 20, curHeight * _placeSizeHeight + 4);
                }
            }
            catch (Exception ex) {}
            if (curWidth > 0)
                curWidth--;
            else {
                curWidth = width - 1;
                curHeight++;
            }
            if (curHeight > height) {
                return;
            }
        }
    }
}

