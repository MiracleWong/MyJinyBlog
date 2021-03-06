package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.JloggArticleContent;
import domain.ResultInfo;
import service.ArticleService;
import service.Impl.ArticleServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servletFlushArticleContent")
public class ServletFlushArticleContent extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final ResultInfo info = new ResultInfo();
        info.setFlag(false);
        info.setErrorMsg("0");

        try{
            final String parameterAid = request.getParameter("aid");
            int aid = Integer.parseInt(parameterAid);
            ArticleService service = new ArticleServiceImpl();
            JloggArticleContent content = service.findContentByAid(aid);
            info.setFlag(true);
            info.setErrorMsg("1");
            info.setData(content);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String s = mapper.writeValueAsString(info);
            response.setContentType("application/json");
            response.getWriter().write(s);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}
