package Exceptions;
//неиспользованные методы
public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(int i) { super("Не найден объект по позиции " + i); }
    public ObjectNotFoundException() { super(); }
    public ObjectNotFoundException(String message) { super(message); }
    public ObjectNotFoundException(String message, Throwable cause) {super(message, cause); }
}
