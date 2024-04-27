import java.util.ArrayList;
import java.util.Map.Entry;

public class nodeBasket {

    protected ArrayList<Node> basket;
    Node traveler;

    public nodeBasket() {
        this.basket = new ArrayList<Node>();
        this.traveler = nodeMaker("traveler", State.AS);
    }

    public ArrayList<Node> getBasket() {
        return this.basket;
    }

    public int size() {
        return this.basket.size();
    }

    public static Node nodeMaker(String name, State state) {
        return new Node(state, name);
    }

    public void nodeInsert(String name, State state) {
        this.basket.add(nodeMaker(name, state));
        return;
    }

    public void nodeInsert(Node node) {
        this.basket.add(node);
        return;
    }

    public static void nodeLinker(Node from, Node to, char value) {
        from.transition.put(from.transition.size(), new Transition(value, to));
        return;
    }

    private Node searchNode(String name) {
        for (Node n : basket) {
            if (n.name.equals(name)) {
                return n;
            }
        }

        return null;
    }

    protected Node searchNode() {
        for (Node n : basket) {
            if (n.state == State.S || n.state == State.AS) {
                return n;
            }
        }

        return null;
    }

    public void linkNode(String from, String to, char value) {
        nodeLinker(
                this.basket.get(this.basket.indexOf(searchNode(from))),
                this.basket.get(this.basket.indexOf(searchNode(to))),
                value);

        return;
    }

    public void transverse(String input) {
        this.traveler = searchNode();
        walk(input, this.traveler.name);

        return;
    }

    private boolean check(Node node) {
        if (node.state == State.A) {
            return true;
        }

        else if (node.state == State.AS) {
            return true;
        }

        return false;
    }

    private void walk(String input, String output) {
        boolean hasil;

        if (input.length() == 0) {
            hasil = check(traveler);
            System.out.println(output + " " + hasil);
        }

        else {
            ArrayList<Node> jalanPossible = new ArrayList<Node>();
            for (Entry<Integer, Transition> entry : this.traveler.transition.entrySet()) {
                if (input.charAt(0) == entry.getValue().getTransitionValue()) {
                    jalanPossible.add(entry.getValue().getTarget());
                    this.traveler = entry.getValue().getTarget();
                    walk(input.substring(1), output + " " + entry.getValue().getTargetName());
                }

                else if ('e' == entry.getValue().getTransitionValue()) {
                    jalanPossible.add(entry.getValue().getTarget());
                    this.traveler = entry.getValue().getTarget();
                    walk(input, output + " " + entry.getValue().getTargetName());
                }
            }
        }

        return;
    }

    protected char numToChar(int num) {
        if (num > 0) {
            return '1';
        }

        return '0';
    }

    public void toNFA() {
        ENFAtoNFA nfaManager = new ENFAtoNFA();
        nfaManager.toNFA(this.basket);
    }

    public void toDFA() {
        NFAtoDFA DFAmanager = new NFAtoDFA(this.basket);
        this.basket = DFAmanager.engine(searchNode());
    }

    private String statePrinter(Node node) {
        if (node.state == State.A) {
            return "Accepting";
        }

        if (node.state == State.AS) {
            return "StartingAccepting";
        }

        if (node.state == State.S) {
            return "Starting";
        }

        return "NonAccepting";
    }

    public void print() {
        System.out.println("state");
        for (Node node : this.basket) {
            System.out.println(node.name + " " + statePrinter(node));
        }

        System.out.println("links");
        for (Node node : this.basket) {
            for (Entry<Integer, Transition> entry : node.transition.entrySet()) {
                System.out.println(node.name + " " + entry.getValue().getTargetName() + " "
                        + entry.getValue().getTransitionValue());
            }
        }
    }

}
