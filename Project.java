package database;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Project implements ActionListener{
    JDesktopPane desk;
    JInternalFrame f1, f2, f3, f4, f5;
    JFrame frame;
    Connection con;
    JTextField Tuser; JPasswordField Tpwd;
    JButton logout, login, go1, go2, go3, back, pSal; 
    JLabel shEmp, paySal, edEmp, username, pwd, admin; 
    JTable table;
   
    
      
    public static void main(String[] args){
        Project p = new Project();
        p.createConnection();
      
    }
    
    void createConnection(){
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3080/mydb?serverTimezone=UTC", "root","root");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
        
    }
    
    public Project(){
        frame = new JFrame("Payroll");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desk = new JDesktopPane();
        f1 = new JInternalFrame("WELCOME", true, true, true, true);
        f1.setBounds(20,20,350,300);
        f1.setBackground(Color.gray);
        f1.setVisible(true);
        f2 = new JInternalFrame("ADMIN", true, true, true, true);
        f2.setBounds(20, 20, 350, 200);
    //    f2.setVisible(true);
        f3 = new JInternalFrame("ADMIN", true, true, true, true);
        f3.setBounds(20, 20, 350, 400);
        f3.setBackground(Color.gray);
    //    f3.setVisible(true);
        f4 = new JInternalFrame("ADMIN", true, true, true, true);
        f4.setBounds(20, 20, 350, 400);
        f4.setBackground(Color.gray);
    //    f4.setVisible(true);
        f5 = new JInternalFrame("ADMIN", true, true, true, true);
        f5.setBounds(20, 20, 400, 450);
      
        JPanel p1 = new JPanel();
        p1.setBackground(Color.CYAN);
        p1.setLayout(new FlowLayout());
        username = new JLabel("Username");
        p1.add(username);
        Tuser = new JTextField("admin", 20);
        p1.add(Tuser);
        pwd = new JLabel("Password");
        p1.add(pwd);
        Tpwd = new JPasswordField(20);
        p1.add(Tpwd);
        login = new JButton("Login");
        login.addActionListener(this);
        p1.add(login);
        f1.add(p1);
        
        JPanel p2 = new JPanel();
        p2.setBackground(Color.GRAY);
        p2.setLayout(new GridLayout(0,2, 10, 20));
        shEmp = new JLabel("Show Employees");
        p2.add(shEmp);
        go1 = new JButton("GO");
        go1.addActionListener(this);
        p2.add(go1);
        paySal = new JLabel("Pay Salary");
        p2.add(paySal);
        go2 = new JButton("GO");
        go2.addActionListener(this);
        p2.add(go2);
        edEmp = new JLabel("Edit Employee's Records");
        p2.add(edEmp);
        go3 = new JButton("GO");
        go3.addActionListener(this);
        p2.add(go3);
        logout = new JButton("LOGOUT");
        logout.addActionListener(this);
        p2.add(logout);
        f2.add(p2);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        String[] colHeads = {"name", "level", "salary"};
        Object[] [] data = {
              };
      
        DefaultTableModel tableModel = new DefaultTableModel(data, colHeads);
        table = new JTable(tableModel);
        JScrollPane jsp = new JScrollPane(table);
 //       p3.add(table.getTableHeader(), BorderLayout.LINE_START);
        p3.add(jsp, BorderLayout.NORTH);
        back = new JButton("BACK");
        back.addActionListener(this);
        p3.add(back, BorderLayout.SOUTH);
        f3.add(p3);
    
        
        JPanel p4 = new JPanel();
        p4.setLayout(new BorderLayout());
        pSal = new JButton("Pay Salary");
        pSal.setSize(10, 5);
        pSal.addActionListener(this);
        p4.add(pSal, BorderLayout.NORTH);
        p4.add(jsp, BorderLayout.CENTER);
        back.setSize(20, 10);
        p4.add(back, BorderLayout.SOUTH);
        f4.add(p4);
    //    f4.setVisible(true);
        
        
        
        desk.add(f1);
        desk.add(f2);
        desk.add(f3);
        desk.add(f4);
        frame.add(desk);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
      if (e.getSource() == back){
          f2.setVisible(true);
          f3.setVisible(false);
          f4.setVisible(false);
          
      }
      if (e.getSource() == login){
          if (("admin".equals(Tuser.getText().toLowerCase())) && ("admin".equals(String.valueOf(Tpwd.getPassword()).toLowerCase()))){
                
              f2.setVisible(true);
              f1.setVisible(false);
            }
          else{
              JOptionPane.showMessageDialog(frame, "Please Enter the correct username and password");
      
      }
      }
      if (e.getSource() == go1){
          DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.setRowCount(0);
            try {
                Statement stm = con.createStatement();
                
                 //statement to SELECT from a table
                ResultSet rs = stm.executeQuery("SELECT * FROM EMPLOYEES");
                
                while (rs.next()){
                    String name = rs.getString("name");
                    int age = rs.getInt("level");
                    int salary = rs.getInt("salary");
                    tableModel.addRow(new Object[]{name, age, salary}); 
                     
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
          f3.setVisible(true);
          f4.setVisible(true);
          f2.setVisible(false);
      }
      if (e.getSource() == go2){
          DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
          tableModel.setRowCount(0);
          try {
                Statement stm = con.createStatement();
                
                 //statement to SELECT from a table
                ResultSet rs = stm.executeQuery("SELECT * FROM EMPLOYEES");
                
                while (rs.next()){
                    String name = rs.getString("name");
                    int age = rs.getInt("level");
                    int salary  = rs.getInt("salary");
                    tableModel.addRow(new Object[]{name, age, salary}); 
                     
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
          f4.setVisible(true);
          f2.setVisible(false);
      }
      if (e.getSource() == pSal){
          JOptionPane.showMessageDialog(frame, "You have just paid salary for the current month");
      }
      
      if (e.getSource() == logout){
          f1.setVisible(true);
      }
        
    }

   
}
