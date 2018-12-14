package com.sitech.esb.mouse;

public class Test {


    public static void main(String args[]){
        String[] values = new String[]{"1","2","3","4","5","6","7","8"};
        int size = 4;
        String[] cols = new String[values.length-size];
        for (int i = size; i < values.length; i++) {
            cols[i-size] = values[i];
        }
        for (String s : cols){
            System.out.println(s);
        }
    }


}
