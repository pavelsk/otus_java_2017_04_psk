package web.servlets;

import web.helpers.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AdminServlet extends HttpServlet {
    private static final String PAGE = "admin.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().println(TemplateProcessor.instance().getPage(PAGE, Collections.emptyMap()));
    }
}
