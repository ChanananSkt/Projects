package spcs.bigboot.delchk.dao.alarm;

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
	public int countUncompletedShiplot() {
		log.debug("********** Through this step (Shipment JDBC) *********** =");
		String sql = "select count(*) from (                         "
				+ "(select t1.sh_lo006 sh_lo006                   " + "  from cs_tblef001 t1,                         "
				+ "       cs_tblxx008 t8                          " + " where t1.sh_st001 = '0'                       "
				+ "   and t1.sh_lo006 =t8.sh_lo006                " + " group by t1.sh_lo006                          "
				+ " having count(t8.pa_co001) > 0 )               " + "MINUS                                          "
				+ "(select t1.sh_lo006 sh_lo006                   " + "  from cs_tblef001 t1,                         "
				+ "       cs_tblxx008 t8                          " + " where t1.sh_st001 = '0'                       "
				+ "   and t1.sh_lo006 =t8.sh_lo006                " + "   and t8.wa_di004 = '0'                       "
				+ " group by t1.sh_lo006                          " + " having count(t8.pa_co001) > 0  )              "
				+ ")";
		try {
			int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
			return count;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return -1;
		}

	}

	@Override
	public List<String> getUncompletedShiplot() {
		log.debug(" ********** Through this step (Shipment JDBC) *********** ");
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
