package spcs.aws.web.dao.alarm;

import java.util.List;



//########## Final Modified by Epick (Sahassawat O.) on 08/11/2022 ##########
public interface ShipmentDAO {
	
	/**
	 * This method is used to get count of shipments lot not completed information from TBLEF001 and TBLXX008 (PC table) 
     * of a shipment which their shipment-status = '0' (still not completed).
	 * @return list of <b>countUncompletedShiplot</b>
	 */
	public List<String> getUncompletedShiplot();
	
//	public int countUncompletedShiplot();
	
}
