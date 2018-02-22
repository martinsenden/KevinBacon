package alda.graph;

import java.util.*;

public class MyUndirectedGraph<E> implements UndirectedGraph<E> {
    private int totalEdges = 0;
    Map<E, UndirectedGraphNode<E>> mapOfNodes = new HashMap<>();

    Map<E, HashSet<E>> creditsMap = new HashMap<>();//Tänk på att den här kan tas bort när uppbyggnad är färdig

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
        if (nodeObj1.isConnected(nodeObj2)){
            return false;
        }
        nodeObj1.connectNode(nodeObj2);
        nodeObj2.connectNode(nodeObj1);
        return true;
    }

    @Override
    public boolean isConnected(E node1, E node2) {
        UndirectedGraphNode<E> nodeObj1 = mapOfNodes.get(node1);
        UndirectedGraphNode<E> nodeObj2 = mapOfNodes.get(node2);
        if (nodeObj1.isConnected(nodeObj2)){
            return true;
        }
        return false;
    }

    @Override
    public List<E> breadthFirstSearch(E start, E end) {
        return null;
    }

}
