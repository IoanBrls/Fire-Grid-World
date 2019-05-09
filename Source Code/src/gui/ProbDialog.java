package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class ProbDialog extends JDialog {
    
    /** Creates a new instance of NewGameFrame */
    public ProbDialog(Component parent, String title) {
        super(new Frame(), title, true);
        
       
        
        
        probability = new double[4];
        initComponents();
        setProbability(0.8, 0.1, 0.0, 0.1);
    }
    
    
    private void initComponents() {
        //rows = 0;
        //columns = 0;
        c = getContentPane();
        
        //"North" panel
        panela = new JPanel(new GridLayout(2,1));
        labela = new JLabel("<html><font color=blue size=4><code>Case you want to move<font color=green> North</font>.</code></font></html>");
        JLabel labelb = new JLabel("<html><font color=blue size=4><code>What is the probability to go:</code></font></html>");
        panela.add(labela);
        panela.add(labelb);
        
        //"Center" panel
       
        labelb1 = new JLabel("  North : ");
        labelb2 = new JLabel("  East : ");
        labelb3 = new JLabel("  South : ");
        labelb4 = new JLabel("  West : ");
        textb1 = new JTextField("" + probability[0]);
        textb2 = new JTextField("" + probability[1]);
        textb3 = new JTextField("" + probability[2]);
        textb4 = new JTextField("" + probability[3]);
        
        
        panelb = new JPanel();
        panelb.setLayout(new GridLayout(6,2));
        panelb.setBorder(BorderFactory.createTitledBorder("  Direction  "));
        panelb.add(labelb1);
        panelb.add(textb1);
        panelb.add(labelb2);
        panelb.add(textb2);
        panelb.add(labelb3);
        panelb.add(textb3);
        panelb.add(labelb4);
        panelb.add(textb4);
        panelb.add(four_ways);
        
        
        
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
    	
        ProbDialog jf = new ProbDialog(null, "Setup");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (double e : jf.probability) {
            System.out.println(e);
        }
    }
    private void refreshFields() {
        textb1.setText("" + this.probability[0]);
        textb2.setText("" + this.probability[1]);
        textb3.setText("" + this.probability[2]);
        textb4.setText("" + this.probability[3]);
    }
    
    
    private void setProbability(double n, double e, double s, double w) {
        double sum = n+e+s+w;
        this.probability[0] = n/sum;
        this.probability[1] = e/sum;
        this.probability[2] = s/sum;
        this.probability[3] = w/sum;
        refreshFields();
    }
    
    
    public void setProbVector(double[] prob) {
        if ((prob != null) && (prob.length == 4)) {
            setProbability(prob[0], prob[1], prob[2], prob[3]);
        }
        
    }
    
   
    
    public double[] getProbability() {
        return this.probability;
    }
    
    private boolean b=true;
    private Container c;
    private JLabel labela, labelb1, labelb2, labelb3, labelb4,labelb5, labelb6,labelb7,labelb8;
    private JTextField textb1, textb2, textb3, textb4, textb5, textb6, textb7, textb8;
    private JButton okbtn, cancelbtn;
    private JPanel panel, panela, panelb, panelc;
    private double r, g, tolerance;
    private int iteration;
    private double[] probability;
    JRadioButton four_ways = new JRadioButton("action in four directions", true);
    JRadioButton eight_ways = new JRadioButton("action in eight directions", false);


 
    
    private class OKAction extends AbstractAction { 
        OKAction() {
            super("  OK  ");
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                double no = Double.parseDouble(textb1.getText());
                double ea = Double.parseDouble(textb2.getText());
                double so = Double.parseDouble(textb3.getText());
                double we = Double.parseDouble(textb4.getText());
                setProbability(no, ea, so, we);
            } catch (NumberFormatException nfe) {
                //default values
                setProbability(0.8, 0.1, 0, 0.1);
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
            dispose();
        }
    }

	
    
}
