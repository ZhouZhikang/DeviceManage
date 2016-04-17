package DeviceManageControl;

import java.sql.Connection;
import java.sql.SQLException;

import DeviceManageModel.Manager;
import DeviceManageModel.Person;
import DeviceManageUtil.BaseException;
import DeviceManageUtil.BusinessException;
import DeviceManageUtil.DBUtil;
import DeviceManageUtil.DbException;

public class PersonManager {
	public void createPerson(Person p) throws BaseException {
		if (p.getPersonName() == null
				|| "".equals(p.getPersonName())
				|| p.getPersonName().length() > 8) {
			throw new BusinessException("人员名必须为1-8个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Person where PersonName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, p.getPersonName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("账号已经存在");
			else {
				rs.close();
				pst.close();
				sql = "insert into Person(PersonId,PersonName,Department) values(?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, p.getPersonId());
				pst.setString(2, p.getPersonName());
				pst.setString(3, p.getDepartment());
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
}
