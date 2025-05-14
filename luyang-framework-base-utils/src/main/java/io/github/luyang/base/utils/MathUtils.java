package io.github.luyang.base.utils;

/**
 * 数学工具类，提供常见的数学计算方法。
 * @author wangjixin
 */
public final class MathUtils {

	private MathUtils() {
		throw new AssertionError("禁止实例化工具类");
	}

	/**
	 * 计算两个整数的最大公约数。
	 * @param a 第一个整数
	 * @param b 第二个整数
	 * @return 最大公约数
	 */
	public static int gcd(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	/**
	 * 计算两个整数的最小公倍数。
	 * @param a 第一个整数
	 * @param b 第二个整数
	 * @return 最小公倍数
	 */
	public static int lcm(int a, int b) {
		return (a * b) / gcd(a, b);
	}

	/**
	 * 判断一个数是否为质数。
	 * @param num 要判断的数
	 * @return 如果是质数返回 true，否则返回 false
	 */
	public static boolean isPrime(int num) {
		if (num < 2) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
}
