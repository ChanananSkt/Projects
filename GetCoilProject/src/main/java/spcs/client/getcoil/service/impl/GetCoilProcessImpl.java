package spcs.client.getcoil.service.impl;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import spcs.client.getcoil.service.GetCoilProcess;

@Service
public class GetCoilProcessImpl implements GetCoilProcess {

	private static Logger log = LoggerFactory.getLogger(GetCoilProcessImpl.class);

	@Override // Copy file 61COIL_NO.txt from source path.
	public void checkFile(String file_chk1, String file_chk2){
		String path1 = "\\\\203.151.40.40\\COIL_DATA\\";
		String path2 = "\\\\203.151.40.161\\COIL_DATA\\";
		String file_no1 = "61";
		String file_no2 = "62";
		File file_61 = new File(path1+file_chk1+"COIL_NO.txt");
		File file_62 = new File(path2+file_chk2+"COIL_NO.txt");
		JFrame jframe = new JFrame();
		
		if(file_61.exists()&&file_62.exists()) {

		}else if(file_61.exists()==false&&file_62.exists()) {
			JOptionPane.showMessageDialog(jframe, "File " + file_chk1 + ".txt not founded.");
			createFile(file_no1);
			
		}else if(file_61.exists()&&file_62.exists()==false){
			JOptionPane.showMessageDialog(jframe, "File " + file_chk2 + ".txt not founded.");
			createFile(file_no1);	
			
		}else {
			JOptionPane.showMessageDialog(jframe, "File " + file_chk1 + ".txt and File " + file_chk2 + " not founded.");
			createFile(file_no1);
			createFile(file_no2);
		}
			

	}
	
	

	@Override // Copy file COIL_NO.txt from source path.
	public void getFile(String source, String line_no){
		String target = "D:\\GetCoil\\";
		Path source_path = Paths.get(source + line_no + "COIL_NO.txt");
		Path target_path = Paths.get(target + line_no + "COIL_NO.txt");
		try {
			Files.copy(source_path, target_path, StandardCopyOption.REPLACE_EXISTING);
			log.debug("Copied " + line_no + "COIL_NO.txt completed.");

		} catch (Exception ex2) {
			log.debug(line_no + "COIL_NO.txt uncompleted!!.");
		}
	}


	@Override // Create empty file when target file not founded.
	public void createFile(String file_name){
		Path empty_target = Paths.get("D:\\GetCoil\\" + file_name + "COIL_NO.txt");
		try {
			Files.createFile(empty_target);
		} catch (Exception ex) {
		}
	}

}
