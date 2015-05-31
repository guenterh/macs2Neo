package org.swissbib.macs2Neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.util.ArrayList;




public class Neo4JCreator {

    GraphDatabaseService service;


    public Neo4JCreator()  {


        init();
    }


    private void init() {
        this.service  = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(
                "target/ne04jdb/location" )
                //.setConfig( GraphDatabaseSettings.read_only, "true" )
                .newGraphDatabase();

        String test = "";
    }

    public void startTransformation(ArrayList<SubjectNodesRelations> macsSubjectList) {

        for (SubjectNodesRelations relations: macsSubjectList) {

            for (SWDType swdType :  relations.getSwdTypes()) {

            }

        }


    }

    public void shutdownDatabase ()
    {
        if (this.service != null) {
            this.service.shutdown();

        }

    }

}


