package javacode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strs) {
            if (str.length() == 0) {
                stringBuilder.append("299");
            }

            for (int i = 0; i < str.length(); i++) {
                String s = String.valueOf((int) str.charAt(i));
                switch (s.length()) {
                    case 1 :
                        stringBuilder.append("00");
                        break;
                    case 2 :
                        stringBuilder.append("0");
                }
                stringBuilder.append(s);
            }
            stringBuilder.append("300");
        }
        return stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length()).toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        for (String st : s.split("300")) {
            if (st.equals("299")) {
                result.add("");
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < st.length(); i += 3) {
                sb.append((char) (Integer.parseInt(st.substring(i, i + 3))));
            }
            result.add(sb.toString());
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        Codec codec = new Codec();

        System.out.println(codec.encode(list));
    }
}