import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class TreeGenerator extends JPanel
{
    private static final long serialVersionUID = 1L;
    static Tree tree = new Tree();
    public static void main(String[] args)
    {
        JPanel ui = createUI();
        JFrame frame = new JFrame("Tree Generator");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Grow");   // Add button to grow a new tree
        button.addActionListener(e -> frame.repaint());
        ui.add(button);
        frame.getContentPane().add(ui, BorderLayout.SOUTH);
        
        TreeGenerator tree = new TreeGenerator();   // Add tree generator display
        tree.setBackground(new Color(0,204,255));
        frame.getContentPane().add(tree);
        
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Point center = new Point(getWidth() / 2, getHeight());
        tree.createTree(center);

        ((Graphics2D)g).setStroke(new BasicStroke(10)); // Make the branches thicker
        g.setColor(new Color(150,75,0));
        drawBranch(g, tree.trunk);
        
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        drawLeaves(g, tree.trunk);
    }

    public void drawBranch(Graphics g, Branch b)
    {
        g.drawLine(b.start.x, b.start.y, b.end.x, b.end.y);

        for(Branch nextBranch : b.connections)  // Draw all connected branches
            drawBranch(g, nextBranch);
    }

    public void drawLeaves(Graphics g, Branch b)
    {
        int leafSize = 50 * (b.height / 2); // Leaves get bigger the higher the branch is
        if(b.connections.isEmpty()) // If branch has no more branches, it is a leaf
        {
            g.setColor(new Color(0,200,0,100)); // Draw leaf body
            g.fillOval(b.end.x - leafSize/2, b.end.y - leafSize/2, leafSize, leafSize);
            g.setColor(new Color(0,110,0,200)); // Draw leaf outline
            g.drawOval(b.end.x - leafSize/2, b.end.y - leafSize/2, leafSize, leafSize);
        }

        for(Branch nextBranch : b.connections)  // Draw leaves for all connected branches
            drawLeaves(g, nextBranch);
    }

    public static JPanel createUI() // Add text fields to change tree growth parameters
    {
        JPanel fields = new JPanel();

        fields.add(new JLabel("Density"));
        JTextField density = new JTextField("80",3);
        Document amountText = density.getDocument();
        fields.add(density);
        amountText.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.density = Integer.parseInt(density.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.density = Integer.parseInt(density.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        fields.add(new JLabel("Angle Range"));
        JTextField angleRange = new JTextField("120",3);
        Document angleRangeDoc = angleRange.getDocument();
        fields.add(angleRange);
        angleRangeDoc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.angleRange = Integer.parseInt(angleRange.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.angleRange = Integer.parseInt(angleRange.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        fields.add(new JLabel("Max New Branches"));
        JTextField maxBranches = new JTextField("3",1);
        Document maxBranchesDoc = maxBranches.getDocument();
        fields.add(maxBranches);
        maxBranchesDoc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.maxBranchConnections = Integer.parseInt(maxBranches.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.maxBranchConnections = Integer.parseInt(maxBranches.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        fields.add(new JLabel("Max Branch Length"));
        JTextField maxBranchLength = new JTextField("150",4);
        Document maxBranchLengthDoc = maxBranchLength.getDocument();
        fields.add(maxBranchLength);
        maxBranchLengthDoc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.maxBranchLength = Integer.parseInt(maxBranchLength.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.maxBranchLength = Integer.parseInt(maxBranchLength.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        fields.add(new JLabel("Max Height"));
        JTextField maxHeight = new JTextField("5",2);
        Document maxHeightDoc = maxHeight.getDocument();
        fields.add(maxHeight);
        maxHeightDoc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.maxHeight = Integer.parseInt(maxHeight.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.maxHeight = Integer.parseInt(maxHeight.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        fields.add(new JLabel("Min Height"));
        JTextField minHeight = new JTextField("1",1);
        Document minHeightDoc = minHeight.getDocument();
        fields.add(minHeight);
        minHeightDoc.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { tree.minHeight = Integer.parseInt(minHeight.getText()); }
            public void insertUpdate(DocumentEvent e) { tree.minHeight = Integer.parseInt(minHeight.getText()); }
            public void removeUpdate(DocumentEvent e) {}
        });

        return fields;
    }
}