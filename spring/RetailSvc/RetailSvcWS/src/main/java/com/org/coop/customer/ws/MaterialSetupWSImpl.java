package com.org.coop.customer.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.coop.canonical.beans.UIModel;
import com.org.coop.canonical.master.beans.MasterDataBean;
import com.org.coop.retail.service.MaterialSetupServiceImpl;
import com.org.coop.retail.service.RetailBranchSetupServiceImpl;

@RestController
@RequestMapping("/rest")
public class MaterialSetupWSImpl {
	
	private static final Logger log = Logger.getLogger(MaterialSetupWSImpl.class); 
	
	@Autowired
	private MaterialSetupServiceImpl materialSetupServiceImpl;
	
	@RequestMapping(value = "/getMaterialGroup", method = RequestMethod.GET, headers="Accept=application/json",produces="application/json")
	public UIModel getMaterialGroup(@RequestParam(value = "branchId",required = true,defaultValue = "0") Integer branchId,
										@RequestParam(value = "materialGroupId",required = true,defaultValue = "0") Integer materialGroupId) {
		UIModel uiModel = new UIModel();
		try {
			uiModel = materialSetupServiceImpl.getMaterialGroup(branchId, materialGroupId);
		} catch (Exception e) {
			log.error("Error Retrieving material group by mterial group Id", e);
			uiModel.setErrorMsg("Error Retrieving material group by material group Id: " + materialGroupId);
		}
		return uiModel;
	}
	
	@RequestMapping(value = "/saveMaterialGroup", method = RequestMethod.POST, headers="Accept=application/json",produces="application/json")
	public UIModel saveMaterialGroup(@RequestBody UIModel uiModel) {
		try {
			uiModel = materialSetupServiceImpl.saveMaterialGroup(uiModel);
		} catch (Exception e) {
			log.error("Error while adding material group", e);
			uiModel.setErrorMsg("Error while adding material group for a branch: " + uiModel.getBranchBean().getBranchId());
		}
		return uiModel;
	}
	
	@RequestMapping(value = "/deleteMaterialGroup", method = RequestMethod.DELETE, headers="Accept=application/json",produces="application/json",consumes="application/json")
	public UIModel deleteMaterialGroup(@RequestBody UIModel uiModel) {
		try {
			uiModel = materialSetupServiceImpl.deleteMaterialGroup(uiModel);
			
		} catch (Exception e) {
			log.error("Error while deleting Material group", e);
			uiModel.setErrorMsg("Error while deleting Material Group");
		}
		return uiModel;
	}
	
	@RequestMapping(value = "/getMaterial", method = RequestMethod.GET, headers="Accept=application/json",produces="application/json")
	public UIModel getMaterial(@RequestParam(value = "branchId",required = true,defaultValue = "0") Integer branchId,
										@RequestParam(value = "materialId",required = true,defaultValue = "0") Integer materialId) {
		UIModel uiModel = new UIModel();
		try {
			uiModel = materialSetupServiceImpl.getMaterial(branchId, materialId);
		} catch (Exception e) {
			log.error("Error Retrieving material by mterial Id", e);
			uiModel.setErrorMsg("Error Retrieving material by material Id: " + materialId);
		}
		return uiModel;
	}
	
	@RequestMapping(value = "/saveMaterial", method = RequestMethod.POST, headers="Accept=application/json",produces="application/json")
	public UIModel saveMaterial(@RequestBody UIModel uiModel) {
		try {
			uiModel = materialSetupServiceImpl.saveMaterial(uiModel);
		} catch (Exception e) {
			log.error("Error while adding material", e);
			uiModel.setErrorMsg("Error while adding material for a material group: " + uiModel.getBranchBean().getMaterialGroups().get(0).getMaterialGrpId());
		}
		return uiModel;
	}
	
	@RequestMapping(value = "/deleteMaterial", method = RequestMethod.DELETE, headers="Accept=application/json",produces="application/json",consumes="application/json")
	public UIModel deleteMaterial(@RequestBody UIModel uiModel) {
		try {
			uiModel = materialSetupServiceImpl.deleteMaterial(uiModel);
			
		} catch (Exception e) {
			log.error("Error while deleting Material", e);
			uiModel.setErrorMsg("Error while deleting Material");
		}
		return uiModel;
	}
}
