import FS.*;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;

public class StartClient {

    Scanner input = new Scanner(System.in);

    /*
    * Main
    */

    public static void main(String[] args) {
      StartClient c = new StartClient();
      try{
        ORB orb = ORB.init(args, null);
        org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        FileSystem fileSystem = (FileSystem) FileSystemHelper.narrow(ncRef.resolve_str("FileSystem"));

        c.menu(fileSystem);
      }
      catch(Exception e){
        System.out.println("Error on client : " + e.toString());
        e.printStackTrace();
      }
    }

    public void menu(FileSystem fileSystem) {

      //**  MENU **//

      String opt = "";

      while(!opt.contains("QUIT")){
        System.out.print("••••••••••••••••••\n");
        System.out.print("• OPTIONS: \n");
        System.out.print("•\n");
        System.out.print("• create \n");
        System.out.print("• delete \n");
        System.out.print("• copy \n");
        System.out.print("• write \n");
        System.out.print("• read \n");
        System.out.print("•\n");
        System.out.print("• >>> ");
        opt = input.nextLine();
        switch(opt){
          case "create":
            this.create(fileSystem);
            break;
          case "delete":
            this.delete(fileSystem);
            break;
          case "copy":
            this.copy(fileSystem);
            break;
          case "write":
            this.write(fileSystem);
            break;
          case "read":
            this.read(fileSystem);
            break;
          case "EXIT":
            input.close();
            System.exit(0);
          default:
            System.out.println("This command doesnt exist. Try Again.");
            break;
        }
      }
    }

    public void create(FileSystem fileSystem) {
      System.out.print("\n - Enter the file name: ");
  		int result = fileSystem.create(input.nextLine());
      if(result == 0)
  			System.out.println("\n • File created successfully \n");
  		else if(result == 1)
  			System.out.println("\n • File already exist \n");
      else if(result == 3)
        System.out.println("\n • Error creating file \n");
    }

    public void delete(FileSystem fileSystem) {
      System.out.print("\n - Enter the file name: ");
      int result = fileSystem.delete(input.nextLine());
      if(result == 0)
        System.out.println("\n • File deleted successfully \n");
      else if(result == 4)
        System.out.println("\n • Failed to delete the file \n");
      else if (result == 2)
        System.out.println("\n • File doesn't exist \n");
    }

    public void copy(FileSystem fileSystem) {
      System.out.print("\n - Enter the fileName and newFileName, separated by ',' : ");
      String textInput = input.nextLine();
      String[] data = textInput.split(",");
      if (data.length < 2) {
        System.out.print("\n • ERRO -  You must enter the fileName and the newFileName, separated by ','\n\n ");
        return;
      }
      int result = fileSystem.copy(data[0], data[1]);
      if(result == 0)
        System.out.println("\n • File copied successfully \n");
      else if(result == 2)
        System.out.println("\n • File doesn't exist \n");
      else if (result == 4)
        System.out.println("\n • Failed to copy the file \n");
      else if (result == 5)
        System.out.println("\n • Already exist in file with the copy suggested name \n");
    }

    public void write(FileSystem fileSystem) {
      System.out.print("\n - Enter the fileName and the text, separated by ',' : ");
      String textInput = input.nextLine();
      String[] data = textInput.split(",");
      if (data.length < 2) {
        System.out.print("\n • ERRO -  You must enter the fileName and the text, separated by ','\n\n ");
        return;
      }

      int result = fileSystem.write(data[0], data[1]);
      if(result == 0)
        System.out.println("\n • File written successfully \n");
      else if(result == 2)
        System.out.println("\n • File doesn't exist \n");
      else if (result == 3)
        System.out.println("\n • Failed to write at the file \n");
    }

    public void read(FileSystem fileSystem) {
      System.out.print("\n - Enter the file name: ");
      org.omg.CORBA.StringHolder readText = new org.omg.CORBA.StringHolder();

      int result = fileSystem.read(input.nextLine(), readText);

      if(result == 0){
        System.out.println("\n • Text: "+ readText.value +" \n");
      } else if(result == 2)
        System.out.println("\n • File doesn't exist \n");
      else if (result == 6)
        System.out.println("\n • Error trying to read the file \n");
    }
}
