package DeviceManageModel;

import java.sql.Date;

public class DeviceType {
	private int DeviceTypeId;
	private String DeviceTypeName;
	private Date DeleteTime;

	public int getDeviceTypeId() {
		return DeviceTypeId;
	}

	public void setDeviceTypeId(int DeviceTypeId) {
		this.DeviceTypeId = DeviceTypeId;
	}

	public String getDeviceTypeName() {
		return DeviceTypeName;
	}

	public void setDeviceTypeName(String DeviceTypeName) {
		this.DeviceTypeName = DeviceTypeName;
	}

	public Date getDeleteTimee() {
		return DeleteTime;
	}

	public void setDeleteTime(Date DeleteTime) {
		this.DeleteTime = DeleteTime;
	}
}
