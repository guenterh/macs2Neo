package org.swissbib.macs2Neo4j;

import com.sun.xml.stream.events.XMLEventAllocatorImpl;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by swissbib on 31.05.15.
 */
public class ProcessMacsEngine {



    public static void main (String[] args) {


        SolrMacs2NeoMacsParser solr2NeoParser = new SolrMacs2NeoMacsParser("/home/swissbib/Desktop/macs/export-tel.html","");

        solr2NeoParser.parseSolrFile();


        /*
        try {
            XMLInputFactory xmlif = XMLInputFactory.newInstance();
            xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE); // converts &#x303; to ñ etc.

            xmlif.setEventAllocator(new XMLEventAllocatorImpl());
            allocator = xmlif.getEventAllocator();

            // marc21 data
            FileInputStream fileInputStream = new FileInputStream("/home/swissbib/Desktop/macs/export-tel.html");
            XMLStreamReader xmlStreamReader = xmlif.createXMLStreamReader(fileInputStream);

            // solr docs
            openSolrOut();

            while (xmlStreamReader.hasNext()) {
                parseRecord(xmlStreamReader);
            }

        } catch (XMLStreamException streamExc) {

            streamExc.printStackTrace();
        } catch (FileNotFoundException fnFExc) {
            fnFExc.printStackTrace();
        } catch (UnsupportedEncodingException unsEnc) {
            unsEnc.printStackTrace();
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
        }
        */


    }




}
