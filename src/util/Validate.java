package util;

import java.sql.SQLException;

//ʵ�ֳ��������ݸ�ʽ��У��
public class Validate {
	
	
	//�������ʽ��У��
	public boolean isCode(String date) throws SQLException{
		boolean ret=true;
		int i=date.length();
		if (i!=6){
			System.out.println("���������Ʒ�������ʽ����ȷ������������");
			ret=false;
		}
		return ret;
	}
	
	
	//���ڸ�ʽ��У��
	public boolean isDate(String date){
		boolean ret=false;
		String[] arrays=date.split("-");
		if (arrays.length==3){
			if (arrays[0].length()==4&&arrays[1].length()==2&&arrays[2].length()==2){
				ret=true;
			}
		}
		if (!ret){
			System.out.println("����������ڸ�ʽ����ȷ������������");
		}
		return ret;
	}
	

}

