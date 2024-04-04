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

public class TypingGameEasy extends JFrame {
    private JPanel gamePanel;
    private JLabel timerLabel;
    private JLabel pointsLabel;
    private JLabel difficultyLabel;
    private JTextField typeField;
    private Timer timer;
    private final int GAME_DURATION = 60;
    private final int WORD_DURATION = 3;
    private int points = 0;
    private String[] words = { "apple", "banana", "grape", "starfruit", "kiwi", "pineapple", "house", "Car", "school",
            "cat", "dog", "father", "mother", "day", "time", "month", "book", "table", "pencil", "laptop", "phone",
            "friend", "smile", "road", "water", "park", "strawberry" };
    private List<String> shuffledWords;
    private List<JLabel> wordLabels = Collections.synchronizedList(new ArrayList<>());
    private int wordIndex = 0;

    public TypingGameEasy() {
        setTitle("Typing Game");
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

        difficultyLabel = new JLabel ("Difficulty : Easy ");
        difficultyLabel.setForeground(Color.BLACK);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        topPanel.add(difficultyLabel, BorderLayout.EAST);


        add(topPanel, BorderLayout.NORTH);

        gamePanel = new JPanel();
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setLayout(null);
        add(gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        typeField = new JTextField();
        typeField.setHorizontalAlignment(SwingConstants.CENTER);
        typeField.setFont(new Font("Courier New", Font.PLAIN, 30));
        typeField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
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
                }
            }
        });

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
        label.setFont(new Font("Courier New", Font.BOLD, 40));
        int x = new Random().nextInt(gamePanel.getWidth() - label.getPreferredSize().width);
        int y = new Random().nextInt(gamePanel.getHeight() - label.getPreferredSize().height);
        label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
        gamePanel.add(label);
        wordLabels.add(label);
        gamePanel.revalidate();
        gamePanel.repaint();

        int finalX = x; // Buat variabel baru yang final
        Thread t = new Thread(() -> {
            try {
                int localX = finalX; // Gunakan variabel lokal yang final
                while (true) {
                    Thread.sleep(50);
                    localX += 5;
                    if (localX > gamePanel.getWidth()) {
                        break;
                    }
                    label.setBounds(localX, y, label.getWidth(), label.getHeight());
                    gamePanel.repaint();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        t.start();

        Timer wordTimer = new Timer(WORD_DURATION * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.remove(label);
                gamePanel.revalidate();
                gamePanel.repaint();
                wordLabels.remove(label);
                t.interrupt(); // Stop the thread when the word is removed
            }
        });
        wordTimer.setRepeats(false);
        wordTimer.start();

        wordIndex++;
    }

    private void checkWord() {
        String typedText = typeField.getText();
        for (JLabel label : wordLabels) {
            String word = label.getText();
            if (word.equals(typedText)) {
                gamePanel.remove(label);
                gamePanel.revalidate();
                gamePanel.repaint();
                points += typedText.length();
                pointsLabel.setText("Points: " + points);
                typeField.setText("");
                wordLabels.remove(label);
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
            TypingGameEasy typingGame = new TypingGameEasy(); 
            typingGame.setVisible(true);
        });
    }
}