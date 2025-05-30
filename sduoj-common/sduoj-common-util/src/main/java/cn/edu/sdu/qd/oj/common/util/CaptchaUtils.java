package cn.edu.sdu.qd.oj.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtils {

    private static final Random RANDOM = new Random();

    // 验证码参数
    private static final int WIDTH = 165;
    private static final int HEIGHT = 45;
    private static final int LINE_COUNT = 30;
    private static final int CHAR_COUNT = 4;
    private static final String CHAR_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 字体配置
    private static Font getFont() {
        return new Font("Times New Roman", Font.PLAIN, 40);
    }

    // 随机颜色生成
    private static Color getRandomColor(int min, int max) {
        min = Math.min(min, 255);
        max = Math.min(max, 255);
        if (max <= min + 16) {
            max = min + 17;  // 保证不越界
        }
        int r = min + RANDOM.nextInt(max - min - 15);
        int g = min + RANDOM.nextInt(max - min - 13);
        int b = min + RANDOM.nextInt(max - min - 11);
        return new Color(r, g, b);
    }

    // 绘制干扰线
    private static void drawLine(Graphics g) {
        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HEIGHT);
        int xl = RANDOM.nextInt(20);
        int yl = RANDOM.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    // 获取随机字符
    private static char getRandomChar() {
        return CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length()));
    }

    // 绘制验证码字符
    private static String drawChars(Graphics g) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CHAR_COUNT; i++) {
            char ch = getRandomChar();
            sb.append(ch);

            g.setFont(getFont());
            g.setColor(getRandomColor(108, 190));
            g.translate(RANDOM.nextInt(3), RANDOM.nextInt(6));
            g.drawString(String.valueOf(ch), 40 * i + 10, 35);
        }
        return sb.toString();
    }

    // 获取验证码图像的 Base64 编码
    public static CaptchaEntity getRandomBase64Captcha() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        try {
            // 背景填充
            g.setColor(getRandomColor(200, 250));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // 干扰线
            for (int i = 0; i < LINE_COUNT; i++) {
                drawLine(g);
            }

            // 验证码字符
            String code = drawChars(g);

            // 转为 Base64
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);
            String base64 = Base64.getEncoder().encodeToString(bos.toByteArray());

            return new CaptchaEntity(code, "data:image/png;base64," + base64);
        } catch (Exception e) {
            throw new RuntimeException("生成验证码失败", e);
        } finally {
            g.dispose();
        }
    }

    // 封装验证码内容
    public static class CaptchaEntity {
        private final String randomStr;
        private final String base64;

        public CaptchaEntity(String randomStr, String base64) {
            this.randomStr = randomStr;
            this.base64 = base64;
        }

        public String getRandomStr() {
            return randomStr;
        }

        public String getBase64() {
            return base64;
        }
    }
}
