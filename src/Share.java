import java.util.*;

public class Share {

    public boolean can_share(Right right, Object_ object_1, Object_ object_2, List<Edge> graph) {
        List<Edge> list_subj = new ArrayList<>();
        List<Edge> list_obj = new ArrayList<>();
        // создание списка рёбер состоящих из субъектов
        for (Edge e : graph) {
            if (e.getObject_1().getClassName().equals("Subject") && e.getObject_2().getClassName().equals("Subject")) {
                list_subj.add(e);
            }
        }
        //список всех субъектов
        List<Object_>listAllSubject = new ArrayList<>();
        for (Edge e: graph){
            if (e.getObject_1().getClassName().equals("Subject")){
                listAllSubject.add(e.getObject_1());
            }
            if (e.getObject_2().getClassName().equals("Subject")){
                listAllSubject.add(e.getObject_2());
            }
        }
        //список рёбер состоящих из объектов
        for (Edge e : graph) {
            if (e.getObject_1().getClassName().equals("Object") && e.getObject_2().getClassName().equals("Object"))
                list_obj.add(e);
        }

        Set<Island> islands = new HashSet<>();
        Set<Object_> handledInAnyIsland = new HashSet<>();
        Deque<Object_> stack = new ArrayDeque<>();
        Object_ current;

        //групирование по островам
        int i = 1;
        for (Edge e : graph) {
            if (e.getObject_1().getClassName().equals("Subject") && !handledInAnyIsland.contains(e.getObject_1())) {
                stack.push(e.getObject_1());
            }
            if (e.getObject_2().getClassName().equals("Subject") && !handledInAnyIsland.contains(e.getObject_2())) {
                stack.push(e.getObject_2());
            }
            if (!stack.isEmpty()) {
                Island island = new Island(i);
                while (!stack.isEmpty()) {
                    current = stack.pop();
                    island.add(current);

                    for (Edge k : list_subj) {
                        Object_ o = null;
                        if (k.getObject_1().getObjectNumber() == current.getObjectNumber()) {
                            o = k.getObject_2();
                        } else if (k.getObject_2().getObjectNumber() == current.getObjectNumber()) {
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

        //установление связей между остравами
        Deque<Object_> stack_bridge = new ArrayDeque<>();
        Object_ current_for_obj;
        for (Object_ e: listAllSubject) {

            while(!stack_bridge.isEmpty()){
                current_for_obj= stack_bridge.pop();
                for (Edge k: graph){
                    if ((k.getObject_1().getObjectNumber()== current_for_obj.getObjectNumber()) && k.getObject_2().getClassName().equals("Object")){
                        stack_bridge.push(k.getObject_2());
                    }
                }
            }
        }

        for(Island t: islands){
            System.out.println(t.getIslandIndex() + " ");
        }
        System.out.println(islands);
        System.out.println(list_obj);

        return true;
    }
}
