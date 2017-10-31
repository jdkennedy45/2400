/**
 * Created by DONK on 4/17/2017.
 */
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * Created by DONK on 3/14/2017.
 */

public class KruskalsGraph {
    private static int max_vertices;
    private static int num_edges;
    private ArrayList<String> vertices;
    private boolean[] checked;
    private int gcount;
    Graph2 maingraph;
    Graph2 mintree;

    Edge[] edges = null;


    public KruskalsGraph(int max_num_vertices, int max_num_edges) {
        num_edges = max_num_edges;
        max_vertices = max_num_vertices;
        vertices = new ArrayList<String>();
        edges = new Edge[num_edges];
        checked = new boolean[num_edges];
        checked[0] = true;
        maingraph = new Graph2(num_edges);
        mintree = new Graph2(num_edges);
        gcount = 0;
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
        maingraph.edge[count] = new Edge(start_key_index, end_key_index, edge_weight);

        if (count == num_edges - 1)
        {
            Kruskal(maingraph);
        }
    }

    public class Edge implements Comparable<Edge>
    {
        int start;
        int end;
        double weight;

        Edge(int s, int e, double weight)
        {
            start = s;
            end = e;
            this.weight = weight;
        }

        public int compareTo(Edge edge) {
            return toString().compareTo(edge.toString());
        }
    }

    public class Parent
    {
        int[] par;
        Parent(int vert)
        {
            par = new int[vert];
            for(int i = 0; i < vert; i++) {
                par[i] = -1;
            }
        }
    }

    public int Find(Parent par, int vert)
    {
        if(par.par[vert] == -1)
        {
            return vert;
        }
        else {
            return Find(par, par.par[vert]);
        }
    }

    public void Kruskal(Graph2 firstgraph)
    {
        try {
            Arrays.sort(firstgraph.edge, new Comparator<Edge>() {
                public int compare(Edge edge1, Edge edge2) {
                    return Double.compare(edge1.weight, edge2.weight);
                }
            });

            Parent parent = new Parent(max_vertices);
            for (int i = 0; i < firstgraph.edge.length; i++) {
                if (gcount == max_vertices - 1) {
                    break;
                } else {

                    //quick union attempt
                    if (Find(parent, firstgraph.edge[i].start) != Find(parent, firstgraph.edge[i].end)) {
                        if (parent.par[firstgraph.edge[i].start] == -1) {
                            parent.par[firstgraph.edge[i].start] = firstgraph.edge[i].end;
                            mintree.edge[gcount] = new Edge(firstgraph.edge[i].start, firstgraph.edge[i].end, firstgraph.edge[i].weight);
                        } else {
                            parent.par[firstgraph.edge[i].end] = firstgraph.edge[i].start;
                            mintree.edge[gcount] = new Edge(firstgraph.edge[i].start, firstgraph.edge[i].end, firstgraph.edge[i].weight);
                        }
                        gcount++;
                    }
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public class Graph2
    {
        Edge[] edge;
        Graph2(int e) {
            edge = new Edge[e];
        }
    }

    public void displayKruskal()
    {
        for (int i = 0; i < gcount; i++) {
            System.out.println("(" + vertices.get(mintree.edge[i].start) + ", " + vertices.get(mintree.edge[i].end) + ", " + (int) mintree.edge[i].weight + ")");
        }

    }
}
