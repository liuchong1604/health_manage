package com.health.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created with IDEA
 *  前端不能显示(到学校问同学解决)
 * @author chong  liu
 * @create 2020/3/9 11:51
 */
@Controller
@RequestMapping("/code")
public class CodeController {
    private char []code ={ 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2',
            '3', '4', '5', '6', '7', '8', '9' };
    private static final int WIDTH = 90;
    private static final int HEIGHT = 40;
    private static final int LENGTH = 4;
    @GetMapping("/code")
    public void generateCode(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("生成图片验证码");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //设置响应的MIME类型-----jpg
        response.setContentType("image/jpeg");

        //创建一个透明背景的图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Font mFont = new Font("Arial", Font.BOLD, 18);
        Graphics g = image.getGraphics();
        Random rd = new Random();

        //设置背景颜色
        g.setColor(new Color(rd.nextInt(55) + 200, rd.nextInt(55) + 200, rd.nextInt(55) + 200));
        g.fillRect(0, 0, WIDTH, HEIGHT);      //填充矩形
        g.setFont(mFont);

        //画边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        //随机产生验证码
        String result = "";
        for (int i = 0; i < LENGTH; ++i) {
            result += code[rd.nextInt(code.length)];
        }
        HttpSession session = request.getSession();
        session.setAttribute("code", result);
        System.out.println(session.getAttribute("code"));

        //画验证码
        for (int i = 0; i < result.length(); i++) {
            g.setColor(new Color(rd.nextInt(200), rd.nextInt(200), rd.nextInt(200)));
            g.drawString(result.charAt(i) + "", 20 * i + 1, 25);
        }

        //随机产生3个干扰线
        for (int i = 0; i < 3; i++) {
            g.setColor(new Color(rd.nextInt(200), rd.nextInt(200), rd.nextInt(200)));
            int x1 = rd.nextInt(WIDTH);
            int x2 = rd.nextInt(WIDTH);
            int x3 = rd.nextInt(WIDTH);
            int x4 = rd.nextInt(WIDTH);
            int y1 = rd.nextInt(HEIGHT);
            int y2 = rd.nextInt(HEIGHT);
            int y3 = rd.nextInt(HEIGHT);
            int y4 = rd.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x3, y3, x4, y4);
        }
        g.dispose();
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "JPEG", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
