import java.util.ArrayList;
import java.util.Map.Entry;

public class nodeBasket {

    ArrayList<Node> basket;
    Node traveler;

    ArrayList<String> path;

    public nodeBasket() {
        path = new ArrayList<String>();
        basket = new ArrayList<Node>();
        traveler = nodeMaker("traveler", State.AS);
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
        for(Node n : basket) {
            if(n.name == name) {
                return n;
            }
        }
        return null;
    }

    private Node searchNode() {
        for(Node n : basket) {
            if(n.state == State.S || n.state == State.AS) {
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

    public void cari(String input) {

        this.traveler = searchNode();

        maju(input);

        return;
    }

    private boolean check(Node node) {
        if(node.state == State.A) {
            return true;
        } else if (node.state == State.AS) {
            return true;
        }

        return false;
    }

    private void maju(String input) {

        boolean hasil;

        if(input.length() == 0) {
            hasil = check(traveler);
            System.out.println(hasil);
            if(hasil) {
                return;
            }

        } else {

            ArrayList<Node> jalanPossible = new ArrayList<Node>();

            for(Entry<Integer, Transition> entry : this.traveler.transition.entrySet()) {
                if(input.charAt(0) == entry.getValue().getTransitionValue()) {
                    jalanPossible.add(entry.getValue().getTarget());
                    this.traveler = entry.getValue().getTarget();
                    System.out.println(this.traveler.name);
                    maju(input.substring(1));
                } else if ('e' == entry.getValue().getTransitionValue()) {
                    jalanPossible.add(entry.getValue().getTarget());
                    this.traveler = entry.getValue().getTarget();
                    System.out.println(this.traveler.name);
                    maju(input);
                }
            }
        }

        return;
    }


}
