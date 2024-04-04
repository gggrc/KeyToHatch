import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TypingGameHard extends JFrame {
    private JPanel gamePanel;
    private JLabel timerLabel;
    private JLabel pointsLabel;
    private JLabel difficultyLabel;
    private JTextField typeField;
    private Timer timer;
    private final int GAME_DURATION = 60;
    private final int WORD_DURATION = 2;// Decreased to make words appear more frequently
    private final int WORD_SPEED = 35; // Pixels to move per update
    private int points = 0;
    private String[] words = {"apple", "banana", "aiwufhnlad", "grape", "starfruit", "kiwi", "pineapple", "strawberry"};
    private List<String> shuffledWords;
    private List<JLabel> wordLabels = new ArrayList<>();
    private List<Point> wordPositions = new ArrayList<>();
    private int wordIndex = 0;

    public TypingGameHard() {
        setTitle("Typing Game Hard"); // Changed title
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        timerLabel = new JLabel("Time: " + GAME_DURATION);
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        topPanel.add(timerLabel, BorderLayout.WEST);

        
        pointsLabel = new JLabel("Points: " + points);
        pointsLabel.setForeground(Color.BLACK);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(pointsLabel, BorderLayout.CENTER);

        difficultyLabel = new JLabel ("Difficulty : Hard ");
        difficultyLabel.setForeground(Color.BLACK);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        topPanel.add(difficultyLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        gamePanel = new JPanel();
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setLayout(null); // Absolute positioning
        add(gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        typeField = new JTextField();
        typeField.setHorizontalAlignment(SwingConstants.CENTER);
        typeField.setFont(new Font("Courier New", Font.PLAIN, 30));
        typeField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not needed for this implementation
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Not needed for this implementation
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkWord();
            }
        });
        bottomPanel.add(typeField, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        timer = new Timer(1000, new ActionListener() {
            int timeLeft = GAME_DURATION;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft <= 0) {
                    endGame();
                } else {
                    timeLeft--;
                    timerLabel.setText("Time: " + timeLeft);
                    if (timeLeft % WORD_DURATION == 0) {
                        addNextWord();
                    }
                    moveWords();
                }
            }
        });

        // Shuffling the words
        shuffledWords = new ArrayList<>(List.of(words));
        Collections.shuffle(shuffledWords);

        timer.start();
    }

    private void addNextWord() {
        if (wordIndex >= shuffledWords.size()) {
            wordIndex = 0;
        }
        String word = shuffledWords.get(wordIndex);
        JLabel label = new JLabel(word);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Courier New", Font.BOLD, 20));
        int x = new Random().nextInt(gamePanel.getWidth() - label.getPreferredSize().width);
        int y = new Random().nextInt(gamePanel.getHeight() - label.getPreferredSize().height);
        label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
        gamePanel.add(label);
        gamePanel.revalidate();
        gamePanel.repaint();
        wordLabels.add(label);
        wordPositions.add(new Point(x, y));
        wordIndex++;

        timer.setDelay(500);
    }

    private void moveWords() {
        for (int i = 0; i < wordLabels.size(); i++) {
            JLabel label = wordLabels.get(i);
            Point position = wordPositions.get(i);
            
            // Choose a random direction
            if (new Random().nextInt(100) < 5) { // Adjust the probability as needed
                position.setLocation(new Random().nextInt(gamePanel.getWidth() - label.getWidth()),
                                     new Random().nextInt(gamePanel.getHeight() - label.getHeight()));
            } else {
                // Choose a random direction
                int direction = new Random().nextInt(3); // 0: downwards, 1: upwards, 2: right to left
    
                switch (direction) {
                    case 0: // Move downwards
                        position.translate(0, WORD_SPEED);
                        break;
                    case 1: // Move upwards
                        position.translate(0, -WORD_SPEED);
                        break;
                    case 2: // Move right to left
                        position.translate(-WORD_SPEED, 0);
                        break;
                }
            }
    
            label.setLocation(position);
        }
    }

    private void checkWord() {
        String typedText = typeField.getText();
        for (int i = 0; i < wordLabels.size(); i++) {
            JLabel label = wordLabels.get(i);
            String word = label.getText();
            if (word.equals(typedText)) {
                gamePanel.remove(label);
                gamePanel.revalidate();
                gamePanel.repaint();
                points += typedText.length();
                pointsLabel.setText("Points: " + points);
                typeField.setText("");
                wordLabels.remove(i);
                wordPositions.remove(i);
                break;
            }
        }
    }

    private void endGame() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your final score is: " + points);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TypingGameHard typingGame = new TypingGameHard(); // Changed class name
            typingGame.setVisible(true);
        });
    }
}
