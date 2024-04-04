package Graphs;

import java.util.LinkedList;
import java.util.List;

// Represents a vertex in the graph.
 public class Vertex
{
    public String     name;   // Vertex name
    public List<Edge> adj;    // Adjacent vertices
    public double     dist;   // Cost
    public Vertex     prev;   // Previous vertex on shortest path
    public int        scratch;// Extra variable used in algorithm
    public String    soc;//true for shop false for client


    /***** */
    public String getShop_Client() {
        return soc;
    }

    public void setShop_Client(String soc) {
        this.soc = soc;
    }
   /********** */
    public Vertex( String nm )
      { name = nm; adj = new LinkedList<Edge>( ); reset( ); }

    public void reset( )
    //  { dist = Graph.INFINITY; prev = null; pos = null; scratch = 0; }    
    { dist = Graph.INFINITY; prev = null; scratch = 0; }
      
   // public PairingHeap.Position<Path> pos;  // Used for dijkstra2 (Chapter 23)
}