package spcs.aws.web.dao.alarm;

import java.util.List;



//########## Final Modified by Epick (Sahassawat O.) on 07/11/2022 ##########
public interface TruckDAO {
	

	/**
	 * This method is used to get distinct(Not duplicated)shipments lot not completed information from TBLXX008 (PC table) 
     * and this method have to used to get shipment lot from plant 1 that specific export coil and Overland Exwork.
	 * @return list of string  <b>getShiplotlist</b>
	 */
	public List<String> getShiplotlist();

}
