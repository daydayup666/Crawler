package tao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 
 * @author Administrator
 *
 */
public class BioComputingPageProcessor extends PageProcessor {

	private List<CommitteeMember> member = new ArrayList<CommitteeMember>();

	private Matcher matcher;

	@Override
	public void fetchInfo(File file) {
		String str = null;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((str = reader.readLine()) != null) {
				matcher = Pattern.compile(
						"[A-Za-z]+\\sCommittee|" + "[A-Za-z]+\\scommittee")
						.matcher(str);
				while (matcher.find()) {
					String committee = matcher.group();
					if (committee.matches("Steering Committee")) {
						System.out.println("Steering Committee");
						String mem = null;
						while ((mem = reader.readLine()) != null) {
							boolean isEnd = false;
							matcher = Pattern.compile("[A-Za-z\\s,\\.\\(\\)]{15,}</span></p>|[A-Za-z\\s,\\.\\(\\)]{15,}").matcher(mem);
							
							if (matcher.find()) {
								mem = matcher.group();
							} else {
								mem = null;
							}

							if (mem != null) {								
								if (mem.endsWith("</span></p>")) {
									mem = mem.replaceAll("</span></p>", "");
									isEnd = true;
								}
								CommitteeMember committeeMember = new CommitteeMember();
								committeeMember.setCommittee("Steering committee");
								committeeMember = get(committeeMember,mem);
							
								member.add(committeeMember);
								if(isEnd) break;
							}
						}

					} else if (committee.matches("Organizing committee")) {
						System.out.println("Organizing committee");
						String mem = null;
						while((mem = reader.readLine()) != null) {
							boolean isEnd = false;
							matcher = Pattern.compile("[A-Z][A-Za-z\\s,\\.-]{10,}</h3>|[A-Z][A-Za-z\\s,\\.-]{10,}").matcher(mem);
							if(matcher.find()) {
								mem = matcher.group();
							}else {
								mem = null;
							}
							if(mem != null) {
								if(mem.endsWith("</h3>")) {
									mem = mem.replaceAll("</h3>", "");
									isEnd = true;
								}
								CommitteeMember committeeMember = new CommitteeMember();
								committeeMember.setCommittee("Organizing committee");
								committeeMember = get(committeeMember,mem);
							
								member.add(committeeMember);
								if(isEnd)  break;
							}
						}
					} else if (committee.matches("Program committee")) {
						System.out.println("Program committee");
						String mem = null;
						while((mem = reader.readLine()) != null) {
							boolean isEnd = false;
							matcher = Pattern.compile("[A-Za-z\\s-,\\.\\(\\)\\d;#&]{15,}</h3>|[A-Za-z\\s-,\\.\\(\\)\\d;#&]{15,}").matcher(mem);
							if(matcher.find()) {
								mem = matcher.group();
							}else {
								mem = null;
							}
							if(mem!= null) {
								if(mem.endsWith("</h3>")){
									mem = mem.replaceAll("</h3>", "");
									isEnd = true;
								}
								if(mem.contains("&#8217;")) {
									mem = mem.replaceAll("&#8217;", "'");
								}
								CommitteeMember committeeMember = new CommitteeMember();
								committeeMember.setCommittee("Program committee");
								committeeMember = get(committeeMember,mem);
							
								member.add(committeeMember);
								if(isEnd)  break;
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("文件不存在！");
		} catch (IOException e) {
			System.out.println("文件读取错误!");
		}
		for(CommitteeMember mem :member) {
			System.out.println(mem.getCommittee()+", "
					+mem.getName() + ", "+mem.getCollege() + ", "
					+mem.getNationality());
		}
	}

	public CommitteeMember get(CommitteeMember committeeMember,String str) {
		
		String[] strVar = str.split(", ");
		if (strVar.length == 2) {
			committeeMember.setName(strVar[0]);
			committeeMember.setNationality(strVar[1]);
		} else if (strVar.length == 3) {
			committeeMember.setName(strVar[0]);
			committeeMember.setCollege(strVar[1]);
			committeeMember.setNationality(strVar[2]);
		} else if (strVar.length == 4) {
			committeeMember.setName(strVar[0]);
			committeeMember.setCollege(strVar[1]);
			committeeMember.setNationality(strVar[2] + " of " + strVar[2]);
		}
		return committeeMember;
	}

}

class CommitteeMember {
	private String committee;

	private String name;
	private String college;
	private String nationality;

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
