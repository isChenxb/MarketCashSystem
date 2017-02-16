package util;

import java.sql.SQLException;

//实现常见的数据格式的校验
public class Validate {
	
	
	//条形码格式的校验
	public boolean isCode(String date) throws SQLException{
		boolean ret=true;
		int i=date.length();
		if (i!=6){
			System.out.println("您输入的商品条形码格式不正确，请重新输入");
			ret=false;
		}
		return ret;
	}
	
	
	//日期格式的校验
	public boolean isDate(String date){
		boolean ret=false;
		String[] arrays=date.split("-");
		if (arrays.length==3){
			if (arrays[0].length()==4&&arrays[1].length()==2&&arrays[2].length()==2){
				ret=true;
			}
		}
		if (!ret){
			System.out.println("您输入的日期格式不正确，请重新输入");
		}
		return ret;
	}
	

}

