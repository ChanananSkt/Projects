package spcs.aws.web.service.shiplot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spcs.aws.web.dao.shiplot.ShipmentLotDAO;
import spcs.aws.web.entity.db.shiplot.CombineLotDB;
import spcs.aws.web.entity.db.shiplot.GoingToBerthLotDB;
import spcs.aws.web.entity.db.shiplot.ShipmentLotDB;

@Service
public class ShipmentLotControlServiceImpl implements ShipmentLotControlService {
	private static final Logger log = LoggerFactory.getLogger(ShipmentLotControlServiceImpl.class);

	@Autowired
	ShipmentLotDAO shipmentLotDao;

	@Override
	public ShipmentLotDAO getShipmentLotDao() {
		return this.shipmentLotDao;
	}
	
	@Override
	public CombineLotDB getCombineLotObj(String shipLotNo) {
		GoingToBerthLotDB gtbLotObj = this.shipmentLotDao.findGoingToBerthLot(shipLotNo);
		if (gtbLotObj != null) {
			return this.getCombineLotObj(gtbLotObj.getShipmentLotNo(), gtbLotObj.getGoingToBerthLotNo());
		}
		return this.getCombineLotObj(shipLotNo, null);
	}
	
	@Override
	public CombineLotDB getCombineLotObj(String shipLotNo, String gtbLotNo) {
		CombineLotDB subjectLotObj = null;
		CombineLotDB combineLotObj = null;
		
		if (gtbLotNo == null) {
			ShipmentLotDB shipLotObj = this.shipmentLotDao.findShipmentLot(shipLotNo);
			if (shipLotObj == null) {
				log.error("not found shipment lot [shipLot=" + shipLotNo + "]");
				return null;
			}
			subjectLotObj = shipLotObj.toCombineLotObj();
			combineLotObj = this.shipmentLotDao.findCombineLotByShipmentLot(shipLotNo);
			
		} else {
			GoingToBerthLotDB gtbLotObj = this.shipmentLotDao.findGoingToBerthLot(shipLotNo, gtbLotNo);
			if (gtbLotObj == null) {
				log.error("not found going-to-berth lot [shipLot=" + shipLotNo + ", gtbLot=" + gtbLotNo + "]");
				return null;
			}
			subjectLotObj = gtbLotObj.toCombineLotObj();
			combineLotObj = this.shipmentLotDao.findCombineLotByGoingToBerthLot(shipLotNo, gtbLotNo);
		}
		
		if (combineLotObj == null) {
			// in case: not found combine-lot 
			// 		--> return a passed Lot No. as CombineLot Object
			combineLotObj = subjectLotObj;
		}		
		return combineLotObj;
	}

	@Override
	public List<CombineLotDB> getSubCombineLotList(String subjectLotNo) {
		log.info("getCombineLotList [subjectLot=" + subjectLotNo + "]");

		// find all subject shipment lot
		List<CombineLotDB> subCombineLotList = this.shipmentLotDao.findCombineLotList(subjectLotNo);
		log.info("comb lot size = " + Integer.toString(subCombineLotList.size()));

		// a passed shipmentLot is not sub-combine lot
		if (subCombineLotList.size() == 0) {			
			ShipmentLotDB shipLotDB = this.shipmentLotDao.findShipmentLot(subjectLotNo);
			if (shipLotDB == null) {
				GoingToBerthLotDB goingToBerthLotDB = this.shipmentLotDao.findGoingToBerthLot(subjectLotNo);
				if (goingToBerthLotDB == null) {
					return new ArrayList<CombineLotDB>();
				}
				subCombineLotList.add(goingToBerthLotDB.toCombineLotObj());
				
			} else {
				subCombineLotList.add(shipLotDB.toCombineLotObj());
			}
			
		// sub-combine lot
		} else {
			for (CombineLotDB combineLot : subCombineLotList) {
				if ((combineLot.getShippingInstructionDate() == null)
						| (combineLot.getShippingInstructionShift() == null)
						| (combineLot.getShippingInstructionRoundTrip() == 0)) {
					if (combineLot.getGoingToBerthLotNo() == null) {
						ShipmentLotDB shipLotDB = this.shipmentLotDao.findShipmentLot(combineLot.getShipmentLotNo());
						combineLot.setTransporterCode(shipLotDB.getTransporterCode());
						combineLot.setShippingInstructionShift(shipLotDB.getShippingInstructionShift());
						combineLot.setShippingInstructionDate(shipLotDB.getShippingInstructionDate());
						combineLot.setShippingInstructionRoundTrip(shipLotDB.getShippingInstructionRoundTrip());
					} else {
						GoingToBerthLotDB gtbLotDB = this.shipmentLotDao
								.findGoingToBerthLot(combineLot.getShipmentLotNo(), combineLot.getGoingToBerthLotNo());
						combineLot.setTransporterCode(gtbLotDB.getTransporterCode());
						combineLot.setShippingInstructionShift(gtbLotDB.getShippingInstructionShift());
						combineLot.setShippingInstructionDate(gtbLotDB.getShippingInstructionDate());
						combineLot.setShippingInstructionRoundTrip(gtbLotDB.getShippingInstructionRoundTrip());
					}
				}
			}
		}
		log.info("sub comb-lot list size = " + subCombineLotList.size());
		return subCombineLotList;
	}

}
