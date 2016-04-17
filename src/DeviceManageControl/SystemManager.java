package DeviceManageControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DeviceManageModel.Manager;
import DeviceManageUtil.*;
import DeviceManageUI.*;

public class SystemManager {
	public static Manager currentManager = null;

	public void createManager(Manager manager) throws BaseException {
		if (manager.getManagerName() == null
				|| "".equals(manager.getManagerName())
				|| manager.getManagerName().length() > 8) {
			throw new BusinessException("管理员名必须为1-8个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Manager where ManagerName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, manager.getManagerName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("账号已经存在");
			else {
				rs.close();
				pst.close();
				sql = "insert into Manager(ManagerId,ManagerName,PassWord) values(?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, manager.getManagerId());
				pst.setString(2, manager.getManagerName());
				pst.setString(3, manager.getPassWord());
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

	public void changeManagerPwd(String pwd, String pwd1, String pwd2)
			throws BaseException {
		if (pwd == null)
			throw new BusinessException("原始密码不能为空");
		if (pwd == null || "".equals(pwd2) || pwd2.length() > 16)
			throw new BusinessException("必须为1-18个字符");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Manager where ManagerId=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, SystemManager.currentManager.getManagerId());
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if (!pwd.equals(rs.getString(3)))
				throw new BusinessException("原始密码错误");
			else {
				rs.close();
				pst.close();
				sql = "update Managers set PassWord=? where ManagerId=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, pwd2);
				pst.setInt(2, SystemManager.currentManager.getManagerId());
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

	public void resetManagerPwd(int managerid) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Manager where ManagerId=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, managerid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql = "update Manager set PassWord=? where ManagerId=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, managerid);
			pst.setInt(2, managerid);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
					JOptionPane.ERROR_MESSAGE);
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

	public Manager loadManager(String managername) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select ManagerId,ManagerName,PassWord from Manager where ManagerName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, managername);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("登陆账号不存在");
			Manager m = new Manager();
			m.setManagerId(rs.getInt(1));
			m.setManagerName(rs.getString(2));
			m.setPassWord(rs.getString(3));

			rs.close();
			pst.close();
			return m;
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
