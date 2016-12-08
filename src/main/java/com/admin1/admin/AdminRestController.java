package com.admin1.admin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminRestController {
	
	 @RequestMapping(value= "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	    public Check CheckUser(@RequestParam(value="userName")String userName,@RequestParam(value="password")String password) {
            Check check=new Check();
            if(userName.equals("admin")&&password.equals("admin")){
            	check.setUserExist(true);
            }else{
            	check.setUserExist(false);
            }
		
			 return check;
	 }

}
