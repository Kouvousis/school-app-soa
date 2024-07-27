package gr.aueb.cf.schoolapp.viewcontroller;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.StudentDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptios.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.StudentServiceImpl;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolapp.validator.StudentValidator;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class StudentsUpdateDeleteFrame extends JFrame {

    // Wiring
    private final IStudentDAO studentDAO = new StudentDAOImpl();
    private final IStudentService studentService = new StudentServiceImpl(studentDAO);
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable teachersTable;
    private DefaultTableModel model = new DefaultTableModel();
    private JLabel lastNameSearchLabel;
    private JTextField lastNameSearchText;
    private JLabel idLabel;
    private JTextField idText;
    private JLabel firstNameLabel;
    private JTextField firstNameText;
    private JLabel lastNameLabel;
    private JTextField lastNameText;
    private JLabel errorFirstName;
    private JLabel errorLastName;
    private JPanel panel;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton closeBtn;

    public StudentsUpdateDeleteFrame() {
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource("eduv2.png")));
        setTitle("Update / Delete Student");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                lastNameSearchText.setText("");
                buildTable();	// initial render
                idText.setText("");
                firstNameText.setText("");
                lastNameText.setText("");
            }
            @Override
            public void windowActivated(WindowEvent e) {
                lastNameSearchText.setText("");
                buildTable();	// refresh after update / delete
                idText.setText("");
                firstNameText.setText("");
                lastNameText.setText("");
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 600, 410);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        teachersTable = new JTable();
        teachersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                idText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 0));
                firstNameText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 1));
                lastNameText.setText((String) model.getValueAt(teachersTable.getSelectedRow(), 2));
            }
        });
        teachersTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "First Name", "Last Name"}
        ));

        model = (DefaultTableModel) teachersTable.getModel();

        teachersTable.setBounds(10, 54, 221, 291);
        contentPane.add(teachersTable);

        JScrollPane scrollPane = new JScrollPane(teachersTable);
        scrollPane.setBounds(10, 54, 286, 291);
        contentPane.add(scrollPane);

        lastNameSearchLabel = new JLabel("Last Name");
        lastNameSearchLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lastNameSearchLabel.setForeground(new Color(128, 0, 64));
        lastNameSearchLabel.setBounds(10, 11, 75, 20);
        contentPane.add(lastNameSearchLabel);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buildTable();
            }
        });
        btnSearch.setForeground(new Color(0, 0, 255));
        btnSearch.setBounds(324, 11, 88, 21);
        contentPane.add(btnSearch);

        lastNameSearchText = new JTextField();
        lastNameSearchText.setBounds(95, 11, 219, 21);
        contentPane.add(lastNameSearchText);
        lastNameSearchText.setColumns(10);

        idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idLabel.setForeground(new Color(0, 0, 255));
        idLabel.setBounds(337, 59, 75, 19);
        contentPane.add(idLabel);

        idText = new JTextField();
        idText.setEditable(false);
        idText.setBounds(422, 58, 57, 20);
        contentPane.add(idText);
        idText.setColumns(10);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(new Color(0, 0, 255));
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        firstNameLabel.setBounds(337, 89, 75, 20);
        contentPane.add(firstNameLabel);

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
            }}
        );
        firstNameText.setBounds(422, 89, 125, 20);
        contentPane.add(firstNameText);
        firstNameText.setColumns(10);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lastNameLabel.setForeground(new Color(0, 0, 255));
        lastNameLabel.setBounds(337, 135, 75, 21);
        contentPane.add(lastNameLabel);

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
                                      }
        );
        lastNameText.setBounds(422, 135, 125, 20);
        contentPane.add(lastNameText);
        lastNameText.setColumns(10);

        errorLastName = new JLabel("");
        errorLastName.setForeground(new Color(255, 0, 0));
        errorLastName.setBounds(422, 157, 125, 20);
        contentPane.add(errorLastName);

        panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(306, 54, 268, 135);
        contentPane.add(panel);
        panel.setLayout(null);

        errorFirstName = new JLabel("");
        errorFirstName.setForeground(new Color(255, 0, 0));
        errorFirstName.setBounds(115, 54, 125, 20);
        panel.add(errorFirstName);

        updateBtn = new JButton("Update");
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, String> errors;
                String firstnameMessage;
                String lastnameMessage;
                Student student;

                if (idText.getText().trim().isEmpty()) return;

                try {
                    StudentUpdateDTO updateDTO = new StudentUpdateDTO();
                    updateDTO.setId(Integer.parseInt(idText.getText().trim()));
                    updateDTO.setFirstname(firstNameText.getText().trim());
                    updateDTO.setLastname(lastNameText.getText().trim());

                    errors = StudentValidator.validate(updateDTO);

                    if (!errors.isEmpty()) {
                        firstnameMessage = errors.getOrDefault("firstname", "");
                        lastnameMessage = errors.getOrDefault("lastname", "");
                        errorFirstName.setText(firstnameMessage);
                        errorLastName.setText(lastnameMessage);
                        return;
                    }

                    student = studentService.updateStudent(updateDTO);
                    StudentReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(student);

                    JOptionPane.showMessageDialog(null,
                            "Student with id: " + readOnlyDTO.getId() + " was updated",
                            "Update",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (StudentDAOException | StudentNotFoundException e1) {
                    //e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        updateBtn.setForeground(new Color(0, 0, 255));
        updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        updateBtn.setBounds(323, 224, 89, 23);
        contentPane.add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response;

                try {
                    if (idText.getText().trim().isEmpty()) return;
                    int inputId = Integer.parseInt(idText.getText().trim());

                    response = JOptionPane.showConfirmDialog(null,
                            "Are you sure?",
                            "Warning",
                            JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        studentService.deleteStudent(inputId);
                        JOptionPane.showMessageDialog(null,
                                "Student was deleted successfully",
                                "Delete",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (StudentDAOException | StudentNotFoundException ex) {
                    //ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,  ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteBtn.setForeground(Color.BLUE);
        deleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        deleteBtn.setBounds(458, 224, 89, 23);
        contentPane.add(deleteBtn);

        closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getStudentsMenuFrame().setEnabled(true);
                Main.getStudentsUpdateDeleteFrame().setVisible(false);
            }
        });
        closeBtn.setForeground(Color.BLUE);
        closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        closeBtn.setBounds(485, 322, 89, 23);
        contentPane.add(closeBtn);
    }

    private void buildTable() {

        Vector<String> vector;
        java.util.List<StudentReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
        StudentReadOnlyDTO readOnlyDTO;

        try  {
            String searchStr = lastNameSearchText.getText().trim();

            List<Student> students = studentService.getStudentsByLastname(searchStr);
            for (Student student : students) {
                readOnlyDTO = mapToReadOnlyDTO(student);
                readOnlyDTOS.add(readOnlyDTO);
            }

            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }

            for (StudentReadOnlyDTO studentReadOnlyDTO : readOnlyDTOS) {
                vector= new Vector<>(3);
                vector.add(String.valueOf(studentReadOnlyDTO.getId()));
                vector.add(studentReadOnlyDTO.getFirstname());
                vector.add(studentReadOnlyDTO.getLastname());
                model.addRow(vector);
            }

        } catch (StudentDAOException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateFirstname(String inputFirstname) {
        if (inputFirstname.isEmpty()) {
            errorFirstName.setText("Name is required");
        }

        if (inputFirstname.isEmpty()) {
            errorFirstName.setText("");
        }
    }

    private void validateLastname(String inputLastname) {
        if (inputLastname.isEmpty()) {
            errorLastName.setText("Lastname is required");
        }

        if (inputLastname.isEmpty()) {
            errorFirstName.setText("");
        }
    }


    private StudentReadOnlyDTO mapToReadOnlyDTO(Student student) {
        return new StudentReadOnlyDTO(student.getId(), student.getFirstname(), student.getLastname());
    }
}
