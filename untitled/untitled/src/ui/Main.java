package ui;

//import ast.PROGRAM;
//import libs.Tokenizer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static ui.Run.run_copied_java;

public class Main {
    public static Map<String,Object> symbolTable = new HashMap<>();
    private static List<String> nameList = new ArrayList<String>();
    private static File folder = new File("/Users/rex/Desktop/cpsc-410-project-2/test1");
    private static File copied_java_folder = new File("/Users/rex/Desktop/cpsc-410-project-2/copied_java");
    private static String content;
    private static List<String> valide_pathes = new ArrayList<>();


    public static void copyFolder(File src, File dest)
            throws IOException{

        if(src.isDirectory()){

            //if directory not exists, create it
            if(!dest.exists()){
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        }else{
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }
    private static void listFileName(File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFileName(fileEntry);
            } else {
//                System.out.println(fileEntry.getName());
                if (fileEntry.getName().endsWith(".java")){
                    nameList.add(fileEntry.getName());
                    valide_pathes.add(fileEntry.toString());
                }
            }
        }

    }
    private static List<String> get_dependency() {
        String fileName = "";
        List<String> classNames = new ArrayList<>();
        List<String> classDependecies = new ArrayList<>();

        for (String valide_path: valide_pathes){
            Path file_path = Paths.get(valide_path);
            try {
                content = new String(Files.readAllBytes(file_path), StandardCharsets.UTF_8);
            }catch (Exception e){
                System.out.println("cannot find the file");
            }
            int last_i= valide_path.split("/").length - 1;
            String current_class = valide_path.split("/")[last_i];
            fileName = current_class.substring(0,current_class.indexOf("."));
            classNames.add(fileName);
        }
////      System.out.println("1");
//        for (String name : nameList) {
//            try {
//                path= Paths.get("test1/src/ast/", name);
//                content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
//                String className = name.substring(0, name.indexOf("."));
//                fileName = className;
//                classNames.add(fileName);
//            } catch(Exception e){
////                    System.out.println("a is wrong");
//                try {
//                    path = Paths.get("test1/src/libs/", name);
//                    content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
//                    String className = name.substring(0, name.indexOf("."));
//                    fileName = className;
//                    classNames.add(fileName);
//
//                }catch (Exception v) {
////                        System.out.println("c is wrong");
//                    try {
//                        path = Paths.get("test1/src/ui/", name);
//                        content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
//                        String className = name.substring(0, name.indexOf("."));
//                        fileName = className;
//                        classNames.add(fileName);
//                    } catch (Exception k) {
////                            System.out.println("b is wrong");
//                    }
//                }
//            }
//            valide_pathes.add(String.valueOf(path));
//        }
//        System.out.println("path: " + valide_pathes);
//        System.out.println("nameList: " + nameList);
//        System.out.println("classNames: "+ classNames);
        for (String valide_path: valide_pathes) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(String.valueOf(valide_path)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int last_i= valide_path.split("/").length - 1;
            String current_class = valide_path.split("/")[last_i];
            current_class = current_class.substring(0,current_class.indexOf("."));
            try {
                String line = reader.readLine();
                while (line != null) {
                    if (!line.contains("//")&& !line.contains("import")) {
                        for (String sub : classNames) {
                            if (line.contains(sub) && !sub.equals(current_class)) {
                                classDependecies.add(current_class + " ->" + sub);
                            }
                        }
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classDependecies;
    }

//    private static void create_copy() throws IOException {
//        for (String valide_path: valide_pathes){
//            int last_i= valide_path.split("/").length - 1;
//            String current_class = valide_path.split("/")[last_i];
////            System.out.println("hihih: " + current_class);
//            String dest_path = copied_java_folder.getPath() + "/"+ current_class;
//            File src_file = new File(valide_path);
//            File destFile = new File(dest_path);
//            copyFolder(src_file,destFile);
//        }
//    }

    private static void update_create_copy() throws IOException {
        System.out.println("update_create_copy");
        for (String valide_path: valide_pathes){
            int last_i= valide_path.split("/").length - 1;
            String current_class = valide_path.split("/")[last_i];
//            System.out.println("hihih: " + current_class);
            String dest_path = copied_java_folder.getPath() + "/"+ current_class;
//            System.out.println("dest path: " + dest_path);
            BufferedReader reader = null;
            BufferedWriter writer = null;
            File src_file = new File(valide_path);
            File destFile = new File(dest_path);
            try {
                reader = new BufferedReader(new FileReader(String.valueOf(src_file)));
                writer = new BufferedWriter(new FileWriter(destFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                String currentLine = reader.readLine().trim();
                writer.write("package copied_java;"+ System.getProperty("line.separator"));
                while (currentLine != null) {
                    if (!currentLine.contains("import") && !currentLine.contains("package")) {
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
//                    if (currentLine.contains("public static void runThis() {")){
//                        writer.write("        Log log = new Log();\n" +
//                                "        log.log();"+ System.getProperty("line.separator"));
//                    }
                    currentLine = reader.readLine();
                }
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//    private static void adjust_copy(){
//        for (final File fileEntry : copied_java_folder.listFiles())
//            if (fileEntry.getName().endsWith(".java")) {
//                BufferedReader reader = null;
//                try {
//                    reader = new BufferedReader(new FileReader(String.valueOf(valide_path)));
//                } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                }
//                try {
//                    String line = reader.readLine();
//                    while (line != null) {
//                        if (line.contains("//")){
//                            line.de
//                        }
//
//                        line = reader.readLine();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                }


//            for (String valide_path: valide_pathes) {
//            BufferedReader reader = null;
//            try {
//                reader = new BufferedReader(new FileReader(String.valueOf(valide_path)));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            int last_i= valide_path.split("/").length - 1;
//            String current_class = valide_path.split("/")[last_i];
//            current_class = current_class.substring(0,current_class.indexOf("."));
//            try {
//                String line = reader.readLine();
//                while (line != null) {
//                    if (!line.contains("//")&& !line.contains("import")) {
//                        for (String sub : classNames) {
//                            if (line.contains(sub) && !sub.equals(current_class)) {
//                                classDependecies.add(current_class + " ->" + sub);
//                            }
//                        }
//                    }
//                    line = reader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        //working code---------------------------------------
        listFileName(folder);
        List<String> classDependecies = get_dependency();
        System.out.println("classDependecies: " + classDependecies);
        ////////------------------------------------------
        try {
            update_create_copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        run_copied_java();

//        try {
//            copyFolder(folder,copied_java_folder);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



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

}

