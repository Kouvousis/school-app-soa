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

public class TeachersMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public TeachersMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Thread.currentThread()
						.getContextClassLoader()
						.getResource("eduv2.png")));
		setTitle("Teachers Menu");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton teachersViewBtn = new JButton("View Teachers");
		teachersViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersUpdateDeleteFrame().setVisible(true);
				Main.getTeachersMenuFrame().setEnabled(false);
			}
		});
		teachersViewBtn.setForeground(new Color(18, 18, 194));
		teachersViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teachersViewBtn.setBounds(133, 45, 140, 39);
		contentPane.add(teachersViewBtn);
		
		JButton teacherInsertBtn = new JButton("Insert Teacher");
		teacherInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getTeachersInsertFrame().setVisible(true);
				Main.getTeachersMenuFrame().setEnabled(false);
			}
		});
		teacherInsertBtn.setForeground(new Color(18, 18, 194));
		teacherInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teacherInsertBtn.setBounds(133, 106, 140, 39);
		contentPane.add(teacherInsertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getMainMenuFrame().setEnabled(true);
				Main.getTeachersMenuFrame().setVisible(false);
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
