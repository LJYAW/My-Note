package org.springside.modules.columnbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class exeLinuxCmd {

	public static int execLinuxCmd(String cmd) {
		int exitValue = 0;

		Process process = null;
		BufferedReader infoInput = null;
		BufferedReader errorInput = null;
		try {
			System.out.println("exec cmd begin:" + cmd);


			String[] args = new String[]{"sh", "-c", cmd};
			process = Runtime.getRuntime().exec(args);

			// 获取输出流
			infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			boolean bRead = true;
			String line_i = "";
			String line_e = "";

			while (bRead) {
				bRead = ((line_i = infoInput.readLine()) != null);
				// 正常输出
				if (line_i != null) {
					System.out.println(line_i);
					//output_info.append(line_i + "\r");
				}

				bRead = bRead || ((line_e = errorInput.readLine()) != null);
				// 异常输出
				if (line_e != null) {
					System.out.println(line_e);
					//output_error.append(line_e + "\r");
				}
			}

			// 等待结束
			exitValue = process.waitFor();

		} catch (Exception e) {
			System.out.println("Error" + e);
		} finally {
			if (infoInput != null) {
				try {
					if (infoInput != null) {
						infoInput.close();
					}
				} catch (IOException e) {
					System.out.println("Error" + e);
				}
			}
			if (errorInput != null) {
				try {
					if (errorInput != null) {
						errorInput.close();
					}
				} catch (IOException e) {
					System.out.println("Error" + e);
				}
				if (process != null) {
					process.destroy();
				}
			}

			return exitValue;
		}


	}

}
