package pl.mbcode.nn.bank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.NONE)
public class FileReader {

    private static final String RESOURCE_PATH_PREFIX = "./src/test/resources/";

    public static String readResource(String resourcePath) {
        return FileReader.readFile(RESOURCE_PATH_PREFIX + resourcePath);
    }

    private static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return "";
        }
    }
}
