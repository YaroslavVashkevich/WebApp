package org.example.servlet;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.service.ImageService;
import org.example.util.UrlPath;

import java.io.InputStream;


@WebServlet(UrlPath.IMAGES + "/*")
public class ImageServlet extends HttpServlet {
    private final ImageService imageService = ImageService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        String imagePath = requestUri.replace("/images", "");
        imageService.get(imagePath).ifPresentOrElse(image -> {
            resp.setContentType("application/octet-stream");
            writeImage(image, resp);
        }, () -> resp.setStatus(404));}
    @SneakyThrows
    private void writeImage(InputStream image, HttpServletResponse resp) {
        try (image; ServletOutputStream outputStream = resp.getOutputStream()) {
            int currentByte;
            while ((currentByte = image.read()) != -1) { 
                outputStream.write(currentByte);}}}}
