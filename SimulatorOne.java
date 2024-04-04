

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import Graph.java;
import Graphs.Graph;
/**
 * This class should handle the input of data from system.in 
 * <number of nodes><newline>
*{<source node number> {<destination node number> <weight>}*<newline>}*
*<number of shops><newline>
*{<shop node number>}*<newline>
*<number of clients><newline>
*{<client node number>}*<newline>
*(‘{x}*’ mean zero or more of item x.)
 */


public class SimulatorOne{


 public static void main(String[] args) {
           
        int numNodes;
        int numShops, numClients; 
       

        Graph graph = new Graph();
       

        ArrayList<int[]> list = new ArrayList<>();
        

        File path = new File("input.txt");
        try (Scanner input = new Scanner(path)) {
          while(input.hasNextLine()){
                    String[] a = input.nextLine().split(" ");//create an array of the string of numbers 
                    int[] b = new int[a.length];
                    for(int i=0; i<a.length;i++){
                              b[i] = Integer.parseInt(a[i]);  //convert the string array into \a number array
                                     }

                     list.add(b);
          
                  }
} catch (FileNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
}


/**
 * at this point list contains a list of arrays of the input
 * every time we have a single entry we change the type of input
 * we need to loop through the list array assigning the number of Nodes and vertices 
 * CREATE THE REQUIRED GRAPH
 */

System.out.println(Arrays.deepToString(list.toArray()));
int id =0;
int[] shops = new int[0];
int[] clients = new int[0];


for(int[] li: list){
        if(id==0 && li.length==1){
                numNodes = li[0];
                id++;
        }else if(id==1 && li.length==1){
                numClients = li[0];
                id++;

        }else if(id==2 && li.length==1){
                numShops = li[0];
                id++;
        }else if( id==1 && li.length>1){
                for(int i = 0;i<li.length-1; i+=2){
                graph.addEdge(String.valueOf(li[0]), String.valueOf(li[i+1]), li[i+2]);
                System.out.println("Added " +li[i+1] + " " + li[i+2]);
        }
        }else if (id==2 && li.length >= 1){
                numShops = li.length;
                shops = li;
                for (int i : li){
                      graph.getVertex(String.valueOf(i)).setShop_Client(" shop");
                      System.out.println("Set node " + i + " as shop " );
                }

        }else if(id==3 && li.length>=1){
                clients = li;
                
                
                for(int i:li){
                        graph.getVertex(String.valueOf(i)).setShop_Client(" client");
                        System.out.println("Set node " + i + " as client " );
                }
        }
}


//at this point the graph is full
/**
 * Need to figure out a way to get the distances from the shops clients in the array clients
 * 1-> we could calculate the dijkstra from the client node to the shop and save the least cost path with the shop number 
 * 2-> we will then calculate the least cost path from the client to the shops 
 * 3-> 
 * NB :: The results for a client (at a given node) consist of details of the lowest cost pick up (shortest
*path from a taxi to the client), and then details of the lowest cost drop off (shortest path from the
*client to a shop). therefore we must find the taxi with 
 */

System.out.println("shops " +Arrays.toString(shops)   );
System.out.println("client " +Arrays.toString(clients)   );


ArrayList<Integer[]> S_2_C = new ArrayList<>();//shops to Clients
ArrayList<Integer[]> C_2_S= new ArrayList<>();


for (int i: shops){
        graph.dijkstra(String.valueOf(i));
        S_2_C.add(graph.printer(String.valueOf(clients[0])));
}
System.out.println("Shop to Client");
for (Integer[] i : S_2_C){
        System.out.println(Arrays.toString(i));
}

graph.dijkstra(String.valueOf(clients[0]));
for (int j: shops){
   C_2_S.add(graph.printer(String.valueOf(j)));
}
System.out.println("Client to Shop");
for (Integer[] i : C_2_S){
        System.out.println(Arrays.toString(i));
}



// now we want to add these 2 arrays in such a way that we have this will give
//this gives the least cost pair between the arrays of 
ArrayList<Integer[]> leastCostPair = new ArrayList<>();
Integer[] gap = {0};//this will be used when we have the
leastCostPair = (ArrayList<Integer[]>) findLeastCostPairs(S_2_C, C_2_S);




System.out.println("\n \n Least Cost pairs \n\n");
for (Integer[] i : leastCostPair){
        System.out.println(Arrays.toString(i));
}

int finalClient =0;
int  finalTaxi =0;
int finalShop =0;
ArrayList<Integer> taxiRoute = new ArrayList<>();

if(leastCostPair.size()==2){
        Integer[] shopToClient = leastCostPair.get(0);
        Integer[] clientToShop = leastCostPair.get(1);
        taxiRoute.add(shopToClient[1]);
        taxiRoute.add(clientToShop[1]);

        finalClient  = shopToClient[2];
        finalTaxi =shopToClient[1];
        finalClient = clientToShop[1];



}



System.out.println("client " + finalClient +"\ntaxi " + "\n" +  taxiRoute.get(0) + " " + taxiRoute.get(1) + "\nshop\n"+ finalShop);






       
        
          
 }
 
    public static List<Integer[]> findLeastCostPairs(List<Integer[]> S_2_C, List<Integer[]> C_2_S) {
        int minCost = Integer.MAX_VALUE;
        List<Integer[]> leastCostPairs = new ArrayList<>();

        for (Integer[] i : S_2_C) {
            for (Integer[] j : C_2_S) {
                int currentCost = i[0] + j[0];
                if (currentCost < minCost) {
                    minCost = currentCost;
                    leastCostPairs.clear(); // Clear the list since we found a smaller sum
                    leastCostPairs.add(i);
                    leastCostPairs.add(j);
                }else if(currentCost == minCost){
                        Integer[] a = {0};//seperator to show that we have mkore than one possible path
                        leastCostPairs.add(a);
                        leastCostPairs.add(i);
                        leastCostPairs.add(j);

                }
            }
        }
        return leastCostPairs;
    }



}
       

        






         

