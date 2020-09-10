package com.sitech.esb.hb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.hibernate.Session;

public class CompressBook {
public CompressBook() {
	
}

/**
* inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
*/
public void zip(String inputFileName, String zipFileName) throws Exception {
   //System.out.println(zipFileName);
   zip(zipFileName, new File(inputFileName));
}

private void zip(String zipFileName, File inputFile) throws Exception {
   ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
     zipFileName));
   zip(out, inputFile, "");
   //System.out.println("zip done");
   out.close();
}

private void zip(ZipOutputStream out, File f, String base) throws Exception {
   if (f.isDirectory()) {
    File[] fl = f.listFiles();
    out.putNextEntry(new ZipEntry(base + "/"));
    base = base.length() == 0 ? "" : base + "/";
    for (int i = 0; i < fl.length; i++) {
     zip(out, fl[i], base + fl[i].getName());
    }
   } else {
    out.putNextEntry(new ZipEntry(base));
    FileInputStream in = new FileInputStream(f);
    int b;
    //System.out.println(base);
    while ((b = in.read()) != -1) {
     out.write(b);
    }
    in.close();
   }
}

public static void main(String[] temp) {
   CompressBook book = new CompressBook();
   try {
//    book.zip("f:\\a", "f:\\a.zip");//你要压缩的文件夹

    book.unzipFile("D:\\tt","D:\\ESB工作资料\\中国联通全业务服务接入平台\\天源迪科提供的资料\\WSDL+XSD\\WSDL+XSD.zip" );//你要解压的文件夹
   } catch (Exception ex) {
    ex.printStackTrace();
   }
}
//传入字符串路径
public void releaseZipToFile(String sourceZip, String outFileName)
    throws IOException {
   ZipFile zfile = new ZipFile(sourceZip);
   //System.out.println(zfile.getName());
   Enumeration zList = zfile.entries();
   ZipEntry ze = null;
   byte[] buf = new byte[1024];
   while (zList.hasMoreElements()) {
    // 从ZipFile中得到一个ZipEntry
    ze = (ZipEntry) zList.nextElement();
    if (ze.isDirectory()) {
     continue;
    }
    // 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
    OutputStream os = new BufferedOutputStream(new FileOutputStream(
      getRealFileName(outFileName, ze.getName())));
    InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
    int readLen = 0;
    while ((readLen = is.read(buf, 0, 1024)) != -1) {
     os.write(buf, 0, readLen);
    }
    is.close();
    os.close();
    //System.out.println("Extracted: " + ze.getName());
   }
   zfile.close();

}
// 传入文件
public void releaseZipToFile(File sourceZip, String outFileName)
    throws IOException {
   ZipFile zfile = new ZipFile(sourceZip);
   //System.out.println(zfile.getName());
   Enumeration zList = zfile.entries();
   ZipEntry ze = null;
   byte[] buf = new byte[1024];
   while (zList.hasMoreElements()) {
    // 从ZipFile中得到一个ZipEntry
    ze = (ZipEntry) zList.nextElement();
    if (ze.isDirectory()) {
     continue;
    }
    // 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
    OutputStream os = new BufferedOutputStream(new FileOutputStream(
      getRealFileName(outFileName, ze.getName())));
    InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
    int readLen = 0;
    while ((readLen = is.read(buf, 0, 1024)) != -1) {
     os.write(buf, 0, readLen);
    }
    is.close();
    os.close();
    //System.out.println("解压文件: " + ze.getName());
   }
   zfile.close();

}
private File getRealFileName(String baseDir, String absFileName) {
   String[] dirs = absFileName.split("/");
   // System.out.println(dirs.length);
   File ret = new File(baseDir);
   // System.out.println(ret);
   if (dirs.length > 1) {
    for (int i = 0; i < dirs.length - 1; i++) {
     ret = new File(ret, dirs[i]);
    }
   }
   if (!ret.exists()) {
    ret.mkdirs();
   }
   ret = new File(ret, dirs[dirs.length - 1]);
   return ret;
}
/**

* 解压zip文件

*

* @param targetPath

* @param zipFilePath

*/

public void unzipFile(String targetPath, InputStream is,Session s,String srvId,String srvWsdlFileid) {

try {



ZipInputStream zis = new ZipInputStream(is);

ZipEntry entry = null;

while ((entry = zis.getNextEntry()) != null) {

String zipPath = entry.getName();

try {

if (entry.isDirectory()) {

File zipFolder = new File(targetPath + File.separator

+ zipPath);

//System.out.println(zipFolder.getAbsolutePath());
if (!zipFolder.exists()) {

zipFolder.mkdirs();

}

} else {





int bread;

SrvInfoDAO srvInfoDAO = new SrvInfoDAO();
if(zipPath.endsWith(".wsdl")){
	  SrvWsdlFile srvWsdlFile=new SrvWsdlFile();

		srvWsdlFile.setSrvWsdlFileName(zipPath);
	    if(!"-1".equals(srvId)&&!"".equals(srvId)){
	    	srvWsdlFile.setSrvId(new Long(srvId));	
		    }

				    if(!"".equals(srvWsdlFileid)){
				    	srvWsdlFile.setSrvWsdlFileid(new Long(srvWsdlFileid));	
		    }
		srvWsdlFile.setSrvWsdlFile(org.hibernate.Hibernate.createBlob(new byte[1]));
		srvInfoDAO.save(srvWsdlFile, s);
		s.flush();
		s.refresh(srvWsdlFile, org.hibernate.LockMode.UPGRADE);
		java.io.OutputStream outs = srvWsdlFile.getSrvWsdlFile().setBinaryStream(1); 
	
	
			while ((bread = zis.read()) != -1) {

				outs.write(bread);

				}
		  
			outs.close(); 
}
if(zipPath.endsWith(".xsd")){
	 SrvSchemaFile srvSchemaFile=new SrvSchemaFile();

	 srvSchemaFile.setSrvSchemaFilename(zipPath);
	    if(!"-1".equals(srvId)&&!"".equals(srvId)){
		    srvSchemaFile.setSrvId(new Long(srvId));	
		    }

				    if(!"".equals(srvWsdlFileid)){
		    srvSchemaFile.setSrvWsdlFileid(new Long(srvWsdlFileid));	
		    }
	 srvSchemaFile.setSrvSchemaFile(org.hibernate.Hibernate.createBlob(new byte[1]));
		srvInfoDAO.save(srvSchemaFile, s);
		s.flush();
		s.refresh(srvSchemaFile, org.hibernate.LockMode.UPGRADE);
		java.io.OutputStream outs = srvSchemaFile.getSrvSchemaFile().setBinaryStream(1); 
	
	
			while ((bread = zis.read()) != -1) {

				outs.write(bread);

				}
		
			outs.close(); 
}



}

//System.out.println("成功解压:" + zipPath);

} catch (Exception e) {

//System.out.println("解压" + zipPath + "失败");
e.printStackTrace();
continue;

}

}

zis.close();

is.close();

//System.out.println("解压结束");

} catch (Exception e) {



}

}


public void unzipFile(String targetPath, String zipFilePath) {

	try {

	File zipFile = new File(zipFilePath);

	InputStream is = new FileInputStream(zipFile);

	ZipInputStream zis = new ZipInputStream(is);

	ZipEntry entry = null;

	//System.out.println("开始解压:" + zipFile.getName() + "...");

	while ((entry = zis.getNextEntry()) != null) {

	String zipPath = entry.getName();

	try {

	if (entry.isDirectory()) {

	File zipFolder = new File(targetPath + File.separator

	+ zipPath);

	if (!zipFolder.exists()) {

	zipFolder.mkdirs();

	}

	} else {
		//System.out.println(targetPath + File.separator
		//		+ zipPath);
	File file = new File(targetPath + File.separator

	+ zipPath);

	if (!file.exists()) {

	File pathDir = file.getParentFile();

	pathDir.mkdirs();

	file.createNewFile();

	}

	FileOutputStream fos = new FileOutputStream(file);

	int bread;

	while ((bread = zis.read()) != -1) {
		
	fos.write(bread);

	}

	fos.close();

	}

	//System.out.println("成功解压:" + zipPath);

	} catch (Exception e) {

	//System.out.println("解压" + zipPath + "失败");

	continue;

	}

	}

	zis.close();

	is.close();

	//System.out.println("解压结束");

	} catch (Exception e) {

	e.printStackTrace();

	}

	}

}

