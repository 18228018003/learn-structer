package com.mengtu.structer.graph;

import java.util.*;

public class ListGraph<V,E> extends Graph<V, E> {

    private Map<V,Vertex<V,E>> vertexMap = new HashMap<>();
    private Set<Edge<V,E>> edges = new HashSet<>();
    private Comparator<Edge<V,E>> edgeComparator = (e1,e2)-> 0;

    public ListGraph() {}
    public  ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    public void print(){
        vertexMap.forEach((k,v)->{
            System.out.println(k);
        });
        edges.forEach(System.out::println);
    }
    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertexMap.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertexMap.containsKey(v)) return;
        vertexMap.put(v,new Vertex<V,E>(v));

    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from,to,null);
    }

    @Override
    public void addEdge(V from, V to, E e) {
        Vertex<V,E> fromVertex = vertexMap.get(from);
        if (fromVertex == null){
            fromVertex = new Vertex<>(from);
            vertexMap.put(from,fromVertex);
        }
        Vertex<V,E> toVertex = vertexMap.get(to);
        if (toVertex == null){
            toVertex = new Vertex<>(to);
            vertexMap.put(to,toVertex);
        }
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = e;
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, E> vertex = vertexMap.remove(v);
        if (vertex == null) return;
        Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator();
        while (iterator.hasNext()) {
            Edge<V, E> next = iterator.next();
            next.to.inEdges.remove(next);
            iterator.remove();
            edges.remove(next);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertexMap.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertexMap.get(to);
        if (toVertex == null) return;

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)){
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    @Override
    public void bfs(V begin,VertexVisitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V,E> beginVertex = vertexMap.get(begin);
        if (beginVertex == null) return;
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        Set<Vertex<V,E>> set = new HashSet<>();
        queue.offer(beginVertex);
        set.add(beginVertex);
        while (!queue.isEmpty()){
            Vertex<V, E> vertex = queue.poll();
            if (visitor.visit(vertex.value)) return;
            for (Edge<V, E> edge : vertex.outEdges) {
                Vertex<V, E> to = edge.to;
                if (!set.contains(to)){
                    queue.offer(to);
                    set.add(to);
                }
            }
        }
    }
    @Override
    public void dfs(V begin,VertexVisitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V,E> beginVertex = vertexMap.get(begin);
        if (beginVertex == null) return;
        Stack<Vertex<V,E>> stack = new Stack<>();
        Set<Vertex<V,E>> set = new HashSet<>();
        stack.push(beginVertex);
        set.add(beginVertex);
        if (visitor.visit(begin)) return;
        while (!stack.isEmpty()){
            Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                Vertex<V, E> to = edge.to;
                if (!set.contains(to)){
                    stack.push(vertex);
                    stack.push(to);
                    set.add(to);
                    if (visitor.visit(to.value)) return;
                    break;
                }
            }
        }
    }
    private Set<Graph.EdgeInfo<V, E>> kruskal() {
        int edgeSize = vertexMap.size() - 1;
        if (edgeSize == -1) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);
        UnionFind<Vertex<V, E>> uf = new UnionFind<>();
        vertexMap.forEach((V v, Vertex<V, E> vertex) -> {
            uf.makeSet(vertex);
        });
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = heap.remove();
            if (uf.isSame(edge.from, edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from, edge.to);
        }
        return edgeInfos;
    }

    /**
     * 拓扑排序 有向无环图
     * @return 结果
     */
    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        Map<Vertex<V,E>,Integer> ins = new HashMap<>();
        vertexMap.forEach((v,vertex)->{
            int size = vertex.inEdges.size();
            if (size == 0){
                queue.offer(vertex);
            }else {
                ins.put(vertex,size);
            }
        });
        //初始化（将度为0的节点都放入队列
        while (!queue.isEmpty()){
            Vertex<V, E> vertex = queue.poll();
            list.add(vertex.value);
            for (Edge<V, E> edge : vertex.outEdges) {
                int in = ins.get(edge.to) - 1;
                if (in == 0) queue.offer(edge.to);
                else ins.put(edge.to,in);
            }
        }
        return list;
    }

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {

        return dijkstra(begin);
    }

    @Override
    public List<Object> allPath(V begin, V end) {
        Vertex<V, E> beginVertex = vertexMap.get(begin);
        Vertex<V, E> endVertex = vertexMap.get(end);
        if (beginVertex == null || endVertex == null) return null;
        LinkedList<Object> allPathList = new LinkedList<>();
        Set<Edge<V,E>> set = new HashSet<>();
        Stack<Vertex<V,E>> stack = new Stack<>();
        stack.push(beginVertex);
        LinkedList<String> pathList = new LinkedList<>();
        pathList.add(beginVertex.value.toString());
        while (!stack.isEmpty()){

            Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                if (set.contains(edge)) {
                    pathList.remove(edge.to.value.toString());
                    pathList.remove(edge.toString());
                    set.remove(edge);
                    continue;
                }
                stack.push(vertex);
                stack.push(edge.to);
                pathList.add(edge.toString());
                pathList.add(edge.to.value.toString());
                set.add(edge);
                if (edge.to.equals(endVertex)){
                    allPathList.add(pathList);
                    pathList = new LinkedList<>(pathList);
                    stack.pop();
                }
                break;
            }
        }

        return allPathList;
    }




    private Map<V,PathInfo<V,E>> bellmanFold(V begin){
        Vertex<V, E> beginVertex = vertexMap.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V,E>> selectedPaths = new HashMap<>();
        int count = verticesSize() - 1;
        for (int i = 0; i < count; i++) {
            for (Edge<V, E> edge : edges) {
                PathInfo<V,E> fromPath = selectedPaths.get(edge.to.value);
                relaxBellmanFold(selectedPaths,fromPath,edge);
            }
        }
        return selectedPaths;
    }
    private Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V, E> beginVertex = vertexMap.get(begin);
        if (beginVertex == null) return null;

        Map<Vertex<V,E>, PathInfo<V,E>> paths = new HashMap<>();

        Map<V, PathInfo<V,E>> selectedPaths = new HashMap<>();

        //初始化paths
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            PathInfo<V, E> pathInfo = new PathInfo<>();
            pathInfo.weight = outEdge.weight;
            pathInfo.edgeInfos.add(outEdge.info());
            paths.put(outEdge.to, pathInfo);
        }

        while (!paths.isEmpty()){
            Map.Entry<Vertex<V, E>, PathInfo<V,E>> minPathEntry = getMinPath(paths);
            Vertex<V, E> minVertex = minPathEntry.getKey();
            selectedPaths.put(minVertex.value, minPathEntry.getValue());
            paths.remove(minVertex);
            //对它的minVertex的outEdges进行松弛
            for (Edge<V, E> edge : minVertex.outEdges) {
                //如果edge.to已经离开桌面，就没必要进行松弛操作
                if (selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) continue;
                relax(paths, minPathEntry.getValue(), edge);
            }
        }

        return selectedPaths;
    }



    private void relax(Map<Vertex<V, E>, PathInfo<V, E>> paths, PathInfo<V, E> pathInfo, Edge<V, E> edge) {
        //新的可选择的最短路径 beginVertex 到 edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(pathInfo.weight, edge.weight);
        PathInfo<V,E> oldPath = paths.get(edge.to);
        if (oldPath != null &&  weightManager.compare(newWeight, oldPath.weight) >= 0) return;

        if (oldPath == null){
            oldPath = new PathInfo<>();
        }else
            oldPath.edgeInfos.clear();

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(pathInfo.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        paths.put(edge.to,oldPath);
    }
    private void relaxBellmanFold(Map<V, PathInfo<V, E>> paths, PathInfo<V, E> pathInfo, Edge<V, E> edge) {
        //新的可选择的最短路径 beginVertex 到 edge.from的最短路径 + edge.weight
        E newWeight = weightManager.add(pathInfo.weight, edge.weight);
        PathInfo<V,E> oldPath = paths.get(edge.to.value);
        if (oldPath != null &&  weightManager.compare(newWeight, oldPath.weight) >= 0) return;

        if (oldPath == null){
            oldPath = new PathInfo<>();
        }else
            oldPath.edgeInfos.clear();

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(pathInfo.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        paths.put(edge.to.value,oldPath);
    }

    private Map.Entry<Vertex<V, E>, PathInfo<V,E>> getMinPath(Map<Vertex<V,E>,PathInfo<V,E>> paths) {
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V,E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, PathInfo<V,E>> minEntry = it.next();
        while (it.hasNext()){
            Map.Entry<Vertex<V, E>, PathInfo<V,E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight,minEntry.getValue().weight) < 0){
                minEntry = entry;
            }
        }
        return minEntry;
    }

    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        return null;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return prim();
    }

    private Set<EdgeInfo<V, E>> prim(){
        Iterator<Vertex<V, E>> it = vertexMap.values().iterator();
        if (!it.hasNext()) return null;


        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>();
        Set<Vertex<V,E>> added = new HashSet<>();

        Vertex<V, E> vertex = it.next();
        added.add(vertex);

        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges,edgeComparator);

        int edgeSize = vertexMap.size() - 1;
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize){
            Edge<V, E> edge = heap.remove();
            if (added.contains(edge.to)) continue;

            edgeInfos.add(edge.info());
            added.add(edge.to);

            heap.addAll(edge.to.outEdges);
        }

        return edgeInfos;
    }
    public void dfs1(V v){
        Vertex<V, E> beginVertex = vertexMap.get(v);
        if (beginVertex == null) return;
        Set<Vertex<V,E>> set = new HashSet<>();
        dfs(beginVertex,set);
    }

    private void dfs(Vertex<V,E> beginVertex,Set<Vertex<V,E>> set) {
        System.out.println(beginVertex.value);
        set.add(beginVertex);
        for (Edge<V, E> edge : beginVertex.outEdges) {
            if (set.contains(edge.to)) continue;
            dfs(edge.to,set);
        }
    }
  /*  public List<Object> allPaths(V begin,V end){
        Vertex<V, E> beginVertex = vertexMap.get(begin);
        Vertex<V, E> endVertex = vertexMap.get(end);
        if (beginVertex == null || endVertex == null) return null;
        HashSet<Vertex<V, E>> set = new HashSet<>();
        StringBuilder sb = new StringBuilder(begin.toString());
        set.add(beginVertex);
        return allPaths(beginVertex,endVertex,set,sb);
    }

    private List<Object> allPaths(Vertex<V, E> beginVertex, Vertex<V, E> endVertex, HashSet<Vertex<V, E>> set,StringBuilder sb) {
        if (beginVertex.equals(endVertex)) return null;

        return null;
    }*/

    class Vertex<V,E>{
        public Vertex(V value){
            this.value = value;
        }
        V value;
        Set<Edge<V,E>> inEdges = new HashSet<>();
        Set<Edge<V,E>> outEdges = new HashSet<>();

        @Override
        public boolean equals(Object o) {
            return Objects.equals(value,((Vertex<V,E>)o).value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
    class Edge<V,E>{
        Vertex<V,E> from;
        Vertex<V,E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        EdgeInfo<V,E> info(){
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public boolean equals(Object o) {
            Edge<V,E> edge = (Edge<V,E>) o;
            return Objects.equals(from,edge.from) && Objects.equals(to,edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from.value +
                    ", to=" + to.value +
                    ", weight=" + weight +
                    '}';
        }

    }
}
