//このソースは、VstoneMagicによって自動生成されました。
//ソースの内容を書き換えた場合、VstoneMagicで開けなくなる場合があります。
package	jp.co.mysota;
import	main.main.GlobalVariable;
import	jp.vstone.RobotLib.*;
import	jp.vstone.sotatalk.*;
import	jp.vstone.sotatalk.SpeechRecog.*;
import	java.awt.Color;
import	jp.vstone.camera.*;
import	java.io.*;
import	java.time.*;
import	jp.vstone.camera.FaceDetectLib.*;
import	java.util.Random;
import	twitter4j.Status;
import	twitter4j.Twitter;
import	twitter4j.TwitterException;
import	twitter4j.TwitterFactory;
import	twitter4j.User;
import	twitter4j.conf.*;
import	java.nio.file.FileSystems;
import	java.nio.file.FileSystem;
import	java.nio.file.Path;
import	twitter4j.StatusUpdate;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class mymain
{

	public CRobotPose pose;
	public String speechRecogResult;
	public String ocName;
	public String date_string;
	public String readText;
	public RecogResult recogresult;
	public LocalDateTime localDateTime;
	public String time_string;
	public int addFaceuserErrSayInterval;
	public String faceName;
	public String callName;
	public int faceDetectResultSmile;
	public int faceDetectResultAge;
	public int count;
	public long noDetectDuration;
	public long detectDuration;
	public boolean exist;
	public String tweet;
	public long tweetId;
	public String[] news;
	public boolean speakBl;
	public boolean newsBl;
	public boolean poseBl;
	public boolean photoBl;
	public boolean tempBl;
	public CRobotPose defaultPose;
	public CRobotPose leftUpPose;
	public CRobotPose rightUpPose;
	public CRobotPose beShyPose;
	public void tempRegistration()																						//@<BlockInfo>jp.vstone.block.func,32,912,1088,912,False,7,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		GlobalVariable.robocam.setEnableFaceSearch(false);																//@<BlockInfo>jp.vstone.block.facedetect.traking,96,912,1024,912,False,6,顔追従@</BlockInfo>
		GlobalVariable.robocam.setEnableSmileDetect(true);
		GlobalVariable.robocam.setEnableAgeSexDetect(true);

		GlobalVariable.robocam.StartFaceTraking();
		try{
			GlobalVariable.detectCount=0;


																														//@<OutputChild>
			while(GlobalVariable.TRUE)																					//@<BlockInfo>jp.vstone.block.while.endless,160,912,960,912,False,5,Endless@</BlockInfo>
			{

																														//@<OutputChild>
				GlobalVariable.faceresult = GlobalVariable.robocam.getDetectResult();									//@<BlockInfo>jp.vstone.block.facedetect.isdetect,224,864,896,864,False,4,コメント@</BlockInfo>

				if(GlobalVariable.faceresult.isDetect()) GlobalVariable.detectCount++;
				else GlobalVariable.detectCount=0;

				if(GlobalVariable.detectCount>(int)2)
				{
																														//@<OutputChild>
					GlobalVariable.faceuser = GlobalVariable.robocam.getUser(GlobalVariable.faceresult);				//@<BlockInfo>jp.vstone.block.facedetect.user.get2,288,816,832,816,False,3,認識した顔の特徴を取得して、グローバル変数FaceUser faceuserに代入します。また、登録済みのユーザの場合、名前をグローバル変数String facenameに代入します。@</BlockInfo>

					if(GlobalVariable.faceuser != null)
					{
						if(GlobalVariable.faceuser.getName() != null) GlobalVariable.facename = GlobalVariable.faceuser.getName();
						else GlobalVariable.facename="";
						
																														//@<OutputChild>
							if(GlobalVariable.faceuser!=null)																//@<BlockInfo>jp.vstone.block.facedetect.user.add,352,768,768,768,False,2,@</BlockInfo>
							{
								GlobalVariable.faceuser.setName((String)"Aさん");
								int faceuserAddReturnCode = GlobalVariable.robocam.addUserwithErrorCode(GlobalVariable.faceuser);
								boolean isfaceuseradd = (faceuserAddReturnCode==1);
							
								String errMessageVoice=null;
								switch(faceuserAddReturnCode)
								{
									case -100: errMessageVoice="ユーザ情報がありません。";
									break;
									case -201: errMessageVoice="顔が上や下を向いているみたい。まっすぐ前を向いてください";
									break;
									case -202: errMessageVoice="首が傾いているみたい。まっすぐにしてください";
									break;
									case -203: errMessageVoice="顔が横を向いているみたい。まっすぐ前を見てください";
									break;
									case -300: errMessageVoice="もっと近くで顔を見せてください。";
									break;
									case -400: errMessageVoice="顔が良く見えませんでした。しっかり顔を見せてください。";
									break;
									case -500: errMessageVoice="顔が見つかりませんでした。";
									break;
								}
								if(errMessageVoice!=null && ++addFaceuserErrSayInterval == 3)
								{
									addFaceuserErrSayInterval=0;
									String file = TextToSpeechSota.getTTSFile(errMessageVoice);
									if(file!=null) CPlayWave.PlayWave(file,true);
								}
							 
								if(isfaceuseradd==true)
								{
																															//@<OutputChild>
								{																							//@<BlockInfo>jp.vstone.block.talk.tts,416,768,416,768,False,1,動作を伴わず音声合成のみ行います。@</BlockInfo>
									String file = TextToSpeechSota.getTTSFile((String)"OK",(int)11,(int)13,(int)11);
									if(file!=null) CPlayWave.PlayWave(file,true);
								}
																															//@<EndOfBlock/>
																															//@</OutputChild>
							
								}else
								{
																															//@<OutputChild>
																															//@</OutputChild>
							
								}
							
							}
																															//@<EndOfBlock/>
																																//@</OutputChild>

					}
					else
					{
						
																														//@<OutputChild>
																														//@</OutputChild>

					}
																														//@<EndOfBlock/>
																														//@</OutputChild>

				}else
				{
																														//@<OutputChild>
																														//@</OutputChild>

				}
																														//@<EndOfBlock/>
																														//@</OutputChild>
			}
																														//@<EndOfBlock/>
																														//@</OutputChild>


		}finally{
			GlobalVariable.robocam.StopFaceTraking();
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	/*
	{																													//@<BlockInfo>jp.vstone.block.talk.tts,864,1072,864,1072,False,8,動作を伴わず音声合成のみ行います。@</BlockInfo>
		String file = TextToSpeechSota.getTTSFile((String)"ご、よん、さん、に、いち",(int)11,(int)13,(int)11);
		if(file!=null) CPlayWave.PlayWave(file,true);
	}
																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	public void testSpeak()																								//@<BlockInfo>jp.vstone.block.func,32,1328,336,1328,False,10,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		{																												//@<BlockInfo>jp.vstone.block.talk.tts,128,1328,128,1328,False,9,動作を伴わず音声合成のみ行います。@</BlockInfo>
			String file = TextToSpeechSota.getTTSFile((String)"www3.nhk.or.jp/news/html/20160927/k10010708461000.html ",(int)11,(int)13,(int)11);
			if(file!=null) CPlayWave.PlayWave(file,true);
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void imageUploader()																							//@<BlockInfo>jp.vstone.block.func,32,1168,1136,1168,False,23,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		time_string = CRobotUtil.getTimeString();																		//@<BlockInfo>jp.vstone.block.time.gettime,96,1168,96,1168,False,22,ロボット内のカレンダーより現在時刻を文字列で取得し、変数String time_stringに代入。@</BlockInfo>
																														//@<EndOfBlock/>
		GlobalVariable.robocam.setEnableFaceSearch(false);																//@<BlockInfo>jp.vstone.block.facedetect.traking,160,1168,800,1168,False,21,顔追従@</BlockInfo>
		GlobalVariable.robocam.setEnableSmileDetect(true);
		GlobalVariable.robocam.setEnableAgeSexDetect(true);

		GlobalVariable.robocam.StartFaceTraking();
		try{
			GlobalVariable.detectCount=0;


																														//@<OutputChild>
			while(GlobalVariable.TRUE)																					//@<BlockInfo>jp.vstone.block.while.endless,224,1168,736,1168,False,17,Endless@</BlockInfo>
			{

																														//@<OutputChild>
				GlobalVariable.faceresult = GlobalVariable.robocam.getDetectResult();									//@<BlockInfo>jp.vstone.block.facedetect.isdetect,288,1120,672,1120,False,16,コメント@</BlockInfo>

				if(GlobalVariable.faceresult.isDetect()) GlobalVariable.detectCount++;
				else GlobalVariable.detectCount=0;

				if(GlobalVariable.detectCount>(int)2)
				{
																														//@<OutputChild>
					GlobalVariable.faceuser = GlobalVariable.robocam.getUser(GlobalVariable.faceresult);				//@<BlockInfo>jp.vstone.block.facedetect.user.get2,352,1072,608,1072,False,15,認識した顔の特徴を取得して、グローバル変数FaceUser faceuserに代入します。また、登録済みのユーザの場合、名前をグローバル変数String facenameに代入します。@</BlockInfo>

					if(GlobalVariable.faceuser != null)
					{
						if(GlobalVariable.faceuser.getName() != null) GlobalVariable.facename = GlobalVariable.faceuser.getName();
						else GlobalVariable.facename="";
						
																														//@<OutputChild>
							callName=(String)GlobalVariable.facename;														//@<BlockInfo>jp.vstone.block.calculater,416,1072,416,1072,False,13,@</BlockInfo>
																															//@<EndOfBlock/>
							System.out.println(callName + "を識別しました。");														//@<BlockInfo>jp.vstone.block.freeproc,480,1072,480,1072,False,12,@</BlockInfo>
																															//@<EndOfBlock/>
							break;																							//@<BlockInfo>jp.vstone.block.break,544,1072,544,1072,False,11,break@</BlockInfo>	@<EndOfBlock/>
																																//@</OutputChild>

					}
					else
					{
						
																														//@<OutputChild>
							System.out.println("識別できませんでした。");																//@<BlockInfo>jp.vstone.block.freeproc,480,1168,480,1168,False,14,@</BlockInfo>
																															//@<EndOfBlock/>
																																//@</OutputChild>

					}
																														//@<EndOfBlock/>
																														//@</OutputChild>

				}else
				{
																														//@<OutputChild>
																														//@</OutputChild>

				}
																														//@<EndOfBlock/>
																														//@</OutputChild>
			}
																														//@<EndOfBlock/>
																														//@</OutputChild>


		}finally{
			GlobalVariable.robocam.StopFaceTraking();
		}
																														//@<EndOfBlock/>
		{																												//@<BlockInfo>jp.vstone.block.facedetect.stillpicture,928,1168,928,1168,False,20,still@</BlockInfo>
			String filepath = "/var/sota/photo/";
			filepath += (String)"image";
			boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
			if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
			GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
			GlobalVariable.robocam.StillPicture(filepath);

			CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
			if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();
		}																												//@<EndOfBlock/>
		CRobotUtil.wait((int)5000);																						//@<BlockInfo>jp.vstone.block.wait,992,1168,992,1168,False,19,コメント@</BlockInfo>	@<EndOfBlock/>
		try{																											//@<BlockInfo>jp.vstone.block.freeproc,1056,1168,1056,1168,False,18,@</BlockInfo>
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
		  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
		  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
		  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();
		User user = tw.verifyCredentials();

		time_string = CRobotUtil.getTimeString();
		FileSystem fs = FileSystems.getDefault();
		        Path path = fs.getPath("/var/sota/photo/image.jpg");
		        File file = path.toFile();
		        
		        
		        Status status = tw.updateStatus(
		            new StatusUpdate("[" + time_string + "]" + "Posted from Sota.").media(file));
		}catch(TwitterException te){
		System.out.println(te);
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void tweetSpeak()																							//@<BlockInfo>jp.vstone.block.func,32,1552,160,1552,False,24,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void newsReader()																							//@<BlockInfo>jp.vstone.block.func,32,1664,160,1664,False,26,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
																														//@<BlockInfo>jp.vstone.block.freeproc,96,1664,96,1664,False,25,@</BlockInfo>
		try{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
		  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
		  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
		  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();

		while(true){
		User user = tw.showUser("@nhk_news");
		CRobotUtil.wait((int)5000);

		Status status = user.getStatus();

		if(tweetId != status.getId()){
		tweetId = status.getId();
		tweet = status.getText();
		String[] news = tweet.split("#", -1);

		System.out.println("新しいニュースを見つけました。");
		System.out.println(news[0]);

		{
		String file = TextToSpeechSota.getTTSFile("新しいニュースです。。。" + news[0],(int)11,(int)13,(int)11);
		if(file!=null) CPlayWave.PlayWave(file,true);
		}

		}else{
		System.out.println("新しいニュースは見つかりませんでした。");
		}
		}

		}catch(TwitterException te){
		System.out.println(te);
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	/*
	User user = tw.showUser("@IPLSota");																				//@<BlockInfo>jp.vstone.block.freeproc,96,1552,96,1552,False,27,@</BlockInfo>
	Status status = user.getStatus();

	if(tweetId != status.getId()){
	tweetId = status.getId();
	tweet = status.getText();
	System.out.println("新しいツイートを見つけました。");
	System.out.println(tweet);

	String file = TextToSpeechSota.getTTSFile(tweet,(int)11,(int)13,(int)11);
	if(file!=null) CPlayWave.PlayWave(file,true);

	}else{
	System.out.println("新しいツイートは見つかりませんでした。");
	}
																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	/*
	time_string = CRobotUtil.getTimeString();																			//@<BlockInfo>jp.vstone.block.time.gettime,592,1680,592,1680,False,28,ロボット内のカレンダーより現在時刻を文字列で取得し、変数String time_stringに代入。@</BlockInfo>
																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	public mymain()																										//@<BlockInfo>jp.vstone.block.func.constructor,32,32,864,192,False,58,@</BlockInfo>
	{
																														//@<OutputChild>
		/*CRobotPose pose*/;																							//@<BlockInfo>jp.vstone.block.variable,96,32,96,32,False,57,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String speechRecogResult*/;																					//@<BlockInfo>jp.vstone.block.variable,160,32,160,32,False,56,break@</BlockInfo>
																														//@<EndOfBlock/>
		ocName=null;																									//@<BlockInfo>jp.vstone.block.variable,224,32,224,32,False,55,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String date_string*/;																							//@<BlockInfo>jp.vstone.block.variable,288,32,288,32,False,54,break@</BlockInfo>
																														//@<EndOfBlock/>
		readText="";																									//@<BlockInfo>jp.vstone.block.variable,352,32,352,32,False,53,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*RecogResult recogresult*/;																					//@<BlockInfo>jp.vstone.block.variable,416,32,416,32,False,52,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*LocalDateTime localDateTime*/;																				//@<BlockInfo>jp.vstone.block.variable,480,32,480,32,False,51,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String time_string*/;																							//@<BlockInfo>jp.vstone.block.variable,544,32,544,32,False,50,break@</BlockInfo>
																														//@<EndOfBlock/>
		addFaceuserErrSayInterval=0;																					//@<BlockInfo>jp.vstone.block.variable,608,32,608,32,False,49,break@</BlockInfo>
																														//@<EndOfBlock/>
		faceName=null;																									//@<BlockInfo>jp.vstone.block.variable,96,112,96,112,False,48,break@</BlockInfo>
																														//@<EndOfBlock/>
		callName=null;																									//@<BlockInfo>jp.vstone.block.variable,160,112,160,112,False,47,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*int faceDetectResultSmile*/;																					//@<BlockInfo>jp.vstone.block.variable,224,112,224,112,False,46,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*int faceDetectResultAge*/;																					//@<BlockInfo>jp.vstone.block.variable,288,112,288,112,False,45,break@</BlockInfo>
																														//@<EndOfBlock/>
		count=0;																										//@<BlockInfo>jp.vstone.block.variable,352,112,352,112,False,44,break@</BlockInfo>
																														//@<EndOfBlock/>
		noDetectDuration=0;																								//@<BlockInfo>jp.vstone.block.variable,416,112,416,112,False,43,break@</BlockInfo>
																														//@<EndOfBlock/>
		detectDuration=0;																								//@<BlockInfo>jp.vstone.block.variable,480,112,480,112,False,42,break@</BlockInfo>
																														//@<EndOfBlock/>
		exist=true;																										//@<BlockInfo>jp.vstone.block.variable,544,112,544,112,False,41,break@</BlockInfo>
																														//@<EndOfBlock/>
		tweet="null";																									//@<BlockInfo>jp.vstone.block.variable,608,112,608,112,False,40,break@</BlockInfo>
																														//@<EndOfBlock/>
		tweetId=0;																										//@<BlockInfo>jp.vstone.block.variable,112,192,112,192,False,39,break@</BlockInfo>
																														//@<EndOfBlock/>
		news=null;																										//@<BlockInfo>jp.vstone.block.variable,176,192,176,192,False,38,break@</BlockInfo>
																														//@<EndOfBlock/>
		speakBl=true;																									//@<BlockInfo>jp.vstone.block.variable,256,192,256,192,False,37,break@</BlockInfo>
																														//@<EndOfBlock/>
		newsBl=false;																									//@<BlockInfo>jp.vstone.block.variable,320,192,320,192,False,36,break@</BlockInfo>
																														//@<EndOfBlock/>
		poseBl=false;																									//@<BlockInfo>jp.vstone.block.variable,384,192,384,192,False,35,break@</BlockInfo>
																														//@<EndOfBlock/>
		photoBl=false;																									//@<BlockInfo>jp.vstone.block.variable,448,192,448,192,False,34,break@</BlockInfo>
																														//@<EndOfBlock/>
		tempBl=false;																									//@<BlockInfo>jp.vstone.block.variable,512,192,512,192,False,33,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose defaultPose*/;																						//@<BlockInfo>jp.vstone.block.variable,608,192,608,192,False,32,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose leftUpPose*/;																						//@<BlockInfo>jp.vstone.block.variable,672,192,672,192,False,31,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose rightUpPose*/;																						//@<BlockInfo>jp.vstone.block.variable,736,192,736,192,False,30,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose beShyPose*/;																						//@<BlockInfo>jp.vstone.block.variable,800,192,800,192,False,29,break@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void pose()																									//@<BlockInfo>jp.vstone.block.func,32,1440,160,1440,False,60,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		pose = new CRobotPose();																						//@<BlockInfo>jp.vstone.block.pose,96,1440,96,1440,False,59,コメント@</BlockInfo>
		pose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{0,330,0,900,0,0,0,0}
						);
		pose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
						new Short[]{100,100,100,100,100,100,100,100}
						);
		pose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
						new Short[]{0,-255,0,180,80,0,180,80,0}
						);
		GlobalVariable.motion.play(pose,1400);
		CRobotUtil.wait(1400);																							//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	/*
	{																													//@<BlockInfo>jp.vstone.block.talk.tts,320,1456,320,1456,False,61,動作を伴わず音声合成のみ行います。@</BlockInfo>
		String file = TextToSpeechSota.getTTSFile((String)"こんにちは。",(int)11,(int)13,(int)11);
		if(file!=null) CPlayWave.PlayWave(file,true);
	}
																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	public void Runner()																								//@<BlockInfo>jp.vstone.block.func,496,1440,624,1440,False,63,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		try{																											//@<BlockInfo>jp.vstone.block.freeproc,560,1440,560,1440,False,62,@</BlockInfo>
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
		  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
		  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
		  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();

		defaultPose = new CRobotPose();
				defaultPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{0,-900,0,900,0,0,0,0}
								);
				defaultPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{100,100,100,100,100,100,100,100}
								);
				defaultPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
								new Short[]{0,-255,0,180,80,0,180,80,0}
								);
			leftUpPose = new CRobotPose();
				leftUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{0,500,0,900,0,0,0,0}
								);
				leftUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{100,100,100,100,100,100,100,100}
								);
				leftUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
								new Short[]{0,-255,0,180,80,0,180,80,0}
								);
			rightUpPose = new CRobotPose();
				rightUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{0,-900,0,-500,0,0,0,0}
								);
				rightUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{100,100,100,100,100,100,100,100}
								);
				rightUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
								new Short[]{0,-255,0,180,80,0,180,80,0}
								);
			beShyPose = new CRobotPose();
				beShyPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{-15,-14,-900,14,900,2,50,-2}
								);
				beShyPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
								new Short[]{100,100,100,100,100,100,100,100}
								);
				beShyPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
								new Short[]{0,-255,0,140,30,30,140,30,30}
								);

		while(true){

			User hostAccount = tw.showUser("@IPLSota");
			User newsAccount = tw.showUser("@nhk_news");





		if(tempBl){
			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();

			System.out.println("tempmode...please tweet task");

			if(tweet.equals("speak")){
				speakBl = true;
				tempBl = false;
			}else if(tweet.equals("news")){
				newsBl = true;
				tempBl = false;
			}else if(tweet.equals("pose")){
				poseBl = true;
				tempBl = false;
			}else if(tweet.equals("photo")){

			}
		}
		if(speakBl){

			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();

			if(tweet.equals("speak")){
				speakBl = true;
			}else if(tweet.equals("news")){
				newsBl = true;
				speakBl = false;
			}else if(tweet.equals("pose")){
				poseBl = true;
				speakBl = false;
			}else if(tweet.equals("photo")){
				photoBl = true;
				speakBl = false;
			}

			CRobotUtil.wait((int)10000);

			switch(tweet){
			case "news":
			case "pose":
			case "photo":
				break;

			default:
			if(tweetId != hostStatus.getId()){
			tweetId = hostStatus.getId();
			System.out.println("Sota found new tweet");
			String file = TextToSpeechSota.getTTSFile(tweet,(int)11,(int)13,(int)11);
			if(file!=null) CPlayWave.PlayWave(file,true);
			}else{
			System.out.println("Sota could't find new tweet");
			}
			}
		}

		if(newsBl){

			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();

			if(tweet.equals("news")){
				newsBl = true;
			}else if(tweet.equals("speak")){
				speakBl = true;
				newsBl = false;

			}else if(tweet.equals("pose")){
				poseBl = true;
				newsBl = false;

			}else if(tweet.equals("photo")){
				photoBl = true;
				newsBl = false;

			}

			CRobotUtil.wait((int)10000);

			switch(tweet){
			case "speak":
			case "pose":
			case "photo":
				break;

			default:
			Status newsStatus = newsAccount.getStatus();
			tweet = newsStatus.getText();
			String[] news = tweet.split("#", -1);
			if(tweetId != newsStatus.getId()){
			tweetId = newsStatus.getId();
			System.out.println("Sota found new news");
			String file = TextToSpeechSota.getTTSFile(news[0],(int)11,(int)13,(int)11);
			if(file!=null) CPlayWave.PlayWave(file,true);
			}else{
			System.out.println("Sota could't find new news");
			}
			}
		}

		if(poseBl){

			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();

			if(tweet.equals("pose")){
				poseBl = true;
			}else if(tweet.equals("speak")){
				speakBl = true;
				poseBl = false;

			}else if(tweet.equals("news")){
				newsBl = true;
				poseBl = false;

			}else if(tweet.equals("photo")){
				photoBl = true;
				poseBl = false;

			}

			CRobotUtil.wait((int)10000);

			switch(tweet){
			case "speak":
			case "news":
			case "photo":
				break;

			default:
			hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();
			if(tweetId != hostStatus.getId()){
			tweetId = hostStatus.getId();
			switch(tweet){
				case "まっすぐ":
				case "デフォルト":
				case "default":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(defaultPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "ひだり":
				case "左":
				case "left":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(leftUpPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "みぎ":
				case "右":
				case "right":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(rightUpPose,1000);
					CRobotUtil.wait(1000);
					break;
				case "てれる":
				case "照れる":
					System.out.println("Sota found new action");
					GlobalVariable.motion.play(beShyPose,1000);
					CRobotUtil.wait(1000);
					break;
				default:
				System.out.println("Sota could't find new action");
			}
			}else{
			System.out.println("Sota could't find new action");
			}
			}
		}

		if(photoBl){
			GlobalVariable.motion.play(defaultPose,1000);
			CRobotUtil.wait((int)1000);

			System.out.println("Take photo standby...");
			String photoCount = TextToSpeechSota.getTTSFile("５、４、３、２、１、",(int)6,(int)13,(int)11);
			if(photoCount!=null) CPlayWave.PlayWave(photoCount,true);

			time_string = CRobotUtil.getTimeString();
			String filepath = "/var/sota/photo/";
			filepath += "photo" + " at " + "[" + time_string + "]";
			boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
			if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
			GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
			GlobalVariable.robocam.StillPicture(filepath);

			CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
			if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();

			photoBl = false;
			tempBl = true;

			}
		}
		}catch(TwitterException te){
		System.out.println(te);
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void likeVideo()																								//@<BlockInfo>jp.vstone.block.func,32,320,656,320,False,65,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_QVGA, CameraCapture.CAP_FORMAT_MJPG));	//@<BlockInfo>jp.vstone.block.freeproc,304,320,304,320,False,64,@</BlockInfo>
				while(true){
							{
								String filepath = "/var/sota/photo/";
								filepath += (String)"abc";
								boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
								if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
								GlobalVariable.robocam.StillPicture(filepath);
								CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
								if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();
							}
						}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void workingChecker()																						//@<BlockInfo>jp.vstone.block.func,32,528,1840,528,False,89,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		time_string = CRobotUtil.getTimeString();																		//@<BlockInfo>jp.vstone.block.time.gettime,96,528,96,528,False,88,ロボット内のカレンダーより現在時刻を文字列で取得し、変数String time_stringに代入。@</BlockInfo>
																														//@<EndOfBlock/>
		GlobalVariable.robocam.setEnableFaceSearch(false);																//@<BlockInfo>jp.vstone.block.facedetect.traking,336,528,1776,528,False,87,顔追従@</BlockInfo>
		GlobalVariable.robocam.setEnableSmileDetect(true);
		GlobalVariable.robocam.setEnableAgeSexDetect(true);

		GlobalVariable.robocam.StartFaceTraking();
		try{
			GlobalVariable.detectCount=0;


																														//@<OutputChild>
			while(GlobalVariable.TRUE)																					//@<BlockInfo>jp.vstone.block.while.endless,400,528,912,528,False,86,Endless@</BlockInfo>
			{

																														//@<OutputChild>
				GlobalVariable.faceresult = GlobalVariable.robocam.getDetectResult();									//@<BlockInfo>jp.vstone.block.facedetect.isdetect,464,480,848,480,False,71,コメント@</BlockInfo>

				if(GlobalVariable.faceresult.isDetect()) GlobalVariable.detectCount++;
				else GlobalVariable.detectCount=0;

				if(GlobalVariable.detectCount>(int)2)
				{
																														//@<OutputChild>
					GlobalVariable.faceuser = GlobalVariable.robocam.getUser(GlobalVariable.faceresult);				//@<BlockInfo>jp.vstone.block.facedetect.user.get2,528,432,784,432,False,70,認識した顔の特徴を取得して、グローバル変数FaceUser faceuserに代入します。また、登録済みのユーザの場合、名前をグローバル変数String facenameに代入します。@</BlockInfo>

					if(GlobalVariable.faceuser != null)
					{
						if(GlobalVariable.faceuser.getName() != null) GlobalVariable.facename = GlobalVariable.faceuser.getName();
						else GlobalVariable.facename="";
						
																														//@<OutputChild>
							callName=(String)GlobalVariable.facename;														//@<BlockInfo>jp.vstone.block.calculater,592,432,592,432,False,68,@</BlockInfo>
																															//@<EndOfBlock/>
							System.out.println(callName + "を識別しました。");														//@<BlockInfo>jp.vstone.block.freeproc,656,432,656,432,False,67,@</BlockInfo>
							
							try{
							ConfigurationBuilder cb = new ConfigurationBuilder();
							cb.setDebugEnabled(true)
							  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
							  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
							  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
							  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");
							
							TwitterFactory tf = new TwitterFactory(cb.build());
							
							Twitter tw = tf.getInstance();
							User user = tw.verifyCredentials();
							
							tw.updateStatus("[" + time_string + "]" + callName + "は研究室で作業を始めました。");
							}catch(TwitterException te){
							System.out.println(te);
							}
																															//@<EndOfBlock/>
							break;																							//@<BlockInfo>jp.vstone.block.break,720,432,720,432,False,66,break@</BlockInfo>	@<EndOfBlock/>
																																//@</OutputChild>

					}
					else
					{
						
																														//@<OutputChild>
							System.out.println("識別できませんでした。");																//@<BlockInfo>jp.vstone.block.freeproc,656,528,656,528,False,69,@</BlockInfo>
																															//@<EndOfBlock/>
																																//@</OutputChild>

					}
																														//@<EndOfBlock/>
																														//@</OutputChild>

				}else
				{
																														//@<OutputChild>
																														//@</OutputChild>

				}
																														//@<EndOfBlock/>
																														//@</OutputChild>
			}
																														//@<EndOfBlock/>
			while(GlobalVariable.TRUE)																					//@<BlockInfo>jp.vstone.block.while.endless,1024,528,1712,528,False,85,Endless@</BlockInfo>
			{

																														//@<OutputChild>
				if(exist==false)																						//@<BlockInfo>jp.vstone.block.if,1088,480,1648,480,False,84,コメント@</BlockInfo>
				{
																														//@<OutputChild>
					detectDuration = GlobalVariable.robocam.getDetectDuration();										//@<BlockInfo>jp.vstone.block.facedetect.detectduration.get,1152,480,1152,480,False,77,顔が見えている場合、その累積時間を変数long detectDurationに返す。@</BlockInfo>	@<EndOfBlock/>
					CRobotUtil.wait((int)1000);																			//@<BlockInfo>jp.vstone.block.wait,1216,480,1216,480,False,76,コメント@</BlockInfo>	@<EndOfBlock/>
					if(detectDuration == 0){																			//@<BlockInfo>jp.vstone.block.freeproc,1280,480,1280,480,False,75,@</BlockInfo>
					System.out.println("いません");
					}else{
					System.out.println("みえてるじかん： " + detectDuration + "ms" );
					}
																														//@<EndOfBlock/>
					if(detectDuration>=30000)																			//@<BlockInfo>jp.vstone.block.if,1376,384,1568,384,False,74,コメント@</BlockInfo>
					{
																														//@<OutputChild>
						try{																							//@<BlockInfo>jp.vstone.block.freeproc,1440,384,1440,384,False,73,@</BlockInfo>
						ConfigurationBuilder cb = new ConfigurationBuilder();
						cb.setDebugEnabled(true)
						  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
						  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
						  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
						  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

						TwitterFactory tf = new TwitterFactory(cb.build());

						Twitter tw = tf.getInstance();
						User user = tw.verifyCredentials();

						time_string = CRobotUtil.getTimeString();
						tw.updateStatus("[" + time_string + "]" + callName + "は研究室で作業を再開しました。");
						}catch(TwitterException te){
						System.out.println(te);
						}
																														//@<EndOfBlock/>
						exist=(boolean)true;																			//@<BlockInfo>jp.vstone.block.calculater,1504,384,1504,384,False,72,@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
					}
					else
					{
																														//@<OutputChild>
																														//@</OutputChild>
					}
																														//@<EndOfBlock/>
																														//@</OutputChild>
				}
				else
				{
																														//@<OutputChild>
					noDetectDuration = GlobalVariable.robocam.getNotDetectDuration();									//@<BlockInfo>jp.vstone.block.facedetect.nodetectduration.get,1152,576,1152,576,False,83,顔が見えていない場合、その累積時間を変数long noDetectDurationに返す。@</BlockInfo>	@<EndOfBlock/>
					CRobotUtil.wait((int)1000);																			//@<BlockInfo>jp.vstone.block.wait,1216,576,1216,576,False,82,コメント@</BlockInfo>	@<EndOfBlock/>
					if(noDetectDuration == 0){																			//@<BlockInfo>jp.vstone.block.freeproc,1280,576,1280,576,False,81,@</BlockInfo>
					System.out.println("みえてます");
					}else{
					System.out.println("みえてないじかん： " + noDetectDuration + "ms" );
					}
																														//@<EndOfBlock/>
					if(noDetectDuration>=30000)																			//@<BlockInfo>jp.vstone.block.if,1376,576,1568,576,False,80,コメント@</BlockInfo>
					{
																														//@<OutputChild>
						try{																							//@<BlockInfo>jp.vstone.block.freeproc,1440,576,1440,576,False,79,@</BlockInfo>
						ConfigurationBuilder cb = new ConfigurationBuilder();
						cb.setDebugEnabled(true)
						  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
						  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
						  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
						  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

						TwitterFactory tf = new TwitterFactory(cb.build());

						Twitter tw = tf.getInstance();
						User user = tw.verifyCredentials();

						time_string = CRobotUtil.getTimeString();
						tw.updateStatus("[" + time_string + "]" + callName + "は離席中です。");
						}catch(TwitterException te){
						System.out.println(te);
						}
																														//@<EndOfBlock/>
						exist=(boolean)false;																			//@<BlockInfo>jp.vstone.block.calculater,1504,576,1504,576,False,78,@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
					}
					else
					{
																														//@<OutputChild>
																														//@</OutputChild>
					}
																														//@<EndOfBlock/>
																														//@</OutputChild>
				}
																														//@<EndOfBlock/>
																														//@</OutputChild>
			}
																														//@<EndOfBlock/>
																														//@</OutputChild>


		}finally{
			GlobalVariable.robocam.StopFaceTraking();
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	/*
	{																													//@<BlockInfo>jp.vstone.block.talk.tts,816,1440,816,1440,False,90,動作を伴わず音声合成のみ行います。@</BlockInfo>
		String file = TextToSpeechSota.getTTSFile((String)"こんにちは。",(int)11,(int)13,(int)11);
		if(file!=null) CPlayWave.PlayWave(file,true);
	}
																														//@<EndOfBlock/>

	*/

	//@<Separate/>
	public void photo()																									//@<BlockInfo>jp.vstone.block.func,496,1568,624,1568,False,91,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		System.out.println("Take photo standby...");																	//@<BlockInfo>jp.vstone.block.freeproc,560,1568,560,1568,False,92,@</BlockInfo>
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
		Imgproc.putText(mat, "2016/10/13", new Point(1850, 1900), Core.FONT_HERSHEY_SIMPLEX, 3.0, new Scalar(20, 0, 200), 6);
		Imgcodecs.imwrite(path_out, mat);
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

}
