package core;

public class FireTile extends Tile {
    
    /** Creates a new instance of EndTile */
    public FireTile() {
        super();
        setUtility(5);
    }
    
    public void computeUtility(Tile[] tile, double[] probability, double r, double g) {
        //do nothing. Its utility remains constant.
    }
    
     public String toString() {
        return "End Tile: " + this.getNextBest() + " (" + this.getUtility() + ")";
    }
	
}
