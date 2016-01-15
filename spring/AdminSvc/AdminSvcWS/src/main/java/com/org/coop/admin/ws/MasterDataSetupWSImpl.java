package com.org.coop.admin.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.coop.admin.service.BranchSetupServiceImpl;
import com.org.coop.admin.service.MasterDataSetupServiceImpl;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.canonical.master.beans.CountryMasterBean;
import com.org.coop.canonical.master.beans.MasterDataBean;

@RestController
@RequestMapping("/rest")
public class MasterDataSetupWSImpl {
	
	private static final Logger log = Logger.getLogger(MasterDataSetupWSImpl.class); 
	
	@Autowired
	private MasterDataSetupServiceImpl masterDataSetupServiceImpl;
	
	@RequestMapping(value = "/saveCountryStateDist", method = RequestMethod.POST, headers="Accept=application/json",produces="application/json",consumes="application/json")
	public MasterDataBean saveCountryStateDist(@RequestBody MasterDataBean masterDataBean) {
		try {
			masterDataSetupServiceImpl.saveCountryStateDist(masterDataBean);
			masterDataBean = getCountryStateDist(((CountryMasterBean)masterDataBean.getCountries().toArray()[0]).getCountryId());
		} catch (Exception e) {
			log.error("Error Creating/Updating country/state/district", e);
			masterDataBean.setErrorMsg("Error Creating/Updating country/state/district");
		}
		return masterDataBean;
	}
	
	@RequestMapping(value = "/getCountryStateDist", method = RequestMethod.GET, headers="Accept=application/json",produces="application/json")
	public MasterDataBean getCountryStateDist(@RequestParam(value = "countryId",required = true,defaultValue = "0") Integer countryId) {
		MasterDataBean masterData = new MasterDataBean();
		try {
			masterData = masterDataSetupServiceImpl.getCountryStateDist(countryId);
		} catch (Exception e) {
			log.error("Error Retrieving Country by Country Id", e);
			masterData.setErrorMsg("Error Retrieving Country by Country Id: " + countryId);
		}
		return masterData;
	}
	
}
