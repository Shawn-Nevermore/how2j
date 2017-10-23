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

		System.out.println("初始化后的合集：");
		System.out.println(heros);
		System.out.println("使用匿名类的方式，筛选出 hp>100 && damage<50的英雄");
/*
		//匿名类实现接口的方式，传入给filter参数
		filter(heros, new HeroChecker() {

			@Override
			public boolean test(Hero h) {
				// TODO Auto-generated method stub
				return (h.getHp() > 100 && h.getDamage() < 50);
			}
		};);
*/	
		
		System.out.println("使用Lambda表达式");
		filter(heros, h -> h.getHp() > 100 && h.getDamage() < 50);
		System.out.println("在Lamdba表达式中使用静态方法");
		filter(heros, h -> TestLambda.testHero(h));
		System.out.println("直接引用静态方法");
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
