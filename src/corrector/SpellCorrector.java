package corrector;

import mapping.Trie;
import scanner.DictionaryScanner;
import spell.ISpellCorrector;

import java.io.IOException;
import java.nio.file.Path;

public class SpellCorrector implements ISpellCorrector {
    Trie dictionary;
    DictionaryScanner scanner;
    Path dictionaryFilepath;

    public SpellCorrector () {
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        dictionaryFilepath = Path.of(dictionaryFileName);
        scanner = new DictionaryScanner(dictionaryFilepath);
        dictionary = new Trie();

        while (scanner.hasNext()) {
            String word = scanner.nextWord();
            dictionary.add(word);
        }


    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        // TODO
        return null;
    }
}
