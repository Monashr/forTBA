public class Main {
    public static void main(String[] args) {

        nodeBasket basket = new nodeBasket();

        basket.nodeInsert("p1", State.S);
        basket.nodeInsert("p2", State.N);
        basket.nodeInsert("p3", State.N);
        basket.nodeInsert("p4", State.A);
        basket.nodeInsert("p5", State.N);
        basket.nodeInsert("p6", State.N);




        basket.linkNode("p1", "p2", 'e');
        basket.linkNode("p2", "p3", '0');
        basket.linkNode("p2", "p1", '0');
        basket.linkNode("p3", "p4", '1');
        basket.linkNode("p1", "p5", '0');
        basket.linkNode("p5", "p6", '1');


        basket.cari("01");




    }
}
