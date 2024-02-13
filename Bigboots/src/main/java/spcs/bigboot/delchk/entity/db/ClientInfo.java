package spcs.bigboot.delchk.entity.db;

import spcs.lib.entity.db.ClientPrinterInfo;

public class ClientInfo extends ClientPrinterInfo {
	private String processLine;

	public String getProcessLine() {
		return processLine;
	}

	public void setProcessLine(String processLine) {
		this.processLine = processLine;
	}

	@Override
	public String toString() {
		return "DelCheckClientPrinterInfo [oracleTerminal=" + this.getOracleTerminal() 
				+ ", laserPrinterName=" + this.getLaserPrinterName()
				+ ", barcodePrinterName=" + this.getBarcodePrinterName() 
				+ ", barcodePrinterNameForTestPiece=" + this.getBarcodePrinterNameForTestPiece() 
				+ ", barcodePrinterNameForPackageCard="	+ this.getBarcodePrinterNameForPackageCard() 
				+ ", barcodePrinterNameForProductLabel=" + this.getBarcodePrinterNameForProductLabel() 
				+ ", serialPrinterName=" + this.getSerialPrinterName() 
				+ ", processLine=" + this.getProcessLine() + "]";
	}

}
