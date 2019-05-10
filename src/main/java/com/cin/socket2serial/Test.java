package com.cin.socket2serial;

public class Test {

    public static void main(String[] args) {
        String text = "关关雎鸠，在河之洲。窈窕淑女，君子好逑。" +
                "参差荇菜，左右流之。窈窕淑女，寤寐求之。" +
                "求之不得，寤寐思服。悠哉悠哉，辗转反侧。" +
                "参差荇菜，左右采之。窈窕淑女，琴瑟友之。" +
                "参差荇菜，左右芼之。窈窕淑女，钟鼓乐之。";
        String leftBlank = "1111";
        String rightBlank = "2222";
        String[] spilt = spilt(text, 5);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spilt.length; i++) {
            String str = spilt[i];
            sb.append(leftBlank).append(str).append(rightBlank).append("\n");
        }
        System.out.println(sb.toString());
        System.out.println(" ".length());

        StringBuilder builder = new StringBuilder(text);
        char c = builder.charAt(2);
        System.out.println(c);
        System.out.println(builder.length());
    }

    public static int lengthOfGBK(String value) {
        if (value == null)
            return 0;
        StringBuilder buff = new StringBuilder(value);
        int length = 0;
        String stmp;
        for (int i = 0; i < buff.length(); i++) {
            stmp = buff.substring(i, i + 1);
            try {
                stmp = new String(stmp.getBytes("utf8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (stmp.getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

    private static String[] spilt(String text, int len) {
        int spiltNum = text.length() / len + (text.length() % len == 0 ? 0 : 1);
        String[] subs = new String[spiltNum];
        int index = 0;
        for (int i = 0; i < text.length(); i = i + len) {
            int subLen = i + len;
            if (subLen > text.length()) {
                subLen = text.length();
            }
            subs[index] = text.substring(i, subLen);
            index++;
        }
        return subs;
    }

}
