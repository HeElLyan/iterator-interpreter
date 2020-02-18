package com.he.service;

import com.he.tree.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

public final class FacadeXmlImpl implements Facade{

    private static FacadeXmlImpl instance;
    private File file;
    private FileWriter writer;

    public static FacadeXmlImpl getInstance() throws IOException {
        if (instance != null) {
            return instance;
        } else {
            return instance = new FacadeXmlImpl();
        }
    }

    private FacadeXmlImpl() throws IOException {
        file = new File("file1.txt");
        writer = new FileWriter(file, true);
    }

    public Node readTree(File file) {
        try {
            String str = null;
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            if (scanner.hasNextLine()) {
                str = scanner.nextLine();
            }
            return XmlToObject(str);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void writeTree(Node node) {
        try {
            writer.write(jaxbObjectToXML(node));
            writer.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Node XmlToObject(String string) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Node.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Node) jaxbUnmarshaller.unmarshal(new StringReader(string));
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    private static String jaxbObjectToXML(Node node) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(node, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
