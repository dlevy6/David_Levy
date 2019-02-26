package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnID;

import java.util.ArrayList;
import java.util.List;

public class ResultSet{

    private List<Object> myTable;
    private String tableName;
    private ColumnDescription[] columnDescriptions;
    private ColumnID[] selectColumnIDsWithFunction;
    private  List<Object> selectedList;
    private ColumnID[] selectedCIDs;
    private String message;

    protected ResultSet(Boolean bool){
        myTable = new ArrayList<>();
        boolResultSet(bool);

    }


    protected ResultSet(Table table) {
        this.tableName = table.getTableName();
        this.columnDescriptions = table.getColumnDescriptions();
        myTable = new ArrayList<>();
    }

    protected ResultSet (ColumnID[] selectColumnIDsWithFunction, String tableName, List<Object> selectedList, ColumnDescription[] columnDescriptions, ColumnID[] selectedCIDs){
        this.myTable = selectedList;
        this.tableName = tableName;
        this.selectColumnIDsWithFunction = selectColumnIDsWithFunction;
        this.columnDescriptions = columnDescriptions;
        this.selectedCIDs = selectedCIDs;

    }

    /**
     * resultset for createTable
     * @return
     */
    protected ResultSet createResultSet() {
        return this;
    }

    /**
     * true fales resultset
     * @param bool
     */
    protected void boolResultSet(Boolean bool) {
        List<Object> boolList = new ArrayList<>();
        boolList.add(bool);
        myTable.add(boolList);
    }

    /**
     * printing table
     */
    protected void printMethod(){

        System.out.println();
        if (selectColumnIDsWithFunction != null) {
            System.out.print("ColumnName");
            for (ColumnID cdf: selectColumnIDsWithFunction) {
                System.out.print(String.format("%20s", cdf.getColumnName()));

            }
        }
        System.out.println();

        if (selectColumnIDsWithFunction != null) {
            System.out.print("ColumnType");
            for (ColumnID cdf: selectedCIDs) {
                for (ColumnDescription cd: columnDescriptions) {
                    if (cdf.getColumnName().equals(cd.getColumnName())) {
                        System.out.print(String.format("%20s", cd.getColumnType()));
                    }
                }
            }
        }

        System.out.println("\n");
        for( Object index: myTable){
            System.out.print(String.format("%10s", ""));
            ArrayList<Object> index2 = (ArrayList<Object>) index;
            for (Object c : index2){         //error
                    System.out.print(String.format("%20s", c));
            }
            System.out.println();
        }

    }

    protected List<Object> getMyTable() {
        return myTable;
    }

    /**
     * getting the boolean in a boolean resultset
     * @return
     */
    protected String getBool(){
        List<Object> tmp = (List<Object>) myTable.get(0);
        String tmp2 = (String) tmp.get(0).toString();
        return tmp2;
    }
    //setting error message
    protected void setMessage(String message) {
        this.message = message;
    }

    protected String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        String f = "";
        if (selectColumnIDsWithFunction != null) {
            System.out.print("ColumnName");
            for (ColumnID cdf: selectColumnIDsWithFunction) {
                f += cdf.getColumnName() + " ";

            }
        }
        f += "\n";

        if (selectColumnIDsWithFunction != null) {
            System.out.print("ColumnType");
            for (ColumnID cdf: selectedCIDs) {
                for (ColumnDescription cd: columnDescriptions) {
                    if (cdf.getColumnName().equals(cd.getColumnName())) {
                        f += cd.getColumnType() + " ";
                    }
                }
            }
        }


        for( Object index: myTable){
            f += "\n";
            ArrayList<Object> index2 = (ArrayList<Object>) index;
            for (Object c : index2){         //error
                f+= c.toString() + " ";
            }


        }
        return f;
    }

}