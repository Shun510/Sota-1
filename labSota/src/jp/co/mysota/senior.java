try{
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
	
	/* AM6:00　起床検知 
		動体検出,発話,ツイート */
	if(!getUp){
		if(hour == 6 && minute == 0){
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
	
	/* 定時フラグ処理 */
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
	
	/* 体温測定(AM/PM 7:00) 
		顔認証,撮影,ツイート */
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
/* talkモード 
	発話,ログ出力 */
if(talkBl){
	Status hostStatus = hostAccount.getStatus();
	tweet = hostStatus.getText();

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
			if(loop == 1) break;	//tempからの移行で発生する参照エラーの回避
			
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
/* photoモード 
	撮影,加工,アップロード */
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