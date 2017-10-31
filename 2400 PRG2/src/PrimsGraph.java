import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * Created by DONK on 3/14/2017.
 */

public class PrimsGraph {

    static class Edge {
        int start;
        int end;
        double weight;

        Edge(int s, int e, double w) {
            start = s;
            end = e;
            weight = w;
        }
    }

    private static int max_vertices;
    private static int num_edges;
    private double[][] AdjM;
    private ArrayList<LinkedList<Integer>> AdjL;
    private ArrayList<String> vertices;
    private ArrayList<ArrayList<Edge>> graph;
    private ArrayList<Edge> mintree;
    private boolean[] checked;

    Edge[] edges = null;

    public PrimsGraph(int max_num_vertices, int max_num_edges) {
        num_edges = max_num_edges;
        max_vertices = max_num_vertices;
        AdjM = new double[max_vertices][max_vertices];
        AdjL = new ArrayList<>(max_num_vertices);
        for (int i = 0; i < max_vertices; i++) {
            AdjL.add(new LinkedList<>());
        }
        vertices = new ArrayList<String>();
        edges = new Edge[num_edges];
        graph = new ArrayList<>();
        mintree = new ArrayList<>();
        checked = new boolean[num_edges];
        checked[0] = true;
    }


    public void addVertex(String item) //allow items (items are identified by String search keys) to be stored in the Graph vertices
    {
        vertices.add(item);
    }

    public void addEdge(String start_vertex_key, String end_vertex_key, double edge_weight, int count) //add a directed edge between two vertices
    {
        int start_key_index = vertices.indexOf(start_vertex_key);
        int end_key_index = vertices.indexOf(end_vertex_key);

        //System.out.println("count being passed to edges: " + count);
        AdjM[start_key_index][end_key_index] = edge_weight; //directed graph
        AdjL.get(start_key_index).add(end_key_index);
        edges[count] = new Edge(start_key_index, end_key_index, edge_weight);
    }

    public void makeGraph()
    {
        int size = edges.length;

        for (int i=0; i<size; i++)
        {
            graph.add(new ArrayList<>());
        }

        for (Edge edge : edges)
        {
            Edge temp = new Edge(edge.start, edge.end, edge.weight);
            graph.get(edge.start).add(edge);
            graph.get(edge.end).add(temp);
        }
    }

    /*public static void printAdjacentMatrix(double matrix[][]) {
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix) {
                System.out.print(anAMatrix + " ");
            }
            System.out.println();
        }
    }
    */
    public void prim()
    {
        try {
            PriorityQueue<Edge> pq = new PriorityQueue<>((Object edge1, Object edge2) -> {
                Edge first, next;
                first = (Edge) edge1;
                next = (Edge) edge2;

                if (first.weight < next.weight)
                {
                    return -1;
                }
                else if (first.weight > next.weight)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            });

            ArrayList<Edge> start = graph.get(0);
            for (Edge e : start)
            {
                pq.add(e);
            }
            while (!pq.isEmpty())
            {
                Edge edge = pq.peek();
                pq.poll();
                if (checked[edge.start] && checked[edge.end]) {
                    continue;
                }
                checked[edge.start] = true;

                ArrayList<Edge> end = graph.get(edge.end);
                for (Edge e : end)
                {
                    if (!checked[edge.end]) {
                        pq.add(e);
                    }
                }
                checked[edge.end] = true;
                mintree.add(edge);
            }
        } catch (NullPointerException npe) {
            System.out.println("Nothing found in graph.");
            System.out.println(npe.getCause());
        }

    }

    public void displayPrim() {
        for(Edge edge : mintree){
            System.out.println("(" + vertices.get(edge.start)+", "+vertices.get(edge.end)+", "+ (int) edge.weight+")");
        }
    }

}