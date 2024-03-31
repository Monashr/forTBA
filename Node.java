import java.util.HashMap;


public class Node {

    State state;
    String name;
    HashMap<Integer, Transition> transition = new HashMap<Integer, Transition>();

    public Node(State state, String name) {
        this.name = name;
        this.state = state;
    }

    public void addTransition(Transition value) {
        this.transition.putIfAbsent(this.transition.size(), value);
    }

}
