package spcs.bigboot.delchk.service;

import spcs.bigboot.delchk.entity.db.Employee;

public interface EmployeeInfoProcess {
	
	public Employee getEmployeeInfo(int EmployeeID);
	
	public boolean displayPopup(int empID, Employee employee);
	
	public boolean addEmployeeInfo(Employee employee);
	
	public boolean updateEmployeeInfo(Employee employee);
	
	public boolean deleteEmployeeInfo(int EmployeeID);
}
