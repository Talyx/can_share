import java.util.*;

public class Main {
    public static void main(String[] args) {
        Share can = new Share();
        Main main = new Main();
        main.create_graph();
        can.can_share(main.right_alpha, main.subject_x4, main.object_z8, main.graph);
    }

    //объявление графа
    Graph graph = new Graph();
    Graph graph1 = new Graph();
    private Subject subject_x1 = new Subject("Subject");
    private Subject subject_x2 = new Subject("Subject");
    private Subject subject_x3 = new Subject("Subject");
    private Subject subject_x4 = new Subject("Subject");
    private Subject subject_x5 = new Subject("Subject");
    private Subject subject_x6 = new Subject("Subject");
    private Subject subject_x7 = new Subject("Subject");
    private Subject subject_x12 = new Subject("Subject");
    private Object_ object_z8 = new Object_("Object");
    private Object_ object_o9 = new Object_("Object");
    private Object_ object_o10 = new Object_("Object");
    private Object_ object_o11 = new Object_("Object");
    private Object_ object_o13 = new Object_("Object");
    private Right right_take = new Right("take");
    private Right right_grant = new Right("grant");
    private Right right_alpha = new Right("alpha");

    //создание графа
    public void create_graph() {
        graph1.add_graph_element(new Edge(subject_x1, subject_x2, right_grant),graph);
        graph1.add_graph_element(new Edge(subject_x1, subject_x3, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x2, subject_x7, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x4, subject_x5, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x5, subject_x6, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x7, object_z8, right_alpha), graph);
        graph1.add_graph_element(new Edge(subject_x6, object_o9, right_take), graph);
        graph1.add_graph_element(new Edge(object_o9, subject_x3, right_take), graph);
        graph1.add_graph_element(new Edge(object_o11, object_o10, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x4, object_o11, right_take), graph);
        graph1.add_graph_element(new Edge(subject_x12, object_o13, right_take), graph);
        graph1.add_graph_element(new Edge(object_o13, object_o10, right_grant), graph);
    }

}

