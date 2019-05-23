package main;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import data.MaxObj;
import io.FileLoader;
import io.FileWriter;

public class RefMaker {

	public static int dir_choice = 0;
	public static File out_dir = null;
	
	public static void main(String[] args) throws FileNotFoundException, InvocationTargetException, InterruptedException {
		
		JFileChooser jfc = new JFileChooser();
		
		int choice = jfc.showOpenDialog(null);
		
		if(choice == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			
			System.out.println("Moving to data loading");
			ArrayList<MaxObj> objs = FileLoader.loadXMLData(f);
			

			
			if(objs != null) {		
				
				EventQueue.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						JFileChooser dir_chooser = new JFileChooser();
				
						dir_chooser.setDialogTitle("Select XML Save Location");
						dir_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
						dir_choice = dir_chooser.showOpenDialog(null);
						out_dir = (dir_choice == JFileChooser.APPROVE_OPTION) ? dir_chooser.getSelectedFile() : null;
					}
				});
				
				if(out_dir != null) {
					System.out.println("Moving to Data Writing");
					FileWriter.createXMLFiles(out_dir, objs.toArray(new MaxObj[objs.size()]));
				}
				
			} else {
				System.out.println("No Data loaded from file");
			}
		}
		
		System.out.println("All Done");
	}

}
