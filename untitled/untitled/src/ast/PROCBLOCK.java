package ast;

import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PROCBLOCK extends STATEMENT {
    List<STATEMENT> statements;
    DEC param;
    String retval;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("with");
        param = new DEC();
        param.parse();
        tokenizer.getAndCheckNext("start");
        statements = new ArrayList<>();
        while (!tokenizer.checkToken("return")){
            STATEMENT s = STATEMENT.getSubStatement();
            statements.add(s);
            s.parse();
        }
        tokenizer.getAndCheckNext("return");
        retval = tokenizer.getNext();
        tokenizer.getAndCheckNext("end");
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        return null;
    }

    @Override
    public void visitASTNode() {
        printNode();
        printEdgeTo();
        param.visitASTNode();
        for (STATEMENT s : statements){
            printEdgeTo();
            s.visitASTNode();
        }
    }

    public String evaluate(String arg) throws FileNotFoundException, UnsupportedEncodingException {
        enterScope(this.toString());
        param.evaluate();
        Main.symbolTable.put(param.getName()+"."+getScope(),arg);
        for (STATEMENT s : statements){
            s.evaluate();
        }
        leaveScope();
        return retval;
    }

    public void staticCallGraphNoTunnel(){
        for (STATEMENT s : statements){
            s.staticCallGraphNoTunnel();
        }
    }

    public void staticCallGraphWithTunnel(String procname){
        for (STATEMENT s : statements){
            s.staticCallGraphWithTunnel(procname);
        }
    }

}
