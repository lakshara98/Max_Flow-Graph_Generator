/*
*Name - Lakshara Warnakula
*IIT No - 2017384
* S3Reuis, princiraj(1992)Ford-Fulkerson Algorithm for Maximum Flow Problem[Source code].https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
*/
import java.util.*;
import java.lang.*;
import java.util.LinkedList;

public class MaximumFlow {

    //Random number of nodes(6 to 12 nodes including source and sink)
    static final int node = (int) (Math.random() * (12 - 6)) + 6;

    /*A method which returns true if there is a path in the residual graph from source to sink
     *Fills the pth array with the path
     */
    boolean isAPath(int residualCapacity[][], int source, int sink, int path[]) {

        /*Create an array to hold nodes
         *mark all tha nodes as nodes which are not visited
         */
        boolean isVisited[] = new boolean[node];

        for (int i = 0; i < node; ++i)
            isVisited[i] = false;

        //Create a LinkedList, add the source to it and mark it as a visited node
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(source);
        isVisited[source] = true;
        path[source] = -1;

        //Breadth-first search loop
        while (list.size() != 0) {
            int j = list.poll();

            for (int k = 0; k < node; k++) {
                if (isVisited[k] == false && residualCapacity[j][k] > 0) {
                    list.add(k);
                    path[k] = j;
                    isVisited[k] = true;
                }
            }
        }

        //Return true if it reach the sink from the source
        return (isVisited[sink] == true);
    }

    //Method to return the maximum flow from the source to sink in the randomly generated graph
    int maxFlow(int capacity[][], int source, int sink) {
        int j, k;

        //A 2d array to hold residual capacities of nodes
        int residualCapacity[][] = new int[node][node];

        //Fill the residualCapacity array with the capacities given in the capacity array
        for (j = 0; j < node; j++)
            for (k = 0; k < node; k++)
                residualCapacity[j][k] = capacity[j][k];

        //Array to hold the path from the source to sink
        int path[] = new int[node];

        //No flow at first
        int maximum = 0;

        //Augment the flow of the path found
        while (isAPath(residualCapacity, source, sink, path)) {

            ArrayList<Integer> board = new ArrayList<Integer>();

            //Find maximum flow through the path
            int pathOfFlow = Integer.MAX_VALUE;

            for (k = sink; k != source; k = path[k]) {
                j = path[k];
                pathOfFlow = Math.min(pathOfFlow, residualCapacity[j][k]);
                board.add(k);
            }
            board.add(0);
            System.out.println(board);

            //Update the residualCapacity graph
            for (k = sink; k != source; k = path[k]) {
                j = path[k];
                residualCapacity[j][k] -= pathOfFlow;
                residualCapacity[k][j] += pathOfFlow;
            }

            // Add the path found to the flow to the current flow
            maximum += pathOfFlow;

            System.out.println("Flow of the current path : " + pathOfFlow);
            System.out.println("Maximum Flow : "+maximum);
            System.out.println("Graph");
            System.out.println(Arrays.deepToString(residualCapacity)+"\n");

            MaximumFlowGUI maximumFlowGUI = new MaximumFlowGUI();
            maximumFlowGUI.createOtherGeneratedGraphs(residualCapacity,board);
        }

        // Return the flow
        return maximum;
    }

}