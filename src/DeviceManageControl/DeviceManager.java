package DeviceManageControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

import javax.swing.JOptionPane;

import com.mysql.fabric.xmlrpc.base.Data;

import DeviceManageModel.*;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class DeviceManager {
	public static Device currentDevice = new Device();

	public void CreateDevice(Device d) throws BaseException {
		if (d.getDeviceName() == null || "".equals(d.getDeviceName())
				|| d.getDeviceName().length() > 50) {
			throw new BusinessException("设备名称必须是1-50个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Device where DeviceName=? and DeleteTime is null";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, d.getDeviceName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("设备已经存在");
			else {
				rs.close();
				pst.close();
				sql = "insert into Device(DeviceName,DeviceType,Count) values(?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, d.getDeviceName());
				pst.setString(2, d.getDeviceType());
				pst.setInt(3, 0);
				pst.execute();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} catch (BusinessException e) {
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

	public void DeleteDevice(String D) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			Date d = new Date(System.currentTimeMillis());
			String sql = "update Device set DeleteTime ='" + d
					+ "'where DeviceName='" + D + "'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.execute(sql);
			pst.close();
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

	public void EditDevice(String dn, String dd, String dt)
			throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update Device set DeviceName=? where DeviceName='"
					+ dd + "'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, dn);
			pst.execute();
			pst.close();
			sql = "update Device set DeviceType=? where DeviceName='" + dn
					+ "'";
			pst = conn.prepareStatement(sql);
			pst.setString(1, dt);
			pst.execute();
			pst.close();
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
}
