package pro.onlyou.apache.shiro.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pro.onlyou.apache.shiro.boot.model.User;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class BaseController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session){
        UsernamePasswordToken token =
                new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();

        try{
            subject.login(token);

            User user = (User) subject.getPrincipal();
            session.setAttribute("user",user);
            return "redirect:/welcome";
        }catch (Exception e){
            return "/login";
        }
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();

        if(Objects.nonNull(subject)){
            subject.logout();
        }

        return "login";
    }

    @RequestMapping("/unauthorize")
    public String unauthorize(){
        return "unauthorize";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(){
        return "执行了update操作！";
    }

}
