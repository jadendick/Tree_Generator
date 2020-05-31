import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tree
{
    Branch trunk;
    int maxBranchLength = 150;
    int maxBranchConnections = 3;   // Max number of child branches from one parent
    int maxHeight = 6;  // Max for one chain of branches
    int angleRange = 100;   // Range for a branch to grow based on previous branch angle
    int density = 70;   // 0-100 determines how likely branches are to form
    int minHeight = 2;  // Guarantees a tree will grow at least this height regardless of density

    public void createTree(Point centerPoint)   // Grow tree starting with a trunk branch
    {
        trunk = new Branch(centerPoint, new Point(centerPoint.x, centerPoint.y-2*maxBranchLength), 90, maxBranchLength, 0);
        Random rng = new Random(System.currentTimeMillis());
        addBranches(trunk, rng);
    }

    public void addBranches(Branch b, Random rng)
    {
        if(b.height >= maxHeight || b.connections.size() >= maxBranchConnections)   // If branch has reached a limit, don't add more branches
            return;    
        if(b.height >= minHeight && rng.nextInt(100) > density) // If height is over minimum, random chance to grow a branch determined by density
            return;
            
        int xLength = (int)(b.length*(rng.nextDouble()%0.5 + 0.4));  // New branch is 40-90% length of previous one
        int yLength = (int)(rng.nextDouble()*maxBranchLength);
        double angle = rng.nextInt(angleRange) + (b.angle-angleRange/2);   // Range of angle based on angle of previous branch
        int newX = (int)(b.end.x + xLength*Math.cos(Math.toRadians(angle)));
        int newY = (int)(b.end.y - yLength*Math.sin(Math.toRadians(angle)));
        Point newEnd = new Point(newX, newY);
        
        Branch newBranch = new Branch(b.end, newEnd, angle, (int)Math.sqrt(xLength*xLength + yLength*yLength), b.height+1);
        addBranches(newBranch, rng);    // Grow new branch out

        b.connections.add(newBranch);   // Add new branch to old branch
        addBranches(b, rng);    // Try to add more branches to old branch
    }
}

class Branch
{
    ArrayList<Branch> connections;  // Hold branches growing from this branch
    Point start, end;   // Start and end point
    double angle;   // Angle of branch in degrees
    int length; // Length of branch
    int height; // Number of previous branches, trunk is 0

    public Branch(Point start, Point end, double angle, int length, int height) 
    {
        connections = new ArrayList<Branch>();
        this.start = start;
        this.end = end;
        this.angle = angle;
        this.length = length;
        this.height = height;
    }
}