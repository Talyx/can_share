public class Object_ {

    private String ClassName;

    public Object_(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getClassName() {
        return ClassName;
    }

    public boolean is_Object() {
        return getClassName().equals("Object");
    }
}
