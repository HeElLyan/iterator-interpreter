package com.he;

import com.he.interpreter.Context;
import com.he.interpreter.CrudData;
import com.he.interpreter.Interpreter;
import com.he.iterator.IteratorDFS;
import com.he.service.TreeConverter;
import com.he.tree.Tree;
import java.util.Scanner;

public class MainApp implements Runnable {

    public final static String INPUT_FILENAME  = "src/main/resources/data.xml";
    public final static String OUTPUT_FILENAME = "src/main/resources/data99.json";

    public static final String NAME = "name";
    public static final String PRIORITY = "priority";

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.run();
    }

    public void run() {
        TreeConverter converter = new TreeConverter();
        Tree tree = converter.readTree(INPUT_FILENAME);
        tree.setIteratorClass(IteratorDFS.class);

        Context context = new Context();
        context.addAttribute(Context.TREE, tree);
        context.addAttribute(Context.CONVERTER, converter);
        context.addAttribute(Context.INPUT_FILENAME, INPUT_FILENAME);
        context.addAttribute(Context.OUTPUT_FILENAME, OUTPUT_FILENAME);

        Interpreter interpreter = new Interpreter(context, new CrudData());
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!(input = scanner.nextLine()).equals("stop")) {
            interpreter.execute(input);
        }
    }

}
