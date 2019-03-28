public class MathOperations {
    private String name;
    private String shortName;
    boolean toStringMethod=true;

    public MathOperations(String name,String shortName){
        this.name=name;
        this.shortName=shortName;
    }

    @Override
    public String toString() {
        if (toStringMethod) {
            return name;
        } else {
            return shortName;
        }
    }

    public void setToStringMethod(boolean toStringMethod) {
        this.toStringMethod = toStringMethod;
    }

    public String getShortName() {
        return shortName;
    }
}
