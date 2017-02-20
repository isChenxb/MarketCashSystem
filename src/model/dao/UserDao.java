package model.dao;


import java.sql.ResultSet;
import java.util.Scanner;

import util.DBManager;
import util.Encription;
import model.vo.UserVo;

//封装的是user对象的所有操作,一个方法对应一个具体的业务逻辑
public class UserDao {
	//用户登录次数
	private int loginTimes = 1;
	public int getLoginTimes() {
		return loginTimes;
	}
	//实现的是用户登录的判断
	public boolean canLogin(UserVo user) throws Exception{
		if (loginTimes >= 3) {
			System.out.println("最多只能尝试输入账号密码3次，程序退出。");
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
	
	//密码的修改(需要提供用户名及新的密码)
	public boolean changePassword(UserVo user) throws Exception{
		boolean ret=false;
		DBManager db=new DBManager();
		Encription ep=new Encription();
		String userAccount=user.getUserAccount();
		String newPassword=user.getPassword();
		Scanner input=new Scanner(System.in);
		System.out.println("请输入您的新密码：");
		newPassword=input.next();
		System.out.println("请再次输入密码：");
		String passwordCheck=input.next();
		if (newPassword.equals(passwordCheck)){
			newPassword=ep.md5Encode(newPassword);
			String sql="UPDATE tuser SET user_password='"+newPassword+"' " +
					"WHERE user_account='"+userAccount+"'";
			if (db.executeUpdate(sql)){
				ret=true;
			}
		}else{
			System.out.println("两次密码输入不一致！");
		}
		db.close();
		return ret;
	}
	
	/*
	 * 根据项目的需要增加对user对象其他操作所对应的方法
	 */
	
}
