package DeviceManageModel;

import java.sql.Date;

;

public class Device {
	private int DeviceId;
	private String DeviceName;
	private String DeviceType;
	private int Count;
	private Date DeleteTime;

	public Date getDeleteTime() {
		return DeleteTime;
	}

	public void setDeleteTime(Date DeleteTime) {
		this.DeleteTime = DeleteTime;
	}

	public int getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(int DeviceId) {
		this.DeviceId = DeviceId;
	}

	public String getDeviceName() {
		return DeviceName;
	}

	public void setDeviceName(String DeviceName) {
		this.DeviceName = DeviceName;
	}

	public String getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(String DeviceType) {
		this.DeviceType = DeviceType;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int Count) {
		this.Count = Count;
	}

}
