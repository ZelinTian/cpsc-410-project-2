package ast;

import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class SET extends STATEMENT {
    String name;
    String value=null;
    USE varuse=null;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("set");
        name = tokenizer.getNext();
        varuse = (USE) STATEMENT.getSubStatement();
        if (varuse==null){
            value = tokenizer.getNext();
        }
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        if (varuse!=null){
            value=varuse.evaluate();
        }
        System.out.println("Setting "+name+"."+getScope()+" to "+value);
        Main.symbolTable.put(name+"."+getScope(),value);
        return null;
    }

    @Override
    public void visitASTNode() {
        printNode();
        if (varuse!=null){
            printEdgeTo();
            varuse.visitASTNode();
        }
    }
}
