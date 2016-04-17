package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
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

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import DeviceManageControl.*;
import DeviceManageModel.*;
import DeviceManageUI.*;
import DeviceManageUtil.*;

public class FrmScrapDevice extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JComboBox Devices = new JComboBox();
	private JLabel labelName = new JLabel("设备名称");
	private JLabel labelNum = new JLabel("报废数量");
	private JLabel labelnull = new JLabel("                                 ");
	private JTextField edtNum = new JTextField(18);
	String Ddevice = null;
	int flag = 1;

	public FrmScrapDevice(JFrame f, String s, boolean b) throws SQLException {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		Connection conn = DBUtil.getConnection();
		String sql = "select DeviceName from Device where DeleteTime is null";
		java.sql.Statement st = conn.createStatement();
		java.sql.ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			if (flag == 1) {
				Ddevice = rs.getString(1);
				flag = 0;
			}
			Devices.addItem(rs.getString(1));
		}
		workPane.add(labelName);
		workPane.add(Devices);
		workPane.add(labelnull);
		workPane.add(labelNum);
		workPane.add(edtNum);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 200);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) throws NumberFormatException {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				ScrapDevice s = new ScrapDevice();
				int InNum = Integer.parseInt(this.edtNum.getText());
				Ddevice = (String) this.Devices.getSelectedItem();
				String sql = "select Count from Device where DeviceName='"
						+ Ddevice + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) - InNum;
				if (num < 0) {
					JOptionPane.showMessageDialog(null, "报废量超额", "错误",
							JOptionPane.ERROR_MESSAGE);
				} else if (InNum == 0) {
					JOptionPane.showMessageDialog(null, "报废量不能为0", "错误",
							JOptionPane.ERROR_MESSAGE);
				} else {
					s.setCount(InNum);
					s.setScrapDeviceName(Ddevice);
					sql = "select DeviceType from Device where DeviceName='"
							+ Ddevice + "' and DeleteTime is null";
					java.sql.Statement pst = conn.createStatement();
					java.sql.ResultSet rst = pst.executeQuery(sql);
					rst.next();
					s.setScrapDeviceType(rst.getString(1));
					Date now = new Date();

					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
					String time = dateFormat.format(now);
					s.setScrapTime(time);
					s.setManager(SystemManager.currentManager.getManagerName());
					new ScrapDeviceManager().Scrap(s);
					this.setVisible(false);
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "格式输入错误", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
