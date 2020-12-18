package question2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    ArrayList<states> frontierCopy = new ArrayList();
    ArrayList<states> exploredCopy = new ArrayList();

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
//                            System.out.println("new state reach by: " + how);
//                            System.out.println("---------");

                            newSate.howToGet = how;
                            newSate.depth = state.depth + 1;
                            newSate.how = (ArrayList<String>) (state.how).clone();
                            newSate.how.add(how);
                            newSate.parent = state;

//                            System.out.println("counter: " + counter);

                            frontier.add(counter, newSate);
                            frontierCopy.add(newSate);


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
//                        System.out.println("new state reach by: " + how);
//                        System.out.println("---------");

                        newSate.howToGet = how;
                        newSate.depth = state.depth + 1;
                        newSate.parent = state;
                        newSate.how = (ArrayList<String>) (state.how).clone();
                        newSate.how.add(how);
//                        System.out.println("counter: " + counter);
                        frontier.add(counter, newSate);
                        frontierCopy.add(newSate);
//                        frontierCopy.addAll(frontier);


                        newNodes.add(newSate);

//                        newSate.printSpace();
                        counter++;


                    }


                }


            }


        }

//        System.out.println("Expanding this node finish...");
//        System.out.println("******");


        counter = 0;
        explored.add(state);


        state.expand = true;

    }


    public void IDS(int limit, int numbers, states initial) {

        ArrayList<ArrayList> forOutPut = new ArrayList<>();


        int count = 0;
        while (count <= limit) {
            System.out.println("DLS" + " " + count);
            boolean goal=DLS(count, numbers, initial);
            if (goal){
                return;
            }

            frontier.clear();
            explored.clear();

            frontier.add(initial);
            count++;


        }




    }


    public boolean DLS(int limit, int numbers, states initial) {

        int produce = 0;
        int expand = 0;


        boolean goal = false;


        if (limit == 0) {


            if (goalTest(initial, numbers)) {

                goal = true;
                System.out.println("Goalll");
                System.out.println("Expanded: 0");
                System.out.println("produced: 1");

                return true;
            } else {
                System.out.println("No Goal!!");


                frontier.remove(0);

                return false;

            }


        } else {

            boolean superFlag = true;
            while (superFlag) {


                if (frontier.isEmpty()) {

                    Iterator iterator = explored.iterator();

                    while (iterator.hasNext()) {

                        if (goalTest((states) iterator.next(), numbers)) {
                            return true;
                        } else {

                            iterator.remove();
                        }
                    }

                    superFlag = false;

                    System.out.println("No GOAL Found :(");
//                    System.out.println("exploredcopy: " + exploredCopy.size());
//                    System.out.println("frontiercopy: " + frontierCopy.size());
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

                            if (goalTest(temp, numbers)) {
                                System.out.println("Goal!!");
                                System.out.println("Depth: " + temp.depth);
                                for (int k = 0; k < temp.how.size(); k++) {

                                    System.out.println(temp.how.get(k));
                                }
                                System.out.println("explored: " + exploredCopy.size());
//                                System.out.println("frontiercopy: " + frontierCopy.size());
                                int x = exploredCopy.size() + frontierCopy.size();
                                System.out.println("produced nodes:" + x);


                                temp.printSpace();
                                return true;
                            } else {


                                frontier.remove(0);
                                if (frontier.size() == 0) {


                                    states temp1 = explored.get(explored.size() - 1);

                                    if (goalTest(temp1, numbers)) {

                                        System.out.println("Goal");

                                        System.out.println("Depth: " + temp.depth);
                                        for (int k = 0; k < temp.how.size(); k++) {

                                            System.out.println(temp.how.get(k));
                                        }
                                        System.out.println("explored: " + exploredCopy.size());
//                                System.out.println("frontiercopy: " + frontierCopy.size());
                                        int x = exploredCopy.size() + frontierCopy.size();
                                        System.out.println("produced nodes:" + x);
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

                            if (goalTest(temp1, numbers)) {

                                System.out.println("Goal");
                                System.out.println("explored: " + exploredCopy.size());
//                                System.out.println("frontiercopy: " + frontierCopy.size());
                                System.out.println("produced nodes:" + exploredCopy.size() + frontierCopy.size());
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
                    frontierCopy.remove(toexpand);



                    expanding(toexpand);
                    exploredCopy.add(toexpand);

                    frontierCopy.remove(toexpand);


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

//            if (state.GameSpace.get(i).size() != 0 && state.GameSpace.get(i).size() != numbers) {
//                return false;
//            }

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


        states initialState = new states();
        initialState.GameSpace = space;

        initialState.initial = true;
        initialState.depth = 0;
        q2 q2 = new q2();


        q2.frontier.add(initialState);

        q2.IDS(5, numbers, initialState);


    }

}

