package model.dao;


import java.sql.ResultSet;
import java.util.Scanner;

import util.DBManager;
import util.Encription;
import model.vo.UserVo;

//��װ����user��������в���,һ��������Ӧһ�������ҵ���߼�
public class UserDao {
	//�û���¼����
	private int loginTimes = 1;
	public int getLoginTimes() {
		return loginTimes;
	}
	//ʵ�ֵ����û���¼���ж�
	public boolean canLogin(UserVo user) throws Exception{
		if (loginTimes >= 3) {
			System.out.println("���ֻ�ܳ��������˺�����3�Σ������˳���");
			System.exit(1);
		}
		boolean ret=false;
		DBManager db=new DBManager();
		Encription ep=new Encription();
		String userAccount=user.getUserAccount();
		String password=user.getPassword();
		password=ep.md5Encode(password);
		//
		String sql="SELECT * FROM tuser WHERE user_account='"+userAccount+
				"' and user_Password='"+password+"'";
		ResultSet rs=db.executeQuery(sql);
		if (rs.next()){
			ret=true;
		}else {
			loginTimes++;
		}
//		String sql="SELECT * FROM tuser WHERE user_account='"+userAccount+"'";
//		ResultSet rs=db.executeQuery(sql);
//		String realUserPassword="";
//		if (rs.next()){
//			realUserPassword=rs.getString("user_password");
//		}
//		if (password.equals(realUserPassword)){
//			ret=true;
//		}
		rs.close();
		db.close();
		return ret;
		
	}
	
	//������޸�(��Ҫ�ṩ�û������µ�����)
	public boolean changePassword(UserVo user) throws Exception{
		boolean ret=false;
		DBManager db=new DBManager();
		Encription ep=new Encription();
		String userAccount=user.getUserAccount();
		String newPassword=user.getPassword();
		Scanner input=new Scanner(System.in);
		System.out.println("���������������룺");
		newPassword=input.next();
		System.out.println("���ٴ��������룺");
		String passwordCheck=input.next();
		if (newPassword.equals(passwordCheck)){
			newPassword=ep.md5Encode(newPassword);
			String sql="UPDATE tuser SET user_password='"+newPassword+"' " +
					"WHERE user_account='"+userAccount+"'";
			if (db.executeUpdate(sql)){
				ret=true;
			}
		}else{
			System.out.println("�����������벻һ�£�");
		}
		db.close();
		return ret;
	}
	
	/*
	 * ������Ŀ����Ҫ���Ӷ�user����������������Ӧ�ķ���
	 */
	
}
