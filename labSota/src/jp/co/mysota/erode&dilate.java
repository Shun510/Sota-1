package opencv;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class erodeTest {
    public static void main(String[] args) {
    	System.out.println("start");

    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    	int kernel = 3;

        String path_in = "C:/Users/makino/Pictures/yccHand3(binary).jpg";
        String path_out = "C:/Users/makino/Pictures/yccHand3(EXbinary).jpg";

        Mat mat_src = new Mat();

        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(kernel, kernel));

        mat_src = Highgui.imread(path_in);                          // 入力画像の読み込み
        for(int i=0; i<2 ; i++){
        Imgproc.erode(mat_src, mat_src, element);
        System.out.println("erode:" + i);
        }
        for(int i=0; i<2 ; i++){
        Imgproc.dilate(mat_src, mat_src, element);
        System.out.println("dilate:" + i);
        }
        

        Highgui.imwrite(path_out, mat_src);                         // 出力画像を保存]]
        System.out.println("finish");
    }

}