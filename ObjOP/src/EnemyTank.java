import java.awt.Graphics;
import javax.swing.ImageIcon;

public class EnemyTank {
    private int x, y;
    private String direction;
    private EnemyBullet bullet;
    private int fireCooldown = 0;  // Timer for cooldown to prevent rapid firing

    public EnemyTank(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

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

    // The fire method handles firing bullets
    public void fire() {
        // Cooldown mechanism to prevent constant firing
        if (fireCooldown > 0) {
            fireCooldown--;
            return; // If cooldown is not zero, do not fire
        }

        // Only fire if the tank is not already firing a bullet
        if (bullet == null) {
            switch (direction) {
                case "up":
                    bullet = new EnemyBullet(x + 15, y - 5, "up"); // Fire from above the tank
                    break;
                case "down":
                    bullet = new EnemyBullet(x + 15, y + 40, "down"); // Fire from below the tank
                    break;
                case "left":
                    bullet = new EnemyBullet(x - 5, y + 15, "left"); // Fire from the left side of the tank
                    break;
                case "right":
                    bullet = new EnemyBullet(x + 40, y + 15, "right"); // Fire from the right side of the tank
                    break;
            }
            fireCooldown = 60; // Reset cooldown (e.g., 60 ticks before the next shot)
        }
    }

    // Update bullet and check if it is out of bounds
    public void updateBullet() {
        if (bullet != null) {
            bullet.move();
            if (bullet.isOutOfBounds()) {
                bullet = null;  // Remove the bullet if it's out of bounds
            }
        }
    }

    public void draw(Graphics g, ImageIcon tankUp, ImageIcon tankDown, ImageIcon tankRight, ImageIcon tankLeft) {
        // Draw the enemy tank depending on the current direction
        if (direction.equals("up")) tankUp.paintIcon(null, g, x, y);
        else if (direction.equals("down")) tankDown.paintIcon(null, g, x, y);
        else if (direction.equals("left")) tankLeft.paintIcon(null, g, x, y);
        else if (direction.equals("right")) tankRight.paintIcon(null, g, x, y);
    }

    // Getters and setters for the bullet and position would be here
    public EnemyBullet getBullet() {
        return bullet;
    }

    // Return the x-coordinate of the enemy tank
    public int getX() {
        return x;
    }

    // Destroy the bullet by setting it to null
    public void destroyBullet() {
        bullet = null;
    }
}
