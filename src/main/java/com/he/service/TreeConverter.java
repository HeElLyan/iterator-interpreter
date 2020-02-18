package com.he.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.he.MainApp;
import com.he.tree.Node;
import com.he.tree.NodeType;
import com.he.tree.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeConverter{

    public TreeConverter() {
    }

    public Tree readTree(String filename) {
        if (filename.endsWith(".xml")) {
            return readTreeAsXml(new File(filename));
        } else if (filename.endsWith(".json")) {
            return readTreeAsJson(new File(filename));
        } else {
            throw new IllegalArgumentException("This type of file is not supported");
        }
    }

    public void writeTree(String filename, Tree tree) {
        if (filename.endsWith(".xml")) {
            writeTreeAsXml(new File(filename), tree);
        } else if (filename.endsWith(".json")) {
            writeTreeAsJson(new File(filename), tree);
        } else {
            throw new IllegalArgumentException("This type of file is not supported");
        }
    }

    private void writeTreeAsXml(File file, Tree tree) {
        write(file, tree.getRoot());
    }

    private void writeTreeAsJson(File file, Tree tree) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, tree.getRoot());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Tree readTreeAsXml(File file) {
        return new Tree((Node) read(file));
    }

    private Tree readTreeAsJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new Tree(mapper.readValue(file, Node.class));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    public void write(File file, Node root) {
        DocumentBuilder documentBuilder;
        Document document;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.newDocument();
            document.setXmlStandalone(true);
            document.appendChild(f(root, document));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerException | ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    private Element f(Node root, Document document) {
        Element element = document.createElement(root.getType().name().toLowerCase());
        element.setAttribute(MainApp.NAME, root.getName());
        element.setAttribute(MainApp.PRIORITY, String.valueOf(root.getPriority()));
        for (Node child : root.getChildren()) {
            element.appendChild(f(child, document));
        }
        return element;
    }

    public com.he.tree.Node read(File file) {
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new FileInputStream(file));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
        Element documentElement = document.getDocumentElement();
        return f(documentElement);
    }

    private com.he.tree.Node f(org.w3c.dom.Node node) {
        NamedNodeMap attributes = node.getAttributes();

        String name = attributes.getNamedItem(MainApp.NAME).getTextContent();
        NodeType type = NodeType.valueOf(node.getNodeName().toUpperCase());
        int priority = Integer.parseInt(attributes.getNamedItem(MainApp.PRIORITY).getTextContent());
        List<Node> children = new ArrayList<>();

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node cur = nodeList.item(i);
            if (cur.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                children.add(f(cur));
            }
        }
        return new com.he.tree.Node.Builder()
                .name(name)
                .type(type)
                .priority(priority)
                .children(children)
                .build();
    }
}



