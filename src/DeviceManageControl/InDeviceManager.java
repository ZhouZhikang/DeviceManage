package DeviceManageControl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DeviceManageModel.*;
import DeviceManageControl.*;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class InDeviceManager {
	public void AddIn(InDevice i) throws BaseException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "insert into InDevice(InDeviceName,InDeviceType,InCount,Price,InTime,Manager) values(?,?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst = conn.prepareStatement(sql);
			pst.setString(1, i.getInDeviceName());
			pst.setString(2, i.getInDeviceType());
			pst.setInt(3, i.getInCount());
			pst.setInt(4, i.getPrice());
			pst.setString(5, i.getInTime());
			pst.setString(6, i.getManager());
			pst.execute();
			pst.close();
			sql = "select Count from Device where DeviceName='"
					+ i.getInDeviceName() + "'";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			rs.next();
			int num = rs.getInt(1) + i.getInCount();
			sql = "update Device set Count=" + num + " where DeviceName='"
					+ i.getInDeviceName() + "'";
			pst = conn.prepareStatement(sql);
			pst = conn.prepareStatement(sql);
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

	public List<InDevice> LoadInDevice() throws DbException {
		List<InDevice> result = new ArrayList<InDevice>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select InDeviceName,InDeviceType,InCount,Price,InTime,Manager from InDevice";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				InDevice s = new InDevice();
				s.setInDeviceName(rs.getString(1));
				s.setInDeviceType(rs.getString(2));
				s.setInCount(rs.getInt(3));
				s.setPrice(rs.getInt(4));
				s.setInTime(rs.getString(5));
				s.setManager(rs.getString(6));
				result.add(s);
			}
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
		return result;
	}
}
