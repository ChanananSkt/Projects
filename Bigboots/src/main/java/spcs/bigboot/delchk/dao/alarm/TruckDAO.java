package spcs.bigboot.delchk.dao.alarm;

import java.util.List;

//import spcs.aws.web.entity.db.cache.CraneDB;


public interface TruckDAO {
	
	/**
     * This method is used to get employee information from TBLXX900 
     * of a passed EmployeeID.
	 * @param EmployeeID
	 * @return <b>employee object</b>
     */
//	public Employee getEmployeeInfo();
	
	/**
	 * This method is used to get list of Coil information from TBLXX007 (CC table) and TBLXX008 (PC table) 
     * of a passed Process line which their verify-status = '0' (still not verify).
	 * @param processLine
	 * @return list of <b>CoilInfo object</b>
	 */
	
	public List<String> getShiplotlist();
	
	/**
     * This method is used to get shiplotlist record(cs_tblxx008).
     */
	
	
}
