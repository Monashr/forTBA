import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;

public class NFAtoDFA extends nodeBasket {

    private HashSet<Node> DFAMap;
    private Node DeadState;

    public NFAtoDFA(ArrayList<Node> basket) {
        this.DFAMap = new HashSet<Node>();
    }

    private void insertToMap(Node node) {
        this.DFAMap.add(node);
    }

    private Node checker(String name) {
        for(Node iterator : this.DFAMap) {
            if(iterator.name.equals(name)) {
                return iterator;
            }
        }
        return null;
    }

    public ArrayList<Node> engine(Node startingPoint) {
        maker(startingPoint);

        return new ArrayList<Node>(this.DFAMap);
    }

    private void makeDeadState() {
        this.DeadState = super.nodeMaker("DeadState", State.N); 
        for(int i = 0 ; i < 2 ; i++) {
            nodeLinker(this.DeadState, this.DeadState, super.numToChar(i));
        }
        insertToMap(this.DeadState);
    }

    private String nameBuilder(HashSet<Node> nodeList) {
        StringBuilder sb = new StringBuilder();

        for(Node iterator : nodeList) {
            sb.append(iterator.name);
        }

        return sb.toString();
    }

    private State stateChecker(HashSet<Node> Nodes) {
        for(Node iterator : Nodes) {
            if(iterator.state.equals(State.A) || iterator.state.equals(State.AS)) {
                return State.A;
            }
        }
        return State.N;
    }

    private Node combineMultipleNodes(HashSet<Node> Nodes, String name, char state) {

        HashSet<String> transitionList = new HashSet<String>();
        Node tempNode = super.nodeMaker(name, stateChecker(Nodes));        
            for(Node iterator : Nodes) {
                for(Entry<Integer, Transition> entry : iterator.transition.entrySet()) {
                    String transition = entry.getValue().getTargetName() + entry.getValue().getTransitionValue();
                    if(transitionList.contains(transition)) {
                        continue;
                    }

                    transitionList.add(transition);
                    super.nodeLinker(tempNode, entry.getValue().getTarget(), entry.getValue().getTransitionValue());
            }
        }

        return tempNode;
    }

    private Node getNodeFromSet(HashSet<Node> set) {
        for(Node node : set) {
            return node;
        }

        return null;
    }

    private String getNodeNameFromSet(HashSet<Node> set) {
        for(Node node : set) {
            return node.name;
        }

        return "NoName";
    }

    public Node maker(Node node) {
        HashSet<Node> temp =  new HashSet<Node>();
        Node newNode = new Node(node);
        this.DFAMap.add(newNode);
        for(int i = 0 ; i < 2 ; i++) {
            temp.clear();
            for(Entry<Integer, Transition> entry : node.transition.entrySet()) {
                if(entry.getValue().getTransitionValue() == super.numToChar(i)) {
                    temp.add(entry.getValue().getTarget());
                }
            }

            if(temp.size() == 0) {
                if(this.DeadState == null) {
                    makeDeadState();
                }

                super.nodeLinker(newNode, this.DeadState, numToChar(i));
                temp.clear();
                continue;
            }
            if(temp.size() == 1) {
                Node existingNode = checker(getNodeNameFromSet(temp));
                if(existingNode != null) {
                    super.nodeLinker(newNode, existingNode, numToChar(i));
                    temp.clear();
                    continue;
                }
                super.nodeLinker(newNode, maker(getNodeFromSet(temp)), numToChar(i));
            } 
            else {
                String name = nameBuilder(temp);
                Node existingNode = checker(name);
                if(existingNode != null) {
                    super.nodeLinker(newNode, existingNode, numToChar(i));
                    temp.clear();
                    continue;
                }
                Node tempNode = combineMultipleNodes(temp, name, numToChar(i));
                super.nodeLinker(newNode, maker(tempNode), numToChar(i));
            }
            
        }
        return newNode;
    }
}

class linkList {
    private String from;
    private String to;
    private char value;

    public linkList(String first, String second, char third) {
        this.from = first;
        this.to = second;
        this.value = third;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public char getValue() {
        return this.value;
    }
}