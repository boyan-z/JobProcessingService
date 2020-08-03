package com.bdz.procservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Topological sort utility class.
 */
public class TopologicalSort {

    /**
     * Could topologically sort elements which could be represented as directed graph.
     *
     * @param elements            list with the elements that could be sorted.
     * @param idTranslator        function that translate element to id.
     * @param neighborsTranslator function that translate element to neighbors.
     * @param <T>                 the element id type.
     * @param <V>                 the element type.
     * @return List with topologically sorted elements.
     */
    public static <T, V> List<V> topologicalSort(final List<V> elements, final Function<V, T> idTranslator, final Function<V, List<T>> neighborsTranslator) {

        // Check if empty.
        if (elements == null || elements.isEmpty()) {
            throw new IllegalArgumentException("Elements can't be empty!");
        }

        // Translate list of elements to list of nodes.
        List<Node<T, V>> nodes = elements.stream()
                .map(element -> {
                    Node<T, V> node = new Node(idTranslator.apply(element), neighborsTranslator.apply(element), element);
                    return node;
                })
                .collect(Collectors.toList());

        // Create graph with nodes representing elements.
        var graph = new Graph<T, V>(nodes);
        return topologicalSort(graph).stream()
                .map(Node::getContent)
                .collect(Collectors.toList());
    }

    /**
     * Could topologically sort elements which could be represented as directed graph.
     *
     * @param graph to be topologically sorted.
     * @param <T>   the element id type.
     * @param <V>   the element type.
     * @return List with topologically sorted nodes.
     */
    public static <T, V> List<Node<T, V>> topologicalSort(final Graph<T, V> graph) {
        // List to store the topological order
        List<Node<T, V>> order = new ArrayList<>();

        // Map which indicates if a node is visited (has been processed by the algorithm)
        Map<T, Boolean> visited = graph.getNodes().stream()
                .collect(Collectors.toMap(Node::getId, node -> Boolean.FALSE));

        // Go through not visited nodes using trace function.
        for (var node : graph.getNodes()) {
            if (!visited.get(node.getId()))
                topologicalTrace(graph, node.getId(), visited, order);
        }

        // Return the list of the sorted objects.
        return order;
    }

    private static <T, V> void topologicalTrace(final Graph<T, V> graph, final T nodeId, final Map<T, Boolean> visited, final List<Node<T, V>> order) {

        // Current node is visited.
        visited.replace(nodeId, true);

        // Find the node or throw error if not existing in graph!
        var node = graph.findNode(nodeId).orElseThrow();

        // Continue with the recursion till root node.
        for (var neighborId : node.getNeighbors()) {
            if (!visited.get(neighborId))
                topologicalTrace(graph, neighborId, visited, order);
        }

        // Put the current node in the array
        order.add(node);
    }
}
