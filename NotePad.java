import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.*;
import java.io.*;

class NotePad implements ActionListener{
    MenuBar mb = new MenuBar();
    JTextArea jt; JFrame f2; Button b2;
    JPanel p = new JPanel();
    JScrollPane sp;
    Menu file, Edit, Format, View, Help;
    MenuItem New, Open, Save, Save_As, Exit, Page, Print, Find;
    NotePad(){
        JFrame f = new JFrame("NotePad");
        
        f.setSize(500, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       
        file = new Menu("File");
  //      file.setMnemonic(KeyEvent.VK_F);
        
        
   //     New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
   //     New.getAccessibleContext().setAccessibleDescription("This creates a new file");
        file.add(New = new MenuItem("New"));
        New.setShortcut(new MenuShortcut(KeyEvent.VK_N));
        New.addActionListener(this);
        file.add(Open = new MenuItem("Open..."));
        Open.setShortcut(new MenuShortcut(KeyEvent.VK_O));
        Open.addActionListener(this);
        file.add(Save = new MenuItem("Save"));
        Save.setShortcut(new MenuShortcut(KeyEvent.VK_S));
        Save.addActionListener(this);
        
        file.add(Save_As = new MenuItem("Save As..."));
        file.addSeparator();
        file.add(Page = new MenuItem("Page Setup..."));
        file.add(Print = new MenuItem("Print..."));
        file.addSeparator();
        file.add(Exit = new MenuItem("Exit"));
        Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            System.exit(0);
            }
            });
        mb.add(file);
        
        Edit = new Menu("Edit");
        MenuItem Undo, Cut, Copy, Paste, Delete, Find, FN, Replace, Go, SA, Time;
        Edit.add(Undo = new MenuItem("Undo"));
        Edit.addSeparator();
        Edit.add(Cut = new MenuItem("Cut"));
        Edit.add(Copy = new MenuItem("Copy"));
        Edit.add(Paste = new MenuItem("Paste"));
        Edit.add(Delete = new MenuItem("Delete"));
        Edit.addSeparator();
        Edit.add(Find = new MenuItem("Find"));
        Find.addActionListener(this);
        Edit.add(FN = new MenuItem("Find Next"));
        Edit.add(Replace = new MenuItem("Replace..."));
        Edit.add(Go = new MenuItem("Go To..."));
        Edit.addSeparator();
        Edit.add(SA = new MenuItem("Select All"));
        Edit.add(Time = new MenuItem("Time/Date"));
        mb.add(Edit);
        
        Format = new Menu("Format");
        MenuItem Word, Font;
        Format.add(Word = new MenuItem("Word Wrap"));
        Format.add(Font = new MenuItem("Font..."));
        mb.add(Format);
        
        View = new Menu("View");
        MenuItem Status;
        View.add(Status = new MenuItem("Status Bar"));
        mb.add(View);
        
        Help = new Menu("Help");
        MenuItem VH, About;
        Help.add(VH = new MenuItem("VIew Help"));
        Help.addSeparator();
        Help.add(About = new MenuItem("About NotePad"));
        mb.add(Help);
        
        GridLayout gl = new GridLayout();
            JFrame f2 = new JFrame("Find");
            f2.setSize(300, 200);
            f2.setLayout(gl);
            JLabel label = new JLabel("Find");
            JTextField tf = new JTextField(10);
            JPanel p1 = new JPanel();
            JButton b1 = new JButton("Find Next");
            JButton b2 = new JButton("Close");
            b2.addActionListener(this);
            
            p1.add(b1);
            p1.add(b2);
            f2.add(p1);
            f2.add(label);
            f2.add(tf);
            
        
        p.setLayout(new BorderLayout());
        JTextArea jt = new JTextArea(5, 30); 
        sp = new JScrollPane(jt); 
        p.add(sp, BorderLayout.CENTER);
        f.add(p);
      f.setMenuBar(mb);  
      f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == New)
        {
            jt.setText("");
            Save.setEnabled(true);
            
        }
        else if (e.getSource() == Open)
        {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(open);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                jt.setText("");
                try
                {
                 Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                 while (scan.hasNext())
                     jt.append(scan.nextLine() + "\n");
                     
                }
                catch(Exception ex)
                        {
                            System.out.println(ex.getMessage());
                            
                        }
                
            }
        }
        
        else if(e.getSource() == Save)
        {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(save);
            if (option == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(jt.getText());
                    out.close();
                    
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        
        else if (e.getSource() == Find)
        {
            
            f2.setVisible(true);
        }
        
        if (e.getSource() == b2)
        {
              f2.setVisible(false);
              f2.dispose();
            
        }
    }
    public static void main(String[] args){
        new NotePad();
    }

}
