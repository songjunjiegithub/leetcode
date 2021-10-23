package javacode;

import java.util.HashMap;
import java.util.HashSet;

class Trie {
    TrieTree tree;

    static class TrieTree {
        TrieTree[] next;
        Boolean isOver;

        public TrieTree() {
        }

        public TrieTree(TrieTree[] next) {
            this.next = next;
        }
    }
    /** Initialize your data structure here. */
    public Trie() {
        this.tree = new TrieTree();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieTree ele = this.tree;
        for (int i = 0; i < word.length(); i++) {
            if (ele.next == null) ele.next = new TrieTree[26];
            if (ele.next[word.charAt(i) - 'a'] == null) ele.next[word.charAt(i) - 'a'] = new TrieTree();
            ele = ele.next[word.charAt(i) - 'a'];
        }
        ele.isOver = true;
    }

    public boolean startsWith(String prefix, TrieTree[] ele) {
        for (int i = 0; i < prefix.length(); i++) {
            if  (ele[0].next == null || ele[0].next[prefix.charAt(i) - 'a'] == null) return false;
            ele[0] = ele[0].next[prefix.charAt(i) - 'a'];
        }

        return true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieTree[] ele = new TrieTree[1];
        ele[0] = this.tree;
        return startsWith(word, ele) && ele[0].isOver != null;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return startsWith(prefix, new TrieTree[]{this.tree});
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 True
        System.out.println(trie.search("app"));     // 返回 False
        System.out.println(trie.startsWith("app")); // 返回 True
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 True
    }
}
