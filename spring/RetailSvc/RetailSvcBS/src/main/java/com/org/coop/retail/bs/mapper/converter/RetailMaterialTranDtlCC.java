package com.org.coop.retail.bs.mapper.converter;

import org.dozer.CustomConverter;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.coop.canonical.retail.beans.MaterialTranDtlBean;
import com.org.coop.retail.entities.BranchMaster;
import com.org.coop.retail.entities.MaterialTranDtl;
import com.org.coop.retail.entities.MaterialTranHrd;
import com.org.coop.retail.entities.StockEntry;
import com.org.coop.retail.repositories.RetailBranchMasterRepository;
import com.org.coop.retail.repositories.RetailMaterialTranHrdRepository;
import com.org.coop.retail.repositories.RetailStockEntryRepository;

@Component("retailMaterialTranDtlCC")
public class RetailMaterialTranDtlCC extends DozerConverter<MaterialTranDtlBean, MaterialTranDtl> implements MapperAware, CustomConverter {
	@Autowired
	private RetailBranchMasterRepository retailBranchMasterRepository;
	
	@Autowired
	private RetailMaterialTranHrdRepository retailMaterialTranHrdRepository;
	
	@Autowired
	private RetailStockEntryRepository retailStockEntryRepository;
	
	public RetailMaterialTranDtlCC() {
		super(MaterialTranDtlBean.class, MaterialTranDtl.class);
	}
	
	public RetailMaterialTranDtlCC(Class prototypeA, Class prototypeB) {
		super(prototypeA, prototypeB);
	}
	
	public void setMapper(Mapper arg0) {
		
	}

	@Override
	public MaterialTranDtlBean convertFrom(MaterialTranDtl src, MaterialTranDtlBean dest) {
		return null;
	}

	@Override
	public MaterialTranDtl convertTo(MaterialTranDtlBean src, MaterialTranDtl dest) {
		if(src != null) {
			BranchMaster branch = retailBranchMasterRepository.findOne(src.getBranchId());
			StockEntry stock = retailStockEntryRepository.findOne(src.getStockId());
			MaterialTranHrd materialTranHrd = retailMaterialTranHrdRepository.findOne(src.getTranId());
			dest.setBranchMaster(branch);
			dest.setStockEntry(stock);
			dest.setMaterialTranHrd(materialTranHrd);
		}
		return dest;
	}
}
