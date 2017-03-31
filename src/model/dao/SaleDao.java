package model.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import model.vo.UserVo;
import util.DBManager;
import util.Tools;
import util.Validate;

public class SaleDao {
	
	//收银功能
	public void getMoney(UserVo user) throws SQLException, IOException{
		DBManager db=new DBManager();
		Tools ts=new Tools();
		Scanner input=new Scanner(System.in);
		String date;
		String lsh,sale_men,sale_time;
		String product_no="";
		String product_name="";
		Float product_price=0.f;
		int sale_sum;
		while(true){
		System.out.println("请输入商品条形码（6位数字字符）：");
		Validate va=new Validate();
		date=input.next();
		if (va.isCode(date)){        //检查条形码格式是否正确
			if (ts.isProductExist(date)){       //检查条形码是否存在对应商品
				break;            
			}else{
				System.out.println("该条形码不存在！请重新输入");
			}
		}
		}
		    System.out.println("请输入商品数量：");
			sale_sum=input.nextInt();
			String sql="SELECT * FROM tproduct WHERE product_no='"+date+"'";
			ResultSet rs=db.executeQuery(sql);
			while (rs.next()){
				product_no=rs.getString(1);
				product_name=rs.getString(2);
				product_price=rs.getFloat(3);
			}
			rs.close();
			//调用工具得到流水号
			lsh=ts.generateLsh();
			//获取当前登录用户中文名
			sale_men=user.getChrName();
			//获取当前时间
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			sale_time=df.format(new Date());
			
			//将销售信息写入数据表
			String sql2="INSERT INTO tsale_detail VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pst=db.getConnection().prepareStatement(sql2);
			pst.setString(1, lsh);
			pst.setString(2, product_no);
			pst.setString(3, product_name);
			pst.setFloat(4, product_price);
			pst.setInt(5, sale_sum);
			pst.setString(6, sale_men);
			pst.setString(7, sale_time);
			int iCount=pst.executeUpdate();
			if (iCount>0){
				System.out.println("成功增加一笔销售数据");
			}else{
				System.out.println("增加销售数据失败！");
			}
			pst.close();
		    db.close();
	}
	
	
	//查询统计功能
	public void forQuery() throws SQLException, IOException{
		Validate va=new Validate();
		Scanner input=new Scanner(System.in);
		DBManager db=new DBManager();
		String date;
		while(true){
			System.out.println("请输入销售日期（yyyy-mm-dd）:");
			date=input.next();
			if (va.isDate(date)){
				break;
			}
		}
		String[] arrays=date.split("-");
		date=arrays[0]+arrays[1]+arrays[2];
		String sql="SELECT * FROM tsale_detail WHERE LEFT(sale_no,8)='"+date+"'";
		ResultSet rs=db.executeQuery(sql);
		int allSum=0;     //销售总数
		int allProduct=0;       //商品总件
		int allSaleMoney=0;    //销售总金额
		System.out.println(arrays[0]+"年"+arrays[1]+"月"+arrays[2]+"日销售如下");
		System.out.println("流水号	          商品名称       单价	      数量	      金额	          时间	           收银员");
		System.out.println("=====	      =======	 =====	  ====	      ===		      ====	           =====");
		while (rs.next()){
			System.out.print(rs.getString(1)+"    ");
			System.out.print(rs.getString(3)+"    ");
			System.out.print(rs.getFloat(4)+"    ");
			System.out.print(rs.getInt(5)+"    ");
			System.out.print(rs.getFloat(4)*rs.getInt(5)+"    ");
			System.out.print(rs.getString(7)+"    ");
			System.out.println(rs.getString(6));
			allSum++;
			allProduct+=rs.getInt(5);
			allSaleMoney+=rs.getFloat(4)*rs.getInt(5);
		}
		System.out.println("=====	=======	=====	====	===		====	=====");
		System.out.println("销售总数："+allSum+"笔  商品总件："+allProduct+"件  销售总金额："+allSaleMoney+"元");
		System.out.println("请输入任意按键返回主界面");
		String str=input.next();
		if (str!=null){
			return;
		}
	}
	
	//将销售数据导出到xls文件中
	public void exportToExcel() throws IOException, SQLException, RowsExceededException, WriteException{
		DBManager db=new DBManager();
		//获取当前时间
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String date=df.format(new Date());
		
		WritableWorkbook writebook=Workbook.createWorkbook(
				new File(".//exportFile//saleDetail"+date+".xls"));
		WritableSheet writeSheet=writebook.createSheet("销售记录", 0);
		//查询销售表中的销售记录，存入结果集中
		String sql="SELECT * FROM tsale_detail";
		ResultSet rs=db.executeQuery(sql);
		//开始写入数据
		String[] title={"流水号","条形码","商品名称","单价","数量","销售员","销售时间"};
		//写入标题
		for (int col=0;col<7;col++){
			//设置标题字体格式
			WritableFont arial11font = new WritableFont(WritableFont.ARIAL, 11,WritableFont.BOLD);
			WritableCellFormat arial11format = new WritableCellFormat (arial11font);
			arial11format.setAlignment(jxl.format.Alignment.CENTRE);  //水平居中
			arial11format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);  //边框样式
			arial11format.setBackground(Colour.RED); //背景颜色
			Label label=new Label(col,0,title[col],arial11format);
			writeSheet.addCell(label);
		}
		//写入商品信息
		int row=1;
		while(rs.next()){
			String sale_no=rs.getString(1);
			String product_no=rs.getString(2);
			String product_name=rs.getString(3);
			String product_price=String.valueOf(rs.getFloat(4));
			String sale_sum=String.valueOf(rs.getInt(5));
			String sale_men=rs.getString(6);
			String sale_time=rs.getString(7);
			String[] message={sale_no,product_no,product_name,product_price,sale_sum,sale_men,sale_time};
			for(int col=0;col<7;col++){
				WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
				WritableCellFormat arial10format = new WritableCellFormat (arial10font);
				arial10format.setAlignment(jxl.format.Alignment.CENTRE);  //水平居中
				arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);  //边框样式
				Label label=new Label(col,row,message[col],arial10format);
				writeSheet.addCell(label);
			}
			row++;
		}
		System.out.println("成功导入"+(row-1)+"条销售数据到excel文件中");
		writebook.write();
		writebook.close();
		rs.close();
		db.close();
	}
	
	
	//将销售数据导出到txt文件中
	public void exportToTxt() throws IOException, SQLException{
		//获取当前时间
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
	    String date=df.format(new Date());
	    
	    DBManager db=new DBManager();
	    File file=new File(".//exportFile//saleDetail"+date+".txt");
	    FileWriter wri=new FileWriter(file);
	    PrintWriter prn=new PrintWriter(wri);
	  //查询销售表中的销售记录，存入结果集中
	    String sql="SELECT * FROM tsale_detail";
	    ResultSet rs=db.executeQuery(sql);
	    prn.println("流水号	 条形码        商品名称   	单价	  数量	收银员      销售时间");
	    prn.println("=====	=======	=====	====	===		====	=====");
	    //写入销售信息
	    int sum=0;
	    while (rs.next()){
	    String sale_no=rs.getString(1);
		String product_no=rs.getString(2);
		String product_name=rs.getString(3);
		String product_price=String.valueOf(rs.getFloat(4));
		String sale_sum=String.valueOf(rs.getInt(5));
		String sale_men=rs.getString(6);
		String sale_time=rs.getString(7);
		prn.print(sale_no+"      ");
		prn.print(product_no+"      ");
		prn.print(product_name+"      ");
		prn.print(product_price+"      ");
		prn.print(sale_sum+"      ");
		prn.print(sale_men+"      ");
		prn.println(sale_time+"      ");
		sum++;
	    }
	    prn.close();
	    System.out.println("成功导出"+sum+"条销售数据到文本文件中");
	}

}