package spcs.bigboot.delchk.service;

import java.util.List;

import spcs.bigboot.delchk.dao.alarm.ShipmentDAO;
import spcs.bigboot.delchk.dao.alarm.TruckDAO;



public interface AlarmService {
	
	public TruckDAO checkTruckAlarm();
	
	public ShipmentDAO checkShipAlarm();
	
	public boolean displayPopupShipment(String shiplot);
	
	public boolean displayPopupTruck(String shiplottruck);
}
