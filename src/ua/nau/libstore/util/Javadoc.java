package ua.nau.libstore.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Class is used for generation javadoc documentation
 * 
 * @author Geniy
 * 
 */
public class Javadoc {

	/**
	 * Generate javadoc documentation in folder path. New folder named doc will
	 * be created
	 * 
	 * @param folderPath
	 *            path to java files
	 * @return true - generation complete successfully, docs wasn't generated
	 */
	public static boolean generateJavadoc(String folderPath) {
		folderPath = folderPath.replace("/", "\\");
		
		Runtime runtime = Runtime.getRuntime();

		try {
			new ProcessBuilder("cmd.exe", "/c", "dir", "/s", "/b", 
					folderPath + "*.java", ">c:\\file.lst")
					.redirectErrorStream(true).start();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			runtime.exec("javadoc -d "+ folderPath +"doc/ @c:\\file.lst");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new ProcessBuilder("cmd.exe", "/c", "del", "c:\\file.lst").redirectErrorStream(true).start();
		
			new Thread(new RunIndexJavadoc(folderPath + "doc\\index.html")).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		return true;
	}

	
}

class RunIndexJavadoc implements Runnable{
	
	//Thread runner;
	private String path;
	
	public RunIndexJavadoc(String path) {
		this.path = path;
		//runner = new Thread();
		//runner.start();
	}
	
	@Override
	public void run() {		
		int count = 60;
		
		for(int i = 0; i < count; i++){
			File file = new File(path);	
			if(file.canExecute()){
				try {
					Desktop.getDesktop().open(file);
					i = count;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}