package tot.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String readFileAsString(String filePath) throws IOException {
    	return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
