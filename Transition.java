// HASHMAP code, Transition value, target 
public class Transition {

    private char transitionValue;
    private Node target;

    public Transition(char transitionValue, Node target) {
        this.transitionValue = transitionValue;
        this.target = target;
    }

    public char getTransitionValue() {
        return this.transitionValue;
    }

    public Node getTarget() {
        return this.target;
    }

    public String getTargetName() {
        return this.target.name;
    }

    public void changeTarget(Node node) {
        this.target = node;
    }
}
