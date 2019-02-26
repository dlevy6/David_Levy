package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Indexing {
    private List<Row> arrayList;
    private HashMap<String, BTree> bTreeMap;
    private BTree bTree;

    private String columnName;
    private String indexName;
    private Table table;
    private ColumnDescription[] columnDescription;

    protected Indexing(String columnName, Table table) {
        this.columnName = columnName;
        this.indexName = indexName;
        this.table = table;
        this.bTreeMap = table.getbTreeMap();
        this.columnDescription = table.getColumnDescriptions();

    }

    /**
     * getting the btree
     * @param columnName
     * @return
     */
    protected BTree getbTree(String columnName){
        if(bTreeMap.get(columnName) == null ){
            bTreeMap.put(columnName, new BTree());
        }
        return bTreeMap.get(columnName);
    }

    /**
     * creating an index
     * @param Key
     * @param currentRow
     * @param columnName
     */
    protected void createIndex(String Key, Row currentRow, String columnName){//check to see if index exist if not then index
            ArrayList rowHolder= new ArrayList();
            BTree newtree = bTreeMap.get(columnName);
        if (newtree.get(Key) != null) {
            rowHolder =(ArrayList) newtree.get(Key);

        }
        rowHolder.add(currentRow); ///here
            newtree.put(Key, rowHolder);
        }


    /**
     * indexing a column
     * @param columnName
     * @param table
     */
    protected void indexAColumn(String columnName,Table table) {
        bTreeMap = table.getbTreeMap();
        bTreeMap.put(columnName, new BTree());
        BTree newTree = bTreeMap.get(columnName);
        int amountofColumns = 0;//Needs to be the number of columns we need to get
        for (int i = 0; i < table.getMyTable().size(); i++) {

            ArrayList toHoldRows = new ArrayList();
            String value = findKey(columnName,table, table.getMyTable().get(i).getRowArrayList());
            if(newTree.get(value) != null){
                toHoldRows = (ArrayList) newTree.get(value);
            }
                    //rowsWithCorrespondingIndex(toHoldRows, i, columnName, value);//this should be adding the row with the corres
                toHoldRows.add(table.getMyTable().get(i));


            newTree.put(findKey(columnName,table, table.getMyTable().get(i).getRowArrayList()), toHoldRows);
        }
    }

    /**
     * finding a key
     * @param columnName
     * @param table
     * @param row
     * @return
     */
    private String findKey(String columnName, Table table, ArrayList<String> row){
        int ind = -1;
        for (int i = 0; i<columnDescription.length;i++){
            if (columnName.equals(columnDescription[i].getColumnName())){
                ind = i;
            }
        }
        return row.get(ind);
    }

    /**
     * getting rows with corresponding indexes
     * @param toHoldRows
     * @param i
     * @param columnName
     * @param value
     */
    private void rowsWithCorrespondingIndex(ArrayList toHoldRows, int i, String columnName, String value){
        int ind = -1;
        for (int j = 0; j<columnDescription.length;j++){
            if (columnName.equals(columnDescription[j].getColumnName())){
                ind = j;
            }
        }
            if (table.getMyTable().get(i).getRowArrayList().get(ind).equals(value)){
            toHoldRows.add(table.getMyTable().get(i));
        }

    }


}
