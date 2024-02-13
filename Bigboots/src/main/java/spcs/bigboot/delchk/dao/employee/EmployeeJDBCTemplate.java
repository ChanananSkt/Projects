package spcs.bigboot.delchk.dao.employee;

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


import spcs.bigboot.delchk.entity.db.Employee;

@Repository
public class EmployeeJDBCTemplate implements EmployeeDAO {
	private static Logger log = LoggerFactory.getLogger(EmployeeJDBCTemplate.class);

	@Autowired
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplateObject;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public Employee getEmployeeInfo(int EmployeeID) {
		log.debug("getEmployeeInfo [EmployeeID=" + EmployeeID + "]");
		String query = "SELECT EM_NO001," + 
				"NA_00002," + 
				"NA_00001," + 
				"NA_00003," + 
				"BE_TO001," + 
				"PO_SI001" + 
				" FROM TBLXX900" + 
				" WHERE EM_NO001 = ? ";
		try {
			Employee employee = jdbcTemplateObject.queryForObject(query, new Object[] {EmployeeID}, new EmployeeMapper());
			return employee;

		} catch (EmptyResultDataAccessException e) {
			log.error("not found Employee-Info [EmployeeID=" + EmployeeID + "]");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
