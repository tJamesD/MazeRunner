package maze;

import java.util.Random;

public class mazeBoard {

    int[][] mazeBoardArray;
    Random rand;

    public mazeBoard(int row, int col) {
        this.mazeBoardArray = new int[row][col];
        rand = new Random();
        fillArrayOneZero();
    }

    public void fillArrayOneZero() {
        for(int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols<mazeBoardArray[rows].length; cols++) {
                mazeBoardArray[rows][cols] = rand.nextInt(2);
            }

        }
    }

    public void printMazeBoardArray() {
        for(int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols<mazeBoardArray[rows].length; cols++) {
                System.out.print(mazeBoardArray[rows][cols]);
            }
            System.out.println();
        }
    }
    public int[][] getMazeBoardArray() {
        return mazeBoardArray;
    }



}
