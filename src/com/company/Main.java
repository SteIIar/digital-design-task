package com.company;


public class Main {


    public static void main(String[] args) throws Exception {
	// write your code here
        String expression = args[0];
        if (Unpacker.isValid(expression)) {
            System.out.println(expression + " is not valid");
            return;
        }

        System.out.println(Unpacker.unpack(expression));

    }

}
