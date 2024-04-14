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

    public void ToAccepting() {
        if(this.state.equals(State.S)) {
            this.state = State.AS;
            return;
        }

        this.state = State.A;
    }

    public void replaceTransition(HashMap<Integer, Transition> newTransition) {
        this.transition.clear();
        this.transition.putAll(newTransition);
    }

    public void clearTransition() {
        this.transition.clear();
    }

}
