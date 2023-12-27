import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point shouldn't be null");
        root = recursiveInsert(root, p, true);
    }

    private Node recursiveInsert(Node node, Point2D point, boolean xDimNode) {
        if (node == null) return new Node(point, xDimNode);
        if (point.equals(node.point)) return node;

        if (needGoLeft(node, point)) {
            node.left = recursiveInsert(node.left, point, !xDimNode);
        }
        else {
            node.right = recursiveInsert(node.right, point, !xDimNode);
        }

        updateSize(node);
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point shouldn't be null");

        Node curr = root;

        while (curr != null) {
            if (curr.point.equals(p)) return true;
            curr = (needGoLeft(curr, p))
                   ? curr.left
                   : curr.right;
        }

        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return (root != null) ? root.size : 0;
    }

    public void draw() {
        recursiveDraw(root);
    }

    private void recursiveDraw(Node node) {
        if (node != null) {
            node.point.draw();
            recursiveDraw(node.left);
            recursiveDraw(node.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle shouldn't be null");
        List<Point2D> list = new ArrayList<>();
        range(list, root, rect);
        return list;
    }

    private void range(List<Point2D> list, Node node, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.point)) list.add(node.point);

        if (node.xDimNode) {
            if (rect.xmin() <= node.point.x()) {
                range(list, node.left, rect);
            }
            if (rect.xmax() >= node.point.x()) {
                range(list, node.right, rect);
            }
        }
        else {
            if (rect.ymin() <= node.point.y()) {
                range(list, node.left, rect);
            }
            if (rect.ymax() >= node.point.y()) {
                range(list, node.right, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point shouldn't be null");
        return recursiveNearest(root, p, null);
    }

    private Point2D recursiveNearest(Node node, Point2D p, Point2D nearest) {
        if (node == null) return nearest;
        if (p.equals(node.point)) return p;

        if (nearest == null || node.point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
            nearest = node.point;
        }

        if (needGoLeft(node, p)) {
            nearest = recursiveNearest(node.left, p, nearest);
            if (nearest.distanceSquaredTo(p) > Math.pow(pToLineDistance(node, p), 2)) {
                nearest = recursiveNearest(node.right, p, nearest);
            }
        }
        else {
            nearest = recursiveNearest(node.right, p, nearest);
            if (nearest.distanceSquaredTo(p) > Math.pow(pToLineDistance(node, p), 2)) {
                nearest = recursiveNearest(node.left, p, nearest);
            }
        }

        return nearest;
    }

    private boolean needGoLeft(Node node, Point2D p) {
        return node.xDimNode && p.x() < node.point.x()
                || !node.xDimNode && p.y() < node.point.y();
    }

    private double pToLineDistance(Node node, Point2D p) {
        return (node.xDimNode)
               ? Math.abs(p.x() - node.point.x())
               : Math.abs(p.y() - node.point.y());
    }

    private void updateSize(Node node) {
        int leftSize = (node.left != null) ? node.left.size : 0;
        int rightSize = (node.right != null) ? node.right.size : 0;
        node.size = leftSize + rightSize + 1;
    }

    private static class Node {
        Point2D point;
        boolean xDimNode;
        int size;
        Node left;
        Node right;

        Node(Point2D point, boolean xDimNode) {
            this.point = point;
            this.xDimNode = xDimNode;
            this.size = 1;
        }
    }

}
