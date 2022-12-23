package maze;

import java.util.ArrayList;
import java.util.Arrays;
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

        graph.createSpanningTree();
        ArrayList<Vertex> tempList1 = graph.getSpanningTreeVertexArray();

        for(Vertex v : tempList1) {
            System.out.println(v.getLabel());
        }

        //graph.printMap();
        //graph.printValues();
        ArrayList<Edge> tempArray = graph.getEdgeArray();

        for (Edge e : tempArray) {
            //System.out.println(e.getLabel());
        }

        graph.createSpanningTree();

        //System.out.println(maze.createUnChangeableWallArray().toString());

        //String[] temp = maze.setSurroundingCellArray(0,0);

        //for(int i = 0; i <temp.length; i++) {
        //  System.out.println(temp[i]);
        //}


        //System.out.println(maze.getHorizontalMaze());
        //System.out.println(maze.getVerticalMaze());

    }
}
