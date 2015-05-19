package com.bko.domain;

import java.util.List;

public class DeploymentRequest {

	private String drName;
	private List<Patch> patchList;
	public String getDrName() {
		return drName;
	}
	public void setDrName(String drName) {
		this.drName = drName;
	}
	public List<Patch> getPatchList() {
		return patchList;
	}
	public void setPatchList(List<Patch> patchList) {
		this.patchList = patchList;
	}


}
