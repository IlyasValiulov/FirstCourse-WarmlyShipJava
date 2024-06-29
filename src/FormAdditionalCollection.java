import CollectionAdditionalObjects.AdditionalCollections;
import CollectionGenericObjects.AbstractCompany;
import DiffetentsDrawingDecks.DrawingDecksType1;
import DiffetentsDrawingDecks.DrawingDecksType2;
import DiffetentsDrawingDecks.DrawingDecksType3;
import DiffetentsDrawingDecks.IDifferentDecks;
import DrawingShip.DrawingShip;
import DrawingShip.DrawingWarmlyShip;
import DrawingShip.CanvasWarmlyShip;
import Entities.EntityShip;
import Entities.EntityWarmlyShip;
import Exceptions.CollectionOverflowException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static DrawingShip.ExtentionDrawningShip.GetDataForSave;

public class FormAdditionalCollection extends JFrame {
    public DrawingShip drawingShip = null;
    private AbstractCompany company = null;
    private CanvasWarmlyShip canvasship = new CanvasWarmlyShip();
    private AdditionalCollections<EntityShip, IDifferentDecks> additionalCollection = null;
    private Random random = new Random();
    private JButton buttonGenerate = new JButton("Создать");
    private JList<String> listEntity = new JList<String>();
    private JList<String> listDecks = new JList<String>();
    private org.apache.logging.log4j.Logger logger;
    private org.apache.logging.log4j.Logger loggerErrow;
    public FormAdditionalCollection(org.apache.logging.log4j.Logger logger,
                                    org.apache.logging.log4j.Logger logger2) {
        setTitle("Random ship");
        setMinimumSize(new Dimension(650,310));
        this.logger = logger;
        this.loggerErrow = logger2;
        additionalCollection = new AdditionalCollections<EntityShip, IDifferentDecks>(3,
                (Class) EntityShip.class, (Class) IDifferentDecks.class);
        AddEntities();
        AddDecks();
        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drawingShip = additionalCollection.CreateAdditionalCollectionShip();
                    drawingShip.SetPictureSize(getWidth(), getHeight());
                    drawingShip.SetPosition(50, 50);
                    canvasship._drawingShip = drawingShip;
                    canvasship.repaint();
                    DrawingShip copyShip;
                    if (drawingShip instanceof DrawingWarmlyShip)
                        copyShip = new DrawingWarmlyShip((EntityWarmlyShip) drawingShip.EntityShip, drawingShip.drawingDecks);
                    else
                        copyShip = new DrawingShip(drawingShip.EntityShip, drawingShip.drawingDecks);
                    company._collection.Insert(copyShip);
                    FormShipCollection.canvasShow();
                    logger.info("Добавлен объект: " + GetDataForSave(copyShip));
                    String[] data1 = new String[additionalCollection.CountEntities];
                    for (int i = 0; i < additionalCollection.CountEntities; i++) {
                        EntityShip entity = additionalCollection._collectionEntity[i];
                        data1[i] = ToString(entity);
                    }
                    String[] data2 = new String[additionalCollection.CountDecks];
                    for (int i = 0; i < additionalCollection.CountDecks; i++) {
                        IDifferentDecks decks = additionalCollection._collectionDecks[i];
                        data2[i] = ToString(decks);
                    }
                    listEntity.setListData(data1);
                    listDecks.setListData(data2);
                }
                catch (CollectionOverflowException ex) {
                    loggerErrow.warn(ex.toString());
                }
                catch (Exception ex) {
                    loggerErrow.fatal("Ошибка в выводу форме FormAdditionalCollection");
                }
            }
        });
        buttonGenerate.setBounds(450, 10, 100, 50);
        add(buttonGenerate);
        listEntity.setBounds(10,200,300,60);
        listDecks.setBounds(320,200,300,60);
        add(listEntity);
        add(listDecks);
        add(canvasship);
        setVisible(true);
    }
    private String ToString(EntityShip entity) {
        String str = "";
        if (entity instanceof EntityWarmlyShip) str += "EntityWarmlyShip ";
        else str += "EntityShip ";
        str += entity.getBodyColor().toString();
        return str;
    }
    private String ToString(IDifferentDecks decks) {
        if (decks == null || decks.getNumberOfDecks() == null)
            return "Dont have decks";
        String str = "Deck ";
        if (decks instanceof DrawingDecksType1) str += "Type 1 ";
        else if (decks instanceof DrawingDecksType2) str += "Type 2 ";
        else str += "Type 3 ";
        str += decks.getNumberOfDecks().toString();
        return str;
    }
    public void AddEntities() {
        for (int i = 0; i < additionalCollection.CountEntities; i++) {
            random = new Random();
            int speed = random.nextInt(100, 300);
            double weight = random.nextInt(1000, 3000);
            Color bodycolor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            EntityShip entity;
            if (random.nextBoolean()) {
                entity = new EntityShip(speed, weight, bodycolor);
            }
            else {
                Color additionalcolor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                boolean sheeppipes = random.nextBoolean();
                boolean fueltaln = random.nextBoolean();
                entity = new EntityWarmlyShip(speed, weight, bodycolor,
                        additionalcolor, sheeppipes, fueltaln);
            }
            additionalCollection.Insert(entity);
        }
    }
    public void AddDecks() {
        for (int i = 0; i < additionalCollection.CountDecks; i++) {
            random = new Random();
            Integer numberOfDecks = random.nextInt(0, 4);
            IDifferentDecks drawingDecks = null;
            switch (random.nextInt(0,4)) {
                case 1:
                    drawingDecks = new DrawingDecksType1();
                    break;
                case 2:
                    drawingDecks = new DrawingDecksType2();
                    break;
                case 3:
                    drawingDecks = new DrawingDecksType3();
                    break;
                default:
                    numberOfDecks = null;
                    break;
            }
            if (drawingDecks != null) drawingDecks.setNumberOfDecks(numberOfDecks);
            additionalCollection.Insert(drawingDecks);
        }
    }
    void setCompany(AbstractCompany company) {
        this.company = company;
    }
}
