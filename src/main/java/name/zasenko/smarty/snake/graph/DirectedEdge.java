package name.zasenko.smarty.snake.graph;

public class DirectedEdge {
    final int v;
    final int w;
    final double weight;

    DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getSource() {
        return this.v;
    }

    public int getDestination() {
        return this.w;
    }

    @Override
    public String toString() {
        return this.getSource() + " -> " + this.getDestination() + " " + String.format("%.2f", this.getWeight());
    }
}
