package ui;

import ast.PROGRAM;
import libs.Tokenizer;

import java.io.*;
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
        Path path = Paths.get("");
        listFileName(folder);
        String fileName = "";
        List<String> classNames = new ArrayList<>();

//            System.out.println("1");
            for (String name : nameList) {
                try {
                    path= Paths.get("src/ast/", name);
                    content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                    String className = name.substring(0, name.indexOf("."));
                    fileName = className;
                    classNames.add(fileName);
                } catch(Exception e){
//                    System.out.println("a is wrong");
                    try {
                        path = Paths.get("src/libs/", name);
                        content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                        String className = name.substring(0, name.indexOf("."));
                        fileName = className;
                        classNames.add(fileName);

                    }catch (Exception v) {
//                        System.out.println("c is wrong");
                        try {
                            path = Paths.get("src/ui/", name);
                            content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            String className = name.substring(0, name.indexOf("."));
                            fileName = className;
                            classNames.add(fileName);
                        } catch (Exception k) {
//                            System.out.println("b is wrong");
                        }
                    }
                }
//                System.out.println(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
                try {
                    String line = reader.readLine();
                    while (line != null){
                        if (!line.contains("//")) {
                            for (String sub : classNames){
                                if (line.contains(sub)){
                                    System.out.println(fileName + " ->" + sub);
                                }
                            }
                        }
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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

