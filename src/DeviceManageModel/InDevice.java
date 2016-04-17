package DeviceManageModel;

public class InDevice {
	private int DeviceId;
	private String InDeviceName;
	private String InDeviceType;
	private int InCount;
	private int Price;
	private String InTime;
	private String Manager;
	private int Device_DeviceId;

	public String getInDeviceName() {
		return InDeviceName;
	}

	public void setInDeviceName(String InDeviceName) {
		this.InDeviceName = InDeviceName;
	}

	public String getInDeviceType() {
		return InDeviceType;
	}

	public void setInDeviceType(String InDeviceType) {
		this.InDeviceType = InDeviceType;
	}
	
	public int getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(int DeviceId) {
		this.DeviceId = DeviceId;
	}

	public int Device_DeviceId() {
		return Device_DeviceId;
	}

	public void setDevice_DeviceId(int Device_DeviceId) {
		this.Device_DeviceId = Device_DeviceId;
	}

	public int getInCount() {
		return InCount;
	}

	public void setInCount(int InCount) {
		this.InCount = InCount;
	}

	public String getInTime() {
		return InTime;
	}

	public void setInTime(String InTime) {
		this.InTime = InTime;
	}

	public String getManager() {
		return Manager;
	}

	public void setManager(String Manager) {
		this.Manager = Manager;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int Price) {
		this.Price = Price;
	}
}
