package copied_java;


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
        runThis();
    }
}
