package com.rongrong.shop.action;

import com.rongrong.shop.bean.User;
import com.rongrong.shop.service.ShopService;
import com.rongrong.shop.utils.Constants;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;
    //定义业务层对象
    private ShopService shopService;

    @Override
    public void init() throws ServletException {
        super.init();
        //获取spring的容器。然后从容器中得到业务对象
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        shopService = (ShopService) context.getBean("shopService");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.request = req;
        this.response = resp;
        String method = req.getParameter("method");
        switch (method){
            case "getJsp":
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
                break;
            case "login":
                login();
                break;
        }
    }

    private void login(){
        try{
            String loginName = request.getParameter("loginName");
            String passWord = request.getParameter("passWord");
            Map<String, Object> results = shopService.login(loginName, passWord);
            if((int)results.get("code") == 0){
                User user = (User) results.get("msg");
                request.setAttribute(Constants.USER_SESSION,user);
              // request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request,response);
               response.sendRedirect(request.getContextPath()+"/list?method=getAll");
            }else {
                String msg =  results.get("msg") + "";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
