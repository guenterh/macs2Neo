package org.swissbib.macs2Neo4j;

import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Str;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * this type is no longer used
 * for this use case parsing of the solr XML structure is easier done with the STAX API
 */
public class SolrMacsSaxNeoMacsParser {


    private final String inputFile;
    //perhaps could be used for batch load with cypher??
    private final String neoStructureOutputFile;



    private class MacsLinkModel extends DefaultHandler {

        private StringBuffer curCharValue = new StringBuffer(1024);
        private boolean inSearchedStartElement = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            curCharValue = new StringBuffer(1024);

            if (localName.equalsIgnoreCase("field")) {
                inSearchedStartElement = true;
                System.out.println(attributes.getValue("name"));

            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (inSearchedStartElement) {
                System.out.println(curCharValue.toString());
            }
            inSearchedStartElement = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (inSearchedStartElement) {
                curCharValue.append(ch, start, length);
            }
        }

    }


    public SolrMacsSaxNeoMacsParser (String inputFile, String neoStructureOutputFile) {

        this.inputFile = inputFile;
        this.neoStructureOutputFile = neoStructureOutputFile;


    }


    public void parseSolrModel() {
        try {

            final XMLReader parser = XMLReaderFactory.createXMLReader();

            parser.setContentHandler(new MacsLinkModel());

            final InputSource input = new InputSource(new FileInputStream(this.inputFile));

            parser.parse(input);


        } catch(SAXException | IOException saxExc) {
            saxExc.printStackTrace();
        }


    }


}
