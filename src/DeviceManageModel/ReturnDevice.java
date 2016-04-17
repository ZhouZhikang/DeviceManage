package DeviceManageModel;

public class ReturnDevice {
	private int LendDeviceId;
	private String ReturnDeviceName;
	private String ReturnDeviceType;
	private int Count;
	private String ReturnTime;
	private String Person;
	private String Department;

	public int getLendDeviceId() {
		return LendDeviceId;
	}

	public void setLendDeviceId(int LendDeviceId) {
		this.LendDeviceId = LendDeviceId;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int Count) {
		this.Count = Count;
	}

	public String getReturnDeviceName() {
		return ReturnDeviceName;
	}

	public void setReturnDeviceName(String ReturnDeviceName) {
		this.ReturnDeviceName = ReturnDeviceName;
	}

	public String getReturnDeviceType() {
		return ReturnDeviceType;
	}

	public void setReturnDeviceType(String ReturnDeviceType) {
		this.ReturnDeviceType = ReturnDeviceType;
	}

	public String getReturnTime() {
		return ReturnTime;
	}

	public void setReturnTime(String ReturnTime) {
		this.ReturnTime = ReturnTime;
	}

	public String getPerson() {
		return Person;
	}

	public void setPerson(String Person) {
		this.Person = Person;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String Department) {
		this.Department = Department;
	}
}
