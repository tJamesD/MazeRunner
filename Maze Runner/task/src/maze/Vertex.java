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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return row == vertex.row && col == vertex.col && label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, row, col);
    }
}
