package view;

import java.sql.SQLException;
import java.util.Scanner;

import model.vo.UserVo;

//ʵ�ֵ��ǵ�½�������ʾ
public class LoginView {
	
	//��ʾ����,�������û�������,����������ݷ�װ�ɶ���󴫵ݸ�������
	public UserVo display() throws SQLException{
		UserVo user=new UserVo();
		Scanner input=new Scanner(System.in);
		System.out.println("*****��ӭʹ�ó�������ϵͳ*****");
		System.out.println("�����������˺ţ�");
		String userAccount=input.next();
		System.out.println("�������������룺");
		String password=input.next();
		user.setUserAccount(userAccount);
		user.setPassword(password);
		return user;
		
	}

}
