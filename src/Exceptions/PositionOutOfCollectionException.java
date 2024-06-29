package Exceptions;
//неиспользуемые методы
public class PositionOutOfCollectionException extends Exception{
    public PositionOutOfCollectionException(int i) { super("Выход за границы коллекции. Позиция " + i); }
    public PositionOutOfCollectionException() { super(); }
    public PositionOutOfCollectionException(String message) { super(message); }
    public PositionOutOfCollectionException(String message, Throwable cause) { super(message, cause); }
}
