import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;

public class nodeBasket {

    ArrayList<Node> basket;
    ArrayList<HashSet<Node>> NFAlist;
    HashSet<Node> acceptingForNFA;
    Node traveler;

    public nodeBasket() {
        this.basket = new ArrayList<Node>();
        this.traveler = nodeMaker("traveler", State.AS);
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
            if(n.name.equals(name)) {
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

    public void transverse(String input) {
        this.traveler = searchNode();
        walk(input);
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

    private void walk(String input) {

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
                    walk(input.substring(1));
                } else if ('e' == entry.getValue().getTransitionValue()) {
                    jalanPossible.add(entry.getValue().getTarget());
                    this.traveler = entry.getValue().getTarget();
                    walk(input);
                }
            }
        }

        return;
    }

    private void eWalks(int row, int order) {
        HashSet<Node> temp = new HashSet<Node>();
        temp.addAll(this.NFAlist.get(row));

        for(Node iterator : this.NFAlist.get(row)) {
            eWalk(iterator, iterator, row, temp, order);
        }

        this.NFAlist.get(row).clear();
        this.NFAlist.get(row).addAll(temp);
    }

    private void eWalk(Node node, Node init, int row, HashSet<Node> temp, int order) {
        for(Entry<Integer, Transition> entry : node.transition.entrySet()) {
            if('e' == entry.getValue().getTransitionValue()) {
                temp.add(entry.getValue().getTarget());
                if(( entry.getValue().getTarget().state == State.A ||
                     entry.getValue().getTarget().state == State.AS) && order == 0) {
                    this.acceptingForNFA.add(init);
                }
                eWalk(entry.getValue().getTarget(), init, row, temp, order);
            } 
        }
    }

    private void cusWalk(int row, int num) {
        HashSet<Node> temp = new HashSet<Node>();
        for(Node iterator : this.NFAlist.get(row)) {
            for(Entry<Integer, Transition> entry : iterator.transition.entrySet()) {
                if(numToChar(num) == entry.getValue().getTransitionValue()) {
                    temp.add(entry.getValue().getTarget());
                }
            }
        }
        this.NFAlist.get(row).clear();
        this.NFAlist.get(row).addAll(temp);
    }

    private char numToChar(int num) {
        if(num > 0) {
            return '1';
        }
        return '0';
    }

    public void toNFA() {
        this.NFAlist = new ArrayList<HashSet<Node>>(); //misalkan ada 4 state
        this.acceptingForNFA = new HashSet<Node>();

        for(int i = 0 ; i < this.basket.size() ; i++) { //4 state
            for(int j = 0 ; j < 2 ; j++) { // 4 state ada 2 kolom 1 dan 0 p10, p11, p20, p21, p30, p31, p40, p41
                
                this.NFAlist.add(new HashSet<Node>());
                this.NFAlist.get((i * 2) + j).add(this.basket.get(i));
                eWalks((i * 2) + j, 0);
                cusWalk((i * 2) + j, j);
                eWalks((i * 2) + j, 1);
                
                //this.NFAlist.get((i * 2) + j).add(this.traveler.name);
                //this.traveler = this.basket.get(i);
            }
        }

        ENFAtoNFALinker();
    }

    private void makeTransition(int num) { //
        this.basket.get(num).clearTransition();
        for(int k = 0 ; k < 2 ; k++) {
            for(Node iterator : this.NFAlist.get((num * 2) + k)) {
                nodeLinker(this.basket.get(num), iterator, numToChar(k));
            }
        }
    }

    private void ENFAtoNFALinker() {
        for(int i = 0 ; i < this.basket.size() ; i++) {
            if(this.acceptingForNFA.contains(this.basket.get(i))) {
                this.basket.get(i).ToAccepting();
            }
            makeTransition(i);
        }
    }


}
