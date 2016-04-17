package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DeviceManageControl.*;
import DeviceManageModel.*;
import DeviceManageUI.*;
import DeviceManageUtil.*;

public class FrmDeleteType extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelType = new JLabel("设备类型");
	private JComboBox Types = new JComboBox();
	private JLabel labelnull = new JLabel("                ");
	String DType = "";
	int flag = 1;

	public FrmDeleteType(JFrame f, String s, boolean b) throws SQLException {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		Connection conn = DBUtil.getConnection();
		String sql = "select DeviceTypeName from DeviceType where DeleteTime is null";
		java.sql.Statement st = conn.createStatement();
		java.sql.ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			if (flag == 1) {
				DType = (rs.getString(1));
				flag = 0;
			}
			Types.addItem(rs.getString(1));
		}
		Types.addActionListener(this);
		workPane.add(labelType);
		workPane.add(Types);
		workPane.add(labelnull);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 120);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			try {
				DType = (String) this.Types.getSelectedItem();
				this.setVisible(false);
				new DeviceTypeManager().DeleteDeviceType(DType);
			} catch (BaseException ep) {
				JOptionPane.showMessageDialog(null, ep.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				ep.printStackTrace();
			}
		}
	}
}
