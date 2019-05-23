package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.MaxObj;
import data.MaxObj.*;
import data.MaxObj.InOut;
import data.MaxObj.MetaData;



public class FileWriter {

public static final String preamble_0 = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n";	
public static final String preamble_1 = "<?xml-stylesheet href=\"./c74ref.xsl\" type=\"text/xsl\"?>\n";
	
	public static void createXMLFiles(File dir, MaxObj[] objs) {
		
		//avoid trying to work on empty arrays
		if(objs.length == 0)
			return;
		
		String path = dir.getAbsolutePath() + System.getProperty("file.separator");
		
		for(int i = 0; i < objs.length; i++) {
			MaxObj obj = objs[i];
			ArrayList<String> contents = new ArrayList<String>();
			//Add necessary preamble info for the XML Style data
			contents.add(preamble_0);
			contents.add(preamble_1);
			contents.add("\n\n");
			
			//begin object data with its name
			contents.add("<c74object name=\"" + obj.getName() + "\">\n");
			//then the digest (or short description)
			contents.add("\t<digest>\n\t\t" + obj.getDigest() + "\n\t</digest>\n");
			//then the full description, which could be the same as the digest
			contents.add("\t<description>\n\t\t" + obj.getDescription() + "\n\t</description>\n");
			
			//now we move on to the metadata section
			MetaData md[] = obj.getMetadata();
			
			if(md != null) {
				contents.add("\n<!--METADATA-->\n\t<metadatalist>\n");
				
				for(int j = 0; j < md.length; j++) {
					MetaData m = md[j];
					contents.add("\t\t<metadata name=\"" + m.getName() + "\">" + m.getValue() + "</metadata>\n");
				}
				contents.add("\t</metadatalist>\n");
			}
			
			//next is inlets
			
			InOut ins[] = obj.getInlets();
			
			if(ins != null) {
				contents.add("\n<!--INLETS-->\n\t<inletlist>\n");
				
				for(int j = 0; j < ins.length; j++) {
					InOut in = ins[j];
					contents.add("\t\t<inlet id=\"" + j + "\" type=\"" + in.getType() + "\">\n\t\t\t<digest>" + in.getDigest() + "</digest>\n\t\t</inlet>\n");
				}
				
				contents.add("\t</inletlist>\n");
			}
			
			//outlets
			
			InOut outs[] = obj.getOutlets();
			
			if(outs != null) {
				contents.add("\n<!--OUTLETS-->\n\t<outletlist>\n");
				
				for(int j = 0; j < outs.length; j++) {
					InOut out = outs[j];
					contents.add("\t\t<outlet id=\"" + j + "\" type=\"" + out.getType() + "\">\n\t\t\t<digest>" + out.getDigest() + "</digest>\n\t\t</outlet>\n");
				}
				
				contents.add("\t</outletlist>\n");
			}
			
			//arguments
			
			Arg args[] = obj.getArguments();
			
			if(args != null) {
				contents.add("\n<!--ARGUMENTS-->\n\t<objarglist>\n");
				
				for(int j = 0; j < args.length; j++) {
					Arg a = args[j];
					contents.add("\t\t<objarg name=\"" + a.getName() + "\" optional=\"" + a.getOptional() + "\" type=\"" + a.getType() + "\">\n\t\t\t<digest>" + a.getDigest() + "</digest>\n\t\t</objarg>\n");
				}
				
				contents.add("\t</objarglist>\n");
			}
			
			Message msgs[] = obj.getMessages();
			
			if(msgs != null) {
				contents.add("\n<!--MESSAGES-->\n\t<methodlist>\n");
				
				for(int j = 0; j < msgs.length; j++) {
					Message m = msgs[j];
					contents.add("\t\t<method name=\"" + m.getName() + "\">\n");
					Arg margs[] = m.getArgs();
					if(margs != null) {
						contents.add("\t\t\t<arglist>\n");
						for(int k = 0; k < margs.length; k++) {
							Arg a = margs[k];
							contents.add("\t\t\t\t<arg name=\"" + a.getName() + "\" optional=\"" + a.getOptional() + "\" type=\"" + a.getType() + "\" />\n");
						}
						contents.add("\t\t\t</arglist>\n");
					}
					contents.add("\t\t\t<digest>\n\t\t\t\t" + m.getDigest() + "\n\t\t\t</digest>\n");
					contents.add("\t\t\t<description>\n\t\t\t\t" + m.getDescription() + "\n\t\t\t</description>\n");
					contents.add("\t\t</method>\n");
				}
				contents.add("\t</methodlist>\n");
			}
			
			//jitter methods
			
			String jitterM[] = obj.getJitterMethods();
			
			if(jitterM != null) {
				contents.add("\t<jittermethodlist>\n");
				for(int j = 0; j < jitterM.length; j++) {
					contents.add("\t\t<jittermethod name=\"" + jitterM[j] + "\" />\n");
				}
				contents.add("\t</jittermethodlist>\n");
			}
			
			
			//attributes
			Attr attrs[] = obj.getAttributes();
			
			if(attrs != null) {
				contents.add("\n<!--ATTRIBUTES-->\n");
				contents.add("\t<attributelist>\n");
				
				for(int j = 0; j < attrs.length; j++) {
					Attr a = attrs[j];
					contents.add("\t\t<attribute name=\"" + a.getName() + "\" get=\"" + a.getGet() + "\" set=\"" + a.getSet() + "\" type=\"" + a.getType() + "\" size=\"" + a.getSize() + "\" >\n");
					contents.add("\t\t\t<digest>" + a.getDigest() + "</digest>\n");
					contents.add("\t\t\t<description>" + ((!a.getDescription().equals("")) ? a.getDescription() : a.getDigest()) + "</description>\n");
					contents.add("\t\t</attribute>\n");
				}
				
				contents.add("\t</attributelist>\n");
			}
			
			//jitter attributes
			
			String jitterAttrs[] = obj.getJitterAttrs();
			
			if(jitterAttrs != null) {
				contents.add("\t<jitterattributelist>\n");
				for(int j = 0; j < jitterAttrs.length; j++){
					contents.add("\t\t<jitterattribute name=\"" + jitterAttrs[j] + "\" />\n");
				}
			}
			
			SeeAlso sas[] = obj.getSeeAlso();
			
			if(sas != null) {
				
				contents.add("\n<!--SEEALSO-->\n");
				contents.add("\t<seealsolist>\n");
				
				for(int j = 0; j < sas.length; j++) {
					SeeAlso sa = sas[j];
					contents.add("\t\t<seealso name=\"" + sa.getName() + "\"/>\n");
				}
				
				contents.add("\t</seealsolist>\n");
			}
			
			contents.add("</c74object>");
			
			String file_path = path + obj.getName() + ".maxref.xml";
			
			System.out.println("Writing file: " + file_path);
			
			boolean result = writeDataToFile(new File(file_path), contents.toArray(new String[contents.size()]));
		}
	}
	
	private static boolean writeDataToFile(File f, String[] data) {
		try {
			PrintWriter writer = new PrintWriter(f);
			
			for(int i = 0; i < data.length; i++) {
				writer.write(data[i]);
			}
			
			writer.close();
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Creating File: " + f.getAbsolutePath());
		}
		return false;
	}
}
