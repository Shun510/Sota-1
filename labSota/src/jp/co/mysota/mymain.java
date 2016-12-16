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
import	org.opencv.core.Core;
import	org.opencv.core.Mat;
import	org.opencv.core.Point;
import	org.opencv.core.Scalar;
import	org.opencv.core.Size;
import	org.opencv.imgcodecs.Imgcodecs;
import	org.opencv.imgproc.Imgproc;

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
	public String name;
	public int faceDetectResultSmile;
	public int faceDetectResultAge;
	public int count;
	public long noDetectDuration;
	public long detectDuration;
	public boolean exist;
	public String tweet;
	public long tweetId;
	public String[] news;
	public boolean talkBl;
	public boolean newsBl;
	public boolean poseBl;
	public boolean photoBl;
	public boolean tempBl;
	public CRobotPose defaultPose;
	public CRobotPose leftUpPose;
	public CRobotPose rightUpPose;
	public CRobotPose beShyPose;
	public int getDateTimeElement;
	public String dateRecord;
	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public String folderDate;
	public String path_out;
	public boolean photoOptionBl;
	public int loop;
	public int failCount;
	public boolean bodyTemp1;
	public boolean bodyTemp2;
	public Double motiondetectresult;
	public boolean getUp;
	public String announce;
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
	public void block()																									//@<BlockInfo>jp.vstone.block.func,16,1280,528,1280,False,13,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		GlobalVariable.robocam.StartMotionDetection();																	//@<BlockInfo>jp.vstone.block.facedetect.motiondetect,80,1280,464,1280,False,12,動体検出を開始します。@</BlockInfo>
		try{


																														//@<OutputChild>
			while(GlobalVariable.TRUE)																					//@<BlockInfo>jp.vstone.block.while.endless,144,1280,400,1280,False,11,Endless@</BlockInfo>
			{

																														//@<OutputChild>
				motiondetectresult = GlobalVariable.robocam.getMotionDetectResult();									//@<BlockInfo>jp.vstone.block.facedetect.detectmovingobject.get,208,1280,208,1280,False,10,動体検知を行っている場合、最新の検出値を取得し、変数(Double)motiondetectresultに代入@</BlockInfo>
				if(motiondetectresult == null) motiondetectresult = 0.0;												//@<EndOfBlock/>
				CRobotUtil.wait((int)200);																				//@<BlockInfo>jp.vstone.block.wait,272,1280,272,1280,False,9,コメント@</BlockInfo>	@<EndOfBlock/>
				System.out.println(motiondetectresult);																	//@<BlockInfo>jp.vstone.block.freeproc,336,1280,336,1280,False,8,@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
			}
																														//@<EndOfBlock/>
																														//@</OutputChild>


		}finally{
			  GlobalVariable.robocam.StopMotionDetection();
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

	//@<Separate/>
	public mymain()																										//@<BlockInfo>jp.vstone.block.func.constructor,32,32,1024,352,False,60,@</BlockInfo>
	{
																														//@<OutputChild>
		/*CRobotPose pose*/;																							//@<BlockInfo>jp.vstone.block.variable,96,32,96,32,False,59,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String speechRecogResult*/;																					//@<BlockInfo>jp.vstone.block.variable,160,32,160,32,False,58,break@</BlockInfo>
																														//@<EndOfBlock/>
		ocName=null;																									//@<BlockInfo>jp.vstone.block.variable,224,32,224,32,False,57,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String date_string*/;																							//@<BlockInfo>jp.vstone.block.variable,288,32,288,32,False,56,break@</BlockInfo>
																														//@<EndOfBlock/>
		readText="";																									//@<BlockInfo>jp.vstone.block.variable,352,32,352,32,False,55,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*RecogResult recogresult*/;																					//@<BlockInfo>jp.vstone.block.variable,416,32,416,32,False,54,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*LocalDateTime localDateTime*/;																				//@<BlockInfo>jp.vstone.block.variable,480,32,480,32,False,53,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*String time_string*/;																							//@<BlockInfo>jp.vstone.block.variable,544,32,544,32,False,52,break@</BlockInfo>
																														//@<EndOfBlock/>
		addFaceuserErrSayInterval=0;																					//@<BlockInfo>jp.vstone.block.variable,608,32,608,32,False,51,break@</BlockInfo>
																														//@<EndOfBlock/>
		faceName=null;																									//@<BlockInfo>jp.vstone.block.variable,96,112,96,112,False,50,break@</BlockInfo>
																														//@<EndOfBlock/>
		name=null;																										//@<BlockInfo>jp.vstone.block.variable,160,112,160,112,False,49,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*int faceDetectResultSmile*/;																					//@<BlockInfo>jp.vstone.block.variable,224,112,224,112,False,48,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*int faceDetectResultAge*/;																					//@<BlockInfo>jp.vstone.block.variable,288,112,288,112,False,47,break@</BlockInfo>
																														//@<EndOfBlock/>
		count=0;																										//@<BlockInfo>jp.vstone.block.variable,352,112,352,112,False,46,break@</BlockInfo>
																														//@<EndOfBlock/>
		noDetectDuration=0;																								//@<BlockInfo>jp.vstone.block.variable,416,112,416,112,False,45,break@</BlockInfo>
																														//@<EndOfBlock/>
		detectDuration=0;																								//@<BlockInfo>jp.vstone.block.variable,480,112,480,112,False,44,break@</BlockInfo>
																														//@<EndOfBlock/>
		exist=true;																										//@<BlockInfo>jp.vstone.block.variable,544,112,544,112,False,43,break@</BlockInfo>
																														//@<EndOfBlock/>
		tweet="null";																									//@<BlockInfo>jp.vstone.block.variable,608,112,608,112,False,42,break@</BlockInfo>
																														//@<EndOfBlock/>
		tweetId=0;																										//@<BlockInfo>jp.vstone.block.variable,112,192,112,192,False,41,break@</BlockInfo>
																														//@<EndOfBlock/>
		news=null;																										//@<BlockInfo>jp.vstone.block.variable,176,192,176,192,False,40,break@</BlockInfo>
																														//@<EndOfBlock/>
		talkBl=true;																									//@<BlockInfo>jp.vstone.block.variable,256,192,256,192,False,39,break@</BlockInfo>
																														//@<EndOfBlock/>
		newsBl=false;																									//@<BlockInfo>jp.vstone.block.variable,320,192,320,192,False,38,break@</BlockInfo>
																														//@<EndOfBlock/>
		poseBl=false;																									//@<BlockInfo>jp.vstone.block.variable,384,192,384,192,False,37,break@</BlockInfo>
																														//@<EndOfBlock/>
		photoBl=false;																									//@<BlockInfo>jp.vstone.block.variable,448,192,448,192,False,36,break@</BlockInfo>
																														//@<EndOfBlock/>
		tempBl=false;																									//@<BlockInfo>jp.vstone.block.variable,512,192,512,192,False,35,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose defaultPose*/;																						//@<BlockInfo>jp.vstone.block.variable,608,192,608,192,False,34,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose leftUpPose*/;																						//@<BlockInfo>jp.vstone.block.variable,672,192,672,192,False,33,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose rightUpPose*/;																						//@<BlockInfo>jp.vstone.block.variable,736,192,736,192,False,32,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose beShyPose*/;																						//@<BlockInfo>jp.vstone.block.variable,800,192,800,192,False,31,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*int getDateTimeElement*/;																						//@<BlockInfo>jp.vstone.block.variable,864,192,864,192,False,30,break@</BlockInfo>
																														//@<EndOfBlock/>
		dateRecord="null";																								//@<BlockInfo>jp.vstone.block.variable,928,192,928,192,False,29,break@</BlockInfo>
																														//@<EndOfBlock/>
		year=0;																											//@<BlockInfo>jp.vstone.block.variable,144,272,144,272,False,28,break@</BlockInfo>
																														//@<EndOfBlock/>
		month=0;																										//@<BlockInfo>jp.vstone.block.variable,208,272,208,272,False,27,break@</BlockInfo>
																														//@<EndOfBlock/>
		day=0;																											//@<BlockInfo>jp.vstone.block.variable,272,272,272,272,False,26,break@</BlockInfo>
																														//@<EndOfBlock/>
		hour=0;																											//@<BlockInfo>jp.vstone.block.variable,336,272,336,272,False,25,break@</BlockInfo>
																														//@<EndOfBlock/>
		minute=0;																										//@<BlockInfo>jp.vstone.block.variable,400,272,400,272,False,24,break@</BlockInfo>
																														//@<EndOfBlock/>
		folderDate="";																									//@<BlockInfo>jp.vstone.block.variable,480,272,480,272,False,23,break@</BlockInfo>
																														//@<EndOfBlock/>
		path_out="null";																								//@<BlockInfo>jp.vstone.block.variable,560,272,560,272,False,22,break@</BlockInfo>
																														//@<EndOfBlock/>
		photoOptionBl=false;																							//@<BlockInfo>jp.vstone.block.variable,624,272,624,272,False,21,break@</BlockInfo>
																														//@<EndOfBlock/>
		loop=0;																											//@<BlockInfo>jp.vstone.block.variable,704,272,704,272,False,20,break@</BlockInfo>
																														//@<EndOfBlock/>
		failCount=0;																									//@<BlockInfo>jp.vstone.block.variable,768,272,768,272,False,19,break@</BlockInfo>
																														//@<EndOfBlock/>
		bodyTemp1=false;																								//@<BlockInfo>jp.vstone.block.variable,848,272,848,272,False,18,break@</BlockInfo>
																														//@<EndOfBlock/>
		bodyTemp2=false;																								//@<BlockInfo>jp.vstone.block.variable,912,272,912,272,False,17,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*Double motiondetectresult*/;																					//@<BlockInfo>jp.vstone.block.variable,976,272,976,272,False,16,break@</BlockInfo>
																														//@<EndOfBlock/>
		getUp=false;																									//@<BlockInfo>jp.vstone.block.variable,144,352,144,352,False,15,break@</BlockInfo>
																														//@<EndOfBlock/>
		announce="";																									//@<BlockInfo>jp.vstone.block.variable,208,352,208,352,False,14,break@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void senior()																								//@<BlockInfo>jp.vstone.block.func,32,1120,160,1120,False,61,@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		try{																											//@<BlockInfo>jp.vstone.block.freeproc,96,1120,96,1120,False,62,@</BlockInfo>
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("amssqZdWo4YasEHpYacSnvLBL")
		  .setOAuthConsumerSecret("p1RjOH9z75rVXBDlPD4pfX2KV11S7glrZycxDptRgO5thDINkb")
		  .setOAuthAccessToken("4882733018-6r7fqkuAiWOtXGorBCX0eXbt3Kn6QezyW54H00d")
		  .setOAuthAccessTokenSecret("oTPnpb2asAsos7N4OWGZUeURrlPXBmn2TLcG3hk0o68Dg");

		TwitterFactory tf = new TwitterFactory(cb.build());

		Twitter tw = tf.getInstance();

		/* pose登録 */
		// defaultPose = new CRobotPose();
			// defaultPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{0,-900,0,900,0,0,0,0}
							// );
			// defaultPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{100,100,100,100,100,100,100,100}
							// );
			// defaultPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
							// new Short[]{0,-255,0,180,80,0,180,80,0}
							// );
		// leftUpPose = new CRobotPose();
			// leftUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{0,500,0,900,0,0,0,0}
							// );
			// leftUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{100,100,100,100,100,100,100,100}
							// );
			// leftUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
							// new Short[]{0,-255,0,180,80,0,180,80,0}
							// );
		// rightUpPose = new CRobotPose();
			// rightUpPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{0,-900,0,-500,0,0,0,0}
							// );
			// rightUpPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{100,100,100,100,100,100,100,100}
							// );
			// rightUpPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
							// new Short[]{0,-255,0,180,80,0,180,80,0}
							// );
		// beShyPose = new CRobotPose();					
			// beShyPose.SetPose(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{-15,-14,-900,14,900,2,50,-2}
							// );
			// beShyPose.SetTorque(	new Byte[]{1,2,3,4,5,6,7,8},
							// new Short[]{100,100,100,100,100,100,100,100}
							// );
			// beShyPose.SetLed(	new Byte[]{0,1,2,8,9,10,11,12,13},
							// new Short[]{0,-255,0,140,30,30,140,30,30}
							// );
								
		while(true){
			System.out.println("loop: " + loop);
			
			/* ユーザー設定 */
			User hostAccount = tw.showUser("@IPLSota");
			User newsAccount = tw.showUser("@nhk_news");
			
			/* 日付時刻取得 */
			localDateTime = LocalDateTime.now();
			LocalDateTime d = (LocalDateTime)localDateTime;
			year = d.getYear();
			month = d.getMonthValue();
			day = d.getDayOfMonth();
			hour = d.getHour();
			minute = d.getMinute();
			
			/* AM6:00　起床検知 */
			if(!getUp){
				if(hour == 18 && minute == 0){
					GlobalVariable.robocam.StartMotionDetection();
					try{
						while(!getUp){
							motiondetectresult = GlobalVariable.robocam.getMotionDetectResult();
							System.out.println("検出値:" + motiondetectresult);
							if(motiondetectresult == null){
								motiondetectresult = 0.0;
							}else if(motiondetectresult > 0.20){
								getUp = true;
								announce = TextToSpeechSota.getTTSFile("おはようございます！",(int)8,(int)13,(int)11);
								CPlayWave.PlayWave(announce,true);
								
								File file = new File("/var/sota/photo/twitter/morning.png");
								Status hostStatus = tw.updateStatus(new StatusUpdate("ただいま起床を確認しました。").media(file));
								
								tempBl = true;
								loop = 0;
							}else{
							}
							
							CRobotUtil.wait((int)200);
							System.out.println(motiondetectresult);
						}
					}finally{
						  GlobalVariable.robocam.StopMotionDetection();
					}
				}
			}
			
			/* 定時処理 */
			if(getUp){
				if(hour == 7 && minute == 0){
					bodyTemp1 = true;
					bodyTemp2 = false;
				}else if(hour == 19 && minute == 0){
					bodyTemp2 = true;
					bodyTemp1 = false;
				}else if(hour == 23 && minute == 59){
					getUp = false;
				}
			}
			
			/* 体温測定(AM/PM 7:00) */
			if(bodyTemp1 || bodyTemp2){
				if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

				GlobalVariable.robocam.setEnableFaceSearch(true);
				GlobalVariable.robocam.setEnableSmileDetect(true);
				GlobalVariable.robocam.setEnableAgeSexDetect(true);

				announce = TextToSpeechSota.getTTSFile("体温の測定をはじめましょう。顔を見せてください！",(int)8,(int)13,(int)11);
				CPlayWave.PlayWave(announce,true);
				
				GlobalVariable.robocam.StartFaceDetect();
				try{
					GlobalVariable.detectCount=0;

					while(GlobalVariable.TRUE){
						GlobalVariable.faceresult = GlobalVariable.robocam.getDetectResult();

						if(GlobalVariable.faceresult.isDetect()) GlobalVariable.detectCount++;	//顔が見つかった回数のカウント
						else GlobalVariable.detectCount=0;

						if(GlobalVariable.detectCount>(int)2){	//顔が見つかったかの回数条件
						
							GlobalVariable.faceuser = GlobalVariable.robocam.getUser(GlobalVariable.faceresult);	//登録されているか

							if(GlobalVariable.faceuser != null)	//特徴取得できた場合
							{
								if(GlobalVariable.faceuser.getName() != null){
									GlobalVariable.facename = GlobalVariable.faceuser.getName();	//名前取得
									System.out.println("認証結果:" + GlobalVariable.facename);
									failCount = 0;
									break;
								}else{
									GlobalVariable.facename="";
									System.out.println("認証できませんでした。");
									failCount++;
									if(failCount > 100){
										System.out.println("長時間認証に失敗したため測定に移ります。");
										failCount = 0;
										break;
									}
								} 
							}else{

							}

						}else
						{

						}
					}

				}finally{
					GlobalVariable.robocam.StopFaceDetect();
				}
				
				announce = TextToSpeechSota.getTTSFile(GlobalVariable.facename + "さん。それでは体温の測定をはじめます。",(int)8,(int)13,(int)11);
				CPlayWave.PlayWave(announce,true);
				
				announce = TextToSpeechSota.getTTSFile("30秒後に写真を撮影します。",(int)8,(int)13,(int)11);
				CPlayWave.PlayWave(announce,true);
				
				CRobotUtil.wait((int)25000);
				
				announce = TextToSpeechSota.getTTSFile("体温計を30センチくらい離して見せてください。",(int)8,(int)13,(int)11);
				CPlayWave.PlayWave(announce,true);
				
				CRobotUtil.wait((int)3000);

				announce = TextToSpeechSota.getTTSFile("５、４、３、２、１、",(int)6,(int)13,(int)11);
				CPlayWave.PlayWave(announce,true);
				
				boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
				if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
				
				String filepath = "/var/sota/photo/";
				filepath += "bodyTemp/" + String.valueOf(year) + "_" + String.valueOf(month) + "_" + String.valueOf(day) + " " + hour + ":" + minute;
				dateRecord = year + "/" + month + "/" + day + " " + hour + ":" + minute;
				
				GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
				GlobalVariable.robocam.StillPicture(filepath);
				CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
				
				if(bodyTemp1){
					announce = TextToSpeechSota.getTTSFile("体温の記録が終わりました。次の体温測定は夜の７時です。",(int)8,(int)13,(int)11);
					CPlayWave.PlayWave(announce,true);
				}else if(bodyTemp2){
					announce = TextToSpeechSota.getTTSFile("体温の記録が終わりました。次の体温測定は朝の７時です。",(int)8,(int)13,(int)11);
					CPlayWave.PlayWave(announce,true);
				}else{
					
				}
				
				Mat mat = new Mat();
				mat = Imgcodecs.imread(filepath + ".jpg");
				Imgproc.putText(mat, dateRecord, new Point(1650, 1900), Core.FONT_HERSHEY_SIMPLEX, 3.0, new Scalar(20, 0, 200), 6);
				Imgcodecs.imwrite(filepath + ".jpg", mat);

				File file = new File(filepath + ".jpg");
				Status hostStatus = tw.updateStatus(new StatusUpdate("体温測定結果" + " " + hour + ":" + minute).media(file));
				
				bodyTemp1 = false;
				bodyTemp2 = false;
				tempBl = true;
			}else{
				
			}

		/* tempモード */
		if(tempBl){
			loop++;
			
			/* フラグ処理 */
			talkBl = false;
			newsBl = false;
			photoBl = false;
			
			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();
			tweetId = hostStatus.getId();
			
			System.out.println("tempMode : Please select(tweet) mode");
			CRobotUtil.wait((int)1000);
			
			if(tweet.equals("talk")){
				talkBl = true;
				tempBl = false;
				loop = 0;
				tw.destroyStatus(tweetId);
			}else if(tweet.equals("news")){
				newsBl = true;
				tempBl = false;
				loop = 0;
				tw.destroyStatus(tweetId);
				CRobotUtil.wait((int)10000);
			}else if(tweet.equals("photo")){
				photoBl = true;
				tempBl = false;
				loop = 0;
				tw.destroyStatus(tweetId);
			}else if(tweet.equals("modecheck")){
				System.out.println("modecheck");
				tweetId = hostStatus.getId();
				tw.destroyStatus(tweetId);
				hostStatus = tw.updateStatus("talkMode(this tweet will be deleted in 15 sec)");
				CRobotUtil.wait((int)15000);
				tweetId = hostStatus.getId();
				tw.destroyStatus(tweetId);
			}
		}
		/* talkモード */
		if(talkBl){
			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();
			
			System.out.println(tweet);

			if(loop == 0){
				tweetId = hostStatus.getId();
			}else if(loop % 50 == 0){
				//action(5ふんごと)
			}
			loop++;
			
			CRobotUtil.wait((int)6000);
			
			/* フラグ取得・初期化 */
			switch(tweet){
				case "news":
					talkBl = false;
					newsBl = true;
					loop = 0;
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					break;
				case "photo":
					talkBl = false;
					photoBl = true;
					loop = 0;
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					break;
				case "talk":
					tw.destroyStatus(tweetId);
				case "modecheck":
					System.out.println("modecheck");
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					hostStatus = tw.updateStatus("talkMode(this tweet will be deleted in 15 sec)");
					CRobotUtil.wait((int)15000);
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
				default:
					if(loop == 1) break;
					
					if(tweetId != hostStatus.getId()){
						tweetId = hostStatus.getId();
						System.out.println("talkMode : Sota found new tweet");
						String file = TextToSpeechSota.getTTSFile(tweet,(int)11,(int)13,(int)11);
						/* ログ出力 */
						try {
					        FileWriter fw = new FileWriter("/talklog.txt", true);
					        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
					        pw.println(year + "/" + month + "/" + day + " " + hour + ":" + minute + "　→　" + tweet);
					        pw.close();
					    } catch (IOException e) {
					        e.printStackTrace();
					    }
					if(file!=null) CPlayWave.PlayWave(file,true);
					}else{
					System.out.println("talkMode : Sota could't find new tweet");
					}
			}
		}
		/* newsモード */
		if(newsBl){
			Status hostStatus = hostAccount.getStatus();
			tweet = hostStatus.getText();
			
			CRobotUtil.wait((int)6000);
			
			/* フラグ取得・初期化 */
			switch(tweet){
				case "talk":
					newsBl = false;
					talkBl = true;
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					break;
				case "photo":
					newsBl = false;
					photoBl = true;
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					break;
				case "news":
					tw.destroyStatus(tweetId);
				case "modecheck":
					System.out.println("modecheck");
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
					hostStatus = tw.updateStatus("talkMode(this tweet will be deleted in 15 sec)");
					CRobotUtil.wait((int)15000);
					tweetId = hostStatus.getId();
					tw.destroyStatus(tweetId);
				default:
					Status newsStatus = newsAccount.getStatus();
					tweet = newsStatus.getText();
					String[] news = tweet.split("#", -1);	//NHKnewsの場合の処理
					
					if(tweetId != newsStatus.getId()){
					tweetId = newsStatus.getId();
					System.out.println("newsMode : Sota found new news");
					
					String file = TextToSpeechSota.getTTSFile(news[0],(int)11,(int)13,(int)11);
					if(file!=null) CPlayWave.PlayWave(file,true);
					}else{
					System.out.println("newsMode : Sota could't find new news");
					}
			}
		}
		/* photoモード */
		if(photoBl){
			GlobalVariable.motion.play(defaultPose,1000);
			CRobotUtil.wait((int)1000);

			folderDate = String.valueOf(year) + "_" + String.valueOf(month) + "_" + String.valueOf(day);
			dateRecord = year + "/" + month + "/" + day + " " + hour + ":" + minute;

			/* 日付フォルダ作成 path指定*/
			File newFolder = new File("/var/sota/photo/" + folderDate);
			
			if (newFolder.mkdir()){
		      System.out.println("photoMode : Succeeded making new directory");
		    }else{
		      System.out.println("photoMode : Directory already exists");
		    }
			String filepath = "/var/sota/photo/";
			filepath += folderDate + "/" + hour + ":" + minute;
			
			/* 撮影 */
			System.out.println("photoMode : Take photo standby...");
			String photoCount = TextToSpeechSota.getTTSFile("５、４、３、２、１、",(int)6,(int)13,(int)11);
			if(photoCount!=null) CPlayWave.PlayWave(photoCount,true);
			
			boolean isTrakcing=GlobalVariable.robocam.isAliveFaceDetectTask();
			if(isTrakcing) GlobalVariable.robocam.StopFaceTraking();
			
			GlobalVariable.robocam.initStill(new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_5Mpixel, CameraCapture.CAP_FORMAT_MJPG));
			GlobalVariable.robocam.StillPicture(filepath);
			CRobotUtil.Log("stillpicture","save picthre file to \"" + filepath +"\"");
			
			/* 写真に日付時刻をかきこむ */
			Mat mat = new Mat();
			mat = Imgcodecs.imread(filepath + ".jpg");
			Imgproc.putText(mat, dateRecord, new Point(1650, 1900), Core.FONT_HERSHEY_SIMPLEX, 3.0, new Scalar(20, 0, 200), 6);
			Imgcodecs.imwrite(filepath + ".jpg", mat);
			
			/* 画像アップロード */
			File file = new File(filepath + ".jpg");
			Status hostStatus = tw.updateStatus(new StatusUpdate(folderDate + " " + hour + ":" + minute).media(file));
			
			/* tempに移行 */
			tempBl = true;
			
			if(isTrakcing) GlobalVariable.robocam.StartFaceTraking();
			
			}
		}
		}catch(TwitterException te){
		System.out.println(te);
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

}
