package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.*;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private Map<String, Table> dataBase;
    private boolean rSBoolean = true;
    private String tableName;
    private Table myTable;

    public DataBase() {
        this.dataBase = new HashMap<>();
        
    }

    /**
     * my driver method
     * @param SQL
     * @return a Result Set
     */
    public ResultSet execute(String SQL) {

        SQLQuery type = null;
        SQLParser parser = new SQLParser();

        try {
            type = parser.parse(SQL);

            if (type instanceof CreateTableQuery) {
                return createTable(type);
            } else if (type instanceof InsertQuery) {
                return insertQuery(type);
            } else if (type instanceof UpdateQuery) {
                return updateQuery(type);
            }else if (type instanceof DeleteQuery) {
               return deleteQuery(type);
            }else if (type instanceof SelectQuery) {
                return selectQuery(type);
            }else if (type instanceof CreateIndexQuery) {
                return indexQuery(type);
            }
        } catch (Exception e) {
            rSBoolean = false;
            ResultSet myRS = new ResultSet(rSBoolean);
            //e.printStackTrace();
            myRS.setMessage(e.getMessage());
            return myRS;
        }
        return new ResultSet(rSBoolean);
    }

    /**
     * executed if an indexQuery is inputted
     * @param type
     * @return true or false result set
     */
    private ResultSet indexQuery(SQLQuery type){
        CreateIndexQuery result=(CreateIndexQuery) type;
        myTable= dataBase.get(result.getTableName());
        Indexing indexing= new Indexing(result.getColumnName(),myTable);
        indexing.indexAColumn(result.getColumnName(),myTable);
        return new ResultSet(true);
    }

    /**
     * executed if an CreateTableQuery is inputted
     * @param type
     * @return empty table result set
     */
    private ResultSet createTable(SQLQuery type){
        CreateTableQuery result = (CreateTableQuery) type;
        myTable = new Table(result);
        tableName = myTable.getTableName();
        dataBase.put(tableName = myTable.getTableName(), myTable);
        return new ResultSet(myTable);
    }

    /**
     * executed if an insertQuery is inputted
     * @param type
     * @return true or false result set
     */
    private ResultSet insertQuery(SQLQuery type){
        InsertQuery result = (InsertQuery) type;
        myTable = dataBase.get(result.getTableName());
        myTable.addRow(result.getColumnValuePairs(), myTable.getColumnDescriptions());
        tableName = myTable.getTableName();
        dataBase.put(tableName = myTable.getTableName(), myTable);
        return new ResultSet(true);

    }


    /**
     * executed if an updateQuery is inputted
     * @param type
     * @return true or false result set
     */
    private ResultSet updateQuery(SQLQuery type){
        UpdateQuery result = (UpdateQuery) type;
        myTable = dataBase.get(result.getTableName());
        new Select(myTable).update(result.getWhereCondition(), result.getColumnValuePairs());
        tableName = myTable.getTableName();
        dataBase.put(tableName = myTable.getTableName(), myTable);
        return new ResultSet(true);
    }

    /**
     * executed if an deleteQuery is inputted
     * @param type
     * @return true or false result set
     */
    private ResultSet deleteQuery(SQLQuery type){
        DeleteQuery result = (DeleteQuery) type;
        myTable = dataBase.get(result.getTableName());
        new Select(myTable).delete(result.getWhereCondition()); //check if rows are equal
        tableName = myTable.getTableName();
        dataBase.put(tableName = myTable.getTableName(), myTable);
        return new ResultSet(true);
    }

    /**
     * executed if an selectQuery is inputted
     * @param type
     * @return a result set of the selected columns
     */
    private ResultSet selectQuery(SQLQuery type){
        SelectQuery result = (SelectQuery) type;
        tableName = result.getFromTableNames()[0];
        if (dataBase.get(tableName) == null){
            throw new IllegalArgumentException("table does not exist");
        }
        myTable = dataBase.get(tableName);
        return new Select(myTable).mySelect(result);
    }

    /**
     * the print method that i print out because it looks nicer
     * @param tableName
     */
    protected void printMethod(String tableName){
        dataBase.get(tableName).printTable();
    }

    /**
     * the method i use that is easier to test but does not look as nice
     * @return
     */
   @Override
    public String toString() {
        return myTable.toString();
    }

}

