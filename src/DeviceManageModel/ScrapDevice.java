package DeviceManageModel;

public class ScrapDevice {
	private int ScrapDeviceId;
	private String ScrapDeviceName;
	private String ScrapDeviceType;
	private int Count;
	private String ScrapTime;
	private String Manager;
	private int Device_DeviceId;

	public String getScrapDeviceName() {
		return ScrapDeviceName;
	}

	public void setScrapDeviceName(String ScrapDeviceName) {
		this.ScrapDeviceName = ScrapDeviceName;
	}

	public String getScrapDeviceType() {
		return ScrapDeviceType;
	}

	public void setScrapDeviceType(String ScrapDeviceType) {
		this.ScrapDeviceType = ScrapDeviceType;
	}

	public void setScrapDeviceId(int ScrapDeviceId) {
		this.ScrapDeviceId = ScrapDeviceId;
	}

	public int Device_DeviceId() {
		return Device_DeviceId;
	}

	public void setDevice_DeviceId(int Device_DeviceId) {
		this.Device_DeviceId = Device_DeviceId;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int Count) {
		this.Count = Count;
	}

	public String getScrapTime() {
		return ScrapTime;
	}

	public void setScrapTime(String ScrapTime) {
		this.ScrapTime = ScrapTime;
	}

	public String getManager() {
		return Manager;
	}

	public void setManager(String Manager) {
		this.Manager = Manager;
	}

}
