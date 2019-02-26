package edu.yu.cs.dataStructures.db;


import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnValuePair;

import java.util.ArrayList;


public class Row {

    private ArrayList<String> rowArrayList;
    private ColumnValuePair[] columnValuePairs;
    private ColumnDescription[] columnDescriptions;

    /**
     *constructer
     * @param columnValuePairs
     * @param columnDescriptions
     * @param primaryKey
     */
    protected Row(ColumnValuePair[] columnValuePairs,ColumnDescription[] columnDescriptions, ColumnDescription primaryKey) {
        this.columnValuePairs = columnValuePairs;
        this.columnDescriptions = columnDescriptions;
        rowArrayList = new ArrayList<>();
        putPrimaryColumnInIndex0(columnDescriptions, primaryKey);
        for(int i =0; i<columnDescriptions.length; i++){
            rowArrayList.add(null);
        }
        insertingDefaultValue();
        int length = -1;
        for (ColumnDescription cd : columnDescriptions) {
            length++;
            for (ColumnValuePair cvp : columnValuePairs) {
                if (cvp.getColumnID().getColumnName().equals(cd.getColumnName())) {
                    new DataCheck(cd, cvp.getValue());
                    rowArrayList.set(length, cvp.getValue());

                }
            }
        }
        checkIfNotNull();
    }



    /**
     * making sure for not null values
     */
    private void checkIfNotNull(){
        for (int i=0; i<columnDescriptions.length; i++){
            if (columnDescriptions[i].isNotNull() || i==0) {
                if (rowArrayList.get(i) == null) {
                    throw new IllegalArgumentException("value cannot be null");
                }
            }
        }

    }

    /**
     * make primary key first column
     * @param columnDescriptions
     * @param primaryKey
     */
    private void putPrimaryColumnInIndex0(ColumnDescription[] columnDescriptions, ColumnDescription primaryKey){
        ColumnDescription firstIndex = columnDescriptions[0];
        int indexOfPKey = -1;
        boolean bool = true;
        for (int i = 0; i<columnDescriptions.length; i++) {
            indexOfPKey++;
            if (primaryKey.getColumnName().equals(columnDescriptions[i].getColumnName())) {
                columnDescriptions[0]=columnDescriptions[indexOfPKey];
                columnDescriptions[indexOfPKey] = firstIndex;
                i = columnDescriptions.length +1;
            }
        }

    }




    /**
     * for default values
     */
    private void insertingDefaultValue(){
        for (int i =0; i<columnDescriptions.length; i++){
            if(columnDescriptions[i].getHasDefault()) {
                rowArrayList.set(i, columnDescriptions[i].getDefaultValue());
            }
        }
    }







    protected void printString(){
        for (String c : rowArrayList){
            System.out.print(String.format("%20s", c));
        }
        System.out.println();
    }



    protected ArrayList<String> getRowArrayList() {
        return rowArrayList;
    }

    @Override
    public String toString() {
        String f = "";
        for (String c : rowArrayList){
            f += c + " ";
        }
        System.out.println();
        return f;
    }

}
