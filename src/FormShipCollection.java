import CollectionGenericObjects.*;
import DrawingShip.CanvasFormShipCollection;
import DrawingShip.DrawingShip;
import Exceptions.ObjectNotFoundException;
import Exceptions.PositionOutOfCollectionException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Stack;
import java.util.logging.Logger;

import static DrawingShip.ExtentionDrawningShip.GetDataForSave;
import static java.lang.Integer.parseInt;

public class FormShipCollection extends JFrame{
    private String title;
    private Dimension dimension;
    public static CanvasFormShipCollection<DrawingShip> _canvasWarmlyShip = new CanvasFormShipCollection<DrawingShip>();
    private static AbstractCompany _company = null;
    private Stack<DrawingShip> _collectionRemoveObjects = new Stack<DrawingShip>();
    private StorageCollection<DrawingShip> _storageCollection = new StorageCollection<DrawingShip>();
    private JTextField textBoxCollection = new JTextField();
    private JRadioButton radioButtonMassive = new JRadioButton("Massive");
    private JRadioButton radioButtonList = new JRadioButton("List");
    private JButton buttonAddCollection = new JButton("Add");
    private JList listBoxCollection = new JList();
    private JButton buttonRemoveCollection = new JButton("Remove");
    private JButton buttonCreateCompany = new JButton("Create company");
    private JButton CreateShipButton = new JButton("Create ship");
    private JButton RemoveButton = new JButton("Remove");
    private JButton GoToCheckButton = new JButton("Check");
    private JButton RandomButton = new JButton("RandomShip");
    private JButton RemoveObjectsButton = new JButton("Show remove");
    private JButton RefreshButton = new JButton("Refresh");
    private JComboBox ComboBoxCollections = new JComboBox(new String[]{"", "Хранилище"});
    private JFormattedTextField MaskedTextField;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem loadItem = new JMenuItem("Load");
    private JMenuItem saveItem = new JMenuItem("Save");
    private JMenuItem loadCollection = new JMenuItem("Load coll");
    private JMenuItem saveCollection = new JMenuItem("Save coll");
    private org.apache.logging.log4j.Logger logger;
    private org.apache.logging.log4j.Logger loggerErrow;
    public FormShipCollection(String title, Dimension dimension,
                              org.apache.logging.log4j.Logger logger1, org.apache.logging.log4j.Logger logger2) {
        this.title = title;
        this.dimension = dimension;
        this.logger = logger1;
        logger.info("Форма загрузилась");
        this.loggerErrow = logger2;
    }

    public static void canvasShow() {
        _company.SetPosition();
        _canvasWarmlyShip.SetCollectionToCanvas(_company);
        _canvasWarmlyShip.repaint();
    }
    public void Init() {
        setTitle(title);
        setMinimumSize(dimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##");
            mask.setPlaceholder("00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MaskedTextField = new JFormattedTextField(mask);

        CreateShipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_company == null) return;
                FormShipConfig form = new FormShipConfig("", new Dimension(700, 300),
                        logger, loggerErrow);
                form.setCompany(_company);
                form.Init();
            }
        });

        RemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_company == null || MaskedTextField.getText() == null ||
                        listBoxCollection.getSelectedValue().toString() == null) {
                    return;
                }
                int pos = parseInt(MaskedTextField.getText());
                int resultConfirmDialog = JOptionPane.showConfirmDialog(null,
                        "Удалить", "Удаление",
                        JOptionPane.YES_NO_OPTION);
                if (resultConfirmDialog == JOptionPane.NO_OPTION) return;
                try {
                    DrawingShip obj = _storageCollection.Get(
                            listBoxCollection.getSelectedValue().toString(), pos);
                    if (obj != null) {
                        JOptionPane.showMessageDialog(null, "Объект удален");
                        _collectionRemoveObjects.push(obj);
                        canvasShow();
                        logger.info("Объект удален по позиции " + pos);
                    }
                }
                catch (PositionOutOfCollectionException | ObjectNotFoundException ex) {
                    loggerErrow.warn(ex.toString());
                }
                catch (Exception ex) {
                    loggerErrow.fatal(ex.toString());
                    JOptionPane.showMessageDialog(null, "Объект не удалось удалить");
                }
            }
        });

        GoToCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_company == null)
                {
                    return;
                }
                try {

                    DrawingShip ship = null;
                    int counter = 100;
                    while (ship == null) {
                        ship = _company.GetRandomObject();
                        counter--;
                        if (counter <= 0) {
                            break;
                        }
                    }
                    if (ship == null) {
                        return;
                    }
                    FormWarmlyShip form = new FormWarmlyShip("Теплоход", new Dimension(900, 565));
                    form.Init(ship);
                }
                catch (Exception ex) {}
            }
        });

        RandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_company == null)
                {
                    return;
                }
                try {
                    FormAdditionalCollection form = new FormAdditionalCollection(logger, loggerErrow);
                    form.setCompany(_company);
                }
                catch (Exception ex) {
                    loggerErrow.warn(ex.toString());
                }
            }
        });

        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_company == null)
                {
                    return;
                }
                canvasShow();
            }
        });

        buttonAddCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textBoxCollection.getText().isEmpty() || (!radioButtonMassive.isSelected()
                    && !radioButtonList.isSelected())) {
                    JOptionPane.showMessageDialog(null, "Не все данные заполнены");
                    return;
                }
                try {
                    CollectionType collectionType = CollectionType.None;
                    if (radioButtonMassive.isSelected()) {
                        collectionType = CollectionType.Massive;
                    } else if (radioButtonList.isSelected()) {
                        collectionType = CollectionType.List;
                    }
                    _storageCollection.AddCollection(textBoxCollection.getText(), collectionType);
                    RerfreshListBoxItems();
                    logger.info("Добавлена коллекция: " + textBoxCollection.getText());
                }
                catch (Exception ex) {
                    loggerErrow.error(ex.toString());
                }
            }
        });

        buttonRemoveCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxCollection.getSelectedIndex() < 0 || listBoxCollection.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null, "Коллекция не выбрана");
                    return;
                }
                int resultConfirmDialog = JOptionPane.showConfirmDialog(null,
                        "Удалить", "Удаление",
                        JOptionPane.YES_NO_OPTION);
                if (resultConfirmDialog == JOptionPane.NO_OPTION) return;
                try {
                    _storageCollection.DelCollection(listBoxCollection.getSelectedValue().toString());
                    RerfreshListBoxItems();
                    logger.info("Удалена коллекция: " + textBoxCollection.getText());
                }
                catch (Exception ex) {
                    loggerErrow.error(ex.toString());
                }
            }
        });

        buttonCreateCompany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listBoxCollection.getSelectedIndex() < 0 || listBoxCollection.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null, "Коллекция не выбрана");
                    return;
                }
                ICollectionGenericObjects<DrawingShip> collection =
                        _storageCollection.getCollectionObject(listBoxCollection.getSelectedValue().toString());
                if (collection == null) {
                    JOptionPane.showMessageDialog(null, "Коллекция не проинициализирована");
                    return;
                }
                switch (ComboBoxCollections.getSelectedItem().toString()) {
                    case "Хранилище":
                        _company = new ShipPortService(getWidth()-200, getHeight()-70,
                                collection);
                        break;
                }
                RerfreshListBoxItems();
            }
        });

        RemoveObjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_collectionRemoveObjects.empty())
                {
                    return;
                }
                DrawingShip ship = null;
                ship = _collectionRemoveObjects.pop();
                if (ship == null)
                {
                    return;
                }
                FormWarmlyShip form = new FormWarmlyShip("Теплоход", new Dimension(900,565));
                form.Init(ship);
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveFile();
            }
        });


        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadFile();
            }
        });

        saveCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save coll");
                SaveCollection();
            }
        });

        loadCollection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Load coll");
                LoadCollection();
            }
        });

        JLabel labelCollectionName = new JLabel("Название коллекции");
        ButtonGroup radiobuttonsGroup = new ButtonGroup();
        radiobuttonsGroup.add(radioButtonMassive);
        radiobuttonsGroup.add(radioButtonList);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadCollection);
        fileMenu.add(saveCollection);
        fileMenu.addSeparator();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        _canvasWarmlyShip.setBounds(0, 0, getWidth()-200, getHeight());
        labelCollectionName.setBounds(getWidth()-190, 10, 150, 20);
        textBoxCollection.setBounds(getWidth()-190, 32, 150, 25);
        radioButtonMassive.setBounds(getWidth()-190, 60, 75, 20);
        radioButtonList.setBounds(getWidth()-105, 60, 50, 20);
        buttonAddCollection.setBounds(getWidth()-190, 85, 150, 20);
        listBoxCollection.setBounds(getWidth()-190, 115, 150, 70);
        buttonRemoveCollection.setBounds(getWidth()-190, 195, 150, 20);
        ComboBoxCollections.setBounds(getWidth()-190, 235, 150, 20);
        buttonCreateCompany.setBounds(getWidth()-190, 260, 150, 20);
        CreateShipButton.setBounds(getWidth()-190, 295, 150, 30);
        MaskedTextField.setBounds(getWidth()-190,365,150,30);
        RemoveButton.setBounds(getWidth()-190, 400, 150, 30);
        GoToCheckButton.setBounds(getWidth()-190, 435, 150, 30);
        RandomButton.setBounds(getWidth()-190, 470, 150, 30);
        RemoveObjectsButton.setBounds(getWidth()-190, 505, 150, 30);
        RefreshButton.setBounds(getWidth()-190, getHeight()-100, 150, 30);

        setSize(dimension.width,dimension.height);
        setLayout(null);
        add(_canvasWarmlyShip);
        add(labelCollectionName);
        add(textBoxCollection);
        add(radioButtonMassive);
        add(radioButtonList);
        add(buttonAddCollection);
        add(listBoxCollection);
        add(buttonRemoveCollection);
        add(ComboBoxCollections);
        add(buttonCreateCompany);
        add(CreateShipButton);
        add(MaskedTextField);
        add(RemoveButton);
        add(GoToCheckButton);
        add(RandomButton);
        add(RemoveObjectsButton);
        add(RefreshButton);
        setVisible(true);
    }
    private String SaveWindow() {
        FileDialog fileDialog = new FileDialog(this, "Save File", FileDialog.SAVE);
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (directory == null || file == null) return null;
        return directory + file;
    }
    private void SaveFile() {
        String filename = SaveWindow();
        try {
            _storageCollection.SaveData(filename);
            JOptionPane.showMessageDialog(null, "Сохранено");
            logger.info("Сохранение в файл: " + filename);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ошибка сохранения");
            loggerErrow.error(ex.toString());
        }
    }
    private void SaveCollection() {
        String filename = SaveWindow();
        if (filename == null) {
            JOptionPane.showMessageDialog(null, "Файл не выбран");
            return;
        }
        if (listBoxCollection.getSelectedIndex() < 0 || listBoxCollection.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(null, "Коллекция не выбрана");
        }
        try {
            _storageCollection.SaveOneCollection(filename, listBoxCollection.getSelectedValue().toString());
            JOptionPane.showMessageDialog(null, "Коллекция сохранена");
            logger.info("Сохранение коллекции:" +
                    listBoxCollection.getSelectedValue().toString() + " в файл: " + filename);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ошибка сохранения");
            loggerErrow.error(ex.toString());
        }
    }
    private String LoadWindow() {
        FileDialog fileDialog = new FileDialog(this, "Save File", FileDialog.LOAD);
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (directory == null || file == null) return null;
        return directory + file;
    }
    private void LoadFile() {
        String filename = LoadWindow();
        try {
            _storageCollection.LoadData(filename);
            JOptionPane.showMessageDialog(null, "Загрузка прошла успешно");
            RerfreshListBoxItems();
            logger.info("Загрузка файла: " + filename);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Не загрузилось");
            loggerErrow.error(ex.toString());
        }
    }
    private void LoadCollection() {
        String filename = LoadWindow();
        try {
            _storageCollection.LoadOneCollection(filename);
            JOptionPane.showMessageDialog(null, "Коллекция загружена");
            RerfreshListBoxItems();
            logger.info("Загрузка коллекции: ");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Не загрузилось");
            loggerErrow.error(ex.toString());
        }
    }
    private void RerfreshListBoxItems()
    {
        DefaultListModel<String> list = new DefaultListModel<String>();
        for (String name : _storageCollection.Keys()) {
            if (name != "")
            {
                list.addElement(name);
            }
        }
        listBoxCollection.setModel(list);
    }
}
