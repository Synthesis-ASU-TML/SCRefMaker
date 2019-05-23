package data;

import java.util.ArrayList;
import java.util.Arrays;

public class MaxObj {
	
	private String 				name;
	private String 				digest;
	private String 				description;
	private String				discussion;
	private ArrayList<MetaData> mData;
	private ArrayList<InOut>	inlets;
	private ArrayList<InOut>	outlets;
	private ArrayList<Arg>		args;
	private ArrayList<Message>	msgs;
	private ArrayList<Attr>		attrs;
	private ArrayList<SeeAlso>	also;
	private ArrayList<String>	jitterMethods;
	private ArrayList<String>	jitterAttrs;
	
	public MaxObj() {
		name = "";
		digest = name;
		description = name;
		mData = new ArrayList<MetaData>();
		inlets = new ArrayList<InOut>();
		outlets = new ArrayList<InOut>();
		args = new ArrayList<Arg>();
		msgs = new ArrayList<Message>();
		attrs = new ArrayList<Attr>();
		also = new ArrayList<SeeAlso>();
		jitterMethods = new ArrayList<String>();
		jitterAttrs = new ArrayList<String>();
		discussion = null;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDigest() {
		return digest;
	}
	
	public String getDescription() {
		return description;
	}
	
	public MetaData[] getMetadata() {
		return (mData.size() > 0) ? mData.toArray(new MetaData[mData.size()]) : null;
	}
	
	public InOut[] getInlets() {
		return (inlets.size() > 0) ? inlets.toArray(new InOut[inlets.size()]) : null;
	}
	
	public InOut[] getOutlets() {
		return (outlets.size() > 0) ? outlets.toArray(new InOut[outlets.size()]) : null;
	}
	
	public Arg[] getArguments() {
		return (args.size() > 0) ? args.toArray(new Arg[args.size()]) : null;
	}
	
	public Message[] getMessages() {
		return (msgs.size() > 0) ? msgs.toArray(new Message[msgs.size()]) : null;
	}
	
	public Attr[] getAttributes() {
		return (attrs.size() > 0) ? attrs.toArray(new Attr[attrs.size()]) : null;
	}
	
	public SeeAlso[] getSeeAlso() {
		return (also.size() > 0) ? also.toArray(new SeeAlso[also.size()]) : null;
	}
	
	public String[] getJitterMethods() {
		return (jitterMethods.size() > 0) ? jitterMethods.toArray(new String[jitterMethods.size()]) : null;
	}
	
	public String[] getJitterAttrs() {
		return (jitterAttrs.size() > 0) ? jitterAttrs.toArray(new String[jitterAttrs.size()]) : null;
	}
	
	public String getDiscussion() {
		return discussion; 
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setDigest(String s) {
		digest = s;
	}
	
	public void setDescription(String s) {
		description = s;
	}
	
	public void createInlet(String type, String digest) {
		InOut in = new InOut();
		in.type = type;
		in.digest = digest;
		
		inlets.add(in);
	}
	
	public void createOutlet(String type, String digest) {
		InOut out = new InOut();
		out.type = type;
		out.digest = digest;
		
		outlets.add(out);
	}
	
	public void createArgument(String name, String type, String digest, boolean opt) {
		Arg a = new Arg();
		a.name = name;
		a.type = type;
		a.digest = digest;
		a.optional = opt;
		args.add(a);
	}
	
	public Arg createAndReturnArg(String name, String type, boolean opt) { 
		Arg a = new Arg();
		a.name = name;
		a.type = type;
		a.optional = opt;
		
		return a;
	}
	
	public void createMessage(String name, String dig, String desc, Arg[] args) {
		Message m = new Message();
		m.name = name;
		m.digest = dig;
		m.description = desc;
		if(args != null) {
			m.args.addAll(Arrays.asList(args));
		}
		
		msgs.add(m);
	}
	
	public void createMetadata(String name, String value) {
		mData.add(new MetaData());
		MetaData m = mData.get(mData.size() - 1);
		m.name = name;
		m.value = value;
	}
	
	public void addAttribute(String name, String dig, String desc, String type, boolean get, boolean set, int size) {
		Attr a = new Attr();
		a.name = name;
		a.digest = dig;
		a.type = type;
		a.get = get;
		a.set = set;
		a.size = size;
		a.desc = desc;
		attrs.add(a);
	}
	
	public void addSeeAlso(String name) {
		SeeAlso sa = new SeeAlso();
		sa.name = name;
		also.add(sa);
	}
	
	public void addJitterMethod(String s) {
		jitterMethods.add(s);
	}
	
	public void addJitterAttr(String s) {
		jitterAttrs.add(s);
	}
	
	public class MetaData {
		String name;
		String value;
		
		public MetaData() {
			name = "Tag";
			value = "Default";
		}
		
		public String getName() {
			return name;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	public class InOut {
		String type;
		String digest;
		
		public InOut() {
			type = "matrix";
			digest = "matrices go here";
		}
		
		public String getType() {
			return type;
		}
		
		public String getDigest() {
			return digest;
		}
	}
	
	public class Arg {
		String name;
		boolean optional;
		String type;
		String digest;
		
		public Arg() {
			name = "Argument";
			optional = true;
			type = "symbol";
			digest = "A default argument";
		}
		
		public String getName() {
			return name;
		}
		
		public int getOptional() {
			return (optional) ? 1 : 0;
		}
		
		public String getType() {
			return type;
		}
		
		public String getDigest() {
			return digest;
		}
	}
	
	public class Message {
		String name;
		ArrayList<Arg> args;
		String digest;
		String description; 
		
		public Message() {
			name = "msg";
			args = new ArrayList<Arg>();
			description = "A default message";
			digest = "a default message";
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getDigest() {
			return digest;
		}
		
		public Arg[] getArgs() {
			return (args.size() > 0) ? args.toArray(new Arg[args.size()]) : null;
		}
	}
	
	public class Attr {
		String name;
		boolean get;
		boolean set;
		String type;
		String digest;
		String desc;
		int size = 1;
		
		public Attr() {
			name = "dummy";
			get = false;
			set = true;
			type = "symbol";
			digest = "A default Attribute";
			desc = "";
		}
		
		public String getName() {
			 return name;
		}
		
		public int getSet() {
			return (set) ? 1 : 0;
		}
		
		public int getGet() {
			return (get) ? 1 : 0;
		}
		
		public String getType() {
			return type;
		}
		
		public String getDigest() {
			return digest;
		}
		
		public String getDescription() {
			return desc;
		}
		
		public int getSize() {
			 return size;
		}
	}
	
	public class SeeAlso {
		String name;
		
		public SeeAlso() {
			name = "dummy";
		}
		
		public String getName() {
			return name;
		}
	}
}
