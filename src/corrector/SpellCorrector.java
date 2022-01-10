package corrector;

import mapping.Trie;
import scanner.DictionaryScanner;
import spell.INode;
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
        final int MAX_EDIT_DISTANCE = 2;
        Vector<String> candidates = new Vector<>(1);
        candidates.add(inputWord);
        for (int i = 0; i < MAX_EDIT_DISTANCE; i++) {
            Vector<String> deletionVariants = getDeletionVariants(candidates);
            Vector<String> transpositionVariants = getTranspositionVariants(candidates);
            Vector<String> alterationVariants = getAlterationVariants(candidates);
            Vector<String> insertionVariants = getInsertionVariants(candidates);

            candidates.clear();

            candidates.addAll(deletionVariants);
            candidates.addAll(transpositionVariants);
            candidates.addAll(alterationVariants);
            candidates.addAll(insertionVariants);
            candidates.sort(String::compareTo);

            Vector<String> matches = new Vector<>();
            for (String candidate : candidates) {
                if (dictionary.find(candidate) != null) {
                    matches.add(candidate);
                }
            }
            if (matches.size() > 0) {
                String topMatch = matches.get(0);
                INode topMatchNode = dictionary.find(matches.get(0));
                for (String match : matches) {
                    INode matchNode = dictionary.find(match);
                    if (matchNode.getValue() > topMatchNode.getValue()) {
                        topMatch = match;
                        topMatchNode = matchNode;
                    }
                }
                return topMatch;
            }
        }
        return null;
    }
}
