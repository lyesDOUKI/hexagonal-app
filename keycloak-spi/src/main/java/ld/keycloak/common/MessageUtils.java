package ld.keycloak.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageUtils {
    private static final Logger LOG = Logger.getLogger(MessageUtils.class.getName());
    private static final Properties props = new Properties();
    private static final String DEFAULT_MESSAGE_FILE_PATH = "/opt/keycloak/messages/mesMessagesDomain.properties";
    private static File messageFile;
    private static long lastModifiedTime = 0;

    static {
        String messageFilePath = System.getenv("MESSAGE_FILE_PATH");
        if (messageFilePath == null || messageFilePath.isEmpty()) {
            messageFilePath = DEFAULT_MESSAGE_FILE_PATH;
        }
        messageFile = new File(messageFilePath);

        if (!messageFile.exists()) {
            LOG.severe("Message file not found: " + messageFile.getPath());
            throw new RuntimeException("Failed to load message file from: " + messageFilePath);
        }
        loadMessages();
        startFileWatcher();
    }

    private static void loadMessages() {
        try (InputStream inputStream = new FileInputStream(messageFile)) {
            Properties newProps = new Properties();
            newProps.load(inputStream);
            //on sychronise cette partie seulement pour etre safe au niveau de l'accès à "props"
            synchronized (props) {
                props.clear();
                props.putAll(newProps);
            }
            LOG.info("Messages successfully loaded from: " + messageFile.getPath());
        } catch (IOException e) {
            LOG.severe("Failed to load message file: " + e.getMessage());
        }
    }

    public static String getMessage(String key) {
        return props.getProperty(key, "No message found for key: " + key);
    }


    /**
     * Permets de surveiller le fichier des messages et de le recharger en cas de modifications
     */
    private static void startFileWatcher() {
        Thread watcherThread = new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                Path parentDir = messageFile.getParentFile().toPath();
                parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take(); // on attend un évenement sur le répértoire
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (messageFile.getName().equals(changed.getFileName().toString())) {
                            LOG.log(Level.INFO,"File {0} has been modified. Reloading messages...", changed);
                            loadMessages(); //on recharge le fichier
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                LOG.severe("Error in file watcher: " + e.getMessage());
            }
        });

        watcherThread.setDaemon(true);
        watcherThread.start();
    }
}
