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
            Scanner scanner = new Scanner(new ByteArrayInputStream(s.getBytes()));
            Parser parser = new Parser(scanner);
            parser.calculator=new Calculator();
            while(!"OFF".equals(s)){
                parser.Parse();
                System.out.println("Next calculation");
                s=br.readLine();
                scanner.buffer=new Buffer(new ByteArrayInputStream(s.getBytes()));
                scanner.Init();
                parser.scanner=scanner;
                parser.errors=new Errors();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
