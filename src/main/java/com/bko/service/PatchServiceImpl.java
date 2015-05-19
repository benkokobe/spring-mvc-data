package com.bko.service;

import java.util.List;

import com.bko.domain.Patch;
import com.bko.domain.PatchMember;
import com.bko.domain.TransferOperation;
import com.bko.persistence.PatchDao;


public class PatchServiceImpl implements PatchService{
	
	private PatchDao patchDao;

	public void setPatchDao(PatchDao patchDao) {
		this.patchDao = patchDao;
	}

	public List<PatchMember> getPatchMember(String REFPAT) {
		return patchDao.getPatchMember(REFPAT);
	}

	public List<TransferOperation> getTransferOperation(String REFMAI) {
		return patchDao.getTransferOperation(REFMAI);
	}
	
	public List<Patch> getPatchListComplete(String refpat) {
		return this.patchDao.getPatchListComplete(refpat);
	}

}
