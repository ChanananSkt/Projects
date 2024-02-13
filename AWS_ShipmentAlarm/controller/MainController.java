package spcs.aws.web.controller.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spcs.aws.web.controller.CranePageController;
import spcs.aws.web.entity.ErrorCode;
import spcs.aws.web.entity.Pit;
import spcs.aws.web.entity.req_res.Payload;
import spcs.aws.web.plant2.service.nedi.SN91SendingService;
import spcs.aws.web.service.crane.CraneControlService;
import spcs.aws.web.service.dock.DockInEventService;
import spcs.aws.web.service.frontend.FrontEndService;
import spcs.aws.web.service.pit.PitControlService;

@RestController
public class MainController {
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	private SimpMessagingTemplate template;
	
	private static String RESPONSE_TRUE = "{\"IsSuccess\":\"True\"}";
	private static String RESPONSE_FALSE = "{\"IsSuccess\":\"False\",\"Msg\":\"Error\"}";
	
	private static String URL_DOCK_IN = "/topic/dock-in";
	private static String URL_LOGIN = "/topic/reset-login";
	
	@Autowired
	CraneControlService craneService;
	@Autowired
	PitControlService pitService;
	@Autowired
	FrontEndService frontEndService;
	@Autowired
	DockInEventService dockInService;
	
	// TODO For External test
	@Autowired
	SN91SendingService sn91Service;
	
	@Autowired
	public MainController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@GetMapping(value = "/test", produces = "text/plain")
	public String greeting(@RequestParam(name = "action", required = false, defaultValue = "") String action) {
		if ("1".equals(action)) {
			this.template.convertAndSend(URL_DOCK_IN,
					new Payload("Time is : " + new SimpleDateFormat("HH:mm:ss").format(new Date())));
		}
		return "true";
	}
	
	/**
	 * <dl><dt><span class="strong">(SPD request on 02/Apr/2019)</span></dt></dl>
	 * This method is used to set Task Schedule : Require Crane OP to login when
	 * discharging PC.<br>
	 * (every 0.00, 8.00 and 16.00)<br>
	 */
	@Scheduled(cron = "0 0 0,8,16 * * *")
	public void scheduleTaskWithCronExpression() {
		log.info("-- task schedule for reset dischargePerson -------");
		
		// clear discharged person on cache-crane tbl (all cranes)
		boolean status = true;
		for (String craneId : new String[] { "01", "02", "03", "04", "05", "06" }) {
			status = this.craneService.getCraneDao().clearDischargeInfo(craneId);
			if (!status) {
				break;
			}
		}
		if (status) {
			this.template.convertAndSend(URL_LOGIN, new Payload(RESPONSE_TRUE));
		}
		
		log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
	}
	
	/**
	 * This method is used to clear Dock-in items of a CachePit record(TBLEF907) 
	 * of a passed DockCode/Pit.
	 * @param request &nbsp;&nbsp;dockCode and pit<br><br>
	 * 09/Aug/2021 - change URL from /admin/dock/clear
	 */
	@PatchMapping(value = "/admin/clear/dock", produces = "text/plain")
	public ResponseEntity clearCacheDock(@RequestParam(name = "dock", required = true) String dockCode,
			   							 @RequestParam(name = "pit", required = true) String pit) {
		log.info("-- clear dock-in cache ---------------------------");
		log.info("request :: dock=" + dockCode + ", pit=" + pit);
		
		Pit tempPit = new Pit(dockCode, pit);
		
		// set CacheDock.Dock-InInfo = NULL
		boolean status = this.pitService.getCachePitDao().clearCacheDockIn(tempPit);		

		// 26/Dec/2020 - set CacheDock.ScannedDock-InInfo = NULL
		status = this.pitService.getCachePitDao().clearCacheDockPage(tempPit);
		
		// 04/Jan/2021 - set TBLEF908.do_da002 (dock-out_date) = SYSDATE
		status = this.dockInService.getDockInDao().setCompleteDockInRecord(tempPit);
		
		// update FrontEnd page
		String header = StringUtils.rightPad(CranePageController.CLIENT_REQ, 16) + ": " + "clear pit       : ";
		status = this.frontEndService.setPitInfoToFrontEnd(header, tempPit);
		
		log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
		
		return new ResponseEntity(status ? RESPONSE_TRUE : RESPONSE_FALSE, HttpStatus.OK);
	}
	
	/**
	 * This method is used to clear Crane event items of a CacheCrane record(TBLEF906) 
	 * of a passed Crane ID.<br><br>
	 * 09/Aug/2021 - change URL from /admin/crane/clear
	 */
	@PatchMapping(value = "/admin/clear/crane", produces = "text/plain")
	public ResponseEntity clearCacheCrane(@RequestParam(name = "crane", required = true) String craneId) {
		log.info("-- clear crane-event cache -----------------------");
		log.info("request :: craneId=" + craneId);
		
		// set CacheCrane.CraneEvent = NULL
		boolean status = this.craneService.getCraneDao().clearCacheRecord(craneId);
		
		// update FrontEnd page
		String header = StringUtils.rightPad(CranePageController.CLIENT_REQ, 16) + ": " + "clear crane     : ";
		status = this.frontEndService.setCraneInfoToFrontEnd(header, craneId);
		
		log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
		
		return new ResponseEntity(status ? RESPONSE_TRUE : RESPONSE_FALSE, HttpStatus.OK);
	}
	
	/**
	 * This method is used to clear Discharge info. items of a CacheCrane record(TBLEF906) 
	 * of a passed Crane ID.<br><br>
	 * 09/Aug/2021 - change URL from /admin/discharge/clear
	 */
	@PatchMapping(value = "/admin/clear/discharge", produces = "text/plain")
	public ResponseEntity clearCacheDischarge(@RequestParam(name = "crane", required = true) String craneId) {
		log.info("-- clear discharge info. cache -------------------");
		log.info("request :: craneId=" + craneId);
		
		// set CacheCrane.DischargeInfo = NULL
		boolean status = this.craneService.getCraneDao().clearDischargeInfo(craneId);
		String header = StringUtils.rightPad(CranePageController.CLIENT_REQ, 16) + ": " + "clear discharge : ";
		status = this.frontEndService.setCraneInfoToFrontEnd(header, craneId);
		
		log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
		
		return new ResponseEntity(status ? RESPONSE_TRUE : RESPONSE_FALSE, HttpStatus.OK);
	}
	
	/**
	 * <b>10/Aug/2021 - For External Test</b><br>
	 * This method is used to test send SN91 interface to Plant#2.
	 */
	@GetMapping(value = "/sendsn91", produces = "text/plain")
	public String sendSn91(@RequestParam(name = "flag", required = true) String flag,
						   @RequestParam(name = "coil", required = true) String coilNo,
						   @RequestParam(name = "shipLot", required = true) String shipLotNo,
						   @RequestParam(name = "lastCoil", required = true) String lastCoil) {
		
		boolean isLastCoil = ("1".equals(lastCoil) ? true : false);
		ErrorCode errorCode = this.sn91Service.sendSN91Interface(flag, coilNo, shipLotNo, isLastCoil);
		
		log.info(String.format("%1$50s\r\n", "").replace(' ', '-'));
		
		if (errorCode != null) {
			return errorCode.toString();
		}
		return "true";
	}
	
}
