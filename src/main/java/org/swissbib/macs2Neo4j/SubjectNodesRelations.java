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


    public void setSwdTpe(SWDType swtType) {

        this.swdTypes.add(swtType);
    }


    public void setRameauTpe(RameauType rameauType) {

        this.rameauTypes.add(rameauType);
    }

    public void setLcshTpe(LcshType lcshType) {

        this.lcshTypes.add(lcshType);
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



}
