package cpServer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompressDemo {
	
	
	
	
	public static void main(String[] args) {

		/**

		 * d://3.jpg ԴͼƬ

		 * d://31.jpg Ŀ��ͼƬ

		 * ѹ����Ⱥ͸߶ȶ���1000

		 * 

		 */

		System.out.println("ѹ��ͼƬ��ʼ...");

		File srcfile = new File("d://3.PNG");

		System.out.println("ѹ��ǰsrcfile size:" + srcfile.length());

		reduceImg("d://3.PNG", "d://31.PNG", 1000, 1000,null);

		File distfile = new File("d://31.PNG");

		System.out.println("ѹ����distfile size:" + distfile.length());

	

	}

    /**

     * ����ָ����ȡ��߶Ȼ�ѹ������ �ķ�ʽ��ͼƬ����ѹ��

     * @param imgsrc ԴͼƬ��ַ

     * @param imgdist Ŀ��ͼƬ��ַ

     * @param widthdist ѹ����ͼƬ��ȣ���rate==nullʱ���ش���

     * @param heightdist ѹ����ͼƬ�߶ȣ���rate==nullʱ���ش���

     * @param rate ѹ������

     */

    public static void reduceImg(String imgsrc, String imgdist, int widthdist,

                                 int heightdist, Float rate) {

        try {

            File srcfile = new File(imgsrc);

            // ����ļ��Ƿ����

            if (!srcfile.exists()) {

                return;

            }

            // ���rate��Ϊ��˵���ǰ�����ѹ��

            if (rate != null && rate > 0) {

                // ��ȡ�ļ��߶ȺͿ��

                int[] results = getImgWidth(srcfile);

                if (results == null || results[0] == 0 || results[1] == 0) {

                    return;

                } else {

                    widthdist = (int) (results[0] * rate);

                    heightdist = (int) (results[1] * rate);

                }

            }

            // ��ʼ��ȡ�ļ�������ѹ��

            Image src = javax.imageio.ImageIO.read(srcfile);

            BufferedImage tag = new BufferedImage((int) widthdist,

                    (int) heightdist, BufferedImage.TYPE_INT_RGB);



            tag.getGraphics().drawImage(

                    src.getScaledInstance(widthdist, heightdist,

                            Image.SCALE_SMOOTH), 0, 0, null);



            FileOutputStream out = new FileOutputStream(imgdist);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            encoder.encode(tag);

            out.close();



        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }


    /**

     * ��ȡͼƬ���

     *

     * @param file

     *            ͼƬ�ļ�

     * @return ���

     */

    public static int[] getImgWidth(File file) {

        InputStream is = null;

        BufferedImage src = null;


        int result[] = { 0, 0 };
        try {

            is = new FileInputStream(file);

            src = javax.imageio.ImageIO.read(is);

            result[0] = src.getWidth(null); // �õ�Դͼ��

            result[1] = src.getHeight(null); // �õ�Դͼ��

            is.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }


}