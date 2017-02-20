package driver;

import model.vo.UserVo;

import controller.Controller;
import view.LoginView;
import view.MenuView;

public class SystemDriver {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LoginView lv=new LoginView();
        Controller con=new Controller();
        while (true){
        	UserVo user=lv.display();
        	if (con.loginRequestProcess(user)){
        		break;     //�û���¼�ɹ�������ѭ��
        	}
        }
        
        //ѭ��������˵�
        MenuView mv=new MenuView();
        while(con.mainMenuRequestProcess(mv.display(con.getUser()))){
        	//���û���ѡ���˳����ִ�����û���������������˵�
        }
       
	}
	    
	    	
	}


