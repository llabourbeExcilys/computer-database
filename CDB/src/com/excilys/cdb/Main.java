package com.excilys.cdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("helloworld");
		
	
	  try {
	      Class.forName("com.mysql.cj.jdbc.Driver");

	      String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	      String user = "admincdb";
	      String passwd = "qwerty1234";

	      
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	      System.out.println("Connexion effective !");         
	      
	      //Création d'un objet Statement
	      Statement state = conn.createStatement();
	      //L'objet ResultSet contient le résultat de la requête SQL
	      ResultSet result = state.executeQuery("SELECT * FROM company");
	      //On récupère les MetaData
	      ResultSetMetaData resultMeta = result.getMetaData();
	         
	      System.out.println("\n**********************************");
	      //On affiche le nom des colonnes
	      for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	        System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
	         
	      System.out.println("\n**********************************");
	         
	      while(result.next()){         
	        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	          System.out.print("\t" + result.getObject(i).toString() + "\t |");
	            
	        System.out.println("\n---------------------------------");

	      }

	      result.close();
	      state.close();
	      
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }      
	  }
		
	}

