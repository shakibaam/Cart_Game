package question3;

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
        return number + "" + color;
    }
}


class state {

    public ArrayList<ArrayList<cart>> GameSpace = new ArrayList<>();
    public boolean expand = false;
    public boolean initial = false;
    public String howToGet;
    public ArrayList<String> how = new ArrayList<>();
    int depth;
    state parent;
    int cost;


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

    public ArrayList<Character> getColors(int index) {

        ArrayList<cart> temp = GameSpace.get(index);
        ArrayList<Character> colors = new ArrayList<>();

        for (int i = 0; i < temp.size(); i++) {

            colors.add(temp.get(i).color);
        }

        return colors;


    }

    public void printSpace() {

        for (int i = 0; i < GameSpace.size(); i++) {

            if (GameSpace.get(i).size() == 0) {
                System.out.println("#");
            } else {

                for (int j = 0; j < GameSpace.get(i).size(); j++) {

                    System.out.print(GameSpace.get(i).get(j) + " ");
                }
                System.out.println();

            }
        }

        System.out.println("------------");
    }
//
//    public int calculateH(int numbers) {
//
//        int h = 0;
//
//        ArrayList<Integer> nums = new ArrayList();
//        for (int i = numbers; i >= 1; i--) {
//            nums.add(i);
//        }
//
//        for (int i = 0; i < GameSpace.size(); i++) {
//
//            if (GameSpace.get(i).size() != 0 && GameSpace.get(i).size() != 1 ) {
//
//                if (GameSpace.get(i).get(0).number != nums.get(0)) {
//                    h += GameSpace.get(i).size();
//                } else {
//
//                    char color = GameSpace.get(i).get(0).color;
//                    int x = 1;
//                    boolean flag = true;
//                    while (flag) {
//
//                        if (GameSpace.get(i).get(x).color == color) {
//                            if (GameSpace.get(i).get(x).number == nums.get(x)) {
//                                x++;
//                                if (x == GameSpace.get(i).size()) {
//                                    flag = false;
//                                }
//
//
//                            } else {
//                                h += (GameSpace.get(i).size() - x);
//                                flag = false;
//                            }
//                        } else {
//                            h += (GameSpace.get(i).size() - x);
//                            flag = false;
//                        }
//
//                    }
//
//                }
//            }
//        }
//
//
//        return h;
//
//
//    }

    public int H(int numbers){
        int h=0;
        boolean flag=true;

        for (int i = 0; i < GameSpace.size(); i++) {

            if (GameSpace.get(i).size() != 0 && GameSpace.get(i).size() != 1 ) {

                int temp=1;
                while (flag){
//                    System.out.println(GameSpace.get(i).size());

                    if (GameSpace.get(i).get(temp).color!= GameSpace.get(i).get(temp-1).color){


                        h+=(GameSpace.get(i).size()-temp);
                       flag=false;


                    }


                    if (GameSpace.get(i).get(temp).number> GameSpace.get(i).get(temp-1).number){
//                        System.out.println(GameSpace.get(i).get(temp).number+"  "+ GameSpace.get(i).get(temp-1).number);
                        h+=(GameSpace.get(i).size()-(temp-1));
                        flag=false;


                    }
                    temp++;
                    if (temp==GameSpace.get(i).size()){
                        flag=false;
                    }



                }
            }
            flag=true;

        }
        System.out.println(h);
        return h;

    }

    public int calculateF(int numbers) {

        return cost + H(numbers);
    }


}

public class q3 {


    public ArrayList<state> frontier = new ArrayList<>();
    public ArrayList<state> explored = new ArrayList<>();

    public boolean expanding(state state, int numbers) {


        ArrayList tempSpace;
        ArrayList tempSpace1;


        ArrayList<state> newNodes = new ArrayList();


        for (int i = 0; i < state.GameSpace.size(); i++) {


            for (int j = 0; j < state.GameSpace.size(); j++) {


                ArrayList<ArrayList<cart>> stateCopy = new ArrayList<>();

                for (ArrayList<cart> cards : state.GameSpace) {
                    stateCopy.add((ArrayList<cart>) cards.clone());
                }


                if (j != i) {
                    tempSpace1 = state.GameSpace.get(j);
                    tempSpace = state.GameSpace.get(i);

                    if (tempSpace1.size() != 0 && tempSpace.size() != 0) {


                        int x = tempSpace.size() - 1;
                        cart card1 = (cart) tempSpace.get(x);


                        cart card2 = (cart) tempSpace1.get(tempSpace1.size() - 1);


                        if (card1.number < card2.number) {

                            state newSate = new state();

                            stateCopy.get(i).remove(card1);
                            stateCopy.get(j).add(card1);
                            newSate.setGameSpace(stateCopy);


                            String how = card1.number + "" + card1.color + " from " + i + " to " + j;
                            System.out.println("new state reach by: " + how);
                            System.out.println("---------");
                            newSate.howToGet = how;
                            newSate.depth = state.depth + 1;

                            newSate.how = (ArrayList<String>) (state.how).clone();
                            newSate.how.add(newSate.howToGet);
                            newSate.cost = state.cost + 1;


                            newSate.parent = state;
                            newSate.printSpace();

                            if (!redundant(newSate)) {

                                frontier.add(newSate);
                                newNodes.add(newSate);
                            } else {

                                System.out.println("Sorry this node is available");
                            }

//                            newSate.printSpace();

                            if (goalTest(newSate, numbers)) {

                                System.out.println("Goal here!!");
                                newSate.printSpace();
                                System.out.println("Depth of answer: " + newSate.depth);
                                System.out.println(newSate.how.size());
                                for (int k = 0; k < newSate.how.size(); k++) {

                                    System.out.println(newSate.how.get(k));
                                }

                                int produced = frontier.size() + explored.size();

                                System.out.println("produced nodes:" + produced);
                                System.out.println("Expanded nodes: " + explored.size());
                                System.out.println("Frontier size : " + frontier.size());

                                return true;
                            } else {
                                System.out.println("Not Goal Yet :(");
                            }


                        }
                    }

                    if (tempSpace1.size() == 0 && tempSpace.size() != 0) {

                        cart card1 = (cart) tempSpace.get(tempSpace.size() - 1);

                        state newSate = new state();

                        stateCopy.get(i).remove(card1);
                        stateCopy.get(j).add(card1);


                        newSate.setGameSpace(stateCopy);


                        String how = card1.number + "" + card1.color + " from " + i + " to " + j;
                        System.out.println("new state reach by: " + how);
                        System.out.println("---------");
                        newSate.howToGet = how;
                        newSate.depth = state.depth + 1;
                        newSate.parent = state;
                        newSate.how = (ArrayList<String>) (state.how).clone();
                        newSate.how.add(how);
                        newSate.cost = state.cost + 1;
                        newSate.printSpace();

                        if (!redundant(newSate)) {

                            frontier.add(newSate);
                            newNodes.add(newSate);
                        } else {

                            System.out.println("Sorry this node is available");
                        }

//                        newSate.printSpace();

                        if (goalTest(newSate, numbers)) {

                            System.out.println("Goal here!!");
                            System.out.println("Depth of answer: " + newSate.depth);
                            System.out.println(newSate.how.size());
                            for (int k = 0; k < newSate.how.size(); k++) {

                                System.out.println(newSate.how.get(k));
                            }

                            int produced = frontier.size() + explored.size();

                            System.out.println("produced nodes:" + produced);
                            System.out.println("Expanded nodes: " + explored.size());
                            System.out.println("Frontier size : " + frontier.size());

                            return true;
                        } else {
                            System.out.println("Not Goal Yet :(");
                        }


                    }


                }


            }


        }

        System.out.println("Expanding this node finish...");
        System.out.println("******");


        state.expand = true;

        return false;

    }

    public void AStar(state state, int numbers) {

        boolean goal = false;
        while (!goal) {

            state toExpand = findMinF(frontier, numbers);


            frontier.remove(toExpand);


            explored.add(toExpand);

            System.out.println("Going to Expand: ");
            toExpand.printSpace();


            boolean temp = expanding(toExpand, numbers);

            if (temp == true) {

                goal = true;
                System.out.println("algorithm finish...");
            }

            System.out.println("Frontier: " + frontier.size());

        }


    }

    public state findMinF(ArrayList<state> frontier, int numbers) {

        state temp = frontier.get(0);
        System.out.println(frontier.get(0).calculateF(numbers));
        for (int i = 1; i < frontier.size(); i++) {
            System.out.println(frontier.get(i).calculateF(numbers));

            if (frontier.get(i).calculateF(numbers) <= temp.calculateF(numbers)) {
                temp = frontier.get(i);
            }
        }

        return temp;

    }

    public boolean redundant(state state) {

        for (int i = 0; i < frontier.size(); i++) {

            if (statesEqual(frontier.get(i), state)) {
                return true;
            }
        }
        for (int i = 0; i < explored.size(); i++) {

            if (statesEqual(explored.get(i), state)) {
                return true;
            }

        }

        return false;
    }

    public boolean statesEqual(state state1, state state2) {


        for (int i = 0; i < state1.GameSpace.size(); i++) {


            if ((!state1.getNumbers(i).equals(state2.getNumbers(i))) || (!state1.getColors(i).equals(state2.getColors(i)))) {
                return false;
            }

        }


        return true;
    }


    public boolean goalTest(state state, int numbers) {

        char color;

        boolean sorted = true;


        for (int i = 0; i < state.GameSpace.size(); i++) {

            if (state.GameSpace.get(i).size() != 0 && state.GameSpace.get(i).size() != numbers) {
                return false;
            }

            if (state.GameSpace.get(i).size() != 0) {

                color = state.GameSpace.get(i).get(0).color;

                for (int j = 1; j < state.GameSpace.get(i).size(); j++) {

                    if ((state.GameSpace.get(i).get(j).color != color)) {

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


    }


    public boolean isCollectionSorted(ArrayList list) {
        ArrayList copy = new ArrayList(list);
        Collections.sort(copy, Collections.reverseOrder());

        return copy.equals(list);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int colors = scanner.nextInt();
        int numbers = scanner.nextInt();
        ArrayList input = new ArrayList();
        ArrayList<ArrayList<cart>> space = new ArrayList<>();

        for (int i = 0; i <= k; i++) {


            String str = scanner.nextLine();
            input.add(str);

        }
        input.remove(0);


        for (int i = 0; i < k; i++) {

            String temp = (String) input.get(i);

            ArrayList<cart> spacei = new ArrayList();

            String[] splited = temp.split("\\s+");

            for (int j = 0; j < splited.length; j++) {

                String card = splited[j];
                if (!card.equals("#")) {
                    int number = Integer.parseInt(String.valueOf(card.charAt(0)));
                    char color = card.charAt(1);
                    cart cart = new cart(color, number);
                    spacei.add(cart);
                }


            }
            space.add(spacei);

        }


        state initialState = new state();
        initialState.GameSpace = space;

        initialState.initial = true;
        initialState.depth = 0;
        initialState.cost = 0;
        q3 q3 = new q3();
        q3.frontier.add(initialState);
//        q3.AStar(initialState, numbers);
        initialState.H(numbers);
    }


}
