package spcs.aws.web.dao.alarm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


//########## Final Modified by Epick (Sahassawat O.) on 08/11/2022 ##########

@Repository
public class ShipmentJDBCTemplate implements ShipmentDAO {
	private static Logger log = LoggerFactory.getLogger(ShipmentJDBCTemplate.class);

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplateObject;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	
	@Override
	public List<String> getUncompletedShiplot() {
		log.debug("Can't getcountshiplot Error");
		String sql1 = "select sh_lo006\r\n" + "from (\r\n" + "    (select t1.sh_lo006 as sh_lo006                  \r\n"
				+ "    from cs_tblef001 t1, cs_tblxx008 t8                          \r\n"
				+ "    where t1.sh_st001 = '0' and t1.sh_lo006=t8.sh_lo006                \r\n"
				+ "    group by t1.sh_lo006 \r\n" + "    having count(t8.pa_co001) > 0 )\r\n" + "    \r\n"
				+ "                  \r\n" + "    MINUS                                          \r\n"
				+ "        (select t1.sh_lo006 sh_lo006                   \r\n"
				+ "         from cs_tblef001 t1, cs_tblxx008 t8                          \r\n"
				+ "         where t1.sh_st001 = '0' and t1.sh_lo006 =t8.sh_lo006 and t8.wa_di004 = '0'                  \r\n"
				+ "         group by t1.sh_lo006                          \r\n"
				+ "         having count(t8.pa_co001) > 0  ) \r\n" + "         \r\n" + "    )\r\n" + "  ";
		try {
			List<String> cshiplot = jdbcTemplateObject.queryForList(sql1, String.class);
			return cshiplot;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}


