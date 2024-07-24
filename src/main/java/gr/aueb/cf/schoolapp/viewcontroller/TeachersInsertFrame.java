package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TeachersInsertFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JLabel errorFirstName;
    private JLabel errorLastName;

    public TeachersInsertFrame() {
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                firstNameText.setText("");
                lastNameText.setText("");
                errorFirstName.setText("");
                errorLastName.setText("");
            }
        });
        setTitle("Insert");
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource("eduv2.png")));
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(new Color(0, 0, 255));
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        firstNameLabel.setBounds(10, 46, 64, 23);
        contentPane.add(firstNameLabel);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.BLUE);
        lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lastNameLabel.setBounds(10, 109, 64, 23);
        contentPane.add(lastNameLabel);

        firstNameText = new JTextField();
        firstNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String inputFirstName = firstNameText.getText().trim();
                if (inputFirstName.equals("")) {
                    errorFirstName.setText("Name is required");
                } else {
                    errorFirstName.setText("");
                }
            }
        });
        firstNameText.setBounds(98, 47, 232, 20);
        contentPane.add(firstNameText);
        firstNameText.setColumns(10);

        lastNameText = new JTextField();
        lastNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String inputLastName = lastNameText.getText().trim();
                if (inputLastName.equals("")) {
                    errorLastName.setText("Last name is required");
                } else {
                    errorLastName.setText("");
                }
            }
        });
        lastNameText.setColumns(10);
        lastNameText.setBounds(98, 110, 232, 20);
        contentPane.add(lastNameText);

        JButton insertBtn = new JButton("Insert");
        insertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFirstName = firstNameText.getText().trim();
                String inputLastName = lastNameText.getText().trim();
                
                if (inputFirstName.equals("")) {
                    errorFirstName.setText("Name is required");
                } else {
                    errorFirstName.setText("");
                }

                if (inputLastName.equals("")) {
                    errorLastName.setText("Last name is required");
                } else {
                    errorLastName.setText("");
                }

                if (inputFirstName.equals("") || inputLastName.equals("")) {
                    return;
                }

                String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";

                try (Connection conn = DBUtil.getConnection(); 
                		PreparedStatement ps = conn.prepareStatement(sql))
                {
                    
                    ps.setString(1, inputFirstName);
                    ps.setString(2, inputLastName);

                    int n = ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, n + " records were inserted", "INSERT", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException e1) {
                    //e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Insertion error", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        insertBtn.setForeground(new Color(0, 0, 255));
        insertBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        insertBtn.setBounds(241, 227, 89, 23);
        contentPane.add(insertBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getTeachersMenuFrame().setEnabled(true);
                Main.getTeachersInsertFrame().setVisible(false);
            }
        });
        closeBtn.setForeground(new Color(0, 0, 255));
        closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        closeBtn.setBounds(340, 227, 89, 23);
        contentPane.add(closeBtn);

        errorFirstName = new JLabel("");
        errorFirstName.setForeground(new Color(255, 0, 0));
        errorFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        errorFirstName.setBounds(98, 69, 232, 23);
        contentPane.add(errorFirstName);

        errorLastName = new JLabel("");
        errorLastName.setForeground(Color.RED);
        errorLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        errorLastName.setBounds(98, 134, 232, 23);
        contentPane.add(errorLastName);
    }
}
