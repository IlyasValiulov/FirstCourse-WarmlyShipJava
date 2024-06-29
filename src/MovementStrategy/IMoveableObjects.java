package MovementStrategy;

public interface IMoveableObjects {
    ObjectParameters GetObjectPosition();
    int GetStep();
    boolean TryMoveObject(MovementDirection direction);
}
