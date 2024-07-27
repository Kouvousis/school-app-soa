package gr.aueb.cf.schoolapp.viewcontroller;

import gr.aueb.cf.schoolapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentsMenuFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public StudentsMenuFrame() {
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(Thread.currentThread()
                        .getContextClassLoader()
                        .getResource("eduv2.png")));
        setTitle("Students Menu");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton studentsViewBtn = new JButton("View Students");
        studentsViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getStudentsUpdateDeleteFrame().setVisible(true);
                Main.getStudentsMenuFrame().setEnabled(false);
            }
        });
        studentsViewBtn.setForeground(new Color(18, 18, 194));
        studentsViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        studentsViewBtn.setBounds(133, 45, 140, 39);
        contentPane.add(studentsViewBtn);

        JButton studentInsertBtn = new JButton("Insert Student");
        studentInsertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getStudentsInsertFrame().setVisible(true);
                Main.getStudentsMenuFrame().setEnabled(false);
            }
        });
        studentInsertBtn.setForeground(new Color(18, 18, 194));
        studentInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        studentInsertBtn.setBounds(133, 106, 140, 39);
        contentPane.add(studentInsertBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.getMainMenuFrame().setEnabled(true);
                Main.getStudentsMenuFrame().setVisible(false);
            }
        });
        closeBtn.setForeground(new Color(18, 18, 194));
        closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        closeBtn.setBounds(320, 219, 89, 31);
        contentPane.add(closeBtn);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 207, 434, 1);
        contentPane.add(separator);
    }
}
