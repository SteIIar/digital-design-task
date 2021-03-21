package com.company;


import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    public static void main(String[] args) throws Exception {
	// write your code here
//        gsdf2[5[h]h]fsadf5[f4[g]]

        Unpacker.unpack("2[f]g[a]")
    }

    static StringBuilder str = new StringBuilder();
    static int index = 0;


    public static boolean isValid(String str) {

        //проверка на литинские буквы, цифры и квадратные скобки
        Pattern pattern = Pattern.compile("^[\\d\\w\\[\\]]+$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            System.out.println("NON");
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {


            char ch = str.charAt(i);

            if (Character.isDigit(ch)) {
                String number = String.valueOf(readNumber(i));
                if (!isOpenBracket(str.charAt(i + number.length()))) {
                    System.out.println("Expected open bracket");
                    return false;
                }
                i+=number.length();
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

    public static StringBuilder analyze() throws Exception {
        if (!isValid(str.toString()))
            return null;

        StringBuilder result = new StringBuilder();

        int count = 1;
        while (index < str.length()) {
            if (Character.isDigit(str.charAt(index))) {

                count = readNumber(index);
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
                throw new Exception("SoMeThInGwEnTwRoNg :(");
            }

        }
        return result;
    }

    public static int readNumber(int index) {
        StringBuilder n = new StringBuilder();
        int i = index;
        while (Character.isDigit(str.charAt(i))) {
            n.append(str.charAt(i));
            i++;
        }
        return Integer.parseInt(n.toString());
    }

    public static String readWord(int index) {
        StringBuilder n = new StringBuilder();

        for (int i = index; i < str.length() && Character.isLetter(str.charAt(i)); i++)
            n.append(str.charAt(i));

        return n.toString();
    }

    public static boolean isOpenBracket(char ch) {
        return ch == '[';
    }

    public static boolean isCloseBracket(char ch) {
        return ch == ']';
    }

}
