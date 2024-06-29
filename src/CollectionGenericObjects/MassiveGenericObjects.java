package CollectionGenericObjects;

import DrawingShip.DrawingShip;
import Exceptions.CollectionOverflowException;
import Exceptions.ObjectNotFoundException;
import Exceptions.PositionOutOfCollectionException;

import java.lang.reflect.Array;
import java.util.*;

public class MassiveGenericObjects<T> implements ICollectionGenericObjects<T>{
    private T[] _collection = null;
    private int Count;
    private CollectionType collectionType = CollectionType.Massive;
    @Override
    public void SetMaxCount(int size) {
        if (size > 0) {
            if (_collection == null) {
                _collection = (T[]) Array.newInstance((Class) DrawingShip.class, size);
                Count = size;
            }
        }
    }
    @Override
    public int getCount() {
        return Count;
    }
    @Override
    public CollectionType GetCollectionType() {
        return collectionType;
    }
    @Override
    public int Insert(T obj) throws CollectionOverflowException {
        int index = 0;
        while (index < getCount())
        {
            if (_collection[index] == null)
            {
                _collection[index] = obj;
                return index;
            }
            ++index;
        }
        throw new CollectionOverflowException(Count);
    }
    @Override
    public T Remove(int position) throws PositionOutOfCollectionException, ObjectNotFoundException {
        if (position >= getCount() || position < 0)
            throw new PositionOutOfCollectionException(position);
        if (_collection[position] == null) throw new ObjectNotFoundException(position);
        T obj = (T) _collection[position];
        _collection[position] = null;
        return obj;
    }
    @Override
    public T Get(int position) throws PositionOutOfCollectionException, ObjectNotFoundException {
        if (position >= getCount() || position < 0) throw new PositionOutOfCollectionException(position);
        if (_collection[position] == null) throw new ObjectNotFoundException(position);
        return (T) _collection[position];
    }
    @Override
    public Iterable<T> GetItems() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private int currentIndex = 0;
                    //нужен ли count
                    private int count = 0;
                    @Override
                    public boolean hasNext() {
                        return currentIndex < getCount();
                    }
                    @Override
                    public T next() {
                        if (hasNext()) {
                            count++;
                            return _collection[currentIndex++];
                        }
                        throw new NoSuchElementException();
                    }
                };
            }
        };
    }
    @Override
    public void ClearCollection() {
        for (T ship : _collection) {
            ship = null;
        }
    }
}
