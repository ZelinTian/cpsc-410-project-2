package ast;

import ui.Main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class PROCDEC extends STATEMENT {
    private String name;
    private PROCBLOCK codeblock;
    private STATEMENT retstmt;


    @Override
    public void parse() {
        tokenizer.getAndCheckNext("def");
        name = tokenizer.getNext();
        tokenizer.checkToken(("with"));
        codeblock = new PROCBLOCK();
        codeblock.parse();
    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        Main.symbolTable.put(name,codeblock);
        return null;
    }

    @Override
    public void visitASTNode() {
        printNode();
        printEdgeTo();
        printNode(name);
        printEdgeTo();
        codeblock.visitASTNode();
    }

    public void staticCallGraphNoTunnel(){
        writer.println(name+"->");
        writer.println("{");
        codeblock.staticCallGraphNoTunnel();
        writer.println("}");
    }

    public void staticCallGraphWithTunnel(String ignore){
        codeblock.staticCallGraphWithTunnel(name);
    }

}
