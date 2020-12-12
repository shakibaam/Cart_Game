import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


  class card {

    public char color;
    public int number;

    public card(char color, int number) {
        this.color = color;
        this.number = number;
    }

    @Override
    public String toString() {
        return "card{" +
                "color=" + color +
                ", number=" + number +
                '}';
    }
}


class state {

    public ArrayList<ArrayList<cart>> GameSpace = new ArrayList<>();
    public boolean expand = false;
    public boolean initial = false;
    public String howToGet;
    public ArrayList<String> how = new ArrayList<>();
    int depth;
    states parent;


    public void setGameSpace(ArrayList<ArrayList<cart>> gameSpace) {
        GameSpace = gameSpace;
    }

    public ArrayList getspecificSpace(int index) {

        return GameSpace.get(index);

    }

    public ArrayList<Integer> getNumbers(int index) {

        ArrayList<cart> temp = GameSpace.get(index);
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < temp.size(); i++) {

            numbers.add(temp.get(i).number);
        }

        return numbers;
    }

    public  void printSpace(){

        for (int i = 0; i <GameSpace.size() ; i++) {

            for (int j = 0; j <GameSpace.get(i).size() ; j++) {

                System.out.print(GameSpace.get(i).get(j));
            }
            System.out.println();

        }

        System.out.println("------------");
    }


}

public class q1 {

    public ArrayList<states> frontier = new ArrayList<>();
    public ArrayList<states> explored = new ArrayList<>();

    states cloning=new states();


    public boolean expanding(states state) {


        ArrayList tempSpace;
        ArrayList tempSpace1;


//        Iterator<ArrayList<card>> iterator = state.GameSpace.iterator();
//
//        while(iterator.hasNext())
//        {
//            //Add the object clones
//            stateCopy.add((ArrayList<card>) iterator.next().clone());
//        }

        ArrayList<states> newNodes = new ArrayList();




        for (int i = 0; i < state.GameSpace.size(); i++) {




            for (int j = 0; j < state.GameSpace.size(); j++) {


                ArrayList<ArrayList<cart>> stateCopy = new ArrayList<>();

                for(ArrayList<cart> cards : state.GameSpace) {
                    stateCopy.add((ArrayList<cart>) cards.clone());
                }
                cloning.setGameSpace(state.GameSpace);



                if (j != i) {
                    tempSpace1 = state.GameSpace.get(j);
                    tempSpace = state.GameSpace.get(i);

                    if (tempSpace1.size() != 0 && tempSpace.size() != 0) {


                        int x = tempSpace.size() - 1;
                        cart card1 = (cart) tempSpace.get(x);


                        cart card2 = (cart) tempSpace1.get(tempSpace1.size() - 1);


                        if (card1.number < card2.number) {

                            states newSate = new states();

                            stateCopy.get(i).remove(card1);
                            stateCopy.get(j).add(card1);
                            newSate.setGameSpace(stateCopy);

//                            cloning.GameSpace.get(i).remove(card1);
//                            cloning.GameSpace.get(j).add(card1);
//                            newSate.setGameSpace(cloning.GameSpace);


//                            state.GameSpace.get(i).add(card1);
//                            state.GameSpace.get(j).remove(card1);


                            String how = card1.number + "" + card1.color + " from " + i + " to " + j;
                            System.out.println("new state reach by: " + how);
                            System.out.println("---------");
                            newSate.howToGet = how;
                            newSate.depth = state.depth + 1;
                            newSate.how = state.how;
                            newSate.how.add(how);
                            newSate.parent = state;
                            frontier.add(newSate);
                            newNodes.add(newSate);

//                            newSate.printSpace();

                            if (goalTest(newSate)){

                                System.out.println("Goal here!!");
                                System.out.println("Depth of answer: " + newSate.depth);
                                for (int k = 0; k < newSate.how.size(); k++) {

                                    System.out.println(newSate.how.get(k));
                                }

                                int produced = frontier.size() + explored.size();

                                System.out.println("produced nodes:" + produced);
                                System.out.println("Expanded nodes: " + explored.size());

                                return  true;
                            }

                            else {
                                System.out.println("Not Goal Yet :(");
                            }




                        }
                    }

                    if (tempSpace1.size() == 0 && tempSpace.size()!=0) {

                        cart card1 = (cart) tempSpace.get(tempSpace.size() - 1);

                        states newSate = new states();

                        stateCopy.get(i).remove(card1);
                        stateCopy.get(j).add(card1);



                        newSate.setGameSpace(stateCopy);


                        String how = card1.number + "" + card1.color + " from " + i + " to " + j;
                        System.out.println("new state reach by: " + how);
                        System.out.println("---------");
                        newSate.howToGet = how;
                        newSate.depth = state.depth + 1;
                        newSate.parent = state;
                        newSate.how = state.how;
                        newSate.how.add(how);
                        frontier.add(newSate);
                        newNodes.add(newSate);

//                        newSate.printSpace();

                        if (goalTest(newSate)){

                            System.out.println("Goal here!!");
                            System.out.println("Depth of answer: " + newSate.depth);
                            for (int k = 0; k < newSate.how.size(); k++) {

                                System.out.println(newSate.how.get(k));
                            }

                            int produced = frontier.size() + explored.size();

                            System.out.println("produced nodes:" + produced);
                            System.out.println("Expanded nodes: " + explored.size());

                            return  true;
                        }

                        else {
                            System.out.println("Not Goal Yet :(");
                        }


                    }



                }



            }


        }

        System.out.println("Expanding this node finish...");
        System.out.println("******");


        state.expand = true;

        return  false;

    }

    public void bfs( states initial) {


        frontier.add(initial);
        boolean goal = false;
        boolean temp=false;

        for (int i = 0; i <6 ; i++) {


//        while (!goal) {

            if (frontier.isEmpty()) {

                System.out.println("No answer...!");
            }

            states toExpand = frontier.get(0);
            frontier.remove(0);
            explored.add(toExpand);


            temp = expanding(toExpand);
            System.out.println(frontier.size());
            if (temp == true) {

                goal = true;
                System.out.println("algorithm finish...");
            }

        }
//        }
//            if (goalTest(toExpand)) {
//                goal = true;
//                System.out.println("Goal!!!");
//                System.out.println("Depth of answer: " + toExpand.depth);
//                for (int i = 0; i < toExpand.how.size(); i++) {
//
//                    System.out.println(toExpand.how.get(i));
//                }
//
//                int produced = frontier.size() + explored.size();
//
//                System.out.println("produced nodes:" + produced);
//                System.out.println("Expanded nodes: " + explored.size());


//            } else {





//            }



    }

    public boolean goalTest(states state) {

        char color;

        boolean sorted = true;


        for (int i = 0; i < state.GameSpace.size(); i++) {

            if (state.GameSpace.get(i).size() != 0) {
                color = state.GameSpace.get(i).get(0).color;

                for (int j = 1; j < state.GameSpace.get(i).size(); j++) {

                    if (state.GameSpace.get(i).get(j).color != color) {

                        return false;
                    }


                }

                sorted = isCollectionSorted(state.getNumbers(i));
                if (sorted == false) {
                    return false;
                }

            }

        }

        return true;

//        int i = 0;
//        int counter = 0;
//
//        for (int k = 0; k < state.GameSpace.size(); k++) {
//
//            ArrayList<card> temp = state.GameSpace.get(k);
//            if (isSorted(temp)) {
//
//                counter++;
//
//                if (counter == numbers) {
//
//                    return true;
//                }
//            }
//
//
//        }
//
//        return false;


    }

//    boolean isSorted(ArrayList<card> array) {
//        for (int i = 0; i < array.size() - 1; i++) {
//            if ((array.get(i).number < array.get(i + 1).number)) {
//
//
//                return false;
//
//            }
//
//
//            if (array.get(i).color != array.get(i + 1).color) {
//
//                return false;
//            }
//
//
//        }
//
//
//        return true;
//    }

    public boolean isCollectionSorted(ArrayList list) {
        ArrayList copy = new ArrayList(list);
        Collections.sort(copy, Collections.reverseOrder());

        return copy.equals(list);
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        int k=scanner.nextInt();
//        int colors=scanner.nextInt();
//        int numbers=scanner.nextInt();
        String card = "";


        ArrayList<cart> k1 = new ArrayList();
        card = "5g";
        int number = Integer.parseInt(String.valueOf(card.charAt(0)));
        char color = card.charAt(1);
        cart g5 = new cart(color, number);


        card = "5r";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart r5 = new cart(color, number);

        card = "4y";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart y4 = new cart(color, number);

        k1.add(g5);
        k1.add(r5);
        k1.add(y4);

        ArrayList<cart> k2 = new ArrayList();

        card = "2g";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart g2 = new cart(color, number);

        card = "4r";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart r4 = new cart(color, number);


        card = "3y";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart y3 = new cart(color, number);

        card = "3g";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart g3 = new cart(color, number);

        card = "2y";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart y2 = new cart(color, number);

        k2.add(g2);
        k2.add(r4);
        k2.add(y3);
        k2.add(g3);
        k2.add(y2);

        ArrayList<cart> k3 = new ArrayList();

        card = "1y";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart y1 = new cart(color, number);

        card = "4g";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart g4 = new cart(color, number);

        card = "1r";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart r1 = new cart(color, number);

        k3.add(y1);
        k3.add(g4);
        k3.add(r1);

        ArrayList<cart> k4 = new ArrayList();


        card = "1g";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart g1 = new cart(color, number);

        card = "2r";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart r2 = new cart(color, number);

        card = "5y";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart y5 = new cart(color, number);

        card = "3r";
        number = Integer.parseInt(String.valueOf(card.charAt(0)));
        color = card.charAt(1);
        cart r3 = new cart(color, number);

        k4.add(g1);
        k4.add(r2);
        k4.add(y5);
        k4.add(r3);

        ArrayList<cart> k5 = new ArrayList();

        states initialState = new states();
        initialState.GameSpace.add(k1);
        initialState.GameSpace.add(k2);
        initialState.GameSpace.add(k3);
        initialState.GameSpace.add(k4);
        initialState.GameSpace.add(k5);
        initialState.initial = true;
        initialState.depth = 0;
        q1 q1 = new q1();
//        q1.expanding(initialState);
        q1.bfs( initialState);


    }

}
