package CollectionAdditionalObjects;

import DiffetentsDrawingDecks.IDifferentDecks;
import DrawingShip.DrawingShip;
import DrawingShip.DrawingWarmlyShip;
import Entities.EntityShip;
import Entities.EntityWarmlyShip;

import java.lang.reflect.Array;
import java.util.Random;

public class AdditionalCollections <T extends EntityShip, U extends IDifferentDecks>{
    public T[] _collectionEntity;
    public U[] _collectionDecks;
    public AdditionalCollections(int size, Class<T> type1, Class<T> type2) {
        _collectionEntity = (T[]) Array.newInstance(type1, size);
        _collectionDecks = (U[]) Array.newInstance(type2, size);
        CountEntities = size;
        CountDecks = size;
    }
    public int CountEntities;
    public int CountDecks;
    public int Insert(T entity) {
        int index = 0;
        while (index < CountEntities) {
            if (_collectionEntity[index] == null)
            {
                _collectionEntity[index] = entity;
                return index;
            }
            ++index;
        }
        return -1;
    }
    public int Insert(U decks) {
        int index = 0;
        while (index < CountDecks) {
            if (_collectionDecks[index] == null)
            {
                _collectionDecks[index] = decks;
                return index;
            }
            ++index;
        }
        return -1;
    }
    public DrawingShip CreateAdditionalCollectionShip() {
        Random random = new Random();
        if (_collectionEntity == null || _collectionDecks == null) return null;
        T entity = _collectionEntity[random.nextInt(CountEntities)];
        U decks = _collectionDecks[random.nextInt(CountDecks)];
        DrawingShip drawingShip = null;
        if (entity instanceof EntityWarmlyShip) {
            drawingShip = new DrawingWarmlyShip((EntityWarmlyShip) entity, decks);
        }
        else {
            drawingShip = new DrawingShip(entity, decks);
        }
        return drawingShip;
    }
}
