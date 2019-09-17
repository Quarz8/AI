// Course:			CS4242
// Name:			Warren Davis
// Student ID:		000545250
// Assignment #:	#1
// Due Date:		03/06/2019

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class EightPuzzle
{
    // Goal puzzle state (' ' represents empty space)
    public static final char[][] goal =
    {
            { '1', '2', '3' },
            { '8', ' ', '4' },
            { '7', '6', '5' } };

    // Returns true if state is same as goal
    public static boolean isGoal(char[][] state)
    {

        for (int row = 0; row < state.length; row++)
        {
            for (int col = 0; col < state[row].length; col++)
            {
                if (state[row][col] != goal[row][col])
                    return false;
            }
        }
        return true;
    }

    // Finds empty tile and moves it right
    public static void blankRight(char[][] state)
    {
        for (int row = 0; row < state.length; row++)
        {
            for (int col = 0; col < state[row].length - 1; col++)
            {
                // Swap empty tile and tile to its right
                if (state[row][col] == ' ')
                {
                    state[row][col] = state[row][col + 1];
                    state[row][col + 1] = ' ';
                    return;
                }
            }
        }
    }

    // Finds empty tile and moves it left
    public static void blankLeft(char[][] state)
    {
        for (int row = 0; row < state.length; row++)
        {
            for (int col = 1; col < state[row].length; col++)
            {
                // Swap empty tile and tile to its left
                if (state[row][col] == ' ')
                {
                    state[row][col] = state[row][col - 1];
                    state[row][col - 1] = ' ';
                    return;
                }
            }
        }
    }

    // Finds empty tile and moves it up
    public static void blankUp(char[][] state)
    {
        for (int row = 1; row < state.length; row++)
        {
            for (int col = 0; col < state[row].length; col++)
            {
                // Swap empty tile and tile above it
                if (state[row][col] == ' ')
                {
                    state[row][col] = state[row - 1][col];
                    state[row - 1][col] = ' ';
                    return;
                }
            }
        }
    }

    // Finds empty tile and moves it down
    public static void blankDown(char[][] state)
    {
        for (int row = 0; row < state.length - 1; row++)
        {
            for (int col = 0; col < state[row].length; col++)
            {
                // Swap empty tile and tile below it
                if (state[row][col] == ' ')
                {
                    state[row][col] = state[row + 1][col];
                    state[row + 1][col] = ' ';
                    return;
                }
            }
        }
    }

    // Copies values of src puzzle to dst puzzle (assumes square matrix)
    public static void copyPuzzle(char[][] src, char[][] dst)
    {
        for (int i = 0; i < dst.length && i < src.length; i++)
        {
            char[] aSrc = src[i];
            System.arraycopy(aSrc, 0, dst[i], 0, src.length);
        }
    }

    /** Main method */
    public static void main(String[] args)
    {

        // Initial puzzle state (' ' represents empty space)
        // Edit to try different problems
        char[][] puzzle =
        {
                { '2', '8', '3' },
                { '1', '6', '4' },
                { '7', ' ', '5' } };

        Node start = new Node(puzzle);

        /** A* Algorithm implementation starts here */
        // Override to compare Nodes in priorityQueues using their F values
        // (lowest f value at head of queue)
        Comparator<Node> fValueComparator = new Comparator<Node>()
        {
            @Override
            public int compare(Node n1, Node n2)
            {
                return n1.getF() - n2.getF();
            }
        };

        // Priority Queue, lowest f value at front of queue
        // Holds nodes available for expansion
        PriorityQueue<Node> openList = new PriorityQueue<Node>(fValueComparator);
        // Iterator to search openList
        Iterator<Node> openIterator = openList.iterator();
        // Holds nodes that have been expanded
        ArrayList<Node> closedList = new ArrayList<Node>();
        // Iterator to search closedList
        Iterator<Node> closedIterator = closedList.iterator();

        // Will store goalNode when it is found
        Node goalNode = null;

        openList.add(start); // add starting node to openList

        // check if start is already goal state
        if (isGoal(start.getPuzzleState()))
        {
            goalNode = start;
        }
        else
            while (!openList.isEmpty()) // while openList is not empty...
            {
                // Find lowest f value node in openList (q)
                Node q = openList.poll(); // set q to first Node in openList pQueue

                // Generate states of children of q as 2D arrays
                char[][] upArray = new char[3][3];
                char[][] downArray = new char[3][3];
                char[][] leftArray = new char[3][3];
                char[][] rightArray = new char[3][3];
                // Copy parent node's puzzleState into each child array
                copyPuzzle(q.getPuzzleState(), upArray);
                copyPuzzle(q.getPuzzleState(), downArray);
                copyPuzzle(q.getPuzzleState(), leftArray);
                copyPuzzle(q.getPuzzleState(), rightArray);
                // Edit each child array with blankUp, blankDown, etc.
                blankUp(upArray);
                blankDown(downArray);
                blankLeft(leftArray);
                blankRight(rightArray);
                // Create child nodes with edited arrays and parent node q
                Node upChild = new Node(upArray, q);
                Node downChild = new Node(downArray, q);
                Node leftChild = new Node(leftArray, q);
                Node rightChild = new Node(rightArray, q);

                /** Creating upChild */
                // if the blank actually moved, i.e. state has changed...
                if (!Arrays.deepEquals(upChild.getPuzzleState(), q.getPuzzleState()))
                {
                    // if goal state is reached, set goalNode and stop search
                    if (isGoal(upChild.getPuzzleState()))
                    {

                        goalNode = upChild;
                        break;
                    }

                    // Set iterators
                    openIterator = openList.iterator();
                    closedIterator = closedList.iterator();

                    boolean found = false; // was this state already found?

                    // Holds next() node from respective iterator
                    Node openElement;
                    Node closedElement;

                    // If state is already in openList or closed list, skip this child
                    while (openIterator.hasNext() || closedIterator.hasNext())
                    { // searching openList for same state with lower f value
                        if (openIterator.hasNext())
                        {
                            openElement = openIterator.next();

                            if (Arrays.deepEquals(openElement.getPuzzleState(), upChild.getPuzzleState())
                                    && openElement.getF() <= upChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                        // searching closedList for same state with lower f value
                        if (closedIterator.hasNext())
                        {
                            closedElement = closedIterator.next();
                            if (Arrays.deepEquals(closedElement.getPuzzleState(), upChild.getPuzzleState())
                                    && closedElement.getF() <= upChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                    }
                    // if upChild's state was not already in openList or closedList
                    // with a <= f value, add it to openList
                    if (!found)
                        openList.add(new Node(upChild.getPuzzleState(), q));
                }

                /** Creating downChild */
                // if the blank actually moved, i.e. state has changed...
                if (!Arrays.deepEquals(downChild.getPuzzleState(), q.getPuzzleState()))
                {
                    // if goal state is reached, set goalNode and stop search
                    if (isGoal(downChild.getPuzzleState()))
                    {

                        goalNode = downChild;
                        break;
                    }

                    // Set iterators
                    openIterator = openList.iterator();
                    closedIterator = closedList.iterator();

                    boolean found = false; // was this state already found?

                    // Holds next() node from respective iterator
                    Node openElement;
                    Node closedElement;

                    // If state is already in openList or closed list, skip this child
                    while (openIterator.hasNext() || closedIterator.hasNext())
                    {// searching openList for same state with lower f value
                        if (openIterator.hasNext())
                        {
                            openElement = openIterator.next();
                            if (Arrays.deepEquals(openElement.getPuzzleState(), downChild.getPuzzleState())
                                    && openElement.getF() <= downChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                        // searching closedList for same state with lower f value
                        if (closedIterator.hasNext())
                        {
                            closedElement = closedIterator.next();
                            if (Arrays.deepEquals(closedElement.getPuzzleState(), downChild.getPuzzleState())
                                    && closedElement.getF() <= downChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                    }
                    // if downChild's state was not already in openList or closedList
                    // with a <= f value, add it to openList
                    if (!found)
                        openList.add(new Node(downChild.getPuzzleState(), q));
                }

                /** Creating leftChild */
                // if the blank actually moved, i.e. state has changed...
                if (!Arrays.deepEquals(leftChild.getPuzzleState(), q.getPuzzleState()))
                {
                    // if goal state is reached, set goalNode and stop search
                    if (isGoal(leftChild.getPuzzleState()))
                    {

                        goalNode = leftChild;
                        break;
                    }

                    // Set iterators
                    openIterator = openList.iterator();
                    closedIterator = closedList.iterator();

                    boolean found = false; // was this state already found?

                    // Holds next() node from respective iterator
                    Node openElement;
                    Node closedElement;

                    // If state is already in openList or closed list, skip this child
                    while (openIterator.hasNext() || closedIterator.hasNext())
                    {// searching openList for same state with lower f value
                        if (openIterator.hasNext())
                        {
                            openElement = openIterator.next();
                            if (Arrays.deepEquals(openElement.getPuzzleState(), leftChild.getPuzzleState())
                                    && openElement.getF() <= leftChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                        if (closedIterator.hasNext())
                        {
                            closedElement = closedIterator.next();
                            if (Arrays.deepEquals(closedElement.getPuzzleState(), leftChild.getPuzzleState())
                                    && closedElement.getF() <= leftChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                    }
                    // if leftChild's state was not already in openList or closedList
                    // with a <= f value, add it to openList
                    if (!found)
                        openList.add(new Node(leftChild.getPuzzleState(), q));
                }

                /** Creating rightChild */
                // if the blank actually moved, i.e. state has changed...
                if (!Arrays.deepEquals(rightChild.getPuzzleState(), q.getPuzzleState()))
                {
                    // if goal state is reached, set goalNode and stop search
                    if (isGoal(rightChild.getPuzzleState()))
                    {

                        goalNode = rightChild;
                        break;
                    }

                    // Set iterators
                    openIterator = openList.iterator();
                    closedIterator = closedList.iterator();

                    boolean found = false; // was this state already found?

                    // Holds next() node from respective iterator
                    Node openElement;
                    Node closedElement;

                    // If state is already in openList or closed list, skip this child
                    while (openIterator.hasNext() || closedIterator.hasNext())
                    {// searching openList for same state with lower f value
                        if (openIterator.hasNext())
                        {
                            openElement = openIterator.next();
                            if (Arrays.deepEquals(openElement.getPuzzleState(), rightChild.getPuzzleState())
                                    && openElement.getF() <= rightChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                        if (closedIterator.hasNext())
                        {
                            closedElement = closedIterator.next();
                            if (Arrays.deepEquals(closedElement.getPuzzleState(), rightChild.getPuzzleState())
                                    && closedElement.getF() <= rightChild.getF())
                            {
                                found = true;
                                break;
                            }
                        }
                    }
                    // if right Child's state was not already in openList or closedList
                    // with a <= f value, add it to openList
                    if (!found)
                        openList.add(new Node(rightChild.getPuzzleState(), q));
                }

                // add q to closedList
                closedList.add(q);

            } // end while loop

        // If goalNode was reached
        if (goalNode != null)
        {
            // Assign goal node and parent nodes to arrayList
            ArrayList<Node> printQueue = new ArrayList<Node>();
            Node temp = goalNode;
            while (temp.getParent() != null)
            {
                printQueue.add(temp);
                temp = temp.getParent();
            }
            printQueue.add(temp);

            // Print priority queue
            int moves = 0;
            // Prints printQueue in reverse order (start node to goal node)
            for (int i = printQueue.size() - 1; i >= 0; i--)
            {
                System.out.println((moves++) + " move(s):");
                printQueue.get(i).printNode();
                System.out.println();
            }
            System.out.println("Mission COMPLETE");
        }
        else
            System.out.println("Failure: No solution found.");
    }
}