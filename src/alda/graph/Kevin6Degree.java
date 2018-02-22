package alda.graph;


import java.io.IOException;
import java.util.*;

public class Kevin6Degree {

    public static void main(String[] args) {
        try {
            HashMap<String, HashSet<String>> creditMap = new HashMap<>();
            BaconReader br = new BaconReader("/Users/martinsenden/Desktop/Programmering/ALDA/KevinBacon/src/alda/graph/actressesTest.list");
            BaconReader.Part pr = br.getNextPart();
            String tempAct = "";
            Boolean title = false;
            //Boolean year = false;
            String key = "";

            while(pr != null){

                System.out.println("-------------------");

                if(pr.type.toString().equals("NAME")){
                    tempAct = pr.text.toString();
                    System.out.println(pr.type.toString());

                    pr = br.getNextPart();

                }
                if(pr.type.toString().equals("TITLE")) {
                    key = key + pr.text.toString();
                    title = true;
                    System.out.println(pr.type.toString());
                    pr = br.getNextPart();
                }

                if(pr.type.toString().equals("YEAR") || pr.text.equals("????")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if(pr.type.toString().equals("ID")) {
                    key = key + pr.text.toString();
                    pr = br.getNextPart();
                }
                if(pr.type.toString().equals("INFO")) {
                    //System.out.println(pr.type.toString());
                    System.out.println("Info-text: " + pr.text);
                    pr = br.getNextPart();
                }

                if(creditMap.containsKey(key) && title){
                    creditMap.get(key).add(tempAct);
                    System.out.println(key + " | " + tempAct + "  dcontains");
                    key = "";
                    title = false;
                    //year = false;

                }else if(!creditMap.containsKey(key) && title){
                    HashSet creditSet = new HashSet();
                    System.out.println(key + " | " + tempAct + " !contains");
                    creditSet.add(tempAct);
                    creditMap.put(key, creditSet);
                    title = false;
                    //year = false;
                    key = "";
                }

            }
            System.out.println(creditMap.get("Coal"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
