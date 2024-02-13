package spcs.aws.web.service.shiplot;

import java.util.List;

import spcs.aws.web.dao.shiplot.ShipmentLotDAO;
import spcs.aws.web.entity.db.shiplot.CombineLotDB;

public interface ShipmentLotControlService {
	
	/**
	 * This method is used to return ShipmentLotDAO (DAO of Table-TBLEF001/TBLEF002/TBLEF033).
	 */
	public ShipmentLotDAO getShipmentLotDao();

	/**
	 * This method is used to return Combine Lot No. of a passed Shipment Lot No.<br>
	 * IN CASE: A passed Shipment Lot No. is not a sub-lot of any Combine Lot --> Return NULL.
	 */
	public CombineLotDB getCombineLotObj(String shipLotNo);
	
	/**
	 * This method is used to return Combine Lot No. of a passed Shipment Lot No. and Going-To-Berth Lot No.<br>
	 * IN CASE: A passed Going-To-Berth Lot No. is not a sub-lot of any Combine Lot --> Return NULL.
	 */
	public CombineLotDB getCombineLotObj(String shipLotNo, String gtbLotNo);
	
	/**
	 * This method is used to get List of Sub-Combine_Lot records(TBLEF033) of a passed 
	 * Subject Lot Number.<br><br>
	 * In case of Not found Sub-Combine_Lot -> Set a passed Subject Lot to List of Sub-Combine Lot.
	 */
	public List<CombineLotDB> getSubCombineLotList(String subjectLotNo);

}
