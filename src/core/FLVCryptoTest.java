package core;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

public class FLVCryptoTest {

	@Test
	public void test() {
		Path originalFilePath = Paths.get("/Users/yasith/bear.flv");
		Path stegaFilePath = Paths.get("/Users/yasith/output.flv");
		FLVCrypto flv = new FLVCrypto();
		
		// Embedding
		try{
		byte[] originalMessageBytes = "Hello World".getBytes();
		byte[] inputBytes = IO.readFileBytes(originalFilePath);
		byte[] embeddedBytes = flv.embed(inputBytes, originalMessageBytes);
		IO.writeFileBytes(stegaFilePath, embeddedBytes);
				
		// Retrieving
		inputBytes = IO.readFileBytes(stegaFilePath);
		byte[] retrievedMessageBytes = flv.extract(inputBytes);
		
		System.out.println(originalMessageBytes.length + "|" + retrievedMessageBytes.length);
		assertArrayEquals(Arrays.copyOfRange(originalMessageBytes, 0, retrievedMessageBytes.length), retrievedMessageBytes);
		} catch (Exception e){
			fail("Exception occured : " + e.getMessage());
		}
	}
	
}
