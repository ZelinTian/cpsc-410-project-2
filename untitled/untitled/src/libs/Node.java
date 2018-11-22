package libs;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public abstract class Node {
    protected static Tokenizer tokenizer = Tokenizer.getTokenizer();
    static protected PrintWriter writer;
    static private LinkedList<String> scope = new LinkedList();

    abstract public void parse();
    abstract public String evaluate() throws FileNotFoundException, UnsupportedEncodingException;
    public static void enterScope(String s){
        System.out.println("Entering scope "+s);
        scope.addFirst(s);
    }
    public static void leaveScope(){
        scope.removeFirst();
    }
    public static String getScope(){
        return scope.getFirst().toString();
    }
    public static String getGlobal(){
        return scope.getLast().toString();
    }
    public abstract void visitASTNode();
    public abstract void staticCallGraphNoTunnel();
    public abstract void staticCallGraphWithTunnel(String tunnel);
    protected void startGraph(String filename) {
        try {
            writer = new PrintWriter(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        writer.println("Digraph G {\nrankdir=LR");
    }
    protected void endGraph(){
        writer.println("}");
        writer.close();
    }
    protected void printNode(){
        writer.println(this);
    }
    protected void printNode(String s){
        writer.println(this+"_"+s);
    }

    protected void printEdgeTo(){
        writer.print(this+"->");
    }



    public String toString(){
        String name=getClass().getName().replace(".", "_");
        return name +"_"+ Integer.toHexString(hashCode());
    }

}
