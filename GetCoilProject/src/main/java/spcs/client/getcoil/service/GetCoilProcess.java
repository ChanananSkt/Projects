package spcs.client.getcoil.service;

//import spcs.client.getcoil.entity.GetCoil;

public interface GetCoilProcess {
	
		public void checkFile(String file_chk1, String file_chk2);
		public void getFile(String source_path,String line_no);
//		public void popupMessage(String file_no);
		public void createFile(String file_name);
}
