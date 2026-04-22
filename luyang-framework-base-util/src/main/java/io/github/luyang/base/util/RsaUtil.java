package io.github.luyang.base.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * RSA 非对称加密工具类
 * 默认使用 RSA/ECB/OAEPWithSHA-256AndMGF1Padding 填充方式，提高安全性
 *
 * @author yang.lu
 */
public final class RsaUtil {

	/** 算法名称 */
	private static final String ALGORITHM = "RSA";

	/** 加密填充方式 */
	private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

	/** 默认密钥长度 */
	private static final int DEFAULT_KEY_SIZE = 2048;

	private RsaUtil() {
		// 工具类禁止实例化
	}

	/**
	 * 生成 RSA 密钥对（默认 2048 位）
	 *
	 * @return KeyPair 密钥对对象
	 * @author yang.lu
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		return generateKeyPair(DEFAULT_KEY_SIZE);
	}

	/**
	 * 生成指定长度的 RSA 密钥对
	 *
	 * @param keySize 密钥长度（建议 2048 及以上）
	 * @return KeyPair 密钥对
	 * @author yang.lu
	 */
	public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
		generator.initialize(keySize);
		return generator.generateKeyPair();
	}

	/**
	 * 使用公钥加密（结果为 Base64 编码字符串）
	 *
	 * @param data      原始数据
	 * @param publicKey 公钥字符串 (Base64)
	 * @return 加密后的 Base64 字符串
	 * @author yang.lu
	 */
	public static String encrypt(String data, String publicKey) throws Exception {
		Objects.requireNonNull(data, "Data must not be null");
		byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedBytes = encrypt(dataBytes, getPublicKey(publicKey));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	/**
	 * 使用私钥解密
	 *
	 * @param base64Data 加密后的 Base64 数据
	 * @param privateKey 私钥字符串 (Base64)
	 * @return 解密后的原始字符串
	 * @author yang.lu
	 */
	public static String decrypt(String base64Data, String privateKey) throws Exception {
		Objects.requireNonNull(base64Data, "Encoded data must not be null");
		byte[] encryptedBytes = Base64.getDecoder().decode(base64Data);
		byte[] decryptedBytes = decrypt(encryptedBytes, getPrivateKey(privateKey));
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	/**
	 * 核心加密逻辑
	 *
	 * @author yang.lu
	 */
	public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 核心解密逻辑
	 *
	 * @author yang.lu
	 */
	public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 将 Base64 编码的公钥字符串转为 PublicKey 对象
	 *
	 * @author yang.lu
	 */
	public static PublicKey getPublicKey(String base64PublicKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		return keyFactory.generatePublic(spec);
	}

	/**
	 * 将 Base64 编码的私钥字符串转为 PrivateKey 对象
	 *
	 * @author yang.lu
	 */
	public static PrivateKey getPrivateKey(String base64PrivateKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		return keyFactory.generatePrivate(spec);
	}

	/**
	 * 获取密钥对象的 Base64 编码
	 *
	 * @param key 密钥对象 (Public 或 Private)
	 * @return Base64 字符串
	 * @author yang.lu
	 */
	public static String getKeyString(Key key) {
		// Java 21 模式匹配简化代码
		return switch (key) {
			case null -> null;
			default -> Base64.getEncoder().encodeToString(key.getEncoded());
		};
	}
}
