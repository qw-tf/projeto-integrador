import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class GerarLogs {
  
    private static final Logger log = Logger.getLogger(GerarLogs.class.getName());

    static {
        try {
            // Cria o diretório "logs" se ele não existir
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Configura o FileHandler
            FileHandler fileHandler = new FileHandler("logs/GeralLogs.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
            log.setUseParentHandlers(false); // Não mostra no console, apenas no arquivo

        } catch (IOException e) {
            System.err.println("Erro ao configurar o logger: " + e.getMessage());
        }
    }

    public static void logAutomatic(String corpo) {
        log.info("Ação realizada -> " + corpo + " <- |");
    }
}
