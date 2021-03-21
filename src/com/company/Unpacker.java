package com.company;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unpacker {

    private static String str = "";
    private static int index = 0;

    public static String unpack(String expression) {
        if (!isValid(expression))
            return null;

        index = 0;

        return analyze().toString();
    }

    public static boolean isValid(String expression) {
        str = expression;
        //проверка на литинские буквы, цифры и квадратные скобки
        Pattern pattern = Pattern.compile("^[\\d\\w\\[\\]]+$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {

            char ch = str.charAt(i);

            if (Character.isDigit(ch)) {
                String number = String.valueOf(readNumber(i));
                char charAfterNumber = str.charAt(i + number.length());
                if (!isOpenBracket(charAfterNumber)) {
                    return false;
                }

                i += number.length();
            }

            switch (ch) {
                case '[' : stack.push(ch);
                    if (!Character.isDigit(str.charAt(i-1)))
                        return false;
                    break;

                case ']' :
                    if (!stack.isEmpty()) {
                        char chx = stack.pop();
                        if (chx != ch)
                            return false;
                    }
                    break;

                default:
                    break;
            }
        }

        if (!stack.isEmpty())
            return false;

        return true;
    }

    private static StringBuilder analyze() {
        StringBuilder result = new StringBuilder();

        while (index < str.length()) {

            if (Character.isDigit(str.charAt(index))) {

                int count = readNumber(index);
                index += String.valueOf(count).length();

                if (isOpenBracket(str.charAt(index))) {
                    index++;

                    StringBuilder sb = new StringBuilder(analyze());
                    for (int i = 0; i < count; i++)
                        result.append(sb);

                }

            } else if (Character.isLetter(str.charAt(index))) {

                String word = readWord(index);
                index += word.length();
                result.append(word);

            } else if (isCloseBracket(str.charAt(index))) {

                index++;
                return result;

            } else {
//                throw new Exception("SoMeThInGwEnTwRoNg :(");
            }

        }
        return result;
    }

    private static int readNumber(int index) {
        StringBuilder n = new StringBuilder();

        for (int i = index; Character.isDigit(str.charAt(i)); i++)
            n.append(str.charAt(i));


        return Integer.parseInt(n.toString());
    }

    private static int readNumber(String s) {
        StringBuilder n = new StringBuilder();

        for (int i = 0; Character.isDigit(s.charAt(i)); i++)
            n.append(s.charAt(i));


        return Integer.parseInt(n.toString());
    }

    private static String readWord(int index) {
        StringBuilder n = new StringBuilder();

        for (int i = index; i < str.length() && Character.isLetter(str.charAt(i)); i++)
            n.append(str.charAt(i));

        return n.toString();
    }

    private static boolean isOpenBracket(char ch) {
        return ch == '[';
    }

    private static boolean isCloseBracket(char ch) {
        return ch == ']';
    }

}
