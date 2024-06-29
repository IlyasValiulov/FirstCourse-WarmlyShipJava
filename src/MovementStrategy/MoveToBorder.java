package MovementStrategy;

public class MoveToBorder extends AbstractStrategy{
    @Override
    protected boolean IsTargetDestinaion() {
        ObjectParameters objParams = GetObjectParameters();
        if (objParams == null)
        {
            return false;
        }
        return objParams.RightBorder + GetStep() >= FieldWidth-GetStep() &&
                objParams.DownBorder + GetStep() >= FieldHeight-GetStep();
    }
    @Override
    protected void MoveToTarget() {
        ObjectParameters objParams = GetObjectParameters();
        if (objParams == null)
        {
            return;
        }
        //реализация в правый нижний угол
        int x = objParams.RightBorder;
        if (x + GetStep() < FieldWidth) MoveRight();
        int y = objParams.DownBorder;
        if (y + GetStep() < FieldHeight) MoveDown();
    }
}
