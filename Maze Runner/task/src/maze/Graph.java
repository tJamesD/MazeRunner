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
    ArrayList<Edge> usedEdgesArray;
    ArrayList<Edge> availableEdgesArray;

    public Graph() {
        adjVertices = new LinkedHashMap<>();
        allEdgesArray = new ArrayList<>();
        spanningTreeVertexArray = new ArrayList<>();
        spanningTreeEdgeArray = new ArrayList<>();
        usedEdgesArray = new ArrayList<>();
        availableEdgesArray = new ArrayList<>();

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
            populateAvailableEdgesArray(tempList);


            v.setVisted();
            System.out.println( "ACTIVE VERTEX1 " + v.getLabel());
            System.out.println("v vist check " + v.getIsVisted());
            spanningTreeVertexArray.add(v);

            Edge minEdge = pickMinOrRandomEdge(availableEdgesArray,rand);


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
            if(e.getWeight()< minEdge.getWeight()&&e.getAvailable()) {
                minEdge = e;
            }
            else {
                int randomNumber = rand.nextInt(tempList.size());
                minEdge = tempList.get(randomNumber);
            }
            e.setAvailable();
            minEdge.setAvailable();
        }
        return minEdge;
    }

    public Edge pickMinOrRandomEdge2(Random rand) {

        Edge minEdge = new Edge(new Vertex(-1,-1), new Vertex(-1,-1), -1);


            if(availableEdgesArray.size()>0) {
                minEdge = availableEdgesArray.get(0);
            }


        for(Edge e: availableEdgesArray) {
            if(e.getWeight()< minEdge.getWeight()) {
                minEdge = e;
            }
            else {
                int randomNumber = rand.nextInt(availableEdgesArray.size());
                minEdge = availableEdgesArray.get(randomNumber);
            }
            //e.setAvailable();
            //e.setVisted();

        }
        minEdge.setAvailable();
        minEdge.setVisted();
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
            if((!e.getIsVisted()) && (!e.getDest().getIsVisted())){
                unChosenList.add(e);
            }
        }

        for(Edge e : unChosenList) {
            System.out.println(e.getLabel());
        }
        System.out.println();

        return unChosenList;
    }

    public void populateAvailableEdgesArray(ArrayList<Edge> tempList) {
        for(Edge e : tempList) {
            //String label = e.getLabel();
            if((!availableEdgesArray.contains(e)) && (e.getAvailable())) {
                availableEdgesArray.add(e);
            }
        }
        for(Edge e: availableEdgesArray) {
            //System.out.println("AEA " + e.getLabel());
        }
    }

    public void findMatchEdge(String label) {
        for(Vertex v : adjVertices.keySet()) {
            ArrayList<Edge> tempList = adjVertices.get(v);

            for(Edge e: tempList) {
                if(e.getLabel().equals(label)) {
                    e.setVisted();
                    e.setAvailable();
                }
            }

        }
    }

    public void findMatchVertex(String label) {
        for(Vertex v : adjVertices.keySet()) {
            if(v.getLabel().equals(label)) {
                v.setVisted();

            }

            ArrayList<Edge> tempList = adjVertices.get(v);
            for(Edge e: tempList) {
                if(label.equals(e.getDest().getLabel())) {
                    e.getDest().setVisted();
                }
                if(label.equals(e.getSrc().getLabel())) {
                    e.getSrc().setVisted();
                }
                if(e.getSrc().getIsVisted() && e.getDest().getIsVisted()) {
                    //e.setVisted();
                    e.setAvailable();
                }
            }
            adjVertices.put(v,tempList);

        }
    }

    public void addAvailableEdges(Vertex v ) {
        ArrayList<Edge> tempEdges = adjVertices.get(v);





        for (Edge e: tempEdges) {
            if(availableEdgesArray.size()==0) {
                for(Edge e3: tempEdges) {
                    if(e3.getAvailable()) {
                        availableEdgesArray.add(e);
                    }
                }
                continue;
            }
            System.out.println("EDGE E " + e.getLabel());
            ListIterator<Edge> iter = availableEdgesArray.listIterator();
            while(iter.hasNext()) {
                Edge nextEdge = iter.next();
                //Edge nextEdge2 = new Edge(nextEdge.getSrc(), nextEdge.getDest(), nextEdge.getWeight());
                //iter.previous();
                System.out.println("ITER EDGE " + nextEdge.getLabel());
                if(nextEdge.getLabel().equals(e.getLabel())) {
                    //iter.next();
                    continue;
                }
                else if(nextEdge.getAvailable()) {
                    iter.add(nextEdge);
                    //iter.next();
                    System.out.println("Added edge " + nextEdge.getLabel());
                }
            }
            /*
            for(Edge e2: availableEdgesArray) {
                System.out.println("EDGE E2 " + e2.getLabel());
                if((e2.getLabel().equals(e.getLabel()))) {
                    continue;
                }
                else if(e.getAvailable()) {
                    availableEdgesArray.add(e);
                    System.out.println("ADDED EDGE " + e.getLabel());
                }
            }

             */
            /*
            if(e.getAvailable()) {
                availableEdgesArray.add(e);
                System.out.println("ADDED EDGE " + e.getLabel());
            }

             */
        }
    }

    public void setIfEdgeIsAvailable(Edge e) {
        if (e.getSrc().getIsVisted() && e.getDest().getIsVisted()) {
            e.setAvailable();
        }
    }

    public void createSpanningTree2() {
        ArrayList<Vertex> treeArray = new ArrayList<>();
        Random rand = new Random();

        int row = 0;
        int col = 0;

        Vertex v = new Vertex(row, col);


        System.out.println("ACTIVE VERTEX " + v.getLabel());

        while(!allVisted()) {
            treeArray.add(v);
            populateAvailableEdgesArray(adjVertices.get(v));
            //addAvailableEdges(v);
            removeInvalidEdges();
            Edge nextEdge = pickMinOrRandomEdge2(rand);
           // marks edges as visited and unavailable
            System.out.println("CHOSEN EDGE " + nextEdge.getLabel());
            findMatchEdge(nextEdge.getLabel());

            if(nextEdge.getDest().getIsVisted()) {
                v = nextEdge.getSrc();

            }
            else {
                v = nextEdge.getDest();

            }
            System.out.println("ACTIVE VERTEX " + v.getLabel());
        }
    }

    public void createSpanningTree3() {
        int row = 0;
        int col = 0;

        Vertex v = new Vertex(row, col);

        while(!allVisted()) {
            System.out.println("V TO BE ADDED " + v.getLabel());
            spanningTreeVertexArray.add(v);
            findMatchVertex(v.getLabel());//marks vertex as visited and unavailable.
            updateVertexInsideOfHashMapArray(v.getLabel());
            //showUnavailableVertexes();
            searchHashMapForNewlyUnavailableEdges();//updates hashmap.

            ArrayList<Edge> tempList = adjVertices.get(v);

            for (Edge e : tempList) {
                if (!e.getIsVisted()) {
                    e.setAvailableTrue();
                } else {
                    e.setVisted();
                }

            }

            searchHashMapForNewlyUnavailableEdges();

            Edge minEdge = minEdge3();
            System.out.println("CHOSEN EDGE " + minEdge.getLabel());
            findMatchEdge(minEdge.getLabel());// marks edge as visited and unavailable.
            v = returnAvailableVertexFromEdge(minEdge);
            updateVertexInsideOfHashMapArray(v.getLabel());
            System.out.println("V CHOSEN FROM METHOD " + v.getLabel());
            System.out.println("-----------------------");

            searchHashMapForNewlyUnavailableEdges();
        }

    }

    public Vertex returnAvailableVertexFromEdge(Edge e) {
        if(!e.getDest().getIsVisted()) {
            System.out.println(e.getDest().getIsVisted() + "-IS VISTED OF DEST");
            System.out.println("DESTINATION VERTEX PICKED");
            return e.getDest();
        }
        else {
            System.out.println("SRC VERTEX PICKED");
            return e.getSrc();
        }
    }

    public Edge minEdge3() {

        ArrayList<Edge> currentAvailableEdges = new ArrayList<>();

        for(Vertex v : adjVertices.keySet()) {
            ArrayList<Edge> tempList = adjVertices.get(v);
            for(Edge e: tempList) {
                if(e.getAvailable()) {
                    currentAvailableEdges.add(e);
                }
            }
        }

        Edge minEdge = new Edge(new Vertex(-1, -1), new Vertex(-2, -2), 99);
        for(Edge e: currentAvailableEdges) {
            if(e.getWeight()<minEdge.getWeight()) {
                minEdge = e;
            }
        }
        return minEdge;
    }

    public void removeInvalidEdges() {

        Iterator iterator = availableEdgesArray.iterator();

        while(iterator.hasNext()) {
            Edge e = (Edge) iterator.next();
            if ((!e.getAvailable()) || (e.getSrc().getIsVisted() && e.getDest().getIsVisted())) {
                System.out.println("REMOVED EDGE" + e.getLabel());
                iterator.remove();
            }
        }
    }


    public ArrayList<Vertex> getSpanningTreeVertexArray() {
        return spanningTreeVertexArray;
    }

    public void searchHashMapForNewlyUnavailableEdges() {
        for(Vertex V: adjVertices.keySet()) {
            ArrayList<Edge> tempList = new ArrayList<>();

            for (Edge e : tempList) {
                if (e.getSrc().getIsVisted() && e.getDest().getIsVisted()) {
                    e.setAvailable();
                }

            }
            //adjVertices.put(V, tempList);
        }
    }

    public void showUnavailableVertexes() {
        for(Vertex v: adjVertices.keySet()) {
            if( v.getIsVisted()) {
                System.out.println("sUV " + v.getLabel());
            }
        }
    }

    public void updateVertexInsideOfHashMapArray(String label) {
        for(Vertex v: adjVertices.keySet()) {
            ArrayList<Edge> tempList = adjVertices.get(v);

            for(Edge e: tempList) {
                if(label.equals(e.getSrc().getLabel())) {
                    e.getSrc().setVisted();

                }
                if(label.equals(e.getDest().getLabel())) {
                    e.getDest().setVisted();
                }

            }
            adjVertices.put(v,tempList);

        }
    }

    public void showUnselectedEdges() {
        for(Vertex v : adjVertices.keySet()) {
            ArrayList<Edge> tempList = adjVertices.get(v);

            for(Edge e: tempList) {
                if(!e.getIsVisted()) {
                    System.out.println(e.getLabel());
                }
            }
        }
    }
    public void showSelectedEdges() {
        for(Vertex v : adjVertices.keySet()) {
            ArrayList<Edge> tempList = adjVertices.get(v);

            for(Edge e: tempList) {
                if(e.getIsVisted()) {
                    System.out.println("SELECTED " + e.getLabel());
                }
            }
        }
    }

    public LinkedHashMap<Vertex,ArrayList<Edge>> getAdjVerticesHashMap() {
        return adjVertices;
    }
}
