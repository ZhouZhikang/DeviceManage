package DeviceManageControl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DeviceManageModel.*;
import DeviceManageUtil.*;
import DeviceManageUI.*;

public class DeviceTypeManager {

	public static DeviceType currentDeviceType = new DeviceType();

	public void CreateDeviceType(DeviceType d) throws BaseException {
		if (d.getDeviceTypeName() == null || "".equals(d.getDeviceTypeName())
				|| d.getDeviceTypeName().length() > 50) {
			throw new BusinessException("设备类型名称必须是1-50个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from DeviceType where DeviceTypeName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, d.getDeviceTypeName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("类型已经存在");
			else {
				rs.close();
				pst.close();
				sql = "insert into DeviceType(DeviceTypeName) values(?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, d.getDeviceTypeName());
				pst.execute();
				pst.close();
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

	}

	public void DeleteDeviceType(String dt) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			Date d = new Date(System.currentTimeMillis());
			String sql = "update DeviceType set DeleteTime ='" + d
					+ "'where DeviceTypeName='" + dt + "'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.execute(sql);
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
		}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void EditDeviceType(String dn, String dt) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from DeviceType where DeviceTypeName='" + dn
					+ "'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.close();
			pst.close();
			sql = "update DeviceType set DeviceTypeName =? where DeviceTypeName='"
					+ dt + "'";
			pst = conn.prepareStatement(sql);
			pst.setString(1, dn);
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
