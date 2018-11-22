package ast;

import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class PROCCALL extends STATEMENT {
    private String name;
    private String arg;


    @Override
    public void parse() {
        tokenizer.getAndCheckNext("call");
        name = tokenizer.getNext();
        tokenizer.getAndCheckNext("with");
        arg = tokenizer.getNext();
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        PROCBLOCK PROCBLOCK = (PROCBLOCK) Main.symbolTable.get(name);
        return PROCBLOCK.evaluate(arg);
    }

    @Override
    public void visitASTNode() {
        printNode(name);
    }

    @Override
    public void staticCallGraphNoTunnel() {
        writer.print(name+";");
    }
    public void staticCallGraphWithTunnel(String procname) {
        writer.print(procname+"->"+name+";");
    }
}
