package core;

import java.util.Arrays;

public class FLVCrypto {
	
	private static int EOF_SIZE = 20;
	
	// Embed content into FLV bytes.
	public byte[] embed(byte[] f, byte[] messageBytes) throws Exception{
		System.out.println("=== Embedding Process Started ===");
		// Message as a string of bits
		String message = new String();
		for (int i = 0; i < messageBytes.length; i++) {
			byte b = messageBytes[i];
			message += ByteOps.toString(b);
		}
		
		// File format check (FLV - Version 1
		if(!((f[0] & 0xFF) == 0x46 && (f[1] & 0xFF) == 0x4c && (f[2] & 0xFF) == 0x56 && (f[3] & 0xFF) == 0x01)){
			throw new Exception("Not a FLV file");
		}
		
		// File flags check
		int typeflags = (f[4] & 0x05);	// Only keep 1st and 3rd bits from right
		boolean audio = ((typeflags >>> 2) & 0x01) != 0; // third bit from right (1 - Contains audio Tags)
		boolean video = (typeflags & 0x01) != 0; // first bit from right (1 - Contains Video tags)
		if(audio) System.out.println("Audio tags exist");
		if(video) System.out.println("Video tags exist");
		if(!video) throw new Exception("No video to embed the message in");
		
		// f[5] thru f[8] contains how long the header is. 
		// This value is a fixed '0000 0009' in FLV-1 so not double checked here
		
		/* FLV BODY FORMAT 
		 * 	- 4 bytes 		- Size of previous tag
		 * 	- (n1) bytes 	- Tag 1,
		 * 	- 4 bytes		- Size of previous tag (In this case n1)
		 * 	- (n2) bytes	- Tag 2,
		 * 	- 4 bytes		- Size of previous tag (In this case n2)
		 * 	- .......................
		 * 	- 4 bytes		- Size of last tag,.
		 */
		
		/* Tag format 
		 * 	- 1		 	- Tag Type [8 - audio, 9 - video, 18 - script data]
		 * 	- 2,3,4		- Length of data field (In bytes) [n]
		 * 	- 5,6,7,8	- Time stamp in milliseconds relative to first time stamp  (which is 0)
		 * 	- 9,10,11	- Always 0 (Stream Id)
		 * 	- 12 thru (n + 11) contains data
		 */

		// tags f[9] thru f[12] is all zeros [4 bytes of data kept to store previous tag size]
		
		// LOGIC - Check if the tag is a video tag, and if so update the LSB of the DATA 
		// with each byte of the message until whole message is appended. Then mark the end of message
		// ****with EOF_SIZE consecutive 0 bytes**** (This is for convenience. Can change later if needed)
		
		int p = 13;// start of tag 1 (Contains tag type)
		int m = 0; // pointer to message byte position
		int e = 0; // pointer to EOF string position
		
		while(p < (f.length - 13)){
			boolean isVideoTag = false;
			
			if((f[p] & 0xFF) == 8){
//				System.out.println("Inside a audio tag");
			}
			else if((f[p] & 0xFF) == 9){
//				System.out.println("Inside a video tag");
				isVideoTag = true;
			}
			else if((f[p] & 0xFF) == 18){
//				System.out.println("Inside a script tag");
			} 
			else {
//				System.out.println("Unidentified tag : " + (f[p] & 0xFF));
			}
			// ADVANCE TO NEXT TAG
			// (p+1, p+2, p+3) collectively stores data size. Get it and increase p by data size + 11
			int i = ( (f[p+1] & 0xFF) << 16) + ((f[p+2] & 0xFF) << 8) + (f[p+3] & 0xFF);
//			System.out.println("Data field size - " + i);
			
			// If data tag exists, q is on LSB of Data Tag. Else its on end of Stream ID
			int q = (p + i + 10);
			if(isVideoTag){
				// Do the magic here ;) [Setting LSB]
				if (m < message.length()) {
					int c = message.charAt(m) - '0';
					f[q] = ByteOps.setLSB(f[q], c);
					System.out.print(c);
					m++;
				} else if (e < EOF_SIZE) {
					// After the message is embedded, 
					// Make the LSB of EOF_SIZE#of bytes to 0 to mark end of message
					f[q] = ByteOps.setLSB(f[q], 0);
					System.out.print(0);
					e++;
				} else {
					// Once message is completely inserted, break the loop
					System.out.println("E before Loop exited : " + e);
					break;
				}
			}
			q++;
			
			// Now q is on the first byte of the 4 bytes that store the size of previous tag
			int r = ((f[q] & 0xFF) << 24) + ((f[q+1] & 0xFF) << 16) + ((f[q+2] & 0xFF) << 8) + (f[q+3] & 0xFF);
//			System.out.println("Previous tag size - " + r);
			if(q - p == r) {
//				System.out.println("Previous tag size verified. Good to go");
				p = q + 4;
			} else {
				throw new Exception("Tag size mismatch. Check your logic");
			}
		}
		System.out.println("All tags iterated");
		System.out.println("=== Embedding Process Completed ===");
		// After all modifications, return the byte array with embedded message
		return f;
	}
	
	// Return unsigned integer value of byte
	public byte[] extract(byte[] f) throws Exception{
		System.out.println("=== Extraction Process Started ===");
		// File format check (FLV - Version 1
		if(!((f[0] & 0xFF) == 0x46 && (f[1] & 0xFF) == 0x4c && (f[2] & 0xFF) == 0x56 && (f[3] & 0xFF) == 0x01)){
			throw new Exception("Not a FLV file");
		}
		
		// File flags check
		int typeflags = (f[4] & 0x05);	// Only keep 1st and 3rd bits from right
		boolean audio = ((typeflags >>> 2) & 0x01) != 0; // third bit from right (1 - Contains audio Tags)
		boolean video = (typeflags & 0x01) != 0; // first bit from right (1 - Contains Video tags)
		if(audio) System.out.println("Audio tags exist");
		if(video) System.out.println("Video tags exist");
		if(!video) throw new Exception("No video to embed the message in");
		
		// f[5] thru f[8] contains how long the header is. 
		// This value is a fixed '0000 0009' in FLV-1 so not double checked here
		
		/* FLV BODY FORMAT 
		 * 	- 4 bytes 		- Size of previous tag
		 * 	- (n1) bytes 	- Tag 1,
		 * 	- 4 bytes		- Size of previous tag (In this case n1)
		 * 	- (n2) bytes	- Tag 2,
		 * 	- 4 bytes		- Size of previous tag (In this case n2)
		 * 	- .......................
		 * 	- 4 bytes		- Size of last tag,.
		 */
		
		/* Tag format 
		 * 	- 1		 	- Tag Type [8 - audio, 9 - video, 18 - script data]
		 * 	- 2,3,4		- Length of data field (In bytes) [n]
		 * 	- 5,6,7,8	- Time stamp in milliseconds relative to first time stamp  (which is 0)
		 * 	- 9,10,11	- Always 0 (Stream Id)
		 * 	- 12 thru (n + 11) contains data
		 */

		// tags f[9] thru f[12] is all zeros [4 bytes of data kept to store previous tag size]
		
		// LOGIC - Check if the tag is a video tag, and if so update the LSB of the DATA 
		// with each byte of the message until whole message is appended. Then mark the end of message
		// ****with EOF_SIZE consecutive 0 bytes**** (This is for convenience. Can change later if needed)
		
		int p = 13; // start of tag 1 (Contains tag type)
		
		// Array for storing the message
		String message = new String();
		int e = 0;
		
		while(p < f.length){
			boolean isVideoTag = false;
			
			if((f[p] & 0xFF) == 8){
//				System.out.println("Inside a audio tag");
			}
			else if((f[p] & 0xFF) == 9){
//				System.out.println("Inside a video tag");
				isVideoTag = true;
			}
			else if((f[p] & 0xFF) == 18){
//				System.out.println("Inside a script tag");
			} 
			else {
//				System.out.println("Unidentified tag : " + (f[p] & 0xFF));
			}
			// ADVANCE TO NEXT TAG
			// (p+1, p+2, p+3) collectively stores data size. Get it and increase p by data size + 11
			int i = ( (f[p+1] & 0xFF) << 16) + ((f[p+2] & 0xFF) << 8) + (f[p+3] & 0xFF);
//			System.out.println("Data field size - " + i);
			
			// If data tag exists, q is on LSB of Data Tag. Else its on end of Stream ID
			int q = (p + i + 10);
					
			if(isVideoTag){
				// Do the magic here ;) [Extracting LSB]
				int b = ByteOps.getLSB(f[q]);
				message += b;
				System.out.print(b);
				// Count up e when EOF bytes found, or reset if another byte found
				if(b == 0){
					e = e + 1;
				} else {
					e = 0;
				}
				// If e reaches EOF_SIZE#, Break the loop
				if(e == EOF_SIZE) {
					System.out.println("EOF Reached. Breaking loop. ");
					break;
				}
			}
			q++;
			
			// Now q is on the first byte of the 4 bytes that store the size of previous tag
			int r = ((f[q] & 0xFF) << 24) + ((f[q+1] & 0xFF) << 16) + ((f[q+2] & 0xFF) << 8) + (f[q+3] & 0xFF);
//			System.out.println("Previous tag size - " + r);
			if(q - p == r) {
//				System.out.println("Previous tag size verified. Good to go");
				p = q + 4;
			} else {
				throw new Exception("Tag size mismatch. Check your logic");
			}
		}
//		System.out.println("String length: " + message.length() + "| String: " + message);
		System.out.println(message.length());
		int len = (message.length() - e) / 8;
		boolean k = false;
		
		byte[] result = new byte[len + 1];
		for (int i = 0; i < len + 1; i++) {
			int d = 0;
			if(8 * (i+1) > message.length()){
				d = 8 * (i+1) - message.length();
			}
			String byteString = message.substring(8 * i, 8 * (i+1) - d);
			if(d > 0){
				for (int j = 0; j < d; j++) {
					byteString += "0";
				}
			}
			byte b = ByteOps.toByte(byteString);
			if( i != len){
				result[i] = b;
			}
			else if(i == len && b != 0){
				result[i] = b;
				k = true;
			}
		}
		System.out.println("=== Extraction Process Completed ===");
		if(k){
			return result;
		} else {
			return Arrays.copyOfRange(result, 0, result.length - 1);
		}
	}
}