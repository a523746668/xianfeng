package com.qingyii.hxt.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.http.HttpUrlConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadUtil {

	/**
	 * 保存图片到SD卡
	 * 
	 * @param filePath
	 *            图片原地址
	 * @param picName
	 *            图片sd卡保存文件名
	 */
	public static int saveBmpToSd(String filePath, String picName) {
		String sdPath = HttpUrlConfig.cacheDir + "/" + picName;
		File file = new File(sdPath);
		// 图片SD卡中不存在则下载  下载成功返回1  已下载返回3 错误返回0
		if (!file.exists()) {
			try {
				Bitmap bm = ImageLoader.getInstance().loadImageSync(HttpUrlConfig.photoDir+picName);
//				Bitmap bm=getHttpBitmap(filePath);
				if (bm != null) {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(file));
					bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
					bos.flush();
					bos.close();
					return 1;
				//	Log.v("Your table", "图片已下载成功！");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return 1;
		}
			return 0;
	}
	
	
	
	/**
	 * 保存gif图片到SD卡
	 * 
	 * @param filePath
	 *            图片原地址
	 * @param picName
	 *            图片sd卡保存文件名
	 */
	public static int saveGifToSD(String path, String saveName) {
			String sdPath = HttpUrlConfig.cacheDir + "/" + saveName;
			String gifPath= HttpUrlConfig.photoDir+saveName;
			URL url;
			HttpURLConnection con;
		    byte[] b;
				try {
					url = new URL(gifPath);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
			        con.setConnectTimeout(1000 * 6);
			        if (con.getResponseCode() == 200) {
			            InputStream inputStream = con.getInputStream();
			            b = getByte(inputStream);
			            File file = new File(sdPath);
			            FileOutputStream fileOutputStream = new FileOutputStream(file);
			            fileOutputStream.write(b);
			            fileOutputStream.close();
			            return 1;
			        }
			        
				}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				return 0;
		}
	

	 /**
	  * sd卡获取gif图片  字节流返回
	  * @param dir
	  * @param fileName
	  * @return
	  */
	public  static byte[] getGifFromSD(String fileName){
	  File file = new File(HttpUrlConfig.cacheDir+"/"+fileName);
	  if( !file.exists() ){
	   return null;
	  }
	  InputStream inputStream = null;
	  try {
	   inputStream = new BufferedInputStream(new FileInputStream(file));
	   byte[] data = new byte[inputStream.available()];
	   inputStream.read(data);
	   return data;
	  } catch (FileNotFoundException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }finally{
	   try {
	    if( inputStream != null ){
	     inputStream.close();
	    }
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  }
	  return null;
	 }



	/**
	 * 保存biitmap到Sd卡云中原缓存目录
	 * @param context
	 * @param bitName
	 * @param mBitmap
	 */
	public static boolean saveMyBitmap(Context context, String bitName, Bitmap mBitmap) {
		boolean flag=true;
		if(SDUtil.sdCardExist()){
			if(NetworkUtils.isNetConnected(context)){
				File f = new File(HttpUrlConfig.cacheDir +"/"+ bitName + ".png");
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(context, "图片保存失败！", Toast.LENGTH_SHORT).show();
					flag=false;
				}
				FileOutputStream fOut = null;
				try {
					fOut = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					flag=false;
				}
				mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				try {
					fOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
					flag=false;
				}
				try {
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
					flag=false;
				}
			}else{
				Toast.makeText(context, "网络异常！", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(context, "SD卡不存在，请检查！", Toast.LENGTH_SHORT).show();
		}
		return flag;
	}
	
	/**
     * 获取网落图片资源 
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
    	URL myFileURL;
    	Bitmap bitmap=null;
    	InputStream is=null;
    	try{
    		myFileURL = new URL(url);
    		//获得连接
    		HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
    		//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
    		conn.setConnectTimeout(6000);
    		//连接设置获得数据流
    		conn.setDoInput(true);
    		//不使用缓存
    		conn.setUseCaches(false);
    		//这句可有可无，没有影响
    		//conn.connect();
    		//得到数据流
    		 is= conn.getInputStream();
    		//解析得到图片
    		bitmap = BitmapFactory.decodeStream(is);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		//关闭数据流
    		if(is!=null){
    			try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
		return bitmap;
    	
    }
    
	private static byte[] getByte(InputStream inputStream) throws Exception {
		byte[] b = new byte[1024];
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int len = -1;
		while ((len = inputStream.read(b)) != -1) {
			byteArrayOutputStream.write(b, 0, len);
		}
		byteArrayOutputStream.close();
		inputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
}
