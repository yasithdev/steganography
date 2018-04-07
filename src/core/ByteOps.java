package core;

class ByteOps {
	
	// Method to set LSB to new value
	public static byte setLSB(byte input, int value) throws Exception {
		if(value != 0 && value != 1) throw new Exception("Only integers allowed are 0 and 1");
		if(value == 1) return (byte)(input | 0x01);
		else return (byte)((input >>> 1) << 1);
	}
	
	public static int getLSB(byte input) {
		return (((input << 7) >>> 7) & 0x01);
	}
	
	public static int getBit(byte input, int pos) {
		return (((input << (7 - pos)) & 0xFF) >>> 7);
	}
	
	public static byte toByte(String s) throws Exception{
		char[] c = s.toCharArray();
		if(s.length() != 8) throw new Exception("Need exactly 8 bits to get a byte");
		int b = 
				((c[0] - '0') << 7) | 
				((c[1] - '0') << 6) |
				((c[2] - '0') << 5) |
				((c[3] - '0') << 4) |
				((c[4] - '0') << 3) |
				((c[5] - '0') << 2) | 
				((c[6] - '0') << 1) | 
				((c[7] - '0') << 0);
		return (byte)(b & 0xFF);
	}
	
	public static String toString(byte b) throws Exception{
		String r = new String();
		for (int i = 0; i < 8; i++) {
			r += (int)(((b << i) & 0xFF) >>> 7);
		}
		return r;
	}

}
