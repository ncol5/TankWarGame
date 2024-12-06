import java.awt.Graphics;
import javax.swing.ImageIcon;

public class PlayerTank {
    private int x, y;
    private String direction;
    private PlayerBullet bullet;  // Player's bullet
    private boolean isShooting;   // Flag to check if the player is shooting
    private int moveSpeed = 5;    // Speed at which the player tank moves

    public PlayerTank(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.direction = "up"; // Initial direction is up
        this.isShooting = false;
    }

    // Move the player tank based on the current direction
    public void move(String direction) {
        this.direction = direction;
        switch (direction) {
            case "up":
                y -= moveSpeed;
                break;
            case "down":
                y += moveSpeed;
                break;
            case "left":
                x -= moveSpeed;
                break;
            case "right":
                x += moveSpeed;
                break;
        }
    }

    // Shoot a bullet in the current direction
    public void shoot() {
        if (!isShooting && bullet == null) {
            isShooting = true;
            switch (direction) {
                case "up":
                    bullet = new PlayerBullet(x + 15, y, "up");
                    break;
                case "down":
                    bullet = new PlayerBullet(x + 15, y + 40, "down");
                    break;
                case "left":
                    bullet = new PlayerBullet(x, y + 15, "left");
                    break;
                case "right":
                    bullet = new PlayerBullet(x + 40, y + 15, "right");
                    break;
            }
        }
    }

    // Update the bullet's position
    public void updateBullet() {
        if (bullet != null) {
            bullet.move(direction);
            if (bullet.isOutOfBounds()) {
                bullet = null;  // Remove the bullet if it's out of bounds
                isShooting = false;
            }
        }
    }

    // Draw the player tank and its bullet
    public void draw(Graphics g, ImageIcon tankUp, ImageIcon tankDown, ImageIcon tankRight, ImageIcon tankLeft) {
        // Draw the player tank based on the current direction
        if (direction.equals("up")) {
            tankUp.paintIcon(null, g, x, y);
        } else if (direction.equals("down")) {
            tankDown.paintIcon(null, g, x, y);
        } else if (direction.equals("left")) {
            tankLeft.paintIcon(null, g, x, y);
        } else if (direction.equals("right")) {
            tankRight.paintIcon(null, g, x, y);
        }

        // Draw the player's bullet, if any
        if (bullet != null) {
            bullet.draw(g);
        }
    }

    // Getters for the player's position and bullet
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PlayerBullet getBullet() {
        return bullet;
    }

    // Setters for position, for when the tank moves manually
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
