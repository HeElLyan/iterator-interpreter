package com.he.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.he.tree.Node;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class FacadeJsonImpl implements Facade{

    private static FacadeJsonImpl instance;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private FileWriter fileWriter;
    private FileReader fileReader;

    public static FacadeJsonImpl getInstance() throws IOException {
        if (instance != null) {
            return instance;
        } else {
            return instance = new FacadeJsonImpl();
        }
    }

    private FacadeJsonImpl() throws IOException {
        file = new File("file.txt");
        fileWriter = new FileWriter(file, true);
    }

    public Node readTree(File file) throws IOException {
        fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        return mapper.readValue(stringBuilder.toString(), Node.class);
    }

    public void writeTree(Node node) throws IOException {
        fileWriter.write(mapper.writeValueAsString(node));
        fileWriter.flush();
    }
}
