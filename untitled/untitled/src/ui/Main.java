package ui;

import ast.PROGRAM;
import libs.Tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static Map<String,Object> symbolTable = new HashMap<>();
    private static List<String> nameList = new ArrayList<String>();
    private static List<String> splitedContent = new ArrayList<String>();
    private static File folder = new File("/Users/zelin/Desktop/untitled");
    private static String content;

    private static void listFileName(File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFileName(fileEntry);
            } else {
//                System.out.println(fileEntry.getName());
                if (fileEntry.getName().endsWith(".java")){
                    nameList.add(fileEntry.getName());
                }
            }
        }

    }
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        listFileName(folder);
        String fileName = "";

//            System.out.println("1");
            for (String name : nameList) {
                try {
                    Path a = Paths.get("src/ast/", name);
                    content = new String(Files.readAllBytes(a), StandardCharsets.UTF_8);
                    String className = name.substring(0, name.indexOf("."));
                    fileName = className;
                } catch(Exception e){
//                    System.out.println("a is wrong");
                    try {
                        Path b = Paths.get("src/libs/", name);
                        content = new String(Files.readAllBytes(b), StandardCharsets.UTF_8);
                        String className = name.substring(0, name.indexOf("."));
                        fileName = className;
                    }catch (Exception v) {
//                        System.out.println("c is wrong");
                        try {
                            Path c = Paths.get("src/ui/", name);
                            content = new String(Files.readAllBytes(c), StandardCharsets.UTF_8);
                            String className = name.substring(0, name.indexOf("."));
                            fileName = className;
                        } catch (Exception k) {
//                            System.out.println("b is wrong");
                        }
                    }
                }
                
            }


    }

//            for (String retval : content.trim().split(" ")) {
//                if (retval.contains(".")) {
//                    for (String subRetval : retval.split(".")) {
//                        splitedContent.add(subRetval);
//                    }
//                } else {
//                    splitedContent.add(retval);
//                }
//            }

//            List<String> b = splitedContent;
//            if (b.contains("tokenizer")){
//                System.out.println("y");
//            }
//            System.out.println(b);

//        List<String> literals = Arrays.asList("class","set","print","new","times","do","def","call","start","end", "return","with","loop","times","pool");
//        Tokenizer.makeTokenizer("BLOCK.java",literals);
//        PROGRAM p = new PROGRAM();
//        p.parse();
//        p.visitASTNode();
//        p.staticCallGraphNoTunnel();
//        p.staticCallGraphWithTunnel("ignore");
//        p.evaluate();
//        System.out.println("completed successfully");
//        System.out.println(symbolTable);
    }

