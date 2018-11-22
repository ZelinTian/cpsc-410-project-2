package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class IFLESSTHAN extends STATEMENT {
    USE var1,var2;
    BLOCK block;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("iflessthan");
        var1 = new USE();
        var1.parse();
        var2 = new USE();
        var2.parse();
        tokenizer.getAndCheckNext("then");
        block=new BLOCK();
        block.parse();
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        int val1 = Integer.parseInt(var1.evaluate());
        int val2 = Integer.parseInt(var2.evaluate());
        if (val1 < val2){
            block.evaluate();
        }
        else {
            System.out.println("....false");
        }
        return null;
    }

    @Override
    public void visitASTNode() {

    }

    public void staticCallGraphNoTunnel(){
        block.staticCallGraphNoTunnel();
    }

    public void staticCallGraphWithTunnel(String procname){
        block.staticCallGraphWithTunnel(procname);
    }

}
