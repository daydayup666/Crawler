package tao;

import java.io.*;
import java.util.Scanner;

public class PageProcessFactory {
	private File file = new File("BioComutingPage.txt");
	private Scanner input = new Scanner(System.in);	
	private String url = "http://2014.bicta.org/?page_id=66";

	public void pageProcess(PageProcessor pageProcessor) {
		if (file.exists()) {
			System.out
					.println("BioComutingPage.txt�Ѵ��ڣ��Ƿ�����������ҳ��BioComputingPage.txt?(y/n)");
			String reader = null;
			while ((reader = input.next().toLowerCase()) != null) {
				if (reader.equals("y")) {
					String pageContend = pageProcessor.downloadPage(url);
					pageProcessor.writeToFile(pageContend, file);
					pageProcessor.fetchInfo(file);
					System.out.println("��ҳ�������...");
					break;
				} else if (reader.equals("n")) {
					pageProcessor.fetchInfo(file);
					System.out.println("��ҳ�������...");
					break;
				} else {
					System.out.println("������y����n��");
				}
			}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String pageContend = pageProcessor.downloadPage(url);
			pageProcessor.writeToFile(pageContend, file);
			pageProcessor.fetchInfo(file);
			System.out.println("��ҳ�������...");
		}
	}
}
