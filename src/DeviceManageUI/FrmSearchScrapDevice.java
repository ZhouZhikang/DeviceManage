package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DeviceManageControl.*;
import DeviceManageModel.*;
import DeviceManageUI.*;
import DeviceManageUtil.*;

public class FrmSearchScrapDevice extends JDialog {

	private Object tblTitle[] = { "编号", "设备名称", "设备类型", "报废数量（件）", "报废时间",
			"操作员" };
	private Object tblData[][];
	DefaultTableModel tablmod = new DefaultTableModel();
	private JTable dataTable = new JTable(tablmod);

	private void loadTable() {
		try {
			List<ScrapDevice> records = new ScrapDeviceManager()
					.LoadScrapDevice();
			tblData = new Object[records.size()][6];
			for (int i = 0; i < records.size(); i++) {
				tblData[i][0] = i + 1;
				tblData[i][1] = records.get(i).getScrapDeviceName();
				tblData[i][2] = records.get(i).getScrapDeviceType();
				tblData[i][3] = records.get(i).getCount();
				tblData[i][4] = records.get(i).getScrapTime();
				tblData[i][5] = records.get(i).getManager();
			}
			tablmod.setDataVector(tblData, tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}

	public FrmSearchScrapDevice(Frame f, String s, boolean b)
			throws SQLException {
		super(f, s, b);

		this.loadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable),
				BorderLayout.CENTER);

		this.setSize(1100, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
	}

}
