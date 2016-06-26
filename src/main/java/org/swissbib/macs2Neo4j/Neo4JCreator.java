package org.swissbib.macs2Neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import java.io.File;
import java.util.ArrayList;




public class Neo4JCreator {

    GraphDatabaseService service;

    String neo4JDirectory;

    public Neo4JCreator(String neo4jDirectory)  {
        this.neo4JDirectory = neo4jDirectory;
        init();
    }


    private void init() {
        this.service  = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(
                new File(this.neo4JDirectory) )
                //.setConfig( GraphDatabaseSettings.read_only, "true" )
                .newGraphDatabase();

    }

    public void startTransformation(ArrayList<SubjectNodesRelations> macsSubjectList) {

        for (SubjectNodesRelations relations: macsSubjectList) {

            //todo: now create the Neo structure

            ArrayList<ArrayList<? extends SubjectType>> listOfExpressions = relations.getExpressionList();
            //System.out.println();

        }


    }

    public void shutdownDatabase ()
    {
        if (this.service != null) {
            this.service.shutdown();

        }

    }

}


