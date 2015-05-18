package com.bko.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bko.domain.Patch;
import com.bko.domain.TransferOperation;

public class DeploymentRequestDaoImpl implements DeploymentRequestDao{
	
	private final Logger logger = Logger.getLogger(DeploymentRequestDaoImpl.class);
	//private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate jdbcTemplate2;
	//private BeanPropertySqlParameterSource namedParameters;

	public void setDataSource(DataSource dataSource) {
		//this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcTemplate2 = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Patch> getPatchList(String drName) {

		System.out.println("DEBUG:getPatchList");
		try {
		MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("drName", drName);
		String query1 = "SELECT REFMAI FROM yfd06 WHERE REFLOT =:drName";
		System.out.println("DEBUG: SQL:" + query1);
		/**
		 * Implement the RowMapper callback interface
		 */
		//return (List)jdbcTemplate.queryForObject(query1, new Cwd81RowMapper());
		List<Patch> patchList = jdbcTemplate2.query(query1, params,new PatchListRowMapper2());
		return patchList;
		 } catch ( DataAccessException exc ) {
		      //logger.error("FAILED to get PatchList List", exc);
		    	System.out.println("FAILED to get transfer op. List "+ exc);
		      return new ArrayList<Patch>();
		}

	}
	@SuppressWarnings("unchecked")
	public List<Patch> getPatchList2( String NAMLOT ) {
	    try {
	      //MapSqlParameterSource params = new MapSqlParameterSource( "NAMLOT", NAMLOT );
	      
	      MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("NAMLOT", NAMLOT);
	      
	      
	      //BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(drName);

	      //String sql = "SELECT REFPAT FROM YSW11 WHERE SYNDPR  = (SELECT SYNDPR FROM YSW10 WHERE NOMDPR = :NAMLOT)";
	      String sql = "SELECT REFMAI FROM YFD06 WHERE REFLOT  = (SELECT REFLOT FROM YFD05 WHERE NAMLOT =:NAMLOT)";
	      //String sql = "SELECT REFPAT FROM YFD06 WHERE REFLOT  = (SELECT REFLOT FROM YFD05 WHERE NAMLOT = :NAMLOT)";
	      //String sql = "SELECT REFPAT FROM YSW11 WHERE REFPAT = :NAMLOT";
	      logger.info( "SQL getPatchList2: " + ":"+ NAMLOT+":"+ sql );
	      //System.out.println("DEBUG:" + sql);
	      //List<Patch> patches = jdbcTemplate2.query(sql,  params, new PatchListRowMapper());
	      List<Patch> patches = jdbcTemplate2.query(sql,  params, new PatchListRowMapper());
	      //namedParameters
	      return patches;
	    } catch ( DataAccessException exc ) {
	      //logger.error("FAILED to get PatchList List", exc);
	    	System.out.println("FAILED to get PatchList List"+ exc);
	      return new ArrayList<Patch>();
	    }
	  }
	@SuppressWarnings("unchecked")
	public List<Patch> getPatchListComplete( String REFPAT ) {
	    try {
	    	logger.info( "REFPAT: " + REFPAT);
	      //MapSqlParameterSource params = new MapSqlParameterSource( "NAMLOT", NAMLOT );
	      
	      MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("REFPAT", REFPAT);
	      
	      
	      //BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(drName);

	      String sql = "SELECT * FROM YPD01_SYN WHERE REFPAT = :REFPAT";
	      //String sql = "SELECT REFPAT FROM YFD06 WHERE REFLOT  = (SELECT REFLOT FROM YFD05 WHERE NAMLOT = :NAMLOT)";
	      //String sql = "SELECT REFPAT FROM YSW11 WHERE REFPAT = :NAMLOT";
	      logger.info( sql );
	      //System.out.println("DEBUG:" + sql);
	      //List<Patch> patches = jdbcTemplate2.query(sql,  params, new PatchListRowMapper());
	      List<Patch> patches = jdbcTemplate2.query(sql,  params, new PatchCompleteListRowMapper());
	      //namedParameters
	      return patches;
	    } catch ( DataAccessException exc ) {
	      //logger.error("FAILED to get PatchList List", exc);
	    	System.out.println("FAILED to get PatchList List"+ exc);
	      return new ArrayList<Patch>();
	    }
	  }
	public List<TransferOperation> getTransferOperation(String NAMLOT) {
		
		logger.info("DEBUG:getTransferOperation");
		try {
		MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("NAMLOT", NAMLOT);
		String query1 = "SELECT * FROM yfd07 WHERE REFLOT =:NAMLOT";
		System.out.println("DEBUG: SQL:" + query1);
		/**
		 * Implement the RowMapper callback interface
		 */
		//return (List)jdbcTemplate.queryForObject(query1, new Cwd81RowMapper());
		List<TransferOperation> transferOperationList = jdbcTemplate2.query(query1, params,new TransferOperationsRowMapper());
		return transferOperationList;
		 } catch ( DataAccessException exc ) {
		      //logger.error("FAILED to get PatchList List", exc);
		    	System.out.println("FAILED to get transfer op. List "+ exc);
		      return new ArrayList<TransferOperation>();
		}
	}
	public String getRefLot(String drName) {

	    String sql = "SELECT REFLOT FROM YFD05 WHERE NAMLOT = :drNAME";

	    MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("drName", drName);
		//return (List)jdbcTemplate.queryForObject(query1, new Cwd81RowMapper());
		return (String) jdbcTemplate2.queryForObject(sql, params, String.class);

	}

	public class TransferOperationsRowMapper implements RowMapper {

		public TransferOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			TransferOperation transferOperation = new TransferOperation();
			transferOperation.setIttCmd(rs.getString("ITTCMD"));
			transferOperation.setPatchRef(rs.getString("REFMAI"));
			transferOperation.setBypass(rs.getString("BYPASS"));
			transferOperation.setSwiChk(rs.getString("SWICHK"));
			transferOperation.setSwiMan(rs.getString("SWIMAN"));
			transferOperation.setTypTft(rs.getString("TYPTFT"));
			transferOperation.setStpAll(rs.getString("STPALL"));
			return transferOperation;
		}
	}
	public class PatchCompleteListRowMapper implements RowMapper {

		public Patch mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			//System.out.println("PatchListRowMapper");
			Patch patch = new Patch();
			patch.setPatchId(rs.getString("REFPAT"));
			patch.setNomGrp(rs.getString("NOMGRP"));
			patch.setVerPat(rs.getString("VERPAT"));
			patch.setSujPat(rs.getString("SUJPAT"));
			//System.out.println("RESULT: " + rs.getString("REFPAT"));
			return patch;
		}
	}

	/**
	* rowmapper is used by Spring to read a line from a database table 
	* and to fill an instance of the class with the values
	*/
	public class PatchListRowMapper implements RowMapper {

		public Patch mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			//System.out.println("PatchListRowMapper");
			Patch patch = new Patch();
			patch.setPatchId(rs.getString("REFMAI"));
			//System.out.println("RESULT: " + rs.getString("REFPAT"));
			return patch;
		}
	}
	//Row mapper for YFD06 ( refmai)
	public class PatchListRowMapper2 implements RowMapper {

		public Patch mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			//System.out.println("PatchListRowMapper2");
			Patch patch = new Patch();
			patch.setPatchId(rs.getString("REFMAI"));
			//System.out.println("RESULT: " + rs.getString("REFMAI"));
			return patch;
		}
	}

}
