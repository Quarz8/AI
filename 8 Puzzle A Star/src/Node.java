// Course:			CS4242
// Name:			Warren Davis
// Student ID:		000545250
// Assignment #:	#1
// Due Date:		03/06/2019

public class Node
{
    private char[][] puzzleState = new char[3][3]; // state of 8 Puzzle
    private char[][] goalState =
    {
            { '1', '2', '3' },
            { '8', ' ', '4' },
            { '7', '6', '5' } };
    private int g = 0; // cost from starting point
    private int h = 0; // heuristic value
    private int f = 0; // cost of g+h
    private Node parent = null; // this node's parent (default null)

    // Constructs Node containing copy of pState as puzzleState
    public Node(char[][] pState)
    {
        // Generate copy of pState
        for (int i = 0; i < 3; i++)
        {
            System.arraycopy(pState[i], 0, puzzleState[i], 0, 3);
        }

        // Calculate h (# of misplaced tiles)
        for (int row = 0; row < puzzleState.length; row++)
        {
            for (int col = 0; col < puzzleState[row].length; col++)
            {
                if (puzzleState[row][col] != ' ' && puzzleState[row][col] != goalState[row][col])
                    h++;
            }
        }

        // Calculate f
        f = h + g;
    }

    // Constructs Node containing copy of pState as puzzleState and pNode as parent
    public Node(char[][] pState, Node pNode)
    {
        // Generate copy of pState
        for (int i = 0; i < 3; i++)
        {
            System.arraycopy(pState[i], 0, puzzleState[i], 0, 3);
        }

        // Set parent of Node
        parent = pNode;

        // Calculate h (# of misplaced tiles)
        for (int row = 0; row < puzzleState.length; row++)
        {
            for (int col = 0; col < puzzleState[row].length; col++)
            {
                if (puzzleState[row][col] != ' ' && puzzleState[row][col] != goalState[row][col])
                    h++;
            }
        }

        // Calculate g (distance of path from root)
        g = parent.g + 1;

        // Calculate f
        f = h + g;
    }

    // Returns value of f
    public int getF()
    {
        return f;
    }

    // Gets parent Node
    public Node getParent()
    {
        return parent;
    }

    // Returns puzzleState of Node
    public char[][] getPuzzleState()
    {
        return puzzleState;
    }

    // Prints puzzleState of Node
    public void printNode()
    {
        for (int row = 0; row < puzzleState.length; row++)
        {
            for (int col = 0; col < puzzleState[row].length; col++)
            {
                System.out.print(puzzleState[row][col] + " ");
            }
            System.out.println();
        }
    }
}
