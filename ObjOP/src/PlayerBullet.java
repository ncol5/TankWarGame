import java.awt.*;

public class PlayerBullet {
    private int x, y; // Bullet position
    private String direction; // Movement direction
    private final int speed = 5; // Bullet speed

    public PlayerBullet(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Moves the bullet in the specified direction.
     * @param bulletShootDir 
     */
    public void move(String bulletShootDir) {
        switch (direction) {
            case "up":
                y -= speed;
                break;
            case "down":
                y += speed;
                break;
            case "left":
                x -= speed;
                break;
            case "right":
                x += speed;
                break;
        }
    }

    /**
     * Checks if the bullet has moved out of bounds.
     *
     * @return True if the bullet is out of bounds, false otherwise.
     */
    public boolean isOutOfBounds() {
        return x < 0 || x > 650 || y < 0 || y > 600;
    }

    /**
     * Draws the bullet.
     * 
     * @param g The Graphics object for drawing.
     */
    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x, y, 5, 5); // Represent the bullet as a small rectangle
    }
}
