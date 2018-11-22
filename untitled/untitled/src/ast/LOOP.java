package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LOOP extends STATEMENT {
    List<STATEMENT> statements;
    int times=0;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("loop");
        times= new Integer(tokenizer.getNext());
        tokenizer.getAndCheckNext("times");
        statements = new ArrayList<>();
        while (!tokenizer.checkToken("pool")){
            STATEMENT s = STATEMENT.getSubStatement();
            statements.add(s);
            s.parse();
        }
        tokenizer.getAndCheckNext("pool");
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        for (int i=0; i<times; i++){
            for (STATEMENT s: statements) {
                s.evaluate();
            }
        }
        return null;
    }

    @Override
    public void visitASTNode() {
        printNode();
        for (STATEMENT s : statements) {
            printEdgeTo();
            s.visitASTNode();
        }

    }

    public void staticCallGraphWithTunnel(String procname){
        for (STATEMENT s : statements) {
            s.staticCallGraphWithTunnel(procname);
        }
    }


}
