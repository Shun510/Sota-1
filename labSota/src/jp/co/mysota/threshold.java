package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class thresholdTest {
	public static void main(String args[]){
		System.out.println("start");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Highgui.imread("C:/Users/makino/Pictures/hand.jpg");		// 入力画像の取得
		Mat dstImg = new Mat();
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(img,dstImg,80,255,Imgproc.THRESH_BINARY);
		Highgui.imwrite("C:/Users/makino/Pictures/binaryHand.jpg", dstImg);												// 画像の出力
		System.out.println("finish");
	}
}