package mapping;

import spell.INode;

public class Node implements INode {
    private int frequency;
    public Node[] children;

    public Node() {
        frequency = 0;
        children = new Node[26];
    }

    @Override
    public int getValue() {
        return frequency;
    }

    @Override
    public void incrementValue() {
        frequency++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }

    public void add(String word) {
        // if the word is empty, then this word already in the tree
        if (word.length() == 0) {
            this.incrementValue();
            return;
        }
        int index = word.charAt(0) - 'a';
        // if the child is null, then create a new node
        if (children[index] == null) {
            children[index] = new Node();
        }
        children[index].add(word.substring(1));
    }

    public int getChildNodeCount() {
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (this.children[i] != null) {
                count++;
                count += this.children[i].getChildNodeCount();
            }
        }
        return count;
    }
}
