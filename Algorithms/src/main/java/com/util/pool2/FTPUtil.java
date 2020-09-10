package com.util.pool2;

import cn.hutool.core.io.IoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 
 * FTP UTIL
 *
 */
public final class FTPUtil {

	public static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
	
	private FTPClientPool ftpClientPool = new FTPClientPool(new FTPClientFactory(new FTPConfig()), new FTPClientPoolConfig());

	/**
	 * 
	 * 获取FTPClient对象
	 * 
	 * @param middleDir
	 * @return
	 */
	private FTPClient getFtpClient(String middleDir) {

		FTPClient ftpClient = null;
		try {
			ftpClient = ftpClientPool.borrowObject();
		} catch (Exception e) {
			logger.error("getFtpClient error", e);
			
		}

		if (StringUtils.isEmpty(middleDir)) {
			middleDir = "/";
		}

		try {
			// change work directory
			if (!ftpClient.changeWorkingDirectory(middleDir)) {

				String middleDirTmp = middleDir.endsWith("/") ? removeEnd(middleDir, "/") : middleDir;
				String[] middleDirList = middleDirTmp.split("/");

				String levelDir = "";
				for (String subDir : middleDirList) {
					levelDir = levelDir + "/" + subDir;
					if (!ftpClient.changeWorkingDirectory(levelDir)) {
						if (ftpClient.makeDirectory(levelDir))
							ftpClient.changeWorkingDirectory(levelDir);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ftpClient;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param fileName  --本地文件名
	 * @param middleDir --扩展中间路径
	 * @return true:上传成功;false:上传失败
	 */
	public boolean uploadFile(String fileName, InputStream input, String middleDir) {
		boolean flag = true;
		FTPClient client = null;
		try {
			client = getFtpClient(middleDir);

			client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			flag = client.storeFile(fileName, input);
			
		} catch (Exception e) {
			flag = false;
			logger.error("上传文件失败", e);
		} finally {
			closeFTPClient(client);
			closeIO(input);
		}

		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFileName -- 服务器上的文件名
	 * @param localFileName  -- 本地文件名
	 * @return true:下载成功;false:下载失败
	 */
	public boolean loadFile(String remoteFileName, String localFileName) {
		boolean flag = true;
		FTPClient client = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			client = getFtpClient("/");
			fos = new FileOutputStream(localFileName);
			bos = new BufferedOutputStream(fos);
			flag = client.retrieveFile(remoteFileName, bos);
		} catch (Exception e) {
			flag = false;
			logger.error("下载文件失败", e);
		} finally {
			closeIO(bos);
			closeIO(fos);
			closeFTPClient(client);
		}

		return flag;
	}

	/**
	 * 下载文件
	 * <p>
	 * 获取下载目标文件对应的流
	 * 
	 * @param remoteFileName -- 服务器上的文件名
	 * @return
	 */
	public InputStream loadFile(String remoteFileName) {
		FTPClient client = null;
		try {
			client = getFtpClient("/");
			return client.retrieveFileStream(remoteFileName);
		} catch (Exception e) {
			logger.error("获取下载文件对应的流失败", e);
		}
		return null;
	}
	
	public void closeFTPClient(FTPClient ftpClient) {
		ftpClientPool.returnObject(ftpClient);
	}
	
	private void closeIO(Closeable closeable) {
		IoUtil.close(closeable);
	}
	
	private boolean isEmpty(String value) {
        return null == value || value.trim().length() == 0;
    }
	
	private String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        
        return str;
    }
}
