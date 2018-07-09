package ro.academyplus.swingy;

import ro.academyplus.swingy.storage.HsqlDb;
import ro.academyplus.swingy.view.consoleView.ConsoleV;
//org.eclipse.m2e.MAVEN2_CLASSPATH_CONTAINER'

//java -jar .m2/repository/org/hsqldb/hsqldb/2.3.5/hsqldb-2.3.5.jar
public class Main {
	public static void main(String[] args) {
//		HsqlDb.getHsql().createDb();
		try {
			switch(args[0]) {
				case "console":
					ConsoleV.go();
				break;
				case "gui":
	
				break;
				default:
					System.out.println("Invalid input: console/gui. Retry\n");
			}
			HsqlDb.getHsql().stop();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid argument: console/gui. Retry\n");
			e.printStackTrace();
		}
	}	
}