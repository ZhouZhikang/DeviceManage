package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DeviceManageControl.*;
import DeviceManageModel.*;
import DeviceManageUI.*;
import DeviceManageUtil.*;

public class FrmPersonRegister extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("注册");
	private Button btnCancel = new Button("取消");

	private JLabel labelPerson = new JLabel("  用户  ");
	private JLabel labelDepartment = new JLabel("  单位  ");
	private JTextField edtPersonId = new JTextField(18);
	private JTextField edtDepartment = new JTextField(18);

	public FrmPersonRegister(JFrame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelPerson);
		workPane.add(edtPersonId);
		workPane.add(labelDepartment);
		workPane.add(edtDepartment);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(310, 180);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			java.sql.Connection conn = null;
			// 补充注册相关代码
			String personname = this.edtPersonId.getText();
			String dep = this.edtDepartment.getText();
			Person p = new Person();
			try {
				conn = DBUtil.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String sql = "select max(PersonId) from Person";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) + 1;
				p.setPersonName(personname);
				p.setDepartment(dep);
				this.setVisible(false);
				new PersonManager().createPerson(p);
				JOptionPane.showMessageDialog(null, "注册成功", "提示",
						JOptionPane.WARNING_MESSAGE);
			} catch (BaseException ep) {
				// TODO Auto-generated catch block
				// ep.printStackTrace();
				JOptionPane.showMessageDialog(null, ep.getMessage(), "错误提示",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
