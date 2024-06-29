package CollectionGenericObjects;

import Exceptions.CollectionOverflowException;
import Exceptions.ObjectNotFoundException;
import Exceptions.PositionOutOfCollectionException;

public interface ICollectionGenericObjects<T>
{
    int getCount();
    void SetMaxCount(int count);
    int Insert(T obj) throws CollectionOverflowException;
    T Remove(int position) throws PositionOutOfCollectionException, ObjectNotFoundException;
    T Get(int position) throws PositionOutOfCollectionException, ObjectNotFoundException;
    CollectionType GetCollectionType();
    Iterable<T> GetItems();
    void ClearCollection();
}

