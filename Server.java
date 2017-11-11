import FS.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.nio.file.*;
import java.io.*;

class Server extends FileSystemPOA {

	private ORB orb;

	public void setORB(ORB orb_val) {
	 orb = orb_val;
	}

	public int create (String fileName) {
		System.out.println("\n - Creating...");
		File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + fileName + ".txt");

		if (file.exists() && file.isFile()) {
			System.out.println("File Exists at • " + file);
    	return 1;
		} else {
			try{
				PrintWriter writer = new PrintWriter(new FileWriter(file,false));
				writer.flush();
				writer.close();
				System.out.println("Created at • " + file);
				return 0;
			}
			catch(IOException e){
				System.out.println("Error • " + e.toString());
				return 3;
			}
		}
	}

	public int delete (String fileName) {
		System.out.println("\n - Deleting...");
		File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + fileName + ".txt");
		//File file = new File(fileName + ".txt");
		System.out.println(file.exists());
		System.out.println(file.isFile());

		if (file.exists() && file.isFile()) {
			if(file.delete()){
				System.out.println("File deleted at • " + file);
				return 0;
			}else{
				System.out.println("Fail to delete file");
				return 4;
			}
		} else {
			System.out.println("File doesnt't exists at • " + file);
			return 2;
		}
	}

	public int copy (String fileName, String newFileName) {
		System.out.println("\n - Copying...");
		File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + fileName + ".txt");
		File copyFile = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + newFileName + ".txt");

		if (file.exists() && file.isFile()) {
			if (copyFile.exists() && copyFile.isFile()) {
				System.out.println("\n • Already exist in file with the copy suggested name");
				return 5;
			} else {
				try {
					Files.copy(file.toPath(), copyFile.toPath());
					System.out.println("\n • Copy File created");
					return 0;
				} catch (IOException e) {
						e.printStackTrace();
						return 4;
				}
			}
		} else {
			System.out.println("File doesnt't exists at • " + file);
			return 2;
		}
	}

	public int write (String fileName, String text) {
		System.out.println("\n - Writing...");
		File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + fileName + ".txt");

		if (file.exists() && file.isFile()) {
			try{
				org.omg.CORBA.StringHolder readText = new org.omg.CORBA.StringHolder();
				int result = read(fileName, readText);
				PrintWriter writer = new PrintWriter(new FileWriter(file,false));

				if (result == 0){
					writer.println(readText.value + "\n" + text);
					writer.flush();
					writer.close();
					System.out.println("Writed '" + readText + text +"'  at • " + file);
					return 0;
				}else if (result == 2){
					writer.println(text);
					writer.flush();
					writer.close();
					System.out.println("Writed '" + text +"'  at • " + file);
					return 0;
				}else
					return result;
			}
			catch(IOException e){
				System.out.println("Error • " + e.toString());
				return 3;
			}
		} else {
			System.out.println("File doesnt't exists at • " + file);
			return 2;
		}
	}

	public int read (String fileName, org.omg.CORBA.StringHolder text) {
		System.out.println("\n - Reading...");
		File file = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/Files/" + fileName + ".txt");
		//File file = new File(fileName + ".txt");
		if (file.exists() && file.isFile()) {
			try{
				String txt = "";
				String line = "";
				BufferedReader buff = new BufferedReader(new FileReader(file));
				txt = buff.lines().collect(Collectors.joining());
				System.out.println(txt);
				buff.close();
				text.value = txt;
				return 0;
			} catch(IOException e) {
				System.out.println("Error • " + e.toString());
				System.out.println("Error reading file");
				return 6;
			}
		} else {
			System.out.println("File doesnt't exists at • " + file);
			return 2;
		}
	}

	public void shutdown() {
		orb.shutdown(true);
	}
}
