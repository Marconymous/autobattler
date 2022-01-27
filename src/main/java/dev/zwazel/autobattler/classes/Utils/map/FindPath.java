package dev.zwazel.autobattler.classes.Utils.map;

import dev.zwazel.autobattler.classes.Utils.Vector;

import java.util.HashSet;
import java.util.PriorityQueue;

public class FindPath {
    PriorityQueue<Node> openList = new PriorityQueue<>(1, new NodeComparator());
    HashSet<Node> closedList = new HashSet<>();

    public Node findPath(Vector start, Vector end, GridGraph grid) {
        Node node = grid.getNodes()[start.getX()][start.getY()];
        node.setCost(0);
        openList.add(node);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            if (currentNode.getMyGridCell().getPosition().equals(end)) {
                return currentNode;
            }
            closedList.add(currentNode);
            expandNode(currentNode, end);
        }

        return null;
    }

    private void expandNode(Node currentNode, Vector end) {
        for (Node successor : currentNode.getMyNeighbors()) {
            if (closedList.contains(successor) || successor.getMyGridCell().getCurrentObstacle() != null) {
                continue;
            }

            double tentativeCost = currentNode.getCost() + (currentNode.getMyGridCell().getPosition().getDistanceFrom(successor.getMyGridCell().getPosition()));

            if (openList.contains(successor) && tentativeCost >= successor.getCost()) {
                continue;
            }

            successor.setPredecessor(currentNode);
            successor.setCost(tentativeCost + end.getDistanceFrom(successor.getMyGridCell().getPosition()));

            if (!openList.contains(successor)) {
                openList.add(successor);
            }
        }
    }

    public Node getNextMoveSteps(Vector start, Vector vectorToGo, Grid grid, int moveCount) {
        FindPath findPath = new FindPath();
        Node node = findPath.findPath(start, vectorToGo, new GridGraph(grid));

        if (node == null) {
            vectorToGo = findClosestNearbyNode(grid, start, vectorToGo);
            if (vectorToGo != null) {
                node = findPath.findPath(start, vectorToGo, new GridGraph(grid));
            }
        }

        if (node != null) {
            System.out.println("hierarchy full:");
            printNodeHierarchy(node);

            int amountPredecessor = node.countHowManyPredecessors();
            if (amountPredecessor > moveCount) {
                for (int i = amountPredecessor; i > moveCount; i--) {
                    node = node.getPredecessor();
                }
            }

            System.out.println("\nhierarchy with " + moveCount + " moves:");
            printNodeHierarchy(node);
        }

        return node;
    }

    public Vector findClosestNearbyNode(Grid grid, Vector start, Vector end) {
        GridGraph graph = new GridGraph(grid);
        Node targetNode = graph.getNodes()[end.getX()][end.getY()];

        for (Node node : targetNode.getMyNeighbors()) {
            node.setCost(node.getMyGridCell().getPosition().getDistanceFrom(start));
        }

        PriorityQueue<Node> neighbors = new PriorityQueue<>(targetNode.getMyNeighbors().size(), new NodeComparator());
        neighbors.addAll(targetNode.getMyNeighbors());
        for (Node node : neighbors) {
            Vector vector = node.getMyGridCell().getPosition();
            if (isReachable(start, vector, grid)) {
                return vector;
            }
        }
        return null;
    }

    public boolean isReachable(Vector start, Vector end, Grid grid) {
        FindPath path = new FindPath();
        return (path.findPath(start, end, new GridGraph(grid)) != null);
    }

    private void printNodeHierarchy(Node node) {
        int amountPredecessor = node.countHowManyPredecessors();
        int counter = 0;
        for (int i = amountPredecessor; i >= 0; i--) {
            System.out.print("\n"+"\t".repeat(Math.max(0, counter++)) + node.getMyGridCell().getPosition());
            node = node.getPredecessor();
        }
    }
}
