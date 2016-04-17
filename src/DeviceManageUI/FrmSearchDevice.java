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
import java.util.List;

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

public class FrmSearchDevice extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JComboBox Type = new JComboBox();
	private JLabel label = new JLabel(
			"                   填写任意一项即可                          ");
	private JTextField edt = new JTextField(18);
	String select = "设备编号";

	public FrmSearchDevice(JFrame f, String s, boolean b) throws SQLException {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(label);
		Type.addItem("设备编号");
		Type.addItem("设备名称");
		Type.addItem("设备类型");
		workPane.add(Type);
		workPane.add(edt);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 220);
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
				int DId = 0;
				String DName = null, DType = null;
				Device s = new Device();
				select = (String) this.Type.getSelectedItem();
				if (select == "设备编号")
					DId = Integer.parseInt(this.edt.getText());
				else if (select == "设备名称")
					DName = this.edt.getText();
				else if (select == "设备类型")
					DType = this.edt.getText();
				s.setDeviceId(DId);
				s.setDeviceName(DName);
				s.setDeviceType(DType);
				List<Device> devices = new SearchDeviceManager().LoadSearchDevice(s);
				FrmSearchResult dlg = new FrmSearchResult(null, "查询设备", true,
						devices);
				dlg.setVisible(true);
				this.setVisible(false);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "格式输入错误", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (DbException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
