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

//���������������������������󣬸��ݲ�ͬ������������������ת������Ӧ��dao��ʵ�ִ���
public class Controller {
	UserVo user;

	public UserVo getUser() {
		return user;
	}
	// һ��������Ӧһ�����������

	// ʵ�ֵ�½�����ת��
	public boolean loginRequestProcess(UserVo user) throws Exception {
		boolean flag = true;
		UserDao dao = new UserDao();
		this.user = dao.Login(user);
		if (this.user == null) {// ��½���ɹ�����ʾ������ʾ
			System.out.println("�û������������");
			flag = false;
		}
		return flag;
	}

	// ʵ�����˵�ѡ�������ת��
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
			if (user.getRole().equals("��ͨ�û�")) {
				System.out.println("��ǰ�û�û��ִ�и���ܵ�Ȩ��");
				break;
			}
			SubmenuView sv = new SubmenuView();
			int subChoice;
			// ���û���ѡ���˳����ִ�����û�������������Ӳ˵�
			while ((subChoice = sv.importDisplay()) != 5) {
				Controller con = new Controller();
				con.importSubMenuRequestProcess(subChoice);
			}
			break;
		}
		case 4: {
			if (userDao.changePassword(user)) {
				System.out.println("�޸�����ɹ�");
			} else {
				System.out.println("�����޸�ʧ��");
			}
			break;
		}
		case 5: {
			SubmenuView sv = new SubmenuView();
			int subChoice;
			// ���û���ѡ���˳����ִ�����û�������������Ӳ˵�
			while ((subChoice = sv.exportDisplay()) != 3) {
				Controller con = new Controller();
				con.exportSubMenuRequestProcess(subChoice);
			}
			break;
		}
		case 6: {
			Scanner input = new Scanner(System.in);
			System.out.println("��ȷ���˳�ϵͳ��y/n��");
			String str = input.next();
			if (str.equals("y")) {
				System.out.println("�������˳�����ӭ�´μ���ʹ��");
				flag = false;
			}
			return flag;
		}
		default: {
			System.out.println("������Ч��ֻ������1-6");
		}
		}
		return flag;
	}

	// ʵ����Ʒά���Ӳ˵������ת��
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
			System.out.println("�������");
			break;
		}
		}
	}

	// ʵ�����ݵ����Ӳ˵���ת��
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
			System.out.println("�������");
			break;
		}
		}
	}

}
