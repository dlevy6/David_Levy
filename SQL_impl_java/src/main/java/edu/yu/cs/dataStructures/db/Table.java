package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnValuePair;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateTableQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Table {

    private String tableName;
    private List<Row> myTable;
    private ColumnDescription[] columnDescriptions;
    private ColumnDescription primaryKey;
    private HashMap<String, BTree> bTreeMap;



    protected Table(CreateTableQuery result){
        myTable = new ArrayList<>();
        this.tableName = result.getTableName();
        this.columnDescriptions = result.getColumnDescriptions();
        this.primaryKey = result.getPrimaryKeyColumn();
        this.bTreeMap = new HashMap<>();

    }

    protected HashMap<String, BTree> getbTreeMap() {
        return bTreeMap;
    }

    protected String getTableName() {
        return tableName;
    }

    protected ColumnDescription[] getColumnDescriptions() {
        return columnDescriptions;
    }

    /**
     * inserting row to table
     * @param columnValuePairs
     * @param columnDescriptions
     */
    protected void addRow(ColumnValuePair[] columnValuePairs, ColumnDescription[] columnDescriptions){

        for (ColumnDescription cd : columnDescriptions) {
            for (ColumnValuePair cvp : columnValuePairs) {
                if (cvp.getColumnID().getColumnName().equals(cd.getColumnName())) {

                    if (cd.isUnique() || cd.equals(primaryKey)) {

                        checkingIsUnique(cd.getColumnName(), cvp.getValue());
                    }
                }
            }
        }
        Row cRow = new Row(columnValuePairs, columnDescriptions, primaryKey);
        //checkindexing(cRow, columnDescriptions);
        fixInsertBtree(cRow, this);
        myTable.add(cRow);
    }


    /**
     * insert for indexed btree
     * @param row
     * @param table
     */
    private  void fixInsertBtree(Row row, Table table){
        ArrayList rowStuff= row.getRowArrayList();

        for(int i=0; i<row.getRowArrayList().size();i++){
            if(bTreeMap.get(columnDescriptions[i].getColumnName())!=null){
                BTree currentBTree= bTreeMap.get(columnDescriptions[i].getColumnName());
                ArrayList fromBtree= new ArrayList();
                if((currentBTree.get((String) rowStuff.get(i))!=null)){
                    fromBtree= (ArrayList) currentBTree.get(i);
                    fromBtree.add(row);

                }
                else{
                    fromBtree.add(row);
                    currentBTree.put((String) rowStuff.get(i),fromBtree);
                }
            }

        }
    }


    /**
     *check if unique
     * @param cn
     * @param value
     */
    protected void checkingIsUnique(String cn, String value){
        int index = -1;
        for (int i=0; i<columnDescriptions.length; i++){
            if(columnDescriptions[i].getColumnName().equals(cn)){
                index = i;
            }
        }
        if (index == -1){
            //throw error
        }
        for (Row row: myTable){
            if(row.getRowArrayList().get(index).equals(value)){
                throw new IllegalArgumentException("value is not unique and must be");
            }
        }
    }

    /**
     * print table mainly for dbtest
     * @return
     */
    protected List<Row> getMyTable() {
        return myTable;
    }

    protected void printTable(){
        System.out.println();
        for (ColumnDescription cd: columnDescriptions) {
            System.out.print(String.format("%20s", cd.getColumnName()));
        }
        System.out.println();
        for (ColumnDescription cd: columnDescriptions) {
            System.out.print(String.format("%20s", cd.getColumnType()));
        }
        System.out.println("\n");
        //throwing exception
        for( Row r: myTable){
            r.printString();
        }
        System.out.print("\n\n");
    }

    @Override
    public String toString() {
        String f = "";
        for (ColumnDescription cd: columnDescriptions) {
            f += cd.getColumnName() + " ";
        }
        f += "\n";
        for (ColumnDescription cd: columnDescriptions) {
            f += cd.getColumnType() + " ";
        }

        for( Row r: myTable){
            f += "\n" + r.toString();
        }
        return f;
    }

    protected ColumnDescription getPrimaryKey() {
        return primaryKey;
    }
}

