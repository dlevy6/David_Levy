package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;

public class DataCheck {
    private ColumnDescription cd;
    private Object value;

    /**
     * this class checks that each value for column is of correct type and size
     * @param cd
     * @param value
     */
    protected DataCheck(ColumnDescription cd, Object value) {
        this.cd = cd;
        this.value = value;
        isRightDatatype();

    }

    /**
     * checking if each value is correctly matching the column type
     * @throws IllegalArgumentException if not correct type
     */
    private void isRightDatatype(){//need to fix
        try {
            switch (cd.getColumnType()) {
                case INT:
                    Integer.parseInt(value.toString());
                    break;
                case DECIMAL:
                    checkingWholeNumberLength(value.toString(), cd.getWholeNumberLength());
                    checkingFractionalLength(value.toString(), cd.getFractionLength());
                    break;
                case BOOLEAN:
                    if (!(value.equals("true") || value.equals("false"))){
                        throw new IllegalArgumentException("not a boolean");
                    }
                    break;
                case VARCHAR:
                    checkingVarCharLength(value.toString(), cd.getVarCharLength());
                    break;
                default:
                    throw new IllegalArgumentException("not correct type");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("not right data type");

        }
    }

    /**
     * check the lengths after the decimal point
     * @throws IllegalArgumentException if the lengths is too long
     * @param value
     * @param theSize
     */
    private void checkingFractionalLength(String value, int theSize){//fix
            Double.parseDouble(value);

        int length =-1;
        for (int i =0; i<value.length(); i++){
            if(value.charAt(i) == '.'){
                length = 0;
            }else if(length >=0){
                length++;
            }
        }
//check
        if (theSize < length){
            throw new IllegalArgumentException("fractional length is not correct");
        }
    }


    /**
     * checks the lengths of the number before the decimal point
     * @throws IllegalArgumentException if the lengths is to long
     * @param value
     * @param theSize
     */
    private void checkingWholeNumberLength(String value, int theSize){// is for int also?
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {

            throw new IllegalArgumentException("wrong whole number length");
        }
        value.toString();
        int length = 0;
        while (length != value.length() && !(value.charAt(length) == '.')){

            length++;
        }


        if (length > theSize){
            throw new IllegalArgumentException(" Whole number length is not correct");
        }
    }

    /**
     * checks the lengths of VARCHAR values
     * @throws IllegalArgumentException if the lengths is too long
     * @param value
     * @param theSize
     */
    private void checkingVarCharLength(String value, int theSize){
        if (value.length() > theSize){
            throw new IllegalArgumentException(" varchar number length is not correct");
        }
    }
}
