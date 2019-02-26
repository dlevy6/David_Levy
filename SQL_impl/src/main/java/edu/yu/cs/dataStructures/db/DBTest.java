package edu.yu.cs.dataStructures.db;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SQLParser;
import net.sf.jsqlparser.JSQLParserException;

public class DBTest {
    public static void main(String[] args) throws JSQLParserException {

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
        System.out.println("my Create Table");
        System.out.println("CREATE TABLE YCStudent"
                + "("
                + " BannerID int,"
                + " SSNum int UNIQUE,"
                + " FirstName varchar(255),"
                + " LastName varchar(255) NOT NULL,"
                + " Class varchar(255)," //added
                + " GPA decimal(1,2) DEFAULT 0.00,"
                + " CurrentStudent boolean DEFAULT true,"
                + " PRIMARY KEY (BannerID)"
                + ");");

        db.execute(query);
        //System.out.println(db.toString());
//create
        System.out.println("\n////////////////////////////////////////////printing empty table/////////////////////////////////////////////\n\n");
        db.printMethod("YCStudent");
//insert
        System.out.println("\n\n//////printing table after inserts//////////////\n\n");

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Ploni', 7486, 'Almoni',2.0, 'Junoir',800012347);").printMethod();

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('David', 4857,'Doe',1.0, 'Senior',800012345);").printMethod();

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 7446,'Greenberg', 3.0, 'Senior',800022345);").printMethod();

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', 7386, 'Faigen', 4.0, 'Freshman',800012845);").printMethod();

        db.execute("INSERT INTO YCStudent (FirstName, CurrentStudent, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', false, 7336, 'Faigen', 3.0, 'Freshman',810012845);").printMethod();

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Albert', 7416, 'Smith', 2.0, 'Super Senior',800012346);").printMethod();

        System.out.println("inserted\n" +
                "INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Ploni', 7486, 'Almoni',2.0, 'Junoir',800012347);\n" +
                "INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('David', 4857,'Doe',1.0, 'Senior',800012345);\n"+
                "INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 7446,'Greenberg', 3.0, 'Senior',800022345);\n" +
                "INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', 7386, 'Faigen', 4.0, 'Freshman',800012845);\n" +
                "INSERT INTO YCStudent (FirstName, CurrentStudent, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', false, 7336, 'Faigen', 4.0, 'Freshman',810012845);\n" +
                "INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Albert', 7416, 'Smith', 2.0, 'Super Senior',800012346);");
        System.out.println("\n\n//////printing table after inserts//////////////\n\n");
        db.printMethod("YCStudent");


        //update
        System.out.println("\n\n/////////////////////////////////////////////////testing update////////////////////////////////////////////////////////\n\n");

        System.out.println("\nupdating GPA=3.0 and Class='Super Senior WHERE BannerID=800012345'");

        db.execute("UPDATE YCStudent SET GPA=3.0,Class='Super Senior' WHERE BannerID=800012345;").printMethod();

        db.printMethod("YCStudent");

        System.out.println("\nSET GPA=3.0,Class='Super Senior' WHERE FirstName='Ploni' ");

        db.execute("UPDATE YCStudent SET GPA=3.0,Class='Super Senior' WHERE FirstName='Ploni';").printMethod();

        db.printMethod("YCStudent");



        //delete

        System.out.println("\n\n////////////////////////////////////printing table after delets///////////////////////////////////////////\n\n");

        System.out.println("\nDELETE FROM YCStudent WHERE Class='Super Senior' AND GPA < 3.0;");

        db.execute("DELETE FROM YCStudent WHERE Class='Super Senior' AND GPA < 3.0;");

        db.printMethod("YCStudent");

        System.out.println("\nDELETE FROM YCStudent WHERE Class='Super Senior' OR GPA < 2.0;");

        db.execute("DELETE FROM YCStudent WHERE Class='Super Senior' OR GPA < 2.0;");

        db.printMethod("YCStudent");

        System.out.println("\nDELETE FROM YCStudent;");

        db.execute("DELETE FROM YCStudent;");

        db.printMethod("YCStudent");

        System.out.println("\n\n//////reinserting to original and testing select//////////////\n\n");
//select
        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Ploni', 7486, 'Almoni',2.0, 'Junoir',800012347);");

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('David', 4857,'Doe',1.0, 'Senior',800012345);");

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 7446,'Greenberg', 3.0, 'Senior',800022345);");

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', 7386, 'Faigen', 4.0, 'Freshman',800012845);");

        db.execute("INSERT INTO YCStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Albert', 7416, 'Smith', 2.0, 'Super Senior',800012346);");

        db.execute("INSERT INTO YCStudent (FirstName, CurrentStudent, SSNum, LastName, GPA, Class, BannerID) VALUES ('Jessica', false, 7336, 'Faigen', 3.0, 'Freshman',810012845);").printMethod();

        db.printMethod("YCStudent");

        System.out.println("\n\nSELECT GPA, FirstName FROM YCStudent WHERE GPA=2.0 AND (FirstName='Ploni' OR FirstName='Albert');");

        db.execute("SELECT GPA, FirstName FROM YCStudent WHERE GPA=2.0 AND (FirstName='Ploni' OR FirstName='Albert');").printMethod();

        System.out.println("SELECT GPA, FirstName FROM YCStudent WHERE GPA=2.0 OR CurrentStudent=true);");

        db.execute("SELECT GPA, FirstName FROM YCStudent WHERE GPA=2.0 OR CurrentStudent=true;").printMethod();

        System.out.println("SELECT * FROM YCStudent ORDER BY GPA ASC, BannerID DESC;");

        db.execute("SELECT * FROM YCStudent ORDER BY GPA ASC, BannerID DESC;").printMethod();

        System.out.println("SELECT FirstName, LastName, AVG(GPA), GPA  FROM YCStudent;");

        db.execute("SELECT FirstName, LastName, AVG(GPA), GPA FROM YCStudent;").printMethod();

        System.out.println("SELECT FirstName, COUNT(DISTINCT GPA), GPA FROM YCStudent;");

        db.execute("SELECT FirstName, COUNT(DISTINCT GPA), GPA FROM YCStudent;").printMethod();

        System.out.println("SELECT * FROM YCStudent ORDER BY BannerID DESC;");

        db.execute("SELECT * FROM YCStudent ORDER BY BannerID DESC;").printMethod();

        System.out.println("SELECT * FROM YCStudent ORDER BY BannerID ASC;");

        db.execute("SELECT * FROM YCStudent ORDER BY BannerID ASC;").printMethod();

        System.out.println("SELECT FirstName, LastName, AVG(SSNum), GPA FROM YCStudent;");

        db.execute("SELECT FirstName, LastName, AVG(SSNum), GPA FROM YCStudent;").printMethod();

        System.out.println("SELECT FirstName, LastName, SUM(SSNum), GPA FROM YCStudent;");

        db.execute("SELECT FirstName, LastName, SUM(SSNum), GPA FROM YCStudent;").printMethod();

        System.out.println("SELECT FirstName, LastName, MAX(GPA), GPA FROM YCStudent;");

        db.execute("SELECT FirstName, LastName, MAX(GPA), GPA FROM YCStudent;").printMethod();

        System.out.println("SELECT FirstName, LastName, MIN(GPA), GPA FROM YCStudent;");

        db.execute("SELECT FirstName, LastName, MIN(GPA), GPA FROM YCStudent;").printMethod();

        //indexing
        System.out.println("\nIndexing\n\n\n");
        DataBase db2 = new DataBase();
        SQLParser parser2 = new SQLParser();
        String query2 =  "CREATE TABLE SymStudent"
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
        db2.execute(query2);

        db2.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Nick', 3875, 'Schmo',4.0, 'Junoir',230012347);");
        db2.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Sam', 384,'Nack',3.9, 'Senior',800012445);");
        db2.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('John', 4875,'Chin', 3.8, 'Senior',803022345);");
        db2.execute("INSERT INTO SymStudent (FirstName, SSNum, LastName, GPA, Class, BannerID) VALUES ('Sara', 3975,'King', 3.2, 'Senior',809322345);");

        db2.execute("CREATE INDEX GPA_Index on SymStudent (GPA);");

        db2.execute("INSERT INTO SymStudent (FirstName, LastName, GPA, Class, BannerID, SSNum) VALUES ('Plni','Almni', 1.0, 'Senior',860012345, 4356);");

        System.out.println("select * from SymStudent WHERE GPA<3.9;");
        db2.execute("select * from SymStudent WHERE GPA<3.9;").printMethod();

        System.out.println("select FirstName from SymStudent WHERE GPA>3.8;");
        db2.execute("select FirstName from SymStudent WHERE GPA>3.8;").printMethod();

        System.out.println("select * from SymStudent WHERE GPA>=3.8;");
        db2.execute("select * from SymStudent WHERE GPA>=3.8;").printMethod();

        System.out.println("select * from SymStudent WHERE GPA<=3.8;");
        db2.execute("select * from SymStudent WHERE GPA<=3.8;").printMethod();

        System.out.println("select * from SymStudent WHERE GPA=1.0;");
        db2.execute("select * from SymStudent WHERE GPA=1.0;").printMethod();

        System.out.println("select * from SymStudent WHERE GPA<>3.8;");
        db2.execute("select * from SymStudent WHERE GPA<>3.8;").printMethod();

        System.out.println("select FirstName, LastName, GPA from SymStudent WHERE GPA>3.8 AND FirstName='Nick';");
        db2.execute("select FirstName, LastName, GPA from SymStudent WHERE GPA>3.8 AND FirstName='Nick';").printMethod();

        System.out.println("select * from SymStudent WHERE GPA>3.8 OR FirstName='Plni';");
        db2.execute("select * from SymStudent WHERE GPA>3.8 OR FirstName='Plni';").printMethod();

        System.out.println("UPDATE SymStudent SET GPA=2.0 WHERE BannerID=230012347;");
        db2.execute("UPDATE SymStudent SET GPA=2.0 WHERE BannerID=230012347;").printMethod();

        System.out.println("select * from SymStudent");
        db2.execute("select * from SymStudent").printMethod();

        System.out.println("DELETE FROM SymStudent WHERE Class='Junoir';");
        db2.execute("DELETE FROM SymStudent WHERE Class='Junoir';").printMethod();

        System.out.println("select * from SymStudent");
        db2.execute("select * from SymStudent").printMethod();



    }



}
