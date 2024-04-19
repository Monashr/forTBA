import java.util.ArrayList;
import java.util.Map.Entry;

public class DFAminimizer extends nodeBasket{

    ArrayList<Node> basket;
    ArrayList<ArrayList<Node>> container;

    public DFAminimizer(ArrayList<Node> basket) {
        this.basket = basket;
        this.container = new ArrayList<ArrayList<Node>>();
    }

    private void separator() {
        this.container.add(new ArrayList<Node>());
        this.container.add(new ArrayList<Node>());
        for(Node iterator : basket) {
            if(iterator.state.equals(State.A) || iterator.state.equals(State.AS)) {
                this.container.get(1).add(iterator);
            } else {
                this.container.get(0).add(iterator);
            }
        }
    }

    public void engine() {
        separator();
        maker();
    }

    private boolean checkBox(Node initial, Node toCheck) {

        for(int i = 0 ; i < this.container.size() ; i++) {
            if(this.container.get(i).contains(initial) && this.container.get(i).contains(toCheck)) {
                return true;
            }
        }

        return false;
    }

    private void removeNodeFromBox(Node node) {
        for(int i = 0 ; i < this.container.size() ; i++) {
            if(this.container.get(i).contains(node)) {
                this.container.get(i).remove(node);
                if(this.container.get(i).isEmpty()) {
                    this.container.remove(i);
                }
                break;
            }
        }
    }

    private boolean lonely(Node node) {
        for(int i = 0 ; i < this.container.size() ; i++) {
            if(this.container.get(i).contains(node) && this.container.get(i).size() == 1) {
                return true;
            }
        }

        return false;
    }

    private void maker() {
        ArrayList<Node> box = new ArrayList<Node>();
        for(int k = 0 ; k < 2 ; k++) {
            for(int i = 0 ; i < 2 ; i++) {
                for(Node iterator : this.basket) {
                    for(Entry<Integer, Transition> entry : iterator.transition.entrySet()) {
                        if(entry.getValue().getTransitionValue() == numToChar(i)) {
                            if(!checkBox(iterator, entry.getValue().getTarget())) {
                                if(lonely(iterator)) {
                                    continue;
                                } else {
                                    box.add(iterator);
                                    removeNodeFromBox(iterator);
                                }
                            }
                        }
                    }
                }
    
                if(!(box.size() == 0)) {
                    this.container.add(new ArrayList<Node>(box));
                    box.clear();
                }
    
            }
        }
        
    }


}
