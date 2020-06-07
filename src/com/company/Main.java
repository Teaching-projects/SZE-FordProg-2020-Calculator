package com.company;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Calculator");
        System.out.println("Turn on: ON");
        System.out.println("Turn off: OFF");
        try {
            String s=br.readLine();
            while(!"ON".equals(s)){
                System.out.println("Type ON");
                s=br.readLine();
            }
            System.out.println("Calculator turned ON");
            System.out.println("You can use '+','-','*','/','(',')'");
            System.out.println("Use last result in calculation: ans");
            System.out.println("Use last result as expression: ANS");
            System.out.println("Can not handle order of operations, so it is necessary to use parenthesis");
            s=br.readLine();
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
            System.out.println("Calculator turned OFF");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
