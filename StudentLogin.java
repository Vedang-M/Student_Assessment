import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class StudentLogin extends JFrame {
    JLabel usernameLabel, passwordLabel, feeLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, payFeesButton;
    JComboBox<String> subjectsComboBox;
    JLabel totalFeeLabel;
    int feePerSubject = 500; // Fee per subject

    // Map to hold student name and mobile number pairs loaded from Data.csv
    Map<String, String> credentials = new HashMap<>();

    public StudentLogin() {
        setTitle("Student Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        usernameLabel = new JLabel("Student Name:");
        passwordLabel = new JLabel("Mobile Number:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.NORTH);

        // Subjects Selection
        JPanel subjectsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel subjectsLabel = new JLabel("Select Subject:");
        subjectsComboBox = new JComboBox<>();
        subjectsComboBox.setEnabled(false); // Disable until login
        subjectsPanel.add(subjectsLabel);
        subjectsPanel.add(subjectsComboBox);
        add(subjectsPanel, BorderLayout.CENTER);

        // Fee Panel
        JPanel feePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalFeeLabel = new JLabel("Total Fee: ₹0");
        payFeesButton = new JButton("Pay Fees");
        payFeesButton.setEnabled(false);

        feePanel.add(totalFeeLabel);
        feePanel.add(payFeesButton);
        add(feePanel, BorderLayout.SOUTH);

        // Load credentials from Data.csv
        loadCredentials();

        // Action Listeners
        loginButton.addActionListener(e -> handleLogin());
        payFeesButton.addActionListener(e -> handleFeePayment());

        setVisible(true);
    }

    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\VESIT\\Java\\JavaProjects\\gui\\Data.csv"))) {
            String line;

            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 8) { // Ensure sufficient columns
                    String username = data[0].trim();
                    String password = data[8].trim();
                    credentials.put(username, password);
                }
            }

            // Debug: Print all credentials
            for (Map.Entry<String, String> entry : credentials.entrySet()) {
                System.out.println("Username: " + entry.getKey() + ", Password: " + entry.getValue());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading Data.csv file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both student name and mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate student name and mobile number against the loaded credentials
        if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadSubjects(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid student name or mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSubjects(String username) {
        // Enable the subjects combo box
        subjectsComboBox.setEnabled(true);

        // Clear existing data
        subjectsComboBox.removeAllItems();

        // Simulate loading subjects from a file (replace with actual file reading logic)
        String[] subjects = {"Maths", "Chemistry", "Physics", "Java", "C++", "Statistics"};
        for (String subject : subjects) {
            subjectsComboBox.addItem(subject);
        }

        payFeesButton.setEnabled(true);
    }

    private void handleFeePayment() {
        String selectedSubject = (String) subjectsComboBox.getSelectedItem();

        if (selectedSubject == null || selectedSubject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a subject to pay fees.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalFee = feePerSubject;
        totalFeeLabel.setText("Total Fee: ₹" + totalFee);

        // Save payment details to a file
        savePaymentDetails(usernameField.getText().trim(), selectedSubject, totalFee);

        JOptionPane.showMessageDialog(this, "Fees paid successfully for " + selectedSubject + "! Total Fee: ₹" + totalFee, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void savePaymentDetails(String username, String subject, int totalFee) {
        try (FileWriter fw = new FileWriter("fees_data.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(username + "," + subject + "," + totalFee);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving payment details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new StudentLogin();
    }
}