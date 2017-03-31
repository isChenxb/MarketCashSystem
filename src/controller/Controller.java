package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import util.DBManager;
import view.SubmenuView;
import model.dao.ProductDao;
import model.dao.SaleDao;
import model.dao.UserDao;
import model.vo.UserVo;

//控制器，接收所有输入界面的请求，根据不同的请求来决定将请求转发给相应的dao来实现处理
public class Controller {
	UserVo user;

	public UserVo getUser() {
		return user;
	}
	// 一个方法对应一个具体的请求

	// 实现登陆请求的转发
	public boolean loginRequestProcess(UserVo user) throws Exception {
		boolean flag = true;
		UserDao dao = new UserDao();
		this.user = dao.Login(user);
		if (this.user == null) {// 登陆不成功，显示错误提示
			System.out.println("用户名或密码错误！");
			flag = false;
		}
		return flag;
	}

	// 实现主菜单选择请求的转发
	public boolean mainMenuRequestProcess(int choice) throws Exception {
		boolean flag = true;
		UserDao userDao = new UserDao();
		SaleDao saleDao = new SaleDao();
		switch (choice) {
		case 1: {
			saleDao.getMoney(user);
			break;
		}
		case 2: {
			saleDao.forQuery();
			break;
		}
		case 3: {
			if (user.getRole().equals("普通用户")) {
				System.out.println("当前用户没有执行该项功能的权限");
				break;
			}
			SubmenuView sv = new SubmenuView();
			int subChoice;
			// 若用户不选择退出项，则执行完用户请求后继续输出子菜单
			while ((subChoice = sv.importDisplay()) != 5) {
				Controller con = new Controller();
				con.importSubMenuRequestProcess(subChoice);
			}
			break;
		}
		case 4: {
			if (userDao.changePassword(user)) {
				System.out.println("修改密码成功");
			} else {
				System.out.println("密码修改失败");
			}
			break;
		}
		case 5: {
			SubmenuView sv = new SubmenuView();
			int subChoice;
			// 若用户不选择退出项，则执行完用户请求后继续输出子菜单
			while ((subChoice = sv.exportDisplay()) != 3) {
				Controller con = new Controller();
				con.exportSubMenuRequestProcess(subChoice);
			}
			break;
		}
		case 6: {
			Scanner input = new Scanner(System.in);
			System.out.println("您确认退出系统吗（y/n）");
			String str = input.next();
			if (str.equals("y")) {
				System.out.println("程序已退出，欢迎下次继续使用");
				flag = false;
			}
			return flag;
		}
		default: {
			System.out.println("输入无效，只能输入1-6");
		}
		}
		return flag;
	}

	// 实现商品维护子菜单请求的转发
	public void importSubMenuRequestProcess(int choice) throws BiffException, IOException, SQLException {
		ProductDao proDao = new ProductDao();
		switch (choice) {
		case 1: {
			proDao.importFromExcel();
			break;
		}
		case 2: {
			proDao.importFromTxt();
			break;
		}
		case 3: {
			proDao.importFromScanner();
			break;
		}
		case 4: {
			proDao.productQuery();
			break;
		}
		default: {
			System.out.println("输入错误");
			break;
		}
		}
	}

	// 实现数据导出子菜单的转发
	public void exportSubMenuRequestProcess(int choice)
			throws RowsExceededException, WriteException, IOException, SQLException {
		SaleDao saleDao = new SaleDao();
		switch (choice) {
		case 1: {
			saleDao.exportToExcel();
			break;
		}
		case 2: {
			saleDao.exportToTxt();
			break;
		}
		default: {
			System.out.println("输入错误");
			break;
		}
		}
	}

}
