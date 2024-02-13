package spcs.bigboot.delchk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

//########## Final Modified by Epick (Sahassawat O.) on 07/11/2022 ##########

@Component
@ConfigurationProperties
@Data
public class AlarmProperties {
	
	private String alarmcraneClient1;
	private String alarmcraneClient2;
	private String alarmcraneClient3;
	private String alarmcraneClient4;
	private String alarmcraneClient5;
	private String alarmpulpitClient1;
	private String alarmpulpitClient2;
	
	
	public String getCraneClient1() {
		return alarmcraneClient1;
	}
	public String getCraneClient2() {
		return alarmcraneClient2;
	}
	public String getCraneClient3() {
		return alarmcraneClient3;
	}
	public String getCraneClient4() {
		return alarmcraneClient4;
	}
	public String getCraneClient5() {
		return alarmcraneClient5;
	}
	public String getPulpitClient1() {
		return alarmpulpitClient1;
	}
	public String getPulpitClient2() {
		return alarmpulpitClient2;
	}
	
	
}
