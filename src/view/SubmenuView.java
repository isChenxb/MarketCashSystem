package view;

import java.util.Scanner;
    //子菜单
public class SubmenuView {
	
	//商品维护子菜单
	public int importDisplay(){
		System.out.println("------超市商品管理维护------");
		System.out.println("1、从excel中导入数据");
		System.out.println("2、从文本文件导入数据");
		System.out.println("3、键盘输入");
		System.out.println("4、按商品名称查询");
		System.out.println("5、返回主菜单");
		System.out.println("请选择（1-5）：");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		return choice;
	}
	
	
	//数据导出子菜单
	public int  exportDisplay(){
		System.out.println("------超市销售信息导出------");
		System.out.println("1、导出到excel文件");
		System.out.println("2、导出到文本文件");
		System.out.println("3、返回主菜单");
		System.out.println("请选择（1-3）：");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		return choice;
	}

}
