package MovementStrategy;

public abstract class AbstractStrategy {
    private IMoveableObjects _moveableObject;
    private StrategyStatus _state = StrategyStatus.NotInit;
    public int FieldWidth;
    public int FieldHeight;
    public StrategyStatus GetStatus() {return _state;}
    public void SetData(IMoveableObjects moveableObjects, int width, int height)
    {
        if (moveableObjects == null)
        {
            _state = StrategyStatus.NotInit;
            return;
        }
        _state = StrategyStatus.InProgress;
        _moveableObject = moveableObjects;
        FieldWidth = width;
        FieldHeight = height;
    }
    public void MakeStep()
    {
        if (_state != StrategyStatus.InProgress) return;
        if (IsTargetDestinaion())
        {
            _state = StrategyStatus.Finish;
            return;
        }
        MoveToTarget();
    }
    protected boolean MoveLeft() {return MoveTo(MovementDirection.Left);};
    protected boolean MoveRight() {return MoveTo(MovementDirection.Right);};
    protected boolean MoveUp() {return MoveTo(MovementDirection.Up);};
    protected boolean MoveDown() {return MoveTo(MovementDirection.Down);};
    protected ObjectParameters GetObjectParameters() {return _moveableObject.GetObjectPosition();};
    protected Integer GetStep()
    {
        if (_state != StrategyStatus.InProgress)
        {
            return null;
        }
        return _moveableObject.GetStep();
    }
    protected abstract void MoveToTarget();
    protected abstract boolean IsTargetDestinaion();
    private boolean MoveTo(MovementDirection movementDirection)
    {
        if (_state != StrategyStatus.InProgress)
        {
            return false;
        }
        boolean stateTryMoveObject = _moveableObject.TryMoveObject(movementDirection);
        if (stateTryMoveObject) return stateTryMoveObject;
        return false;
    }
}
