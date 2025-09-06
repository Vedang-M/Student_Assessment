import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class AdminPageWithBorders {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static ArrayList<String[]> studentList = new ArrayList<>();

    public static void main(String[] args) {
        // Load student data from CSV
        loadStudentData();

        // Create login frame
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(350, 200);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(10);
        JPasswordField passField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        // Add border to the panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel", TitledBorder.LEFT, TitledBorder.TOP));

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginFrame.add(panel);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                loginFrame.dispose();
                showAdminPage();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials!");
            }
        });
    }

    private static void loadStudentData() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\VESIT\\Java\\JavaProjects\\gui\\Data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                studentList.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
    }

    private static void saveStudentData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Data.csv"))) {
            for (String[] student : studentList) {
                pw.println(String.join(",", student));
            }
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }

    private static void showAdminPage() {
        JFrame adminFrame = new JFrame("Admin Page");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(600, 400);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String[] student : studentList) {
            listModel.addElement(String.join(" | ", student));
        }
        JList<String> studentJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(studentJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Student List", TitledBorder.LEFT, TitledBorder.TOP));

        JButton deleteButton = new JButton("Delete");
        JButton modifyButton = new JButton("Modify");

        // Add borders to button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Actions", TitledBorder.LEFT, TitledBorder.TOP));
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);

        adminFrame.add(scrollPane, BorderLayout.CENTER);
        adminFrame.add(buttonPanel, BorderLayout.SOUTH);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);

        deleteButton.addActionListener(e -> {
            int selectedIndex = studentJList.getSelectedIndex();
            if (selectedIndex != -1) {
                studentList.remove(selectedIndex);
                listModel.remove(selectedIndex);
                saveStudentData();
            } else {
                JOptionPane.showMessageDialog(adminFrame, "No student selected!");
            }
        });

        modifyButton.addActionListener(e -> {
            int selectedIndex = studentJList.getSelectedIndex();
            if (selectedIndex != -1) {
                String[] student = studentList.get(selectedIndex);
        
                // Fields for modification
                JTextField nameField = new JTextField(student[0]);
                JComboBox<String> subjectDropdown = new JComboBox<>(new String[]{"Maths", "Chemistry", "Physics", "Java", "C++", "Statistics"});
                subjectDropdown.setSelectedItem(student[1]); // Set current subject
                JTextField ageField = new JTextField(student[2]);
        
                // Panel for modification
                JPanel modifyPanel = new JPanel(new GridLayout(3, 2));
                modifyPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), "Modify Student Details", TitledBorder.LEFT, TitledBorder.TOP));
                modifyPanel.add(new JLabel("Name:"));
                modifyPanel.add(nameField);
                modifyPanel.add(new JLabel("Subject:"));
                modifyPanel.add(subjectDropdown);
                modifyPanel.add(new JLabel("Age:"));
                modifyPanel.add(ageField);
        
                int result = JOptionPane.showConfirmDialog(
                        adminFrame, modifyPanel, "Modify Student", JOptionPane.OK_CANCEL_OPTION);
        
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Validate age
                        int age = Integer.parseInt(ageField.getText());
                        if (age <= 0 || age > 120) {
                            JOptionPane.showMessageDialog(adminFrame, "Please enter a valid age!", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
        
                        // Update student details
                        student[0] = nameField.getText();
                        student[1] = (String) subjectDropdown.getSelectedItem();
                        student[2] = String.valueOf(age);
        
                        // Update list model and save data
                        listModel.set(selectedIndex, String.join(" | ", student));
                        saveStudentData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(adminFrame, "Age must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "No student selected!");
            }
        });
    }
}