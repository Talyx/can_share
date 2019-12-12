public class Edge {
    private Object_ object_1;
    private Object_ object_2;
    private Right right;

    public Edge(Object_ object_1, Object_ object_2,  Right right) {
        this.object_1 = object_1;
        this.object_2 = object_2;
        this.right = right;
    }

    public Object_ getObject_1() {
        return object_1;
    }

    public Object_ getObject_2() {
        return object_2;
    }

    public String getRight() {
        return right.getRight();
    }


}
