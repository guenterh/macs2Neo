package org.swissbib.macs2Neo4j;


import javax.xml.transform.stream.StreamSource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;




//import javax.xml.stream.*;
//import javax.xml.stream.events.XMLEvent;
//import javax.xml.stream.util.XMLEventAllocator;
//import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLInputFactory;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by swissbib on 31.05.15.
 */
public class SolrMacs2NeoMacsParser {

    private BufferedWriter solrOut;
    private String localname = "";
    private String currentAttribute = "";

    private String inputFile;

    //perhaps could be used for batch load via csv or something similar??
    private String outputFileNeoStructure;

    private ArrayList<SubjectNodesRelations> subjectNodeRelations = new ArrayList<SubjectNodesRelations>();

    private SubjectNodesRelations subjectNodes;


    private SubjectType subjectType;
    private String currentType;

    public SolrMacs2NeoMacsParser(String inputFile, String neoStructureOutputFile)
    {
        this.inputFile = inputFile;
        this.outputFileNeoStructure = neoStructureOutputFile;

        init();

    }

    public SolrMacs2NeoMacsParser(String inputFile)
    {
        this(inputFile,"");

    }



    private void init() {


    }


    public void parseSolrModel () {
        //ByteArrayInputStream bAxServerResponse = new ByteArrayInputStream(xServerResponse.getBytes("UTF-8"));


        try {
            XMLInputFactory xmlif = XMLInputFactory.newFactory();

            FileInputStream fInputStream = new FileInputStream(this.inputFile);
            MacsStreamFilter macsFilter = new MacsStreamFilter();

            XMLStreamReader p = xmlif.createFilteredReader(xmlif.createXMLStreamReader(fInputStream),macsFilter);

            while(p.hasNext() ) {
                p.next();
            }

            this.subjectNodeRelations = macsFilter.getMacsBundles();

            // SolrData we have to parse
        } catch (FileNotFoundException | XMLStreamException fnfExc) {
            fnfExc.printStackTrace();
        }



    }


    public ArrayList<SubjectNodesRelations> getMacsRelation() {
        return this.subjectNodeRelations;
    }

}
