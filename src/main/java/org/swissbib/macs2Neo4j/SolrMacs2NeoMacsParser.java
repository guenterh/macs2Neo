package org.swissbib.macs2Neo4j;

import com.sun.xml.stream.events.XMLEventAllocatorImpl;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by swissbib on 31.05.15.
 */
public class SolrMacs2NeoMacsParser {

    private XMLEventAllocator allocator;
    private BufferedWriter solrOut;
    private XMLOutputFactory solrOutput;
    private XMLStreamWriter solrWriter;
    private String localname = "";
    private String currentAttribute = "";

    private String inputFile;
    private String outputFileNeoStructure;

    private ArrayList<SubjectNodesRelations> subjectNodeRelations = new ArrayList<SubjectNodesRelations>();

    private SubjectNodesRelations subjectNodes;

    private XMLStreamReader xmlStreamReader;

    private SubjectType subjectType;
    private String currentType;

    public SolrMacs2NeoMacsParser(String inputFile, String neoStructureOutputFile)
    {
        this.inputFile = inputFile;
        this.outputFileNeoStructure = neoStructureOutputFile;

        init();



    }

    private void init() {

        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE); // converts &#x303; to ñ etc.

        xmlif.setEventAllocator(new XMLEventAllocatorImpl());
        allocator = xmlif.getEventAllocator();

        // SolrData we have to parse
        try {
            FileInputStream fileInputStream = new FileInputStream(this.inputFile);
            xmlStreamReader = xmlif.createXMLStreamReader(fileInputStream);
        } catch (FileNotFoundException fnfExc) {
            fnfExc.printStackTrace();
        } catch (XMLStreamException xmlExc) {
            xmlExc.printStackTrace();
        }




    }

    public void parseSolrFile() {


        try {
            while (xmlStreamReader.hasNext()) {
                checkStreamElement();
            }
        } catch (XMLStreamException xmlStreamExc) {
            xmlStreamExc.printStackTrace();
        }

    }


    private void checkStreamElement() {
        try {

            int eventCode = this.xmlStreamReader.next();
            switch (eventCode) {
                case XMLEvent.START_ELEMENT:
                    if (xmlStreamReader.getLocalName().equalsIgnoreCase("doc")) {
                        this.subjectNodes = new SubjectNodesRelations();

                    } else if (xmlStreamReader.getLocalName().equalsIgnoreCase("field")) {

                        int count = xmlStreamReader.getAttributeCount();
                        if (count > 0) {

                            if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("id")) {

                                this.subjectNodes.setMacsID(xmlStreamReader.getAttributeValue(0));
                                this.currentType = "id";
                                this.currentAttribute = "id";



                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("RAMEAU")) {
                                this.subjectType = new RameauType();
                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "RAMEAU";
                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("RAMEAU_number")) {

                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "RAMEAU_number";
                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("LCSH")) {

                                this.subjectType = new LcshType();
                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "LCSH";
                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("LCSH_number")) {

                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "LCSH_number";
                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("SWD")) {

                                this.subjectType = new SWDType();
                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "SWD";
                            } else if (xmlStreamReader.getAttributeValue(0).equalsIgnoreCase("SWD_number")) {

                                this.currentType = xmlStreamReader.getAttributeValue(0);
                                this.currentAttribute = "SWD_number";
                            }
                        }
                    }


                    break;
                case XMLEvent.CHARACTERS:

                    if (this.currentType != null) {
                        if (this.currentType.equalsIgnoreCase("id")) {
                            this.subjectNodes.setMacsID(xmlStreamReader.getText());
                            this.currentType = null;
                        } else if (this.currentType.equalsIgnoreCase("RAMEAU") ||

                                this.currentType.equalsIgnoreCase("LCSH") ||

                                this.currentType.equalsIgnoreCase("SWD")
                                ) {
                            this.subjectType.value = xmlStreamReader.getText();
                            this.currentType = null;

                        } else if ( this.currentType.equalsIgnoreCase("RAMEAU_number") ||
                                        this.currentType.equalsIgnoreCase("LCSH_number") ||

                                        this.currentType.equalsIgnoreCase("SWD_number")) {
                            this.subjectType.id = xmlStreamReader.getText();
                            this.currentType = null;
                        }
                    }



                    break;
                case XMLEvent.END_ELEMENT:
                    if (xmlStreamReader.getLocalName().equalsIgnoreCase("field") &&
                            (this.currentAttribute.equalsIgnoreCase("SWD_number") ||
                            this.currentAttribute.equalsIgnoreCase("LCSH_number") ||
                            this.currentAttribute.equalsIgnoreCase("RAMEAU_number"))) {


                        if (this.currentAttribute.equalsIgnoreCase("RAMEAU_number")) {
                            this.subjectNodes.setRameauTpe((RameauType) this.subjectType);
                        } else if (this.currentAttribute.equalsIgnoreCase("LCSH_number")) {
                            this.subjectNodes.setLcshTpe((LcshType) this.subjectType);
                        } else if (this.currentAttribute.equalsIgnoreCase("SWD_number")) {
                            this.subjectNodes.setSwdTpe((SWDType) this.subjectType);
                        }
                    } else if (xmlStreamReader.getLocalName().equalsIgnoreCase("doc")) {
                        this.subjectNodeRelations.add(subjectNodes);
                        this.subjectNodes = new SubjectNodesRelations();
                    }

                    break;
            }
        } catch (XMLStreamException xmlStreamExc) {
            xmlStreamExc.printStackTrace();
        }

    }

}
