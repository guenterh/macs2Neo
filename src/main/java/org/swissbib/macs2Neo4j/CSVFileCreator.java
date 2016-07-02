package org.swissbib.macs2Neo4j;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by swissbib on 7/1/16.
 */
public class CSVFileCreator {

    private String pathCSVFile = null;
    private Pattern p = Pattern.compile("\"");



    public CSVFileCreator(String pathCSVFile) {

        this.pathCSVFile = pathCSVFile;
    }



    public void startTransformation(ArrayList<SubjectNodesRelations> macsSubjectList) {

        try {

            File file = new File(this.pathCSVFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            bw.write(this.createHeader());
            bw.newLine();

            for (SubjectNodesRelations subjectRelations : macsSubjectList) {

                ArrayList<ArrayList<? extends SubjectType>>  subjectLists =  subjectRelations.getExpressionList();

                for (ArrayList<? extends SubjectType> subjectTypes : subjectLists) {
                    for (SubjectType sT : subjectTypes) {

                        if (sT instanceof RameauType) {
                            bw.write(this.createRameauLine((RameauType) sT, subjectTypes.size() > 1, subjectRelations.getMacsId()) );
                            bw.newLine();
                        } else if (sT instanceof  LcshType) {
                            bw.write(this.createLCSHLine((LcshType) sT, subjectTypes.size() > 1,subjectRelations.getMacsId()));
                            bw.newLine();
                        } else if (sT instanceof SWDType){
                            bw.write(this.createGNDLine((SWDType) sT,subjectTypes.size() > 1,subjectRelations.getMacsId()));
                            bw.newLine();
                        }

                    }
                }

            }

            bw.flush();
            bw.close();

        } catch ( IOException csvIOException) {
            csvIOException.printStackTrace();
        }

    }



    private String createHeader ()  {

        return "macsID,LCSHtext,LCSHid,LCSHtextAND,LCSHidAND,RameauText,RameauId,RameauTextAnd,RameauIdAnd,GNDText,GNDid,GNDTextAnd,GNDidAnd";

    }

    private String createLCSHLine(LcshType sType, boolean AND, String macsID) {
        //"macsID,LCSHtext,LCSHid,LCSHtextAND,LCSHidAND,RameauText,RameauId,RameauTextAnd,RameauIdAnd,GNDText,GNDid,GNDTextAnd,GNDidAnd";
        return AND ?
                String.format("%s,,,%s,%s,,,,,,,,",macsID,this.escapeValues(sType.value), sType.id)  :
                String.format("%s,%s,%s,,,,,,,,,,",macsID,this.escapeValues(sType.value),sType.id);
    }

    private String createRameauLine(RameauType sType, boolean AND, String macsID) {


        //"macsID,LCSHtext,LCSHid,LCSHtextAND,LCSHidAND,RameauText,RameauId,RameauTextAnd,RameauIdAnd,GNDText,GNDid,GNDTextAnd,GNDidAnd";
        return AND ?
                String.format("%s,,,,,,,%s,%s,,,,",macsID,this.escapeValues(sType.value), sType.id) :
                String.format("%s,,,,,%s,%s,,,,,,",macsID,this.escapeValues(sType.value),sType.id);
    }

    private String createGNDLine(SWDType sType, boolean AND, String macsID) {

        //String t = "jsjsj\"kkskks";
        //String test = this.escapeValues(t);

        //"macsID,LCSHtext,LCSHid,LCSHtextAND,LCSHidAND,RameauText,RameauId,RameauTextAnd,RameauIdAnd,GNDText,GNDid,GNDTextAnd,GNDidAnd";
        return AND ?
                String.format("%s,,,,,,,,,,,%s,%s",macsID,this.escapeValues(sType.value), sType.id) :
                String.format("%s,,,,,,,,,%s,%s,,",macsID,this.escapeValues(sType.value),sType.id);
    }


    private String escapeValues (String value) {
        return "\""  + p.matcher(value).replaceAll("\\\\\"") + "\"";

    }



}
