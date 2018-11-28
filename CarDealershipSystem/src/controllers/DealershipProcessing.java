package controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.DaoModel;

public class DealershipProcessing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DaoModel dao = new DaoModel();
//	    Connection c = null;
//	    DatabaseMetaData dbm = null;
//		try {
//			dbm = c.getMetaData();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    ResultSet rs = null;
//		try {
//			rs = dbm.getTables(null, null, "employee", null);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    try {
//			if (rs.next()) {
//			  System.out.println("Table exists"); 
//			} else {
//			  System.out.println("Table does not exist"); 
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		dao.createTable();

	}

}