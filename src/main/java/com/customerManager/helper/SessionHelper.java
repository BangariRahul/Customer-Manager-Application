package com.customerManager.helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SessionHelper {

    public void removeMessageSession(){
        try{
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
               session.removeAttribute("message");
        }catch(Exception e){
              e.printStackTrace();
        }
    }
}
