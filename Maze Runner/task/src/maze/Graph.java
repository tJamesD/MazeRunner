package maze;

import java.util.*;

public class Graph {
    int vertices;
    //LinkedList<Edge>[] adjacencylist;
    //Vertex[][] vertexArray = new Vertex[100][100];
    private LinkedHashMap<Vertex, ArrayList<Edge>> adjVertices;

    ArrayList<Edge> allEdgesArray;

    public Graph() {
        adjVertices = new LinkedHashMap<>();
        allEdgesArray = new ArrayList<>();
        //this.vertices = vertices;
        //adjacencylist = new LinkedList[vertices];
        //for(int i = 0; i<vertices;i++) {
          //  adjacencylist[i] = new LinkedList<>();
        //}
    }

    public void addVertex(Vertex vertex) {
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public void removeVertex(String label) {
        //Vertex v= new Vertex(label);
        //adjVertices.values().stream().forEach(e -> e.remove(v));
        //adjVertices.remove(new Vertex(label));
    }

    public void populateAllEdgesArray(Edge edge) {
        //Edge tempEdge = checkForExistingEdge(edge);
        allEdgesArray.add(edge);
    }
    public Edge checkForExistingEdge(Edge edge) {
        for(Edge e : allEdgesArray) {
            if (e.getLabel().equals(edge.dest.getLabel() + edge.src.getLabel())) {
               return e;
            }
        }
        return edge;
    }

    public void addEdge(Edge edge) {
        Vertex v1 = edge.getSrc();
        Vertex v2 = edge.getDest();
        //System.out.print("V1 " + v1);
        ArrayList<Edge> tempList = new ArrayList<>();
        //tempList.add(edge);
        tempList = adjVertices.get(v1);
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
            ArrayList<Edge> tempArray = adjVertices.get(v);
            System.out.println();
            for(Edge e:tempArray) {
                System.out.print(e.getLabel() + " ");
            }
        }
    }
}
