package MovementStrategy;

import DrawingShip.CanvasFormShipCollection;
import DrawingShip.DirectionType;
import DrawingShip.DrawingShip;
import DrawingShip.CanvasWarmlyShip;

public class MoveableShip implements IMoveableObjects{
    private CanvasWarmlyShip canvas = new CanvasWarmlyShip();
    public MoveableShip(DrawingShip drawningship)
    {
        canvas._drawingShip = drawningship;
    }
    @Override
    public ObjectParameters GetObjectPosition() {
      if (canvas._drawingShip == null || canvas._drawingShip.EntityShip == null ||
              canvas._drawingShip.GetPosX() == null || canvas._drawingShip.GetPosY() == null)
      {
          return null;
      }
      return new ObjectParameters(canvas._drawingShip.GetPosX(), canvas._drawingShip.GetPosY(),
                canvas._drawingShip.GetWidth(), canvas._drawingShip.GetHeight());
    }
    @Override
    public int GetStep() {
        return (int)(canvas._drawingShip.EntityShip.Step);
    }
    @Override
    public boolean TryMoveObject(MovementDirection direction) {
      if (canvas._drawingShip == null || canvas._drawingShip.EntityShip == null)
      {
          return false;
      }
        return canvas._drawingShip.MoveTransport(GetDirectionType(direction));
    }
    private static DirectionType GetDirectionType(MovementDirection direction)
    {
        switch (direction) {
            case MovementDirection.Left: return DirectionType.Left;
            case MovementDirection.Right: return DirectionType.Right;
            case MovementDirection.Up: return DirectionType.Up;
            case MovementDirection.Down: return DirectionType.Down;
            default: return DirectionType.Unknow;
        }
    }
}
