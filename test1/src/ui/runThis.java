package ui;

import ast.Add;
import libs.Sub;

/**
 * Created by zelin on 2018/11/23.
 */
public class runThis {
    public static void runThis() {
        int a = 2;
        int b = 3;
        Add c = new Add();
        Sub d = new Sub();
        System.out.println(c.addInt(a,b));
        System.out.println(d.subInt(b,a));
    }
    public static void main(String[] args){
        System.out.println("test app go go go!!");
        runThis();
    }
}