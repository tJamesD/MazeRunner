package maze;

import java.util.Objects;
import java.util.Random;

public class Vertex {
    String label;
    int row;
    int col;

    //int weight;

    public Vertex(int row, int col) {
       // Random rand = new Random();
        this.row = row;
        this.col = col;
        this.label = Integer.toString(row) + Integer.toString(col);
        //this.weight = rand.nextInt(3)+1;

    }
    public String getLabel() {
        return label;
    }

    public int hashCode() {
        return Objects.hash(row,col,label);
    }
}
