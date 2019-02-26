package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SQLParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Testing {



    private DataBase createTale(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query = "CREATE TABLE YCStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " Class varchar(255)," //added
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");";
        db.execute(query);

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Ploni', 7486, 'Almoni',2.0, 'Junoir',800012347);");
        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('David', 4857,'Doe',1.0, 'Senior',800012345);");
        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 7446,'Greenberg', 3.0, 'Senior',800022345);");
        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', 7386, 'Faigen', 4.0, 'Freshman',800012845);");
        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Albert', 7416, 'Smith', 2.0, 'Super Senior',800012346);");
        db.execute("INSERT INTO YCStudent (FirstName, CurrentStudent, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', false, 7336, 'Faigen', 3.0, 'Freshman',810012845);").printMethod();


        /*String  whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";*/
        return db;
    }


    private DataBase createTable2(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query =  "CREATE TABLE SymStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " Class varchar(255)," //added
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");";
        db.execute(query);

        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Nick', 3875, 'Schmo',4.0, 'Junoir',230012347);");
        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Sam', 384,'Nack',3.9, 'Senior',800012445);");
        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 4875,'Chin', 3.8, 'Senior',803022345);");

        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n " +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";


        return db;
    }


    @Test
    public void happyPathtTestEmptyTable(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query = "CREATE TABLE YCStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " Class varchar(255)," //added
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");";
        db.execute(query);
        String whatImTestingAgainst ="CurrentStudent Class GPA FirstName BannerID LastName SSNum \n" +
                "BOOLEAN VARCHAR DECIMAL VARCHAR INT VARCHAR INT ";
        assertEquals(whatImTestingAgainst, db.toString());


    }


    @Test
    public void TestCreateBadInputNoPrimaryKey(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query = "CREATE TABLE YCStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + ");";
        db.execute(query);
        assertEquals("false", db.execute(query).getBool());
    }
    @Test
    public void TestInsert(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query = "CREATE TABLE YCStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");";
        db.execute(query);
        assertEquals("true", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni',4.0, 'Senior',800012345);").getBool());

        String whatImTestingAgainst ="BannerID GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012345 4.0 'Ploni' true 'Almoni' null ";

        assertEquals(whatImTestingAgainst, db.toString());
    }





    //bad insert tests



    @Test
    public void TestWrongDataType(){
        DataBase db = createTale();
        //puting string where int belongs
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni','hi', 'Senior',800012345);").getBool());

        //decimal puting where int belongs
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni',4.0, 'Senior',800012345.5);").getBool());



        //putting not boolean where boolean should go
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID, CurrentStudent) VALUES ('Ploni','Almoni',4.0, 'Senior',800012345, 'hi');").getBool());

        //string
        //db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni',4.0, 'Senior',800012345);");


        //testing no changes occured
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals(whatIsExpected, db.toString());
    }


    @Test
    public void TestUniqueNotNull(){
        DataBase db = createTale();
        //a unique column is SSNum and a not null is lastname
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";


        //testing not null
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, SSNum, GPA, Class, BannerID) VALUES ('qq', 3386, '2.0, 'Junoir',550012347);").getBool());

        //testing unique
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('dd', 7486, 'ggg',2.0, 'Junoir',803312347);").getBool());



        //testing that table hasnt changed
        assertEquals(whatIsExpected, db.toString());
    }


    @Test
    public void TestInsertWronglenghts(){
        DataBase db = createTale();

        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";


        //left of decimal might have to change to just cut
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni',4.000, 'Senior',800012345);").getBool());
        //right of decimal
        assertEquals("false", db.execute("INSERT INTO YCStudent (FirstName, LastName, GPA, Class, BannerID) VALUES ('Ploni','Almoni',44.0, 'Senior',800012345);").getBool());

        assertEquals(whatIsExpected, db.toString());
    }
    ////////////////////////////////////testing update///////////////////////////////////////////////////////////////////////////

    @Test
    public void testUpdate(){
        DataBase db = createTale();
        String  whatIsExpectedPriorToUpdates = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";

        assertEquals(whatIsExpectedPriorToUpdates, db.toString());
        assertEquals("true", db.execute("UPDATE YCStudent SET GPA=3.0,Class='Super Senior' WHERE BannerID=800012345;").getBool());
        String  whatIsExpectedAfterUpdate1 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Super Senior' 3.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals(whatIsExpectedAfterUpdate1, db.toString());

        assertEquals("true", db.execute("UPDATE YCStudent SET GPA=3.0,Class='Super Senior';").getBool());
        String  whatIsExpectedAfterUpdate2 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Super Senior' 3.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Super Senior' 3.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Super Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Super Senior' 3.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 3.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Super Senior' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals(whatIsExpectedAfterUpdate2, db.toString());



//        assertEquals()
    }

    @Test
    public void updateBadInput(){

        DataBase db = createTale();
        String  whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";


        //unique
       assertEquals("false", db.execute("UPDATE YCStudent SET BannerID=800012345;").getBool());

        //wrongfactionallengths
        assertEquals("false", db.execute("UPDATE YCStudent SET GPA=33.0 WHERE BannerID=800012345;").getBool());
        assertEquals("false", db.execute("UPDATE YCStudent SET GPA=3.033 WHERE BannerID=800012345;").getBool());
        //not bool
        assertEquals("false", db.execute("UPDATE YCStudent SET CurrentStudent=3.0 WHERE BannerID=800012345;").getBool());
        //not dec/int
        assertEquals("false", db.execute("UPDATE YCStudent SET GPA='hi' WHERE BannerID=800012345;").getBool());
        assertEquals("false", db.execute("UPDATE YCStudent SET SSNum=3.0 WHERE BannerID=800012345;").getBool());

        //making sure nothing changed
        assertEquals(whatIsExpected, db.toString());
    }



    //////////////////////////////////testing delete//////////////////////////////////////////////////


    @Test
    public void testingDelete(){

        DataBase db = createTale();
        String  whatIsExpectedPriorToChanges = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals("true", db.execute("DELETE FROM YCStudent WHERE FirstName='hi';").getBool());
        assertEquals(whatIsExpectedPriorToChanges, db.toString());

        assertEquals("true", db.execute("DELETE FROM YCStudent WHERE Class='Super Senior';").getBool());
        String  whatIsExpected2 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals(whatIsExpected2, db.toString());

        assertEquals("true", db.execute("DELETE FROM YCStudent WHERE Class='Freshman' AND GPA > 3.0;").getBool());
        String  whatIsExpected3 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        assertEquals(whatIsExpected3, db.toString());

        assertEquals("true", db.execute("DELETE FROM YCStudent WHERE Class='Senior' OR CurrentStudent=false;").getBool());
        String  whatIsExpected4 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 ";
        assertEquals(whatIsExpected4, db.toString());



    }




    ///////////////////select/////////////////////////////////////////


    @Test
    public void testingSelect(){
        DataBase db = createTable2();
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
        "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
        "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
        "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
        "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";

        String assert1= "FirstName LastName GPA \n" +
                "VARCHAR VARCHAR DECIMAL \n" +
                "'Nick' 'Schmo' 4.0 \n" +
                "'Sam' 'Nack' 3.9 \n" +
                "'John' 'Chin' 3.8 ";


       assertEquals(assert1, db.execute("SELECT FirstName, LastName, GPA FROM SymStudent;").toString());

       DataBase db2 = createTale();
        String  whatIsExpectedFrombd2 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        String assert2 = "FirstName LastName GPA \n" +
                "VARCHAR VARCHAR DECIMAL \n" +
                "'Nick' 'Schmo' 4.0 \n" +
                "'Sam' 'Nack' 3.9 \n" +
                "'John' 'Chin' 3.8 \n" +
                "'Ploni' 'Almoni' 2.0 \n" +
                "'David 'Doe' 1.0 \n" +
                "'John' 'Greenberg' 3.0 \n" +
                "'Jessica' 'Faigen' 4.0 \n" +
                "'Albert' 'Smith' 2.0 \n" +
                "'Hessica' 'Faigen' 3.0 ";
        //test multiple tables
        //assertEquals(assert2, db2.execute("SELECT FirstName, LastName, GPA FROM SymStudent, YCStudent;").toString());

        String assert3 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 ";


        assertEquals(assert3, db2.execute("select * from YCStudent WHERE CurrentStudent=TRUE;").toString());

        String assert4 = "FirstName LastName BannerID \n" +
                "VARCHAR VARCHAR INT \n" +
                "'Nick' 'Schmo' 230012347 ";
        assertEquals(assert4, db.execute("select FirstName, LastName, BannerID from SymStudent where BannerID=230012347;").toString());



        //making sure no changes to table
        assertEquals(whatIsExpected, db.toString());
        assertEquals(whatIsExpectedFrombd2, db2.toString());
    }

    @Test
    public void testingSelectFunctions(){
        DataBase db = createTable2();
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";
        String avgExp = "FirstName AVG(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.9 \n" +
                "'Sam' 3.9 \n" +
                "'John' 3.9 ";
        //avg
        assertEquals(avgExp, db.execute("SELECT FirstName, AVG(GPA) FROM SymStudent;").toString());

        //count
        String countExp = "FirstName COUNT(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.0 \n" +
                "'Sam' 3.0 \n" +
                "'John' 3.0 ";
        assertEquals(countExp, db.execute("SELECT FirstName, COUNT(GPA) FROM SymStudent;").toString());


        //sum
        String sumExp = "FirstName SUM(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 11.7 \n" +
                "'Sam' 11.7 \n" +
                "'John' 11.7 ";
        assertEquals(sumExp, db.execute("SELECT FirstName, SUM(GPA) FROM SymStudent;").toString());

        //max
        String maxExp = "FirstName MAX(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 4.0 \n" +
                "'Sam' 4.0 \n" +
                "'John' 4.0 ";
        assertEquals(maxExp, db.execute("SELECT FirstName, MAX(GPA) FROM SymStudent;").toString());

        //min
        String minExp = "FirstName MIN(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.8 \n" +
                "'Sam' 3.8 \n" +
                "'John' 3.8 ";
        assertEquals(minExp, db.execute("SELECT FirstName, MIN(GPA) FROM SymStudent;").toString());

        assertEquals(whatIsExpected, db.toString());


    }
    @Test
    public void testingBadInputSelectFunctions(){//ask
        DataBase db = createTable2();
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n " +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";


        String minExp = "FirstName MIN(GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.8 \n" +
                "'Sam' 3.8 \n" +
                "'John' 3.8 ";
        assertEquals(minExp, db.execute("SELECT FirstName, MIN(GPA) FROM SymStudent;").toString());

        //avg
        assertEquals("false", db.execute("SELECT FirstName, MIN(FirstName) FROM SymStudent;").getBool());


        //sum
        assertEquals("false", db.execute("SELECT FirstName, SUM(FirstName) FROM SymStudent;").getBool());

    }

    public DataBase createTableforDistict(){
        DataBase db = new DataBase();
        SQLParser parser = new SQLParser();
        String query =  "CREATE TABLE SymStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " Class varchar(255)," //added
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");";
        db.execute(query);

        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Nick', 3875, 'Schmo',4.0, 'Junoir',230012347);");
        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Sam', 384,'Nack', 3.8, 'Senior',800012445);");
        db.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 4875,'Chin', 3.8, 'Senior',803022345);");

        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.8 'Sam' true 'Nack' 384 \n " +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";


        return db;
    }
    @Test
    public void testingSelectDistinct(){
        DataBase db = createTableforDistict();
        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.8 'Sam' true 'Nack' 384 \n " +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";


        String Exp = "GPA CurrentStudent \n" +
                "DECIMAL BOOLEAN \n" +
                "4.0 true \n" +
                "3.8 true ";
        assertEquals(Exp, db.execute("SELECT DISTINCT GPA, CurrentStudent FROM SymStudent;").toString());
        /// /avg
        String avgExp = "FirstName AVG(DISTINCT GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.9 \n" +
                "'Sam' 3.9 ";
        assertEquals(avgExp, db.execute("SELECT FirstName, AVG(DISTINCT GPA) FROM SymStudent;").toString());

        //count
        String countExp = "FirstName COUNT(DISTINCT GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 2.0 \n" +
                "'Sam' 2.0 ";
        assertEquals(countExp, db.execute("SELECT FirstName, COUNT(DISTINCT GPA) FROM SymStudent;").toString());

        //sum
        String sumExp = "FirstName SUM(DISTINCT GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 7.8 \n" +
                "'Sam' 7.8 ";
        assertEquals(sumExp, db.execute("SELECT FirstName, SUM(DISTINCT GPA) FROM SymStudent;").toString());

        //max
        String maxExp = "FirstName MAX(DISTINCT GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 4.0 \n" +
                "'Sam' 4.0 ";
        assertEquals(maxExp, db.execute("SELECT FirstName, MAX(DISTINCT GPA) FROM SymStudent;").toString());

        //min
        String minExp = "FirstName MIN(DISTINCT GPA) \n" +
                "VARCHAR DECIMAL \n" +
                "'Nick' 3.8 \n" +
                "'Sam' 3.8 ";
        assertEquals(minExp, db.execute("SELECT FirstName, MIN(DISTINCT GPA) FROM SymStudent;").toString());


    }

    @Test
    public void testingSelectOrderBy(){
        DataBase db = createTale();
        String  whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";

        String assert1 ="BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 ";

        assertEquals(assert1, db.execute("SELECT * FROM YCStudent ORDER BY GPA ASC, SSNum DESC;").toString());
        String assert2 ="BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 ";



        assertEquals(assert2, db.execute("SELECT * FROM YCStudent ORDER BY GPA DESC, BannerID DESC;").toString());

        String assert3 ="BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 ";


        assertEquals(assert3, db.execute("SELECT * FROM YCStudent ORDER BY GPA ASC, SSNum ASC;").toString());

        String assert4 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";

        assertEquals(assert4, db.execute("SELECT * FROM YCStudent ORDER BY CurrentStudent DESC;").toString());

    }

    @Test
    public void testingSelectWhere(){
        DataBase db = createTale();
        String  whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";

        String assert1 ="FirstName LastName \n" +
                "VARCHAR VARCHAR \n" +
                "'John' 'Greenberg' \n" +
                "'Jessica' 'Faigen' ";

        assertEquals(assert1, db.execute("SELECT FirstName, LastName FROM YCStudent WHERE GPA=3.0 AND (FirstName='Jessica' OR LastName='Greenberg');").toString());
        String assert2 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 ";


        assertEquals(assert2, db.execute("SELECT * FROM YCStudent WHERE GPA<3.0 AND (SSNum=7486 OR LastName='Doe');").toString());

        String  assert3 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";


        assertEquals(assert3, db.execute("SELECT * FROM YCStudent WHERE GPA>=3.0 AND (SSNum<7446 OR LastName<>'Greenberg');").toString());

        String  assert4 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT ";


        assertEquals(assert4, db.execute("SELECT * FROM YCStudent WHERE GPA>4.0 AND (SSNum<7446 OR LastName<>'Greenberg');").toString());

        //make sure table hasnt changed
        assertEquals(whatIsExpected, db.toString());

    }

    @Test
    public void testingSelectBadInput(){
        DataBase db = createTale();
        String  whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012347 'Junoir' 2.0 'Ploni' true 'Almoni' 7486 \n" +
                "800012345 'Senior' 1.0 'David' true 'Doe' 4857 \n" +
                "800022345 'Senior' 3.0 'John' true 'Greenberg' 7446 \n" +
                "800012845 'Freshman' 4.0 'Jessica' true 'Faigen' 7386 \n" +
                "800012346 'Super Senior' 2.0 'Albert' true 'Smith' 7416 \n" +
                "810012845 'Freshman' 3.0 'Jessica' false 'Faigen' 7336 ";
        //column name does not exist
        assertEquals("false", db.execute("SELECT Price FROM YCStudent WHERE GPA<3.0 AND (SSNum=7486 OR LastName='Doe');").getBool());

        //AVG of a string
        assertEquals("false", db.execute("SELECT FirstName, AVG(Class) FROM YCStudent;").getBool());

        //order by non existing column
        assertEquals("false", db.execute("SELECT * FROM YCStudent ORDER BY Price DESC;").getBool());

        //table name does not exist
        assertEquals("false", db.execute("SELECT * FROM Student ORDER BY LastName DESC;").getBool());


        //make sure table hasnt changed
        assertEquals(whatIsExpected, db.toString());
    }

    /**
     * not real junit just used to help with debugger
     */
    @Test
    public void testingindexing(){
        DataBase db = createTable2();

        String whatIsExpected = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n " +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";

        db.execute("CREATE INDEX GPA_Index on SymStudent (GPA);");
        db.execute("INSERT INTO SymStudent (FirstName, LastName, GPA, Class, BannerID, SSNum) VALUES ('Plni','Almni', 1.0, 'Senior',860012345, 4356);");
        String whatIsExpectedAfterInsert = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 ";
        assertEquals(whatIsExpectedAfterInsert, db.toString());

        //less
        String assert2 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";



        assertEquals(assert2, db.execute("select * from SymStudent WHERE GPA<3.9;").toString());

        //greater
        String assert3 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 ";

             assertEquals(assert3, db.execute("select * from SymStudent WHERE GPA>3.8;").toString());
        //greater or equal
        String assert4 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 ";


                assertEquals(assert4, db.execute("select * from SymStudent WHERE GPA>=3.8;").toString());

        //less or equal
        String assert5 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 ";

                assertEquals(assert5, db.execute("select * from SymStudent WHERE GPA<=3.8;").toString());
        //equal
        String assert6 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 ";
                assertEquals(assert6, db.execute("select * from SymStudent WHERE GPA=1.0;").toString());
        //not equal
        String assert7 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 ";

                assertEquals(assert7, db.execute("select * from SymStudent WHERE GPA<>3.8;").toString());
        //and
        String assert8 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 ";
                   assertEquals(assert8, db.execute("select * from SymStudent WHERE GPA>3.8 AND FirstName='Nick';").toString());
        //or
        String assert9 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 4.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 ";
                assertEquals(assert9, db.execute("select * from SymStudent WHERE GPA>3.8 OR FirstName='Plni';").toString());
        //update
        String assert10 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "230012347 'Junoir' 2.0 'Nick' true 'Schmo' 3875 \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 ";
        db.execute("UPDATE SymStudent SET GPA=2.0 WHERE BannerID=230012347;");
            assertEquals(assert10, db.toString());

        //delete
        String assert11 = "BannerID Class GPA FirstName CurrentStudent LastName SSNum \n" +
                "INT VARCHAR DECIMAL VARCHAR BOOLEAN VARCHAR INT \n" +
                "800012445 'Senior' 3.9 'Sam' true 'Nack' 384 \n" +
                "803022345 'Senior' 3.8 'John' true 'Chin' 4875 \n" +
                "860012345 'Senior' 1.0 'Plni' true 'Almni' 4356 ";
        db.execute("DELETE FROM SymStudent WHERE Class='Junoir';");
        assertEquals(assert11, db.toString());
    }

}
