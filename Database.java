package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Scanner;

public class Database {
    
    Connection con;
    
    public static void main(String[] args){
        Database pro = new Database();
        pro.createConnection();
 //       pro.createTable();
 //       pro.addBatch();
        pro.callableExample();
    }
    
   void callableExample(){
        try {
            CallableStatement stm = con.prepareCall("call Simple(?, ?)");
            stm.setInt(1, 40);
            stm.registerOutParameter(2, java.sql.Types.INTEGER);
            Boolean hasResult = stm.execute();
            if (hasResult){
                int countReturned = stm.getInt(2);
                System.out.println("Number of numbers got = "+ countReturned);
                ResultSet res = stm.getResultSet();
                while (res.next()){
                    System.out.println(res.getString("name"));
                }
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
    void addBatch(){
       
            /*     //-----First Method of batch Operation using simple statement--------
            try {
            Statement stm = con.createStatement();
            stm.addBatch("INSERT INTO USERS2 VALUES('USER1' , 21)");
            stm.addBatch("INSERT INTO USERS2 VALUES('USER2', 45)");
            stm.addBatch("INSERT INTO USERS2 VALUES('USER3', 66)");
            int[] ar = stm.executeBatch();
            for (int i : ar){
            System.out.println(i);
            
            }
            stm.close();
            } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            
            //------Second Method of Batch Operation using Prepared Statement-----------
            try {
            PreparedStatement stm = con.prepareStatement("INSERT INTO USERS2 VALUES(?, ?)");
            stm.setString(1, "User4");
            stm.setInt(2, 32);
            stm.addBatch();
            
            stm.clearParameters();
            stm.setString(1, "User5");
            stm.setInt(2, 65);
            stm.addBatch();
            stm.clearParameters();
            int [] res = stm.executeBatch();
            for (int re : res){
                System.out.println(re);
            }
            
                    } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void createTable(){
        try {
            String q = "CREATE TABLE EMPLOYEES ("
                    + "name varchar(50),"
                    + "age int,"
                    + "salary float"
                    + ");";
            Statement stm = con.createStatement();
            stm.execute(q);
            System.out.println("Successfully Created");
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3080/mydb?serverTimezone=UTC", "root","root");
         //   Statement stm = con.createStatement();
           
     /*       // statement to SELECT from a table
            ResultSet rs = stm.executeQuery("SELECT * FROM USERS2");
            while (rs.next()){
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name + " age " + age);
            }
       */     
     /*       //To INSERT values into table
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the name here");
            String name = in.next();
            String new_names = "Insert Into USERS Values('" + name + "')";
            stm.execute(new_names);
       */     
            
       //     stm.close();
            System.out.println("Database connection successful");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
