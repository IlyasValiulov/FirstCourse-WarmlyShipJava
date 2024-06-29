package Exceptions;
//неиспользованные методы
public class CollectionOverflowException extends Exception {
    public CollectionOverflowException(int count) {super("В коллекции превышено допустимое количество: " + count); }
    public CollectionOverflowException() { super(); }
    public CollectionOverflowException(String message) { super(message); }
    public CollectionOverflowException(String message, Exception exception) { super(message, exception); }
}
