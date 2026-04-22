package io.github.luyang.base.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author yang.lu
 */
@DisplayName("网络工具类 NetUtil 测试")
public class NetUtilTest {

	@Nested
	@DisplayName("本地主机信息获取测试")
	class LocalhostInfoTest {

		@Test
		@DisplayName("获取本地 IP 字符串测试")
		void testGetLocalhostStr() {
			String ip = NetUtil.getLocalhostStr();
			// 验证不为空
			assertNotNull(ip, "获取到的 IP 不应为 null");
			// 简单验证 IP 格式（包含点号）
			assertTrue(ip.contains("."), "获取到的 IP 格式不正确: " + ip);
			System.out.println("检测到本地有效 IP: " + ip);
		}

		@Test
		@DisplayName("获取本地主机名测试")
		void testGetLocalHostName() {
			String hostName = NetUtil.getLocalHostName();
			assertNotNull(hostName, "主机名不应为 null");
			assertFalse(hostName.isEmpty(), "主机名不应为空字符串");
			System.out.println("检测到本地主机名: " + hostName);
		}

		@Test
		@DisplayName("核心获取逻辑测试 (优先级校验)")
		void testGetLocalhost() {
			InetAddress address = NetUtil.getLocalhost();
			assertNotNull(address);
			// 验证是否为 IPv4（根据工具类中的过滤器逻辑）
			assertInstanceOf(Inet4Address.class, address, "应当优先获取 IPv4 地址");
			// 验证非回环地址
			assertFalse(address.isLoopbackAddress(), "不应获取回环地址 (127.0.0.1)");
		}
	}

	@Nested
	@DisplayName("地址列表与过滤测试")
	class AddressListTest {

		@Test
		@DisplayName("获取所有 IPv4 地址集合测试")
		void testLocalAddressList() {
			LinkedHashSet<InetAddress> addresses = NetUtil.localAddressList(addr -> addr instanceof Inet4Address);

			assertNotNull(addresses);
			if (!addresses.isEmpty()) {
				// Java 21 Sequenced Collections 特性测试
				InetAddress first = addresses.getFirst();
				assertNotNull(first);
				assertTrue(first instanceof Inet4Address);

				System.out.println("本机所有 IPv4 地址清单:");
				addresses.forEach(addr -> System.out.println(" -> " + addr.getHostAddress()));
			}
		}

		@Test
		@DisplayName("自定义过滤器测试")
		void testFilteredAddressList() {
			// 仅获取 192.168 开头的内网地址（如果有）
			var filtered = NetUtil.localAddressList(addr ->
				addr.getHostAddress().startsWith("192.168")
			);

			assertNotNull(filtered);
			filtered.forEach(addr -> assertTrue(addr.getHostAddress().startsWith("192.168")));
		}
	}
}
