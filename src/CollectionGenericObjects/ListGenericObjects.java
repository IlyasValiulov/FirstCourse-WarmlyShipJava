package CollectionGenericObjects;

import Exceptions.CollectionOverflowException;
import Exceptions.PositionOutOfCollectionException;

import java.util.*;

public class ListGenericObjects<T> implements ICollectionGenericObjects<T> {
    private List<T> _collection;
    private CollectionType collectionType = CollectionType.List;
    private int _maxCount;
    public int getCount() {
        return _collection.size();
    }
    @Override
    public void SetMaxCount(int size) {
        if (size > 0) {
            _maxCount = size;
        }
    }
    public ListGenericObjects() {
        _collection = new ArrayList<T>();
    }
    @Override
    public CollectionType GetCollectionType() {
        return collectionType;
    }
    @Override
    public T Get(int position) throws PositionOutOfCollectionException {
        if (position >= getCount() || position < 0) throw new PositionOutOfCollectionException(position);
        return _collection.get(position);
    }
    @Override
    public int Insert(T obj) throws CollectionOverflowException {
        if (getCount() == _maxCount) throw new CollectionOverflowException(getCount());
        _collection.add(obj);
        return getCount();
    }
    @Override
    public T Remove(int position) throws PositionOutOfCollectionException {
        if (position >= getCount() || position < 0) throw new PositionOutOfCollectionException(position);
        T obj = _collection.get(position);
        _collection.remove(position);
        return obj;
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
                            return _collection.get(currentIndex++);
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
