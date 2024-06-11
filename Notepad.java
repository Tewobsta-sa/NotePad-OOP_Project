//this is just what i've been tinkering with
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Notepad extends JFrame{
  JPanel panel1,panel2,panel3;
  JButton save,toggle;
  JTextArea textarea;
  JMenu edit,about;
                                           
  JMenuBar mb=new JMenuBar();
  
  public Notepad(){
    panel1=new JPanel();
    textarea=new JTextArea(100,100);
    textarea.setBackground(Color.black);
    textarea.setForeground(Color.gray);
    panel1.add(textarea);
    panel2=new JPanel();
    save= new JButton("Save");
    save.setSize(15,15);
    panel2.add(save);
  
    edit=new JMenu("Edit");
    mb.add(edit);
    
    edit.add(new JMenuItem("New"));
    edit.add(new JMenuItem("Undo"));
    edit.add(new JMenuItem("Redo") );

    about=new JMenu("About");
    mb.add(about); 
   

    panel3=new JPanel();
    panel3.setVisible(false);
   
    add(panel2,BorderLayout.SOUTH);
    add(panel1,BorderLayout.CENTER);
   
    setJMenuBar(mb);
    setSize(700,700);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }


  public static void main(String[] args) {
      new Notepad();
  }
}
class Listener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
