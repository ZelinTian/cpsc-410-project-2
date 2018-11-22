package ast;

import libs.Node;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PROGRAM extends Node {
    private List<STATEMENT> statements = new ArrayList<>();

    @Override
    public void parse() {
        while (tokenizer.moreTokens()) {
            STATEMENT s = STATEMENT.getSubStatement();
            s.parse();
            statements.add(s);
        }

    }

    @Override
    public String evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        enterScope(this.toString());
        for (STATEMENT s : statements){
            s.evaluate();
        }
        return null;
    }

    @Override
    public void visitASTNode() {
        startGraph("ast-out.dot");
        printNode();
        for (STATEMENT s : statements){
            printEdgeTo();
            s.visitASTNode();
        }
        endGraph();
    }

    @Override
    public void staticCallGraphNoTunnel() {
        startGraph("callgraph.dot");
        for (STATEMENT s : statements){
            s.staticCallGraphNoTunnel();
        }
        endGraph();
    }
    public void staticCallGraphWithTunnel(String ignore) {
        startGraph("callgraph_withtunnel.dot");
        for (STATEMENT s : statements){
            s.staticCallGraphWithTunnel("main");
        }
        endGraph();
    }
}
