package com.company;

public class Calculator {
    private Double ans;
    public Calculator(){}

    public void showResult(double x){
        ans=x;
        System.out.println(x);
    }

    public double getAns() {
       return ans==null?0.0:ans;
    }
}
