package spcs.bigboot.delchk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import spcs.bigboot.delchk.config.AlarmProperties;
import spcs.bigboot.delchk.dao.alarm.ShipmentDAO;
import spcs.bigboot.delchk.dao.alarm.TruckDAO;
import spcs.bigboot.delchk.dao.employee.EmployeeDAO;
import spcs.bigboot.delchk.entity.db.Employee;
import spcs.bigboot.delchk.service.AlarmService;
import spcs.bigboot.delchk.service.EmployeeInfoProcess;

import spcs.lib.service.MqPutService;

@EnableScheduling
@SpringBootApplication(scanBasePackages = { "spcs" })
public class BigbootsApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(BigbootsApplication.class);

//  @Autowired
//	private SerialPortDataReceiver serialPortReceiver;	

	// Sent to Mq server and Mq popup

//	@Autowired
//	private EmployeeDAO emDAO;

//	@Autowired
//	private EmployeeInfoProcess empService;

	@Autowired
	private AlarmService alarmService;

	public static void main(String[] args) throws Exception {
		// disabled banner, don't want to see the spring logo
		SpringApplication app = new SpringApplication(BigbootsApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setHeadless(false); // to support tray-icon
		app.run(args);
	}

	@Override
	public void run(String... args) {
		// new Thread((Runnable) serialPortReceiver).start();
		log.info("TESTAWS");

//		Employee employee = this.emDAO.getEmployeeInfo(Integer.parseInt(args[0]));
//
//		if (employee == null) {
//			this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "WX000002",
//					"Not Found EmployeeID [" + args[0] + "]");
//
//		} else {
//			this.MqService.putPopupMqMessage(clientName, "Mai Dai Test", "IX000001",
//					"Hello " + employee.getPrefix() + " " + employee.getFirstName() + " " + employee.getLastName() + " "
//							+ employee.getDepartment() +" " + employee.getPosition());
//		}

		/* --------------- check arguments ---------------- */

//		Employee employee = this.empService.getEmployeeInfo(Integer.parseInt(args[0]));
//		this.empService.displayPopup(Integer.parseInt(args[0]), employee);

//	     TruckDAO alarm = this.alarmService.checkTruckAlarm();
//	     boolean alarmship =this.alarmService.displayPopupShipment("");
//	     log.debug("alarm shipment complete!!");
//		
//	     boolean alarmtruck =this.alarmService.displayPopupTruck("");
//	     log.debug("alarm truck complete!!");
		// this.MqService.putPopupMqMessage("CPUCTX13", "Test", "WX000002", "Parameter
		// error [" + param + "]");
		// this.MqService.putPopupMqMessage(clientName, "Mai Dai Test Aun Ni khong
		// jing", "WX000002", "Parameter error [" + param + "]");
		// check Client ID
//		if (!(this.clientService.isValidClientId(args[0]))) {
//			log.error("Input Client ID is invalid !!!");
//			this.mqService.putPopupMqMessage("CPUCT006", "WmReq", "WX000002", "Parameter error [" + param + "]");
//			return;
//		}
//
//		if (args.length < 4) {
//			log.error("\r\n" + "ERROR : invalid operand. ==> " + param + "\r\n" + "$1 = SPCS client id;\r\n"
//					+ "$2 = calling program name (e.g. EBS06, EBS08);\r\n" + "$3 = line code (61:1CPL, 62:2CPL)\r\n"
//					+ "$4 = sending msg;");
//
//			this.mqService.putPopupMqMessage(args[0], args[1], "WX000002", "Parameter error [" + param + "]");
//			return;
//		}
//
//		PopupAttribute popupAttr = new PopupAttribute();
//		popupAttr.setClientId(args[0]);
//		popupAttr.setAppName(args[1]);
		log.info(String.format("%1$73s\n", "").replace(' ', '-'));
	}

	// Truck unloading will only run every 15 minute
	@Scheduled(cron = "0 */15 * ? * *", zone = "Asia/Bangkok")
	public void scheduleTaskWithCronExpression1() {

		String header = "TruckUnloaded_Alarm : ";
		log.debug(header + "-------------------- Start Service --------------------");

		boolean alarmship = this.alarmService.displayPopupTruck("");

		log.debug(header + "-------------------- End Service --------------------\r\n");

	}

	// Shipment uncompleted will only run every 1 minute
	@Scheduled(cron = "0 */1 * ? * *", zone = "Asia/Bangkok")
	public void scheduleTaskWithCronExpression() {

		String header = "Shipment_Alarm : ";
		log.debug(header + "-------------------- Start Service --------------------");

		boolean alarmship = this.alarmService.displayPopupShipment("");

		log.debug(header + "-------------------- End Service --------------------\r\n");

	}
}
