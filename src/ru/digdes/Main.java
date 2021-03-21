package ru.digdes;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static final String EXPRESSIONS_FILE_PATH = "src/ru/digdes/expressions.txt";

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            Path path = Paths.get(EXPRESSIONS_FILE_PATH);
            Files.lines(path).forEach(s -> System.out.println(s +  " : " + Unpacker.unpack(s)));
        }
        else {
            String expression = args[0];

            if (!Unpacker.isValid(expression)) {
                System.out.println(expression + " is not valid");
                return;
            }

            System.out.println(Unpacker.unpack(expression));
        }

    }

}
