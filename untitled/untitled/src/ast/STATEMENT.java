package ast;

import libs.Node;

public  abstract class STATEMENT extends Node {
    public static STATEMENT getSubStatement(){
        if (tokenizer.checkToken("iflessthan")) {
            return new IFLESSTHAN();
        }
        if (tokenizer.checkToken("loop")) {
            return new LOOP();
        }
        if (tokenizer.checkToken("set")) {
            return new SET();
        }
        if (tokenizer.checkToken("get")){
            return new USE();
        }
        if (tokenizer.checkToken("new")){
            return new DEC();
        }
        if (tokenizer.checkToken("print")){
            return new PRINT();
        }
        if (tokenizer.checkToken("times")){
            return new TIMES();
        }
        if (tokenizer.checkToken("def")){
            return new PROCDEC();
        }
        if (tokenizer.checkToken("call")){
            return new PROCCALL();
        }
        else return null;
    }
    @Override
    public void staticCallGraphNoTunnel() { }

    @Override
    public void staticCallGraphWithTunnel(String ignore) { }

}
