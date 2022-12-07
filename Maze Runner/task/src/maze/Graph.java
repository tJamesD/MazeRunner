package maze;

import java.util.*;

public class Graph {
    int vertices;
    //LinkedList<Edge>[] adjacencylist;
    //Vertex[][] vertexArray = new Vertex[100][100];
    private Map<Vertex, ArrayList<Edge>> adjVertices;
    public Graph() {
        adjVertices = new LinkedHashMap<>();
        //this.vertices = vertices;
        //adjacencylist = new LinkedList[vertices];
        //for(int i = 0; i<vertices;i++) {
          //  adjacencylist[i] = new LinkedList<>();
        //}
    }

    public void addVertex(int row, int col) {
        adjVertices.putIfAbsent(new Vertex(row,col), new ArrayList<Edge>());
    }

    public void removeVertex(String label) {
        //Vertex v= new Vertex(label);
        //adjVertices.values().stream().forEach(e -> e.remove(v));
        //adjVertices.remove(new Vertex(label));
    }

    public void addEdge(Edge edge) {
        Vertex v1 = edge.getSrc();
        Vertex v2 = edge.getDest();
        System.out.print("V1 " + v1);
        ArrayList<Edge> tempList = adjVertices.get(v1);
        tempList.add(edge);
        //adjVertices.get(v1).add(edge);
        //adjVertices.get(v2).add(v1);
    }

    public void printMap() {
        for(Vertex v : adjVertices.keySet() ) {
            System.out.println(v.getLabel());
        }
    }
    public void printValues() {
        for(Vertex v:adjVertices.keySet()) {
            System.out.println(adjVertices.get(v));
        }
    }
}
