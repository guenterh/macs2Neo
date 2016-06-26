package org.swissbib.macs2Neo4j;


import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;


class MacsStreamFilter  implements StreamFilter {

    private SubjectNodesRelations subjectNodes;
    private ArrayList<? extends SubjectType> subjectTypes;

    private ArrayList<RameauType> rameauTypes = null;
    private ArrayList<LcshType> lcshTypes = null;
    private ArrayList<SWDType> swdTypes = null;

    private String currentType;
    private ArrayList<SubjectNodesRelations> subjectNodeRelations = new ArrayList<SubjectNodesRelations>();


    @Override
    public boolean accept(XMLStreamReader reader) {
        boolean toParse = false;
        final int eventType = reader.getEventType();

        try {
            switch (eventType) {
                case XMLStreamConstants.CDATA:
                case XMLStreamConstants.ATTRIBUTE:
                case XMLStreamConstants.COMMENT:
                case XMLStreamConstants.DTD:
                case XMLStreamConstants.ENTITY_DECLARATION:
                case XMLStreamConstants.ENTITY_REFERENCE:
                case XMLStreamConstants.NAMESPACE:
                case XMLStreamConstants.NOTATION_DECLARATION:
                case XMLStreamConstants.PROCESSING_INSTRUCTION:
                case XMLStreamConstants.START_DOCUMENT:
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    //Did we find an error constellation??

                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equalsIgnoreCase("doc")) {
                        this.subjectNodes = new SubjectNodesRelations();
                        toParse = true;

                    } else if (reader.getLocalName().equalsIgnoreCase("field")) {

                        if ( reader.getAttributeCount() > 0 ) {

                            String name = reader.getAttributeValue(null,"name").toLowerCase();
                            switch (name) {
                                case "id":
                                    this.currentType = reader.getAttributeValue(null,"name");
                                    break;
                                case "rameau_number":
                                case "rameau":
                                    //if (null ==  this.rameauTypes) {
                                    //    this.rameauTypes = new ArrayList<RameauType>();
                                    //}
                                    this.currentType = reader.getAttributeValue(null,"name");
                                    break;
                                case "lcsh":
                                case "lcsh_number":
                                    //if (null ==  this.lcshTypes) {
                                    //    this.lcshTypes = new ArrayList<LcshType>();
                                    //}
                                    this.currentType = reader.getAttributeValue(null,"name");
                                    break;
                                case "swd":
                                case "swd_number":
                                    //if (null ==  this.swdTypes) {
                                    //    this.swdTypes = new ArrayList<SWDType>();
                                    //}
                                    this.currentType = reader.getAttributeValue(null,"name");
                                    break;
                                default:
                                    break;
                            }

                            toParse = true;
                        }

                        break;
                    } else {
                        this.currentType = null;
                    }
                case XMLStreamConstants.CHARACTERS:

                    if (this.currentType != null) {
                        if (this.currentType.equalsIgnoreCase("id")) {
                            this.subjectNodes.setMacsID(reader.getText());
                            this.currentType = null;
                        } else if (this.currentType.equalsIgnoreCase("RAMEAU") ||
                                this.currentType.equalsIgnoreCase("rameau_number")) {
                            String tagValue = reader.getText();
                            if (null == this.rameauTypes || this.rameauTypes.isEmpty()) {
                                int number = checkNumberOfExpressions(tagValue);
                                this.rameauTypes = new ArrayList<>(number);

                                for (int i = 0; i < number; i++) {
                                    this.rameauTypes.add(new RameauType());
                                }

                            }
                            String [] expressions = this.getTagExpressions(tagValue);
                            if (expressions.length != this.rameauTypes.size()) {
                                System.out.println("something went wrong with rameau-lenght");
                                if (null != this.subjectNodes) {
                                    System.out.println(this.subjectNodes.getMacsId());
                                }
                            } else {
                                for (int i = 0; i < this.rameauTypes.size(); i++) {
                                    if (this.currentType.equalsIgnoreCase("RAMEAU")) {

                                        this.rameauTypes.get(i).value = expressions[i];
                                    } else {
                                        this.rameauTypes.get(i).id = expressions[i];
                                    }
                                }
                            }

                            this.currentType = null;


                        } else if (this.currentType.equalsIgnoreCase("swd") ||
                                this.currentType.equalsIgnoreCase("swd_number")) {
                            String tagValue = reader.getText();
                            if (null == this.swdTypes   || this.swdTypes.isEmpty()) {
                                int number = checkNumberOfExpressions(tagValue);
                                this.swdTypes = new ArrayList<>(number);

                                for (int i = 0; i < number; i++) {
                                    this.swdTypes.add(new SWDType());
                                }

                            }
                            String [] expressions = this.getTagExpressions(tagValue);
                            if (expressions.length != this.swdTypes.size()) {
                                System.out.println("something went wrong with SWD-lenght");
                                if (null != this.subjectNodes) {
                                    System.out.println(this.subjectNodes.getMacsId());
                                }
                            } else {
                                for (int i = 0; i < this.swdTypes.size(); i++) {
                                    if (this.currentType.equalsIgnoreCase("SWD")) {

                                        this.swdTypes.get(i).value = expressions[i];
                                    } else {
                                        this.swdTypes.get(i).id = expressions[i];
                                    }
                                }
                            }

                            this.currentType = null;
                        } else if (this.currentType.equalsIgnoreCase("lcsh") ||
                                this.currentType.equalsIgnoreCase("lcsh_number")) {
                            String tagValue = reader.getText();
                            if (null == this.lcshTypes || this.lcshTypes.isEmpty()) {
                                int number = checkNumberOfExpressions(tagValue);
                                this.lcshTypes = new ArrayList<>(number);

                                for (int i = 0; i < number; i++) {
                                    this.lcshTypes.add(new LcshType());
                                }

                            }
                            String [] expressions = this.getTagExpressions(tagValue);
                            if (expressions.length != this.lcshTypes.size()) {
                                System.out.println("something went wrong with LCSH-lenght");
                                if (null != this.subjectNodes) {
                                    System.out.println(this.subjectNodes.getMacsId());
                                }

                            } else {
                                for (int i = 0; i < this.lcshTypes.size(); i++) {
                                    if (this.currentType.equalsIgnoreCase("LCSH")) {

                                        this.lcshTypes.get(i).value = expressions[i];
                                    } else {
                                        this.lcshTypes.get(i).id = expressions[i];
                                    }
                                }
                            }

                            this.currentType = null;
                        }

                        toParse = true;
                    }
                    this.currentType = null;
                    break;


                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equalsIgnoreCase("doc")) {

                        if (null != this.rameauTypes) {
                            this.subjectNodes.setRameauTypes(this.rameauTypes);
                            this.rameauTypes = null;
                        }


                        if (null != this.swdTypes) {
                            this.subjectNodes.setSwdTypes(this.swdTypes);
                            this.swdTypes = null;
                        }

                        if (null != this.lcshTypes) {
                            this.subjectNodes.setLcshTypes(this.lcshTypes);
                            this.lcshTypes = null;
                        }


                        this.subjectNodeRelations.add(subjectNodes);
                        this.subjectNodes = new SubjectNodesRelations();
                    }
                    toParse = true;
                    break;

            }
        } catch (Throwable thr) {
            thr.printStackTrace();
        }

        return toParse;
    }

    private int checkNumberOfExpressions (String tagValue) {

        //todo: by now we have difficulties with expressions like this:
        //Borland C++ Builder 6.0
        //we ignore it for the moment
        return tagValue.split("\\+").length;

    }

    private String[] getTagExpressions (String tagValue) {

        //todo compare note in checkNumberOfExpressions
        return tagValue.split("\\+");

    }

    public  ArrayList<SubjectNodesRelations> getMacsBundles() {
        return this.subjectNodeRelations;
    }

}
