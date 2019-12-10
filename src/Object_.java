public class Object_ {
    private String objectName;
    private int objectNumber;
    private String ClassName;
    public Object_(String objectName ,int objectNumber, String ClassName) {
        this.objectName = objectName;
        this.objectNumber=objectNumber;
        this.ClassName=ClassName;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getObjectNumber() {
        return objectNumber;
    }

    public String getClassName() {
        return ClassName;
    }
}
