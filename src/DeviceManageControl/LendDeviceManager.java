package DeviceManageControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DeviceManageModel.InDevice;
import DeviceManageModel.LendDevice;
import DeviceManageModel.ReturnDevice;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class LendDeviceManager {
	public void AddLend(LendDevice l) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into LendDevice(LendDeviceName,LendDeviceType,Person,Department,Count,LendTime) values(?,?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, l.getLendDeviceName());
			pst.setString(2, l.getLendDeviceType());
			pst.setString(3, l.getPerson());
			pst.setString(4, l.getDepartment());
			pst.setInt(5, l.getCount());
			pst.setString(6, l.getLendTime());
			pst.execute();
			pst.close();
			sql = "select * from WareHouse where Device=? and Person=?";
			java.sql.PreparedStatement t1 = conn.prepareStatement(sql);
			t1.setString(1, l.getLendDeviceName());
			t1.setString(2, l.getPerson());
			java.sql.ResultSet rs1 = t1.executeQuery();
			if (rs1.next()) {
				sql = "select Count from WareHouse where Device='"
						+ l.getLendDeviceName() + "' and Person='"
						+ l.getPerson() + "'";
				java.sql.Statement t0 = conn.createStatement();
				java.sql.ResultSet res = t0.executeQuery(sql);
				res.next();
				int Wnum = res.getInt(1) + l.getCount();
				sql = "update WareHouse set Count=? where Device=? and Person =?";
				java.sql.PreparedStatement t2 = conn.prepareStatement(sql);
				t2.setInt(1, Wnum);
				t2.setString(2, l.getLendDeviceName());
				t2.setString(3, l.getPerson());
				t2.execute();
				t2.close();
				sql = "select Count from Device where DeviceName='"
						+ l.getLendDeviceName() + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) - l.getCount();
				sql = "update Device set Count=" + num + " where DeviceName='"
						+ l.getLendDeviceName() + "'";
				pst = conn.prepareStatement(sql);
				pst = conn.prepareStatement(sql);
				pst.execute();
				pst.close();
			} else {
				sql = "insert into WareHouse(Person,Device,Count) values(?,?,?)";
				java.sql.PreparedStatement t = conn.prepareStatement(sql);
				t.setString(1, l.getPerson());
				t.setString(2, l.getLendDeviceName());
				t.setInt(3, l.getCount());
				t.execute();
				t.close();
				sql = "select Count from Device where DeviceName='"
						+ l.getLendDeviceName() + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				rs.next();
				int num = rs.getInt(1) - l.getCount();
				sql = "update Device set Count=" + num + " where DeviceName='"
						+ l.getLendDeviceName() + "'";
				pst = conn.prepareStatement(sql);
				pst = conn.prepareStatement(sql);
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

	public void AddReturn(ReturnDevice r) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into ReturnDevice(ReturnDeviceName,ReturnDeviceType,Person,Department,Count,ReturnTime) values(?,?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, r.getReturnDeviceName());
			pst.setString(2, r.getReturnDeviceType());
			pst.setString(3, r.getPerson());
			pst.setString(4, r.getDepartment());
			pst.setInt(5, r.getCount());
			pst.setString(6, r.getReturnTime());
			pst.execute();
			pst.close();
			sql = "select Count from WareHouse where Device='"
					+ r.getReturnDeviceName() + "' and Person='"
					+ r.getPerson() + "'";
			pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			int num = rs.getInt(1) - r.getCount();
			if (num == 0) {
				sql = "Delete from WareHouse where Device='"
						+ r.getReturnDeviceName() + "' and Person='"
						+ r.getPerson() + "'";
				pst = conn.prepareStatement(sql);
				pst.execute();
				pst.close();
			} else {
				sql = "update WareHouse set Count=? where Device=? and Person=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, num);
				pst.setString(2, r.getReturnDeviceName());
				pst.setString(3, r.getPerson());
				pst.execute();
				pst.close();
			}
			sql = "select Count from Device where DeviceName='"
					+ r.getReturnDeviceName() + "'";
			java.sql.Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			int num2 = rs.getInt(1) + r.getCount();
			sql = "update Device set Count=? where DeviceName = ?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, num2);
			pst.setString(2, r.getReturnDeviceName());
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

	public List<LendDevice> LoadLendDevice() throws DbException {
		List<LendDevice> result = new ArrayList<LendDevice>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select LendDeviceName,LendDeviceType,Person,Department,Count,LendTime from LendDevice";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				LendDevice s = new LendDevice();
				s.setLendDeviceName(rs.getString(1));
				s.setLendDeviceType(rs.getString(2));
				s.setPerson(rs.getString(3));
				s.setDepartment(rs.getString(4));
				s.setCount(rs.getInt(5));
				s.setLendTime(rs.getString(6));
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

	public List<ReturnDevice> LoadReturnDevice() throws DbException {
		List<ReturnDevice> result = new ArrayList<ReturnDevice>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select ReturnDeviceName,ReturnDeviceType,Person,Department,Count,ReturnTime from ReturnDevice";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				ReturnDevice s = new ReturnDevice();
				s.setReturnDeviceName(rs.getString(1));
				s.setReturnDeviceType(rs.getString(2));
				s.setPerson(rs.getString(3));
				s.setDepartment(rs.getString(4));
				s.setCount(rs.getInt(5));
				s.setReturnTime(rs.getString(6));
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
