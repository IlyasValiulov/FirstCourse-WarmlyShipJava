import DrawingShip.CanvasWarmlyShip;
import DrawingShip.DirectionType;
import DrawingShip.DrawingShip;
import MovementStrategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FormWarmlyShip extends JFrame {
    private String title;
    private Dimension dimension;
    private int Width, Height;
    public CanvasWarmlyShip canvasWarmlyShip = new CanvasWarmlyShip();
    private JButton UpButton = new JButton();
    private JButton DownButton = new JButton();;
    private JButton LeftButton = new JButton();;
    private JButton RightButton = new JButton();
    private AbstractStrategy _strategy;
    private JComboBox ComboBoxStrategy = new JComboBox(new String[]{"К центру", "К краю"});
    private JButton ButtonStrategy = new JButton("Шаг");
    public FormWarmlyShip(String title, Dimension dimension) {
        this.title = title;
        this.dimension = dimension;
    }
    public void Init(DrawingShip ship) {
        setTitle(title);
        setMinimumSize(dimension);

        Width = getWidth() - 10;
        Height = getHeight() - 34;
        ComboBoxStrategy.setEnabled(true);
        _strategy = null;
        canvasWarmlyShip._drawingShip = ship;

        Icon iconUp = new ImageIcon("src\\images\\up.jpg");
        UpButton.setIcon(iconUp);
        UpButton.setName("UP");
        DownButton.setName("DOWN");
        Icon iconDown = new ImageIcon("src\\images\\down.jpg");
        DownButton.setIcon(iconDown);
        LeftButton.setName("LEFT");
        Icon iconLeft = new ImageIcon("src\\images\\left.jpg");
        LeftButton.setIcon(iconLeft);
        RightButton.setName("RIGHT");
        Icon iconRight = new ImageIcon("src\\images\\right.jpg");
        RightButton.setIcon(iconRight);

        ButtonStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canvasWarmlyShip._drawingShip == null) return;
                if (ComboBoxStrategy.isEnabled())
                {
                    int index = ComboBoxStrategy.getSelectedIndex();
                    switch(index)
                    {
                        case 0:
                            _strategy = new MoveToCenter();
                            break;
                        case 1:
                            _strategy = new MoveToBorder();
                            break;
                        default:
                            _strategy = null;
                            break;
                    };
                    if (_strategy == null)
                    {
                        return;
                    }
                    _strategy.SetData(new MoveableShip(canvasWarmlyShip._drawingShip), Width, Height);
                }
                if (_strategy == null)
                {
                    return;
                }
                ComboBoxStrategy.setEnabled(false);
                _strategy.MakeStep();
                if (_strategy.GetStatus() == StrategyStatus.Finish)
                {
                    ComboBoxStrategy.setEnabled(true);
                    _strategy = null;
                }
            }
        });

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (canvasWarmlyShip._drawingShip == null) return;
                boolean result = false;
                switch ((((JButton)(event.getSource())).getName())) {
                    case "UP":
                        result = canvasWarmlyShip._drawingShip.MoveTransport(DirectionType.Up);
                        break;
                    case "DOWN":
                        result = canvasWarmlyShip._drawingShip.MoveTransport(DirectionType.Down);
                        break;
                    case "LEFT":
                        result = canvasWarmlyShip._drawingShip.MoveTransport(DirectionType.Left);
                        break;
                    case "RIGHT":
                        result = canvasWarmlyShip._drawingShip.MoveTransport(DirectionType.Right);
                        break;
                }
                if (result) {
                    canvasWarmlyShip.repaint();
                }
            }
        };
        UpButton.addActionListener(actionListener);
        DownButton.addActionListener(actionListener);
        LeftButton.addActionListener(actionListener);
        RightButton.addActionListener(actionListener);

        setSize(dimension.width,dimension.height);
        setLayout(null);
        canvasWarmlyShip.setBounds(0,0, getWidth(), getHeight());
        UpButton.setBounds(getWidth() - 140, getHeight() - 160, 50, 50);
        DownButton.setBounds(getWidth() - 140, getHeight() - 100, 50, 50);
        RightButton.setBounds(getWidth() - 80, getHeight() - 100, 50, 50);
        LeftButton.setBounds(getWidth() - 200, getHeight() - 100, 50, 50);
        ComboBoxStrategy.setBounds(getWidth() - 170, 10, 140, 35);
        ButtonStrategy.setBounds(getWidth() - 130, 55, 100, 25);
        add(UpButton);
        add(DownButton);
        add(RightButton);
        add(LeftButton);
        add(ComboBoxStrategy);
        add(ButtonStrategy);
        add(canvasWarmlyShip);
        setVisible(true);
        //обработка события изменения размеров окна
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Width = getWidth() - 10;
                Height = getHeight() - 34;
                if (canvasWarmlyShip._drawingShip != null)
                    canvasWarmlyShip._drawingShip.SetPictureSize(Width, Height);
                canvasWarmlyShip.setBounds(0,0, getWidth(), getHeight());
                UpButton.setBounds(getWidth() - 140, getHeight() - 160, 50, 50);
                DownButton.setBounds(getWidth() - 140, getHeight() - 100, 50, 50);
                RightButton.setBounds(getWidth() - 80, getHeight() - 100, 50, 50);
                LeftButton.setBounds(getWidth() - 200, getHeight() - 100, 50, 50);
                ComboBoxStrategy.setBounds(getWidth() - 170, 10, 140, 35);
                ButtonStrategy.setBounds(getWidth() - 130, 55, 100, 25);
            }
        });
    }
}
