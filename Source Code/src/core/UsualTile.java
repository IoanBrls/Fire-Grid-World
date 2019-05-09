package core;

public class UsualTile extends Tile {
    
    /** Creates a new instance of UsualTile */
    public UsualTile() {
        super();
        setUtility(0);
    }
    
    
    //Implementation of the Value Iteration algorithm
    
    public void computeUtility(Tile[] tile, double[] probability, double r, double g) {
        int nextBest = -1;
        double maxUtility = -10000;// Double.MIN_VALUE;
        
        if (tile.length != probability.length) {
            return;
        }
        for (int orient = 0; orient < tile.length; ++ orient) {
            double utility = 0;
            for (int it = 0; it < tile.length; ++it) {
                int index = (it + orient) % tile.length; 
                Tile t = tile[index];
                // if t is not a usual tile (that is is a wall-tile or a border-tile)
                // then use utility of this tile.
                if (!(t instanceof WallTile)) {
                    utility += t.getUtility()*probability[it];
                } 
               
                
                else {
                    utility += this.getUtility()*probability[it];
                }
            }
            
            if (utility > maxUtility) {
                nextBest = orient;
                maxUtility = utility;
            }
        }
        
        this.setUtility(r + g*maxUtility);
        this.setNextBest(nextBest);          
    }
    
    public String toString() {
        return "Usual Tile: " + this.getNextBest() + " (" + this.getUtility() + ")";
    }
    
}
