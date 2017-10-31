import java.io.*;
import java.util.Scanner;

/**
 * Created by DONK on 2/16/2017.
 */
public class Driver {
    public static void main(String[] args) throws IOException {
        //RETRIEVING NUMBER OF VERTICES FROM FILE
        File vertices_list = new File(args[0]);

        int verts_count = 0; //GOING TO PASS AS NUM_VERTICES WHEN MAKING GRAPH
        try {
            Scanner scanner = new Scanner(vertices_list);
            while (scanner.hasNext()) {
                String nextline = scanner.nextLine();
                verts_count++;
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }


        //RETRIEVING NUMBER OF EDGES
        File distances = new File(args[1]);
        int edges_count = 0;
        Scanner tempscanner = new Scanner(distances);
        while (tempscanner.hasNextLine()) {
            String next = tempscanner.nextLine();
            edges_count++;
        }

        System.out.println("Verts count: " + verts_count + " Edges count: " + edges_count);

        String thisLine = null;
        FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        PrimsGraph graph = new PrimsGraph(verts_count, edges_count); //FROM EARLIER LINE COUNT
        KruskalsGraph graph2 = new KruskalsGraph(verts_count, edges_count);

        DijkstraGraph dijkstra = new DijkstraGraph();
        dijkstra.setNumEdgesVerts(verts_count, edges_count);

        //READS VERTEXES IN TO NEW GRAPHS
        while ((thisLine = bufferReader.readLine()) != null)
        {
            graph.addVertex(thisLine.trim());
            graph2.addVertex(thisLine.trim());
            dijkstra.addVertex(thisLine.trim());
        }

        String start_vertex_key = null;
        String end_vertex_key = null;
        String edge_weight_string = null;
        double edge_weight = 0;

        //READS EDGES IN AND ADDS THEM TO GRAPHS
        int count1 = 0;
        try {
            Scanner scanner = new Scanner(distances);
            while (scanner.hasNext()) {
                start_vertex_key = scanner.next();
                end_vertex_key = scanner.next();
                edge_weight_string = scanner.next();
                edge_weight = Double.parseDouble(edge_weight_string);
                graph.addEdge(start_vertex_key, end_vertex_key, edge_weight, count1);
                graph2.addEdge(start_vertex_key, end_vertex_key, edge_weight, count1);
                dijkstra.addEdgeToList(start_vertex_key, end_vertex_key, (int) edge_weight, count1);
                count1++;
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }


        //PRINT THE PRIM AND KRUSKAL
        System.out.println("\nPRIM:");
        graph.makeGraph();
        graph.prim();
        graph.displayPrim();

        System.out.print("\nKRUSKAL:\n");
        graph2.displayKruskal();


        //If 3rd and 4th argument weren't provided, don't attempt to perform dijkstra's.
        if (args.length < 4) {
            System.out.println("\nNo arguments given for shortest path algorithm. Program finished.");
            System.exit(0);
        }

        try {
            System.out.print("\nDIJKSTRA:\n");
            dijkstra.finish(args[2], args[3]);
        } catch (IllegalArgumentException iae) {
            System.out.println("No arguments given for shortest path.");
        }
    }
}
