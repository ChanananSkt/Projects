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


//########## Final Modified by Epick (Sahassawat O.) on 07/11/2022 ##########

@Repository
public class TruckJDBCTemplate implements TruckDAO {
	private static Logger log = LoggerFactory.getLogger(TruckJDBCTemplate.class);

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplateObject;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}


	@Override
	public List<String> getShiplotlist() {
		//log.debug("getShiplotlist [shiplot=" + shiplotNolist + "]");

		String sql = "select  sh_lo006                                                                   " + // ---Plant1
				"from (                                                                                "
				+ "select distinct sh_lo006,(sysdate-max(wa_di001))*1440 diff_min      " + // --Overland_Exwork
				"from cs_tblxx008                                                                      "
				+ "where sh_lo006 in (                                                                   "
				+ "                  select sh_lo006                                                     "
				+ "                    from cs_tblxx008                                                  "
				+ "                   where sh_st001 = '0'                                               "
				+ "                     and sh_lo006 is not null                                         "
				+ "                     and substr(sh_lo006,1,1) in ('T','Z')                            "
				+ "                     and wa_di004 = '1'                                               "
				+ "                   )                                                                  "
				+ "  and sh_lo006 in (                                                                   "
				+ "                  select sh_lo006                                                     "
				+ "                 from cs_tblxx008                                                     "
				+ "                where sh_st001 = '0'                                                  "
				+ "                  and sh_lo006 is not null                                            "
				+ "                  and substr(sh_lo006,1,1) in ('T','Z')                               "
				+ "                  and wa_di004 = '0'                                                  "
				+ "    )                                                                                 "
				+ "   group by sh_lo006                                                                  "
				+ " UNION                                                                        " + // --Export
				" select distinct go_lo002 sh_lo006,(sysdate-max(wa_di001))*1440 diff_min              "
				+ "  from cs_tblxx008                                                                    "
				+ " where go_lo002 in (                                                                  "
				+ "                  select go_lo002                                                     "
				+ "                    from cs_tblxx008                                                  "
				+ "                   where sh_st001 = '0'                                               "
				+ "                     and sh_lo006 is not null                                         "
				+ "                    and substr(sh_lo006,1,1) = 'S'                                    "
				+ "                     and wa_di004 = '1'                                               "
				+ "                   )                                                                  "
				+ "   and go_lo002 in (                                                                  "
				+ "                  select go_lo002                                                     "
				+ "                 from cs_tblxx008                                                     "
				+ "                where sh_st001 = '0'                                                  "
				+ "                  and sh_lo006 is not null                                            "
				+ "                 and substr(sh_lo006,1,1) = 'S'                                       "
				+ "                  and wa_di004 = '0'                                                  "
				+ "    )                                                                                 "
				+ "    group by go_lo002                                                                 "
				+ " UNION                                                                        " + // --Plant2
				"select distinct sh_lo006,(sysdate-max(wa_di001))*1440 diff_min      " + // --Overland_Exwork
				"from cs_tblef900                                                                      "
				+ "where sh_lo006 in (                                                                   "
				+ "                  select sh_lo006                                                     "
				+ "                    from cs_tblef900                                                  "
				+ "                   where sh_st001 = '0'                                               "
				+ "                     and sh_lo006 is not null                                         "
				+ "                     and substr(sh_lo006,1,1)  in ('A','Y')                           "
				+ "                     and wa_di004 = '1'                                               "
				+ "                   )                                                                  "
				+ "  and sh_lo006 in (                                                                   "
				+ "                  select sh_lo006                                                     "
				+ "                 from cs_tblef900                                                     "
				+ "                where sh_st001 = '0'                                                  "
				+ "                  and sh_lo006 is not null                                            "
				+ "                  and substr(sh_lo006,1,1) in ('A','Y')                               "
				+ "                  and wa_di004 = '0'                                                  "
				+ "    )                                                                                 "
				+ "    group by sh_lo006                                                                 "
				+ " UNION                                                                       " + // --Export
				" select distinct go_lo002 sh_lo006,(sysdate-max(wa_di001))*1440 diff_min              "
				+ "  from cs_tblef900                                                                    "
				+ " where go_lo002 in (                                                                  "
				+ "                  select go_lo002                                                     "
				+ "                    from cs_tblef900                                                  "
				+ "                   where sh_st001 = '0'                                               "
				+ "                     and sh_lo006 is not null                                         "
				+ "                    and substr(sh_lo006,1,1) = 'I'                                    "
				+ "                     and wa_di004 = '1'                                               "
				+ "                   )                                                                  "
				+ "   and go_lo002 in (                                                                  "
				+ "                  select go_lo002                                                     "
				+ "                 from cs_tblef900                                                     "
				+ "                where sh_st001 = '0'                                                  "
				+ "                  and sh_lo006 is not null                                            "
				+ "                  and substr(sh_lo006,1,1) = 'I'                                      "
				+ "                  and wa_di004 = '0'                                                  "
				+ "    )                                                                                 "
				+ "    group by go_lo002                                                                 "
				+ ") where diff_min > 105 "; // 1 hrs 45 min.
		
		try {			
			List<String> shiplot = jdbcTemplateObject.queryForList(sql, String.class);
			return shiplot;


		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	

}
