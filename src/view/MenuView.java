package view;

import java.util.Scanner;

import model.vo.UserVo;

//主菜单界面的显示
public class MenuView {
	//显示界面,接收输入的数据,并将数据传递给调用者
	public int display(UserVo user){
		System.out.println("------欢迎使用超市收银系统------");
		System.out.println("1、收银");
		System.out.println("2、查询统计");
		System.out.println("3、商品维护");
		System.out.println("4、修改密码");
		System.out.println("5、数据导出");
		System.out.println("6、退出");
		System.out.println("当前收银员："+user.getChrName());
		System.out.println("请选择（1-6）：");
        Scanner input=new Scanner(System.in);
        int choice=input.nextInt();
       return choice;
	}

}
