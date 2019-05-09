package gui;

import java.awt.*;
import java.awt.event.*;
import java.math.MathContext;
import javax.swing.*;
import javax.swing.border.*;
import core.*;

public class SDProblemWindow extends JFrame {
	
	private SDProblem problem;
	private int flag=0;
	
	private void setProblem( SDProblem prob){
		
		problem=prob;
	}
    
	private SDProblem getProblem(){
		
		return problem;
	}
	
    /** Creates a new instance of SDProblemWindow */
    public SDProblemWindow(String title) {
        super(title);

        room = SDProblemWindow.class.getResource("images/room.jpg");
        imageWall = SDProblemWindow.class.getResource("images/wall.jpg");
        fire = SDProblemWindow.class.getResource("images/fire.jpg"); 
        agent = SDProblemWindow.class.getResource("images/agent.jpg");
        
        rValue = -0.04;
        gValue = 1;
        tolerance = 0.0001;
        maxIteration = 100;
        initComponents();
    }
    
    private void initComponents() {
        //layeredPane = new JLayeredPane();
        int rows = 7;
        int columns = 7;
        worldTable = new Tile[7][7];
        
        c = getContentPane();
        menuBar = new JMenuBar();
        menu = new JMenu("File");//(new GameAction());
        menu.add(new JMenuItem(new GameAction()));
        menu.add(new JMenuItem(new ExitAction()));
        menuBar.add(menu);
        
        menu = new JMenu("Configuration");
        menu.add(new JMenuItem(new ParamAction()));
        menu.add(new JMenuItem(new ProbAction()));

        menuBar.add(menu);
        
        menu = new JMenu("Help");
        menu.add(new JMenuItem(new AboutAction()));
        menuBar.add(menu);
        
        setJMenuBar(menuBar);
        
        //Root Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // "Center" panel
        blackline = BorderFactory.createLineBorder(Color.black);
        panel1 = new JPanel();
        // fill panel1 and add it to root panel
        worldTable = new Tile[rows][columns];
        setNewWorld(rows, columns);
        
        // "South" panel
        panel2 = new JPanel();
        button = new JButton(new OKAction());
        panel2.add(button);
        
        panel.add(panel2, BorderLayout.SOUTH);
        
        //"East" panel
        gbc = new GridBagConstraints();
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());//GridLayout(5, 2, 5, 3));
        panel3.setBorder(BorderFactory.createTitledBorder(" Pre - features "));
        JLabel lbl1 = new JLabel(" R value               : ");
        JLabel lbl2 = new JLabel(" G value               : ");
        JLabel lbl3 = new JLabel(" Tolerance          : ");
        JLabel lbl4 = new JLabel(" Max iterations  : ");


        txtf1 = new JLabel("<html><font color=green>" + rValue + "</html>");
        txtf2 = new JLabel("<html><font color=green>" + gValue+ "</html>");
        txtf3 = new JLabel("<html><font color=green>" + tolerance+ "</html>");
        txtf4 = new JLabel("<html><font color=green>" + maxIteration+ "</html>");


        gbc.anchor = GridBagConstraints.LINE_START;
        buildConstraints(gbc, 0, 0, 1, 1, 70, 50);
        panel3.add(lbl1, gbc);
        buildConstraints(gbc, 1, 0, 1, 1, 30, 50);
        panel3.add(txtf1, gbc);
        buildConstraints(gbc, 0, 1, 1, 1, 70, 50);
        panel3.add(lbl2, gbc);
        buildConstraints(gbc, 1, 1, 1, 1, 30, 50);
        panel3.add(txtf2, gbc);
        buildConstraints(gbc, 0, 2, 1, 1, 70, 50);
        panel3.add(lbl3, gbc);
        buildConstraints(gbc, 1, 2, 1, 1, 30, 50);
        panel3.add(txtf3, gbc);
        buildConstraints(gbc, 0, 3, 1, 1, 70, 50);
        panel3.add(lbl4, gbc);
        buildConstraints(gbc, 1, 3, 1, 1, 30, 50);
        panel3.add(txtf4, gbc);
        
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());//GridLayout(2, 2, 5, 3));
        panel4.setBorder(BorderFactory.createTitledBorder(" Post - features "));
        JLabel lbl5 = new JLabel(" Iterations number    : ");
        JLabel lbl6 = new JLabel(" Computational time : ");
        txtf5 = new JLabel("<html><font color=green> -- nothing yet -- </html>");
        txtf6 = new JLabel("<html><font color=green> -- nothing yet -- </html>");
        buildConstraints(gbc, 0, 0, 1, 1, 70, 50);
        panel4.add(lbl5, gbc);
        buildConstraints(gbc, 1, 0, 1, 1, 30, 50);
        panel4.add(txtf5, gbc);
        buildConstraints(gbc, 0, 1, 1, 1, 70, 50);
        panel4.add(lbl6, gbc);
        buildConstraints(gbc, 1, 1, 1, 1, 30, 50);
        panel4.add(txtf6, gbc);
        buildConstraints(gbc, 0, 2, 2, 1, 5, 5);
        con = new JTextField(20);
        con.setEditable(false);
        panel4.add(con, gbc);
        
        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(2, 1));//new BorderLayout());
        panel5.add(panel3);//, BorderLayout.CENTER);
        panel5.add(panel4);//, BorderLayout.SOUTH);
        
        JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout());
        panel7.add(panel5, BorderLayout.NORTH);
        
        //panel 7: pre-features & post-features
        panel.add(panel7, BorderLayout.EAST);
        //add panel to container
        c.add(panel);
        
        //create pop-up menu
        popup = new JPopupMenu();
        menuItem = new JMenuItem(new AgentAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(new RoomAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(new WallAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(new FireAction());
        popup.add(menuItem);
    }
    
   public Insets getInsets() {
        return new Insets(28, 10, 10, 10);
    }
    
    private JPanel getPanel() {
        return this.panel1;
    }
    
    private void setNewWorld(int row, int col) {
        panel.remove(panel1);
        panel1 = null;
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(row, col));
        
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                MyLabel subPanel = new MyLabel("", SwingConstants.CENTER);
                subPanel.setRow(i);
                subPanel.setColumn(j);
                worldTable[i][j] = new UsualTile();
                subPanel.setIcon(new ImageIcon(room));
                subPanel.setBorder(blackline);
                subPanel.setPreferredSize(new Dimension(60,60));
                MouseListener popupListener = new PopupListener();
                subPanel.addMouseListener(popupListener);
                panel1.add(subPanel);
            }
        }
        panel.add(panel1, BorderLayout.CENTER);
        panel.revalidate();
        pack();
    }
    
    //Move the Agent to the computed next best position
    
    public void moveAgent(int action, int[][] outcome)
    {
    	int row = outcome.length;
        int col = outcome[0].length;
        int tempX = -1, tempY = -1;
        
        outerloop:
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
            	if (worldTable[i][j] instanceof AgentTile)
            	{
            		tempX=i;
            		tempY=j;
            		break outerloop;
            	}
            }
        }
        
        if (action == 0 )
        {
        	if (worldTable[tempX-1][tempY] instanceof FireTile)
        	{
        		worldTable[tempX-1][tempY] = new UsualTile();
        	}
        	worldTable[tempX][tempY] = new UsualTile();
        	worldTable[tempX-1][tempY] = new AgentTile();
        }
        else if (action == 1)
        {
        	if (worldTable[tempX][tempY+1] instanceof FireTile)
        	{
        		worldTable[tempX][tempY+1] = new UsualTile();
        	}
        	worldTable[tempX][tempY] = new UsualTile();
    		worldTable[tempX][tempY+1] = new AgentTile();
        }
        else if (action == 2)
        {
        	if (worldTable[tempX+1][tempY] instanceof FireTile)
        	{
        		worldTable[tempX+1][tempY] = new UsualTile();
        	}
        	worldTable[tempX][tempY] = new UsualTile();
    		worldTable[tempX+1][tempY] = new AgentTile();
        }
        else if (action == 3)
        {
        	if (worldTable[tempX][tempY-1] instanceof FireTile)
        	{
        		worldTable[tempX][tempY-1] = new UsualTile();
        	}
        	worldTable[tempX][tempY] = new UsualTile();
    		worldTable[tempX][tempY-1] = new AgentTile();
        }
        
    }
    
    //Implementation of how the fire spreads
    
    public void FireSpread(int[][] outcome)
    {
    	int row = outcome.length;
        int col = outcome[0].length;
        
        outerloop:
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                	if (worldTable[i][j] instanceof FireTile)
                	{
                		if (i+1 < row)
                		{
                			if (worldTable[i+1][j] instanceof UsualTile)
                    		{
                    			worldTable[i+1][j] = new FireTile();
                    			break outerloop;
                    		}
                		}	
            			if (j+1<col)
            			{
            				if (worldTable[i][j+1] instanceof UsualTile)
                    		{
                    			worldTable[i][j+1] = new FireTile();
                    			break outerloop;
                    		}
            			}
            			if(j-1>=0)
        				{
        					if (worldTable[i][j-1] instanceof UsualTile)
                    		{
                    			worldTable[i][j-1] = new FireTile();
                    			break outerloop;
                    		}
        				}
            			if (i-1>=0)
    					{
    						if (worldTable[i-1][j] instanceof UsualTile)
                    		{
                    			worldTable[i-1][j] = new FireTile();
                    			break outerloop;
                    		}
    					}
                	}
                }
            }
    }
    
    private void setWorldOutput(int[][] outcome) {
        ImageIcon[] icons = {new ImageIcon(agent), new ImageIcon(fire), new ImageIcon(imageWall), new ImageIcon(room)};
        int row = outcome.length;
        int col = outcome[0].length;
        
        panel.remove(panel1);
        panel1 = null;
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(row, col));
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
            	MyLabel subPanel = new MyLabel("", SwingConstants.CENTER);
            	
            	subPanel.setRow(i);
                subPanel.setColumn(j);
                if (worldTable[i][j] instanceof WallTile) {
                    subPanel.setIcon(icons[2]);
                } else if( worldTable[i][j] instanceof FireTile) {
                	subPanel.setIcon(icons[1]);
                }
            	else if (worldTable[i][j] instanceof AgentTile){
                    subPanel.setIcon(icons[0]);
                }
            	else {
                    subPanel.setIcon(icons[3]);
                }
                MouseListener popupListener = new PopupListener();
                subPanel.addMouseListener(popupListener);
                subPanel.setBorder(blackline);
                subPanel.setPreferredSize(new Dimension(60, 60));
                panel1.add(subPanel);
            }
        }
        
        button.setEnabled(true);
        panel.add(panel1, BorderLayout.CENTER);
        panel.revalidate();
        pack();
    }
    
    public static void main(String[] str) {
        JFrame frame = new SDProblemWindow("Fire Fighting Emulator");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setSelected(Object o) {
        selectedLabel = (MyLabel) o;
    }
    
    private MyLabel getSelected() {
        return this.selectedLabel;
    }
    
    private void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
        gbc.gridx = gx;
        gbc.gridy = gy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.weightx = wx;
        gbc.weighty = wy;
    }
    
    private MyLabel selectedLabel;
    private Container c;
    private JMenuBar menuBar;
    private JMenuItem menuItem;
    private JPopupMenu popup;
    private JMenu menu;
    private JPanel panel, panel1, panel2, subPanel;
    private JLabel txtf1, txtf2, txtf3, txtf4, txtf5, txtf6,txtf7,txtf8;
    private JTextField con;
    private JButton button;

    private Border blackline;
    private GridBagConstraints gbc;
    private Tile[][] worldTable;
    private double rValue, gValue, tolerance;
    private double[] probability;
    private int maxIteration;
    private int fireCount = 0;

    private java.net.URL room;
    private java.net.URL imageWall;
    private java.net.URL fire;
    private java.net.URL agent;
    
    private class GameAction extends AbstractAction {
        GameAction() {
            super("New Game");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            NewGameFrame dialog = new NewGameFrame(null, "New World");
            dialog.setVisible(true);
            int r = dialog.getRowCount();
            int c = dialog.getColumnCount();
            if ((r > 0) && (c > 0)) {
                worldTable = new Tile[r][c];
                setNewWorld(r, c);
                button.setEnabled(true);
                con.setText("");
                txtf5.setText("<html><font color=green> -- nothing yet -- </html>");
                txtf6.setText("<html><font color=green> -- nothing yet -- </html>");
            }
        }
    }
    
    private class ParamAction extends AbstractAction {
        ParamAction() {
            super("Parametres");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            ParameterDialog dialog = new ParameterDialog(null, "Setup options");
            dialog.setVisible(true);
            rValue = dialog.getR();
            gValue = dialog.getG();
            tolerance = dialog.getTolerance();
            maxIteration = dialog.getMaxIteration();
            txtf1.setText("<html><font color=green>" + rValue + "</html>");
            txtf2.setText("<html><font color=green>" + gValue+ "</html>");
            txtf3.setText("<html><font color=green>" + tolerance+ "</html>");
            txtf4.setText("<html><font color=green>" + maxIteration+ "</html>");        


            SDProblem prob= getProblem();  
            prob.setG(gValue);
            prob.setR(rValue);
            
        }
    }
    
    private class ProbAction extends AbstractAction {
        ProbAction() {
            super("Probability for four directions");
        }
        
        public void actionPerformed(ActionEvent e) {
        	
        	boolean b=true;
            //bring up a window for the number of rows and columns
        	set_flag(0);
            ProbDialog dialog = new ProbDialog(null, "Probability configuration");
            dialog.setProbVector(probability);
            dialog.setVisible(true);
            //probability = dialog.getProbability();    
            
            System.out.println(probability);
            
            SDProblem prob=getProblem();
            
            prob.setProbability(probability);
        }
    }
    
    
    private class OKAction extends AbstractAction {
        OKAction() {
            super("Run Step");
        }
        
        public void actionPerformed(ActionEvent e) {
            //store the state of world
            
        	SDProblem problem = new SDProblem(worldTable, rValue, gValue, tolerance, maxIteration);
        	
        	if (!(problem.finished()))
            {
            	
            	problem.setProbability(probability);
                problem.computeStrategy();
                int AgentAction = problem.nextAgent();
                
                int[][] output = problem.getOutput();
                System.out.println("OUTCOME ");
                
                fireCount++;
            	if (fireCount == 2)
            	{
            		fireCount = 0;
            		FireSpread(output);
            	}
                moveAgent(AgentAction,output);
                setWorldOutput(output);
                
                int lp = problem.getLoopNo();
                if (lp == maxIteration) {
                    con.setForeground(Color.RED);
                    con.setText("Algorithm doesn't converge");
                } else {
                    con.setForeground(Color.BLUE);
                    con.setText("Algorithm does converge");
                }
                double tm = problem.getComputeTime();
                txtf5.setText("<html><font color=green>"+ lp + "</html>");
                txtf6.setText("<html><font color=green>" + tm + " msecs</html>");
            
                for (int i = 0; i < output.length; ++i) {
                    for (int j = 0; j < output[0].length; ++j) {
                        System.out.print(output[i][j] + " ");
                    }
                    System.out.println("");
                }
                problem = new SDProblem(worldTable, rValue, gValue, tolerance, maxIteration);
                setProblem(problem);
                if (problem.finished())
                	button.setEnabled(false);
                
            }
        		
            
        }

    }
    
    
    private class AboutAction extends AbstractAction {
        AboutAction() {
            super("About");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            JPanel about = new JPanel();
            about.setBorder(BorderFactory.createLoweredBevelBorder());
            String s = "<html><b><font size=5>FireFighting Emulator</font></b><hr>";
            s += "<br>Product version:  <font color=blue>0.0.1</font>";
            s += "<br>Java:  <font color=blue>1.5.0_03</font>";
            s += "<br>Author: <font color=blue>Brellas Ioannis</font>";
            s += "<br>Home: <font color=blue>University of Crete</font></font></html>";
            JLabel l = new JLabel(s);
            about.add(l);
            JOptionPane.showMessageDialog(null, about, "About", JOptionPane.PLAIN_MESSAGE);           
        }
    }
    
    private class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }
        
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }
        
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
                setSelected(e.getSource());
                //e.get
            }
        }
    }
    
    private class WallAction extends AbstractAction {
        WallAction() {
            super("Make wall");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            getSelected().setText(null);
            getSelected().setBackground(null);
            getSelected().setIcon(new ImageIcon(imageWall));
            int r = getSelected().getRow();
            int c = getSelected().getColumn();
            worldTable[r][c] = new WallTile();
        }
    }
    
    private class ExitAction extends AbstractAction {
        ExitAction() {
            super("Exit");
        }
        
        public void actionPerformed(ActionEvent e) {
            System.exit(0);        
        }
    }
    
    private class RoomAction extends AbstractAction {
        RoomAction() {
            super("Make empty room");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            getSelected().setText(null);
            getSelected().setBackground(null);
            getSelected().setIcon(new ImageIcon(room));
            int r = getSelected().getRow();
            int c = getSelected().getColumn();
            worldTable[r][c] = new UsualTile();
        }
    }
    
    private class AgentAction extends AbstractAction {
    	AgentAction() {
            super("Make Agent");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
            getSelected().setText(null);
            getSelected().setBackground(null);
            getSelected().setIcon(new ImageIcon(agent));
            int r = getSelected().getRow();
            int c = getSelected().getColumn();
            worldTable[r][c] = new AgentTile();
        }
    }
    
    private class FireAction extends AbstractAction {
        FireAction() {
            super("Make fire");
        }
        
        public void actionPerformed(ActionEvent e) {
            //bring up a window for the number of rows and columns
        	getSelected().setText(null);
            getSelected().setBackground(null);
            getSelected().setIcon(new ImageIcon(fire));
            int r = getSelected().getRow();
            int c = getSelected().getColumn();
            worldTable[r][c] = new FireTile();
        }
    }
    
    private void set_flag(int i){
    	
    	flag=i;
    }
    
    private int get_flag(){
    	
    	return flag;
    }
    
    public static void sleep (int time)
    {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private class MyLabel extends JLabel {
        int row;
        int column;
        
        public MyLabel(String text, int orient) {
            super(text, orient);
        }
        private void setRow(int r) {
            this.row = r;
        }
        
        private void setColumn(int c) {
            this.column = c;
        }
        
        private int getRow() {
            return this.row;
        }
        
        private int getColumn() {
            return this.column;
        }
    }
}
