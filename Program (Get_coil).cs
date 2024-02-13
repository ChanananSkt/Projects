using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

namespace GetCoil
{
    class Program
    {
        static void Main(string[] args)
        {
            Program ap = new Program();

            //1CPL - 61COIL_NO.txt
            //ap.getFtpFile("ftp://203.151.20.51//home1//appl//spcs_adm//production//dmp//61COIL_NO.txt", "61COIL_NO.txt", "spcs_adm", "spcs_adm");
            ap.getPHDFile("61", @"\\203.151.40.40\COIL_DATA\61COIL_NO.txt");
            //2CPL - 62COIL_NO.txt
            //ap.getFtpFile("ftp://203.151.20.51//home1//appl//spcs_adm//production//dmp//62COIL_NO.txt", "62COIL_NO.txt", "spcs_adm", "spcs_adm");
            ap.getPHDFile("62", @"\\203.151.40.161\COIL_DATA\62COIL_NO.txt");            
        }

        //-- FTP from CPUSTCDB --//
        private void getFtpFile(string remoteFtpPath, string localDestPath, string username, string passwd)
        {
            try
            {
                FtpWebRequest req = (FtpWebRequest)WebRequest.Create(@remoteFtpPath);
                req.Method = WebRequestMethods.Ftp.DownloadFile;
                req.Credentials = new NetworkCredential(username, passwd); //, "SPCS");
                req.Proxy = null;

                FtpWebResponse response = (FtpWebResponse)req.GetResponse();
                Stream respStream = response.GetResponseStream();
                StreamReader reader = new StreamReader(respStream);

                using (StreamWriter wr = new StreamWriter(localDestPath, false))
                {
                    long length = response.ContentLength;
                    int buffSize = 2048;
                    int readCount;
                    byte[] buff = new byte[2048];

                    readCount = respStream.Read(buff, 0, buffSize);
                    while (readCount > 0)
                    {
                        string test = System.Text.Encoding.UTF8.GetString(buff, 0, readCount).Replace("\n", System.Environment.NewLine);
                        wr.WriteLine(test);
                        readCount = respStream.Read(buff, 0, buffSize);
                    }
                    wr.Close();
                }
                reader.Close();
                response.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                Console.ReadLine();
            }
        }

        //-- Copy from 1CPL/2CPL SPCS PC --//
        private void getPHDFile(string line, string path)
        {
            try
            {
                if (File.Exists(line.Trim() + "COIL_NO.txt"))
                    File.Delete(line.Trim() + "COIL_NO.txt");
                File.Copy(path, line.Trim() + "COIL_NO.txt");
            }
            catch (Exception ex)
            {
                using (StreamWriter wr = new StreamWriter(line.Trim() + "COIL_NO.txt"))
                {
                    wr.WriteLine(ex.Message);
                    wr.WriteLine(" ");
                    wr.Close();
                }
            }
        }
    }
}
