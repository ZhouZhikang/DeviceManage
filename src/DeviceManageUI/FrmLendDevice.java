package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FrmLendDevice extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel PersonName = new JLabel("人员/单位");
	private JLabel labelDevice = new JLabel("设备名称");
	private JLabel Count = new JLabel("借出数量");
	private JComboBox person = new JComboBox();
	private JComboBox Devices = new JComboBox();
	private JTextField edtCount = new JTextField(18);
	private JLabel labelnull = new JLabel("                                   ");
	private JLabel labelnull2 = new JLabel("                                       ");
	String Ddevice =null;
	String per=null;
	int flag1=1,flag2=1;

	public FrmLendDevice(JFrame f, String s, boolean b) throws SQLException {
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
			if(flag1==1){
				Ddevice=rs.getString(1);
				flag1=0;
			}
			Devices.addItem(rs.getString(1));
		}
		rs.close();
		sql = "select PersonName from Person";
		java.sql.Statement pst = conn.createStatement();
		java.sql.ResultSet rst = pst.executeQuery(sql);
		while (rst.next()) {
			if(flag2==1){
				per=rst.getString(1);
				flag2=0;
			}
			person.addItem(rst.getString(1));
		}
		workPane.add(labelDevice);
		workPane.add(Devices);
		workPane.add(labelnull);
		workPane.add(PersonName);
		workPane.add(person);
		workPane.add(labelnull2);
		workPane.add(Count);
		workPane.add(edtCount);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 180);
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
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				LendDevice l = new LendDevice();
				 Ddevice = (String) this.Devices.getSelectedItem();
				per = (String) this.person.getSelectedItem();
				int Icount = Integer.parseInt(this.edtCount.getText());
				String sql = "select Count from Device where DeviceName='"
						+ Ddevice + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) - Icount;
				if (num < 0) 
					JOptionPane.showMessageDialog(null, "超额", "错误",
							JOptionPane.ERROR_MESSAGE);
				else{
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
				String time = dateFormat.format(now);
				l.setCount(Icount);
				l.setLendDeviceName(Ddevice);
				l.setLendTime(time);
				l.setPerson(per);
				sql = "select DeviceType from Device where DeviceName='"
						+ Ddevice + "' and DeleteTime is null";
				java.sql.Statement pst = conn.createStatement();
				java.sql.ResultSet rst = pst.executeQuery(sql);
				rst.next();
				l.setLendDeviceType(rst.getString(1));
				sql = "select Department from Person where PersonName='"
						+ per + "'";
				java.sql.Statement ss = conn.createStatement();
				java.sql.ResultSet rs1 = ss.executeQuery(sql);
				rs1.next();
				l.setDepartment(rs1.getString(1));
				new LendDeviceManager().AddLend(l);
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