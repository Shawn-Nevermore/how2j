package utils;

import java.util.HashSet;

/**
 * 生成随机数组的工具类 随机指定范围内n个不重复的数
 * 
 * @author 先生
 * @version v1.0
 */
public class RandomArray {

	// 私有构造方法，不让其他类创建本类对象，强制只能类名调用
	private RandomArray() {
	}

	public static void main(String[] args) {

		// 普通随机数组方法实现
		int[] arr = randomCommon(0, 100, 20);
		// print(arr);

		// HashSet生成随机数方法实现
		HashSet<Integer> set = new HashSet<Integer>();
		randomSet(0, 100, 20, set);
		printSet(set);

	}

	/**
	 * 打印数组
	 * 
	 * @param arr
	 */
	public static void print(int[] arr) {

		System.out.print("[ ");
		for (int i : arr) {
			System.out.print(i + " ");
		}
		System.out.print("]");
	}

	/**
	 * 打印结果集
	 * 
	 * @param set
	 */
	public static void printSet(HashSet<Integer> set) {
		System.out.print("[ ");
		for (int i : set) {
			System.out.print(i + " ");
		}
		System.out.print("]");
	}

	/**
	 * 普通的双循环去重
	 * 
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n   随机数个数
	 * @return
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || min > max) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) Math.round(Math.random() * (max - min) + min);
			boolean flag = true;// 重复判定
			for (int j = 0; j < n; j++) {
				if (result[j] == num) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;

	}

	/**
	 * 利用HashSet的特征，只能存放不同的值
	 * 
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n   随机数个数
	 * @param set 随机数结果集
	 */
	public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
		if (n > max - min + 1 || min > max) {
			return;
		}

		while (set.size() < n) {
			int num = (int) Math.round(Math.random() * (max - min) + min);
			set.add(num);
		}
	}
}
