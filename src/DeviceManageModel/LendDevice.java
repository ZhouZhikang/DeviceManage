package DeviceManageModel;

public class LendDevice {
	private int LendDeviceId;
	private String LendDeviceName;
	private String LendDeviceType;
	private int Count;
	private String LendTime;
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

	public String getLendDeviceName() {
		return LendDeviceName;
	}

	public void setLendDeviceName(String LendDeviceName) {
		this.LendDeviceName = LendDeviceName;
	}

	public String getLendDeviceType() {
		return LendDeviceType;
	}

	public void setLendDeviceType(String LendDeviceType) {
		this.LendDeviceType = LendDeviceType;
	}

	public String getLendTime() {
		return LendTime;
	}

	public void setLendTime(String LendTime) {
		this.LendTime = LendTime;
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
