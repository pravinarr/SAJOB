package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daoBean.Factor1Bean;

public class Factor1TableManipulation {
	Connection c = null;

	public Factor1TableManipulation() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:compuPIEMetaInfo.db");
			c.setAutoCommit(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Factor1Bean> getClientInfo(String id) {
		List<Factor1Bean> list = new ArrayList<Factor1Bean>();

		Statement stmt = null;
		ResultSet rs;
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM FACTOR1 where clientId =" + id + ";");
			while (rs.next()) {
				Factor1Bean info = new Factor1Bean();
				info.setId(rs.getInt("id"));
				info.setSocialRoleDescription(rs.getString("socialRoleDescription"));
				info.setProblemType(rs.getString("problemType"));
				info.setServerity(rs.getString("serverity"));
				info.setDuration(rs.getString("duration"));
				info.setCopingAbitity(rs.getString("copingAbitity"));
				info.setPriority(rs.getString("priority"));
				info.setGoal(rs.getString("goal"));
				info.setRecommendedInter(rs.getString("recommendedInter"));
				info.setExpectedOutcome(rs.getString("expectedOutcome"));
				info.setClientId(rs.getInt("clientId"));
				info.setFollowup(rs.getInt("followUp"));
				list.add(info);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getmaxId() {
		int id = 0;
		Statement stmt = null;
		ResultSet rs;
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT max(id) FROM FACTOR1;");
			while (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean saveNewClient(Factor1Bean info) {
		Statement stmt = null;
		int update = 0;
		try {
			stmt = c.createStatement();
			update = stmt.executeUpdate(createStringTOSave(info));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (update > 0);
	}

	public boolean updateNewClient(Factor1Bean info) {
		Statement stmt = null;
		int update = 0;
		try {
			stmt = c.createStatement();
			update = stmt.executeUpdate(createStringToUpdate(info));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (update > 0);
	}

	private String createStringTOSave(Factor1Bean info) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				"insert into FACTOR1 (id,socialRoleDescription,problemType,serverity,duration,copingAbitity,"
				+ "priority,goal,recommendedInter,expectedOutcome,clientId,followUp) ");
		buffer.append("(" + getmaxId() + 1);
		buffer.append(",\"" + info.getSocialRoleDescription() + "\"");
		buffer.append(",\"" + info.getProblemType() + "\"");
		buffer.append(",\"" + info.getServerity() + "\"");
		buffer.append(",\"" + info.getDuration() + "\"");
		buffer.append(",\"" + info.getCopingAbitity() + "\"");
		buffer.append(",\"" + info.getPriority() + "\"");
		buffer.append(",\"" + info.getGoal() + "\"");
		buffer.append(",\"" + info.getRecommendedInter() + "\"");
		buffer.append(",\"" + info.getExpectedOutcome() + "\"");
		buffer.append("," + info.getClientId());
		buffer.append("," + info.getFollowup()+");");
		return buffer.toString();
	}

	private String createStringToUpdate(Factor1Bean info) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("update FACTOR1 set ");
		buffer.append(",socialRoleDescription=\"" + info.getSocialRoleDescription() + "\"");
		buffer.append(",problemType=\"" + info.getProblemType() + "\"");
		buffer.append(",serverity=\"" + info.getServerity() + "\"");
		buffer.append(",duration=\"" + info.getDuration() + "\"");
		buffer.append(",copingAbitity=\"" + info.getCopingAbitity() + "\"");
		buffer.append(",priority=\"" + info.getPriority() + "\"");
		buffer.append(",goal=\"" + info.getGoal() + "\"");
		buffer.append(",recommendedInter=\"" + info.getRecommendedInter() + "\"");
		buffer.append(",expectedOutcome=\"" + info.getExpectedOutcome() + "\"");
		buffer.append(",clientId=" + info.getClientId());
		buffer.append(",followUp=" + info.getFollowup());
		buffer.append(" where id =" + info.getId()+";");
		return buffer.toString();
	}
}