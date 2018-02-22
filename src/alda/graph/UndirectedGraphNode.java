package alda.graph;

import java.util.HashSet;
import java.util.Set;

public class UndirectedGraphNode<E> {
        private E data;
        private Set<String> credits; //Kan tas bort efter uppbyggnad, pga behöver bara bacon-nummer
        private Set<UndirectedGraphNode<E>> connectedNodes;

        public UndirectedGraphNode(E data) {
            this.data = data;
            credits = new HashSet<>();
            connectedNodes = new HashSet<>();
        }

        public boolean isConnected(UndirectedGraphNode<E> otherNode){
            return connectedNodes.contains(otherNode);
        }

        public void connectNode(UndirectedGraphNode<E> otherNode){
            connectedNodes.add(otherNode);
        }

        public boolean addCredits(String production){
            if (credits.contains(production)){
                return false;
            }
            credits.add(production);
            return true;
        }

        public Set<String> getCredits(){
            return credits;
        }


}
