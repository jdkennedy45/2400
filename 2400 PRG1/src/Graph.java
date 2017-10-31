import java.lang.reflect.Array;
import java.util.*;
import java.io.*;


public class Graph {

    private int max_vertices;
    private double[][] AdjM;
    private ArrayList<LinkedList<Integer>> AdjL;
    private ArrayList<String> vertices;
    private LinkedList<Integer> DFSlist;
    private LinkedList<Integer> BFSlist;
    private Queue<Integer> bfsqueue;

    private int count = 0;

    public Graph(int max_num_vertices) {
        max_vertices = max_num_vertices;
        AdjM = new double[max_vertices][max_vertices];
        AdjL = new ArrayList<>(max_num_vertices);
        for (int i = 0; i < max_vertices; i++) {
            AdjL.add(new LinkedList<>());
        }
        vertices = new ArrayList<String>();
        DFSlist = new LinkedList<Integer>();
        BFSlist = new LinkedList<Integer>();
        bfsqueue = new LinkedList<Integer>();
    }

    public void addVertex(String item) //allow items (items are identified by String search keys) to be stored in the Graph vertices
    {
        vertices.add(item);
    }

    public void addEdge(String start_vertex_key, String end_vertex_key, double edge_weight) //add a directed edge between two vertices
    {
        int start_key_index = vertices.indexOf(start_vertex_key);
        int end_key_index = vertices.indexOf(end_vertex_key);

        //System.out.println(start_key_index);
        //System.out.println(end_key_index);
        AdjM[start_key_index][end_key_index] = AdjM[end_key_index][start_key_index] = edge_weight;
        AdjL.get(start_key_index).add(end_key_index);
    }

    public static void printAdjacentMatrix(double matrix[][]) {
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }


    public void dfs() {
        //printAdjacentMatrix(AdjM);
        //System.out.println("\n");

        boolean checked[] = new boolean[max_vertices];
        for (int v = 0; v < max_vertices; v++) {
            if (!checked[v]) {
                dfs(v, checked);
            }
        }
    }

    public void dfs(int v, boolean checked[]) {
        DFSlist.add(v);
        checked[v] = true;

        for (int w = 0; w < max_vertices; w++) {
            if ((AdjM[v][w] > 0) && (w != v)) {
                if (!checked[w]) {
                    dfs(w, checked);
                }
            }
        }

    }

    public void displayDFS() {
        //DFSlist.toArray();

        for (int i = 0; i < max_vertices; i++) {
            int index = (Integer) DFSlist.get(i);
            System.out.println(vertices.get(index));
        }
    }

    public void bfs() //add items to linked list
    {
        //printAdjacentMatrix(AdjM);
        //System.out.println("\n");
        //count = 0;

        boolean checked[] = new boolean[max_vertices];
        for (int v = 0; v < max_vertices; v++) {
            if (!checked[v]) {
                bfs(v, checked);
            }
        }
    }

    public void bfs(int v, boolean checked[]) {
        bfsqueue.add(v);
        //System.out.println(v);
        checked[v] = true;

        while (bfsqueue.size() > 0) {
            v = bfsqueue.remove();
            BFSlist.add(v);  // adding to linked list for easy printing
            //System.out.println(v);

            for (int w = 0; w < AdjL.get(v).size(); w++) {
                int next = AdjL.get(v).get(w);

                if (!checked[next]) {
                    checked[next] = true;
                    bfsqueue.add(next);
                }
            }
        }
    }

    public void displayBFS() {

        for (int i = 0; i < max_vertices; i++) {
            int index = (Integer) BFSlist.get(i);
            System.out.println(vertices.get(index));
        }
    }

}