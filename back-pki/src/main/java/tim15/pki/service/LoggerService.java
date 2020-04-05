package tim15.pki.service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {
    public void print(String text) {
        String nameOfSubsystem = "CertificateServicesLog";
        Logger logger = Logger.getLogger(nameOfSubsystem);
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("./MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info(text);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
