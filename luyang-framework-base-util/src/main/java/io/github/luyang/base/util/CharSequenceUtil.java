package io.github.luyang.base.util;

/**
 * 字符序列工具类
 *
 * @author yang.lu
 */
public class CharSequenceUtil {


	/**
	 * 比较两个字符序列的指定区域是否匹配（支持忽略大小写）
	 *
	 * @param cs         主字符序列（被比较的源序列）
	 * @param ignoreCase 是否忽略大小写比较
	 *                   true：不区分大小写比较
	 *                   false：区分大小写比较
	 * @param thisStart  主字符序列中比较区域的起始索引位置
	 * @param substring  要比较的子字符序列
	 * @param start      子字符序列中比较区域的起始索引位置
	 * @param length     要比较的字符数量
	 * @return 如果指定区域的字符匹配则返回true，否则返回false
	 * @author yang.lu
	 */
	static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
								 final CharSequence substring, final int start, final int length) {

		// 如果两个字符序列都是String类型，直接调用String类的regionMatches方法
		if (cs instanceof String && substring instanceof String) {
			return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
		}

		// 初始化比较索引和长度计数器
		int index1 = thisStart;    // 主字符序列的当前比较位置
		int index2 = start;        // 子字符序列的当前比较位置
		int tmpLen = length;    // 剩余需要比较的字符数

		// 提前获取长度以便检测NPE，保持与java.lang.String版本相同的异常行为
		final int srcLen = cs.length() - thisStart;        // 主字符序列从起始位置到末尾的剩余长度
		final int otherLen = substring.length() - start;    // 子字符序列从起始位置到末尾的剩余长度

		// 检查参数有效性：起始位置和长度不能为负数
		if (thisStart < 0 || start < 0 || length < 0) {
			return false;
		}

		// 检查区域长度是否足够：两个序列的剩余长度都必须不小于要比较的长度
		if (srcLen < length || otherLen < length) {
			return false;
		}

		// 逐个字符比较指定长度的区域
		while (tmpLen-- > 0) {
			// 从两个序列中分别获取当前要比较的字符
			final char c1 = cs.charAt(index1++);
			final char c2 = substring.charAt(index2++);

			// 如果字符完全相同，继续比较下一个字符
			if (c1 == c2) {
				continue;
			}

			// 如果要求区分大小写且字符不相等，直接返回不匹配
			if (!ignoreCase) {
				return false;
			}

			// 忽略大小写比较：使用与String#regionMatches相同的逻辑
			// 先转换为大写比较
			final char u1 = Character.toUpperCase(c1);
			final char u2 = Character.toUpperCase(c2);
			if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
				return false;
			}
		}

		// 所有字符都比较完毕且匹配，返回true
		return true;
	}
}
