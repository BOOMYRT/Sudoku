package src;
// Java Program to Create a Simple JPanel
// and Adding Components to it

// Importing required classes
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;


// Class 1
// Helper class extending JFrame class
class Graphical extends JFrame {

    // JFrame
    static JFrame f;

    // JButton
    static JButton b, b1, b2;

    // Label to display text
    static JLabel l;



    // Main class
    public static void main(String[] args)
    {
        // Creating a new frame to store text field and
        // button
        f = new JFrame("panel");

        // Creating a label to display text
        l = new JLabel("panel label");

        // Creating a new buttons
        b = new JButton("button1");
        b1 = new JButton("button2");
        b2 = new JButton("button3");

        // Creating a panel to add buttons
        JPanel p = new JPanel();

        JPanel board = new JPanel(new GridLayout(9, 9));

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, board, p);

        JPanel[][] squares = new JPanel[9][9];
        //JTE

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        // Adding buttons and textfield to panel
        // using add() method

        //p.add(b1);
        //board.add(b2);
        //p.add(l);

        // setbackground of panel
        board.setBackground(Color.GRAY);
        p.setBackground(Color.RED);

        for (int rows = 1; rows <= 9; rows++) {
            for (int cols = 1; cols <= 9; cols++) {
                squares[rows - 1][cols - 1] = new JPanel();
                squares[rows - 1][cols - 1].setBorder(border);
                squares[rows - 1][cols - 1].setBackground(Color.WHITE);
                board.add(squares[rows - 1][cols - 1]);
            }
        }

        board.add(sep);
        board.add(b);

        //p.add(squares);
        //p.add(border);

        // Adding panel to frame
        //f.add(board);
        f.add(splitPane);
        // Setting the size of frame
        f.setSize(500, 500);

        f.show();
    }
}
