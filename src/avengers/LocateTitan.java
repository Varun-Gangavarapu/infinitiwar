package avengers;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {

    private static int getMinCostNode(int[] minCost, boolean[] set) {
        int minVal = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0 ; i < set.length; i++) {
            if (!set[i]) {
                if (minCost[i] < minVal) {
                    minVal = minCost[i];
                    minIndex = i;
                }
            }
        }
        return minIndex;
    }
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        } 

    	// WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);  // Change to args[0] and args[1]
        StdOut.setFile(args[1]);

        HashMap<Double, Double> wormhole = new HashMap<Double, Double>();
        double n = StdIn.readDouble();
        for (int i = 0; i < n; i++) {

            wormhole.put(StdIn.readDouble(), StdIn.readDouble());
        }
        int[][] adjacencyList = new int[wormhole.size()][wormhole.size()];
        for (int i = 0; i < wormhole.size(); i++) {
            for (int j = 0; j < wormhole.size(); j++) {
                adjacencyList[i][j] = (int)(StdIn.readDouble() / (wormhole.get(i*1.0) * wormhole.get(j*1.0)));
            }
        }
        
        int[] minCost = new int[(int)n];
        boolean[] set = new boolean[(int)n];

        for (int i = 0; i < minCost.length; i++) {
            minCost[i] = Integer.MAX_VALUE;
            if (i == 0) {
                minCost[i] = 0;
            }
        }
        
        for (int i = 0; i < (int) n; i++) {
            int currentNode = getMinCostNode(minCost, set);
            set[currentNode] = true;

            for (int j = 0; j < adjacencyList[currentNode].length; j++) {
                if (adjacencyList[currentNode][j] != 0) {
                    if (!set[j] && minCost[currentNode] != Integer.MAX_VALUE && minCost[j] > (minCost[currentNode] + adjacencyList[currentNode][j])) {
                        minCost[j] = minCost[currentNode] + adjacencyList[currentNode][j];
                    }
                }
            }
        }

        StdOut.print(minCost[minCost.length-1]);

    }
}
