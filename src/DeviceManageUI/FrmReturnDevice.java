package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class FrmReturnDevice extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel PersonName = new JLabel("人员/单位");
	private JLabel labelDevice = new JLabel("设备名称");
	private JLabel Count = new JLabel("    归还数量");
	private JComboBox person = new JComboBox();
	private JComboBox Devices = new JComboBox();
	private JTextField edtCount = new JTextField(18);
	// private JLabel labelnull = new
	// JLabel("                                   ");
	// private JLabel labelnull2 = new JLabel(
	// "                                       ");
	String Ddevice = null;
	String per = null;
	int flag1 = 1, flag2 = 1;

	public FrmReturnDevice(JFrame f, String s, boolean b) throws SQLException {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		Connection conn = DBUtil.getConnection();
		String sql = "select Person from WareHouse group by Person";
		java.sql.Statement st = conn.createStatement();
		java.sql.ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			if (flag1 == 1) {
				per = rs.getString(1);
				flag1 = 0;
			}
			person.addItem(rs.getString(1));
		}
		conn = DBUtil.getConnection();
		sql = "select Device from WareHouse where Person='"
				+ person.getSelectedItem() + "'";
		java.sql.Statement pst = conn.createStatement();
		java.sql.ResultSet rst = pst.executeQuery(sql);
		while (rst.next()) {
			if (flag2 == 1) {
				Ddevice = rst.getString(1);
				flag2 = 0;
			}
			Devices.addItem(rst.getString(1));
		}
		this.person.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Devices.removeAllItems();
					Connection conn = DBUtil.getConnection();
					String sql = "select Device from WareHouse where Person='"
							+ person.getSelectedItem() + "'";
					java.sql.Statement pst = conn.createStatement();
					java.sql.ResultSet rst = pst.executeQuery(sql);
					while (rst.next()) {
						if (flag2 == 1) {
							Ddevice = rst.getString(1);
							flag2 = 0;
						}
						Devices.addItem(rst.getString(1));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		workPane.add(PersonName);
		workPane.add(person);
		// workPane.add(labelnull2);
		workPane.add(labelDevice);
		workPane.add(Devices);
		// workPane.add(labelnull);
		workPane.add(Count);
		workPane.add(edtCount);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.person.addActionListener(this);
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				per = (String) this.person.getSelectedItem();
				Ddevice = (String) this.Devices.getSelectedItem();
				int count = Integer.parseInt(this.edtCount.getText());
				String sql = "select Count from WareHouse where Device='"
						+ Ddevice + "'and Person='" + per + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int total = rs.getInt(1);
				int num = total - count;
				if (num < 0)
					JOptionPane.showMessageDialog(null, "超额", "错误",
							JOptionPane.ERROR_MESSAGE);
				else {
					ReturnDevice r = new ReturnDevice();
					Date now = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
					String time = dateFormat.format(now);
					r.setCount(count);
					r.setReturnDeviceName(Ddevice);
					sql = "select DeviceType from Device where DeviceName='"
							+ Ddevice + "' and DeleteTime is null";
					java.sql.Statement pst = conn.createStatement();
					java.sql.ResultSet rst = pst.executeQuery(sql);
					rst.next();
					r.setReturnDeviceType(rst.getString(1));
					r.setPerson(per);
					sql = "select Department from Person where PersonName='"
							+ per + "'";
					java.sql.Statement ss = conn.createStatement();
					java.sql.ResultSet rs1 = ss.executeQuery(sql);
					rs1.next();
					r.setDepartment(rs1.getString(1));
					r.setReturnTime(time);
					new LendDeviceManager().AddReturn(r);
					this.setVisible(false);
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "单价格式输入错误", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}