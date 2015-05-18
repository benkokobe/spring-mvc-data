package com.bko.persistence;

import java.util.List;

import com.bko.domain.Patch;
import com.bko.domain.TransferOperation;



public interface DeploymentRequestDao {
	public List<Patch> getPatchList(String deploymentRequest);
	public List<Patch> getPatchList2(String deploymentRequest);
	public List<Patch> getPatchListComplete( String NAMLOT );
	public List<TransferOperation> getTransferOperation(String deploymentRequest);

}
