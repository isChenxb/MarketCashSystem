package view;

import java.util.Scanner;
    //�Ӳ˵�
public class SubmenuView {
	
	//��Ʒά���Ӳ˵�
	public int importDisplay(){
		System.out.println("------������Ʒ����ά��------");
		System.out.println("1����excel�е�������");
		System.out.println("2�����ı��ļ���������");
		System.out.println("3����������");
		System.out.println("4������Ʒ���Ʋ�ѯ");
		System.out.println("5���������˵�");
		System.out.println("��ѡ��1-5����");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		return choice;
	}
	
	
	//���ݵ����Ӳ˵�
	public int  exportDisplay(){
		System.out.println("------����������Ϣ����------");
		System.out.println("1��������excel�ļ�");
		System.out.println("2���������ı��ļ�");
		System.out.println("3���������˵�");
		System.out.println("��ѡ��1-3����");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		return choice;
	}

}
