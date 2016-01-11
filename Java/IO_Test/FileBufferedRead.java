import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Exception;

public class FileBufferedRead{

	public FileBufferedRead(){
		try {
			File inFile  = new File("infile3.txt");
			File outFile = new File("outfile3.txt");

			FileReader fr = new FileReader(inFile);
			BufferedReader br = new BufferedReader(fr);
			
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String temp = null;
			boolean keepReading = true;
			while (keepReading){
				temp = br.readLine();
				if (temp == null){
					keepReading = false;
				}else{
					bw.write(temp);
					bw.newLine();
				}
			}
			br.close();
			fr.close();
			bw.close();
			fw.close();
		}
		catch (IOException e) {
			System.err.println("IOException happens: "+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Exception happens: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] Args){
		FileBufferedRead fr = new FileBufferedRead();
	}
}


