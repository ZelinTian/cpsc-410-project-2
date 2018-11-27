package copied_java;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Log {
    List<String> class_call_relation = new ArrayList<>();
    private static String dest_path = "/Users/rex/Desktop/cpsc-410-project-2/copied_java/logfile.txt";
    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName().replaceAll("\\s","");
            }
        }
        return null;
    }
    private static String getCallerCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                if (callerClassName==null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste.getClassName().replaceAll("\\s","");
                }
            }
        }
        return null;
    }
    public void log (){
        String caller = getCallerClassName();
        String caller_caller = getCallerCallerClassName();
        String trimed_caller = null;
        String trimed_caller_caller = null;

        if (caller != null){
            int last_i= caller.split("\\.").length - 1;
            trimed_caller = caller.split("\\.")[last_i];
        }
        if (caller_caller != null){
            int last_i2= caller_caller.split("\\.").length - 1;
            trimed_caller_caller = caller_caller.split("\\.")[last_i2];
        }

        File read_file = new File(dest_path);
        File destFile = new File(dest_path);
        List<String> copied_relation = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(read_file)));
            String currentLine = reader.readLine();
            while(currentLine != null){
                copied_relation.add(currentLine);
                currentLine = reader.readLine();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
            for (int i = 0; i <= copied_relation.size();i++){
                if (i == copied_relation.size()){
                    System.out.println("close file");
                    writer.write(trimed_caller_caller + "->" + trimed_caller + System.getProperty("line.separator"));
                    writer.close();
                } else{
                    writer.write( copied_relation.get(i) + System.getProperty("line.separator"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public List<String> get_call_relation(){
        File read_file = new File(dest_path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(String.valueOf(read_file)));
            String currentLine = reader.readLine();
            while(currentLine != null && currentLine != "") {
                if (!class_call_relation.contains(currentLine.trim())){
                    class_call_relation.add(currentLine.replaceAll("\\s+",""));
                }
                currentLine = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return class_call_relation;
    }

}
