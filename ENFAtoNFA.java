import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;

public class ENFAtoNFA extends nodeBasket {
    ArrayList<HashSet<Node>> NFAlist;
    HashSet<Node> acceptingForNFA;
    ArrayList<Node> basket;

    private void eWalks(int row, int order) {
        HashSet<Node> temp = new HashSet<Node>();
        temp.addAll(this.NFAlist.get(row));

        for (Node iterator : this.NFAlist.get(row)) {
            eWalk(iterator, iterator, row, temp, order);
        }

        this.NFAlist.get(row).clear();
        this.NFAlist.get(row).addAll(temp);
    }

    private void eWalk(Node node, Node init, int row, HashSet<Node> temp, int order) {
        for (Entry<Integer, Transition> entry : node.transition.entrySet()) {
            if ('e' == entry.getValue().getTransitionValue()) {
                temp.add(entry.getValue().getTarget());

                if ((entry.getValue().getTarget().state == State.A ||
                        entry.getValue().getTarget().state == State.AS) && order == 0) {
                    this.acceptingForNFA.add(init);
                }

                eWalk(entry.getValue().getTarget(), init, row, temp, order);
            }
        }
    }

    private void cusWalk(int row, int num) {
        HashSet<Node> temp = new HashSet<Node>();

        for (Node iterator : this.NFAlist.get(row)) {
            for (Entry<Integer, Transition> entry : iterator.transition.entrySet()) {
                if (super.numToChar(num) == entry.getValue().getTransitionValue()) {
                    temp.add(entry.getValue().getTarget());
                }
            }
        }

        this.NFAlist.get(row).clear();
        this.NFAlist.get(row).addAll(temp);
    }

    public void toNFA(ArrayList<Node> basket) {
        this.basket = basket;
        this.NFAlist = new ArrayList<HashSet<Node>>(); // misalkan ada 4 state
        this.acceptingForNFA = new HashSet<Node>();

        for (int i = 0; i < this.basket.size(); i++) { // 4 state
            for (int j = 0; j < 2; j++) { // 4 state ada 2 kolom 1 dan 0 p10, p11, p20, p21, p30, p31, p40, p41

                this.NFAlist.add(new HashSet<Node>());
                this.NFAlist.get((i * 2) + j).add(this.basket.get(i));
                eWalks((i * 2) + j, 0);
                cusWalk((i * 2) + j, j);
                eWalks((i * 2) + j, 1);
            }
        }

        ENFAtoNFALinker();
    }

    private void makeTransition(int num) { //
        this.basket.get(num).clearTransition();

        for (int k = 0; k < 2; k++) {
            for (Node iterator : this.NFAlist.get((num * 2) + k)) {
                nodeLinker(this.basket.get(num), iterator, numToChar(k));
            }
        }
    }

    private void ENFAtoNFALinker() {
        for (int i = 0; i < this.basket.size(); i++) {
            if (this.acceptingForNFA.contains(this.basket.get(i))) {
                this.basket.get(i).ToAccepting();
            }
            makeTransition(i);
        }
    }
}
