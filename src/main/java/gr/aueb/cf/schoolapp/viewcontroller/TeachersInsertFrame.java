package gr.aueb.cf.schoolapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptios.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

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
import java.util.Map;

public class TeachersInsertFrame extends JFrame {

    // Wiring
    private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

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

                Map<String, String> errors;
                TeacherInsertDTO insertDTO = new TeacherInsertDTO();
                String firstnameMessage;
                String lastnameMessage;
                Teacher teacher;

                try {
                    insertDTO.setFirstname(firstNameText.getText().trim());
                    insertDTO.setLastname(lastNameText.getText().trim());

                    // Validation
                    errors = TeacherValidator.validate(insertDTO);

                    if (!errors.isEmpty()) {
                        firstnameMessage = errors.containsKey("firstname") ? errors.get("firstname") : "";
                        lastnameMessage = errors.containsKey("lastname") ? errors.get("lastname") : "";

//                        if (!firstnameMessage.isEmpty()) {
//                            errorFirstName.setText(firstnameMessage);
//                        }

                        errorFirstName.setText(firstnameMessage);

                        if (!lastnameMessage.isEmpty()) {
                            errorLastName.setText(lastnameMessage);
                        }

//                        if (firstnameMessage.isEmpty()) {
//                            errorFirstName.setText("");
//                        }

                        if (lastnameMessage.isEmpty()) {
                            errorLastName.setText("");
                        }

                        if (!firstnameMessage.isEmpty() || !lastnameMessage.isEmpty()) {
                            return;
                        }
                    }

                    teacher = teacherService.insertTeacher(insertDTO);
                    TeacherReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(teacher);
                    JOptionPane.showMessageDialog(null,
                            "Teacher with lastname: " + readOnlyDTO.getLastname(),
                            "Insert teacher",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (TeacherDAOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Insertion Error",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
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



    private TeacherReadOnlyDTO mapToReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getFirstname(), teacher.getLastname());
    }
}
