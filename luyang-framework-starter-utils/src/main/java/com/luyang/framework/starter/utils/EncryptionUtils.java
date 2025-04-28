package com.luyang.framework.starter.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 加密工具类，提供简单的加密和解密功能。
 * @author wangjixin
 */
public final class EncryptionUtils {

	private static final String ALGORITHM = "AES";

	private EncryptionUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	/**
	 * 生成 AES 密钥。
	 * @return 生成的密钥的 Base64 编码字符串
	 * @throws Exception 生成密钥时可能抛出的异常
	 */
	public static String generateKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		keyGenerator.init(128, new SecureRandom());
		SecretKey secretKey = keyGenerator.generateKey();
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
	}

	/**
	 * 使用 AES 算法加密字符串。
	 * @param plainText 要加密的明文
	 * @param key 加密密钥的 Base64 编码字符串
	 * @return 加密后的 Base64 编码字符串
	 * @throws Exception 加密过程中可能抛出的异常
	 */
	public static String encrypt(String plainText, String key) throws Exception {
		SecretKey secretKey = getSecretKey(key);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * 使用 AES 算法解密字符串。
	 * @param encryptedText 加密后的 Base64 编码字符串
	 * @param key 解密密钥的 Base64 编码字符串
	 * @return 解密后的明文
	 * @throws Exception 解密过程中可能抛出的异常
	 */
	public static String decrypt(String encryptedText, String key) throws Exception {
		SecretKey secretKey = getSecretKey(key);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	private static SecretKey getSecretKey(String key) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(key);
		return new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
	}
}
