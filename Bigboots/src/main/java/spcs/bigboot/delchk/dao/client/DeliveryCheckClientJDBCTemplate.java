package spcs.bigboot.delchk.dao.client;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spcs.bigboot.delchk.entity.db.ClientInfo;
import spcs.lib.dao.client.MqInfoMapper;
import spcs.lib.entity.db.ClientMqInfo;

@Repository
public class DeliveryCheckClientJDBCTemplate implements DeliveryCheckClientDAO {
	private static Logger log = LoggerFactory.getLogger(DeliveryCheckClientJDBCTemplate.class);

    @Autowired
    private DataSource dataSource; 
    
    private JdbcTemplate jdbcTemplateObject;
    
    @PostConstruct
    private void postConstruct() {
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }  

	@Override
	public ClientMqInfo getMqInfo(String clientName) {
		String query = "select UPPER(cl_na0001) cl_na0001, "
			 	 	 + "       UPPER(orterm) orterm,       "
			 	 	 + "       wxmc_mq,          "
			 	 	 + "       wxmc_put_queue,   "
			 	 	 + "       wxmc_get_queue    "
			 	 	 + "  from TBLWW006   "
			 	 	 + " where cl_na0001 like UPPER(?)||'%' ";
		try {
			ClientMqInfo mqInfo = jdbcTemplateObject.queryForObject(query, new Object[] {clientName}, new MqInfoMapper());
			return mqInfo;
	    
		} catch (EmptyResultDataAccessException e) {
			log.error("not found MQ-info [clientName=" + clientName + "]");
			return null;
	    
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	} 
    
	@Override
	public ClientInfo getPrinterInfo(String clientId) {
		String query = "select UPPER(cl_lo001) cl_lo001, "
			 	 	 + "       UPPER(la_pr005) la_pr005, "
			 	 	 + "       UPPER(ba_pr002) ba_pr002, "
			 	 	 + "       UPPER(ba_pr003) ba_pr003, "
			 	 	 + "       UPPER(ba_pr004) ba_pr004, "
			 	 	 + "       UPPER(ba_pr005) ba_pr005, "
			 	 	 + "       UPPER(se_pr003) se_pr003, "
			 	 	 + "       NVL(UPPER(pr_co001),'NONE') pr_co001 "
			 	 	 + "  from TBLWW001   "
			 	 	 + " where cl_lo001 = UPPER(?) ";
		
		try {
			ClientInfo prtInfo = jdbcTemplateObject.queryForObject(query, new Object[] {clientId}, new DeliveryCheckPrinterInfoMapper());
			return prtInfo;
	   
		} catch (EmptyResultDataAccessException e) {
			log.error("not found Printer-info [orterm=" + clientId + "]");
			return null;
	   
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
