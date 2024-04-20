import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.Rectangle;
import javax.swing.JOptionPane;


public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
    int frameWidth = 360;
    int frameHeight = 640;

    // Image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // Player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    private int score = 0;
    private JLabel scoreLabel;
    private JPanel scorePanel;

    // Pipe
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    boolean isGameOver = false;

    public FlappyBird()
    {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setBackground(Color.blue);
        addKeyListener(this);

        backgroundImage = new ImageIcon(getClass().getResource("assets/background2.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/burung.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        pipesCooldown = new Timer(1500, new ActionListener()
        {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               placePipes();
           }
        });
        pipesCooldown.start();

        // Score panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        scoreLabel = new JLabel("SCORE: " + score);
        scoreLabel.setFont(new Font("Courier", Font.BOLD, 20));
        scorePanel.add(scoreLabel);
        add(scorePanel);

        // Memastikan panel dapat menerima input dari keyboard
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        if (isGameOver) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Courier", Font.BOLD, 36));
            FontMetrics fm = g.getFontMetrics();
            int x = (frameWidth - fm.stringWidth("Game Over")) / 2;
            int y = frameHeight / 2;
            g.drawString("Game Over", x, y);
            g.setFont(new Font("Courier", Font.BOLD, 20)); // Atur ukuran font
            fm = g.getFontMetrics();
            String retryText = "Press r to retry";
            x = (frameWidth - fm.stringWidth(retryText)) / 2;
            y += fm.getHeight() + 5; // Tambahkan jarak di bawah teks "Game Over"
            g.drawString(retryText, x, y);
        }
    }

    public void draw(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for(int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void move()
    {
        // Pemeriksaan tabrakan dengan pipa
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
        for(int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            Rectangle upperPipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            Rectangle lowerPipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY() + pipe.getHeight() + (frameHeight / 4), pipe.getWidth(), pipe.getHeight());

            if (playerRect.intersects(upperPipeRect) || playerRect.intersects(lowerPipeRect) || player.getPosY() + player.getHeight() >= frameHeight)
            {
                gameOver();
                return;
            }
        }

        // Pemeriksaan jika pemain melewati pipa
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            if (pipe.getPosX() + pipe.getWidth() < player.getPosX() && !pipe.isPassed())
            {
                // Pemain melewati pipa tanpa menabrak
                pipe.setPassed(true);
                increaseScore();
            }
        }

        // Update posisi pemain dan pipa
        player.setVelocityY(player.getVelocityY() + gravity);
        int newPosY = player.getPosY() + player.getVelocityY();
        // Mencegah pemain menembus bagian bawah JFrame
        if (newPosY + player.getHeight() >= frameHeight)
        {
            player.setPosY(frameHeight - player.getHeight());
            gameOver();
            return;
        }
        player.setPosY(newPosY);

        for(int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }
    }

    public void placePipes()
    {
        int randomPipePosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPipePosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPipePosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void gameOver() {
        // Menghentikan timer
        gameLoop.stop();
        pipesCooldown.stop();

        // Set isGameOver menjadi true
        isGameOver = true;

        // Panggil repaint untuk menggambar ulang panel dengan teks "Game Over"
        repaint();
    }

    public void restartGame() {
        // Reset player position
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);

        // Clear pipes
        pipes.clear();

        // Restart timers
        gameLoop.start();
        pipesCooldown.start();

        isGameOver = false;
        requestFocus();

        // Restart score
        score = 0;
        scoreLabel.setText("SCORE: " + score);
    }

    public void increaseScore()
    {
        score++;
        scoreLabel.setText("SCORE: " + score / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
        move();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            player.setVelocityY(-10);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R)
        {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}

