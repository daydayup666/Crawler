package tao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public abstract class PageProcessor implements DownloadPageProcess,
						 WriteToFileProcess,FetchInfoProcess{
	private HttpClient httpClient = new HttpClient();
	private GetMethod getmethod;
	
	public String downloadPage(String path) {
		// GetMethodʵ��HTTP GET����
		getmethod = new GetMethod(path);
		try {
			// �����Ӧ״̬��
			int statusCode = httpClient.executeMethod(getmethod);
			if (statusCode == HttpStatus.SC_OK) {
				String pageString = getmethod.getResponseBodyAsString();
				getmethod.releaseConnection();
				// �ͷ���Դ
				return pageString;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeToFile(String str, File file) {
		try {
			str = str.replaceAll("\r\n|\n", "\r\n");
			OutputStreamWriter output = new OutputStreamWriter(
					new FileOutputStream(file));

			output.write(str);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fetchInfo(File file){ }

}
