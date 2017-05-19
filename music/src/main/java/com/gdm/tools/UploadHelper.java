package com.gdm.tools;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadHelper {
	private static UploadHelper instance;
	private String rootpath;
	private String portPath="/image/port";
	private String bgpath="/image/bg";
	private String musicPath="/data/music";
	private String mvpath="/data/mv";
	private String musicimg="/data/music/img";
	private String lrcpath="/data/music/lrc";
	private String imgpath="/image";
	private UploadHelper(){
		rootpath=System.getProperty("apppath");
	}
	public static UploadHelper getInstance(){
		if(instance==null){
			instance=new UploadHelper();
		}
		return instance;
	}
	/**
	 * 存储头像文件
	 * @param file
	 * @return 头像文件的地址
	 */
	public String savePort(MultipartFile file){
		File root=new File(rootpath+portPath);
		if(!root.exists()){
			boolean b = root.mkdirs();
			if(!b){
				return null;
			}
		}
		UUID uuid = UUID.randomUUID();
		
		CharSequence type = uuid.toString().subSequence(0, 2);
		File f = new File(root,type+file.getOriginalFilename());
		try {
			file.transferTo(f);
			return f.getName();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	public String saveImageFile(MultipartFile file){
		File root=new File(rootpath+imgpath);
		return saveFile(root, file);
	}
	public String saveBackGround(MultipartFile file){
		File root=new File(rootpath+bgpath);
		if(!root.exists()){
			boolean b = root.mkdirs();
			if(!b){
				return null;
			}
		}
		UUID uuid = UUID.randomUUID();
		
		CharSequence type = uuid.toString().subSequence(0, 2);
		File f = new File(root,type+file.getOriginalFilename());
		try {
			file.transferTo(f);
			return f.getName();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public String saveMusic(MultipartFile file){
		File root=new File(rootpath+musicPath);
		return saveFile(root,file);
	}
	private String saveFile(File root, MultipartFile file) {
		// TODO Auto-generated method stub
		if(!root.exists()){
			boolean b = root.mkdirs();
			if(!b){
				return null;
			}
		}
		try {
			if(file.getBytes().length==0){
				return null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return null;
		}
		String name = file.getName();
		String originalFilename = file.getOriginalFilename();
		String[] split = originalFilename.split("\\.");
		UUID uuid = UUID.randomUUID();
		
		CharSequence type = uuid.toString().subSequence(0, 8);
		File f = new File(root,type+"."+split[split.length-1]);
		try {
			file.transferTo(f);
			return f.getName();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public String saveMVfile(MultipartFile file){
		File root=new File(rootpath+mvpath);
		return saveFile(root, file);
	}
	public String saveMusicImg(MultipartFile file){
		File root=new File(rootpath+musicimg);
		return saveFile(root, file);
	}
	public String saveLrc(MultipartFile file){
		File root=new File(rootpath+lrcpath);
		return saveFile(root, file);
	}
}
