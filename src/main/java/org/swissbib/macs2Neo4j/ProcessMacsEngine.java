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


        Neo4JCreator neoCreator = new Neo4JCreator();
        neoCreator.startTransformation(solr2NeoParser.getMacsRelation());
        neoCreator.shutdownDatabase();

    }




}
