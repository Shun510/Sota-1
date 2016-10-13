import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

System.out.println("Take photo standby...");
String photoCount = TextToSpeechSota.getTTSFile("５、４、３、２、１、",(int)6,(int)13,(int)11);
if(photoCount!=null) CPlayWave.PlayWave(photoCount,true);

time_string = CRobotUtil.getTimeString();
String filepath = "/var/sota/photo/";
filepath += "test";
boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
GlobalVariable.robocam.StillPicture(filepath);

CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();

System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
System.out.println("Processing start...");

String path_in = "/var/sota/photo/test.jpg";
String path_out = "/var/sota/photo/processedTest.jpg";

Mat mat = new Mat();

mat = Imgcodecs.imread(path_in);
Core.putText(mat, "2016/10/13", new Point(1850, 1900), Core.FONT_HERSHEY_SIMPLEX, 3.0, new Scalar(20, 0, 200), 6);
Imgcodecs.imwrite(path_out, mat);
