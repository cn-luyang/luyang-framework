package io.github.luyang.framework.base.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络工具类，提供网络相关的操作方法。
 * @author wangjixin
 */
public final class NetworkUtils {

	private NetworkUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	/**
	 * 检查是否可以连接到指定的主机和端口。
	 *
	 * @param host    主机名或 IP 地址
	 * @param port    端口号
	 * @param timeout 超时时间（毫秒）
	 * @return 如果可以连接，返回 true；否则返回 false
	 */
	public static boolean isHostReachable(String host, int port, int timeout) {
		try {
			InetAddress address = InetAddress.getByName(host);
			return address.isReachable(timeout);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 获取本地的 IP 地址。
	 *
	 * @return 本地 IP 地址，如果获取失败返回 null
	 */
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iface = interfaces.nextElement();
				if (iface.isLoopback() || !iface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
						return addr.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}
}
