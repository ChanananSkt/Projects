package spcs.bigboot.delchk.entity.db;

public class Employee {
	
	private int EM_NO001; //Employee
	
	private String NA_00002; //Prefix name
	
	private String NA_00001; //First name
	
	private String NA_00003; // last name
	
	private String BE_TO001; // departments
	
	private String PO_SI001; // position

	public int getEmployeeID() {
		return EM_NO001;
	}

	public void setEmployeeID(int eM_No001) {
		EM_NO001 = eM_No001;
	}

	public String getPrefix() {
		return NA_00002;
	}

	public void setPrefix(String nA_00002) {
		NA_00002 = nA_00002;
	}

	public String getFirstName() {
		return NA_00001;
	}

	public void setFirstName(String nA_00001) {
		NA_00001 = nA_00001;
	}

	public String getLastName() {
		return NA_00003;
	}

	public void setLastName(String nA_00003) {
		NA_00003 = nA_00003;
	}

	public String getDepartment() {
		return BE_TO001;
	}

	public void setDepartment(String bE_TO001) {
		BE_TO001 = bE_TO001;
	}

	public String getPosition() {
		return PO_SI001;
	}

	public void setPosition(String pO_SI001) {
		PO_SI001 = pO_SI001;
	}

	@Override
	public String toString() {
		return "Employee [EM_NO001=" + EM_NO001 + ", NA_00002=" + NA_00002 + ", NA_00001=" + NA_00001 + ", NA_00003="
				+ NA_00003 + ", BE_TO001=" + BE_TO001 + ", PO_SI001=" + PO_SI001 + "]";
	}
	
	

}
