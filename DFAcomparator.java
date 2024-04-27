import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class DFAcomparator extends nodeBasket {

    boolean compareResult;
    List<nodeBasket> listOfBasket;
    ArrayList<twoState> twoStatesList;

    public DFAcomparator(List<nodeBasket> listOfBasket) {
        this.listOfBasket = listOfBasket;
        this.twoStatesList = new ArrayList<twoState>();
        this.compareResult = true;
    }

    private void insertTwoStateList(twoState state) {
        this.twoStatesList.add(state);
    }

    private boolean checker(twoState state) {
        for (twoState iterator : this.twoStatesList) {
            if (state.getNode(0) == iterator.getNode(0) && state.getNode(1) == iterator.getNode(1)) {
                return true;
            }
        }

        return false;
    }

    private twoState getStarting() {
        twoState temp = new twoState(null, null);

        for (int i = 0; i < 2; i++) {
            temp.setNode(listOfBasket.get(i).searchNode(), i);
        }

        return temp;
    }

    public boolean engine() {
        twoState startingState = getStarting();

        if (startingState.stateCompare()) {
            insertTwoStateList(startingState);
            maker(startingState);
            return this.compareResult;
        }

        else {
            return false;
        }
    }

    private void maker(twoState state) {
        twoState tempState = new twoState(null, null);

        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 2; i++) {
                for (Entry<Integer, Transition> entry : state.getNode(i).transition.entrySet()) {
                    if (entry.getValue().getTransitionValue() == super.numToChar(k)) {
                        tempState.setNode(entry.getValue().getTarget(), i);
                    }
                }
            }

            if (!tempState.stateCompare()) {
                this.compareResult = false;
                break;
            }

            if (!checker(tempState)) {
                insertTwoStateList(tempState);
                maker(tempState);
            }
        }
    }
}

class twoState {

    private Node one;
    private Node two;

    public twoState(Node one, Node two) {
        this.one = one;
        this.two = two;
    }

    private State getStateOne() {
        return this.one.state;
    }

    private State getStateTwo() {
        return this.two.state;
    }

    public Node getNode(int num) {
        if (num == 0) {
            return this.one;
        }

        else {
            return this.two;
        }
    }

    public void setNode(Node node, int order) {
        if (order == 0) {
            this.one = node;
        }

        else {
            this.two = node;
        }
    }

    public boolean stateCompare() {
        if (getStateOne().equals(getStateTwo())) {
            return true;
        }

        return false;
    }
}