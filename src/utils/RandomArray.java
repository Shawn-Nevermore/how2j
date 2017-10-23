package utils;

import java.util.HashSet;

/**
 * �����������Ĺ����� ���ָ����Χ��n�����ظ�����
 * 
 * @author ����
 * @version v1.0
 */
public class RandomArray {

	// ˽�й��췽�������������ഴ���������ǿ��ֻ����������
	private RandomArray() {
	}

	public static void main(String[] args) {

		// ��ͨ������鷽��ʵ��
		int[] arr = randomCommon(0, 100, 20);
		// print(arr);

		// HashSet�������������ʵ��
		HashSet<Integer> set = new HashSet<Integer>();
		randomSet(0, 100, 20, set);
		printSet(set);

	}

	/**
	 * ��ӡ����
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
	 * ��ӡ�����
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
	 * ��ͨ��˫ѭ��ȥ��
	 * 
	 * @param min ָ����Χ��Сֵ
	 * @param max ָ����Χ���ֵ
	 * @param n   ���������
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
			boolean flag = true;// �ظ��ж�
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
	 * ����HashSet��������ֻ�ܴ�Ų�ͬ��ֵ
	 * 
	 * @param min ָ����Χ��Сֵ
	 * @param max ָ����Χ���ֵ
	 * @param n   ���������
	 * @param set ����������
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
