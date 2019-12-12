import java.util.*;

public class Share {
    Set<Island> islands = new HashSet<>();
    List<Edge> list_subj = new ArrayList<>();
    List<Subject> listAllSubject = new ArrayList<>();
    HashMap<Subject, Set<Object_>> connectedObjects = new HashMap<>();
    Set<Set<Island>> connectedIslands = new HashSet<>();
    Set<Edge> grantConnectedObject = new HashSet<>();

    public boolean can_share(Right right, Object_ object_1, Object_ object_2, List<Edge> graph) {

        create_list_subj(graph); //список рёбер состоящих из субъектов
        create_List_all_subject(graph);  //список всех субъектов
        create_island_grouping(graph); //групирование по остравам
        create_island_connection(graph); //установление связей "тейк" между остравами и обнаружение "гранов"
        complete_island_connection(); //дополнение списока связанных остраваов

        System.out.println("Соединенные острава:        " + connectedIslands);
        System.out.println("Все острава:    " + islands);

        return true;
    }

    //дополнение списока связанных остраваов
    private void complete_island_connection() {
        HashMap<Subject, Set<Object_>> potomPridumayu = new HashMap<>();
        for (Subject subj : listAllSubject) {
            Set<Object_> bridgeContinue = new HashSet<>();
            for (Object_ obj : connectedObjects.get(subj)) {
                for (Edge grantEdge : grantConnectedObject) {
                    if (grantEdge.getObject_1() == obj || grantEdge.getObject_2() == obj) {
                        bridgeContinue.add(returnNeighbour(grantEdge, obj));
                    }
                }
            }
            potomPridumayu.put(subj, bridgeContinue);
        }

        for (HashMap.Entry<Subject, Set<Object_>> s : connectedObjects.entrySet()) {
            for (HashMap.Entry<Subject, Set<Object_>> k : potomPridumayu.entrySet()) {

                if (intersection(s.getValue(), k.getValue()) && (getIsland(s.getKey()) != getIsland(k.getKey()))) {
                    connectedIslands.add(Set.of(getIsland(s.getKey()), getIsland(k.getKey())));
                }
            }
        }
    }

    //установление связей "тейк" между остравами и обнаружение "гранов"
    private void create_island_connection(List<Edge> graph) {
        Deque<Object_> stack_bridge = new ArrayDeque<>();
        Object_ current_for_obj;
        for (Subject e : listAllSubject) {
            stack_bridge.push(e);
            Set<Object_> set = new HashSet<>();
            while (!stack_bridge.isEmpty()) {
                current_for_obj = stack_bridge.pop();
                for (Edge k : graph) {
                    if ((k.getObject_1() == current_for_obj) && k.getRight().equals("take") && k.getObject_2().is_Object()) {
                        stack_bridge.push(k.getObject_2());
                        set.add(k.getObject_2());
                    }
                    if (k.getRight().equals("grant") && (k.getObject_1() == current_for_obj || k.getObject_2() == current_for_obj)) {
                        grantConnectedObject.add(new Edge(k.getObject_1(), k.getObject_2(), new Right("grant")));
                    }
                    if (k.getObject_1() == current_for_obj && !k.getObject_2().is_Object()) {
                        if (!(getIsland(e) == getIsland((Subject) k.getObject_2())))
                            connectedIslands.add(Set.of(getIsland(e), getIsland((Subject) k.getObject_2())));
                    }
                    if (k.getObject_2() == current_for_obj && !k.getObject_1().is_Object() && k.getRight().equals("grant")) {
                        if (!(getIsland(e) == getIsland((Subject) k.getObject_1())))
                            connectedIslands.add(Set.of(getIsland(e), getIsland((Subject) k.getObject_1())));
                    }
                }
            }
            connectedObjects.put(e, set);
        }
    }

    // групирование по остравам
    private void create_island_grouping(List<Edge> graph) {
        Set<Object_> handledInAnyIsland = new HashSet<>();
        Deque<Object_> stack = new ArrayDeque<>();
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
    }

    //список всех субъектов
    private void create_List_all_subject(List<Edge> graph) {
        for (Edge e : graph) {
            if (!e.getObject_1().is_Object()) {
                listAllSubject.add((Subject) e.getObject_1());
            }
            if (!e.getObject_2().is_Object()) {
                listAllSubject.add((Subject) e.getObject_2());
            }
        }
    }

    // создание списка рёбер состоящих из субъектов
    private void create_list_subj(List<Edge> graph) {

        for (Edge e : graph) {
            if (!e.getObject_1().is_Object() && !e.getObject_2().is_Object()) {
                list_subj.add(e);
            }
        }
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

    //вернуть остров субъекта
    private Island getIsland(Subject s) {
        for (Island i : islands)
            if (i.contains(s))
                return i;
        throw new RuntimeException(s + " not in any island");
    }

}
