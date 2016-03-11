package tao;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

import org.apache.commons.httpclient.HttpException;

public class Main {
	
	/**
	 * ≤‚ ‘¥˙¬Î
	 */
	public static void main(String[] args) {
		PageProcessor pageProcessor = new BioComputingPageProcessor();
		PageProcessFactory factory = new PageProcessFactory();
		factory.pageProcess(pageProcessor);
	}
}
