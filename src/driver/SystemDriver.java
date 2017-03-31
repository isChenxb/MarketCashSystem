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
        //统计用户登录次数times
        int times=0;
        while (true){
        	UserVo user=lv.display();
        	if (con.loginRequestProcess(user)){
        		break;     //用户登录成功则跳出循环
        	}else{
        		times++;
        		if (times==3){
        			System.out.println("最多只能尝试输入账号密码3次，程序退出。");
        			return; 
        		}
        		continue;
        	}
        }
        
        //循环输出主菜单
        MenuView mv=new MenuView();
        while(con.mainMenuRequestProcess(mv.display(con.getUser()))){
        	//若用户不选择退出项，则执行完用户请求后继续输出主菜单
        }
       
	}
	    
	    	
	}


