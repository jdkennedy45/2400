import java.util.ArrayList;

/**
 * Created by DONK on 3/17/2017.
 */

class DijkstraGraph {

    static int num_edges = 0;
    AdjList arr[];
    static int num_verts = 0;
    private ArrayList<String> vertices = new ArrayList<String>();
    int[][] AdjM = null;

    public void setNumEdgesVerts(int verts, int edges)
    {
        this.num_verts = verts;
        num_edges = edges;
        AdjM = new int[num_edges][3];
    }

    public void addVertex(String item) //allow items (items are identified by String search keys) to be stored in the Graph vertices
    {
        vertices.add(item);
    }

    public void addEdgeToList(String start, String end, int weight, int i){
        int start_key_index = vertices.indexOf(start);
        int end_key_index = vertices.indexOf(end);

        AdjM[i][0] = start_key_index;
        AdjM[i][1] = end_key_index;
        AdjM[i][2] = weight;
    }

    public void finish(String s, String e){

        int start = vertices.indexOf(s);
        int end  = vertices.indexOf(e);


        DijkstraGraph DijkstraGraph = new DijkstraGraph();
        DijkstraGraph g = new DijkstraGraph();
        g=DijkstraGraph.makeGraph();


        for (int i = 0; i < num_edges; i++) {
            //System.out.println(AdjM[i][0] + " " + AdjM[i][1]);
            DijkstraGraph.addEdge(g, AdjM[i][0], AdjM[i][1], AdjM[i][2]);
        }

        DijkstraGraph.dijkstra(g, end, start, s, e);
    }

    DijkstraGraph makeGraph()
    {
        DijkstraGraph DijkstraGraph = new DijkstraGraph();

        DijkstraGraph.arr = new AdjList[num_verts];
        for(int i = 0; i < DijkstraGraph.arr.length; i++) {
            DijkstraGraph.arr[i] = new AdjList(); //array of adj lists
        }
        for (int i = 0; i < num_verts; i++) {
            DijkstraGraph.arr[i].head = null;
        }

        return DijkstraGraph;
    }

    void addEdge(DijkstraGraph DijkstraGraph, int src, int goal, int weight)
    {
        //adds a directed edge
        AdjListItem newNode = newAdjListItem(goal, weight);
        newNode.next = DijkstraGraph.arr[src].head;
        DijkstraGraph.arr[src].head = newNode;
    }


    void displayShortest(int dist[], int start, String s, String e)
    {
        System.out.print("(" + s + " traveling to " + e + ")\n");
        System.out.print("Shortest path cost: " + dist[start]+"\n");
    }

    void dijkstra(DijkstraGraph DijkstraGraph, int end, int src, String s, String e)
    {
        int dist[] = new int[num_verts];
        MinHeap minHeap = makeMinHeap(num_verts);

        for (int v = 0; v < num_verts; ++v)
        {
            dist[v] = (int)Double.POSITIVE_INFINITY;
            minHeap.arr[v] = newMinHeapItem(v, dist[v]);
            minHeap.position[v] = v;
        }

        minHeap.arr[src] = newMinHeapItem(src, dist[src]);
        minHeap.position[src] = src;
        dist[src] = 0; //set itself to 0
        decreaseKey(minHeap, src, dist[src]);

        minHeap.size = num_verts;

        try {
            while (!isEmpty(minHeap)) {
                MinHeapItem minHeapNode = extractMin(minHeap);
                int i = minHeapNode.v;
                AdjListItem adj_head = DijkstraGraph.arr[i].head;
                while (adj_head != null) {
                    int goal_index = adj_head.goal;
                    if (isInMinHeap(minHeap, goal_index) && dist[i] != (int) Double.POSITIVE_INFINITY && adj_head.weight + dist[i] < dist[goal_index]) {
                        dist[goal_index] = dist[i] + adj_head.weight;
                        decreaseKey(minHeap, goal_index, dist[goal_index]);
                    }
                    adj_head = adj_head.next;
                }
            }
        }catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        displayShortest(dist, end, s, e);
    }

    class MinHeapItem
    {
        int  v, dist;
        public MinHeapItem(){
        }
    }

    class MinHeap
    {
        int size;
        int capacity;
        int position[];
        MinHeapItem arr[];
    }

    MinHeapItem newMinHeapItem(int v, int dist)
    {
        MinHeapItem minHeapNode = new MinHeapItem();

        minHeapNode.v = v;
        minHeapNode.dist = dist;
        return minHeapNode;
    }

    MinHeap makeMinHeap(int capacity)
    {
        MinHeap minHeap = new MinHeap();

        minHeap.position = new int[capacity];
        minHeap.size = 0;
        minHeap.capacity = capacity;
        minHeap.arr = new MinHeapItem[capacity];
        return minHeap;
    }

    void swapMinHeapItem(MinHeapItem a,  MinHeapItem b)
    {
        MinHeapItem temp = new MinHeapItem(); //make temp node to perform swap
        temp.dist = a.dist;
        temp.v = a.v;
        a.dist = b.dist;
        a.v = b.v;
        b.dist = temp.dist;
        b.v = temp.v;
    }


    void minHeapify(MinHeap minHeap, int index)
    {
        int smallest, left, right;
        left = 2 * index + 1;
        right = 2 * index + 2;
        smallest = index;

        if (left < minHeap.size && minHeap.arr[left].dist < minHeap.arr[smallest].dist )
            smallest = left;

        if (right < minHeap.size && minHeap.arr[right].dist < minHeap.arr[smallest].dist )
            smallest = right;

        if (smallest != index)
        {
            MinHeapItem smallestNode = minHeap.arr[smallest];
            MinHeapItem indexNode = minHeap.arr[index];
            minHeap.position[smallestNode.v] = index;
            minHeap.position[indexNode.v] = smallest;
            swapMinHeapItem(minHeap.arr[smallest], minHeap.arr[index]);
            minHeapify(minHeap, smallest);
        }
    }

    boolean isEmpty(MinHeap minHeap)
    {
        return (minHeap.size == 0);
    }


    MinHeapItem extractMin(MinHeap minHeap)
    {
        if (isEmpty(minHeap)) return null;

        MinHeapItem root = minHeap.arr[0];
        MinHeapItem lastNode = minHeap.arr[minHeap.size - 1];
        minHeap.arr[0] = lastNode;
        minHeap.position[root.v] = minHeap.size-1;
        minHeap.position[lastNode.v] = 0;
        minHeap.size--;
        minHeapify(minHeap, 0);

        return root;
    }

    void decreaseKey(MinHeap minHeap, int v, int dist)
    {
        int i = minHeap.position[v];
        minHeap.arr[i].dist = dist;

        while ((i!=0) && minHeap.arr[i].dist < minHeap.arr[(i - 1) / 2].dist)
        {
            minHeap.position[minHeap.arr[i].v] = (i-1)/2;
            minHeap.position[minHeap.arr[(i-1)/2].v] = i;
            swapMinHeapItem(minHeap.arr[i],  minHeap.arr[(i - 1) / 2]);
            i = (i - 1)/2;
        }
    }


    boolean isInMinHeap(MinHeap minHeap, int v)
    {
        return (minHeap.position[v] < minHeap.size);
    }

    class  AdjListItem
    {
        int goal, weight;
        AdjListItem next;

        public AdjListItem(){
        }
    }

    AdjListItem newAdjListItem(int goal, int weight)
    {
        AdjListItem newNode = new AdjListItem();
        newNode.goal = goal;
        newNode.weight = weight;
        newNode.next = null;
        return newNode;
    }


    class AdjList
    {
        AdjListItem head;

        public AdjList(){
        }
    }
}