package com.util;

import java.io.*;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * FTP UTILS.支持多线程环境.
 *
 */
public final class FTPUtil {

	public static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	private ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<>();
	
	/** FTP 登录用户名 */
	private String userName;

	/** FTP 登录密码 */
	private String password;

	/** FTP 服务器IP地址 */
	private String ip;

	/** FTP 端口 */
	private int port;

	/** 上传路径 */
	private String uploadUrl = "";
	
	public FTPUtil() {
		super();
		// 做调试用的日志
		logger.info("FTPUtils instance created -> " + this);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	/**
	 * 
	 * 获取FTPClient对象
	 * 
	 * @param middleDir
	 * @return
	 */
	private FTPClient getFtpClient(String... middleDir) {
		
		FTPClient ftpClient = this.ftpClientThreadLocal.get();
		
		if (ftpClient != null && ftpClient.isConnected()) {
			return ftpClient;
        }
        
		try {
			// 建立连接
			connect(ftpClient);

			long start = System.currentTimeMillis();

			ftpClient = this.ftpClientThreadLocal.get();
			
			if (ftpClient == null) {
				throw new RuntimeException("登录失败或连接被拒绝");
			}

			String dir = middleDir.length > 0 ? uploadUrl + "/" + middleDir[0] : uploadUrl;
			if (!ftpClient.changeWorkingDirectory(dir)) {
				if (middleDir.length > 0) {
					String middleDirTmp = middleDir[0].endsWith("/") ? removeEnd(middleDir[0], "/") : middleDir[0];
					String[] middleDirList = middleDirTmp.split("/");

					String levelDir = uploadUrl;
					for (String subDir : middleDirList) {
						levelDir = levelDir + "/" + subDir;
						if (!ftpClient.changeWorkingDirectory(levelDir)) {
							if (ftpClient.makeDirectory(levelDir))
								ftpClient.changeWorkingDirectory(levelDir);
						}
					}
				}
			}

			long end = System.currentTimeMillis();
			logger.info("----->>> getFtpClient use time: {}ms", end - start);
			
		} catch (SocketException e) {
			logger.error("连接被拒绝", e);
		} catch (IOException e) {
			logger.error("IO异常", e);
		} catch (Exception e) {
			logger.error("未知异常", e);
		}

		return this.ftpClientThreadLocal.get();
	}

	/**
	 * 建立与文件服务器的连接
	 * 
	 * @param client
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	private FTPClient connect(FTPClient client) throws IOException {
		long start = System.currentTimeMillis();
		if (client == null) {
			client = new FTPClient();
			logger.info("FTPClinet instance created[FTPUtils inst addr:" + this + "; FTPClinet inst addr:" + client + "]");
		}

		client.configure(getFtpClientConfig());
		client.connect(this.ip, this.port);

		long end = System.currentTimeMillis();
		logger.info("----->>> connect use time: {}ms", end - start);

		boolean loginSuccess = client.login(this.userName, this.password);
		
		long loginEnd = System.currentTimeMillis();
		logger.info("----->>> login use time: {}ms", loginEnd - end);
		
		if (!loginSuccess) {
			logger.warn("用户名或密码不匹配,登录失败.");
			client.logout();
			client = null;
		} else {
			// 文件类型,默认是ASCII
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.setControlEncoding("UTF-8");

			// 设置被动模式
			client.enterLocalPassiveMode();
			client.setBufferSize(1024);

			client.setDefaultPort(port);

			client.getReplyString();

			// 响应信息
			int replyCode = client.getReplyCode();
			
			client.setConnectTimeout(60000);
			client.setDataTimeout(120000);

			if (!FTPReply.isPositiveCompletion(replyCode)) {
				// 释放空间
				client = null;
				closeFTPClient();
				logger.warn("连接被拒绝");
				throw new SocketException("连接被拒绝");
			}
		}
		// 放入ThreadLocalMap
		this.ftpClientThreadLocal.set(client);
		
		long configEnd = System.currentTimeMillis();
		logger.info("----->>> connect config use time: {}ms", configEnd - loginEnd);
		
		return client;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param fileName  --本地文件名
	 * @param middleDir --扩展中间路径
	 * @return true:上传成功;false:上传失败
	 */
	public boolean uploadFile(String fileName, InputStream input, String... middleDir) {
		boolean flag = true;
		logger.info("上传文件");
		FTPClient client = null;
		try {
			client = getFtpClient(middleDir);

			long start = System.currentTimeMillis();

			client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			flag = client.storeFile(fileName, input);
			
			long end = System.currentTimeMillis();
			logger.info("----->>> uploadFile use time: {}ms", end - start);
		} catch (Exception e) {
			flag = false;
			logger.error("上传文件失败", e);
		} finally {
			closeFTPClient();
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
			client = getFtpClient();
			fos = new FileOutputStream(localFileName);
			bos = new BufferedOutputStream(fos);
			flag = client.retrieveFile(remoteFileName, bos);
		} catch (Exception e) {
			flag = false;
			logger.error("下载文件失败", e);
		} finally {
			closeIO(bos);
			closeIO(fos);
			closeFTPClient();
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
			client = getFtpClient();
			InputStream inputStream = client.retrieveFileStream(remoteFileName);
			return inputStream;
		} catch (Exception e) {
			logger.error("获取下载文件对应的流失败", e);
		}
		return null;
	}

	/**
	 * 删除文件
	 * @param fileName
	 * @param middleDir
	 * @return
	 */
	public boolean deleteFile(String fileName, String... middleDir) {
		boolean flag = true;
		FTPClient client = null;
		try {
			client = getFtpClient(middleDir);
			flag = client.deleteFile(fileName);
			if (flag) {
				logger.info("删除文件成功");
			} else {
				logger.info("删除文件失败"); 
			}
		} catch (Exception e) {
			flag = false;
			logger.error("删除文件失败", e);
		} finally {
			closeFTPClient();
		}

		return flag;
	}

	/**
	 * 删除目录
	 * @param pathName
	 */
	public void deleteDirectory(String pathName) {
		FTPClient client = null;
		try {
			client = getFtpClient();
			File file = new File(pathName);
			if (file.isDirectory()) {
				file.listFiles();
			} else {
				deleteFile(pathName);
			}
			client.removeDirectory(pathName);
		} catch (Exception e) {
			logger.error("删除目录失败", e);
		} finally {
			closeFTPClient();
		}
	}

	/**
	 * 删除空目录
	 * @param pathName
	 */
	public void deleteEmptyDirectory(String pathName) {
		FTPClient client = null;
		try {
			client = getFtpClient();
			client.removeDirectory(pathName);
		} catch (Exception e) {
			logger.error("删除空目录失败", e);
		} finally {
			closeFTPClient();
		}
	}

	/**
	 * 列出服务器上目录及文件的名称
	 * 
	 * @param regStr -- 匹配的正则表达式
	 */
	public String[] listRemoteFiles(String regStr) {
		String files[] = null;
		FTPClient client = null;
		try {
			client = getFtpClient();
			files = client.listNames(regStr);
			if (files == null || files.length == 0)
				logger.info("没有匹配到相关的目录和文件");
			else {
				for (int i = 0; i < files.length; i++) {
					logger.info(files[i]);
				}
			}
		} catch (Exception e) {
			logger.error("列出服务器上目录及文件的名称失败", e);
		} finally {
			closeFTPClient();
		}

		return files;
	}

	/**
	 * 列出服务器上的所有目录和文件
	 */
	public String[] listRemoteAllFiles() {
		String names[] = null;
		FTPClient client = null;
		try {
		    client = getFtpClient();
			names = client.listNames();
			for (int i = 0; i < names.length; i++) {
				logger.info(names[i]);
			}
		} catch (Exception e) {
			logger.error("列出服务器上目录及文件失败", e);
		} finally {
			closeFTPClient();
		}

		return names;
	}

	/**
	 * 切换到服务器的指定目录
	 * 
	 * @param directory
	 */
	public void changeWorkingDirectory(String directory) {
		FTPClient client = getFtpClient();
		try {
			client.changeWorkingDirectory(directory);
		} catch (Exception e) {
			logger.error("切换到指定目录失败", e);
		}
	}

	/**
	 * 返回到上一层目录
	 */
	public void changeToParentDirectory() {
		FTPClient client = getFtpClient();
		try {
			client.changeToParentDirectory();
		} catch (Exception e) {
			logger.error("返回上一层目录失败", e);
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param oldFileName --原文件名
	 * @param newFileName --新文件名
	 */
	public void renameFile(String oldFileName, String newFileName) {
		FTPClient client = getFtpClient();
		try {
			boolean flag = client.rename(oldFileName, newFileName);
			if (flag) {
				logger.info("重命名文件成功[原文件名称:" + oldFileName + ",新文件名称:"+ newFileName + "]");
			} else {
				logger.info("重命名文件失败");
			}
		} catch (Exception e) {
			logger.error("重命名文件失败", e);
		} finally {
			closeFTPClient();
		}
	}

	/**
	 * 获取连接文件服务器的客户端配置 
	 * 
	 * @return ftpConfig
	 */
	private FTPClientConfig getFtpClientConfig() {
		FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpClientConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpClientConfig;
	}

	/**
	 * 在服务器上创建一个文件夹
	 * 
	 * @param dir 文件夹名称,不能含有特殊字符.如 \ 、/ 、: 、* 、?、 "、 <、>...
	 */
	public boolean makeDirectory(String dir) {
		FTPClient client = getFtpClient();
		boolean flag = true;
		try {
			flag = client.makeDirectory(dir);
			if (flag) {
				logger.info("创建文件夹成功");
			} else {
				logger.info("创建文件夹失败");
			}
		} catch (Exception e) {
			flag = false;
			logger.error("创建文件夹失败", e);
		} finally {
			closeFTPClient();
		}

		return flag;
	}
	
	public void closeFTPClient() {
		long start = System.currentTimeMillis();

		try {
			FTPClient ftpClient = this.ftpClientThreadLocal.get();
			
			if (ftpClient == null) {
				return;
			}
			
			logger.info("登出[FTPUtils instance addr:" + this + "; FTPClient instance addr:" + ftpClient + "]");
			//登出
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				logger.info("断开与文件服务器的连接[FTPUtils instance addr:" + this + "; FTPClient instance addr:" + ftpClient + "]");
				ftpClient.disconnect();
				ftpClient = null;
			}

			long end = System.currentTimeMillis();
			logger.info("----->>> returnFTPClient use time: {}ms", end - start);
			
		} catch (Exception e) {
			logger.error("关闭连接文件服务器的客户端失败", e);
			throw new RuntimeException("关闭连接文件服务器的客户端失败", e);
		} finally {
			//移除
			this.ftpClientThreadLocal.remove();
		}
	}
	
	private void closeIO(Closeable closeableInst) {
		if (closeableInst != null) {
			try {
				closeableInst.close();
			} catch (IOException ex) {
				if (closeableInst != null) {
					try {
						closeableInst.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
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
