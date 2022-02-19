package database;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class DatabaseTwo implements ActionListener{
   
    String colHeads; Object data;
    JFrame f;
    JTable table;  JButton insert, refresh, edit, update, delete; JTextField Tname, Tage, editName, editAge; 
    Connection con;
    public static void main(String[] args){
        DatabaseTwo pro = new DatabaseTwo();
        pro.createConnection();  
        new DatabaseTwo();
        
    }
    
    
    void createConnection(){
            
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3080/mydb?serverTimezone=UTC", "root","root");
              
        
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTwo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    DatabaseTwo(){
        
        f = new JFrame("Database");
        f.setLayout(new GridLayout(0,1));
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.setBackground(Color.BLUE);
        JPanel pp = new JPanel();
        pp.setLayout(new BorderLayout());
        pp.setBackground(Color.DARK_GRAY);
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.setBackground(Color.LIGHT_GRAY);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.setBackground(Color.CYAN);
        Tname = new JTextField(20);
        p.add(Tname);
        Tage = new JTextField(10);
        p.add(Tage);
        insert = new JButton("Insert");
        insert.addActionListener(this);
        p.add(insert);
       
        refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        p1.add(refresh);
        
        edit = new JButton("edit");
        edit.addActionListener(this);
        p1.add(edit);
        delete = new JButton("delete");
        delete.addActionListener(this);
        p1.add(delete);
       
        String[] colHeads = {"Name", "Age"};
        Object[] [] data = {
      //      {"Bucky Roberts", "20"}
        };
      
        DefaultTableModel tableModel = new DefaultTableModel(data, colHeads);
      //tableModel.addColumn("Name");
      //tableModel.addColumn("Age");
        table = new JTable(tableModel);
        JScrollPane jsp = new JScrollPane(table);
        pp.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);
        pp.add(jsp, BorderLayout.CENTER);
        
        editName = new JTextField(20);
        p2.add(editName);
        editAge = new JTextField(10);
        p2.add(editAge);
        update = new JButton("update");
        update.addActionListener(this);
        p2.add(update);
        
        f.add(p);
        f.add(p1);
        f.add(pp);
        f.add(p2);
        f.setVisible(true);
        
    }
    
  
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == insert){
            try{ 
        
            String name = Tname.getText();
            int ages = Integer.parseInt(Tage.getText());
     //       String age = Integer.toString(ages);
            //To INSERT values into table -----First Method------
            //Statement stm = con.createStatement();
                 
                //String new_names = "Insert Into USERS Values('" + name + "')";
                // stm.execute(new_names);
                //---------Second Method with Prepared Statement---------
                    PreparedStatement stm = con.prepareStatement("INSERT INTO USERS2 VALUES (? , ?)  "); 
                    stm.setString(1, name);
                    stm.setInt(2, ages);
                    stm.execute();
                    System.out.println("Insertion Completed");
                    stm.close();
                    //   System.out.println("Database connection successful");
                }
            
             catch (SQLException ex) {
            Logger.getLogger(DatabaseTwo.class.getName()).log(Level.SEVERE, null, ex);
        }        }
        
        if (e.getSource() == refresh){             
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
            try {
                Statement stm = con.createStatement();
                
                 //statement to SELECT from a table
                ResultSet rs = stm.executeQuery("SELECT * FROM USERS2");
                
                while (rs.next()){
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    System.out.println(name + " age " + age);
                    tableModel.addRow(new Object[]{name, age}); 
                     
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
        if (e.getSource() == edit){
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int row = table.getSelectedRow();
            String name = (String) tableModel.getValueAt(row, 0);            
            String age = (tableModel.getValueAt(row, 1).toString());
            editName.setText(name);
            editAge.setText(age);
        }   
        
        if (e.getSource() == update){
            try {
                PreparedStatement stm = con.prepareStatement("UPDATE USERS2 SET AGE = ? WHERE NAME = ?");
                stm.setInt(1, Integer.parseInt(editAge.getText()));
                stm.setString(2, editName.getText());
                stm.executeUpdate();
                stm.close();
                refresh.doClick();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTwo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == delete){
            try {
                int row = table.getSelectedRow();
                String name = table.getValueAt(row, 0).toString();
                String sql = "DELETE FROM USERS2 WHERE NAME = ?";
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.executeUpdate();
                System.out.println("Successfuly Deleted");
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTwo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}
}