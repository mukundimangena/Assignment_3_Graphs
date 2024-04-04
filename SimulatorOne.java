

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
 ArrayList<Integer[]> cost = new ArrayList<>();
 for(int i: clients){
        graph.dijkstra(String.valueOf(i));

        for (int j : shops){
                cost.add(graph.printer(String.valueOf(j)));
                //System.out.println(graph.printer(String.valueOf(j)));
              

        }
        

 }

for (Integer[] i : cost){
        System.out.println(Arrays.toString(i));
}






















//  int identifier  = 0;//used to track the input of the # of nodes 
//  numNodes = list.get(0)[0]; //since this will always be at the beginning of the input
//  for (int i = 1; i<list.size(); i++){
//           int[] a = list.get(i);
//           if (a.length==1 && identifier==0){
//                     numShops = a[0];
//                     identifier++;

//           }else if(a.length==1 && identifier==2){
//                   numClients = a[0];
//                   identifier++;

//           }else if(identifier==0){
//                     int len = a.length;
//                     for (int j =0;j<len;j+=2){
//                               //add edges to the graph
//                               graph.addEdge(String.valueOf(a[0]),String.valueOf(a[j+1]), a[j+2]);
//                     }
//           }else if (identifier==1){
//                     //Assign nodes that are that are shops 
//                     shopNodes = a;
                  

//           }else if(identifier==2){
//                     //assign the nodes that are clients
//                     clientNodes = a;
//           }

//  }


/**
 * The next step is taking the array of the client nodes and  and for each of the client find out 
 * What taxi(shopNode) passes through the client and going somewhere has the least cost
 * For now i think we could utilise a a looping mechanism whereby
 * 
 */
       
        
          
 }
       

        

        






         

}