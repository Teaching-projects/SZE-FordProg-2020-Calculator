package com.company;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Calculator");
        System.out.println("You can use '+','-','*','/'");
        System.out.println("Use last result in calculation: ans");
        System.out.println("Exit: OFF");
        try {
            String s=br.readLine();
            while(!"OFF".equals(s)){
                Scanner scanner = new Scanner(new ByteArrayInputStream(s.getBytes()));
                Parser parser = new Parser(scanner);
                parser.calculator=new Calculator();
                parser.Parse();
                System.out.println("Next calculation");
                s=br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
