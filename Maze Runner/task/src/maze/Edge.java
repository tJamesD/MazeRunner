package maze;

import java.util.Objects;

public class Edge {
    Vertex src, dest;
    int weight;
    String label;

    boolean isVisted;

    public Edge(Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.isVisted = false;
        label = src.getLabel()+dest.getLabel();
    }

    public Vertex getSrc() {
        return src;
    }

    public Vertex getDest() {
        return dest;
    }

    public String getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }

    public void setVisted() {
        isVisted = true;
    }

    public boolean getIsVisted() {
        return isVisted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight && src.equals(edge.src) && dest.equals(edge.dest) && label.equals(edge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight, label);
    }
}
