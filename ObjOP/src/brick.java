import java.awt.*;
import javax.swing.ImageIcon;

public class brick {

    // Brick positions
    int bricksXPos[] = {
        50, 350, 450, 550, 50, 300, 350, 450, 550, 150, 150, 450, 550,
        250, 50, 100, 150, 550, 250, 350, 450, 550, 50, 250, 350, 550,
        50, 150, 250, 300, 350, 550, 50, 150, 250, 350, 450, 550, 50,
        250, 350, 550
    };

    int bricksYPos[] = {
        50, 50, 50, 50, 100, 100, 100, 100, 100, 150, 200, 200, 200, 250,
        300, 300, 300, 300, 350, 350, 350, 350, 400, 400, 400, 400, 450,
        450, 450, 450, 450, 450, 500, 500, 500, 500, 500, 500, 550, 550,
        550, 550
    };

    int solidBricksXPos[] = {
        150, 350, 150, 500, 450, 300, 600, 400, 350, 200, 0, 200, 500
    };

    int solidBricksYPos[] = {
        0, 0, 50, 100, 150, 200, 200, 250, 300, 350, 400, 400, 450
    };

    // States of breakable bricks (1 = present, 0 = destroyed)
    int brickON[] = new int[42];

    // Images for bricks
    private ImageIcon breakBrickImage;
    private ImageIcon solidBrickImage;

    // Constructor
    public brick() {
        // Load images
        try {
            breakBrickImage = new ImageIcon(getClass().getResource("/resources/break_brick.jpg"));
            solidBrickImage = new ImageIcon(getClass().getResource("/resources/solid_brick.jpg"));
        } catch (Exception e) {
            System.err.println("Error loading brick images: " + e.getMessage());
        }

        // Initialize breakable brick states
        for (int i = 0; i < brickON.length; i++) {
            brickON[i] = 1; // Brick is present
        }
    }

    // Draw breakable bricks
    public void draw(Component c, Graphics g) {
        for (int i = 0; i < brickON.length; i++) {
            if (brickON[i] == 1) { // Only draw active bricks
                breakBrickImage.paintIcon(c, g, bricksXPos[i], bricksYPos[i]);
            }
        }
    }

    // Draw solid bricks
    public void drawSolids(Component c, Graphics g) {
        for (int i = 0; i < solidBricksXPos.length; i++) {
            solidBrickImage.paintIcon(c, g, solidBricksXPos[i], solidBricksYPos[i]);
        }
    }

    // Check collision with breakable bricks
    public boolean checkCollision(int x, int y) {
        boolean collided = false;
        for (int i = 0; i < brickON.length; i++) {
            if (brickON[i] == 1) { // Only check active bricks
                if (new Rectangle(x, y, 10, 10).intersects(new Rectangle(bricksXPos[i], bricksYPos[i], 50, 50))) {
                    brickON[i] = 0; // Mark brick as destroyed
                    collided = true;
                    break;
                }
            }
        }
        return collided;
    }

    // Check collision with solid bricks
    public boolean checkSolidCollision(int x, int y) {
        for (int i = 0; i < solidBricksXPos.length; i++) {
            if (new Rectangle(x, y, 10, 10).intersects(new Rectangle(solidBricksXPos[i], solidBricksYPos[i], 50, 50))) {
                return true;
            }
        }
        return false;
    }
}
