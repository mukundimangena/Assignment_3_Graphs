

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
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
       
         int[] abs = {1,2,3,4,5,6};
         System.out.println(abs[abs.length-1]);

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
              
        }
        }else if (id==2 && li.length >= 1){
                numShops = li.length;
                shops = li;
                for (int i : li){
                      graph.getVertex(String.valueOf(i)).setShop_Client(" shop");
                     
                }

        }else if(id==3 && li.length>=1){
                clients = li;
                
                
                for(int i:li){
                        graph.getVertex(String.valueOf(i)).setShop_Client(" client");
                     
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




ArrayList<Integer[]> S_2_C = new ArrayList<>();//shops to Clients
ArrayList<Integer[]> C_2_S= new ArrayList<>();

/**
 * create a method that takes in the the array of the shops and the client 
 * they are going to and finds the dijkstra and returns an arraylist of those values
 */
S_2_C = ShopToClientDjikstra(shops,clients[0], graph);
C_2_S = ClientToShopDjikstra(clients[0], shops, graph);



// now we want to add these 2 arrays in such a way that we have this will give
//this gives the least cost pair between the arrays of 
ArrayList<Integer[]> leastCostPair = new ArrayList<>();

leastCostPair = (ArrayList<Integer[]>) findLeastCostPairs(S_2_C, C_2_S);
System.out.println(printOutResults(leastCostPair));

     
          
 }
 /**
  * find the least cost pair return as 2 arrays with the distances and nodes they pass through to get to 
  *the final
  * @param S_2_C 
  * @param C_2_S
  * @return
  */
 
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

 /**
  * Takes an Array of shops , the client they are going to and the graph
  *it will return arrays of the least cost paths with distances 
  *Output Array {distance , startShopNode, *path,destinationNode}
  */
    public static ArrayList<Integer[]> ShopToClientDjikstra(int[] shops, int client, Graph graph){
        ArrayList<Integer[]> list = new ArrayList<>();
        for(int i : shops){
           
                graph.dijkstra(String.valueOf(i));
                list.add(graph.printer(String.valueOf(client)));
        }


        return list;

    }

    /**
     * 
     * @param client
     * @param shops
     * @param graph
     * @return
     */
    public static ArrayList<Integer[]> ClientToShopDjikstra(int client, int[] shops, Graph graph){
        ArrayList<Integer[]> list = new ArrayList<>();
        graph.dijkstra(String.valueOf(client));
        for(int i: shops){
                list.add(graph.printer(String.valueOf(i)));
                
        }



        return list;
    }
//Maybe actually add the least costs path array to the print results 

//the Multiple costs will be achieved by checking the size of the least costs if its grater than 2 it means we have mutiple paths 
    public static String printOutResults(ArrayList<Integer[]> leastcost){
        String output = "";
      
       if (leastcost.size()==2){
       output = printfor2(leastcost.get(0),leastcost.get(1) );

       }else {
        String client = String.valueOf(leastcost.get(0)[leastcost.get(0).length-1]);
        String shopval = String.valueOf(leastcost.get(1)[leastcost.get(1).length-1]);
        StringBuilder taxi = new StringBuilder();
        String shop ="";

        for (int i = 0; i<leastcost.size();i+=3){
            
              Integer[] shop2C = Arrays.copyOfRange(leastcost.get(i), 1, leastcost.get(i).length);
              taxi.append("taxi ").append(shop2C[0]).append("\n");
              for(Integer j: shop2C){
                taxi.append(j).append(" ");

              }
           
        }
        for(int i:Arrays.copyOfRange(leastcost.get(1),1, leastcost.get(1).length) ){shop += i +" ";}

          output = "client " + client +"\n" + taxi+"\n"+ "shop "+shopval+"\n"+shop ;
       }


        return output;
    }


//     public static String printfor2(Integer[] shop2Client, Integer[] client2Shop){
//         Integer[] shopToClient = shop2Client;
//         Integer[] clientToShop = client2Shop ;

//         Integer client = shopToClient[shopToClient.length-1];
//         Integer taxi = shopToClient[1];
//         Integer shop = clientToShop[shopToClient.length-1];
//         Integer[] taxiroutetoClient = Arrays.copyOfRange(shopToClient,1 , shopToClient.length);
//         Integer[] taxiRouteToShop = Arrays.copyOfRange(clientToShop, 1, clientToShop.length);

//         String taxiRouteString="";
//         String clientRouteString ="";
//         for(Integer i: taxiroutetoClient){
//                 taxiRouteString += String.valueOf(i)+ " ";
//         }
//         for(Integer i: taxiRouteToShop){
//                 clientRouteString += String.valueOf(i)+ " ";
//         }

 
//              return  "client " + client +"\n"+ "taxi "+ taxi + "\n"+ taxiRouteString +"\n"+ "shop " + shop + "\n" + clientRouteString;
        
//     }


//     public static String output(int shops, int clients){
        
// ArrayList<Integer[]> S_2_C = new ArrayList<>();//shops to Clients
// ArrayList<Integer[]> C_2_S= new ArrayList<>();

// for (int i : clients){
//         S_2_C = ShopToClientDjikstra(shops,clients[0], graph);
//         C_2_S = ClientToShopDjikstra(clients[0], shops, graph);
            
// }

 
// S_2_C = ShopToClientDjikstra(shops,clients[0], graph);
// C_2_S = ClientToShopDjikstra(clients[0], shops, graph);




// ArrayList<Integer[]> leastCostPair = new ArrayList<>();

// leastCostPair = (ArrayList<Integer[]>) findLeastCostPairs(S_2_C, C_2_S);
// System.out.println(printOutResults(leastCostPair));




//         return "";
//     }
}
       

        






         

