
public class Main extends Graph {
    public static void main(String[] args) {
        Share can = new Share();
        Main main = new Main();
        main.create_graph();
        if (can.can_share(main.right_alpha, main.object_o15, main.object_z8, main.graph)) {
            System.out.println("it's really true!");
        } else
            System.out.println("don't worry");
    }

    //объявление графа
    private Graph graph = new Graph();

    private Subject subject_x1 = new Subject(false);
    private Subject subject_x2 = new Subject(false);
    private Subject subject_x3 = new Subject(false);
    private Subject subject_x4 = new Subject(false);
    private Subject subject_x5 = new Subject(false);
    private Subject subject_x6 = new Subject(false);
    private Subject subject_x7 = new Subject(false);
    private Subject subject_x12 = new Subject(false);
    private Object_ object_z8 = new Object_(true);
    private Object_ object_o9 = new Object_(true);
    private Object_ object_o10 = new Object_(true);
    private Object_ object_o11 = new Object_(true);
    private Object_ object_o13 = new Object_(true);
    private Object_ object_o14 = new Object_(true);
    private Object_ object_o15 = new Object_(true);
    private Right right_take = Right.TAKE;
    private Right right_grant = Right.GRANT;
    private Right right_alpha = Right.ALPHA;

    //создание графа
    public void create_graph() {
        add_graph_element(new Edge(subject_x1, subject_x2, right_grant), graph);
        add_graph_element(new Edge(subject_x1, subject_x3, right_take), graph);
        add_graph_element(new Edge(subject_x2, subject_x7, right_take), graph);
        add_graph_element(new Edge(subject_x4, subject_x5, right_take), graph);
        add_graph_element(new Edge(subject_x5, subject_x6, right_take), graph);
        add_graph_element(new Edge(subject_x7, object_z8, right_alpha), graph);
        add_graph_element(new Edge(object_o9, subject_x6, right_take), graph);
        add_graph_element(new Edge(subject_x3, object_o9, right_take), graph);
        add_graph_element(new Edge(object_o11, object_o10, right_take), graph);
        add_graph_element(new Edge(subject_x4, object_o11, right_take), graph);
        add_graph_element(new Edge(subject_x12, object_o13, right_take), graph);
        add_graph_element(new Edge(object_o13, object_o10, right_grant), graph);
        add_graph_element(new Edge(object_o14, object_o15, right_grant), graph);
        add_graph_element(new Edge(subject_x12, object_o14, right_take), graph);
    }

}

