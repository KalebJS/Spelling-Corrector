package corrector;

import mapping.Trie;
import scanner.DictionaryScanner;
import spell.ISpellCorrector;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Vector;

public class SpellCorrector implements ISpellCorrector {
    private Trie dictionary;

    public SpellCorrector() {
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Path dictionaryFilepath = Path.of(dictionaryFileName);
        DictionaryScanner scanner = new DictionaryScanner(dictionaryFilepath);
        dictionary = new Trie();

        while (scanner.hasNext()) {
            String word = scanner.nextWord();
            dictionary.add(word);
        }
    }

    private Vector<String> getDeletionVariants(Vector<String> candidates) {
        Vector<String> deletionVariants = new Vector<>();
        for (String word : candidates) {
            for (int i = 0; i < word.length(); i++) {
                String deletionVariant = word.substring(0, i) + word.substring(i + 1);
                deletionVariants.add(deletionVariant);
            }
        }
        return deletionVariants;
    }

    private Vector<String> getTranspositionVariants(Vector<String> candidates) {
        Vector<String> transpositionVariants = new Vector<>();
        for (String word : candidates) {
            for (int i = 0; i < word.length() - 1; i++) {
                String transpositionVariant = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2);
                transpositionVariants.add(transpositionVariant);
            }
        }
        return transpositionVariants;
    }

    private Vector<String> getAlterationVariants(Vector<String> candidates) {
        Vector<String> alterationVariants = new Vector<>();
        for (String word : candidates) {
            for (int i = 0; i < word.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c != word.charAt(i)) {
                        String alterationVariant = word.substring(0, i) + c + word.substring(i + 1);
                        alterationVariants.add(alterationVariant);
                    }
                }
            }
        }
        return alterationVariants;
    }

    private Vector<String> getInsertionVariants(Vector<String> candidates) {
        Vector<String> insertionVariants = new Vector<>();
        for (String word : candidates) {
            for (int i = 0; i < word.length() + 1; i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    String insertionVariant = word.substring(0, i) + c + word.substring(i);
                    insertionVariants.add(insertionVariant);
                }
            }
        }
        return insertionVariants;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        final int MAX_EDIT_DISTANCE = 1;
        Vector<String> candidates = new Vector<>(1);
        candidates.add(inputWord);
        for (int i = 0; i < MAX_EDIT_DISTANCE; i++) {
            Vector<String> deletionVariants = getDeletionVariants(candidates);
//            System.out.println("Deletion variants: " + deletionVariants.size());
            Vector<String> transpositionVariants = getTranspositionVariants(candidates);
//            System.out.println("Transposition variants: " + transpositionVariants.size());
            Vector<String> alterationVariants = getAlterationVariants(candidates);
//            System.out.println("Alteration variants: " + alterationVariants.size());
            Vector<String> insertionVariants = getInsertionVariants(candidates);
//            System.out.println("Insertion variants: " + insertionVariants.size());

            candidates.clear();

            candidates.addAll(deletionVariants);
            candidates.addAll(transpositionVariants);
            candidates.addAll(alterationVariants);
            candidates.addAll(insertionVariants);
//            System.out.println("Candidates: " + candidates.size());

        }
        return null;
    }
}
