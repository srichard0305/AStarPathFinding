package io.steve.comp452;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AStarAlgo {

    ArrayList<Node> open;
    ArrayList<Node> closed;
    Node [][] nodes;
    int [][] costGraph;
    Node startNode, endNode;
    final int ROW = 16, COL = 16;

    AStarAlgo(int [][] graph, Node start, Node end ){
        costGraph = graph;
        startNode = start;
        endNode = end;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        nodes = new Node[ROW][COL];
        for(int x =0; x < ROW; x++){
            for(int y = 0; y < COL; y++){
                nodes[x][y] = new Node(x,y);
                nodes[x][y].setCostSoFar(Integer.MAX_VALUE);
                nodes[x][y].setEstimatedCost(nodes[x][y].getCostSoFar() +  nodes[x][y].getHeuristic());
            }
        }
    }

    public ArrayList<Node> findPath(){

        // check if start node is blocked
        int blocked = 0;
        ArrayList<Node> nodeNeighbours = getConnections(startNode);
        for(Node node: nodeNeighbours){
            if(costGraph[node.getX()][node.getY()] > 4)
                blocked++;
        }

        if(blocked == 4)
            return new ArrayList<>();

        //check if end node is blocked
        blocked = 0;
        nodeNeighbours = getConnections(endNode);
        for(Node node: nodeNeighbours){
            if(costGraph[node.getX()][node.getY()] > 4)
                blocked++;
        }

        if(blocked == 4)
            return new ArrayList<>();

        //set heuristic from start node to end node, cost so far, and estimated cost
        startNode.setHeuristic(heuristic(startNode, endNode));
        startNode.setCostSoFar(0);
        startNode.setEstimatedCost(startNode.getCostSoFar() + startNode.getHeuristic());

        // add start node to open list
        open.add(startNode);

        while(!open.isEmpty()){

            //get smallest node in open list based on estimated cost
            Node currentNode = smallestNode(open);

            //if goal node return
            if(currentNode.compare(endNode))
                return calculatePath(currentNode);

            // add current node to closed and remove from open
            open.remove(currentNode);
            closed.add(currentNode);

            //get connecting nodes
            ArrayList<Node> connections;
            connections = getConnections(currentNode);

            for(Node neighbour : connections){

                // calculate new cost
                float tempCost = currentNode.getCostSoFar() + costGraph[neighbour.getX()][neighbour.getY()];

                //if node is in closed list continue
                if(closed.contains(neighbour))
                    continue;

                // if temp cost is less then the cost of the node set up node
                if(tempCost < neighbour.getCostSoFar()){
                    neighbour.setNodeFrom(currentNode);
                    neighbour.setCostSoFar(tempCost);
                    neighbour.setHeuristic( heuristic(neighbour, endNode));
                    neighbour.setEstimatedCost(neighbour.getCostSoFar() + neighbour.getHeuristic());
                    if(!open.contains(neighbour))
                        open.add(neighbour);
                }
            }
        }

        return new ArrayList<>();
    }

    private ArrayList<Node> calculatePath(Node node){

        ArrayList<Node> path = new ArrayList<>();
        path.add(node);
        Node currentNode = node;
        while(currentNode.getNodeFrom() != null){
            path.add(currentNode.getNodeFrom());
            currentNode = currentNode.getNodeFrom();
        }
        ArrayList<Node> reversePath = new ArrayList<>();
        while(!path.isEmpty()){
            reversePath.add(path.remove(path.size()-1));
            path.trimToSize();
        }

        return reversePath;
    }

   private float heuristic(Node start, Node end){
       float x = (float)Math.pow(end.getX() - start.getX(), 2.0);
       float y = (float)Math.pow(end.getY() - start.getY(), 2.0);

       return (float) Math.sqrt(x + y);
   }

   private Node smallestNode(ArrayList<Node> open){

        Node current = open.get(0);
        for(int i = 1; i < open.size()-1; i++){
            if(current.getEstimatedCost() > open.get(i).getEstimatedCost())
                current = open.get(i);
        }
        return current;
    }

    public ArrayList<Node> getConnections(Node node){

        ArrayList<Node> connections = new ArrayList<>();

        //check left node
        if(node.getX() - 1 >= 0 )
            connections.add(nodes[node.getX()-1][node.getY()]);

        //check right node
        if(node.getX() + 1 < ROW)
            connections.add(nodes[node.getX()+1][node.getY()]);

        //check node down
        if(node.getY() - 1 >= 0)
            connections.add(nodes[node.getX()][node.getY()-1]);

        //check node up
        if(node.getY() +1 < COL)
            connections.add(nodes[node.getX()][node.getY()+1]);

        return connections;
    }

}
