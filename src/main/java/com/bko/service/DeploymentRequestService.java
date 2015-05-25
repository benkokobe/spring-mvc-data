package com.bko.service;

import java.util.List;

import com.bko.domain.Patch;
import com.bko.domain.PatchMember;
import com.bko.domain.TransferOperation;



public interface DeploymentRequestService {
	public List<Patch> getPatchList(String deploymentRequest);
	//public List<Patch> getPatchListComplete( String NAMLOT );
	public List<PatchMember> getDRMembers(String drName);
	public List<TransferOperation> getTransferOperation(String deploymentRequest);
	public String getRefLot(String drName);
	public List<TransferOperation> getMissingYe(String reflot);

}
