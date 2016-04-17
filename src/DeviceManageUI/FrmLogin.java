package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
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

public class FrmLogin extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnLogin = new Button("登陆");
	private Button btnCancel = new Button("退出");
	private Button btnRegister = new Button("注册");

	private JLabel labelManager = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserName = new JTextField(18);
	private JPasswordField edtPwd = new JPasswordField(18);

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelManager);
		workPane.add(edtUserName);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 160);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			// 补充代码，实现用户登陆
			SystemManager sum = new SystemManager();
			String managername = this.edtUserName.getText();
			String pwd = new String(this.edtPwd.getPassword());
			try {
				Manager manager = sum.loadManager(managername);
				if (pwd.equals(manager.getPassWord())) {
					SystemManager.currentManager = manager;
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "密码错误", "错误提示",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if (e.getSource() == this.btnRegister) {
			FrmRegister dlg = new FrmRegister(this, "注册", true);
			dlg.setVisible(true);
		}
	}

}
