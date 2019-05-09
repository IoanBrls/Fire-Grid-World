package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewGameFrame extends JDialog {
    
    /** Creates a new instance of NewGameFrame */
    public NewGameFrame(Component parent, String title) {
        super(new Frame(), title, true);
        initComponents();
    }
    
    private void initComponents() {
        rows = 0;
        columns = 0;
        c = getContentPane();
        
        //"North" panel
        panela = new JPanel();
        labela = new JLabel("<html><font color=blue size=4><code>Give the size of your world</code></font></html>");
        panela.add(labela);
        
        //"Center" panel
        labelb1 = new JLabel("<html>No of <font color=green>rows:        </html>");
        textb1 = new JTextField(10);
        labelb2 = new JLabel("<html>No of <font color=green>columns:  </html>");
        textb2 = new JTextField(10);
        
        panelb = new JPanel();
        panelb.setLayout(new GridLayout(2, 2, 10, 5));
        panelb.add(labelb1);
        panelb.add(textb1);
        panelb.add(labelb2);
        panelb.add(textb2);
        
        //"South" panel
        panelc = new JPanel();
        okbtn = new JButton(new OKAction());
        cancelbtn = new JButton(new CancelAction());
        panelc.add(okbtn);
        panelc.add(cancelbtn);
        
        //add panels
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panela, BorderLayout.NORTH);
        panel.add(panelb, BorderLayout.CENTER);
        panel.add(panelc, BorderLayout.SOUTH);
        c.add(panel);
        pack();
    }
    public Insets getInsets() {
        return new Insets(30, 10, 5, 10);
    }
    
    public static void main(String[] str) {
        JDialog jf = new NewGameFrame(null, "New World");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setRowCount(int s) {
        this.rows = s;
    }
    
    public int getRowCount() {
        return this.rows;
    }
    
    private void setColumnCount(int s) {
        this.columns = s;
    }
    
    public int getColumnCount() {
        return this.columns;
    }
    
    private Container c;
    private JLabel labela, labelb1, labelb2;
    private JTextField textb1, textb2;
    private JButton okbtn, cancelbtn;
    private JPanel panel, panela, panelb, panelc;
    private int rows, columns;   
    
    private class OKAction extends AbstractAction {
        OKAction() {
            super("  OK  ");
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                setRowCount(Integer.parseInt(textb1.getText()));
                setColumnCount(Integer.parseInt(textb2.getText()));
            } catch (NumberFormatException nfe) {
                setRowCount(-1);
                setColumnCount(-1);
            } finally {
                setVisible(false);
                dispose();
            }
        }
    }
    
    private class CancelAction extends AbstractAction {
        CancelAction() {
            super("Cancel");
        }
        
        public void actionPerformed(ActionEvent e) {
            setRowCount(-1);
            setColumnCount(-1);
            setVisible(false);
            dispose();
        }
    }
    
}
