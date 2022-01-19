package mapping;

import spell.INode;
import spell.ITrie;

public class Trie implements ITrie {
    private final Node root;
    private int wordCount;

    public Trie() {
        root = new Node();
        wordCount = 0;
    }

    @Override
    public void add(String word) {
        root.add(word);
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
        if (current.getValue() > 0) {
            return current;
        } else {
            return null;
        }
    }

    @Override
    public int getWordCount() {
        return root.getWordCount();
    }

    @Override
    public int getNodeCount() {
        int nodeCount = 1;
        nodeCount += root.getChildNodeCount();
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        root.generateString(outputString, "");
        return outputString.toString();
    }

    @Override
    public int hashCode() {
        INode[] children = root.getChildren();
        int first_index = 0;
        for (int i = 0; i < 26; i++) {
            if (children[i] != null) {
                first_index = i;
                break;
            }
        }
        int numNodes = this.getNodeCount();
        int wordCount = this.getWordCount();
        return first_index * numNodes * wordCount;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object otherTrie) {
        if (otherTrie == null) {
            return false;
        }

        if (this == otherTrie) {
            return true;
        }

        Node otherRoot = ((Trie) otherTrie).getRoot();
        return root.equalsHelper(otherRoot);
    }
}
