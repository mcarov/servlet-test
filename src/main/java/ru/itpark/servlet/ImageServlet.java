package ru.itpark.servlet;

import ru.itpark.service.ImageService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = -9001225328362761679L;

    private transient ImageService imageService;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            imageService = (ImageService) context.lookup("java:/comp/env/bean/image-service");
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if(req.getPathInfo() != null) {
                String[] split = req.getPathInfo().split("/");
                if(split.length == 2) {
                    imageService.readImage(split[1], resp.getOutputStream());
                }
                else
                    imageService.readImage(resp.getOutputStream());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
