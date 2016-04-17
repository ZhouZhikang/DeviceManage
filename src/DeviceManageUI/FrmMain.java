package DeviceManageUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import java.util.ArrayList;
import java.util.List;

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

public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar = new JMenuBar();;
	private JMenu menu_DeviceTypeManager = new JMenu("类型管理");
	private JMenuItem menuItem_AddType = new JMenuItem("添加类型");
	private JMenuItem menuItem_DeleteType = new JMenuItem("删除类型");
	private JMenuItem menuItem_EditType = new JMenuItem("修改类型");

	private JMenu menu_DeviceManager = new JMenu("设备管理");
	private JMenuItem menuItem_AddDevice = new JMenuItem("添加设备");
	private JMenuItem menuItem_DeleteDevice = new JMenuItem("删除设备");
	private JMenuItem menuItem_EditDevice = new JMenuItem("修改设备");
	private JMenuItem menuItem_InDevice = new JMenuItem("入库");
	private JMenuItem menuItem_ScrapDevice = new JMenuItem("报废");

	private JMenu menu_LendDevice = new JMenu("借还");
	private JMenuItem menuItem_Lend = new JMenuItem("借");
	private JMenuItem menuItem_Return = new JMenuItem("还");

	private JMenu menu_Search = new JMenu("查询");
	private JMenuItem menu_SearchDevice = new JMenuItem("查询设备");
	private JMenuItem menu_SearchInDevice = new JMenuItem("入库记录");
	private JMenuItem menu_SearchScrapDevice = new JMenuItem("报废记录");
	private JMenuItem menu_SearchLendDevice = new JMenuItem("租借记录");
	private JMenuItem menu_SearchReturnDevice = new JMenuItem("归还记录");

	private JMenu menu_More = new JMenu("更多");
	private JMenuItem menu_Person = new JMenuItem("用户注册");

	private Object tblTypeTitle[] = { "类别号", "类别名" };
	private Object tblTypeData[][];
	DefaultTableModel tabTypeModel = new DefaultTableModel();
	private JTable dataTableType = new JTable(tabTypeModel);

	private Object tblDeviceTitle[] = { "设备号", "设备名", "设备类别", "库存" };
	private Object tblDeviceData[][];
	DefaultTableModel tabDeviceModel = new DefaultTableModel();
	private JTable dataTableDevice = new JTable(tabDeviceModel);

	private FrmLogin dlgLogin = null;
	private JPanel statusBar = new JPanel();

	public void reloadTypeTable() throws DbException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(*) from DeviceType";
			java.sql.Statement pst = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			java.sql.ResultSet rs = pst.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			tblTypeData = new Object[n][2];
			sql = "select DeviceTypeId,DeviceTypeName from DeviceType where DeleteTime is null";
			pst = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				DeviceType d = new DeviceType();
				d.setDeviceTypeId(rs.getInt(1));
				d.setDeviceTypeName(rs.getString(2));
				tblTypeData[i][0] = i + 1;
				tblTypeData[i][1] = d.getDeviceTypeName();
				i++;
			}
			tabTypeModel.setDataVector(tblTypeData, tblTypeTitle);
			this.dataTableType.validate();
			this.dataTableType.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void reloadDeviceTable() throws DbException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(*) from Device";
			java.sql.Statement pst = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			java.sql.ResultSet rs = pst.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			tblDeviceData = new Object[n][4];
			sql = "select DeviceId,DeviceName,DeviceType,Count from Device where DeleteTime is null";
			pst = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				Device d = new Device();
				d.setDeviceId(rs.getInt(1));
				d.setDeviceName(rs.getString(2));
				d.setDeviceType(rs.getString(3));
				d.setCount(rs.getInt(4));
				tblDeviceData[i][0] = d.getDeviceId();
				tblDeviceData[i][1] = d.getDeviceName();
				tblDeviceData[i][2] = d.getDeviceType();
				tblDeviceData[i][3] = d.getCount();
				i++;
			}
			tabDeviceModel.setDataVector(tblDeviceData, tblDeviceTitle);
			this.dataTableDevice.validate();
			this.dataTableDevice.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	// 菜单
	public FrmMain() {
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("设备管理系统");
		dlgLogin = new FrmLogin(this, "登陆", true);
		dlgLogin.setVisible(true);

		this.menu_DeviceTypeManager.add(this.menuItem_AddType);
		this.menuItem_AddType.addActionListener(this);
		this.menu_DeviceTypeManager.add(this.menuItem_DeleteType);
		this.menuItem_DeleteType.addActionListener(this);
		this.menu_DeviceTypeManager.add(this.menuItem_EditType);
		this.menuItem_EditType.addActionListener(this);

		this.menu_DeviceManager.add(this.menuItem_AddDevice);
		this.menuItem_AddDevice.addActionListener(this);
		this.menu_DeviceManager.add(this.menuItem_DeleteDevice);
		this.menuItem_DeleteDevice.addActionListener(this);
		this.menu_DeviceManager.add(this.menuItem_EditDevice);
		this.menuItem_EditDevice.addActionListener(this);
		this.menu_DeviceManager.add(this.menuItem_InDevice);
		this.menuItem_InDevice.addActionListener(this);
		this.menu_DeviceManager.add(this.menuItem_ScrapDevice);
		this.menuItem_ScrapDevice.addActionListener(this);

		this.menu_LendDevice.add(this.menuItem_Lend);
		this.menuItem_Lend.addActionListener(this);
		this.menu_LendDevice.add(this.menuItem_Return);
		this.menuItem_Return.addActionListener(this);

		this.menu_Search.add(this.menu_SearchDevice);
		this.menu_SearchDevice.addActionListener(this);
		this.menu_Search.add(this.menu_SearchInDevice);
		this.menu_SearchInDevice.addActionListener(this);
		this.menu_Search.add(this.menu_SearchScrapDevice);
		this.menu_SearchScrapDevice.addActionListener(this);
		this.menu_Search.add(this.menu_SearchLendDevice);
		this.menu_SearchLendDevice.addActionListener(this);
		this.menu_Search.add(this.menu_SearchReturnDevice);
		this.menu_SearchReturnDevice.addActionListener(this);

		this.menu_More.add(this.menu_Person);
		this.menu_Person.addActionListener(this);

		menubar.add(menu_DeviceTypeManager);
		menubar.add(menu_DeviceManager);
		menubar.add(menu_LendDevice);
		menubar.add(menu_Search);
		menubar.add(menu_More);
		this.setJMenuBar(menubar);

		this.getContentPane().add(new JScrollPane(this.dataTableType),
				BorderLayout.WEST);
		this.dataTableType.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int i = FrmMain.this.dataTableType.getSelectedRow();
				String name = tblTypeData[i][1].toString();
				if (i < 0) {
					return;
				}
				Connection conn = null;
				try {
					conn = DBUtil.getConnection();
					String sql = "select DeviceTypeId from DeviceType where DeviceTypeName='"
							+ name + "'";
					java.sql.Statement pst = conn.createStatement();
					java.sql.ResultSet rs = pst.executeQuery(sql);
					rs.next();
					DeviceTypeManager.currentDeviceType.setDeviceTypeId(rs
							.getInt(1));
					DeviceTypeManager.currentDeviceType.setDeviceTypeName(name);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		});
		this.getContentPane().add(new JScrollPane(this.dataTableDevice),
				BorderLayout.CENTER);
		this.dataTableDevice.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int i = FrmMain.this.dataTableDevice.getSelectedRow();
				String name = tblDeviceData[i][1].toString();
				if (i < 0) {
					return;
				}
				Connection conn = null;
				try {
					conn = DBUtil.getConnection();
					String sql = "select DeviceId,DeviceType,Count from Device where DeviceName='"
							+ name + "'";
					java.sql.Statement pst = conn.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					java.sql.ResultSet rs = pst.executeQuery(sql);
					rs.next();
					DeviceManager.currentDevice.setDeviceId(rs.getInt(1));
					DeviceManager.currentDevice.setDeviceName(name);
					DeviceManager.currentDevice.setDeviceType(rs.getString(2));
					DeviceManager.currentDevice.setCount(rs.getInt(3));
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		});

		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("当前管理员："
				+ SystemManager.currentManager.getManagerName());
		statusBar.add(label);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		try {
			this.reloadTypeTable();
			this.reloadDeviceTable();
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.menuItem_AddType) {
			FrmAddDeviceType dlg = new FrmAddDeviceType(this, "添加类型", true);
			dlg.setVisible(true);

		}
		if (e.getSource() == this.menuItem_DeleteType) {
			FrmDeleteType dlg = null;
			try {
				dlg = new FrmDeleteType(this, "删除类型", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_EditType) {

			FrmEditDeviceType dlg = null;
			try {
				dlg = new FrmEditDeviceType(this, "修改类型", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_AddDevice) {
			FrmAddDevice dlg = null;
			try {
				dlg = new FrmAddDevice(this, "添加设备", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_DeleteDevice) {
			FrmDeleteDevice dlg = null;
			try {
				dlg = new FrmDeleteDevice(this, "删除设备", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_EditDevice) {
			FrmEditDevice dlg = null;
			try {
				dlg = new FrmEditDevice(this, "修改设备", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_InDevice) {
			FrmInDevice dlg = null;
			try {
				dlg = new FrmInDevice(this, "入库", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_Lend) {
			FrmLendDevice dlg = null;
			try {
				dlg = new FrmLendDevice(this, "借", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_Return) {
			FrmReturnDevice dlg = null;
			try {
				dlg = new FrmReturnDevice(this, "还", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menuItem_ScrapDevice) {
			FrmScrapDevice dlg = null;
			try {
				dlg = new FrmScrapDevice(this, "报废", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_SearchInDevice) {
			FrmSearchInDevice dlg = null;
			try {
				dlg = new FrmSearchInDevice(this, "入库记录", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_SearchDevice) {
			FrmSearchDevice dlg = null;
			try {
				dlg = new FrmSearchDevice(this, "查询设备", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_SearchScrapDevice) {
			FrmSearchScrapDevice dlg = null;
			try {
				dlg = new FrmSearchScrapDevice(this, "报废记录", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_SearchLendDevice) {
			FrmSearchLendDevice dlg = null;
			try {
				dlg = new FrmSearchLendDevice(this, "租借记录", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_SearchReturnDevice) {
			FrmSearchReturnDevice dlg = null;
			try {
				dlg = new FrmSearchReturnDevice(this, "归还记录", true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dlg.setVisible(true);
		}
		if (e.getSource() == this.menu_Person) {
			FrmPersonRegister dlg = new FrmPersonRegister(this, "用户注册", true);
			dlg.setVisible(true);
		}
		try {
			this.reloadTypeTable();
			this.reloadDeviceTable();
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}