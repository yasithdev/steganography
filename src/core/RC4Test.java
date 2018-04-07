package core;

import org.junit.Assert;
import org.junit.Test;

class RC4Test {

	@Test
	public void testEncryptDecrypt() {
		byte[] key = "sample key".getBytes();
		RC4 rc4 = new RC4(key);
		byte[] toBeEncryptedBytes = "hello world".getBytes();
		byte[] encryptedBytes = rc4.encrypt(toBeEncryptedBytes);
		byte[] decryptedBytes = rc4.encrypt(encryptedBytes);
		Assert.assertArrayEquals(toBeEncryptedBytes, decryptedBytes);
	}
}