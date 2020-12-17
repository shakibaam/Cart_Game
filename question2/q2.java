package question2;

import javax.swing.*;
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
        return number+""+color;
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
                            newSate.how = (ArrayList<String>) (state.how).clone();
                            newSate.how.add(how);
                            newSate.parent = state;

                            System.out.println("counter: " + counter);

                            frontier.add(counter, newSate);


                            newNodes.add(newSate);

//                            newSate.printSpace();
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
                        newSate.how = (ArrayList<String>) (state.how).clone();
                        newSate.how.add(how);
                        System.out.println("counter: " + counter);
                        frontier.add(counter, newSate);


                        newNodes.add(newSate);

//                        newSate.printSpace();
                        counter++;


                    }


                }


            }


        }

        System.out.println("Expanding this node finish...");
        System.out.println("******");
        System.out.println("Frontier: ");
        for (int i = 0; i <frontier.size() ; i++) {
            frontier.get(i).printSpace();
        }
        counter = 0;
        explored.add(state);


        state.expand = true;

    }

//    public boolean dfs(int colors, int numbers, states initial) {
//
//
//        frontier.add(initial);
//        boolean goal = false;
//
//
//        if (frontier.isEmpty()) {
//
//            System.out.println("No answer...!");
//        }
//
//        states toExpand = frontier.get(0);
//        frontier.remove(0);
//        explored.add(toExpand);
//        if (goalTest(toExpand)) {
//
//            return true;
//        } else {
//            expanding(toExpand);
//            return false;
//
//
//        }
//
//
//    }


    public boolean IDS(int limit , int numbers) {

        for (int i = 0; i <= limit; i++) {

            System.out.println("DLS: " + i);

            if (DLS(i , numbers)) {

                System.out.println("Yaaay!!");
                return true;
            }

        }

        return false;

    }


    public boolean DLS(int limit , int numbers) {

        boolean goal = false;


        if (limit == 0) {

            if (goalTest(frontier.get(0) ,numbers)) {

                goal = true;
                return true;
            } else return false;
        } else {

            boolean superFlag = true;
            while (superFlag) {


                if (frontier.isEmpty()) {


                    for (int i = 0; i < explored.size(); i++) {
                        if (goalTest(explored.get(i) , numbers)) {
                            return true;
                        } else {

                            explored.remove(i);
                        }
                    }
                    superFlag = false;

                    System.out.println("No GOAL Found :(");
                    return false;


                }

                states toexpand = frontier.get(0);
//                System.out.println("going to expand: ");
//                toexpand.printSpace();
                if (toexpand.depth == limit) {




                    boolean stillCutOff = true;
                    while (stillCutOff) {


                        states temp = frontier.get(0);

                        if (temp.depth == limit) {

                            if (goalTest(temp ,numbers)) {
                                System.out.println("Goal!!");
                                temp.printSpace();
                                return true;
                            } else {


                                frontier.remove(0);
                                if (frontier.size() == 0) {


                                    states temp1 = explored.get(explored.size() - 1);

                                    if (goalTest(temp1 ,  numbers)) {

                                        System.out.println("Goal");
                                        temp1.printSpace();
                                        return true;
                                    } else {
                                        explored.remove(explored.size() - 1);
                                    }


                                    stillCutOff = false;

                                }
                            }
                        } else {



                            states temp1 = explored.get(explored.size() - 1);

                            if (goalTest(temp1 ,  numbers)) {

                                System.out.println("Goal");
                                temp1.printSpace();
                                return true;
                            } else {
                                explored.remove(explored.size() - 1);
                            }


                            stillCutOff = false;



                        }
                    }


                } else {

                    frontier.remove(0);

                    expanding(toexpand);
                }

            }
        }

        System.out.println("No GOAL Found :(");


        return false;
    }


    public boolean goalTest(states state, int numbers) {

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
                int number = Integer.parseInt(String.valueOf(card.charAt(0)));
                char color = card.charAt(1);
                cart cart = new cart(color, number);
                spacei.add(cart);


            }
            space.add(spacei);

        }



        states initialState = new states();
        initialState.GameSpace=space;

        initialState.initial = true;
        initialState.depth = 0;
        q2 q2 = new q2();
        q2.frontier.add(initialState);
        q2.DLS(2,numbers);

//
//        String card = "";
//
//
//        ArrayList<cart> k1 = new ArrayList();
//        card = "5g";
//        int number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        char color = card.charAt(1);
//        cart g5 = new cart(color, number);
//
//
//        card = "5r";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart r5 = new cart(color, number);
//
//        card = "4y";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart y4 = new cart(color, number);
//
//        k1.add(g5);
//        k1.add(r5);
//        k1.add(y4);
//
//        ArrayList<cart> k2 = new ArrayList();
//
//        card = "2g";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart g2 = new cart(color, number);
//
//        card = "4r";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart r4 = new cart(color, number);
//
//
//        card = "3y";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart y3 = new cart(color, number);
//
//        card = "3g";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart g3 = new cart(color, number);
//
//        card = "2y";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart y2 = new cart(color, number);
//
//        k2.add(g2);
//        k2.add(r4);
//        k2.add(y3);
//        k2.add(g3);
//        k2.add(y2);
//
//        ArrayList<cart> k3 = new ArrayList();
//
//        card = "1y";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart y1 = new cart(color, number);
//
//        card = "4g";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart g4 = new cart(color, number);
//
//        card = "1r";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart r1 = new cart(color, number);
//
//        k3.add(y1);
//        k3.add(g4);
//        k3.add(r1);
//
//        ArrayList<cart> k4 = new ArrayList();
//
//
//        card = "1g";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart g1 = new cart(color, number);
//
//        card = "2r";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart r2 = new cart(color, number);
//
//        card = "5y";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart y5 = new cart(color, number);
//
//        card = "3r";
//        number = Integer.parseInt(String.valueOf(card.charAt(0)));
//        color = card.charAt(1);
//        cart r3 = new cart(color, number);
//
//        k4.add(g1);
//        k4.add(r2);
//        k4.add(y5);
//        k4.add(r3);
//
//        ArrayList<cart> k5 = new ArrayList();

//        states initialState = new states();
//        initialState.GameSpace.add(k1);
//        initialState.GameSpace.add(k2);
//        initialState.GameSpace.add(k3);
//        initialState.GameSpace.add(k4);
//        initialState.GameSpace.add(k5);
//        initialState.initial = true;
//        initialState.depth = 0;
//        q2 q2 = new q2();
//        q2.frontier.add(initialState);



    }

}

