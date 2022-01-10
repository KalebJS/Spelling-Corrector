package mapping;

import spell.INode;
import spell.ITrie;

public class Trie implements ITrie {
    private Node root;
    private int wordCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
    }

    public void add(String word) {
        root.add(word);
        wordCount++;
    }

    @Override
    public INode find(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (current.getChildren()[index] == null) {
                return null;
            } else {
                current = current.children[index];
            }
        }
        return current;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        int nodeCount = 1;
        nodeCount += root.getChildNodeCount();
        return nodeCount;
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
