package javacode;

import java.util.HashMap;

public class ValidWordAbbr {
    HashMap<String, String> map;
    public ValidWordAbbr(String[] dictionary) {
        this.map = new HashMap<>();
        String st;

        for (String s : dictionary) {
            st = s.length() <= 2 ? s : s.charAt(0) + String.valueOf(s.length() - 2) + s.charAt(s.length() - 1);
            if (!this.map.containsKey(st)) {
                this.map.put(st, s);
            } else if (!this.map.get(st).equals("") && !this.map.get(st).equals(s)) {
                this.map.put(st, "");
            }
        }
    }

    public boolean isUnique(String word) {
        return this.map.getOrDefault(word.length() <= 2 ? word : word.charAt(0) + String.valueOf(word.length() - 2) + word.charAt(word.length() - 1), word).equals(word);
    }
}
