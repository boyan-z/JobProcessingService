package com.bdz.procservice.graph;

import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * Data type representing graph with list of {@link Node}.
 *
 * @param <T> type of the nodes id.
 * @param <V> type of the nodes content.
 */
@Data
public class Graph<T, V> {
    private final List<Node<T, V>> nodes;

    /**
     * Finds node by id in the graph.
     *
     * @param searchId the id to be searched.
     * @return the node found.
     */
    public Optional<Node<T, V>> findNode(T searchId) {
        return this.getNodes().stream()
                .filter(node -> node.getId().equals(searchId))
                .findAny();
    }
}
