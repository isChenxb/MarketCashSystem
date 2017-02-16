package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import util.DBManager;
import util.Tools;
import util.Validate;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.vo.ProductVo;

public class ProductDao {
	
	//接收ArrayList对象，将其中的Product对象写入数据库
		public void DBimport(ArrayList<ProductVo> proList) throws SQLException, IOException{
			DBManager db=new DBManager();
			int iCount=0;
			for (int i=0;i<proList.size();i++){
				String sql="INSERT INTO tproduct VALUES(?,?,?,?)";
				PreparedStatement pst=db.getConnection().prepareStatement(sql);
				pst.setString(1, proList.get(i).getProduct_no());
				pst.setString(2, proList.get(i).getProduct_name());
				pst.setFloat(3, proList.get(i).getProduct_price());
				pst.setString(4, proList.get(i).getProduct_from());
				iCount+=pst.executeUpdate();
				pst.close();
			}
			System.out.println("成功导入"+iCount+"条商品数据");
			db.close();
		}
	
	//从Excel中导入数据
	public void importFromExcel() throws BiffException, IOException, SQLException{
		Validate va=new Validate();
		//创建一个ArrayList存放	Product对象
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		Tools ts=new Tools();
		ProductDao proDao=new ProductDao();
	    //创建工作簿对象
		Workbook workbook=Workbook.getWorkbook(new File(".//importFile//product.xls"));
		//创建一个sheet
		Sheet sheet=workbook.getSheet(0);
		int passExist=0;
		int passError=0;
		for(int  row=1;row<sheet.getRows();row++){
			Cell[] cell=sheet.getRow(row);
			String product_no,product_name,product_price,product_from;
			product_no=cell[0].getContents();
			product_name=cell[1].getContents();
			product_price=cell[2].getContents();
			product_from=cell[3].getContents();
			float price=Float.valueOf(product_price);
			//若条形码格式错误，则跳过此商品，添加下一个
			if (!va.isCode(product_no)){
				passError++;
				continue;
			}
			//若已存在相同的商品号，则跳过此商品，添加下一个
			if (ts.isProductExist(product_no)){
				passExist++;
				continue;
			}
			proList.add(new ProductVo(product_no,product_name,price,product_from));
		}
		//调用DBimprot方法将商品信息写入数据库
		proDao.DBimport(proList);
		System.out.println("跳过"+passExist+"条已存在数据");
		System.out.println("跳过"+passError+"条条形码格式错误数据");
	}
	
	
	//从文本文件导入商品信息
	public void importFromTxt() throws IOException, SQLException{
		Tools ts=new Tools();
		ProductDao proDao=new ProductDao();
		Validate va=new Validate();
		//创建一个ArrayList存放	Product对象
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		File file=new File(".//importFile//product.txt");
		FileReader read=new FileReader(file);
		BufferedReader buff=new BufferedReader(read);
		String aLine="";
		int passExist=0;
		int passError=0;
		//从文本读入信息，将商品信息存入Product对象中
		while ((aLine=buff.readLine())!=null){
			String[] str=aLine.split("，");
			String product_no=str[0];
			String product_name=str[1];
			String product_from=str[3];
			float product_price=Float.parseFloat(str[2]);
			//若条形码格式错误，则跳过此商品，添加下一个
			if (!va.isCode(product_no)){
				passError++;
				continue;
			}
			//若已存在相同的商品号，则跳过此商品，添加下一个
			if (ts.isProductExist(product_no)){
				passExist++;
				continue;
			}
			proList.add(new ProductVo(product_no,product_name,product_price,product_from));
		}
		//调用DBimprot方法将商品信息写入数据库
		proDao.DBimport(proList);
		System.out.println("跳过"+passExist+"条已存在数据");
		System.out.println("跳过"+passError+"条条形码格式错误数据");
	}
	
	
	//从键盘输入商品信息
	public void importFromScanner() throws SQLException, IOException{
		//创建一个ArrayList存放	Product对象
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		ProductDao proDao=new ProductDao();
		Validate va=new Validate();
		Tools ts=new Tools();
		Scanner input=new Scanner(System.in);
		while (true){
		System.out.println("请按“商品条形码，商品名称，单价，供应商”格式输入商品信息，" +
				"输入“000”结束录入");
		String str=input.next();
		//若用户输入000则结束输入
		if (str.equals("000")){
			break;
		}
		String[] arrays=str.split("，");
		if (arrays.length!=4){
			System.out.println("您输入的格式有误，请重新输入");
			continue;
		}
		if (!(va.isCode(arrays[0]))){          //若条形码格式错误则跳过
			continue;
		}
		String product_no=arrays[0];
		String product_name=arrays[1];
		float product_price=Float.parseFloat(arrays[2]);
		String product_from=arrays[3];
		//若已存在相同的商品号，则跳过此商品，添加下一个
		if (ts.isProductExist(product_no)){
			System.out.println("该条形码已存在，请重新输入");
			continue;
		}
		proList.add(new ProductVo(product_no,product_name,product_price,product_from));
		}
		//调用DBimprot方法将商品信息写入数据库
				proDao.DBimport(proList);
	}
	
	//商品查询功能
	public void productQuery() throws SQLException, IOException{
		DBManager db=new DBManager();
		Scanner input=new Scanner(System.in);
		System.out.println("请输入查询的商品名称：");
		String product_name=input.next();
		String sql="SELECT * FROM tproduct WHERE product_name LIKE '%"+product_name+"%'";
		ResultSet rs=db.executeQuery(sql);
		
		//获取rs的长度
		rs.last();
		int n=rs.getRow();
		
		System.out.println("满足条件的记录共"+n+"条，信息如下：");
		System.out.println("");
		System.out.println("序号	条形码	商品名称	  单价	 供应商");
		System.out.println("===		=====	=======	====	=====");
		//重置结果集指针
		rs.beforeFirst();
		
		//循环输出查询结果
		int i=1;
		while (rs.next()){
			System.out.print(i+"     "+rs.getString(1)+"    ");
			System.out.print(rs.getString(2)+"    ");
			System.out.print(rs.getFloat(3)+"    ");
			System.out.println(rs.getString(4)+"    ");
			i++;
			}
		rs.close();
		db.close();
	}

}
