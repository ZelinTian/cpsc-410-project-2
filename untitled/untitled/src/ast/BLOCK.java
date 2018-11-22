package ast;


import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BLOCK extends STATEMENT {
    List<STATEMENT> statements;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("start");
        statements = new ArrayList<>();
        while (!tokenizer.checkToken("end")){
            STATEMENT s = STATEMENT.getSubStatement();
            statements.add(s);
            s.parse();
        }
        tokenizer.getAndCheckNext("end");
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        for (STATEMENT s : statements){
            s.evaluate();
        }
        return null;
    }

    @Override
    public void visitASTNode() {
        printNode();
        printEdgeTo();
        for (STATEMENT s : statements){
            printEdgeTo();
            s.visitASTNode();
        }
    }

    public String evaluate(String arg) throws FileNotFoundException, UnsupportedEncodingException {
        for (STATEMENT s : statements){
            s.evaluate();
        }
        return null;
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
