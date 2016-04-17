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

import DeviceManageControl.*;
import DeviceManageModel.*;
import DeviceManageUI.*;
import DeviceManageUtil.*;

public class FrmEditDevice extends JDialog implements ActionListener {

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("修改名称为");
	private JLabel labelDevice = new JLabel("设备名称");
	private JLabel labelType = new JLabel("修改类型");
	private JComboBox DType = new JComboBox();
	private JComboBox Devices = new JComboBox();
	private JTextField edtName = new JTextField(18);
	private JLabel labelnull = new JLabel("                              ");
	private JLabel labelnull2 = new JLabel("                       ");
	String Ddevice=null;
	int flag1=1,flag2=1;
	String Dt=null;
	
	

	public FrmEditDevice(JFrame f, String s, boolean b) throws SQLException {
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
		conn = DBUtil.getConnection();
		sql = "select DeviceTypeName from DeviceType where DeleteTime is null";
		java.sql.Statement pst = conn.createStatement();
		java.sql.ResultSet rst = pst.executeQuery(sql);
		while(rst.next()){
			if(flag2==1){
				Dt=rst.getString(1);
				flag2=0;
			}
			DType.addItem(rst.getString(1));
		}
		Devices.addActionListener(this);
		workPane.add(labelDevice);
		workPane.add(Devices);
		workPane.add(labelnull);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelType);
		workPane.add(DType);
		workPane.add(labelnull2);
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
			try {
				String DName = this.edtName.getText();
				Ddevice = (String) this.Devices.getSelectedItem();
				this.setVisible(false);
				String Dt= (String) this.DType.getSelectedItem();
				new DeviceManager().EditDevice(DName,Ddevice,Dt);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "单价格式输入错误", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (BaseException ep) {
				JOptionPane.showMessageDialog(null, ep.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
				// TODO Auto-generated catch block
				ep.printStackTrace();
			}
		}
	}
}