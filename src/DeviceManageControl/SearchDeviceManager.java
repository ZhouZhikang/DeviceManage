package DeviceManageControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DeviceManageModel.Device;
import DeviceManageModel.InDevice;
import DeviceManageUI.FrmMain;
import DeviceManageUI.FrmSearchDevice;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class SearchDeviceManager {

	public List<Device> LoadSearchDevice(Device d) throws DbException {
		List<Device> result = new ArrayList<Device>();
		Connection conn = null;
		try {
			Device s = new Device();
			conn = DBUtil.getConnection();
			if (d.getDeviceId() != 0) {
				String sql = "select * from Device where DeviceId='"
						+ d.getDeviceId() + "'";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					s.setDeviceId(rs.getInt(1));
					s.setDeviceName(rs.getString(3));
					s.setDeviceType(rs.getString(2));
					s.setCount(rs.getInt(4));
					result.add(s);
				}
			} else if (d.getDeviceName() != null) {
				String sql = "select * from Device where DeviceName='"
						+ d.getDeviceName() + "' and DeleteTime is null";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					s.setDeviceId(rs.getInt(1));
					s.setDeviceName(rs.getString(3));
					s.setDeviceType(rs.getString(2));
					s.setCount(rs.getInt(4));
					result.add(s);
				}
			} else if (d.getDeviceType() != null) {
				String sql = "select * from Device where DeviceType='"
						+ d.getDeviceType() + "' and DeleteTime is null";
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					Device d1 = new Device();
					d1.setDeviceId(rs.getInt(1));
					d1.setDeviceName(rs.getString(3));
					d1.setDeviceType(rs.getString(2));
					d1.setCount(rs.getInt(4));
					result.add(d1);
				}
			} else
				throw new BusinessException("请输入查询信息！");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
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
		return result;
	}

}
