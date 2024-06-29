import CollectionGenericObjects.AbstractCompany;
import DiffetentsDrawingDecks.DrawingDecksType1;
import DiffetentsDrawingDecks.DrawingDecksType2;
import DiffetentsDrawingDecks.DrawingDecksType3;
import DiffetentsDrawingDecks.IDifferentDecks;
import DrawingShip.DrawingShip;
import DrawingShip.DrawingWarmlyShip;
import Entities.EntityWarmlyShip;
import Exceptions.CollectionOverflowException;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static DrawingShip.ExtentionDrawningShip.GetDataForSave;

public class FormShipConfig extends JFrame {
    private String title;
    private Dimension dimension;
    private DrawingShip _ship;
    private AbstractCompany company = null;
    private JLabel labelSpeed = new JLabel("Speed");
    private JLabel labelWeight = new JLabel("Weight");
    private JLabel labelShip = new JLabel("Ship", SwingConstants.CENTER);
    private JLabel labelWarmlyShip = new JLabel("WarmlyShip", SwingConstants.CENTER);
    private JLabel labelColor = new JLabel("Color");
    private JLabel labelBodyColor = new JLabel("BodyColor", SwingConstants.CENTER);
    private JLabel labelSolidDeck = new JLabel("Solid Deck",SwingConstants.CENTER);
    private JLabel labelBeamsDeck = new JLabel("Beams Deck", SwingConstants.CENTER);
    private JLabel labelWindowDeck = new JLabel("Window Deck", SwingConstants.CENTER);
    private JLabel labelAdditionalColor = new JLabel("Additi Color", SwingConstants.CENTER);
    private JLabel labelNumberOfDecks = new JLabel("Numb Of decks");
    private JSpinner spinnerSpeed = new JSpinner();
    private JSpinner spinnerWeight = new JSpinner();
    private JSpinner spinnerNumberOfDecks = new JSpinner();
    private JCheckBox checkBoxPipes = new JCheckBox("Have a Ship Pipes");
    private JCheckBox checkBoxFuelTank = new JCheckBox("Have a Fuel Tank");
    private JComponent panelObject = new JPanel();
    private JPanel panelColorRed = new JPanel();
    private JPanel panelColorGreen = new JPanel();
    private JPanel panelColorBlue = new JPanel();
    private JPanel panelColorYellow = new JPanel();
    private JPanel panelColorBlack = new JPanel();
    private JPanel panelColorWhite = new JPanel();
    private JPanel panelColorGray = new JPanel();
    private JPanel panelColorCyan = new JPanel();
    private JButton buttonAdd = new JButton("Add");
    private JButton buttonCansel = new JButton("Cancel");
    private org.apache.logging.log4j.Logger logger;
    private org.apache.logging.log4j.Logger loggerErrow;
    public FormShipConfig(String title, Dimension dimension, org.apache.logging.log4j.Logger logger,
                          org.apache.logging.log4j.Logger logger2) {
        this.title = title;
        this.dimension = dimension;
        this.logger = logger;
        this.loggerErrow = logger2;
    }
    public void Init() {
        SpinnerModel numSpeed = new SpinnerNumberModel(100, 100, 1000, 1);
        SpinnerModel numWeight = new SpinnerNumberModel(100, 100, 1000, 1);
        spinnerSpeed.setModel(numSpeed);
        spinnerWeight.setModel(numWeight);
        SpinnerModel numDecks = new SpinnerNumberModel(0, 0, 3, 1);
        spinnerNumberOfDecks.setModel(numDecks);
        panelObject = new Canvas();
        panelObject.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        spinnerSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (_ship == null) return;
                _ship.EntityShip.setSpeed((int)spinnerSpeed.getValue());
            }
        });
        spinnerWeight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (_ship == null) return;
                _ship.EntityShip.setWeight((int)spinnerWeight.getValue());
            }
        });
        checkBoxPipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_ship == null) return;
                if (_ship.EntityShip instanceof EntityWarmlyShip warmlyShip) {
                    warmlyShip.setShipPipes(checkBoxPipes.isSelected());
                    panelObject.repaint();
                }
            }
        });
        checkBoxFuelTank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_ship == null) return;
                if (_ship.EntityShip instanceof EntityWarmlyShip warmlyShip) {
                    warmlyShip.setFuelTank(checkBoxFuelTank.isSelected());
                    panelObject.repaint();
                }
            }
        });

        labelShip.setBackground(Color.WHITE);
        labelShip.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelWarmlyShip.setBackground(Color.WHITE);
        labelWarmlyShip.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelBodyColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelAdditionalColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelSolidDeck.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelBeamsDeck.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelWindowDeck.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        MouseAdapter labelObjectsMouseDown = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((JLabel) e.getComponent()).getTransferHandler().exportAsDrag(((JLabel) e.getComponent()), e, TransferHandler.COPY);
            }
        };

        TransferHandler labelObjectsTransferHandler = new TransferHandler() {
            @Override
            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY;
            }

            @Override
            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(((JLabel) c).getText());
            }
        };

        labelShip.addMouseListener(labelObjectsMouseDown);
        labelShip.setTransferHandler(labelObjectsTransferHandler);
        labelWarmlyShip.addMouseListener(labelObjectsMouseDown);
        labelWarmlyShip.setTransferHandler(labelObjectsTransferHandler);

        MouseAdapter labelDecksMouseDown = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((JLabel) e.getComponent()).getTransferHandler().exportAsDrag(((JLabel) e.getComponent()), e, TransferHandler.COPY);
            }
        };

        labelSolidDeck.addMouseListener(labelDecksMouseDown);
        labelBeamsDeck.addMouseListener(labelDecksMouseDown);
        labelWindowDeck.addMouseListener(labelDecksMouseDown);
        labelSolidDeck.setTransferHandler(new TransferHandler() {
            @Override
            public int getSourceActions(JComponent c) {return TransferHandler.COPY;}

            @Override
            protected Transferable createTransferable(JComponent c) {
                return new DecksTransferable(new DrawingDecksType1());
            }
        });
        labelBeamsDeck.setTransferHandler(new TransferHandler() {
            @Override
            public int getSourceActions(JComponent c) {return TransferHandler.COPY;}

            @Override
            protected Transferable createTransferable(JComponent c) {
                return new DecksTransferable(new DrawingDecksType2());
            }
        });
        labelWindowDeck.setTransferHandler(new TransferHandler() {
            @Override
            public int getSourceActions(JComponent c) {return TransferHandler.COPY;}

            @Override
            protected Transferable createTransferable(JComponent c) {
                return new DecksTransferable(new DrawingDecksType3());
            }
        });

        panelObject.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.stringFlavor)
                        || support.isDataFlavorSupported(DecksTransferable.decksDataFlavor);
            }
            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                if (canImport(support)) {
                    try {
                        String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                        switch (data) {
                            case "Ship":
                                _ship = new DrawingShip((int) spinnerSpeed.getValue(), (int) spinnerWeight.getValue(),
                                        Color.WHITE);
                                break;
                            case "WarmlyShip":
                                _ship = new DrawingWarmlyShip((int) spinnerSpeed.getValue(), (int) spinnerWeight.getValue(),
                                        Color.WHITE, Color.BLACK, checkBoxPipes.isSelected(), checkBoxFuelTank.isSelected());
                                break;
                        }
                        if (_ship != null) {
                            _ship.SetPictureSize(155,155);
                            _ship.SetPosition(5,10);
                        }
                        else return false;
                    }
                    catch (UnsupportedFlavorException | IOException e) {}
                    try {
                        IDifferentDecks decks =
                                (IDifferentDecks) support.getTransferable().getTransferData(DecksTransferable.decksDataFlavor);
                        _ship.drawingDecks = decks;
                        _ship.drawingDecks.setNumberOfDecks((int) spinnerNumberOfDecks.getValue());

                    }catch (UnsupportedFlavorException | IOException e) {}
                    panelObject.repaint();
                    return true;
                }
                return false;
            }
        });

        JPanel[] colorPanels = {
                panelColorRed,
                panelColorGreen,
                panelColorBlue,
                panelColorYellow,
                panelColorWhite,
                panelColorBlack,
                panelColorGray,
                panelColorCyan,
        };

        panelColorRed.setBackground(Color.RED);
        panelColorGreen.setBackground(Color.GREEN);
        panelColorBlue.setBackground(Color.BLUE);
        panelColorYellow.setBackground(Color.YELLOW);
        panelColorWhite.setBackground(Color.WHITE);
        panelColorBlack.setBackground(Color.BLACK);
        panelColorGray.setBackground(Color.GRAY);
        panelColorCyan.setBackground(Color.CYAN);

        MouseAdapter colorMouseDown = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((JPanel) e.getComponent()).getTransferHandler().exportAsDrag(((JPanel) e.getComponent()), e, TransferHandler.COPY);
            }
        };

        for (var panelColor : colorPanels) {
            panelColor.addMouseListener(colorMouseDown);
            panelColor.setTransferHandler(new ColorTransferHandler());
        }

        labelBodyColor.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(ColorTransferable.colorDataFlavor);
            }
            @Override
            public boolean importData(TransferSupport support) {
                try {
                    Color color = (Color) support.getTransferable().getTransferData(ColorTransferable.colorDataFlavor);
                    if (_ship == null) return false;
                    _ship.EntityShip.setBodyColor(color);
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        labelAdditionalColor.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                if (!(_ship instanceof DrawingWarmlyShip)) return false;
                return support.isDataFlavorSupported(ColorTransferable.colorDataFlavor);
            }
            @Override
            public boolean importData(TransferSupport support) {
                try {
                    Color color = (Color) support.getTransferable().getTransferData(ColorTransferable.colorDataFlavor);
                    if (_ship == null) return false;
                    if (_ship.EntityShip instanceof EntityWarmlyShip warmlyShip) {
                        warmlyShip.setAdditionalColor(color);
                        labelColor.setBackground(color);
                        return true;
                    }
                    return false;
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_ship == null) return;
                DrawingShip copyShip;
                if (_ship instanceof DrawingWarmlyShip)
                    copyShip =  new DrawingWarmlyShip((EntityWarmlyShip) _ship.EntityShip, _ship.drawingDecks);
                else
                    copyShip =  new DrawingShip(_ship.EntityShip, _ship.drawingDecks);
                try {
                    company._collection.Insert(copyShip);
                    FormShipCollection.canvasShow();
                    logger.info("Добавлен объект: " + GetDataForSave(copyShip));
                } catch (CollectionOverflowException ex) {
                    loggerErrow.warn(ex.toString());
                    JOptionPane.showMessageDialog(null, "Коллекция переполнена");
                } catch (Exception ex) {
                    loggerErrow.fatal("Ошибка при добавлении объекта с формы config");
                }
                dispose();
            }
        });

        buttonCansel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        labelSpeed.setBounds(10, 17, 50, 15);
        labelWeight.setBounds(10,43, 50, 15);
        labelNumberOfDecks.setBounds(10, 68, 50, 15);
        labelShip.setBounds(10,150,70,30);
        labelWarmlyShip.setBounds(10,190,70,30);
        labelColor.setBounds(170,10,30,15);
        labelBodyColor.setBounds(500,5,75, 40);
        labelAdditionalColor.setBounds(580,5,75,40);
        labelSolidDeck.setBounds(120, 210, 100, 40);
        labelBeamsDeck.setBounds(230, 210, 100,40);
        labelWindowDeck.setBounds(340,210,100,40);
        spinnerSpeed.setBounds(60,15, 60,20);
        spinnerWeight.setBounds(60,40, 60,20);
        spinnerNumberOfDecks.setBounds(60, 65,60,20);
        checkBoxPipes.setBounds(8,85,150,20);
        checkBoxFuelTank.setBounds(8,105,150,20);
        panelObject.setBounds(500,50,160,150);
        panelColorRed.setBounds(170, 30, 40, 40);
        panelColorGreen.setBounds(220, 30, 40,40);
        panelColorBlue.setBounds(270,30,40,40);
        panelColorYellow.setBounds(320,30,40,40);
        panelColorWhite.setBounds(170, 80,40,40);
        panelColorBlack.setBounds(220,80,40,40);
        panelColorGray.setBounds(270,80,40,40);
        panelColorCyan.setBounds(320,80,40,40);
        buttonAdd.setBounds(500, 210, 70, 40);
        buttonCansel.setBounds(585, 210, 70, 40);

        setSize(dimension.width, dimension.height);
        setLayout(null);
        add(labelSpeed);
        add(labelWeight);
        add(labelNumberOfDecks);
        add(labelShip);
        add(labelWarmlyShip);
        add(labelColor);
        add(labelBodyColor);
        add(labelAdditionalColor);
        add(labelSolidDeck);
        add(labelSolidDeck);
        add(labelBeamsDeck);
        add(labelWindowDeck);
        add(spinnerSpeed);
        add(spinnerWeight);
        add(spinnerNumberOfDecks);
        add(checkBoxPipes);
        add(checkBoxFuelTank);
        add(panelObject);
        add(panelColorRed);
        add(panelColorGreen);
        add(panelColorBlue);
        add(panelColorYellow);
        add(panelColorWhite);
        add(panelColorBlack);
        add(panelColorGray);
        add(panelColorCyan);
        add(buttonAdd);
        add(buttonCansel);
        setVisible(true);
    }
    public void setCompany(AbstractCompany company) {
        this.company = company;
    }
    private class Canvas extends JComponent {
        public Canvas() {
        }
        public void paintComponent(Graphics g) {
            if (_ship == null) {
                return;
            }
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;
            _ship.DrawTransport(g2d);
            super.repaint();
        }
    }
    private class ColorTransferable implements Transferable {
        private Color color;
        private static final DataFlavor colorDataFlavor = new DataFlavor(Color.class, "Color");
        public ColorTransferable(Color color) {
            this.color = color;
        }
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{colorDataFlavor};
        }
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return colorDataFlavor.equals(flavor);
        }
        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (isDataFlavorSupported(flavor)) {
                return color;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
    private class ColorTransferHandler extends TransferHandler {
        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY;
        }
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new ColorTransferable(c.getBackground());
        }
    }
    private class DecksTransferable implements Transferable {
        private IDifferentDecks decks;
        private static final DataFlavor decksDataFlavor = new DataFlavor(IDifferentDecks.class, "Decks");
        public DecksTransferable(IDifferentDecks decks) {
            this.decks = decks;
        }
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{decksDataFlavor};
        }
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(decksDataFlavor);
        }
        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (isDataFlavorSupported(flavor)) {
                return decks;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

}
