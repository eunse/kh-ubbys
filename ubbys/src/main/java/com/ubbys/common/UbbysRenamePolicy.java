package com.ubbys.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UbbysRenamePolicy implements FileRenamePolicy  {
	@Override
	public File rename(File originalFile) {
		long currentTime = System.currentTimeMillis();
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		int ranNum = (int)(Math.random()*100000);
		
		String str = "_" + String.format("%05d", ranNum);
		
		String name = originalFile.getName();
		String ext = null;
		
		int dot = name.lastIndexOf(".");
		
		if(dot != -1) {
			ext = name.substring(dot);
		} else { 
			ext = "";
		}
		
		String fileName = ft.format(new Date(currentTime)) + str + ext;
		File newFile = new File(originalFile.getParent(), fileName);
		
		return newFile;
	}
}
