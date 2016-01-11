import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileRead{

	public FileRead(){
		try {
			/**
			*	read/write characters
			**/
			File inFile  = new File("infile.txt");
			File outFile = new File("outfile.txt");

			FileReader fr = new FileReader(inFile);
			FileWriter fw = new FileWriter(outFile);
			
			int c = 0;
			boolean keepReading = true;
			while (keepReading){
				c = fr.read();
				if (c == -1){
					keepReading = false;
				}else{
					fw.write(c);
				}
			}
			fr.close();
			fw.close();
			
			/**
			*	read/write bytes
			**/
			File inFile2  = new File("infile2.txt");
			File outFile2 = new File("outfile2.txt");
			FileInputStream fis = new FileInputStream(inFile2);
			FileOutputStream fos = new FileOutputStream(outFile2);
			
			int c2 = 0;
			boolean keepReading2 = true;
			while (keepReading2){
				c2 = fis.read();
				if (c2 == -1){
					keepReading2 = false;
				}else{
					fos.write(c2);
				}
			}
			fis.close();
			fos.close();
		}
		catch (IOException e) {
			System.err.println("IOException happens: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] Args){
		FileRead fr = new FileRead();
	}
}


