package com.bdz.procservice.graph;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Node wrapping object to be used for building directed graph.
 *
 * @param <T> the id object type of the nodes.
 * @param <V> the wrapped content (object) type of the nodes.
 */
@Data
@Builder
public class Node<T, V> {
    private final T id;
    private final List<T> neighbors;
    private final V content;
}
