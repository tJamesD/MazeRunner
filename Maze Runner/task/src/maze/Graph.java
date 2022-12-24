package maze;

import java.sql.Array;
import java.util.*;

public class Graph {
    int vertices;
    //LinkedList<Edge>[] adjacencylist;
    //Vertex[][] vertexArray = new Vertex[100][100];
    private LinkedHashMap<Vertex, ArrayList<Edge>> adjVertices;
    ArrayList<Vertex> spanningTreeVertexArray;
    ArrayList<Edge> spanningTreeEdgeArray;

    ArrayList<Edge> allEdgesArray;

    public Graph() {
        adjVertices = new LinkedHashMap<>();
        allEdgesArray = new ArrayList<>();
        spanningTreeVertexArray = new ArrayList<>();
        spanningTreeEdgeArray = new ArrayList<>();
        //this.vertices = vertices;
        //adjacencylist = new LinkedList[vertices];
        //for(int i = 0; i<vertices;i++) {
          //  adjacencylist[i] = new LinkedList<>();
        //}
    }

    public void addVertex(Vertex vertex) {
        adjVertices.putIfAbsent(vertex, new ArrayList<>());
    }

    public ArrayList<Edge> getValues(Vertex v1) {
        return adjVertices.get(v1);
    }

    public void removeVertex(String label) {
        //Vertex v= new Vertex(label);
        //adjVertices.values().stream().forEach(e -> e.remove(v));
        //adjVertices.remove(new Vertex(label));
    }

    public void populateAllEdgesArray(Edge edge) {
        Edge tempEdge = new Edge(edge.src,edge.dest, edge.weight);
        allEdgesArray.add(tempEdge);
    }
    public Edge checkForExistingEdge(Edge edge) {
        for(Edge e : allEdgesArray) {
            if ((e.getLabel().equals(edge.dest.getLabel() + edge.src.getLabel()))|| e.getLabel().equals(edge.getLabel())) {
               return e;
            }
        }
        return edge;
    }
    public Edge checkForSwappedEdge(Edge edge) {
        for(Edge e : allEdgesArray) {
            if (e.getLabel().equals(edge.dest.getLabel() + edge.src.getLabel())) {
                return e;
            }
        }
        return edge;
    }

    public boolean returnBooleanCheckForSwappedEdge(Edge edge) {
        for(Edge e : allEdgesArray) {
            if (e.getLabel().equals(edge.dest.getLabel() + edge.src.getLabel())) {
                return true;
            }
        }
        return false;
    }
    public Edge checkForExistingEdgeTwoEdges(Edge edge1, Edge edge2) {
        for(Edge e : allEdgesArray) {
            if ((e.getLabel().equals(edge1.getLabel()))|| e.getLabel().equals(edge2.getLabel())) {
                return e;
            }
        }
        return edge2;
    }

    public boolean returnBooleanForExistingEdge(Edge edge) {
        for(Edge e : allEdgesArray) {
            if (e.getLabel().equals(edge.getLabel())) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(Edge edge, boolean swappedEdge) {

        Vertex v1;

        if(swappedEdge) {
            v1 = edge.getDest();
        }
        else {
            v1 = edge.getSrc();
        }

        ArrayList<Edge> tempList = new ArrayList<>();

        tempList = adjVertices.get(v1);



        tempList.add(edge);

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
            System.out.print("MAIN VErTEX "+v.getLabel() + " ");
            for(Edge e:tempArray) {
                System.out.print(e.getLabel() + " ");
            }
        }
        System.out.println();
    }

    public LinkedHashMap<Vertex, ArrayList<Edge>> getAdjVertices() {
        return adjVertices;
    }

    public ArrayList<Edge> getEdgeArray() {
        return allEdgesArray;
    }

    public ArrayList<Vertex> createSpanningTree() {
        ArrayList<Vertex> treeArray = new ArrayList<>();
        Random rand = new Random();

        int row = 0;
        int col = 0;

        Vertex v = new Vertex(row, col);

        System.out.println("ACTIVE VERTEX " + v.getLabel());

        while(!allVisted()) {


            ArrayList<Edge> tempList = adjVertices.get(v);

            v.setVisted();
            System.out.println( "ACTIVE VERTEX1 " + v.getLabel());
            System.out.println("v vist check " + v.getIsVisted());
            spanningTreeVertexArray.add(v);

            Edge minEdge = pickMinOrRandomEdge(tempList,rand);

            System.out.println("EDGEPICKED " + minEdge.getLabel() );

            minEdge.setVisted();

            v.setVisted();
            v = pickNewVertex(minEdge);
            v.setVisted();

            System.out.println( "ACTIVE VERTEX2 " + v.getLabel());
            System.out.println("v vist check2 " + v.getIsVisted());

        }







        return treeArray;
    }

    public Vertex pickNewVertex(Edge edge) {
        Vertex srcVertex = edge.getSrc();
        Vertex destVertex = edge.getDest();

        if(destVertex.getIsVisted()) {
            return srcVertex;
        }
        return destVertex;
    }

    public Edge pickMinOrRandomEdge(ArrayList<Edge> tempList, Random rand) {
        ArrayList<Edge> unChosenList = returnOnlyUnchosenEdges(tempList);


            Edge minEdge = unChosenList.get(0);


        for(Edge e: unChosenList) {
            if(e.getWeight()< minEdge.getWeight()) {
                minEdge = e;
            }
            else {
                int randomNumber = rand.nextInt(tempList.size());
                minEdge = tempList.get(randomNumber);
            }
        }
        return minEdge;
    }

    public boolean allVisted() {
        for(Vertex v : adjVertices.keySet()) {
            if(!v.getIsVisted()) {
                return false;
            }

        }
        return true;
    }

    public ArrayList<Edge> returnOnlyUnchosenEdges(ArrayList<Edge> tempList) {
        ArrayList<Edge> unChosenList = new ArrayList<>();

        for(Edge e : tempList) {
            if(!e.getIsVisted()){
                unChosenList.add(e);
            }
        }

        for(Edge e : unChosenList) {
            System.out.println(e.getLabel());
        }
        System.out.println();

        return unChosenList;
    }

    public ArrayList<Vertex> getSpanningTreeVertexArray() {
        return spanningTreeVertexArray;
    }
}
