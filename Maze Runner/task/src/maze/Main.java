package maze;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        mazeBoard maze = new mazeBoard(10,10);
        maze.createEntranceExit();
        maze.printMazeBoardArrayOneZero();
        maze.printMazeBoardArrayWalls();
        maze.createUnChangeableWallArray();
        maze.scanArrayForOneCount();
        maze.printMazeBoardArrayOneZero();
        maze.printMazeBoardArrayWalls();
        System.out.println("HERE");
        //System.out.println(maze.createUnChangeableWallArray().toString());

        //String[] temp = maze.setSurroundingCellArray(0,0);

        //for(int i = 0; i <temp.length; i++) {
          //  System.out.println(temp[i]);
        //}


        //System.out.println(maze.getHorizontalMaze());
        //System.out.println(maze.getVerticalMaze());

    }
}
