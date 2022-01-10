package mapping;

import spell.INode;
import spell.ITrie;

public class Trie implements ITrie {
    private Node root;

    private void constructTrie() {
        // TODO
    }

    public void add(String word) {
        // TODO
        Node current = root;
//        for (int i = 0; i < word.length(); i++) {
//            char c = word.charAt(i);
//            if (!current.containsChild(c)) {
//                current.addChild(c);
//            }
//            current = current.getChild(c);
//        }
//        current.setEndOfWord(true);
    }

    @Override
    public INode find(String word) {
        // TODO
        return null;
    }

    @Override
    public int getWordCount() {
        // TODO
        return 0;
    }

    @Override
    public int getNodeCount() {
        // TODO
        return 0;
    }

    public String toString() {
        // TODO
        return "Trie";
    }

    @Override
    public int hashCode() {
        // TODO
        return 0;
    }

    public boolean equals(Object o) {
        // TODO
        return true;
    }
}
