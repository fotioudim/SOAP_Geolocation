package com.dfotiou.api.soap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class LocationKDTree {
    private static final int K = 3; // 3-d tree
    private final Node tree;

    public LocationKDTree(@NonNull final List<Point> points) {
        final List<Node> nodes = new ArrayList<>(points.size());
        for (final Point point : points) {
            nodes.add(new Node(point));
        }
        tree = buildTree(nodes, 0);
    }

    @Nullable
    public Point findNearest(final double latitude, final double longitude) {
        final Node node = findNearest(tree, new Node(latitude, longitude), 0);
        return node == null ? null : node.point;
    }

    private static Node findNearest(final Node current, final Node target, final int depth) {
        final int axis = depth % K;
        final int direction = getComparator(axis).compare(target, current);
        final Node next = (direction < 0) ? current.left : current.right;
        final Node other = (direction < 0) ? current.right : current.left;
        Node best = (next == null) ? current : findNearest(next, target, depth + 1);
        if (current.euclideanDistance(target) < best.euclideanDistance(target)) {
            best = current;
        }
        if (other != null) {
            if (current.verticalDistance(target, axis) < best.euclideanDistance(target)) {
                final Node possibleBest = findNearest(other, target, depth + 1);
                if (possibleBest.euclideanDistance(target) < best.euclideanDistance(target)) {
                    best = possibleBest;
                }
            }
        }
        return best;
    }

    @Nullable
    private static Node buildTree(final List<Node> items, final int depth) {
        if (items.isEmpty()) {
            return null;
        }

        Collections.sort(items, getComparator(depth % K));
        final int index = items.size() / 2;
        final Node root = items.get(index);
        root.left = buildTree(items.subList(0, index), depth + 1);
        root.right = buildTree(items.subList(index + 1, items.size()), depth + 1);
        return root;
    }

    private static class Node {
        Node left;
        Node right;
        Point point;
        final double[] p = new double[K];

        Node(final double latitude, final double longitude) {
            p[0] = (double) (Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(longitude)));
            p[1] = (double) (Math.cos(Math.toRadians(latitude)) * Math.sin(Math.toRadians(longitude)));
            p[2] = (double) (Math.sin(Math.toRadians(latitude)));
        }

        Node(final Point point) {
            this(point.getLatitude(), point.getLongitude());
            this.point = point;
        }

        double euclideanDistance(final Node that) {
            final double x = this.p[0] - that.p[0];
            final double y = this.p[1] - that.p[1];
            final double z = this.p[2] - that.p[2];
            return x * x + y * y + z * z;
        }

        double verticalDistance(final Node that, final int axis) {
            final double d = this.p[axis] - that.p[axis];
            return d * d;
        }
    }

    private static Comparator<Node> getComparator(final int i) {
        return NodeComparator.values()[i];
    }

    private static enum NodeComparator implements Comparator<Node> {
        x {
            @Override
            public int compare(final Node a, final Node b) {
                return Double.compare(a.p[0], b.p[0]);
            }
        },
        y {
            @Override
            public int compare(final Node a, final Node b) {
                return Double.compare(a.p[1], b.p[1]);
            }
        },
        z {
            @Override
            public int compare(final Node a, final Node b) {
                return Double.compare(a.p[2], b.p[2]);
            }
        }
    }
}
