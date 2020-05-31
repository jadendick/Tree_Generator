
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class TreeGenerator extends JPanel
{
    private static final long serialVersionUID = 1L;
    public static void main(String[] args)
    {
        JPanel fields = new JPanel();
        // JTextField maxBranches = new JTextField(12);
        // Document amountText = maxBranches.getDocument();
        // fields.add(maxBranches);

        // amountText.addDocumentListener(new DocumentListener() {
        //     public void changedUpdate(DocumentEvent e) { density = Integer.parseInt(maxBranches.getText()); }
        //     public void insertUpdate(DocumentEvent e) { density = Integer.parseInt(maxBranches.getText()); }
        //     public void removeUpdate(DocumentEvent e) {}
        // });


        TreeGenerator tree = new TreeGenerator();
        JFrame frame = new JFrame("Tree Generator");
        frame.getContentPane().add(tree);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tree.setBackground(new Color(0,204,255));
        JButton button = new JButton("Grow");
        button.addActionListener(e -> frame.repaint());
        // frame.getContentPane().add(button, BorderLayout.SOUTH);
        fields.add(button);
        frame.getContentPane().add(fields, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Point center = new Point(getWidth() / 2, getHeight());
        Tree tree = new Tree(center);

        ((Graphics2D)g).setStroke(new BasicStroke(10)); // Make the branches thicker
        g.setColor(new Color(150,75,0));
        drawBranch(g, tree.trunk);
        
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        drawLeaves(g, tree.trunk);
    }

    public void drawBranch(Graphics g, Branch b)
    {
        g.drawLine(b.start.x, b.start.y, b.end.x, b.end.y);

        for(Branch nextBranch : b.connections)  // Draw all connecting branches
            drawBranch(g, nextBranch);
    }

    public void drawLeaves(Graphics g, Branch b)
    {
        int leafSize = 50 * (b.height / 2); // Leaves get bigger the higher the branch is
        if(b.connections.isEmpty()) // If branch has no more branches, it is a leaf
        {
            g.setColor(new Color(0,200,0,100));
            g.fillOval(b.end.x - leafSize/2, b.end.y - leafSize/2, leafSize, leafSize);
            g.setColor(new Color(0,110,0,200));
            g.drawOval(b.end.x - leafSize/2, b.end.y - leafSize/2, leafSize, leafSize);
        }

        for(Branch nextBranch : b.connections)
            drawLeaves(g, nextBranch);
    }
}