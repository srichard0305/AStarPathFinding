package io.steve.comp452;

public class Node {

    private int x, y;
    private Node nodeFrom;
    private float heuristic;
    private float costSoFar;
    private float estimatedCost;

    Node(int x, int y){
        this.x = x;
        this.y =y;
        nodeFrom = null;
        costSoFar = 0;
        estimatedCost = 0;
        heuristic = 0;
    }

    public boolean compare(Node node){
        if(this.x == node.x && this.y == node.y)
            return true;
        else
            return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getNodeFrom() {
        return nodeFrom;
    }

    public float getHeuristic() {
        return heuristic;
    }

    public float getCostSoFar() {
        return costSoFar;
    }

    public float getEstimatedCost() {
        return estimatedCost;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNodeFrom(Node nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public void setHeuristic(float heuristic) {
        this.heuristic = heuristic;
    }

    public void setCostSoFar(float costSoFar) {
        this.costSoFar = costSoFar;
    }

    public void setEstimatedCost(float estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
