package spcs.bigboot.delchk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import spcs.bigboot.delchk.dao.employee.EmployeeDAO;
import spcs.bigboot.delchk.entity.db.Employee;
import spcs.bigboot.delchk.service.EmployeeInfoProcess;
import spcs.lib.service.MqPutService;

@Service
public class EmployeeInfoProcessimpl implements EmployeeInfoProcess {
	private static Logger log = LoggerFactory.getLogger(EmployeeInfoProcessimpl.class);

	@Autowired
	private EmployeeDAO empDAO;

	@Autowired
	private MqPutService MqService;

//	@Value("${spcs.client.name}")
//	private String clientName;

	@Override
	public Employee getEmployeeInfo(int EmployeeID) {

		Employee employee = this.empDAO.getEmployeeInfo(EmployeeID);
		if (employee != null) {
			log.debug(employee.toString());
		}
		return employee;
	}

//	@Override
//	public boolean displayPopup(int empID, Employee employee) {
//		if (employee == null) {
//			this.MqService.putPopupMqMessage(CPUCTX17, "Mai Dai Test", "WX000002",
//					"Not Found EmployeeID [empID=" + empID + "]");
//			return false;
//		} else {
////			this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "IX000001",
////					"Hello " + employee.toString());
//			this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "IX000001",
//					"Hello " + this.setMessageFormat(employee));
//		}
//
//		return true;
//	}

//		if (employee == null) {
//			this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "WX000002",
//					"Not Found EmployeeID [empID=" + empID + "]");
//			return false;
//		}
////		this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "IX000001",
////				"Hello " + employee.toString());
//		this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "IX000001",
//				"Hello " + this.setMessageFormat(employee));
//		return true;
//	}

//	private String setMessageFormat(Employee employee) {
//		return employee.getPrefix() + employee.getFirstName() + " " + employee.getLastName();
//	}

	@Override
	public boolean addEmployeeInfo(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmployeeInfo(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEmployeeInfo(int EmployeeID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean displayPopup(int empID, Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

}
