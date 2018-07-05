package alda.graph;

import java.util.Scanner;

public class Kevin6Degree {
    private MyUndirectedGraph<String> graph = new MyUndirectedGraph<>();
    private GraphBuilder gb = new GraphBuilder();

    public static void main(String[] args) {
        Kevin6Degree k6d = new Kevin6Degree();
        k6d.go();
    }

    private void go() {
        System.out.println("Kevin Bacon Program");
        System.out.println(" -----Loading----- ");

        gb.buildGraph(graph); //Bygger graph
        gb = new GraphBuilder();                //Friar upp minne genom att sätta om referensen. Tar bort creditMap,
        // pga behöver bara för uppbyggning
        System.out.println(graph);              //Skriver ut antal noder och bågar
        menu();
    }

    private void menu() {
        boolean run = true;
        Scanner in = new Scanner(System.in);
        while (run) {
            System.out.println("Commands: ");
            System.out.println("N - Give an actors name and find its' Bacon-number");
            System.out.println("T - Give two actors names to find the Bacon-number between the two");
            System.out.println("X - Exit the program");
            System.out.println("Enter a command: ");

            switch (in.nextLine().toLowerCase()) {

                case "t":
                    System.out.println("Please enter the first actor's last name: ");
                    String lastNameOne = normalizeName(in.nextLine());
                    System.out.println("Please enter first actor's first name: ");
                    String firstNameOne = normalizeName(in.nextLine());
                    String actorNameOne = lastNameOne + ", " + firstNameOne;
                    System.out.println("Please enter the second actor's last name: ");
                    String lastNameTwo = normalizeName(in.nextLine());
                    System.out.println("Please enter the second actor's first name: ");
                    String firstNameTwo = normalizeName(in.nextLine());
                    String actorNameTwo = lastNameTwo + ", " + firstNameTwo;
                    findKevinBaconNumberBetweenTwo(actorNameOne, actorNameTwo);
                    break;

                case "n":
                    System.out.println("Please enter actor's last name: ");
                    String lastName = normalizeName(in.nextLine());

                    System.out.println("Please enter actor's first name: ");
                    String firstName = normalizeName(in.nextLine());

                    String actorName = lastName + ", " + firstName;
                    findKevinBaconNumber(actorName);
                    break;

                case "x":
                    System.out.println("Good bye!");
                    run = false;
                    break;

                default:
                    System.out.println("Incorrect command, please try again");
            }

        }
    }

    private String normalizeName(String name) {
        if (!name.isEmpty()) {
            name = name.trim();
            //name = name.toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1); //Stor bokstav i början av givet namn
        }
        return name;
    }

    private void findKevinBaconNumber(String actorName) {

        if (graph.contains(actorName)) {
            printBaconNumber(actorName, "Bacon, Kevin");
        } else {
            System.out.println("Did not find " + actorName);
        }
    }

    private void findKevinBaconNumberBetweenTwo(String firstActorName, String secondActorName) {
        if (!graph.contains(firstActorName)) {
            System.out.println("Did not find " + firstActorName + " in the actor list");
        } else if (!graph.contains(secondActorName)) {
            System.out.println("Did not find " + secondActorName + " in the actor list");
        } else {
            printBaconNumber(firstActorName, secondActorName);
        }
    }

    private void printBaconNumber(String firstActorName, String secondActorName) {
        int kevinBaconNumber = graph.breadthFirstSearch(firstActorName, secondActorName);
        if (kevinBaconNumber == -1) { //Exempel är Macaroni, Anna
            System.out.println("No link found between " + firstActorName + " and " + secondActorName + ", or search took too long");
        } else { //Hatley, Carla. Lawrence, Vicki
            System.out.println("Kevin Bacon-number for " + firstActorName + " to " + secondActorName + " is: " + kevinBaconNumber);
        }
    }
}
