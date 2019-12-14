import java.util.*;

public class Function {


    // групирование по остравам
    public Set<Island> create_island_grouping(Graph graph, List<Edge> list_subj) {
        Set<Object_> handledInAnyIsland = new HashSet<>();
        Deque<Object_> stack = new ArrayDeque<>();
        Set<Island> islands = new HashSet<>();
        Object_ current;
        int i = 1;
        for (Edge e : graph) {
            if (!e.getObject_1().is_Object() && !handledInAnyIsland.contains(e.getObject_1())) {
                stack.push(e.getObject_1());
            }

            if (!e.getObject_2().is_Object() && !handledInAnyIsland.contains(e.getObject_2())) {
                stack.push(e.getObject_2());
            }
            if (!stack.isEmpty()) {
                Island island = new Island(i);
                while (!stack.isEmpty()) {
                    current = stack.pop();
                    island.add(current);

                    for (Edge k : list_subj) {
                        Object_ o = null;
                        if (k.getObject_1() == current) {
                            o = k.getObject_2();
                        } else if (k.getObject_2() == current) {
                            o = k.getObject_1();
                        }
                        if (!island.contains(o) && !stack.contains(o) && o != null) {
                            stack.push(o);
                        }
                    }
                }
                islands.add(island);
                handledInAnyIsland.addAll(island);
                i++;
            }
        }
        return islands;
    }

    //список всех субъектов
    public List<Subject> create_List_all_subject(Graph graph) {
        List<Subject> listAllSubject = new ArrayList<>();
        for (Edge e : graph) {
            if (!e.getObject_1().is_Object()) {
                listAllSubject.add((Subject) e.getObject_1());
            }
            if (!e.getObject_2().is_Object()) {
                listAllSubject.add((Subject) e.getObject_2());
            }
        }
        return listAllSubject;
    }

    // создание списка рёбер состоящих из субъектов
    public Graph create_graph_subj(Graph graph) {
       Graph list_subj = new Graph();
        for (Edge e : graph) {
            if (!e.getObject_1().is_Object() && !e.getObject_2().is_Object()) {
                list_subj.add(e);
            }
        }
        return list_subj;
    }

    //проверка пересечения
    public boolean intersection(Set<Object_> s, Set<Object_> s1) {
        for (Object_ o : s) {
            for (Object_ o1 : s1) {
                if (o == o1) {
                    return true;
                }
            }
        }
        return false;
    }

    //вернуть другую вершину ребра
    public Object_ returnNeighbour(Edge edge, Object_ object_) {
        if (edge.getObject_1() == object_)
            return edge.getObject_2();
        else
            return edge.getObject_1();
    }


}
