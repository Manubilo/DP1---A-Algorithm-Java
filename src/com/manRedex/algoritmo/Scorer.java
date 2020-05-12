package com.manRedex.algoritmo;

import com.manRedex.algoritmo.GraphNode;

public interface Scorer <T extends GraphNode> {
    double computeCost(T from, T to);
}
