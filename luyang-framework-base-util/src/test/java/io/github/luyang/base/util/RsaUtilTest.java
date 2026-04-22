package io.github.luyang.base.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author yang.lu
 */
@DisplayName("RSA 加解密工具类测试")
public class RsaUtilTest {

	@Nested
	@DisplayName("密钥生成测试")
	class KeyGenerationTest {

		@Test
		@DisplayName("默认长度(2048位)密钥生成测试")
		void testGenerateDefaultKeyPair() throws Exception {
			KeyPair keyPair = RsaUtil.generateKeyPair();
			assertNotNull(keyPair);
			assertNotNull(keyPair.getPublic());
			assertNotNull(keyPair.getPrivate());

			// 验证生成的公钥编码是否为 X.509
			assertEquals("X.509", keyPair.getPublic().getFormat());
			// 验证生成的私钥编码是否为 PKCS#8
			assertEquals("PKCS#8", keyPair.getPrivate().getFormat());
		}

		@ParameterizedTest
		@ValueSource(ints = {1024, 2048, 3072})
		@DisplayName("不同长度密钥生成测试")
		void testGenerateSpecificKeyPair(int keySize) throws Exception {
			KeyPair keyPair = RsaUtil.generateKeyPair(keySize);
			assertNotNull(keyPair);

			String pubKeyStr = RsaUtil.getKeyString(keyPair.getPublic());
			assertNotNull(pubKeyStr);
			assertFalse(pubKeyStr.isEmpty());
		}

		@Test
		@DisplayName("中文及特殊字符加解密测试")
		void testUnicodeData() throws Exception {
			String originalData = "你好，RSA加密！🚀 @#￥%……&*";
			KeyPair kp = RsaUtil.generateKeyPair(2048);

			String encrypted = RsaUtil.encrypt(originalData, RsaUtil.getKeyString(kp.getPublic()));
			String decrypted = RsaUtil.decrypt(encrypted, RsaUtil.getKeyString(kp.getPrivate()));

			assertEquals(originalData, decrypted);
		}
	}
}
