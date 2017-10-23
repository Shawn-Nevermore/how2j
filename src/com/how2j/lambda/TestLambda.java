package com.how2j.lambda;

import java.util.ArrayList;
import java.util.Random;

import com.how2j.character.Hero;

public class TestLambda {

	public static void main(String[] args) {
		Random r = new Random();
		ArrayList<Hero> heros = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			heros.add(new Hero(i, "Hero " + i, r.nextInt(1000), r.nextInt(100)));
		}

		System.out.println("��ʼ����ĺϼ���");
		System.out.println(heros);
		System.out.println("ʹ��������ķ�ʽ��ɸѡ�� hp>100 && damage<50��Ӣ��");
/*
		//������ʵ�ֽӿڵķ�ʽ�������filter����
		filter(heros, new HeroChecker() {

			@Override
			public boolean test(Hero h) {
				// TODO Auto-generated method stub
				return (h.getHp() > 100 && h.getDamage() < 50);
			}
		};);
*/	
		
		System.out.println("ʹ��Lambda���ʽ");
		filter(heros, h -> h.getHp() > 100 && h.getDamage() < 50);
		System.out.println("��Lamdba���ʽ��ʹ�þ�̬����");
		filter(heros, h -> TestLambda.testHero(h));
		System.out.println("ֱ�����þ�̬����");
		filter(heros, TestLambda::testHero);
	}

	private static boolean testHero(Hero h) {
		return h.getHp() > 100 && h.getDamage() < 50;
	}

	private static void filter(ArrayList<Hero> heros, HeroChecker checker) {
		for(Hero hero: heros) {
			if(checker.test(hero)) {
				System.out.println(hero);
			}
		}
	}
}
