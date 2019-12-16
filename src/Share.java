import java.util.*;

public class Share {
    private Set<Island> islands;
    private List<Subject> listAllSubject;
    private Set<Edge> grantConnectedObject = new HashSet<>();
    private Set<Set<Island>> connectedIslands = new HashSet<>();
    private HashMap<Subject, Set<Object_>> connectedObjects = new HashMap<>();


    Function function = new Function();

    public boolean can_share(Right right, Object_ object_1, Object_ object_2, Graph graph) {

        Graph graph_subj = function.create_graph_subj(graph);                             //список рёбер состоящих из субъектов
        listAllSubject = function.create_List_all_subject(graph);                        //список всех субъектов
        islands = function.create_island_grouping(graph, graph_subj);                   //групирование по остравам
        create_island_connection(graph);                                               //установление связей "тейк" между остравами и обнаружение "грантов"
        complete_island_connection();                                                 //дополнение списка связанных остраваов
        create_archipelago();

        Set<Set<Island>> x;
        Set<Set<Island>> y;
        x = get_list_archipelagos_for_object_1(object_1, graph);
        y = get_list_archipelagos_for_object_2(object_2, graph);
        if (archipelago_intersection(x, y)) {
            return true;
        }
        return false;
    }


    private boolean archipelago_intersection(Set<Set<Island>> a1, Set<Set<Island>> a2) {
        for (Set<Island> arch_a1 : a1) {
            for (Set<Island> arch_a2 : a2) {
                if (arch_a1 == arch_a2) {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Set<Island>> get_list_archipelagos_for_object_1(Object_ object_, Graph graph) {
        if (object_.is_Object()) {
            Set<Set<Island>> archipelagos = new HashSet<>();
            Deque<Object_> stack = new ArrayDeque<>();
            Object_ current;
            List<Object_> list = new ArrayList<>();
            for (Edge e : graph) {
                if (e.getObject_2() == object_ && e.getRight() == Right.GRANT) {
                    if (!e.getObject_1().is_Object())
                        archipelagos.add(get_archipelagos((e.getObject_1())));
                    else
                        list.add(e.getObject_1());
                }

            }
            Set<Object_> set = new HashSet<>();
            for (Object_ obj : list) {
                stack.push(obj);
                while (!stack.isEmpty()) {
                    current = stack.pop();
                    set.add(current);
                    for (Edge e : graph) {
                        if (e.getObject_2() == current && e.getRight()== Right.TAKE) {
                            if (!e.getObject_1().is_Object()) archipelagos.add(get_archipelagos(e.getObject_1()));
                            else if (set.contains(e.getObject_1()) && !stack.contains(e.getObject_1())) {
                                stack.push(e.getObject_1());
                            }
                        }
                    }
                }
            }
            return archipelagos;
        } else
            return Set.of(get_archipelagos(object_));
    }

    private Set<Island> get_archipelagos(Object_ object_2) {
        for (Set<Island> archi : archipelagos) {
            if (potom(object_2, archi)) {
                return archi;
            }
        }
        throw new RuntimeException(object_2 + " not have archipelagos");
    }

    private Set<Set<Island>> get_list_archipelagos_for_object_2(Object_ object_, Graph graph) {
        if (object_.is_Object()) {
            List<Object_> list = new ArrayList<>();
            Set<Set<Island>> archipelagos = new HashSet<>();
            Deque<Object_> stack = new ArrayDeque<>();
            Object_ current;
            for (Edge e : graph) {
                if (e.getObject_2() == object_ && e.getRight() == Right.ALPHA) {
                    if (!e.getObject_1().is_Object())
                        archipelagos.add(get_archipelagos((e.getObject_1())));
                    else
                        list.add(e.getObject_1());
                }
            }

            Set<Object_> set = new HashSet<>();
            for (Object_ obj : list) {
                stack.push(obj);
                while (!stack.isEmpty()) {
                    current = stack.pop();
                    set.add(current);
                    for (Edge e : graph) {
                        if (e.getObject_2() == current && e.getRight() == Right.TAKE) {
                            if (!e.getObject_1().is_Object()) archipelagos.add(get_archipelagos(e.getObject_2()));
                            else if (set.contains(e.getObject_1()) && !stack.contains(e.getObject_1())) {
                                stack.push(e.getObject_1());
                            }
                        }
                    }
                }
            }
            return archipelagos;
        } else
            return Set.of(get_archipelagos(object_));
    }

    //установление связей "тейк" между остравами и обнаружение "грантов"
    private void create_island_connection(Graph graph) {
        Deque<Object_> stack_bridge = new ArrayDeque<>();
        Object_ current_for_obj;
        for (Subject e : listAllSubject) {
            stack_bridge.push(e);
            Set<Object_> set = new HashSet<>();
            while (!stack_bridge.isEmpty()) {
                current_for_obj = stack_bridge.pop();
                for (Edge k : graph) {
                    if ((k.getObject_1() == current_for_obj) && k.getRight()== Right.TAKE && k.getObject_2().is_Object()) {
                        stack_bridge.push(k.getObject_2());
                        set.add(k.getObject_2());
                    }
                    if (k.getRight() == Right.GRANT && (k.getObject_1() == current_for_obj || k.getObject_2() == current_for_obj)) {
                        grantConnectedObject.add(new Edge(k.getObject_1(), k.getObject_2(),  Right.GRANT));
                    }
                    if (k.getObject_1() == current_for_obj && !k.getObject_2().is_Object()) {
                        if (!(getIsland(e) == getIsland((Subject) k.getObject_2())))
                            connectedIslands.add(Set.of(getIsland(e), getIsland((Subject) k.getObject_2())));
                    }
                    if (k.getObject_2() == current_for_obj && !k.getObject_1().is_Object() && k.getRight() == Right.GRANT) {
                        if (!(getIsland(e) == getIsland((Subject) k.getObject_1())))
                            connectedIslands.add(Set.of(getIsland(e), getIsland((Subject) k.getObject_1())));
                    }
                }
            }
            connectedObjects.put(e, set);
        }
    }

    //дополнение списка связанных остраваов
    private void complete_island_connection() {
        HashMap<Subject, Set<Object_>> intersections_object = new HashMap<>();
        for (Subject subj : listAllSubject) {
            Set<Object_> bridgeContinue = new HashSet<>();
            for (Object_ obj : connectedObjects.get(subj)) {
                for (Edge grantEdge : grantConnectedObject) {
                    if (grantEdge.getObject_1() == obj || grantEdge.getObject_2() == obj) {
                        bridgeContinue.add(function.returnNeighbour(grantEdge, obj));
                    }
                }
            }
            intersections_object.put(subj, bridgeContinue);
        }

        for (HashMap.Entry<Subject, Set<Object_>> s : connectedObjects.entrySet()) {
            for (HashMap.Entry<Subject, Set<Object_>> k : intersections_object.entrySet()) {

                if (function.intersection(s.getValue(), k.getValue()) && (getIsland(s.getKey()) != getIsland(k.getKey()))) {
                    connectedIslands.add(Set.of(getIsland(s.getKey()), getIsland(k.getKey())));
                }
            }
        }
    }

    Set<Set<Island>> archipelagos = new HashSet<>();

    private void create_archipelago() {
        Deque<Island> archipelago_stack = new ArrayDeque<>();
        Island archipelago_current;
        for (Island isld : islands) {
            Set<Island> archipelago = new HashSet<>();
            archipelago_stack.add(isld);
            while (!archipelago_stack.isEmpty()) {
                archipelago_current = archipelago_stack.pop();
                archipelago.add(archipelago_current);
                for (Set<Island> i : connectedIslands) {
                    if (i.contains(archipelago_current) && !archipelago.contains(return_island_neighbour(i, archipelago_current))) {
                        archipelago_stack.push(return_island_neighbour(i, archipelago_current));
                        archipelago.add(return_island_neighbour(i, isld));
                    }
                }
            }
            archipelagos.add(archipelago);
        }

    }

    private Island return_island_neighbour(Set<Island> i, Island isld) {
        for (Island isla : i) {
            if (isla != isld)
                return isla;
        }
        return isld;
    }

    //вернуть остров субъекта
    private Island getIsland(Subject s) {
        for (Island i : islands)
            if (i.contains(s))
                return i;
        throw new RuntimeException(s + " not in any island");
    }

    public boolean potom(Object_ object_, Set<Island> isL) {
        for (Island i : isL) {
            if (i.contains(object_))
                return true;
        }
        return false;
    }


}
