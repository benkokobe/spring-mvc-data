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
import com.bko.domain.PatchMember;
import com.bko.domain.TransferOperation;


public class PatchDaoImpl implements PatchDao{
	
	private final Logger logger = Logger.getLogger(PatchDaoImpl.class);
	//private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate jdbcTemplate2;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate2 = new NamedParameterJdbcTemplate(dataSource);
	}


	@SuppressWarnings("unchecked")
	public List<PatchMember> getPatchMember( String REFPAT ) {
	    try {
	      
	      MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("REFPAT", REFPAT);
	      
	      
	      //String sql = "SELECT REFPAT FROM YSW11 WHERE SYNDPR  = (SELECT SYNDPR FROM YSW10 WHERE NAMLOT = :NAMLOT)";
	      String sql = "SELECT * FROM YPD02_SYN WHERE REFPAT = :REFPAT";
          logger.info("getPatchMember"  + sql);
	      List<PatchMember> patchMembersList = jdbcTemplate2.query(sql,  params, new PatchMembersListRowMapper());
	      
	      return patchMembersList;
	    } catch ( DataAccessException exc ) {
	      //logger.error("FAILED to get PatchList List", exc);
	    	System.out.println("FAILED to get PatchMembers List "+ exc);
	      return new ArrayList<PatchMember>();
	    }
	  }
	public List<Patch> getPatchListComplete( String REFPAT ) {
	    try {
	    	System.out.println( "REFPAT: " + REFPAT);
	      //MapSqlParameterSource params = new MapSqlParameterSource( "NAMLOT", NAMLOT );
	      
	      MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("REFPAT", REFPAT);
	      
	      
	      //BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(drName);

	      String sql = "SELECT * FROM YPD01_SYN WHERE REFPAT = :REFPAT";
	      //String sql = "SELECT REFPAT FROM YFD06 WHERE REFLOT  = (SELECT REFLOT FROM YFD05 WHERE NAMLOT = :NAMLOT)";
	      //String sql = "SELECT REFPAT FROM YSW11 WHERE REFPAT = :NAMLOT";
	      System.out.println( sql );
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
	
	public List<TransferOperation> getTransferOperation(String REFMAI) {
		
		 try {
		MapSqlParameterSource params = new MapSqlParameterSource();
	      params.addValue("REFMAI", REFMAI);
		String query1 = "SELECT * FROM YPD23_SYN WHERE REFMAI =:REFMAI";
		/**
		 * Implement the RowMapper callback interface
		 */
		//return (List)jdbcTemplate.queryForObject(query1, new Cwd81RowMapper());
		List<TransferOperation> transferOperationList = jdbcTemplate2.query(query1, params,new TransferOperationsRowMapper());
		return transferOperationList;
		 } catch ( DataAccessException exc ) {
		      //logger.error("FAILED to get PatchList List", exc);
		    	System.out.println("FAILED to get Transfer operations List "+ exc);
		      return new ArrayList<TransferOperation>();
		}
	}
	
	/**
	* rowmapper is used by Spring to read a line from a database table 
	* and to fill an instance of the class with the values
	*/
	public class PatchMembersListRowMapper implements RowMapper {

		public PatchMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			PatchMember patchMember = new PatchMember();
			patchMember.setPatchId(rs.getString("REFPAT"));
			patchMember.setPatchMember(rs.getString("NOMMBR"));
			patchMember.setMemberType(rs.getString("TYPMBR"));
			patchMember.setTypAct(rs.getString("TYPACT"));
			return patchMember;
		}
	}
	public class TransferOperationsRowMapper implements RowMapper {

		public TransferOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
			// I use JDK 5 so I do not have to wrap int with an Integer object
			TransferOperation transferOperation = new TransferOperation();
			transferOperation.setIttCmd(rs.getString("ITTCMD"));
			transferOperation.setPatchRef(rs.getString("REFMAI"));
			//transferOperation.setBypass(rs.getString("BYPASS"));
			//transferOperation.setSwiChk(rs.getString("SWICHK"));
			//transferOperation.setSwiMan(rs.getString("SWIMAN"));
			//transferOperation.setTypTft(rs.getString("TYPTFT"));
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

	public Patch getPatch(String patchId) {
		// TODO Auto-generated method stub
		return null;
	}

}