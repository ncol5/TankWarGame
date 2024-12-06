import java.awt.Color;
import java.awt.Graphics;

public class EnemyBullet {
    private int x, y;  // Position of the bullet
    private String direction;  // Direction of movement

    public EnemyBullet(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    // Getter for the x position of the bullet
    public int getX() {
        return x;
    }

    // Getter for the y position of the bullet
    public int getY() {
        return y;
    }

    // Method to destroy the bullet by setting it to null
    public void destroy() {
        this.x = -1;  // Out of bounds to signify destruction
        this.y = -1;  // Out of bounds to signify destruction
    }

    // Method to move the bullet (assuming the bullet moves in a straight line)
    public void move() {
        switch (direction) {
            case "up":
                y -= 5;
                break;
            case "down":
                y += 5;
                break;
            case "left":
                x -= 5;
                break;
            case "right":
                x += 5;
                break;
        }
    }

    // Method to check if the bullet is out of bounds
    public boolean isOutOfBounds() {
        return x < 0 || x > 650 || y < 0 || y > 600;
    }

    // Method to draw the bullet (optional, based on your graphics code)
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 5, 5);  // Representing the bullet as a small red square
    }
}
