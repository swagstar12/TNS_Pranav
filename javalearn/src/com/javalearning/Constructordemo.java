package com.javalearning;
import java.util.Scanner;

public class Constructordemo {
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		String name=sc.nextLine();
		int ID= sc.nextInt();
		Demo d=new Demo(name, ID);
		System.out.println(d.getID());
		System.out.println(d.getName());
	}
}
class Demo
{
	private String name;
	private int ID;
	public Demo(String name, int ID)
	{
		this.name=name;
		this.ID=ID;
	}
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
}