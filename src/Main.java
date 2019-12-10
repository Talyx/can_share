import java.util.*;

public class Main {
    public static void main(String[] args) {
        Share can = new Share();
        Main main = new Main();
        can.can_share(main.right_alpha, main.subject_x4, main.object_z8, main.create_gtraph());
    }
    //объявление вершин графа
    private Subject subject_x1 = new Subject("x", 1, "Subject");
    private Subject subject_x2 = new Subject("x", 2, "Subject");
    private Subject subject_x3 = new Subject("x", 3, "Subject");
    private Subject subject_x4 = new Subject("x", 4, "Subject");
    private Subject subject_x5 = new Subject("x", 5, "Subject");
    private Subject subject_x6 = new Subject("x", 6, "Subject");
    private Subject subject_x7 = new Subject("x", 7, "Subject");
    private Object_ object_z8 = new Object_("z", 8, "Object");
    private Object_ object_o9 = new Object_("o", 9, "Object");
    private Object_ object_o10 = new Object_("o", 10, "Object");
    private Right right_take = new Right("take");
    private Right right_grand= new Right("grand");
    private Right right_alpha = new Right("alpha");

    //создание графа
    public List<Edge> create_gtraph() {

        Edge edge = new Edge(subject_x1, subject_x2, right_take);
        Edge edge1 = new Edge(subject_x1, subject_x3, right_take);
        Edge edge2 = new Edge(subject_x2, subject_x7, right_take);
        Edge edge3 = new Edge(subject_x4, subject_x5, right_take);
        Edge edge4 = new Edge(subject_x5, subject_x6, right_take);
        Edge edge5 = new Edge(subject_x7, object_z8, right_alpha);
        Edge edge6 = new Edge(subject_x6, object_o9, right_take);
        Edge edge7 = new Edge(object_o9, subject_x3, right_take);

        List<Edge> list = new ArrayList<>();
        list.add(edge);
        list.add(edge1);
        list.add(edge2);
        list.add(edge3);
        list.add(edge4);
        list.add(edge5);
        list.add(edge6);
        list.add(edge7);
        System.out.println("subject 1: " + subject_x1);
        System.out.println("subject 2: " + subject_x2);
        System.out.println("subject 3: " + subject_x3);
        System.out.println("subject 4: " + subject_x4);
        System.out.println("subject 5: " + subject_x5);
        System.out.println("subject 6: " + subject_x6);
        System.out.println("subject 7: " + subject_x7);



        return list;
    }

}

