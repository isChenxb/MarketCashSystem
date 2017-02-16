package view;

import java.sql.SQLException;
import java.util.Scanner;

import model.vo.UserVo;

//实现的是登陆界面的显示
public class LoginView {
	
	//显示界面,并接受用户的输入,将输入的数据封装成对象后传递给调用者
	public UserVo display() throws SQLException{
		UserVo user=new UserVo();
		Scanner input=new Scanner(System.in);
		System.out.println("*****欢迎使用超市收银系统*****");
		System.out.println("请输入您的账号：");
		String userAccount=input.next();
		System.out.println("请输入您的密码：");
		String password=input.next();
		user.setUserAccount(userAccount);
		user.setPassword(password);
		return user;
		
	}

}
