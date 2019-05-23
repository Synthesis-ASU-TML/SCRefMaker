package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import data.MaxObj;
import data.MaxObj.*;

public class FileLoader {

	public static final String[] METATAGS = {"author", "tag", "version", "year"};
	
	public static final String NAME = "name";
	public static final String digest = "digest";
	public static final String[] desc = {"description", "desc"};
	
	public static final String inlet = "inlet";
	public static final String in_type = "type";
	public static final String in_dig = digest;
	public static final String[] in_desc = desc;

	public static final String outlet = "outlet";
	public static final String out_type = "type";
	public static final String out_dig = digest;
	public static final String[] out_desc = desc;
	
	public static final String[] arg = {"arg", "argument"};
	public static final String arg_name = "name";
	public static final String optional[] = {"opt", "optional"};
	public static final String[] opt_vals = {"1", "true", "yes", "t", "y"};
	public static final String arg_type = "type";
	public static final String arg_dig = "digest";
	
	public static final String[] msg = {"message", "msg", "msgs"};
	public static final String m_name = "name";
	public static final String[] m_desc = desc;
	public static final String m_dig = "digest";
	public static final String[] m_arg = {"arg", "argument"};
	public static final String m_arg_name = "name";
	public static final String m_arg_type = "type";
	public static final String[] m_arg_opt = {"opt", "optional"};
	public static final String[] m_arg_opt_vals = {"1", "true", "yes", "t", "y"};
	public static final String[] m_arg_end = {"", "end_arg", "end", "endarg"};
	
	public static final String[] attr = {"attr", "attribute"};
	public static final String attr_name = "name";
	public static final String attr_dig = "digest";
	public static final String[] attr_desc = desc;
	public static final String attr_get = "get";
	public static final String attr_set = "set";
	public static final String attr_type = "type";
	public static final String attr_size = "size";
	public static final String[] attr_gs_vals = {"1", "true", "yes", "t", "y"};
	
	public static final String[] see_also = {"seealso", "also", "see"};
	
	public static final String[] jitterMethod = {"jittermethod", "jitmethod", "jittermessage", "jitmessage"};
	
	public static final String[] jitterAttr = {"jitterattribute", "jitterAttr", "jitattribute", "jitattr"};
	
	public static ArrayList<MaxObj> loadXMLData(File xml) throws FileNotFoundException {
		
		ArrayList<MaxObj> objs = new ArrayList<MaxObj>();
		
		Scanner scan = new Scanner(xml);
		
		System.out.println("Starting loading file data");
		
		while(scan.hasNextLine()) {
			System.out.println("Loading New Object Data");
			String data = scan.nextLine();
			String parts[] = data.split("\t");
			MaxObj obj = new MaxObj();
			objs.add(obj);
			for(int i = 0; i < parts.length; i++) {
				String part = parts[i];
				
				if(part.equalsIgnoreCase(NAME)) { //if the data point is the name of the object
					i++;
					obj.setName(parts[i]);
					System.out.println("Found name: " + obj.getName());
				} else if(matchesMetadata(part) != null) { //if the data point is a metadata portion
					i++;
					obj.createMetadata(matchesMetadata(part), parts[i]);
				} else if(part.equalsIgnoreCase(digest)) { //setting the object digest
					i++;
					obj.setDigest(checkForReservedCharacters(parts[i]));
				} else if(isAMatch(desc, part)){ //setting the object description
					i++;
					obj.setDescription(parts[i]);
				}else if(part.equalsIgnoreCase(inlet)) { //creating inlets
					System.out.println("Loading Inlet Data");
					String type = "symbol";
					String description = "default";
					String digest = "default";
					i++;
					while(!parts[i].equals("") && i < parts.length){

						if(parts[i].equalsIgnoreCase(in_type)) {
							i++;
							type = checkForReservedCharacters(parts[i]);
						}
						
						if(isAMatch(desc,parts[i])) {
							i++;
							description = checkForReservedCharacters(parts[i]);
						}
						
						if(parts[i].equalsIgnoreCase(in_dig)){
							i++;
							digest = checkForReservedCharacters(parts[i]);
						}
						
						i++;
					}
					
					obj.createInlet(type, digest);
				}else if(part.equalsIgnoreCase(outlet)) { //creating outlets
					System.out.println("Loading Outlet Data");
					String type = "symbol";
					String description = "default";
					String digest = "default";
					i++;
					while(!parts[i].equals("") && i < parts.length){

						if(parts[i].equalsIgnoreCase(out_type)) {
							i++;
							type = checkForReservedCharacters(parts[i]);
						}
						
						if(isAMatch(desc,parts[i])) {
							i++;
							description = checkForReservedCharacters(parts[i]);
						}
						
						if(parts[i].equalsIgnoreCase(out_dig)){
							i++;
							digest = checkForReservedCharacters(parts[i]);
						}
						
						i++;
					}
					
					obj.createOutlet(type, digest);
				}else if(matchesArgument(part)) { //create arguments
					System.out.println("Loading Argument Data");
					i++;
					String name = "dummy";
					boolean opt = true;
					String type = "Symbol";
					String digest = "Default";
					while(!parts[i].equals("") && i < parts.length) {
						if(parts[i].equalsIgnoreCase(arg_name)) {
							i++;
							name = checkForReservedCharacters(parts[i]);
						}
						if(parts[i].equalsIgnoreCase(arg_dig)) {
							i++;
							digest = checkForReservedCharacters(parts[i]);
						}
						if(parts[i].equalsIgnoreCase(arg_type)) {
							i++;
							type = checkForReservedCharacters(parts[i]);
						}
						if(isAMatch(optional, parts[i])) {
							i++;
							opt = isAMatch(opt_vals, parts[i]);
						}
						i++;
					}
					
					obj.createArgument(name, type, digest, opt);
				} else if(isAMatch(msg, part)) {
					System.out.println("Loading Message Data");
					i++;
					
					String msg_name = "dummy";
					String msg_digest = "Default Message";
					String msg_description = "A Message which has no description";
					
					ArrayList<Arg> args = new ArrayList<Arg>();
					
					while(!parts[i].equalsIgnoreCase("") && i < parts.length) {
						if(isAMatch(m_arg, parts[i])) {
							System.out.println("Loading Message Argument Data");
							i++;
							String a_name = "dummy";
							String a_type = "Symbol";
							boolean a_opt = true;
							while(!isAMatch(m_arg_end, parts[i]) && i < parts.length) {
								if(parts[i].equalsIgnoreCase(m_arg_name)) {
									i++;
									a_name = checkForReservedCharacters(parts[i]);
								}
								
								if(parts[i].equalsIgnoreCase(m_arg_type)) {
									i++;
									a_type = checkForReservedCharacters(parts[i]);
								}
								
								if(isAMatch(m_arg_opt, parts[i])) {
									i++;
									a_opt = isAMatch(m_arg_opt_vals, parts[i]);
								}
								i++;
							}
							
							Arg a = obj.createAndReturnArg(a_name, a_type, a_opt);
							args.add(a);
						}
						
						if(parts[i].equalsIgnoreCase(m_name)) {
							i++;
							msg_name = checkForReservedCharacters(parts[i]);
						}
						
						if(parts[i].equalsIgnoreCase(m_dig)) {
							i++;
							msg_digest = checkForReservedCharacters(parts[i]);
						}
						
						if(isAMatch(desc,parts[i])) {
							i++;
							msg_description = checkForReservedCharacters(parts[i]);
						}
						
						if(!parts[i].equals(""))
							i++;
					}
					
					obj.createMessage(msg_name, msg_digest, msg_description, ((args.size() > 0) ? args.toArray(new Arg[args.size()]) : null));	
				} else if(isAMatch(attr, part)) {
					System.out.println("Loading Attribute Data");
					String a_name = "dummy";
					String a_desc = "An attribute with no description";
					String a_dig = "Default Attr";
					String a_type = "Symbol";
					boolean a_get = false;
					boolean a_set = true;
					int a_size = 1;
					
					i++;
					
					while(i < parts.length && !parts[i].equals("")) {
						if(parts[i].equalsIgnoreCase(attr_name)) {
							i++;
							a_name = checkForReservedCharacters(parts[i]);
						}
						if(parts[i].equalsIgnoreCase(attr_dig)) {
							i++;
							a_dig = checkForReservedCharacters(parts[i]);
						}
						if(isAMatch(desc,parts[i])) {
							i++;
							a_desc = checkForReservedCharacters(parts[i]);
						}
						if(parts[i].equalsIgnoreCase(attr_type)) {
							i++;
							a_type = checkForReservedCharacters(parts[i]);
						}
						if(parts[i].equalsIgnoreCase(attr_get)) {
							i++;
							a_get = isAMatch(attr_gs_vals, parts[i]);
						}
						if(parts[i].equalsIgnoreCase(attr_set)) {
							i++;
							a_set = isAMatch(attr_gs_vals, parts[i]);
						}
						if(parts[i].equalsIgnoreCase(attr_size)) {
							i++;
							
							try{
								int temp = Integer.parseInt(parts[i]);
								a_size = temp;
							} catch(Exception e) {System.err.println("Error in parsing data for object: " + obj.getName() + "\nNon-Numeric value: " + parts[i] + " entered for size of attribute " + a_name);}
						}
						
						i++;
					}
					
					obj.addAttribute(a_name, a_dig, a_desc, a_type, a_get, a_set, a_size);
				} else if(isAMatch(see_also, part)) {
					System.out.println("Loading SeeAlso Data");
					i++;
					String also = checkForReservedCharacters(parts[i]);
					obj.addSeeAlso(also);
				} else if(isAMatch(jitterMethod, part)) {
					i++;
					while(!parts[i].equals("") && i < parts.length) {
						obj.addJitterMethod(parts[i]);
						i++;
					}
				} else if(isAMatch(jitterAttr, part)) {
					i++;
					while(!parts[i].equals("") && i < parts.length) {
						obj.addJitterAttr(parts[i]);
						i++;
					}
				}
				
				System.out.println("End of Loading For Loop");
			}
			
			System.out.println(scan.hasNextLine());
		}
		
		scan.close();
		
		System.out.println("Returning from Data loading");
		
		return objs.size() > 0 ? objs : null;
	}
	
	private static String matchesMetadata(String s) {
		for(int i = 0; i < METATAGS.length; i++) {
			if(s.equalsIgnoreCase(METATAGS[i]))
				return METATAGS[i];
		}
		
		return null;
	}
	
	private static boolean matchesArgument(String s) {
		
		for(int i = 0; i < arg.length; i++) {
			if(s.equalsIgnoreCase(arg[i]))
				return true;
		}
		
		return false;
	}
	
	private static boolean isAMatch(String[] c, String s) {
		
		for(int i = 0; i < c.length; i++) {
			if(s.equalsIgnoreCase(c[i]))
				return true;
		}
		
		return false;
	}
	
	private static String checkForReservedCharacters(String s) {
		s = s.replace("&", "&amp;");
		s = s.replaceAll("<", "&lt;");
		s = s.replace(">", "&gt;");
		s = s.replace("\"", "&quot;");
		s = s.replace("\'", "&apos;");
		return s;
	}
	
}
