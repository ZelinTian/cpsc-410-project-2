package copied_java;


import java.util.ArrayList;
import java.util.List;

public class Log {
    List<String> class_call_relation = new ArrayList<>();

    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Log.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
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
                    return ste.getClassName();
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

        class_call_relation.add(trimed_caller_caller + "->" + trimed_caller);
    }

    public List<String> get_call_relation(){
        return class_call_relation;
    }

}
