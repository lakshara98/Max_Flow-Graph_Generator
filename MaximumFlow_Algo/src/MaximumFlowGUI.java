/*
 *Name - Lakshara Warnakula
 *IIT No - 2017384
 */
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Arrays;

public class MaximumFlowGUI{


    //Create a 2D array to store capacities
    static int[][] capacity = new int[MaximumFlow.node][MaximumFlow.node];

    static int row = 0;
    static int col = 0;

    public static void main(String[] args) {
        testing();

    }

    public static void testing() {

        //Iterating through rows in 2D matrix
        for (row = 0; row < capacity.length; row++) {

            /*Iterate through columns in 2D matrix
             *Generate random values between 5 to 20 as capacities
             */
            for (col = 0; col < capacity[row].length; col++) {
                capacity[row][col] = (int) (Math.random() * (20 - 6)) + 6;

                //Assign value 0 to all the cells in column 1
                if (capacity[row][0] != 0) {
                    capacity[row][0] = 0;
                }

                //Assign value 0 to all the cells in the last row
                if (capacity[capacity.length - 1][col] != 0) {
                    capacity[capacity.length - 1][col] = 0;
                }

                //Assign value 0 to the last cell in th row 1
                if (capacity[0][capacity.length - 1] != 0) {
                    capacity[0][capacity.length - 1] = 0;
                }

                //Assign value 0 to all the cells diagonally
                if (row == col) {
                    capacity[row][col] = 0;
                }
            }
        }


        //Create an instace of the MaximumFlow class
        MaximumFlow max = new MaximumFlow();

        long startTime = System.nanoTime();

        System.out.println("The Maximum FLow of this graph is: "+
                max.maxFlow(capacity,0,MaximumFlow.node -1)+"\n");

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " +
                timeElapsed / 1000000+"\n");

        //Print all the elements in the 2d array
        System.out.println("The following is the original graph");
        System.out.println(Arrays.deepToString(capacity));
        System.out.println("Size " + capacity[0].length);
        System.out.println("nodes " + MaximumFlow.node);

        MaximumFlowGUI maximumFlowGUI = new MaximumFlowGUI();
        maximumFlowGUI.createOriginalGraph();
    }

    //create original graph
    public void createOriginalGraph(){

        ArrayList<Integer> emty_coloured_edges = new ArrayList<>();

        Graph orginal_graph = new SingleGraph("Original Graph");

        orginal_graph.addAttribute("ui.stylesheet", "url('css/style.css')");

        orginal_graph.setStrict(false);
        orginal_graph.setAutoCreate(true);
        orginal_graph.display();

        initiateGraph(capacity,orginal_graph,emty_coloured_edges);

        for (Node node : orginal_graph) {
            int node_number = Integer.valueOf(node.getId());
            node.addAttribute("ui.label", "Node " + node_number);
        }

    }

    //create graph which are generating from the maxflow algorithm
    public void createOtherGeneratedGraphs(int[][] nodes, ArrayList<Integer> colouredges){

        Graph graph = new SingleGraph("Generated Graph");

        graph.addAttribute("ui.stylesheet", "url('css/style-original.css')");

        graph.setStrict(false);
        graph.setAutoCreate(true);

        graph.display();

        initiateGraph(nodes,graph,colouredges);

    }

    //initializing the graph using 2D array
    public void initiateGraph(int[][] nodes, Graph graph, ArrayList<Integer> colouredges){

            for (int i = 0; i < nodes.length; i++) {
                for (int j = 0; j < nodes[i].length; j++) {
                    if (i != j) {
                        if (nodes[i][j] != 0) {
                            String edge = i + "" + j;
                            String node1 = String.valueOf(i);
                            String node2 = String.valueOf(j);
                            graph.addEdge(edge, node1, node2, true);
                        }
                    }
                }
            }



        for(int i=colouredges.size(); i>1; i--) {
            for (Node node : graph) {
                if (colouredges.get(i-1) == Integer.valueOf(node.getId())){

                    String edge = colouredges.get(i-1)+""+colouredges.get(i-2);

                    String node1 = String.valueOf(colouredges.get(i-1));
                    String node2 = String.valueOf(colouredges.get(i-2));

                    graph.addEdge(edge, node1, node2, true).addAttribute("ui.class","important");
                }

                     int node_number = Integer.valueOf(node.getId());
                     node.addAttribute("ui.label", "Node " + node_number);
            }
        }


    }


}


