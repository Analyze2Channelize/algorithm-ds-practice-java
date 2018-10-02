package edu.saurabh.graphs;

import java.util.Iterator;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DirectedEulerianCycleFinder {
    private Stack<Integer> cycle = null;  // Eulerian cycle; null if no such cylce

    /**
     * Computes an Eulerian cycle in the specified digraph, if one exists.
     * 
     * @param G the digraph
     */
    public DirectedEulerianCycleFinder(AdjacencyListDigraph G) {

        // must have at least one edge
        if (G.E() == 0) return;

        // necessary condition: indegree(v) = outdegree(v) for each vertex v
        // (without this check, DFS might return a path instead of a cycle)
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) != G.indegree(v))
                return;

        // create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // initialize stack with any non-isolated vertex
        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);

        // greedily add to putative cycle, depth-first search style
        cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            // add vertex with no more leaving edges to cycle
            cycle.push(v);
        }

        // check if all edges have been used
        // (in case there are two or more vertex-disjoint Eulerian cycles)
        if (cycle.size() != G.E() + 1)
            cycle = null;

        assert certifySolution(G);
    }

    /**
     * Returns the sequence of vertices on an Eulerian cycle.
     * 
     * @return the sequence of vertices on an Eulerian cycle;
     *         {@code null} if no such cycle
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Returns true if the digraph has an Eulerian cycle.
     * 
     * @return {@code true} if the digraph has an Eulerian cycle;
     *         {@code false} otherwise
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex(AdjacencyListDigraph G) {
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) > 0)
                return v;
        return -1;
    }


    /**************************************************************************
     *
     *  The code below is solely for testing correctness of the data type.
     *
     **************************************************************************/

    // Determines whether a digraph has an Eulerian cycle using necessary
    // and sufficient conditions (without computing the cycle itself):
    //    - at least one edge
    //    - indegree(v) = outdegree(v) for every vertex
    //    - the graph is connected, when viewed as an undirected graph
    //      (ignoring isolated vertices)
    private static boolean satisfiesNecessaryAndSufficientConditions(AdjacencyListDigraph G) {

        // Condition 0: at least 1 edge
        if (G.E() == 0) return false;

        // Condition 1: indegree(v) == outdegree(v) for every vertex
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) != G.indegree(v))
                return false;

        // Condition 2: graph is connected, ignoring isolated vertices
        Graph H = new Graph(G.V());
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                H.addEdge(v, w);
        
        // check that all non-isolated vertices are conneted
        int s = nonIsolatedVertex(G);
        BreadthFirstPaths bfs = new BreadthFirstPaths(H, s);
        for (int v = 0; v < G.V(); v++)
            if (H.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;

        return true;
    }

    // check that solution is correct
    private boolean certifySolution(AdjacencyListDigraph G) {

        // internal consistency check
        if (hasEulerianCycle() == (cycle() == null)) return false;

        // hashEulerianCycle() returns correct value
        if (hasEulerianCycle() != satisfiesNecessaryAndSufficientConditions(G)) return false;

        // nothing else to check if no Eulerian cycle
        if (cycle == null) return true;

        // check that cycle() uses correct number of edges
        if (cycle.size() != G.E() + 1) return false;

        // check that cycle() is a directed cycle of G
        // TODO

        return true;
    }


    private static void unitTest(AdjacencyListDigraph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        DirectedEulerianCycleFinder euler = new DirectedEulerianCycleFinder(G);

        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }


    /**
     * Unit tests the {@code DirectedEulerianCycle} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int V = 5;
        int E = 10;

        // Eulerian cycle
        AdjacencyListDigraph G1 = DigraphGeneratorUtil.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        AdjacencyListDigraph G2 = DigraphGeneratorUtil.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // empty digraph
        AdjacencyListDigraph G3 = new AdjacencyListDigraph(V);
        unitTest(G3, "empty digraph");

        // self loop
        AdjacencyListDigraph G4 = new AdjacencyListDigraph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // union of two disjoint cycles
        AdjacencyListDigraph H1 = DigraphGeneratorUtil.eulerianCycle(V/2, E/2);
        AdjacencyListDigraph H2 = DigraphGeneratorUtil.eulerianCycle(V - V/2, E - E/2);
        int[] perm = new int[V];
        for (int i = 0; i < V; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        AdjacencyListDigraph G5 = new AdjacencyListDigraph(V);
        for (int v = 0; v < H1.V(); v++)
            for (int w : H1.adj(v))
                G5.addEdge(perm[v], perm[w]);
        for (int v = 0; v < H2.V(); v++)
            for (int w : H2.adj(v))
                G5.addEdge(perm[V/2 + v], perm[V/2 + w]);
        unitTest(G5, "Union of two disjoint cycles");

        // random digraph
        AdjacencyListDigraph G6 = DigraphGeneratorUtil.simple(V, E);
        unitTest(G6, "simple digraph");

    }

}

