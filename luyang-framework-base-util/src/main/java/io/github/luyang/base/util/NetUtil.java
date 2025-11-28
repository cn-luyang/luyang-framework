package io.github.luyang.base.util;

import io.github.luyang.base.util.exception.UtilException;
import io.github.luyang.base.util.lang.Filter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * 网络相关工具
 *
 * @author yang.lu
 */
public final class NetUtil {

	private NetUtil(){}

	/**
	 * 获取本地主机IP地址字符串
	 *
	 * @return IP地址字符串，获取失败返回null
	 * @author yang.lu
	 */
	public static String getLocalhostStr() {
		InetAddress localhost = getLocalhost();
		return localhost != null ? localhost.getHostAddress() : null;
	}

	/**
	 * 获取本地主机名称
	 *
	 * @return 主机名称，获取失败返回null
	 * @author yang.lu
	 */
	public static String getLocalHostName() {
		InetAddress localhost = getLocalhost();
		return localhost != null ? localhost.getHostName() : null;
	}

	/**
	 * 获取最优的本地网络地址，优先选择非回环、IPv4、非站点本地的地址。
	 *
	 * @return 本地网络地址，获取失败返回 null
	 * @author yang.lu
	 */
	public static InetAddress getLocalhost() {
		// 获取所有符合条件的本地地址：非回环地址且为IPv4
		final LinkedHashSet<InetAddress> localAddressList = localAddressList(address ->
			!address.isLoopbackAddress() && address instanceof Inet4Address
		);

		if (CollUtil.isNotEmpty(localAddressList)) {
			InetAddress siteLocalAddress = null;

			for (InetAddress inetAddress : localAddressList) {
				if (!inetAddress.isSiteLocalAddress()) {
					// 优先返回非地区本地地址（非内网地址）
					// 非地区本地地址范围：非 10.0.0.0/8、172.16.0.0/12、192.168.0.0/16
					return inetAddress;
				} else if (siteLocalAddress == null) {
					// 记录第一个地区本地地址作为备选
					siteLocalAddress = inetAddress;
				}
			}

			// 如果没有非地区本地地址，返回第一个地区本地地址
			if (siteLocalAddress != null) {
				return siteLocalAddress;
			}
		}

		// 回退方案：使用标准方法获取本地主机
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// 忽略异常，返回null
			return null;
		}
	}

	/**
	 * 获取所有符合条件的本地网络地址
	 *
	 * @param addressFilter 地址过滤器
	 * @return 符合条件的网络地址集合
	 * @author yang.lu
	 */
	public static LinkedHashSet<InetAddress> localAddressList(Filter<InetAddress> addressFilter) {
		return localAddressList(null, addressFilter);
	}

	/**
	 * 获取所有符合条件的本地网络地址
	 *
	 * @param networkInterfaceFilter 网络接口过滤器
	 * @param addressFilter          地址过滤器
	 * @return 符合条件的网络地址集合
	 * @author yang.lu
	 */
	public static LinkedHashSet<InetAddress> localAddressList(Filter<NetworkInterface> networkInterfaceFilter,
															  Filter<InetAddress> addressFilter) {
		Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			throw new UtilException(e, "获取网络接口列表失败");
		}

		if (networkInterfaces == null) {
			throw new UtilException("网络接口列表为空");
		}

		final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();

		while (networkInterfaces.hasMoreElements()) {
			final NetworkInterface networkInterface = networkInterfaces.nextElement();

			// 过滤网络接口
			if (networkInterfaceFilter != null && !networkInterfaceFilter.accept(networkInterface)) {
				continue;
			}

			// 过滤未启用和回环接口
			if (!isValidNetworkInterface(networkInterface)) {
				continue;
			}

			final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				final InetAddress inetAddress = inetAddresses.nextElement();
				if (inetAddress != null && isAcceptableAddress(inetAddress, addressFilter)) {
					ipSet.add(inetAddress);
				}
			}
		}

		return ipSet;
	}

	/**
	 * 检查地址是否可接受
	 *
	 * @param inetAddress   网络地址
	 * @param addressFilter 地址过滤器
	 * @return 如果地址可接受返回true，否则返回false
	 * @author yang.lu
	 */
	private static boolean isAcceptableAddress(InetAddress inetAddress, Filter<InetAddress> addressFilter) {
		return addressFilter == null || addressFilter.accept(inetAddress);
	}

	/**
	 * 检查网络接口是否有效
	 *
	 * @param networkInterface 网络接口
	 * @return 如果接口有效返回true，否则返回false
	 * @author yang.lu
	 */
	private static boolean isValidNetworkInterface(NetworkInterface networkInterface) {
		try {
			return networkInterface.isUp() && !networkInterface.isLoopback();
		} catch (SocketException e) {
			return false;
		}
	}
}
