import java.util.*;

public class Share {

    List<Edge>          list_subj;
    Set<Island>         islands;
    List<Subject>       listAllSubject;
    Set<Edge>           grantConnectedObject = new HashSet<>();
    Set<Set<Island>>    connectedIslands = new HashSet<>();
    HashMap<Subject, Set<Object_>> connectedObjects = new HashMap<>();


    Function function = new Function();

    public boolean can_share(Right right, Object_ object_1, Object_ object_2, Graph graph) {

        list_subj = function.create_list_subj(graph);                                   //список рёбер состоящих из субъектов
        listAllSubject = function.create_List_all_subject(graph);                       //список всех субъектов
        islands = function.create_island_grouping(graph, list_subj);                    //групирование по остравам
        create_island_connection(graph);                                                //установление связей "тейк" между остравами и обнаружение "гранов"
        complete_island_connection();                                                   //дополнение списка связанных остраваов


        System.out.println("Соединенные острава:        " + connectedIslands);
        System.out.println("Все острава:    " + islands);

        return true;
    }

    //установление связей "тейк" между остравами и обнаружение "гранов"
    private void create_island_connection(Graph graph) {
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

    //вернуть остров субъекта
    private Island getIsland(Subject s) {
        for (Island i : islands)
            if (i.contains(s))
                return i;
        throw new RuntimeException(s + " not in any island");
    }


}
