package spcs.bigboot.delchk.dao.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spcs.bigboot.delchk.entity.db.ClientInfo;


public class DeliveryCheckPrinterInfoMapper implements RowMapper<ClientInfo>  {

    @Override
    public ClientInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ClientInfo prtInfo = new ClientInfo();
    	prtInfo.setOracleTerminal(rs.getString("cl_lo001"));
		prtInfo.setLaserPrinterName(rs.getString("la_pr005"));
		prtInfo.setBarcodePrinterName(rs.getString("ba_pr002"));
		prtInfo.setBarcodePrinterNameForTestPiece(rs.getString("ba_pr003"));
		prtInfo.setBarcodePrinterNameForPackageCard(rs.getString("ba_pr004"));
		prtInfo.setBarcodePrinterNameForProductLabel(rs.getString("ba_pr005"));
		prtInfo.setSerialPrinterName(rs.getString("se_pr003"));
		prtInfo.setProcessLine(rs.getString("pr_co001"));
        return prtInfo;
    }

}
