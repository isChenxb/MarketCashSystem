package view;

import java.util.Scanner;

import model.vo.UserVo;

//���˵��������ʾ
public class MenuView {
	//��ʾ����,�������������,�������ݴ��ݸ�������
	public int display(UserVo user){
		System.out.println("------��ӭʹ�ó�������ϵͳ------");
		System.out.println("1������");
		System.out.println("2����ѯͳ��");
		System.out.println("3����Ʒά��");
		System.out.println("4���޸�����");
		System.out.println("5�����ݵ���");
		System.out.println("6���˳�");
		System.out.println("��ǰ����Ա��"+user.getChrName());
		System.out.println("��ѡ��1-6����");
        Scanner input=new Scanner(System.in);
        int choice=input.nextInt();
       return choice;
	}

}
