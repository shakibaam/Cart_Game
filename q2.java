import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class cart {

    public char color;
    public int number;

    public cart(char color, int number) {
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


class states {

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

    public void printSpace() {

        for (int i = 0; i < GameSpace.size(); i++) {

            for (int j = 0; j < GameSpace.get(i).size(); j++) {

                System.out.print(GameSpace.get(i).get(j));
            }
            System.out.println();

        }

        System.out.println("------------");
    }


}

public class q2 {

    public ArrayList<states> frontier = new ArrayList<>();
    public ArrayList<states> explored = new ArrayList<>();

    states cloning = new states();
    int counter = 0;


    public void expanding(states state) {


        ArrayList tempSpace;
        ArrayList tempSpace1;


        ArrayList<states> newNodes = new ArrayList();


        for (int i = 0; i < state.GameSpace.size(); i++) {


            for (int j = 0; j < state.GameSpace.size(); j++) {


                ArrayList<ArrayList<cart>> stateCopy = new ArrayList<>();

                for (ArrayList<cart> cards : state.GameSpace) {
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


                            String how = card1.number + "" + card1.color + " from " + i + " to " + j;
                            System.out.println("new state reach by: " + how);
                            System.out.println("---------");

                            newSate.howToGet = how;
                            newSate.depth = state.depth + 1;
                            newSate.how = state.how;
                            newSate.how.add(how);
                            newSate.parent = state;
                            frontier.add(counter, newSate);


                            newNodes.add(newSate);

                            newSate.printSpace();
                            counter++;


                        }
                    }

                    if (tempSpace1.size() == 0 && tempSpace.size() != 0) {

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
                        frontier.add(counter, newSate);


                        newNodes.add(newSate);

                        newSate.printSpace();
                        counter++;


                    }


                }


            }


        }

        System.out.println("Expanding this node finish...");
        System.out.println("******");


        state.expand = true;

    }

    public boolean dfs(int colors, int numbers, states initial) {


        frontier.add(initial);
        boolean goal = false;


        if (frontier.isEmpty()) {

            System.out.println("No answer...!");
        }

        states toExpand = frontier.get(0);
        frontier.remove(0);
        explored.add(toExpand);
        if (goalTest(toExpand)) {

            return true;
        } else {
            expanding(toExpand);
            return false;


        }


    }


    public void IDS() {


        int limit = 0;

    }


    public boolean DLS(int limit) {

        boolean goal = false;


        if (limit == 0) {

            if (goalTest(frontier.get(0))) {

                goal = true;
                return true;
            } else return false;
        }

        else {

            boolean superFlag = true;
            while (superFlag) {

                if (frontier.isEmpty()){
                    if (explored.isEmpty()){
                        superFlag=false;

                    }
                    else {

                        for (int i = 0; i < explored.size(); i++) {
                            if (goalTest(explored.get(i))) {
                                return true;
                            }


                        }
                        superFlag=false;
                    }
                }

                states toexpand = frontier.get(0);
                if (toexpand.depth == limit) {


                    boolean stillCutOff = true;
                    while (stillCutOff) {

                        states temp = frontier.get(0);
                        if (temp.depth == limit) {

                            if (goalTest(temp)) {
                                System.out.println("Goal");
                                return true;
                            } else {

                                frontier.remove(0);
                            }
                        } else {

                            states temp1 = explored.get(explored.size() - 1);
                            if (goalTest(temp1)) {

                                System.out.println("Goal");
                                return true;
                            } else {
                                explored.remove(explored.size() - 1);
                            }

                            stillCutOff = false;

                        }
                    }


                } else {

                    expanding(toexpand);
                }

            }
        }

        return  false;
    }

    public boolean goalTest(states state) {

        char color;
//        boolean colSame = true;
        boolean sorted = true;


        for (int i = 0; i < state.GameSpace.size(); i++) {

            if (state.GameSpace.get(i).size() != 0) {
                color = state.GameSpace.get(i).get(0).color;

                for (int j = 1; j < state.GameSpace.get(i).size(); j++) {

                    if (state.GameSpace.get(i).get(j).color != color) {
//                        colSame = false;
                        return false;
                    }


                }

                sorted = isCollectionSorted(state.GameSpace.get(i));
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
        q2 q2 = new q2();
        q2.expanding(initialState);
//        q1.bfs(3, 5, initialState);


    }

}

