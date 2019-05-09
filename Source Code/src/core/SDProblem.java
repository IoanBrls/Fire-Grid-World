package core;

public class SDProblem {
    private double r;
    private double g;
    private Tile[][] impWorld;
   
    public int rowC;
    public int colC;
     
    private int[][] output;
    private int maxIteration;
    private int loop;
    private double tolerance;
    private double[] prob = {0.3, 0.3, 0.3, 0.1};
    private double computeTime;
    private int flag=0;

        
    
    /** Creates a new instance of SDProblem */
    public SDProblem(Tile[][] world, double r, double g, double tolerance, int maxIt) {
        this.r = r;
        this.g = g;
        this.tolerance = tolerance;
        this.rowC = world.length + 2;
        this.colC = world[0].length + 2;
        this.impWorld = createNewWorld(world);
        this.output = new int[rowC-2][colC-2];
        maxIteration = maxIt;
        flag=0;
        
    }
    
    private Tile[][] createNewWorld(Tile[][] world) {
        Tile[][] nW = new Tile[rowC][colC];
        
        for(int i = 0; i < rowC; ++i) {
            for (int j = 0; j < colC; ++j) {
                if ((i == 0) || (j == 0) || (i == (rowC-1)) || (j == (colC-1))) {
                    nW[i][j] = new WallTile();
                } else {
                    nW[i][j] = world[i-1][j-1];
                }
            }
        }
        return nW;
    }
    
    public int getLoopNo() {
        return this.loop;
    }
    
    private void setLoopNo(int loop) {
        this.loop = loop;
    }
    
    private void setComputeTime(double time) {
        this.computeTime = time;
    }
    
    public double getComputeTime() {
        return this.computeTime;
    }
    
    //checks if there are no more firetiles in the world
    
    public boolean finished()
    {
    	int count = 0;
    	for (int i = 0; i < rowC; ++i) {
            for (int j = 0; j < colC; ++j) {
            	if (impWorld[i][j] instanceof FireTile)
            		count++;
            }
    	}
    	if (count == 0)
    		return true;
    	return false;
    	
    }
    
    //returns the next best action that the agent can perform
    //according to the computed strategy.
    public int nextAgent()
    {
    	
    	for (int i = 0; i < rowC; ++i) {
            for (int j = 0; j < colC; ++j) {
            	if (impWorld[i][j] instanceof AgentTile)
            	{
            		return impWorld[i][j].getNextBest();
            	}
            }
    	}
    	return -1;
        	
    	
    }
    
    //Computes the best strategy for the agent for every step of the simulation.
    
    public void computeStrategy() {
        int counter = 0;
        boolean convergence = false;
        double[] preUtil = new double[rowC*colC];
        double[] postUtil = new double[rowC*colC];
        
        for (int i = 0; i < rowC; ++i) {
            for (int j = 0; j < colC; ++j) {
                preUtil[i*colC + j] = impWorld[i][j].getUtility();
            }
        }
        //start timer
        double start = System.currentTimeMillis();
        while ((!convergence) && (counter != maxIteration)) {
            for (int z = 0; z < rowC*colC; ++z) {
                preUtil[z] = postUtil[z];
            }
                       
            for (int i = 1; i < (rowC-1); ++i) {
                for (int j = 1; j < (colC-1); ++j) {
                    Tile neightbour[] = new Tile[4];
                    neightbour[0] = impWorld[i-1][j];
                    neightbour[1] = impWorld[i][j+1];
                    neightbour[2] = impWorld[i+1][j];
                    neightbour[3] = impWorld[i][j-1];
                    impWorld[i][j].computeUtility(neightbour, prob, r, g);
                    postUtil[i*colC + j] = impWorld[i][j].getUtility();
                }
            }
            convergence = checkConvergence(preUtil, postUtil, tolerance);
            ++counter;
        }
        //end time
        double end = System.currentTimeMillis();
        setComputeTime(end-start);
        setLoopNo(counter);
        
        System.out.println("After " + counter + " iterations, got the result:");
        for (int i = 1; i < (rowC-1); ++i) {
            for (int j = 1; j < (colC-1); ++j) {
                output[i-1][j-1] = impWorld[i][j].getNextBest();
                System.out.println(impWorld[i][j]);
            }
            System.out.println("========");
        }
        
        
    }
    
    
    public void setProbability(double[] prob) {
        if (prob != null) {
            this.prob = prob;
        } else {
            this.prob[0] = 0.8;
            this.prob[1] = 0.1;
            this.prob[2] = 0.0;
            this.prob[3] = 0.1;
        }
    }
    
    public int[][] getOutput() {
        return this.output;
    }
    
   public void setflag(int i){
	   flag=i;
   }
   
   public int getflag(){
	   return flag;
   }
   
   public void setR(double a){
	   r=a;
   }

   public void setG(double a){
	   g=a;
   }
    
    public static boolean checkConvergence(double[] table1, double[] table2, double tolerance) {
        double diff = 0;
        for (int i = 0; i < table1.length; ++i) {
            diff += Math.sqrt(Math.pow((table1[i]-table2[i]), 2));
        }
        if (diff < tolerance) {
            return true;
        }
        return false;
    }
    
}
