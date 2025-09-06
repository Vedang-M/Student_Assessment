import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class QuestionBankGUI extends JFrame {
    // Components for login
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JPanel loginPanel, questionPanel;

    // Components for questions
    JLabel questionLabel;
    JRadioButton[] options;
    ButtonGroup optionsGroup;
    JButton nextButton, submitButton;

    // Data structures
    Map<String, String[]> candidateData = new HashMap<>(); // Map for student data (username -> {mobile, subject})
    java.util.List<Question> selectedQuestions = new ArrayList<>();
    int currentQuestionIndex = 0, score = 0;

    // Constructor
    public QuestionBankGUI() {
        setTitle("Question Bank");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Initialize UI
        initializeLoginPanel();
        initializeQuestionPanel();
        loadCandidateData();

        // Show login panel
        add(loginPanel);
        setVisible(true);
    }

    private void initializeLoginPanel() {
        loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Student Name:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Mobile Number:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        // Action Listener
        loginButton.addActionListener(e -> handleLogin());
    }

    private void initializeQuestionPanel() {
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Question display
        JPanel questionDisplayPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        questionLabel = new JLabel();
        options = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        questionDisplayPanel.add(questionLabel);
        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            optionsGroup.add(options[i]);
            questionDisplayPanel.add(options[i]);
        }

        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        submitButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        questionPanel.add(questionDisplayPanel, BorderLayout.CENTER);
        questionPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        nextButton.addActionListener(e -> handleNextQuestion());
        submitButton.addActionListener(e -> handleSubmit());
    }

    private void loadCandidateData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\VESIT\\Java\\JavaProjects\\gui\\Data.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 8) {
                    String username = data[0].trim(); // Student name
                    String mobile = data[8].trim(); // Mobile number
                    String subject = data[1].trim(); // Subject
                    candidateData.put(username, new String[]{mobile, subject});
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading Data.csv file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private java.util.List<Question> loadQuestionsForSubject(String subject) {
        java.util.List<Question> questions = new ArrayList<>();

        if (subject.equals("Physics")) {
            questions.add(new Question("What is the speed of light?", new String[]{"3x10^8 m/s", "3x10^6 m/s", "3x10^9 m/s", "3x10^7 m/s"}, 0));
            questions.add(new Question("Who discovered gravity?", new String[]{"Newton", "Einstein", "Galileo", "Tesla"}, 0));
            questions.add(new Question("What is a photon?", new String[]{"Particle", "Wave", "Energy", "Force"}, 0));
            questions.add(new Question("What is Ohm's law?", new String[]{"V=IR", "P=IV", "Q=CV", "F=ma"}, 0));
            questions.add(new Question("What is the unit of force?", new String[]{"Newton", "Joule", "Watt", "Pascal"}, 0));
        } else if (subject.equals("Chemistry")) {
            questions.add(new Question("What is H2O?", new String[]{"Water", "Oxygen", "Hydrogen", "Salt"}, 0));
            questions.add(new Question("What is NaCl?", new String[]{"Salt", "Sugar", "Water", "Alcohol"}, 0));
            questions.add(new Question("What is the atomic number of Hydrogen?", new String[]{"1", "2", "3", "4"}, 0));
            questions.add(new Question("What is CH4?", new String[]{"Methane", "Ethane", "Propane", "Butane"}, 0));
            questions.add(new Question("What is the pH of water?", new String[]{"7", "1", "14", "0"}, 0));
        } else if (subject.equals("Maths")) {
            questions.add(new Question("What is 2 + 2?", new String[]{"4", "3", "5", "6"}, 0));
            questions.add(new Question("What is the square root of 16?", new String[]{"4", "3", "5", "6"}, 0));
            questions.add(new Question("What is the value of pi?", new String[]{"3.14", "3.15", "3.16", "3.17"}, 0));
            questions.add(new Question("What is 5 * 5?", new String[]{"25", "20", "30", "15"}, 0));
            questions.add(new Question("What is the derivative of x^2?", new String[]{"2x", "x^2", "x", "1"}, 0));
        } else if (subject.equals("C++")) {
            questions.add(new Question("What is the output of cout << 'A' + 1;", new String[]{"66", "A1", "B", "Error"}, 0));
            questions.add(new Question("What is the size of int in C++?", new String[]{"4 bytes", "2 bytes", "8 bytes", "1 byte"}, 0));
            questions.add(new Question("What is the correct way to declare a pointer?", new String[]{"int *p", "int p*", "p int*", "int &p"}, 0));
            questions.add(new Question("What is the output of 5 / 2 in C++?", new String[]{"2", "2.5", "3", "Error"}, 0));
            questions.add(new Question("What is the keyword to create a class in C++?", new String[]{"class", "Class", "object", "new"}, 0));
        } else if (subject.equals("Java")) {
            questions.add(new Question("What is the output of System.out.println(5 + 5);", new String[]{"55", "10", "Error", "50"}, 0));
            questions.add(new Question("What is the size of int in Java?", new String[]{"4 bytes", "2 bytes", "8 bytes", "1 byte"}, 0));
            questions.add(new Question("What is the correct way to declare a string?", new String[]{"String s = 'Hello';", "String s = \"Hello\";", "String s = Hello;", "String s = 'H' + 'e' + 'l' + 'l' + 'o';"}, 0));
            questions.add(new Question("What is the output of 5 / 2 in Java?", new String[]{"2.5", "2", "3", "Error"}, 0));
            questions.add(new Question("What is the keyword to create a class in Java?", new String[]{"class", "Class", "object", "new"}, 0));
        } else if (subject.equals("Statistics")) {
            questions.add(new Question("What is the mean of {1, 2, 3, 4, 5}?", new String[]{"3", "2", "4", "5"}, 0));
            questions.add(new Question("What is the median of {1, 2, 3, 4, 5}?", new String[]{"3", "2", "4", "5"}, 0));
            questions.add(new Question("What is the mode of {1, 2, 2, 3, 4}?", new String[]{"2", "1", "3", "4"}, 0));
            questions.add(new Question("What is the standard deviation of {1, 2, 3, 4, 5}?", new String[]{"1.41", "1.58", "1.73", "1.87"}, 0));
            questions.add(new Question("What is the variance of {1, 2, 3, 4, 5}?", new String[]{"2", "3", "4", "5"}, 0));
        }

        return questions;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (candidateData.containsKey(username) && candidateData.get(username)[0].equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            String subject = candidateData.get(username)[1];
            selectedQuestions = loadQuestionsForSubject(subject);

            getContentPane().removeAll();
            add(questionPanel);
            revalidate();
            repaint();

            currentQuestionIndex = 0;
            score = 0;
            showQuestion(currentQuestionIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showQuestion(int index) {
        if (index < selectedQuestions.size()) {
            Question q = selectedQuestions.get(index);
            questionLabel.setText((index + 1) + ". " + q.getQuestion());

            String[] optionsText = q.getOptions();
            for (int i = 0; i < options.length; i++) {
                options[i].setText(optionsText[i]);
                optionsGroup.clearSelection(); // Ensure no preselection
            }

            nextButton.setEnabled(index < selectedQuestions.size() - 1);
            submitButton.setEnabled(index == selectedQuestions.size() - 1);
        }
    }

    private void handleNextQuestion() {
        if (currentQuestionIndex < selectedQuestions.size() - 1) {
            recordAnswer();
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
        }
    }

    private void handleSubmit() {
        recordAnswer();

        double percentage = (score / 5.0) * 100;
        JOptionPane.showMessageDialog(this, "Exam Completed!\nScore: " + score + "/5\nPercentage: " + percentage + "%");

        // Reset back to login panel
        getContentPane().removeAll();
        add(loginPanel);
        revalidate();
        repaint();
    }

    private void recordAnswer() {
        Question q = selectedQuestions.get(currentQuestionIndex);
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected() && i == q.getCorrectOption()) {
                score++;
            }
        }
    }

    public static void main(String[] args) {
        new QuestionBankGUI();
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}