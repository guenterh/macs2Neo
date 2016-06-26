package org.swissbib.macs2Neo4j;

import java.util.*;

class SubjectType {
    public String id;
    public String value;

}

class SWDType extends SubjectType{
}


class RameauType extends SubjectType {
}

class LcshType extends SubjectType{
}



public class SubjectNodesRelations {


    private String macsId;


    private ArrayList<ArrayList<? extends SubjectType>> listOfExpressions = new ArrayList<ArrayList<? extends SubjectType>>();


    private ArrayList<SWDType> swdTypes = new ArrayList<SWDType>();
    private ArrayList<RameauType> rameauTypes = new ArrayList<RameauType>();
    private ArrayList<LcshType> lcshTypes = new ArrayList<LcshType>();


    public SWDType getSwdType() {
        return new SWDType();
    }

    public RameauType getRameauType() {
        return new RameauType();
    }

    public LcshType getLcshType() {
        return new LcshType();
    }


    public void setSwdTypes(ArrayList<SWDType> swdTypes) {

        this.swdTypes = swdTypes;
        this.addExpressionlist(swdTypes);
    }


    public void setRameauTypes(ArrayList<RameauType> rameauTypes) {

        this.rameauTypes = rameauTypes;
        this.addExpressionlist(rameauTypes);
    }

    public void setLcshTypes(ArrayList<LcshType> lcshTypes) {

        this.lcshTypes = lcshTypes;
        this.addExpressionlist(lcshTypes);
    }


    public ArrayList<SWDType> getSwdTypes() {
        return this.swdTypes;
    }

    public ArrayList<RameauType> getRameauTypes() {
        return this.rameauTypes;
    }


    public ArrayList<LcshType> getLcshTypes() {
        return this.lcshTypes;
    }

    public void setMacsID(String id) {
        this.macsId = id;
    }

    public String getMacsId() {
        return this.macsId;
    }


    public void addExpressionlist (ArrayList<? extends SubjectType> expressionList) {
        this.listOfExpressions.add(expressionList);

    }

    public ArrayList<ArrayList<? extends SubjectType>> getExpressionList () {
        return this.listOfExpressions;
    }



}
