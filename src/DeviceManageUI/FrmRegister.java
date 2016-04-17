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

public class FrmRegister extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("注册");
	private Button btnCancel = new Button("取消");

	private JLabel labelManager = new JLabel("    用户：   ");
	private JLabel labelPwd = new JLabel("输入密码：");
	private JLabel labelPwd2 = new JLabel("重复密码：");
	private JTextField edtManagerId = new JTextField(18);
	private JPasswordField edtPwd = new JPasswordField(18);
	private JPasswordField edtPwd2 = new JPasswordField(18);

	public FrmRegister(Dialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelManager);
		workPane.add(edtManagerId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 200);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			java.sql.Connection conn = null;
			// 补充注册相关代码
			String managername = this.edtManagerId.getText();
			String pwd = new String(this.edtPwd.getPassword());
			String pwd2 = new String(this.edtPwd2.getPassword());
			if (!pwd.equals(pwd2))
				JOptionPane.showMessageDialog(null, "两次密码输入不一致", "错误提示",
						JOptionPane.ERROR_MESSAGE);
			Manager manager = new Manager();
			try {
				conn = DBUtil.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String sql = "select max(Managerid) from Manager";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) + 1;
				manager.setManagerId(num);
				manager.setPassWord(pwd);
				manager.setManagerName(managername);
				this.setVisible(false);
				new SystemManager().createManager(manager);
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
