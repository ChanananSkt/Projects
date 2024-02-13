package spcs.aws.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

//########## Final Modified by Epick (Sahassawat O.) on 07/11/2022 ##########

@Component
@ConfigurationProperties
@Data
public class AlarmProperties {
	
	private String craneClient1;
	private String craneClient2;
	private String craneClient3;
	private String craneClient4;
	private String craneClient5;
	private String pulpitClient1;
	private String pulpitClient2;
	
	
	public String getCraneClient1() {
		return craneClient1;
	}
	public String getCraneClient2() {
		return craneClient2;
	}
	public String getCraneClient3() {
		return craneClient3;
	}
	public String getCraneClient4() {
		return craneClient4;
	}
	public String getCraneClient5() {
		return craneClient5;
	}
	public String getPulpitClient1() {
		return pulpitClient1;
	}
	public String getPulpitClient2() {
		return pulpitClient2;
	}
	
	
}
