import java.io.*;
import java.util.Scanner;

/**
 * Created by DONK on 2/16/2017.
 */

public class Driver {


    public static void main(String[] args) throws IOException {
        //RETRIEVING NUMBER OF VERTICES FROM FILE
        File distances2 = new File(args[0]);

        int count = 0; //GOING TO PASS AS NUM_VERTICES WHEN MAKING GRAPH
        try {
            Scanner scanner = new Scanner(distances2);
            while (scanner.hasNext()) {
                String nextline = scanner.nextLine();
                count++;
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        //System.out.println(count);
        String thisLine = null;

        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        Graph graph = new Graph(count); //FROM EARLIER LINE COUNT

        while ((thisLine = bufferReader.readLine()) != null)
        {
            graph.addVertex(thisLine.trim());
        }

        //FileReader fileReader2 = new FileReader(args[1]);
        //BufferedReader bufferReader2 = new BufferedReader(fileReader2);

        String start_vertex_key = null;
        String end_vertex_key = null;
        String edge_weight_string = null;
        double edge_weight = 0;
        File distances = new File(args[1]);

        try {
            Scanner scanner = new Scanner(distances);
            while (scanner.hasNext()) {
                start_vertex_key = scanner.next();
                //System.out.println(start_vertex_key);
                end_vertex_key = scanner.next();
                //System.out.println(end_vertex_key);
                edge_weight_string = scanner.next();
                edge_weight = Double.parseDouble(edge_weight_string);
                //System.out.println(edge_weight);
                graph.addEdge(start_vertex_key, end_vertex_key, edge_weight);
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }



        //PRINT THE DFS AND BFS
        System.out.println("\nBFS:");
        graph.bfs();
        graph.displayBFS();

        System.out.print("\nDFS:\n");
        graph.dfs();
        graph.displayDFS();



    }
}
