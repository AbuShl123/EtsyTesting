package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream("config.properties");
            properties.load(file);
        } catch (IOException ignored) {}
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }
}
