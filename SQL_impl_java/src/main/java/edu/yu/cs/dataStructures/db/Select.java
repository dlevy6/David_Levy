package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Select {

    private List<Row> whereconditionArrayList;
    private List<Row> myTable;
    private String tableName;
    private ColumnDescription[] columnDescriptions;
    private List<List<String>> selectedColumns;
    private ColumnID[] selectedCIDs;
    private ColumnID[] selectColumnIDsWithFunction;
    private Table table;
    private HashMap<String, BTree> bTreeMap;


    protected Select(Table myTable) {
        this.table = myTable;
        this.myTable = myTable.getMyTable();
        this.tableName = myTable.getTableName();
        this.columnDescriptions = myTable.getColumnDescriptions();
        this.bTreeMap = myTable.getbTreeMap();
    }



    /**
     *where condition iterate the rows
     * @param condition
     * @return
     */
    protected List<Row> where(Condition condition){
        whereconditionArrayList  = new ArrayList<>();
        for (Row row : myTable){
            if (condition == null){
                whereconditionArrayList.add(row);
            }else if (checkingEachRowForWhere(row, condition)){
                whereconditionArrayList.add(row);
            }

        }
        return whereconditionArrayList;

    }


    /**
     *where condition check each row given by where
     * @param row
     * @param condition
     * @return
     */
    private boolean checkingEachRowForWhere(Row row, Condition condition){
        if (condition.getOperator().toString().equals("AND")){
            if((checkingEachRowForWhere(row, (Condition) condition.getLeftOperand()) && (checkingEachRowForWhere(row, (Condition) condition.getRightOperand())))){
                return true;
            }
        }
        else if (condition.getOperator().toString().equals("OR")){
            if((checkingEachRowForWhere(row, (Condition) condition.getLeftOperand()) || (checkingEachRowForWhere(row, (Condition) condition.getRightOperand())))){
                return true;
            }
        }
        else {
            if(checkEachCondition(row, condition)) {
                return true;
            }
        }
        return false;
    }

    /**
     *evaluate each condition given  checkEachRowForWhere
     * @param row
     * @param condition
     * @return
     */
    private boolean checkEachCondition(Row row, Condition condition){
        try {
            switch (condition.getOperator()){
                case NOT_EQUALS: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase((String) condition.getRightOperand()) != 0);

                case GREATER_THAN_OR_EQUALS: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase(((String) condition.getRightOperand())) >= 0);

                case LESS_THAN_OR_EQUALS: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase(((String) condition.getRightOperand())) <= 0);

                case GREATER_THAN: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase(((String) condition.getRightOperand())) > 0);

                case LESS_THAN: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase(((String) condition.getRightOperand())) < 0);

                case EQUALS: return ((row.getRowArrayList().get(findIndex(row, condition))).compareToIgnoreCase(((String) condition.getRightOperand())) == 0);

                default:
                    System.out.println("sign not found");//throw exception
            }
        } catch (Exception e) {
            System.out.println("index not found");
            e.printStackTrace();
        }
        return false; //check
    }

    /**
     * find the index number in table
     * @param row
     * @param condition
     * @return
     */
    private int findIndex(Row row, Condition condition){
        String column = condition.getLeftOperand().toString();
        int index = -1;
        for (int i=0; i<columnDescriptions.length; i++){
            if (column.equals(columnDescriptions[i].getColumnName())){
                index = i;
                return index;
            }
        }

        return -1;
    }

    /**
     * for delete queary
     * @param condition
     */
    protected void delete(Condition condition){
        ArrayList stuff = new ArrayList<>();
        List<Row> tmp = new ArrayList<>();
        if (condition != null) {
            stuff = bTreeWhere(condition, table);
            if (stuff == null || stuff.size() == 0) {

                tmp = where(condition);

            }else {
                stuff = incrementup(stuff);
                //castToMyTable(stuff);
                deleteDuplicates(stuff);
                checktmpt(stuff, condition);
                tmp = stuff;
            }
        }else {
            tmp = myTable;
        }
        for (int j=0; j<tmp.size(); j++){
            for (int i=0; i<myTable.size(); i++){
                if (myTable.get(i).equals(tmp.get(j)) ){
                    deleteFromBtree(table, myTable.get(i), columnDescriptions);
                    myTable.remove(i);
                }
            }
        }
    }

    /**
     * delete from the btree as well
     * @param table
     * @param currentRow
     * @param columnDescription
     */
    private void deleteFromBtree(Table table ,Row currentRow, ColumnDescription[] columnDescription){
        for (int k = 0; k<columnDescription.length; k++) {
            BTree bTree= table.getbTreeMap().get(columnDescription[k].getColumnName());
            if(bTree!=null){
                ArrayList rowsToEdit=(ArrayList) bTree.get(currentRow.getRowArrayList().get(k).toString());
                if(rowsToEdit!=null){
                    for(int i=0;i<rowsToEdit.size();i++){
                        if(rowsToEdit.get(i).equals(currentRow)){
                            rowsToEdit.remove(i);
                        }
                    }
                }

            }
        }

    }

    /**
     * updating the btree
     * @param table
     * @param newValue
     * @param old
     * @param currentRow
     * @param columnDescription
     */
    private void updateBtree(Table table,String newValue,String old,Row currentRow,String columnDescription){
        BTree bTree= table.getbTreeMap().get(columnDescription);
        if(bTree!=null){
           ArrayList rowsToEdit=(ArrayList) bTree.get(old);
           if(rowsToEdit!=null){
               for(int i=0;i<rowsToEdit.size();i++){
                   if(rowsToEdit.get(i).equals(currentRow)){
                       rowsToEdit.remove(i);
                   }
               }
           }
           if(bTree.get(newValue)!=null){
               ArrayList toAdd = (ArrayList) bTree.get(newValue);
               toAdd.add(currentRow);

           }
           if(bTree.get(newValue)==null){
               ArrayList newTreeValue= new ArrayList();
               newTreeValue.add(currentRow);
               bTree.put(newValue,newTreeValue);
           }
        }

    }

    /**
     * check row are correct
     * @param rows
     * @param condition
     */
    private void checktmpt(List<Row> rows, Condition condition){
        for (Row row : rows){
            checkingEachRowForWhere(row, condition);
        }
    }

    /**
     * for update query
     * @param condition
     * @param cvp
     */
    protected void update(Condition condition, ColumnValuePair[] cvp){
        ArrayList stuff = new ArrayList<>();
        List<Row> tmp = null;
        if (condition != null) {
            tmp = new ArrayList<>();
            stuff = bTreeWhere(condition, table);
            if (stuff == null || stuff.size() == 0) {
                tmp = where(condition);
            }else {
                stuff = incrementup(stuff);
                //castToMyTable(stuff);
                deleteDuplicates(stuff);
                checktmpt(stuff, condition);
                tmp = stuff;
            }
        }else {
            tmp = myTable;
        }
        for (int i=0; i<tmp.size(); i++){
            checkUnique(cvp);
            for (ColumnValuePair cp : cvp){
                new DataCheck(columnDescriptions[matchIdtoColumnDescription(cp.getColumnID())], cp.getValue());
                String oldV = tmp.get(i).getRowArrayList().get(matchIdtoColumnDescription(cp.getColumnID()));
                tmp.get(i).getRowArrayList().set(matchIdtoColumnDescription(cp.getColumnID()), cp.getValue());
                updateBtree(table, cp.getValue(), oldV, tmp.get(i), cp.getColumnID().getColumnName());
            }
        }

    }


    /**
     * btree update
     * @param columnName
     * @param rows
     * @param condition
     */
    private void updateTheRow(Object columnName, ArrayList rows, Condition condition){
        BTree btree = bTreeMap.get(columnName);
        for(Object row1 :rows){
            Row row = (Row) row1;
            if(btree.get(row.getRowArrayList().get(findIndex(row, condition))) != null){
                ArrayList alist = (ArrayList) btree.get(row.getRowArrayList().get(findIndex(row, condition)));
                alist.set(findIndex(row, condition), row);

            }


        }
    }

    /**
     * check for unique in if is unique
     * @param columnValuePairs
     */
    private void checkUnique(ColumnValuePair[] columnValuePairs){
        for (ColumnDescription cd : columnDescriptions) {
            for (ColumnValuePair cvp : columnValuePairs) {
                if (cvp.getColumnID().getColumnName().equals(cd.getColumnName())) {

                    if (cd.isUnique() || cd.equals(columnDescriptions[0])) {

                        checkingIsUnique(cd.getColumnName(), cvp.getValue());
                    }
                }
            }
        }
    }

    /**
     * if unique passed from checkUnique
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
     * finding colummn
     * @param Id
     * @return
     */
    private int matchIdtoColumnDescription(ColumnID Id){
        int index = -1;
        for (int i=0; i<columnDescriptions.length; i++){
            if (columnDescriptions[i].getColumnName().equals(Id.getColumnName())){
                index = i;
            }
        }
        return index;
    }
//////////////////

    /**
     * getting from btree
     * @param condition
     * @param table
     * @return
     */
    private ArrayList<Object> bTreeWhere(Condition condition, Table table){

        ArrayList<Object> rowHolder= new ArrayList<>();
        if( condition.equals("OR")){
             bTreeWhere((Condition) condition.getLeftOperand(), table) ;
          bTreeWhere((Condition) condition.getRightOperand(), table);
        }
        BTree<String, ArrayList<Row>> currentBtree= table.getbTreeMap().get(condition.getLeftOperand().toString());
        if(currentBtree!=null) {//SHould be the columnName edit how you need
            switch (condition.getOperator()) {
                case EQUALS:
                    return rowHolder =  (ArrayList) currentBtree.getEqual(condition.getRightOperand().toString());
                case LESS_THAN:
                    return rowHolder = (ArrayList)  currentBtree.getLessThan(condition.getRightOperand().toString());
                case LESS_THAN_OR_EQUALS:
                    return rowHolder = (ArrayList)  currentBtree.getLessThanOrEqual(condition.getRightOperand().toString());
                case GREATER_THAN:
                    return rowHolder = (ArrayList)  currentBtree.getGreaterThan(condition.getRightOperand().toString());
                case GREATER_THAN_OR_EQUALS:
                    return rowHolder = (ArrayList)  currentBtree.getGreaterThanOrEqual(condition.getRightOperand().toString());

                case NOT_EQUALS:
                    return  rowHolder = (ArrayList)  currentBtree.getNotEqual(condition.getRightOperand().toString());
            }
        }
        return null;
    }


    /**
     * for select query
     * @param selectQuery
     * @return
     */
    protected ResultSet mySelect(SelectQuery selectQuery){
           ArrayList stuff = new ArrayList<>();
        if(selectQuery.getWhereCondition()!=null) {
            stuff = bTreeWhere((Condition) selectQuery.getWhereCondition(), table);
        }if (stuff == null || stuff.size() == 0) {
            myTable = where(selectQuery.getWhereCondition());
        }else {
            stuff = incrementup(stuff);
            //castToMyTable(stuff);
            deleteDuplicates(stuff);
            checktmpt(stuff, selectQuery.getWhereCondition());
            myTable = stuff;

        }
        selectedCIDs = selectQuery.getSelectedColumnNames();
        selectedColumns = new ArrayList<List<String>>();
        fillUpListWithNul(selectedCIDs, selectedColumns);
        fillUpList();
        fillColumnIDwithFunction(selectQuery);
        checkIfSelecthasfunction(selectQuery, selectedColumns, selectedCIDs);
        if (selectQuery.isDistinct()){
            checkIsDistinct(selectQuery, selectedColumns, selectedCIDs);
        }
        checkOrederby(selectQuery.getOrderBys(),selectedColumns,selectedCIDs); // is there always an orderby?

        List<Object> rsetList =  convertToDataType();
        return new ResultSet(selectColumnIDsWithFunction,  tableName, rsetList, columnDescriptions, selectedCIDs);
    }

    /**
     * deleting any duplicates in array
     * @param rowlist
     */
    private void deleteDuplicates(ArrayList<Row> rowlist){
        for (int i =0; i<rowlist.size(); i++){
            for (int j =0; j<rowlist.size(); j++){
                if (i != j){
                    if (rowlist.get(i).equals(rowlist.get(j))){
                        rowlist.remove(j);
                    }
                }
            }
        }
    }


    /**
     * dealing with nested arraylist problem
     * @param mylist
     * @return
     */

private ArrayList incrementup(ArrayList<ArrayList> mylist){
    ArrayList<Row> mylist3 = new ArrayList<>();
    ArrayList<Row> mylist4 = new ArrayList<>();
    ArrayList<Row> mylist2 = new ArrayList<>();
    for (int i = 0; i<mylist.size();i++) {
        mylist2 = (ArrayList<Row>) mylist.get(i);

        for (int j = 0; j < mylist2.size(); j++) {
            mylist3.add(mylist2.get(j));

        }
    }

        for (int j = 0; j< mylist3.size(); j++) {
            mylist4.add(mylist3.get(j));
        }

    return mylist4;
}

///////////////////

    /**
     * filling up selectedcolumns with values
     */
    private void fillUpList() {

        if(selectedCIDs[0].getColumnName().equals("*")){
            for (int i = 0; i< myTable.size();i++){
                selectedColumns.set(i,myTable.get(i).getRowArrayList());
            }
            selectedCIDs = new ColumnID[columnDescriptions.length];
            for (int i=0; i< columnDescriptions.length; i++){
                ColumnID tmpID = new ColumnID(columnDescriptions[i].getColumnName(), tableName);
                selectedCIDs[i] = tmpID;
            }
        }else {

            for (int i = 0; i < selectedCIDs.length; i++) {
                List<String> columnValues = getColumn(selectedCIDs[i].getColumnName());
                for (int h = 0; h < columnValues.size(); h++) {
                    selectedColumns.get(h).set(i, columnValues.get(h));
                }
            }
        }
    }

    /**
     * inserting null into selectedColumns to correct size
     * @param selectedCIDs
     * @param selectedColumns
     */
    private void fillUpListWithNul(ColumnID[] selectedCIDs,   List<List<String>> selectedColumns){
        for (int i=0; i< myTable.size(); i++) {//filling up array
            List<String> nList= new ArrayList<>();
            for (int j = 0; j<selectedCIDs.length; j++) {
                nList.add(null);
            }
            selectedColumns.add(nList);
        }
    }

    /**
     * checking if select query has a function
     * @param selectQuery
     * @param selectedColumns
     * @param selectedCIDs
     */
    private void checkIfSelecthasfunction(SelectQuery selectQuery,  List<List<String>> selectedColumns, ColumnID[] selectedCIDs){
        for (SelectQuery.FunctionInstance h : selectQuery.getFunctions()){
            for (int i=0; i< selectedCIDs.length; i++) {
                String columnName = h.column.getColumnName();
                if (h.column.getColumnName() == (selectedCIDs[i].getColumnName())) {
                    if (h.isDistinct == true){
                        checkColumnForDistinct(getColumnForFunction(h.column.getColumnName(), selectedColumns, selectedCIDs), selectedColumns);
                        columnName = "DISTINCT " + h.column.getColumnName();
                    }
                    List<String> functionColumn = getColumnForFunction(h.column.getColumnName(),selectedColumns, selectedCIDs);
                    String functionResult = functionParser(h, functionColumn) ;
                    for (int j =0; j<selectedColumns.size(); j++){
                        selectedColumns.get(j).set(i, functionResult);
                    }
                    addFunctionNameToColumnDescription(columnName, h.function.toString(), selectedCIDs, i, selectQuery);
                    i = selectedCIDs.length;
                }
            }
        }
    }

    /**
     * changing columnName to include function first step
     * @param selectQuery
     */
    private void fillColumnIDwithFunction(SelectQuery selectQuery){
        selectColumnIDsWithFunction = new ColumnID[selectedCIDs.length];
        for (int i = 0; i < selectedCIDs.length; i++) {
            selectColumnIDsWithFunction[i] = selectedCIDs[i];
        }

    }

    /**
     * adding the function name to the column description
     * @param columnName
     * @param functionName
     * @param selectedCIDs
     * @param cidNumber
     * @param selectQuery
     */
    private void addFunctionNameToColumnDescription(String columnName, String functionName ,ColumnID[] selectedCIDs, int cidNumber,SelectQuery selectQuery){

                        ColumnID tmpID = new ColumnID(functionName + "(" + columnName + ")", tableName);
                        selectColumnIDsWithFunction[cidNumber] = tmpID;


    }


    /**
     * parsing the functions
     * @param h
     * @param functionColumn
     * @return
     */

    private String functionParser(SelectQuery.FunctionInstance h, List<String> functionColumn){
        String columnType = getColumnType(h.column.getColumnName());
        switch (h.function) {
            case AVG:
                return avg(h.column.getColumnName(), functionColumn, columnType);
            case MAX:
                return max(h.column.getColumnName(), functionColumn, columnType);
            case MIN:
                return min(h.column.getColumnName(), functionColumn, columnType);
            case SUM:
                return sum(h.column.getColumnName(), functionColumn, columnType);
            case COUNT:
                return count(h.column.getColumnName(), functionColumn, columnType);
        }
        return null;
    }

    private String getColumnType(String columnName){
        String columnType = "";
        for (int i =0; i< columnDescriptions.length; i++){
            if(columnName.equals(columnDescriptions[i].getColumnName())){
                columnType = columnDescriptions[i].getColumnType().toString();
            }
        }
        return columnType;
    }

    /**
     * check if distinct column
     * @param selectQuery
     * @param selectedColumns
     * @param selectedCIDs
     */
    private void checkIsDistinct(SelectQuery selectQuery,  List<List<String>> selectedColumns, ColumnID[] selectedCIDs){
        for (int i =0; i<selectedColumns.size(); i++){
            for (int j =0; j<selectedColumns.size(); j++){
                if (selectedColumns.get(i).equals(selectedColumns.get(j)) && i!=j){
                    selectedColumns.remove(j);
                }
            }
        }
    }

    /**
     * checking if column distinct
     * @param theColumn
     * @param selectedColumns
     */
    private void checkColumnForDistinct(List<String> theColumn,  List<List<String>> selectedColumns){
       // Integer theSize = theColumn.size();
        for (int i =0; i<theColumn.size(); i++){
            for (int j =0; j<theColumn.size(); j++){
                if (theColumn.get(i).equals(theColumn.get(j)) && i!=j){
                    selectedColumns.remove(j);
                    theColumn.remove(j);
                }
            }
        }

    }

    //-----------------------------------------------------------order by--------------------------------------------------------------------------------


    /**
     * if there are any order by columns this will execute
     * @param theOrderBy
     * @param selectedColumns
     * @param selectedCIDs
     */
    private void checkOrederby(SelectQuery.OrderBy[] theOrderBy, List<List<String>> selectedColumns, ColumnID[] selectedCIDs){

        for (int i = theOrderBy.length-1; i>= 0; i--){
            int hold = 1;
            for (int j=0; j<selectedCIDs.length; j++){
                if (theOrderBy[i].getColumnID().getColumnName().equals(selectedCIDs[j].getColumnName())){ //i is the column number
                    List<String> tempList = getColumnForFunction(selectedCIDs[j].getColumnName(), selectedColumns, selectedCIDs);
                    qsort(tempList, 0, tempList.size()-1);
                    tempList = checkAscendingDescendig(tempList, theOrderBy[i]);
                    theOrderBySort(tempList, selectedColumns, theOrderBy[i], selectedCIDs);
                    hold = 2;
                }
            }
            if (hold == 1){
                throw new IllegalArgumentException("order by column does not exist");
            }
        }

    }

    /**
     * sorting rows for order by
     * @param currentcolumn
     * @param selectedColumns
     * @param theorderby
     * @param selectedCIDs
     */
    private void theOrderBySort(List<String> currentcolumn,  List<List<String>> selectedColumns, SelectQuery.OrderBy theorderby, ColumnID[] selectedCIDs){
        int index = -1;
        for (int i = 0; i< selectedCIDs.length; i++){
            if (theorderby.getColumnID().getColumnName().equals(selectedCIDs[i].getColumnName())){
                index=i;
            }
        }
        for (int i = currentcolumn.size()-1; i>= 0; i--){
            for (int j = 0; j<selectedColumns.size(); j++){
                if (currentcolumn.get(i).equals(selectedColumns.get(j).get(index)) && i != j){
                    swapRow(i, j, selectedColumns);
                }
            }
        }

    }

    /**
     * orderby swap
     * @param i
     * @param j
     * @param selectedColumns
     */
    private void swapRow(int i, int j, List<List<String>> selectedColumns){
        List <String> tmpList;
        tmpList = selectedColumns.get(j);
        selectedColumns.set(j, selectedColumns.get(i));
        selectedColumns.set(i, tmpList);
    }

    /**
     * reverse order ascending descinding
     * @param currentcolumn
     * @param theorderby
     * @return
     */
    private List<String> checkAscendingDescendig(List<String> currentcolumn,SelectQuery.OrderBy theorderby){
        if(theorderby.isAscending()){

        }
        else if(theorderby.isDescending()){
            List<String> tmpList = new ArrayList<>();
            while (currentcolumn.size() != 0){
                tmpList.add(currentcolumn.get(currentcolumn.size()-1));
                currentcolumn.remove(currentcolumn.size()-1);
            }
            currentcolumn = tmpList;
        }
        return currentcolumn;
    }

    /**
     * quicksort
     * got from: http://www.learntosolveit.com/java/GenericQuicksortComparable.html
     * altered slightly
     * @param arr
     * @param a
     * @param b
     */

    private void qsort(List<String> arr, int a, int b) {
        if (a < b) {
            int i = a, j = b;
            String x = arr.get((i+j)/2);


            do {
                while (arr.get(i).compareTo(x) < 0) i++;
                while (x.compareTo(arr.get(j)) < 0) j--;

                if ( i <= j) {
                    String tmp = arr.get(i);
                    arr.set(i,arr.get(j));
                    arr.set(j,tmp);
                    i++;
                    j--;
                }

            } while (i <= j);

            qsort(arr, a, j);
            qsort(arr, i, b);
        }
    }




    //----------------------------------------------------------------end of order by------------------------------------------------------------------------------------


//count function
    private String count(String columnName, List<String> functionColumn, String columnType) {//for distinct
        Integer holder = functionColumn.size();
        return holder.toString();
    }
    // sum fuction
    private String sum(String columnName, List<String> functionColumn, String columnType){

        String sum = "0";
        Object holder = null;
            if (columnType.equals("DECIMAL")) {
                for (int i = 0; i< functionColumn.size(); i++) {
                    holder = Double.parseDouble(sum) + Double.parseDouble(functionColumn.get(i));
                    sum = holder.toString();
                }
            }else if (columnType.equals("INT")){
                for (int i = 0; i< functionColumn.size(); i++) {
                    holder = Integer.parseInt(sum) + Integer.parseInt(functionColumn.get(i));
                    sum = holder.toString();
                }
            }else {
                throw new IllegalArgumentException("wrong Data Type for sum");
            }
            sum = holder.toString();

        return sum;
    }
    //min function
    private String min(String columnName, List<String> functionColumn, String columnType){
        String min = null;
        Object holder = null;
        if (columnType.equals("DECIMAL")) {
            Double mi =Double.MAX_VALUE;
            min = mi.toString();
            for (int i = 0; i< functionColumn.size(); i++) {
                if (Double.parseDouble(functionColumn.get(i)) < Double.parseDouble(min)) {
                    min = functionColumn.get(i);
                }
            }
        }else if (columnType.equals("INT")){
            Integer mi = Integer.MAX_VALUE;
            min = mi.toString();
                for (int i = 0; i< functionColumn.size(); i++) {
                    if (Integer.parseInt(functionColumn.get(i)) < Integer.parseInt(min)) {
                        min = functionColumn.get(i);
                    }
                }
        }else {
            throw new IllegalArgumentException("wrong Data Type for sum");
        }
        return min.toString();


    }
    //max function
    private String max(String columnName, List<String> functionColumn, String columnType){
        String max = "";
        for (int i = 0; i< functionColumn.size(); i++){
            if (functionColumn.get(i).compareTo(max) >0){
                max = functionColumn.get(i);
            }
        }
        return max;
    }
    //avg function
    private String avg(String columnName, List<String> functionColumn, String columnType){
        Double average= 0.00;

        for (int i=0; i<functionColumn.size(); i++){
            average += Double.parseDouble(functionColumn.get(i));

        }
        average = average / functionColumn.size();

        if (columnType.equals("INT")){
            Integer avg = average.intValue();
            return avg.toString();
        }

        return average.toString();
    }

    /**
     * getting column values in column
     * @param columnName
     * @param table
     * @param selectedCIDs
     * @return
     */
    private List<String> getColumnForFunction(String columnName, List<List<String>> table, ColumnID[] selectedCIDs){
        List<String> nList = new ArrayList<>();
        int index = -1;
        for (int i=0;i<selectedCIDs.length;i++){
            if (selectedCIDs[i].getColumnName().equals(columnName)){
                index = i;
            }
        }
        for (int i=0;i<selectedColumns.size();i++){
            nList.add(selectedColumns.get(i).get(index));
        }
        return nList;
    }

    /**
     * getting column values in column
     * @param columnName
     * @return
     */
    private List<String> getColumn(String columnName){
        int index=-1;
        List<String> columnNames = new ArrayList<>();
        for (int i=0; i<columnDescriptions.length; i++) {
            if (columnDescriptions[i].getColumnName().equals(columnName)) {
                index = i;
            }
        }
        for (Row row : myTable){
            columnNames.add(row.getRowArrayList().get(index));
        }

        return columnNames;
    }

    /**
     * convert table to datatype
     * @return
     */
    private List<Object> convertToDataType(){
        List<Object> dataTypeList = new ArrayList<>();
        for (List<String> row : selectedColumns){
            dataTypeList.add(convertEachRow(row));
        }
        return dataTypeList;
    }

    /**
     * convert to datatype part2
     * @param row
     * @return
     */
    private List<Object> convertEachRow(List<String> row) {
        List<Object> rowDataTypeList = new ArrayList<>();
        for (int i=0; i<selectedCIDs.length; i++){
            for (int j=0; j<columnDescriptions.length; j++){
                if (selectedCIDs[i].getColumnName().equals(columnDescriptions[j].getColumnName())){
                    switch (columnDescriptions[j].getColumnType()){
                        case VARCHAR:
                            rowDataTypeList.add(row.get(i).toString());
                            break;
                        case BOOLEAN:
                            rowDataTypeList.add(Boolean.parseBoolean(row.get(i)));
                            break;
                        case DECIMAL:
                            rowDataTypeList.add(Double.parseDouble(row.get(i)));
                            break;
                        case INT:
                            rowDataTypeList.add(Integer.parseInt(row.get(i)));
                            break;
                    }
                }
            }
        }
        return rowDataTypeList;
    }


}
