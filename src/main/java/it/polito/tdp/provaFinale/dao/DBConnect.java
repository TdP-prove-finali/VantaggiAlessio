package it.polito.tdp.provaFinale.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBConnect {

	private static String jdbcURL = "jdbc:mariadb://localhost/statisticheucl_2021-2022";
	private static HikariDataSource ds;

	public static Connection getConnection() {

		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("");
			
			// configurazione MySQL
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			ds = new HikariDataSource(config);
		}

		try {
			Connection c = ds.getConnection();
			return c;
		} catch (SQLException e) {
			System.out.println("Errore: connessione al database fallita");
			e.printStackTrace();
			return null;
		}

	}

}