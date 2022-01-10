package scanner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class DictionaryScanner {
    private final Scanner dictionaryScanner;

    public DictionaryScanner(Path filepath) throws IOException {
        dictionaryScanner = new Scanner(filepath, StandardCharsets.UTF_8);
    }

    public boolean hasNext() {
        return dictionaryScanner.hasNext();
    }

    public String nextWord() {
        return dictionaryScanner.next();
    }
}
