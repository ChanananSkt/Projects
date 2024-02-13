package spcs.bigboot.delchk.dao.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import spcs.bigboot.delchk.entity.db.Employee;


public class EmployeeMapper implements RowMapper<Employee>  {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Employee employee = new Employee();
    	employee.setEmployeeID(rs.getInt("EM_NO001"));
    	employee.setPrefix(rs.getString("NA_00002"));
    	employee.setFirstName(rs.getString("NA_00001"));
    	employee.setLastName(rs.getString("NA_00003"));
    	employee.setDepartment(rs.getString("BE_TO001"));
    	employee.setPosition(rs.getString("PO_SI001"));
      return employee;
    }

}
