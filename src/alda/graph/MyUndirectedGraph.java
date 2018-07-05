package alda.graph;

import java.util.*;

public class MyUndirectedGraph<E> implements UndirectedGraph<E> {
    private int totalEdges = 0;
    private Map<E, UndirectedGraphNode<E>> mapOfNodes = new HashMap<>(); //Här ligger alla actors


    @Override
    public int getNumberOfNodes() {
        return mapOfNodes.size();
    }

    @Override
    public int getNumberOfEdges() {
        return totalEdges;
    }

    @Override
    public boolean add(E newNode) {
        if (mapOfNodes.containsKey(newNode)) {
            return false;
        }
        mapOfNodes.put(newNode, new UndirectedGraphNode<>(newNode));
        return true;
    }

    @Override
    public boolean connect(E node1, E node2) {
        UndirectedGraphNode<E> nodeObj1 = mapOfNodes.get(node1);
        UndirectedGraphNode<E> nodeObj2 = mapOfNodes.get(node2);
        if (nodeObj1.isConnected(nodeObj2) || nodeObj1.getData().equals(nodeObj2.getData())) {
            return false;
        }
        totalEdges++; //Ökar inten med antal edges
        return nodeObj1.connectNode(nodeObj2);    //Will iterate through all nodes and connect so bi-directional is not necessary.
    }

    @Override
    public boolean connectNodes(UndirectedGraphNode<E> nodeObj1, UndirectedGraphNode<E> nodeObj2) {
        if (nodeObj1.isConnected(nodeObj2) || nodeObj1.getData().equals(nodeObj2.getData())) { //Om inte är sig själv eller om inte redan kopplade
            return false;
        }
        totalEdges++; //Ökar inten med antal edges.
        return nodeObj1.connectNode(nodeObj2); //Behöver inte koppla ihop bi-directional för detta kommer göras senare i loopen
    }

    @Override
    public boolean isConnected(E node1, E node2) {
        UndirectedGraphNode<E> nodeObj1 = mapOfNodes.get(node1);
        UndirectedGraphNode<E> nodeObj2 = mapOfNodes.get(node2);
        return nodeObj1.isConnected(nodeObj2);
    }


    @Override
    public int breadthFirstSearch(E start, E end) {
        long startTime = System.currentTimeMillis(); //Avbryt körandet om för lång tid, se till så inte oändlig loop
        boolean found = false;
        int baconNumber = 0;
        UndirectedGraphNode<E> startNode = getNode(start); //Skådespelaren som vi vill gå ifrån
        UndirectedGraphNode<E> endNode = getNode(end); //Kevin Bacon eller actor-objektet som vi vill hitta från start

         if (startNode == endNode) { //Kollar om start är slut och returnerar isf baconnummer 0.
            long endTime = System.currentTimeMillis();
            System.out.println("BFS:  "+(endTime - startTime) + " ms");
            return baconNumber;
        } else {
            Set<UndirectedGraphNode<E>> toSearch = new HashSet<>(startNode.getConnectedNodes()); //Hashset av noderna vi ska söka igenom, dvs starts connectedNodes
            Set<UndirectedGraphNode<E>> tempSet = new HashSet<>();
            Set<UndirectedGraphNode<E>> allCheckedNodes = new HashSet<>(); //Alla noder vi kollat igenom
             allCheckedNodes.add(startNode);

            while (!found) {
                if (System.currentTimeMillis() - startTime > 30000){ //Tar oss ur en endless loop om ingen länk finns
                    return -1;
                }
                if (toSearch.contains(endNode)) { //Kollar om vi slutnoden i connectedNodes. Contains är O(1)
                    found = true;
                } else {
                    for (UndirectedGraphNode<E> tempNode : toSearch) {
                        tempSet.addAll(tempNode.getConnectedNodes()); //Lägger till alla connectedNodes från alla kopplade skådespelare till tempSet
                    }
                    allCheckedNodes.addAll(toSearch); //Lägger till alla vi redan kollat i alla kollade
                    tempSet.removeAll(allCheckedNodes); //Tar bort alla noder för att optimera sökningen vi redan kollat från de noder vi ska kolla härnäst.

                    toSearch = tempSet; //Nu är tempSet det vi ska kolla igenom
                    tempSet = new HashSet<>(); //Tömmer tempSet
                }
                baconNumber++;
            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("BFS:  "+(endTime - startTime) + " ms");
        return baconNumber;
    }

    public boolean addCredit(E node1, String production) {
        return mapOfNodes.get(node1).addCredit(production); //Tar in ett actorName och lägger till crediten i skådespelarnodens creditlista
    }

    public Collection<UndirectedGraphNode<E>> getAllNodes() {
        return mapOfNodes.values();
    }

    public UndirectedGraphNode<E> getNode(E node1) {
        try{
            return mapOfNodes.get(node1);
        } catch (NullPointerException e){
            System.out.println("No such node found: " + node1 + "\nError: " + e);
        }
        return null;
    }

    public boolean contains(String actorName){
        return mapOfNodes.containsKey(actorName);
    }

    @Override
    public String toString() {
        return "Amount of nodes: " + getNumberOfNodes() + " Amount of edges: " + getNumberOfEdges();
    }
}
