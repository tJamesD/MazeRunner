package maze;

public class Edge {
    Vertex src, dest;
    int weight;
    String label;

    public Edge(Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
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
}
