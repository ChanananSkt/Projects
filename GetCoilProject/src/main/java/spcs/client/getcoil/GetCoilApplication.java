package spcs.client.getcoil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.dsl.IntegrationFlow;

//import spcs.lib.service.MqPutService;
import spcs.client.getcoil.GetCoilApplication;
import spcs.client.getcoil.service.GetCoilProcess;

@SpringBootApplication(scanBasePackages= {"spcs"})
public class GetCoilApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(GetCoilApplication.class);
	
//	@Autowired
//	private MqPutService mqService;
	@Autowired
	private GetCoilProcess getCoilService;
	
//	@Value("${spcs.client.path}")
//	private String path;

	
	public static void main(String[] args) throws Exception {
		// disabled banner, don't want to see the spring logo.
		SpringApplication app = new SpringApplication(GetCoilApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setHeadless(false);	  // to support tray-icon. 
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		getCoilService.checkFile("61","62");
		getCoilService.getFile("\\\\203.151.40.40\\COIL_DATA\\","61");
		getCoilService.getFile("\\\\203.151.40.161\\COIL_DATA\\","62");
		log.info("System exist.");
		System.exit(0);

		
//		getCoilService.getPHDFile("C:\\SourceCoil\\1\\","t1");
//		getCoilService.getPHDFile("C:\\SourceCoil\\2\\","t2");
		
	}
}