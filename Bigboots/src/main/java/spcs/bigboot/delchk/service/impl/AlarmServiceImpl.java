package spcs.bigboot.delchk.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import spcs.bigboot.delchk.config.AlarmProperties;
import spcs.bigboot.delchk.BigbootsApplication;
import spcs.bigboot.delchk.dao.alarm.ShipmentDAO;
import spcs.bigboot.delchk.dao.alarm.TruckDAO;
import spcs.bigboot.delchk.service.AlarmService;
import spcs.lib.service.MqPutService;

@Service
public class AlarmServiceImpl implements AlarmService {
	private static Logger log = LoggerFactory.getLogger(BigbootsApplication.class);

	@Autowired
	private ShipmentDAO shipDao;

	@Autowired
	private TruckDAO truckDao;

	@Autowired
	private MqPutService MqService;

	@Value("${spcs.client.name}")
	private String clientName;

	@Autowired
	private AlarmProperties alarmProp;

	@Override
	public TruckDAO checkTruckAlarm() {

		return truckDao;
	}

	@Override
	public ShipmentDAO checkShipAlarm() {

		return shipDao;
	}

//	@Override
//	public boolean displayPopup(String truckDao) {
//		List<String> truck = this.truckDao.getShiplotlist();
//		 
//		if (truckDao == null) {
//			this.MqService.putPopupMqMessage(clientName, "SHIP_UNCOMPLETE", "WX000002",
//					"Not Found ShiplotID [empID=" + truck + "]");
//			return false;
//		} else {
//			this.MqService.putPopupMqMessage(clientName, "TRUCK_UNLOADING", "WX000002",
//					"Found Loading Shipment lot not completed!! ..Please inform Logistics !!! " + truck);
//			return true;
//		}
//	}

//	@Override
//	public boolean displayPopup(String shipDao) {
//		int count = this.shipDao.countUncompletedShiplot();
//		 
//		if (count == 0) {
//			this.MqService.putPopupMqMessage(clientName, "SHIP_UNCOMPLETE", "WX000002",
//					"Not Found ShiplotID [empID=" + count + "]");
//			return false;
//		} else {
//			this.MqService.putPopupMqMessage(clientName, "TRUCK_UNLOADING", "WX000002",
//					"Found Loading Shipment lot not completed!! ..Please inform Logistics !!! " + count);
//			return true;
//		}
//	}

	// MQ-Popup for Shipment Uncomplete (Mod. by New SE)
	@Override
	public boolean displayPopupShipment(String shiplot) {
		try {
			List<String> cshiplot = this.shipDao.getUncompletedShiplot();
			log.debug("*##########* through this step (Shipment)!! *##########*");

			if (cshiplot.size() != 0) {
				MDC.put("logFileName", "ShipAlarm");
				this.MqService.putPopupMqMessage(alarmProp.getCraneClient1(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getCraneClient2(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getCraneClient3(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getCraneClient4(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getCraneClient5(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getPulpitClient1(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				this.MqService.putPopupMqMessage(alarmProp.getPulpitClient2(), "SHIP_UNCOMPLETED", "EEFB0001",
						"Shiplot no.=" + cshiplot);
				MDC.remove("logFileName");
				
				return false;

			}
		} catch (Exception e) {
			log.debug(" " + e);
		}
		return false;

	}

	// MQ-Popup for Truck Unloading (Mod. by New SE)
	@Override
	public boolean displayPopupTruck(String shiplottruck) {
		try {
			List<String> truck = this.truckDao.getShiplotlist();
			log.debug("*##########* through this step (Truck)!! *##########*");

			if (truck.size() != 0) {
				MDC.put("logFileName", "TruckAlarm");
				this.MqService.putPopupMqMessage(alarmProp.getPulpitClient1(), "TRUCK_UNLOADING", "EEFB0002",
						"Shiplot no.=" + truck);
				this.MqService.putPopupMqMessage(alarmProp.getPulpitClient2(), "TRUCK_UNLOADING", "EEFB0002",
						"Shiplot no.=" + truck);
				log.debug("Truck" + truck);
				MDC.remove("logFileName");
				return false;
			}
		} catch (Exception e) {
			log.debug(" " + e);
		}
		return false;
	}

}
