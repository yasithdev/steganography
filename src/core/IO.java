package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IO {
    /**
     * Returns the content of File as a String
     *
     * @param filePath Path of the File as a String
     * @return Contents of the File as a String
     * @throws IOException If file is unavailable or cannot be read
     */
    public static byte[] readFileBytes(Path filePath) throws IOException {
        if (Files.notExists(filePath)) throw new IOException("File not found");
        return Files.readAllBytes(filePath);
    }

    /**
     * Create or replace a file, and writes the content to it
     *
     * @param filePath Path of the File as a String
     * @param content  Content to write into the File as a String
     * @throws IOException If file is unavailable or cannot be written into
     */
    public static void writeFileBytes(Path filePath, byte[] content) throws IOException {
        if (Files.notExists(filePath.getParent())) Files.createDirectory(filePath.getParent());
        Files.write(filePath, content);
    }
}