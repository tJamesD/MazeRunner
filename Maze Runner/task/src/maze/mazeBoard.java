package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class mazeBoard {

    int[][] mazeBoardArray;
    ArrayList<String> unchangeableWalls;
    int baseRow;
    int baseCol;
    Random rand;
    boolean horizontalMaze;
    boolean verticalMaze;

    public mazeBoard(int row, int col) {
        this.mazeBoardArray = new int[row][col];
        rand = new Random();
        baseRow = row;
        baseCol = col;
        fillArrayOneZero();
        //fillArrayAllOnes();
    }

    public void fillArrayOneZero() {
        for (int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols < mazeBoardArray[rows].length; cols++) {
                if (rows == 0 || rows == mazeBoardArray.length-1 || cols == 0 || cols == mazeBoardArray.length-1) {
                    mazeBoardArray[rows][cols] = 1;
                } else {
                    mazeBoardArray[rows][cols] = rand.nextInt(2);
                }

            }

        }
    }

    public void scanArrayForOneCount() {
        for (int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols < mazeBoardArray[rows].length; cols++) {
                String[] surroundingCellArray = setSurroundingCellArray(rows, cols);
                //System.out.println("1" + Arrays.toString(surroundingCellArray));
                int oneCount = oneCountSurroundingCellArray(surroundingCellArray);
                while( oneCount > 2 ) {
                    for(int i = 0 ; i<surroundingCellArray.length;i++) {
                        String mergedNumber = Integer.toString(rows) + Integer.toString(cols);
                        if((!unchangeableWalls.contains(surroundingCellArray[i]) || (!unchangeableWalls.contains(mergedNumber)))) {
                            mazeBoardArray[rows][cols] =0;
                            surroundingCellArray = setSurroundingCellArray(rows, cols);
                            System.out.println("oneCOUNT " + oneCount);
                            oneCount = oneCountSurroundingCellArray(surroundingCellArray);
                            System.out.println("oneCOUNT2 " + oneCount);
                        }
                    }
                }
            }
        }

    }
    public void fillArrayAllOnes() {
        for (int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols < mazeBoardArray[rows].length; cols++) {
                    mazeBoardArray[rows][cols] = 1;
            }
        }
    }

    public void printMazeBoardArrayOneZero() {
        for (int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols < mazeBoardArray[rows].length; cols++) {
                System.out.print(mazeBoardArray[rows][cols]);

                //setSurroundingCellArray(rows, cols);
            }
            System.out.println();
        }
    }

    public void printMazeBoardArrayWalls() {
        for (int rows = 0; rows < mazeBoardArray.length; rows++) {
            for (int cols = 0; cols < mazeBoardArray[rows].length; cols++) {
                if (mazeBoardArray[rows][cols] == 0) {
                    System.out.print("  ");
                }
                if (mazeBoardArray[rows][cols] == 1) {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println();
        }
    }

    public void createEntranceExit() {
        int horizontalOrVerticalExit = rand.nextInt(2);
        int entrance = rand.nextInt(mazeBoardArray.length-2)+1;
        int exit = rand.nextInt(mazeBoardArray.length-2)+1;

        //1 = vertical maze 0 = horizontal maze
        if(horizontalOrVerticalExit == 0 ) {
            mazeBoardArray[entrance][0] = 0;
            mazeBoardArray[exit][mazeBoardArray.length-1] = 0;
            horizontalMaze = true;
        }
        else {
            mazeBoardArray[0][entrance] = 0;
            mazeBoardArray[mazeBoardArray.length-1][exit] = 0;
            verticalMaze = true;
        }

    }

    public ArrayList<String> createUnChangeableWallArray() {
        ArrayList<String> unchangeableArrays = new ArrayList<>();

        for(int row = 0; row<mazeBoardArray.length;row++){
            for (int col = 0; col<mazeBoardArray[row].length;col++) {
                if(row == 0|| row == mazeBoardArray.length-1 || col == 0 || col == mazeBoardArray[col].length-1){
                    String digits = String.valueOf(row) + String.valueOf(col);
                    unchangeableArrays.add(digits);
                }
            }
        }
        this.unchangeableWalls = unchangeableArrays;
        return unchangeableArrays;
    }
    public String[] setSurroundingCellArray(int row, int col) {
        String[] surroundingCellArray = new String[4];

        //String inputCell = String.valueOf(row)+String.valueOf(col);
        String topCell =  String.valueOf(row-1)+String.valueOf(col);
        String bottomCell =  String.valueOf(row+1)+String.valueOf(col);
        String leftCell =  String.valueOf(row)+String.valueOf(col-1);
        String rightCell =  String.valueOf(row)+String.valueOf(col+1);

        //String mergedNumberString = String.valueOf(row)+String.valueOf(col);
        //int mergedNumberInt = Integer.parseInt(mergedNumberString);

        surroundingCellArray[0] = topCell;
        surroundingCellArray[1] = rightCell ;
        surroundingCellArray[2] = bottomCell;
        surroundingCellArray[3] = leftCell;

        //System.out.println(mergedNumberString);
        //System.out.println("INPUT " + inputCell );
        //System.out.println("MERGED " +mergedNumberInt);
        //System.out.println(topCell);
        //System.out.println(rightCell);
        //System.out.println(bottomCell);
        //System.out.println(leftCell);

        return surroundingCellArray;
    }

    public String[] createValidSurroundingCellArray(String[] surroundingCellArray) {
        for(int i = 0; i<surroundingCellArray.length;i++) {
            if (surroundingCellArray[i].contains("-")) {

            }
        }
    }

    public int oneCountSurroundingCellArray(String[] surroundingCellArray) {
        int oneCount = 0;
        int row = 0;
        int col = 0;

        for(int i = 0;i<surroundingCellArray.length;i++ ) {
            //System.out.println("HERE " + surroundingCellArray[i]);
        }

        for(int i = 0; i<surroundingCellArray.length; i++) {
            //int row = surroundingCellArray[i].charAt(0);
            //int col = surroundingCellArray[i].charAt(1);
            if(surroundingCellArray[i].contains("-")) {
                continue;
            }
            else {
                row = Character.getNumericValue(surroundingCellArray[i].charAt(0));
                col = Character.getNumericValue(surroundingCellArray[i].charAt(1));
            }
            if(mazeBoardArray[row][col] == 1 ) {
                oneCount++;
            }
        }
        return oneCount;
    }

    public int[][] getMazeBoardArray() {
        return mazeBoardArray;
    }

    public boolean getHorizontalMaze() {
        return horizontalMaze;
    }

    public boolean getVerticalMaze() {
        return verticalMaze;
    }


}
