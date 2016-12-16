package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class yccTest {
	public static void main(String args[]){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat im = Highgui.imread("C:/Users/makino/Pictures/hand3.jpg");				// 入力画像の取得
		Mat ycc = new Mat();
		Mat mask = new Mat();
		Mat im2 = new Mat();
		Imgproc.cvtColor(im, ycc, Imgproc.COLOR_RGB2YCrCb);						// HSV色空間に変換
		Core.inRange(ycc, new Scalar(0,77,133), new Scalar(255,127,173), mask);	// 緑色領域のマスク作成
		im.copyTo(im2, mask);																	// マスクを 用いて入力画像から緑色領域を抽出
		Highgui.imwrite("C:/Users/makino/Pictures/yccHand3.jpg", im2);												// 画像の出力
	}
}