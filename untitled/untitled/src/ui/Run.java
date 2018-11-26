package ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zelin on 2018/11/23.
 */
public class Run {
    public static void run_copied_java() {
        try {
//          runProcess("pwd");
            System.out.println("**********");
            runProcess("pwd");
            runProcess("cd copied_java");
            runProcess("pwd");
//            runProcess("javac -cp copied_java/src /Users/rex/Desktop/cpsc-410-project-2/copied_java/src/ui/runThis.java");
            runProcess("javac copied_java/runThis.java");
            System.out.println("**********");
//            runProcess("java -cp copied_java/src /Users/rex/Desktop/cpsc-410-project-2/copied_java/src/ui/runThis.java");
            runProcess("java copied_java/runThis");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void main(String[] args) {
        run_copied_java();
    }
}