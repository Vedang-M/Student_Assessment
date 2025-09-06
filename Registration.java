import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Registration {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration Form");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Name Field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25));
        panel.add(nameLabel);
        panel.add(nameField);

        // Subject Selection
        JLabel subjectLabel = new JLabel("Select a subject for registration:");
        JPanel subjectPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JRadioButton mathsButton = new JRadioButton("Maths");
        JRadioButton chemistryButton = new JRadioButton("Chemistry");
        JRadioButton physicsButton = new JRadioButton("Physics");
        JRadioButton javaButton = new JRadioButton("Java");
        JRadioButton cppButton = new JRadioButton("C++");
        JRadioButton statsButton = new JRadioButton("Statistics");

        ButtonGroup subjectGroup = new ButtonGroup();
        subjectGroup.add(mathsButton);
        subjectGroup.add(chemistryButton);
        subjectGroup.add(physicsButton);
        subjectGroup.add(javaButton);
        subjectGroup.add(cppButton);
        subjectGroup.add(statsButton);

        subjectPanel.add(mathsButton);
        subjectPanel.add(chemistryButton);
        subjectPanel.add(physicsButton);
        subjectPanel.add(javaButton);
        subjectPanel.add(cppButton);
        subjectPanel.add(statsButton);

        panel.add(subjectLabel);
        panel.add(subjectPanel);

        // Age Field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        ageField.setPreferredSize(new Dimension(200, 25));
        ageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume(); // Ignore non-digit input
                }
            }
        });
        panel.add(ageLabel);
        panel.add(ageField);

        // Gender Selection
        JLabel genderLabel = new JLabel("Gender:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        panel.add(genderLabel);
        panel.add(genderPanel);

        // Address Field
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(200, 25));
        panel.add(addressLabel);
        panel.add(addressField);

        // State Dropdown
        JLabel stateLabel = new JLabel("State:");
        String[] states = {"Maharashtra", "Andhra Pradesh", "Tamil Nadu", "Gujarat", "Rajasthan", "Karnataka", "Punjab", "Assam", "West Bengal"};
        JComboBox<String> stateDropdown = new JComboBox<>(states);
        panel.add(stateLabel);
        panel.add(stateDropdown);

        // City Field
        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField();
        cityField.setPreferredSize(new Dimension(200, 25));
        panel.add(cityLabel);
        panel.add(cityField);

        // Pincode Field
        JLabel pincodeLabel = new JLabel("Pincode:");
        JTextField pincodeField = new JTextField();
        pincodeField.setPreferredSize(new Dimension(200, 25));
        pincodeField.setDocument(new JTextFieldLimit(6));
        pincodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        panel.add(pincodeLabel);
        panel.add(pincodeField);

        // Phone Number Field
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 25));
        phoneField.setDocument(new JTextFieldLimit(10));
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        panel.add(phoneLabel);
        panel.add(phoneField);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        panel.add(new JLabel());
        panel.add(submitButton);

        // Table for displaying data
        String[] columnNames = {"Name", "Subject", "Age", "Gender", "Address", "State", "City", "Pincode", "Phone"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.SOUTH);

        // Submit Button Action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
                String address = addressField.getText().trim();
                String state = (String) stateDropdown.getSelectedItem();
                String city = cityField.getText().trim();
                String pincode = pincodeField.getText().trim();
                String phone = phoneField.getText().trim();

                // Collect selected subject
                String selectedSubject = "";
                if (mathsButton.isSelected()) selectedSubject = "Maths";
                else if (chemistryButton.isSelected()) selectedSubject = "Chemistry";
                else if (physicsButton.isSelected()) selectedSubject = "Physics";
                else if (javaButton.isSelected()) selectedSubject = "Java";
                else if (cppButton.isSelected()) selectedSubject = "C++";
                else if (statsButton.isSelected()) selectedSubject = "Statistics";

                // Validate fields
                if (name.isEmpty() || selectedSubject.isEmpty() || ageText.isEmpty() || gender.isEmpty() ||
                    address.isEmpty() || city.isEmpty() || pincode.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validate age
                int age;
                try {
                    age = Integer.parseInt(ageText);
                    if (age <= 0 || age > 120) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid age!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Age must be a number!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Check for duplicates
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(name)) {
                        JOptionPane.showMessageDialog(frame, "Duplicate entry! Name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (tableModel.getValueAt(i, 8).equals(phone)) {
                        JOptionPane.showMessageDialog(frame, "Duplicate entry! Phone number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Write data to CSV and table
                try (FileWriter fw = new FileWriter("C:\\VESIT\\Java\\JavaProjects\\gui\\Data.csv", true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter pw = new PrintWriter(bw)) {

                    File file = new File("Data.csv");
                    if (file.length() == 0) { // Add header if file is empty
                        pw.println("Name,Subject,Age,Gender,Address,State,City,Pincode,Phone");
                    }

                    String data = String.format("%s,%s,%d,%s,%s,%s,%s,%s,%s", 
                        name, selectedSubject, age, gender, address, state, city, pincode, phone);
                    pw.println(data);

                    // Add to table
                    tableModel.addRow(new Object[]{name, selectedSubject, age, gender, address, state, city, pincode, phone});

                    JOptionPane.showMessageDialog(frame, "Registration Successful! Data saved to Data.csv.");

                    // Reset fields
                    nameField.setText("");
                    ageField.setText("");
                    genderGroup.clearSelection();
                    addressField.setText("");
                    stateDropdown.setSelectedIndex(0);
                    cityField.setText("");
                    pincodeField.setText("");
                    phoneField.setText("");
                    subjectGroup.clearSelection();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}

class JTextFieldLimit extends javax.swing.text.PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}