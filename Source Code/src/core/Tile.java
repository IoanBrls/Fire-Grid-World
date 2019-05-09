package core;

public abstract class Tile {

    private int nextBest;
    private double utility;
    
    /** Creates a new instance of Tile */
    public Tile() {
    }
    
    /**
     * computes and stores utility of this tile.
     * @param tile An array with the neighbor tiles
     * @param probability An array with the probability of every neighbor tile
     * @param r Reward 
     * @param g Discount
     */
    public abstract void computeUtility(Tile[] tile, double[] probability, double r, double g);

    public double getUtility() {
        return this.utility;
    }
    public void setUtility(double utility) {
        this.utility = utility;
    }
    
    public void setNextBest(int nextBest) {
        this.nextBest = nextBest;
    }
    
    public int getNextBest() {
        return this.nextBest;
    }
    
    public abstract String toString();
    
}
