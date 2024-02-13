package spcs.bigboot.delchk.dao.client;

import spcs.bigboot.delchk.entity.db.ClientInfo;
import spcs.lib.entity.db.ClientMqInfo;

public interface DeliveryCheckClientDAO {
	
	/**
     * This method is used to get MQ information of a passed SPCS client-name (not oracle-terminal).
     */
	public ClientMqInfo getMqInfo(String clientName);
	
	/**
     * This method is used to get Printer information of a passed SPCS client-id (oracle-terminal).
     */
	public ClientInfo getPrinterInfo(String clientId);
	
}
