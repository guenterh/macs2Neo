package org.swissbib.macs2Neo4j;


import java.util.ArrayList;

/**
 * Created by swissbib on 31.05.15.
 */
public class ProcessMacsEngine {



    public static void main (String[] args) {


        //SolrMacsSaxNeoMacsParser solr2NeoParser = new SolrMacsSaxNeoMacsParser("/home/swissbib/temp/macsdata/macsdata.xml","");
        //solr2NeoParser.parseSolrModel();


        if (args.length != 2) {
            System.out.println("use: classname 'path to solr macs file' 'directory for the neo database'" );
            System.exit(-1);
        }

        SolrMacs2NeoMacsParser solr2NeoParser = new SolrMacs2NeoMacsParser(args[0]);
        solr2NeoParser.parseSolrModel();

        ArrayList<SubjectNodesRelations> bundles =  solr2NeoParser.getMacsRelation();
        System.out.println("now we start the transformation to CSV");

        //Neo4JCreator neoCreator = new Neo4JCreator(args[1]);
        //neoCreator.startTransformation(bundles);
        //neoCreator.shutdownDatabase();

        CSVFileCreator csvFileCreator = new CSVFileCreator(args[1]);


        csvFileCreator.startTransformation(bundles);

    }




}
