package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        mazeBoard maze = new mazeBoard(10, 10);
        Graph graph = new Graph();
        Random rand = new Random();
        System.out.println("+++++++++++++++++++");
        maze.printMazeBoardArrayOneZero();
        maze.printMazeBoardArrayWalls();

        int[][] tempMazeBoard = maze.getMazeBoardArray();
        //loop to populate adjacency list.
        for (int rows = 0; rows < tempMazeBoard.length; rows++) {
            for (int cols = 0; cols < tempMazeBoard[rows].length; cols++) {
                //graph.printMap();
                //graph.printValues();
                Vertex srcVertex = new Vertex(rows, cols);
                graph.addVertex(srcVertex);
                String[] initialCellArray = maze.setSurroundingCellArray(rows, cols);
                String[] validCellArray = maze.createValidSurroundingCellArrayForGraph(initialCellArray);
                //System.out.println(Arrays.toString(validCellArray));
                for (int i = 0; i < validCellArray.length; i++) {
                    if ((!validCellArray[i].equals("-1")) && (!validCellArray[i].equals("-2"))) {
                        int vertexRow = Character.getNumericValue(validCellArray[i].charAt(0));
                        int vertexCol = Character.getNumericValue(validCellArray[i].charAt(1));
                        int weight = rand.nextInt(3) + 1;

                        Vertex destVertex = new Vertex(vertexRow, vertexCol);
                        graph.addVertex(destVertex);

                        Edge tempEdge = new Edge(srcVertex, destVertex, weight);

                        boolean swappedEdge = graph.returnBooleanCheckForSwappedEdge(tempEdge);

                        if (swappedEdge) {

                            tempEdge = graph.checkForExistingEdge(tempEdge);


                            graph.addEdge(tempEdge, swappedEdge);

                        } else {


                            graph.addEdge(tempEdge, swappedEdge);

                            graph.populateAllEdgesArray(tempEdge);
                        }

                    }
                }
            }
        }



        //graph.printMap();
        graph.printValues();
        //graph.createSpanningTree2();
        graph.createSpanningTree3();
        ArrayList<Vertex> treeArray = graph.getSpanningTreeVertexArray();

        for(Vertex v : treeArray) {
            System.out.println(v.getLabel());
        }
        graph.showUnselectedEdges();
        graph.showSelectedEdges();
        /*
        graph.findMatchEdge("0001");

        LinkedHashMap<Vertex,ArrayList<Edge>> tempList = graph.getAdjVerticesHashMap();
        Vertex tempVertex1 = new Vertex(0,0);
        Vertex tempVertex2 = new Vertex(0,1);
        ArrayList<Edge> tempVertexList = tempList.get(tempVertex1);
        ArrayList<Edge> tempVertexList2 = tempList.get(tempVertex2);

        Edge tempEdge = new Edge(tempVertex1,tempVertex2, 0);
        Edge tempEdge2 = new Edge(tempVertex1,tempVertex2, 0);

        for(Edge e: tempVertexList) {
            if (e.getLabel().equals("0001")) {
                System.out.println(e.getAvailable());
                System.out.println(e.getIsVisted());
            }
        }

        for(Edge e: tempVertexList2) {
            if(e.getLabel().equals("0001")) {
                System.out.println(e.getAvailable());
                System.out.println(e.getIsVisted());
            }
        }
        */
        //tempEdge.setAvailable();
        //tempEdge.setVisted();

        //System.out.println(tempEdge2.getAvailable());
        //System.out.println(tempEdge2.getIsVisted());
        //System.out.println(tempEdge.getAvailable());
        //System.out.println(tempEdge.getIsVisted());








    }
}
