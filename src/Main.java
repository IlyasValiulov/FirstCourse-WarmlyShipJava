import org.apache.logging.log4j.LogManager;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        org.apache.logging.log4j.Logger logger1 = LogManager.getLogger("logging.file1");
        org.apache.logging.log4j.Logger logger2 = LogManager.getLogger("logging.file2");
        FormShipCollection form = new FormShipCollection("Коллекция судов",
                new Dimension(1100, 648), logger1, logger2);
        form.Init();
    }
}