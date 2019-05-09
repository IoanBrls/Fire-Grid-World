package core;

public class WallTile extends Tile {
    
    /** Creates a new instance of WallTile */
    public WallTile() {
        super();
        setUtility(0);
    }
    
    public void computeUtility(Tile[] tile, double[] probability, double r, double g) {
        //do nothing. It has no utility
    }
    
     public String toString() {
        return "Wall Tile: " + this.getNextBest() + " (" + this.getUtility() + ")";
    }

}
