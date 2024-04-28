import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DFAminimizer extends nodeBasket {

    ArrayList<Node> basket;
    ArrayList<Node> Basket;
    ArrayList<ArrayList<Node>> container;
    HashMap<int[], ArrayList<Node>> pattern;
    HashMap<String, Node> mergedNodes;

    public DFAminimizer(ArrayList<Node> basket) {
        this.basket = basket;
        this.Basket = new ArrayList<Node>();
        this.container = new ArrayList<ArrayList<Node>>();
        this.pattern = new HashMap<int[], ArrayList<Node>>();
        this.mergedNodes = new HashMap<String, Node>();
    }

    private void separator() {
        this.container.add(new ArrayList<Node>());
        this.container.add(new ArrayList<Node>());

        for (Node iterator : basket) {
            if (iterator.state.equals(State.A) || iterator.state.equals(State.AS)) {
                this.container.get(1).add(iterator);
            }

            else {
                this.container.get(0).add(iterator);
            }
        }
    }

    public ArrayList<Node> engine() {
        separator();
        maker();
        return this.Basket;
    }

    private int checkContainer(Node node) {
        for (int i = 0; i < this.container.size(); i++) {
            if (this.container.get(i).contains(node)) {
                return i;
            }
        }

        return -1;
    }

    private boolean containsKeys(HashMap<int[], ArrayList<Node>> keys, int[] key) {
        for (int[] iterator : keys.keySet()) {
            if (checkKey(iterator, key)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkKey(int[] key1, int[] key2) {
        for (int i = 0; i < key1.length; i++) {
            if (key1[i] != key2[i]) {
                return false;
            }
        }

        return true;
    }

    private void patternCheck(Node node) {
        int transitionPattern[] = new int[2];

        for (Entry<Integer, Transition> entry : node.transition.entrySet()) {
            for (int j = 0; j < 2; j++) {
                if (entry.getValue().getTransitionValue() == numToChar(j)) {
                    transitionPattern[j] = checkContainer(entry.getValue().getTarget());
                }
            }
        }

        if (!containsKeys(this.pattern, transitionPattern)) {
            ArrayList<Node> tempContainer = new ArrayList<Node>();
            tempContainer.add(node);
            this.pattern.put(transitionPattern, tempContainer);
        }

        else {
            for (Entry<int[], ArrayList<Node>> entry : this.pattern.entrySet()) {
                if (checkKey(entry.getKey(), transitionPattern)) {
                    entry.getValue().add(node);
                    break;
                }
            }
        }
    }

    private void patternInsert(ArrayList<ArrayList<Node>> tempContainer) {
        for (Entry<int[], ArrayList<Node>> entry : this.pattern.entrySet()) {
            tempContainer.add(entry.getValue());
        }
    }

    private boolean areArrayListsEqual(ArrayList<ArrayList<Node>> list) {

        if (list.size() != this.container.size()) {
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            ArrayList<Node> sublist1 = list.get(i);
            ArrayList<Node> sublist2 = this.container.get(i);

            if (sublist1.size() != sublist2.size()) {
                return false;
            }

            for (int j = 0; j < sublist1.size(); j++) {
                if (!sublist1.get(j).equals(sublist2.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }

    private void linkNode(Node node) {

        HashMap<Integer, Transition> transition = new HashMap<Integer, Transition>();
        transition = node.transition;

        for (Entry<Integer, Transition> entry : transition.entrySet()) {
            for (Entry<String, Node> entry2 : this.mergedNodes.entrySet()) {
                if (entry2.getKey().equals(entry.getValue().getTargetName())) {
                    entry.getValue().changeTarget(entry2.getValue());
                }
            }
        }
    }

    private void mergeNode(ArrayList<Node> nodes) {
        Node tempNode = new Node(nodes.get(0));
        tempNode.transition = nodes.get(0).transition;
        String name = new String();
        for (int i = 0; i < nodes.size(); i++) {
            name = name + nodes.get(i).name;
        }

        tempNode.name = name;
        this.Basket.add(tempNode);

        for (int i = 0; i < nodes.size(); i++) {
            this.mergedNodes.put(nodes.get(i).name, tempNode);
        }

    }

    private void combineNode() {
        for (ArrayList<Node> iterator : this.container) {
            if (iterator.size() > 1) {
                mergeNode(iterator);
            }
        }

        for (ArrayList<Node> iterator : this.container) {
            if (iterator.size() > 1) {
                container.remove(iterator);
            }
        }

        for (ArrayList<Node> iterator : this.container) {
            linkNode(iterator.get(0));
            this.Basket.add(iterator.get(0));
        }

        this.container.clear();
    }

    private void maker() {
        ArrayList<ArrayList<Node>> tempContainer = new ArrayList<ArrayList<Node>>();
        boolean change = true;

        while (change) {
            for (int i = 0; i < container.size(); i++) {
                for (int j = 0; j < container.get(i).size(); j++) {
                    patternCheck(container.get(i).get(j));
                }

                patternInsert(tempContainer);
                this.pattern.clear();
            }

            change = !areArrayListsEqual(tempContainer);
            this.container.clear();
            this.container.addAll(tempContainer);
            tempContainer.clear();
        }

        combineNode();
    }

}
