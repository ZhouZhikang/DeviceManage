package DeviceManageControl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DeviceManageModel.*;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class ScrapDeviceManager {
	public void Scrap(ScrapDevice s) throws BaseException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "insert into ScrapDevice(ScrapDeviceName,ScrapDeviceType,Count,ScrapTime,Manager) values(?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst = conn.prepareStatement(sql);
			pst.setString(1, s.getScrapDeviceName());
			pst.setString(2, s.getScrapDeviceType());
			pst.setInt(3, s.getCount());
			pst.setString(4, s.getScrapTime());
			pst.setString(5, s.getManager());
			pst.execute();
			pst.close();
			sql = "select Count from Device where DeviceName='"
					+ s.getScrapDeviceName() + "'";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			rs.next();
			int num = rs.getInt(1) - s.getCount();
				sql = "update Device set Count=" + num + " where DeviceName='"
						+ s.getScrapDeviceName() + "'";
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

	public List<ScrapDevice> LoadScrapDevice() throws DbException {
		List<ScrapDevice> result = new ArrayList<ScrapDevice>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select ScrapDeviceName,ScrapDeviceType,Count,ScrapTime,Manager from ScrapDevice";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				ScrapDevice s = new ScrapDevice();
				s.setScrapDeviceName(rs.getString(1));
				s.setScrapDeviceType(rs.getString(2));
				s.setCount(rs.getInt(3));
				s.setScrapTime(rs.getString(4));
				s.setManager(rs.getString(5));
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
