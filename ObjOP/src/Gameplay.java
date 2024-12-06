import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Gameplay extends JPanel implements ActionListener {
    private brick br;

    private ImageIcon playerIcon;
    private int playerX = 200;
    private int playerY = 550;
    private boolean playerRight = false;
    private boolean playerLeft = false;
    private boolean playerDown = false;
    private boolean playerUp = true;
    private int playerScore = 0;
    private int playerLives = 5;
    private boolean playerShoot = false;
    private String bulletShootDir = "";

    private Timer timer;
    private int delay = 8;

    private PlayerListener playerListener;

    private PlayerBullet playerBullet = null;

    private boolean play = true;

    // ImageIcons for tanks
    private ImageIcon tankUp, tankDown, tankRight, tankLeft;
    private ImageIcon enemyTankUp, enemyTankDown, enemyTankRight, enemyTankLeft;

    // Enemy tank list
    private List<EnemyTank> enemies;

    public Gameplay() {
        br = new brick();
        playerListener = new PlayerListener();
        setFocusable(true);
        addKeyListener(playerListener);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

        // Load player tank images
        tankUp = new ImageIcon(getClass().getResource("/resources/TankU.gif"));
        tankDown = new ImageIcon(getClass().getResource("/resources/TankD.gif"));
        tankRight = new ImageIcon(getClass().getResource("/resources/TankR.gif"));
        tankLeft = new ImageIcon(getClass().getResource("/resources/TankL.gif"));

        // Load enemy tank images
        enemyTankUp = new ImageIcon(getClass().getResource("/resources/EnemyTankU.gif"));
        enemyTankDown = new ImageIcon(getClass().getResource("/resources/EnemyTankD.gif"));
        enemyTankRight = new ImageIcon(getClass().getResource("/resources/EnemyTankR.gif"));
        enemyTankLeft = new ImageIcon(getClass().getResource("/resources/EnemyTankL.gif"));

        // Initialize enemies
        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Add 3 enemy tanks
            enemies.add(new EnemyTank(100 * i, 50, "down"));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, 650, 600);

        // Right panel for scores and lives
        g.setColor(Color.DARK_GRAY);
        g.fillRect(660, 0, 140, 600);

        // Draw bricks
        br.drawSolids(this, g);
        br.draw(this, g);

        // Draw player tank
        if (playerUp) playerIcon = tankUp;
        else if (playerDown) playerIcon = tankDown;
        else if (playerRight) playerIcon = tankRight;
        else if (playerLeft) playerIcon = tankLeft;

        playerIcon.paintIcon(this, g, playerX, playerY);

        // Draw player bullet
        if (playerBullet != null) {
            // Move the player bullet in the specified direction
            playerBullet.move(bulletShootDir);

            // Check if the bullet is out of bounds
            if (playerBullet.isOutOfBounds()) {
                playerBullet = null;  // Remove the bullet if it's out of bounds
                playerShoot = false;  // Reset shooting flag
            } else {
                playerBullet.draw(g);  // Draw the bullet if it's still in bounds
            }

            // Collision detection: Check if the player bullet hits any enemy tank
            if (playerBullet != null) {
                for (int i = 0; i < enemies.size(); i++) {
                    EnemyTank enemy = enemies.get(i);
                    // Check if the bullet's position is within the enemy's area
                    if (playerBullet.getX() >= enemy.getX() && playerBullet.getX() <= enemy.getX() + 40 &&
                            playerBullet.getY() >= enemy.getX() && playerBullet.getY() <= enemy.getX() + 40) {
                        enemies.remove(i);  // Remove the enemy from the list
                        playerBullet = null;  // Destroy the player's bullet
                        playerShoot = false;  // Reset shooting flag
                        playerScore += 10;  // Add points for destroying the enemy
                        break;
                    }
                }
            }
        }

        // Draw and update enemy tanks
        for (EnemyTank enemy : enemies) {
            enemy.draw(g, enemyTankUp, enemyTankDown, enemyTankRight, enemyTankLeft);
            enemy.updateBullet();
        }

        // Handle collisions for enemy bullets vs. player tank
        for (EnemyTank enemy : enemies) {
            EnemyBullet enemyBullet = enemy.getBullet();
            if (enemyBullet != null) {
                if (enemyBullet.getX() >= playerX && enemyBullet.getX() <= playerX + 40 &&
                        enemyBullet.getY() >= playerY && enemyBullet.getY() <= playerY + 40) {
                    playerLives--;
                    enemy.destroyBullet(); // Destroy bullet on collision
                    if (playerLives <= 0) {
                        play = false;
                    }
                    break;
                }
                // Draw enemy bullet
                enemyBullet.draw(g);
            }
        }

        // Display scores and lives
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("SCORE", 680, 30);
        g.setFont(new Font("serif", Font.PLAIN, 18));
        g.drawString("Player: " + playerScore, 680, 60);

        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("LIVES", 680, 120);
        g.setFont(new Font("serif", Font.PLAIN, 18));
        g.drawString("Player: " + playerLives, 680, 150);

        // Game over screen
        if (playerLives == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("Game Over", 150, 300);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("(Press SPACE to Restart)", 180, 400);
        }

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            for (EnemyTank enemy : enemies) {
                enemy.move();  // Move the enemy tank
                enemy.fire();  // Try to fire a bullet (will be delayed by cooldown)
                enemy.updateBullet();  // Update the enemy's bullet (if any)
            }
            repaint();  // Redraw the screen to update the tank and bullet positions
        }
    }


    private class PlayerListener implements KeyListener {
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            // Restart game logic when lives are 0 and SPACE is pressed
            if (e.getKeyCode() == KeyEvent.VK_SPACE && playerLives == 0) {
                br = new brick();
                playerX = 200;
                playerY = 550;
                playerRight = false;
                playerLeft = false;
                playerDown = false;
                playerUp = true;
                playerBullet = null;
                bulletShootDir = "";
                playerScore = 0;
                playerLives = 5;
                play = true;
                enemies.clear();
                for (int i = 0; i < 3; i++) {
                    enemies.add(new EnemyTank(100 * i, 50, "down"));
                }
                repaint();
            }

            // Shooting logic
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !playerShoot && play) {
                // Create and shoot a bullet based on the player's current direction
                if (playerUp) {
                    playerBullet = new PlayerBullet(playerX + 20, playerY, bulletShootDir);  // Bullet starts above the tank
                    bulletShootDir = "up";
                } else if (playerDown) {
                    playerBullet = new PlayerBullet(playerX + 20, playerY + 40, bulletShootDir);  // Bullet starts below the tank
                    bulletShootDir = "down";
                } else if (playerRight) {
                    playerBullet = new PlayerBullet(playerX + 40, playerY + 20, bulletShootDir);  // Bullet starts to the right of the tank
                    bulletShootDir = "right";
                } else if (playerLeft) {
                    playerBullet = new PlayerBullet(playerX, playerY + 20, bulletShootDir);  // Bullet starts to the left of the tank
                    bulletShootDir = "left";
                }
                playerShoot = true;  // Mark that the player is shooting
            }


            // Movement logic
            if (e.getKeyCode() == KeyEvent.VK_W && playerY > 10) {
                if (!br.checkCollision(playerX, playerY - 10)) playerY -= 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_A && playerX > 10) {
                if (!br.checkCollision(playerX - 10, playerY)) playerX -= 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_S && playerY < 540) {
                if (!br.checkCollision(playerX, playerY + 10)) playerY += 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_D && playerX < 600) {
                if (!br.checkCollision(playerX + 10, playerY)) playerX += 10;
            }
        }
    }
}
