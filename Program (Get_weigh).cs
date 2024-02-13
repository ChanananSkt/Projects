using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.IO;

namespace get_weigh {
    public class Program {

        private String OUTPUT_PATH = AppDomain.CurrentDomain.BaseDirectory + "Output";
        private String LOG_PATH = AppDomain.CurrentDomain.BaseDirectory + "Log";

        static void Main()
        {
            Program ap = new Program();
            string[] readLines = { };
            string proc_cd_chk = String.Empty, weigh_no_chk = String.Empty;
            try
            {
                //readLines = File.ReadAllLines(@"C:\weg_no.txt");     // 16-Dec-2016 by Songpol [SPCS-Revamp]
                readLines = File.ReadAllLines(@"C:\SPCS\weg_no.txt");  // 16-Dec-2016 by Songpol [SPCS-Revamp]
                proc_cd_chk = readLines[0].Trim();
                weigh_no_chk = readLines[1].Trim();
            }
            catch (Exception ex)
            {
                //ap.writeLogFile(@"Cannot read file C:\weg_no.txt", ex.Message, true);     // 16-Dec-2016 by Songpol [SPCS-Revamp]
                ap.writeLogFile(@"Cannot read file C:\SPCS\weg_no.txt", ex.Message, true);  // 16-Dec-2016 by Songpol [SPCS-Revamp]
                Environment.Exit(1);
            }

            // Check Process code and Weighing No. //
            string file_name = String.Empty;
            int limit_min = 0;
            int limit_max = 0;
            string proc_cd_name = String.Empty;

            if (proc_cd_chk == "61")
            {
                //file_name = @"C:\SPCS\Weigher\Weigh01.log";   // 23-Nov-2016 by Songpol [SPCS-Revamp]
                file_name = @"C:\SPCS\AP\Weigher\Weigh01.log";  // 23-Nov-2016 by Songpol [SPCS-Revamp]
                limit_min = 300;
                limit_max = 599;
                proc_cd_name = "1CPL";
            }
            else if (proc_cd_chk == "62")
            {
                //file_name = @"C:\SPCS\Weigher\Weigh02.log";   // 23-Nov-2016 by Songpol [SPCS-Revamp]
                file_name = @"C:\SPCS\AP\Weigher\Weigh02.log";  // 23-Nov-2016 by Songpol [SPCS-Revamp]
                limit_min = 600;
                limit_max = 899;
                proc_cd_name = "2CPL";
            }
            else if (proc_cd_chk == "71")
            {
                //file_name = @"C:\SPCS\Weigher\Weigh03.log";   // 23-Nov-2016 by Songpol [SPCS-Revamp]
                file_name = @"C:\SPCS\AP\Weigher\Weigh03.log";  // 23-Nov-2016 by Songpol [SPCS-Revamp]
                limit_min = 1;
                limit_max = 299;
                proc_cd_name = "RCL";
            }
            // 02/Sep/2021 by Songpol [Support ECL Weigher] ---------------- S# //
            else if (proc_cd_chk == "21")
            {  
                file_name = @"C:\SPCS\AP\Weigher\Weigh04.log";
                limit_min = 900;
                limit_max = 990;
                proc_cd_name = "ECL";
            }
            // 02/Sep/2021 by Songpol [Support ECL Weigher] ---------------- E# //
            else if (proc_cd_chk == "00")   // For test
                Environment.Exit(0);
            else
            {
                ap.writeLogFile("Process code (" + proc_cd_chk + ") error",
                                "Process code is not valid for Weighing operation", true);
                Environment.Exit(1);
            }

            if (!(Int32.Parse(weigh_no_chk) >= limit_min & Int32.Parse(weigh_no_chk) <= limit_max))
            {
                ap.writeLogFile("Weighing No.(" + weigh_no_chk + ") is not valid",
                                "Weighing No. is out of 1CPL Weighing No. range (300-599)", true);
                Environment.Exit(1);
            }

            // Read file C:\SPCS\AP\Weigher\Weigh.log //
            string line;
            int cnt_line = 0;

            DateTime dateChk = new DateTime(1995, 01, 01);
            DateTime date1;
            string data = String.Empty;
            string weigh_no = String.Empty;
            double weight = 0;
            int start_ind = 0;

            try
            {
                StreamReader file = new StreamReader(file_name);
                while ((line = file.ReadLine()) != null)
                {
                    if (line[0] == ' ')
                    {
                        start_ind = line.IndexOf("]");
                        data = line.Substring(start_ind + 1).Replace(" ", "");

                        string[] test = data.Split(',');

                        if (test.Length == 4)
                        {
                            if (data.Split(',')[2] == weigh_no_chk)  //[2] = Weighing No., [3] = Measured weight
                            {
                                date1 = DateTime.ParseExact(data.Substring(0, 13), "MMddyyyy,HHmm", System.Globalization.CultureInfo.InvariantCulture);
                                if ((date1 > dateChk) & (date1 > DateTime.Now.AddDays(-3)))
                                {
                                    dateChk = date1;
                                    weigh_no = data.Substring(14, 3);
                                    weight = Double.Parse(data.Substring(18, 5)) / 1000;
                                    break;
                                }
                            }
                        }
                    }
                    cnt_line++;
                }
                file.Close();
            }
            catch (Exception ex)
            {
                //ap.writeLogFile("Cannot read file C:\\SPCS\\Weigher\\Weigh.log.", ex.Message, true); // 23-Nov-2016 by Songpol [SPCS-Revamp]
                ap.writeLogFile(@"Cannot read file C:\SPCS\AP\Weigher\Weigh.log.", ex.Message, true);  // 23-Nov-2016 by Songpol [SPCS-Revamp]
                Environment.Exit(1);
            }

            // Write C:\SPCS\weigh.txt (Recovery Function)
            // Check/Create output path
            if (!Directory.Exists(ap.OUTPUT_PATH))
                Directory.CreateDirectory(ap.OUTPUT_PATH);

            //StreamWriter weigh_file = new StreamWriter(@"C:\weigh.txt", false);               // 16-Dec-2016 by Songpol [SPCS-Revamp]
            StreamWriter weigh_file = new StreamWriter(ap.OUTPUT_PATH + @"\weigh.txt", false);  // 16-Dec-2016 by Songpol [SPCS-Revamp]

            if (weight > 0.0)
            {
                weigh_file.WriteLine(weigh_no);
                weigh_file.WriteLine(weight.ToString("00.0000"));
            }
            else
            {
                weigh_file.WriteLine("000");
                weigh_file.WriteLine("00.0000");
            }

            weigh_file.Close();

            Environment.Exit(0);
        }

        private void writeLogFile(String msg, String reason, Boolean flg_msg_box)
        {
            string log_name = DateTime.Now.ToString("yyyyMMdd") + "_err.log";

            if (flg_msg_box)
            {
                MessageBox.Show(msg + (char)13 + (char)10 +
                                "Please see error log in " + this.LOG_PATH + @"\" + log_name, 
                                "Error Message", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

            // Check/Create log path
            if (!Directory.Exists(this.LOG_PATH))
                Directory.CreateDirectory(this.LOG_PATH);

            // Delete old log file (older than 1 week) //
            string[] logfiles = Directory.GetFiles(this.LOG_PATH);
            foreach (string file in logfiles)
            {
                FileInfo fi = new FileInfo(file);
                if (fi.LastAccessTime < DateTime.Now.AddDays(-7))
                    fi.Delete();
            }

            // Write Log //
            StreamWriter logfile = new StreamWriter(this.LOG_PATH + @"\" + log_name, true);
            logfile.WriteLine(DateTime.Now.ToString("dd/MMM/yyyy HH:mm:ss"));
            logfile.WriteLine("Message\t: " + msg);
            logfile.WriteLine("Cause\t: " + reason);
            logfile.WriteLine("\r\n#########################################################\r\n");
            logfile.Close();
        }
    }
}