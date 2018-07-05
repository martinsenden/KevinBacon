package alda.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphBuilder {
    private MyUndirectedGraph<String> graph;
    private HashMap<String, HashSet<String>> creditMap; //Map av alla produktioner. Anledning till hashset = Actor får bara finnas en gång. Effektiviserar och tar bort stora sjok. Tar bort O(N^N).

    public void buildGraph(MyUndirectedGraph graph) {
        long startTime = System.currentTimeMillis();
        creditMap = new HashMap<>(); //Nyckel produktion, värde HashSet med skådespelarna
        this.graph = graph;
        parseFileToNodesAndMap(); //Läser in filen mha BaconReader och skapar alla Actor nodes
        connectAllNodes();
        long endTime = System.currentTimeMillis();
        System.out.println("Graph build time: "+(endTime - startTime)/1000 + " s");
    }

    private void parseFileToNodesAndMap() {
        try {

            BaconReader br = new BaconReader("/Users/martinsenden/Desktop/Programmering/ALDA/KevinBacon/src/alda/graph/actresses.list");
            //BaconReader br = new BaconReader("/Users/peradrianbergman/Documents/ALDA/Kod/src/alda/graph/actresses.list");
            //BaconReader br = new BaconReader("/Users/peradrianbergman/Documents/ALDA/Kod/src/alda/graph/actressesTest.list");
            BaconReader.Part pr = br.getNextPart();
            String tempAct = "";
            Boolean title = false;
            String key = "";

            while (pr != null) {


                if (pr.type.toString().equals("NAME")) {
                    tempAct = pr.text.toString();
                    graph.add(tempAct); //Kan inte lägga in actors två ggr pga add-metoden kollar contains
                    pr = br.getNextPart();

                }
                if (pr.type.toString().equals("TITLE")) {
                    key = key + pr.text.toString(); //Bygger upp nyckeln med titel + år + id
                    title = true;
                    pr = br.getNextPart();
                }

                if (pr.type.toString().equals("YEAR") || pr.text.equals("????")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if (pr.type.toString().equals("ID")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if (pr.type.toString().equals("INFO")) {
                    pr = br.getNextPart(); //Skippar enligt instruktionerna
                }

                if (creditMap.containsKey(key) && title) { //Om produktionen redan finns
                    creditMap.get(key).add(tempAct); //Lägg till skådespelare till produktionen
                    graph.addCredit(tempAct, key); //Lägger till produktionen till skådespelaren
                    key = "";
                    title = false;

                } else if (!creditMap.containsKey(key) && title) {
                    HashSet creditSet = new HashSet();
                    creditSet.add(tempAct); //Lägger till skådespelaren till produktionen.
                    creditMap.put(key, creditSet); //Lägger till ny produktion. Effektiviserar och tar bort stora sjok. Tar bort O(N^N).
                    graph.addCredit(tempAct, key); //Lägger till produktionen till skådespelaren
                    title = false;
                    key = "";
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connectAllNodes() {                                                // Effektiviseringen! O(N*K*Y) istället för O(N^N)
        HashSet<String> credits;                                                    //En nod kan inte länka till sig själv
        for (UndirectedGraphNode<String> actorNode : graph.getAllNodes()) {         //För varje skådespelare i grafen, gå i genom alla noder i grafen
            credits = new HashSet<>(actorNode.getCredits());                        //Sätt credits till skådespelarens produktioner
            for (String credit : credits) {                                         //För skådespelarens varje produktion
                for (String actor : creditMap.get(credit)) {                         //För varje skådespelare som varit i en produktion som vår skådespelare varit med i
                    graph.connectNodes(actorNode, graph.getNode(actor));             //Koppla ihop skådespelarna som varit med i produktionen med vår skådespelare.
                }
            }
        }
    }
}
