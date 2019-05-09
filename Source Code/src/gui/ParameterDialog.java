package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ParameterDialog extends JDialog {
    
    /** Creates a new instance of NewGameFrame */
    public ParameterDialog(Component parent, String title) {
        super(new Frame(), title, true);
        initComponents();
    }
    
    private void initComponents() {
        //rows = 0;
        //columns = 0;
        c = getContentPane();
        
        //"North" panel
        panela = new JPanel();
        labela = new JLabel("<html><font color=blue size=4><code>Give the world's parameters</code></font></html>");
        panela.add(labela);
        
        //"Center" panel
        labelb1 = new JLabel("<html><font color=green>Reward (r-value) :      </html>");
        textb1 = new JTextField("-0.04", 10);
        labelb2 = new JLabel("<html><font color=green>G (g-value) :</html>");
        textb2 = new JTextField("1", 10);
        labelb3 = new JLabel("<html><font color=green>Tolerance :</html>");
        textb3 = new JTextField("0.0001", 10);
        labelb4 = new JLabel("<html><font color=green>Number of iteration  :</html>");
        textb4 = new JTextField("100", 10);
        
        
        panelb = new JPanel();
        panelb.setLayout(new GridLayout(6, 2, 10, 5));
        panelb.add(labelb1);
        panelb.add(textb1);
        panelb.add(labelb2);
        panelb.add(textb2);
        panelb.add(labelb3);
        panelb.add(textb3);
        panelb.add(labelb4);
        panelb.add(textb4);
        
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
        ParameterDialog jf = new ParameterDialog(null, "Setup");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(jf.getR());
        System.out.println(jf.getG());
        System.out.println(jf.getTolerance());
        System.out.println(jf.getMaxIteration());


    }
    
    private void setR(double s) {
        this.r = s;
    }
    
    public double getR() {
        return this.r;
    }
    
    private void setG(double s) {
        this.g = s;
    }
    
    public double getG() {
        return this.g;
    }
    
    private void setTolerance(double s) {
        this.tolerance = s;
    }
    
    public double getTolerance() {
        return this.tolerance;
    }
    
    private void setMaxIteration(int s) {
        this.iteration = s;
    }
    
    public int getMaxIteration() {
        return this.iteration;
    }
    
    
    private Container c;
    private JLabel labela, labelb1, labelb2, labelb3, labelb4;
    private JTextField textb1, textb2, textb3, textb4;
    private JButton okbtn, cancelbtn;
    private JPanel panel, panela, panelb, panelc;
    private double r, g, tolerance;
    private int iteration;
    
    private class OKAction extends AbstractAction {
        OKAction() {
            super("  OK  ");
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                setR(Double.parseDouble(textb1.getText()));
                setG(Double.parseDouble(textb2.getText()));
                setTolerance(Double.parseDouble(textb3.getText()));
                setMaxIteration(Integer.parseInt(textb4.getText()));

            } catch (NumberFormatException nfe) {
                //default values
                setR(-0.04);
                setG(1);
                setTolerance(0.00001);
                setMaxIteration(100);
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
            //default values
            setR(-0.04);
            setG(1);
            setTolerance(0.00001);
            setMaxIteration(100);
            setVisible(false);
            dispose();
        }
    }
    
}
