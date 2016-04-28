package com.org.coop.retail.servicehelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coop.org.exception.PaymentException;
import com.coop.org.exception.RetailException;
import com.coop.org.exception.RetailStockEntryException;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.org.coop.bs.util.AdminSvcCommonUtil;
import com.org.coop.bs.util.RetailBusinessConstants;
import com.org.coop.canonical.account.beans.GlLedgerDtlBean;
import com.org.coop.canonical.account.beans.GlLedgerHrdBean;
import com.org.coop.canonical.account.beans.TransactionPaymentBean;
import com.org.coop.canonical.beans.BranchBean;
import com.org.coop.canonical.beans.UIModel;
import com.org.coop.canonical.retail.beans.MaterialTranDtlBean;
import com.org.coop.canonical.retail.beans.MaterialTranHrdBean;
import com.org.coop.canonical.retail.beans.RetailStockEntryBean;
import com.org.coop.retail.bs.mapper.GlLedgerMappingImpl;
import com.org.coop.retail.bs.mapper.PaymentMappingImpl;
import com.org.coop.retail.bs.mapper.RetailMaterialTransactionMappingImpl;
import com.org.coop.retail.bs.mapper.RetailStockEntryMappingImpl;
import com.org.coop.retail.bs.mapper.RetailStockSaleMappingImpl;
import com.org.coop.retail.entities.BranchMaster;
import com.org.coop.retail.entities.Customer;
import com.org.coop.retail.entities.GlLedgerDtl;
import com.org.coop.retail.entities.GlLedgerHrd;
import com.org.coop.retail.entities.GlMaster;
import com.org.coop.retail.entities.LedgerCodeRetailPurchase;
import com.org.coop.retail.entities.LedgerCodeRetailSale;
import com.org.coop.retail.entities.MaterialTranDtl;
import com.org.coop.retail.entities.MaterialTranHrd;
import com.org.coop.retail.entities.QStockEntry;
import com.org.coop.retail.entities.RetailPayment;
import com.org.coop.retail.entities.RetailTransaction;
import com.org.coop.retail.entities.StockEntry;
import com.org.coop.retail.entities.TransactionPayment;
import com.org.coop.retail.repositories.CustomerRepository;
import com.org.coop.retail.repositories.GlLedgerDtlRepository;
import com.org.coop.retail.repositories.GlLedgerHeaderRepository;
import com.org.coop.retail.repositories.GlMasterRepository;
import com.org.coop.retail.repositories.LedgerCodeRetailPurchaseRepository;
import com.org.coop.retail.repositories.LedgerCodeRetailSaleRepository;
import com.org.coop.retail.repositories.RetailBranchMasterRepository;
import com.org.coop.retail.repositories.RetailMaterialMasterRepository;
import com.org.coop.retail.repositories.RetailMaterialTranDtlRepository;
import com.org.coop.retail.repositories.RetailMaterialTranHrdRepository;
import com.org.coop.retail.repositories.RetailPaymentRepository;
import com.org.coop.retail.repositories.RetailStockEntryRepository;
import com.org.coop.retail.repositories.RetailStockReturnRepository;
import com.org.coop.retail.repositories.RetailTransactionRepository;
import com.org.coop.retail.repositories.TransactionPaymentRepository;
import com.org.coop.retail.service.FinancialYearCloseServiceImpl;

@Service
public class RetailStockManagementServiceHelperImpl {

	private static final Logger log = Logger.getLogger(RetailStockManagementServiceHelperImpl.class); 
	
	@Autowired
	private TransactionsServiceHelperImpl txServiceHelper;
	
	@Autowired
	private RetailStockEntryRepository retailStockEntryRepository;
	
	@Autowired
	private RetailStockEntryMappingImpl retailStockEntryMappingImpl;
	
	@Autowired
	private RetailStockReturnRepository retailStockReturnRepository;
	
	@Autowired
	private RetailMaterialMasterRepository retailMaterialMasterRepository;
	
	@Autowired
	private RetailBranchMasterRepository retailBranchMasterRepository;
	
	@Autowired
	private AdminSvcCommonUtil adminSvcCommonUtil;
	
	@Autowired
	private FinancialYearCloseServiceImpl financialYearCloseServiceImpl;
	
	@Autowired
	private RetailMaterialTranHrdRepository retailMaterialTranHrdRepository;
	
	@Autowired
	private GlLedgerHeaderRepository glLedgerHeaderRepository;
	
	@Autowired
	private GlLedgerMappingImpl glLedgerMappingImpl;
	
	@Autowired
	private RetailMaterialTransactionMappingImpl retailMaterialTransactionMappingImpl;
	
	@Autowired
	private RetailTransactionRepository retailTransactionRepository;
	
	@Autowired
	private PaymentServiceHelperImpl paymentServiceHelperImpl;
	
	@Autowired
	private TransactionPaymentRepository transactionPaymentRepository;
	
	@Autowired
	private RetailPaymentRepository retailPaymentRepository;
	
	@Autowired
	private PaymentMappingImpl paymentMappingImpl;
	
	@Autowired 
	private GlMasterRepository glMasterRepository;
	
	@Autowired 
	private GlLedgerDtlRepository glLedgerDtlRepository;
	
	@Autowired
	private LedgerCodeRetailPurchaseRepository retailLedgerCodePurchaseRepository;
	
	@Autowired
	private RetailStockSaleMappingImpl retailStockSaleMappingImpl;
	
	@Autowired
	private RetailMaterialTranDtlRepository retailMaterialTranDtlRepository;
	
	@Autowired
	private CustomerSetupServiceHelperImpl customerSetupServiceHelperImpl;
	
	@Autowired
	private LedgerCodeRetailSaleRepository ledgerCodeRetailSaleRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Transactional(value="retailTransactionManager")
	public UIModel getStockEntries(int branchId, int vendorId, int materialId, int pageNo, int recordsPerPage, String challanNo, String billNo, Date startDate, Date endDate) {
		UIModel uiModel = new UIModel();
		Page<StockEntry> stocks = null;
		
		// Retrieve only selected 
		if(StringUtils.isNotBlank(billNo) && branchId > 0) {
			
		}
		
		
		if(recordsPerPage == 0) recordsPerPage = 1000000;
		if(startDate == null || endDate == null) {
			Date today = new Date();
			String fy = adminSvcCommonUtil.getFinancialYear(today);
			uiModel = adminSvcCommonUtil.getFYStartAndEndDate(uiModel, fy);
			startDate = uiModel.getBranchBean().getFyStartDate();
			endDate = uiModel.getBranchBean().getFyEndDate();
		}
		
		QStockEntry stockdsl = QStockEntry.stockEntry;
		Predicate predicate = null;
		PageRequest pageRequest = new PageRequest(pageNo - 1, recordsPerPage, new QSort(stockdsl.challanDate.asc()));
		int recordCount = 0;
		if(branchId > 0) {
			BooleanExpression branchStocks = stockdsl.branchMaster.branchId.eq(branchId);
			if(vendorId > 0) branchStocks = branchStocks.and(stockdsl.vendorMaster.vendorId.eq(vendorId));
			if(materialId > 0) branchStocks = branchStocks.and(stockdsl.materialMaster.materialId.eq(materialId));
			if(StringUtils.isNotBlank(challanNo)) branchStocks = branchStocks.and(stockdsl.challanNo.eq(challanNo));
			if(StringUtils.isNotBlank(billNo)) branchStocks = branchStocks.and(stockdsl.billNo.eq(billNo));
			if(StringUtils.isNotBlank(challanNo) || StringUtils.isNotBlank(billNo)) { // Invalidating startdate and enddate
				startDate = adminSvcCommonUtil.getDatesByOffsetDays(startDate, -10000);
				endDate = adminSvcCommonUtil.getDatesByOffsetDays(endDate, 10000);
			}
			predicate = branchStocks.and(stockdsl.actionDate.between(startDate, endDate));
			stocks = retailStockEntryRepository.findAll(predicate, pageRequest);
			recordCount = (int)stocks.getTotalElements();
		}
		
		
		if(stocks != null && stocks.getSize() > 0) {
			uiModel.setRecordCount(recordCount);
			uiModel.setPageNo(pageNo);
			uiModel.setRecordsPerPage(recordsPerPage);
			
			List<RetailStockEntryBean> stocksBean = new ArrayList<RetailStockEntryBean>();
			for(StockEntry stock : stocks) {
				RetailStockEntryBean stockBean = new RetailStockEntryBean();
				retailStockEntryMappingImpl.mapBean(stock, stockBean);
				stocksBean.add(stockBean);
			}
			
			uiModel.setBranchBean(new BranchBean());
			uiModel.getBranchBean().setStockEntries(stocksBean);
			
			if(StringUtils.isNotBlank(billNo)) { // Get payment details as well
				MaterialTranHrd materialTranHrd = retailMaterialTranHrdRepository.findByBranchIdAndBillNo(branchId, billNo);
				if(materialTranHrd != null) {
					if(log.isDebugEnabled()) {
						log.debug("Going to retrieve payment details for material tran id: " + materialTranHrd.getTranId());
					}
					TransactionPaymentBean transactionPaymentBean = new TransactionPaymentBean();
					paymentMappingImpl.mapBean(materialTranHrd, transactionPaymentBean);
					uiModel.getBranchBean().setPayments(new ArrayList<TransactionPaymentBean>());
					uiModel.getBranchBean().getPayments().add(transactionPaymentBean);
					UIModel tempModel = paymentServiceHelperImpl.getPayment(branchId, materialTranHrd.getRetailTransactions().get(0).getGlLedgerHrd().getGlTranId(),null, null, 1, 100);
					
					
					transactionPaymentBean.setCashRegisters(tempModel.getBranchBean().getPayments().get(0).getCashRegisters());
					transactionPaymentBean.setCardRegisters(tempModel.getBranchBean().getPayments().get(0).getCardRegisters());
					transactionPaymentBean.setCreditRegisters(tempModel.getBranchBean().getPayments().get(0).getCreditRegisters());
					transactionPaymentBean.setChequeRegisters(tempModel.getBranchBean().getPayments().get(0).getChequeRegisters());
					transactionPaymentBean.setLoanRegisters(tempModel.getBranchBean().getPayments().get(0).getLoanRegisters());
				}
			}
		} else {
			if(log.isDebugEnabled()) {
				log.debug("No Stock-in records found for given input");
			}
		}
		return uiModel;
	}
	
	/**
	 * This method can save the following scenarios <br/>
	 * 1. a. Fresh stock entry (without bill) where parentStockId = 0 and entry_type = 'NEW' <br/>
	 * &nbsp;&nbsp;&nbsp; b. Fresh stock purchase (with bill) where parentStockId = 0 and entry_type = 'PURCHASE' <br/>
	 * 2. a. Opening stock entry (after year close) where parentStockId > 0 and entry_type = 'YR_CLOSE_OPENING'. <br/>
	 * &nbsp;&nbsp;&nbsp; b. Opening stock entry (1st time installation) where parentStockId = 0 and entry_type = 'OPENING'. <br/>
	 * 3. a. Purchase return stock entry where parentStockId > 0 and entry_type = 'STOCK_RETURN' <br/>
	 * &nbsp;&nbsp;&nbsp; b. stock return (before purchase) stock where parentStockId > 0 and entry_type = 'STOCK_RETURN'  <br/>
	 * 4. TODO: Shortage after year close where parentStockId > 0 and entry_type = 'SHORTAGE'. <br/>
	 * 5. TODO: a. Stock transfer among branches where parentStockId > 0 and entry_type = 'TRANSFER'. <br/>
	 * &nbsp;&nbsp;&nbsp; b. Stock return from branch where parentStockId > 0 and entry_type = 'TRANSFER_RETURN'. <br/>
	 * @param uiModel
	 * @return
	 */
	@Transactional(value="retailTransactionManager")
	public UIModel saveStockEntries(UIModel uiModel) {
		if(uiModel.getBranchBean().getStockEntries() != null && uiModel.getBranchBean().getStockEntries().size() > 0 ) {
			
			List<RetailStockEntryBean> stocksBean = uiModel.getBranchBean().getStockEntries();
			List<StockEntry> stocks = new ArrayList<StockEntry>();
			for(RetailStockEntryBean stockBean : stocksBean) {
				int parentStockId = stockBean.getParentStockId();
				int stockId = stockBean.getStockId();
				int branchId = stockBean.getBranchId();
				int vendorId = stockBean.getVendorId();
				int materialId = stockBean.getMaterialId();
				String batch = stockBean.getBatch();
				
				//**********************
				// Validate the input
				//**********************
				isValidStockInput(stockBean);
				
				// RULE: If there is a update on bill number, challan number, purchased price, batch number then update all its children
				if(stockId > 0) {
					List<StockEntry> childStocks = retailStockEntryRepository.findAllChildrenByStockId(stockId);
					if(childStocks != null && childStocks.size() > 0) {
						for(StockEntry stk : childStocks) {
							if(stockBean.getBillDate() != null) stk.setBillDate(stockBean.getBillDate());
							if(stockBean.getBillNo() != null) stk.setBillNo(stockBean.getBillNo());
							if(stockBean.getBatch() != null) stk.setBatch(stockBean.getBatch());
							if(stockBean.getChallanDate() != null) stk.setChallanDate(stockBean.getChallanDate());
							if(stockBean.getPurchasePrice() != null) stk.setPurchasePrice(stockBean.getPurchasePrice());
							
							// Child of child stock to be updated in case of TRANSFER_RETURN
							List<StockEntry> childOfChildStocks = retailStockEntryRepository.findAllChildrenByStockId(stk.getStockId());
							if(childOfChildStocks != null && childOfChildStocks.size() > 0) {
								for(StockEntry tempStk : childOfChildStocks) {
									if(stockBean.getBillDate() != null) tempStk.setBillDate(stockBean.getBillDate());
									if(stockBean.getBillNo() != null) tempStk.setBillNo(stockBean.getBillNo());
									if(stockBean.getBatch() != null) tempStk.setBatch(stockBean.getBatch());
									if(stockBean.getChallanDate() != null) tempStk.setChallanDate(stockBean.getChallanDate());
									if(stockBean.getPurchasePrice() != null) tempStk.setPurchasePrice(stockBean.getPurchasePrice());
								}
								retailStockEntryRepository.save(childOfChildStocks);
							}
						}
						retailStockEntryRepository.save(childStocks);
					}
				}
				
				//******************************************
				// opening stocks, purchase return stocks or entry returns stock will have parent stock id > 0
				// *****************************************
				if(parentStockId > 0) { // Manipulate the parent stocks
					StockEntry parentStock = retailStockEntryRepository.findOne(parentStockId);
					if(parentStock == null) {
						String msg = "Incorrect parentStockId passed: " + parentStockId;
						log.error(msg);
						throw new RetailStockEntryException(msg,RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
					}
					if(stockBean.getMaterialId() == 0) {
						stockBean.setMaterialId(parentStock.getMaterialMaster().getMaterialId());
					}
					if(stockBean.getVendorId() == 0) {
						stockBean.setVendorId(parentStock.getVendorMaster().getVendorId());
					}
					stockBean.setBranchId(uiModel.getBranchBean().getBranchId());
					
					stockBean.setBatch(parentStock.getBatch());
					stockBean.setChallanDate(parentStock.getChallanDate());
					stockBean.setChallanNo(parentStock.getChallanNo());
					stockBean.setBillDate(parentStock.getBillDate());
					stockBean.setBillNo(parentStock.getBillNo());
					stockBean.setPurchasePrice(parentStock.getPurchasePrice());
					
					// **************************************
					// If stock is returned to vendor then the available qty has to be reduced
					// If year is closed then the available qty should not be reduced
					//***************************************
					
					Date actionDate = stockBean.getActionDate();
					
					boolean isYearClosed = isYearClosed(stockBean, actionDate);
					
					
					if(!isYearClosed) {
						// REDUCE AVAILABLE QUANTITY OF THE PARENT STOCK
						List<String> reduceStockTypes = new ArrayList<String>();
						reduceStockTypes.add(RetailBusinessConstants.ENTRY_TYPE_STOCK_RETURN);
						reduceStockTypes.add(RetailBusinessConstants.ENTRY_TYPE_SHORTAGE);
						reduceStockTypes.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER);
						
						if(reduceStockTypes.contains(stockBean.getEntryType())) { // REDUCE AVAILABLE QUANTITY OF THE PARENT STOCK
							BigDecimal availableQty = parentStock.getAvailableQty();
							if(availableQty != null && stockBean.getQty() != null) {
								if(availableQty.compareTo(stockBean.getQty()) < 0) {
									String msg = "Available quantity " + availableQty + " must be more than the return quantity " + stockBean.getQty() + " for the stock id " + stockId;
									log.error(msg);
									throw new RetailStockEntryException(msg,RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
								}
								availableQty = availableQty.subtract(stockBean.getQty());
							} else {
								String msg = "Available quantity " + availableQty + " or Return quantity " + stockBean.getQty() + " can not be null";
								log.error(msg);
								throw new RetailStockEntryException(msg,RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
							}
							parentStock.setAvailableQty(availableQty);
							retailStockEntryRepository.saveAndFlush(parentStock);
							
							// Make an entry for the current branch where the parent belongs to
							if(RetailBusinessConstants.ENTRY_TYPE_TRANSFER.equals(stockBean.getEntryType())) {
								StockEntry tempStock = new StockEntry();
								stockBean.setBranchId(parentStock.getBranchMaster().getBranchId());
								retailStockEntryMappingImpl.mapBean(stockBean, tempStock);
								tempStock.setAvailableQty(new BigDecimal(0));
								retailStockEntryRepository.saveAndFlush(tempStock);
								
								// Change the branch ID back to the previous Id passed from UI
								stockBean.setBranchId(uiModel.getBranchBean().getBranchId());
//								stockBean.setParentStockId(tempStock.getStockId());
							}
							
						} else if(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN.equals(stockBean.getEntryType())) { // INCREASE AVAILABLE QUANTITY OF THE PARENT STOCK
							// Reduce stock from parent stock
							BigDecimal availableQty = parentStock.getAvailableQty();
							availableQty = availableQty.subtract(stockBean.getQty());
							
							StockEntry tempStock = new StockEntry();
							stockBean.setBranchId(parentStock.getBranchMaster().getBranchId());
							retailStockEntryMappingImpl.mapBean(stockBean, tempStock);
							tempStock.setAvailableQty(new BigDecimal(0));
							retailStockEntryRepository.saveAndFlush(tempStock);
							// Change the branch ID back to the previous Id passed from UI
							stockBean.setBranchId(uiModel.getBranchBean().getBranchId());
//							stockBean.setParentStockId(tempStock.getStockId()); // This will make the transferred_return type is the parent of another transferred_return type 
							
							parentStock.setAvailableQty(availableQty);
							retailStockEntryRepository.saveAndFlush(parentStock);
							
							
							
							// increase the available quantity of the parent of the parent stock
							StockEntry parentOfParentStock = retailStockEntryRepository.findOne(parentStock.getParentStockId());
							if(parentOfParentStock != null) {
//								parentOfParentStock = retailStockEntryRepository.findOne(parentOfParentStock.getParentStockId());
//								if(parentOfParentStock != null) {
									availableQty = parentOfParentStock.getAvailableQty();
									availableQty = availableQty.add(stockBean.getQty());
									parentOfParentStock.setAvailableQty(availableQty);
									retailStockEntryRepository.saveAndFlush(parentOfParentStock);
//								}
							}
							
						}
					}
				} 
				// *****************************************************
				// Fresh stock entry/purchase and opening stock (1st time installation) will have parentStockId = 0
				// *****************************************************
				else if(parentStockId == 0) {  
					if(stockBean.getEntryType() == null) {
						if(StringUtils.isNotBlank(stockBean.getBillNo())) { // 
							stockBean.setEntryType(RetailBusinessConstants.ENTRY_TYPE_PURCHASED);
						} else {
							stockBean.setEntryType(RetailBusinessConstants.ENTRY_TYPE_NEW);
							// As the bill is not received so the purchased price is either of the following (whichever matches first)
							//	1. Last price of the same material of same batch (same branch)
							//	2. Last price of the same material from the same vendor (same branch)
							//  3. Last price of the same material from another vendor (same branch)
							//  4. MRP of the material
							
							BigDecimal stockPrice = retailStockEntryRepository.getStockEntryPrice(branchId, materialId, vendorId, batch);
							stockBean.setPurchasePrice(stockPrice);
						}
					} else if(RetailBusinessConstants.ENTRY_TYPE_MISC.equals(stockBean.getEntryType())) {
						if(stockBean.getPurchasePrice() == null) {
							BigDecimal stockPrice = retailStockEntryRepository.getStockEntryPrice(branchId, materialId, vendorId, batch);
							stockBean.setPurchasePrice(stockPrice);
						}
					}
				}
				
				
				StockEntry stock = null;
				if(stockId == 0) { // entry_type = 'NEW' or 'PURCHASED' or 'OPENING'
					if(log.isDebugEnabled()) {
						log.debug("New stock to be added for material: " + stockBean.getMaterialId());
					}
					stock = new StockEntry();
					
					//updateMaterialStocks(stockBean, stock);
				} else {
					stock = retailStockEntryRepository.findOne(stockId);
					if(stock == null) {
						String msg = "Incorrect stockId passed: " + stockId;
						log.error(msg);
						throw new RetailStockEntryException(msg,RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
					}
				}
				//TODO: Need to add all validations here. Example delete stock is possible if the stock is not sold.
				// Modification of stock is possible if the sold stock is not greater than the modification qty
				retailStockEntryMappingImpl.mapBean(stockBean, stock);
				
				if(RetailBusinessConstants.ENTRY_TYPE_STOCK_RETURN.equals(stockBean.getEntryType()) ||
						RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN.equals(stockBean.getEntryType()) ) { // This stock is not eligible for sale
					stock.setAvailableQty(new BigDecimal(0));
				}
				stocks.add(stock);
				
			}
			if(stocks.size() > 0) {
				retailStockEntryRepository.save(stocks);
				if(log.isDebugEnabled()) {
					log.debug("All stocks added");
				}
				
				// stockBean and stocks size has to be same
				if(stocksBean.size() == stocks.size()) {
					int i = 0;
					for(StockEntry stock : stocks) {
						RetailStockEntryBean stockBean = stocksBean.get(i++);
						retailStockEntryMappingImpl.mapBean(stock, stockBean);
//						updateMaterialStocks(stock);
					}
				}
			}
			

			populateTransactionRelatedTables(uiModel, RetailBusinessConstants.ENTRY_TYPE_PURCHASED, RetailBusinessConstants.CREDIT);
		}		
		return uiModel;
	}

	/**
	 * This method is responsible to populate the following tables during purchase
	 * <br>
	 *  1. material_tran_hrd <br/>
	 *  2. material_tran_dtls (Only for SALE) <br/>
	 *  3. gl_ledger_hrd <br/>
	 *  4. gl_ledger_dtls <br/>
	 *  5. retail_transaction <br>
	 *  6. transaction_payment (and cash_register, card_register, loan_register, cheque_register, credit_register etc based on the mode of payment)
	 * @param uiModel
	 * @param tranType
	 * @param drCr
	 */
	@Transactional(value="retailTransactionManager")
	private void populateTransactionRelatedTables(UIModel uiModel, String tranType, String drCr) {
		List<StockEntry> stocks = null;
		//**********************************
		// Once the bill is received, then only the payment is possible
		//**********************************
		if(uiModel.getBranchBean().getPayments() != null && uiModel.getBranchBean().getPayments().size() > 0 ) {
			for(TransactionPaymentBean paymentBean : uiModel.getBranchBean().getPayments()) {
				String billNo = paymentBean.getBillNo();
				if(RetailBusinessConstants.ENTRY_TYPE_PURCHASED.equals(tranType)) { // This check is required at the time of purchase
					stocks = retailStockEntryRepository.findByBillNo(billNo);
					if(stocks == null || stocks.size() == 0) {
						String errorMsg = "Bill no " + billNo + " does not exists in our record for making payments";
						log.error(errorMsg);
						throw new RetailException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
					}
				}
				
				MaterialTranHrd materialTranHrd = retailMaterialTranHrdRepository.findByBranchIdAndBillNo(uiModel.getBranchBean().getBranchId(), billNo);
				
				if(materialTranHrd == null) { 
					//**************************************
					// 1. Make an entry in material_tran_hrd as tran_type=PURCHASED/SALE
					//**************************************
					materialTranHrd = new MaterialTranHrd();
					String tranNo = null;
					if(paymentBean.getCreateUser() != null) {
						tranNo = txServiceHelper.getTransactionNumber(paymentBean.getCreateUser(), paymentBean.getActionDate());
					}
					MaterialTranHrdBean materialTranHrdBean = new MaterialTranHrdBean();
					try {
						BeanUtils.copyProperties(materialTranHrdBean, paymentBean);
						materialTranHrdBean.setTranType(tranType);
						materialTranHrdBean.setTranNo(tranNo);
						if(RetailBusinessConstants.ENTRY_TYPE_PURCHASED.equals(tranType)) {
							materialTranHrdBean.setBillDate(stocks.get(0).getBillDate());
						} else {
							if(materialTranHrdBean.getBillDate() == null) materialTranHrdBean.setBillDate(new Date());
						}
						BigDecimal outstandingAmt = paymentBean.getGrossTotal().subtract(paymentBean.getPaidAmt());
						materialTranHrdBean.setOutstandingAmt(outstandingAmt);
						retailMaterialTransactionMappingImpl.mapMaterialTranHrdBean(materialTranHrdBean, materialTranHrd);
						retailMaterialTranHrdRepository.saveAndFlush(materialTranHrd);
						materialTranHrdBean.setTranId(materialTranHrd.getTranId());
						if(log.isDebugEnabled()) {
							log.debug("Data saved into material_tran_hrd table");
						}
						uiModel.getBranchBean().setMaterialTranHrds(new ArrayList<MaterialTranHrdBean>());
						uiModel.getBranchBean().getMaterialTranHrds().add(materialTranHrdBean);
						
						
						//********************************************
						// 2. A. Make an entry in gl_ledger_hrd table
						//*******************************************
						String fy = adminSvcCommonUtil.getFinancialYear(paymentBean.getActionDate());
						GlLedgerHrdBean glLedgerHrdBean = new GlLedgerHrdBean();
						GlLedgerHrd glLedgerHeader = new GlLedgerHrd();
						BeanUtils.copyProperties(glLedgerHrdBean, materialTranHrdBean);
						glLedgerHrdBean.setFy(fy);
						glLedgerMappingImpl.mapGlLedgerHrdBean(glLedgerHrdBean, glLedgerHeader);
						glLedgerHeaderRepository.saveAndFlush(glLedgerHeader);
						paymentBean.setGlTranId(glLedgerHeader.getGlTranId());
						materialTranHrdBean.setGlTranId(glLedgerHeader.getGlTranId());
						if(log.isDebugEnabled()) {
							log.debug("Data saved into gl_ledger_hrd table");
						}
						
						//********************************************
						// 2. B. Make an entry in gl_ledger_dtls table
						//********************************************
						populateGlLedgerDtlsForMaterial(materialTranHrdBean, drCr, tranType, "MODIFY");
						
						//**************************************
						// 3. Make an entry into retail_transaction table
						//**************************************
						RetailTransaction retailTransaction = new RetailTransaction();
						retailTransaction.setMaterialTranHrd(materialTranHrd);
						retailTransaction.setGlLedgerHrd(glLedgerHeader);
						int branchId = uiModel.getBranchBean().getBranchId();
						BranchMaster branch = retailBranchMasterRepository.findOne(branchId);
						retailTransaction.setBranchMaster(branch);
						retailTransaction.setCreateUser(paymentBean.getCreateUser());
						retailTransactionRepository.saveAndFlush(retailTransaction);
						
						if(log.isDebugEnabled()) {
							log.debug("Data saved into retail_transaction table");
						}
						
					} catch (Exception e) {
						String msg = "Error while populating Purchased data into our database";
						log.error(msg, e);
						throw new RetailException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
					}
				}
				
				//**************************************
				// 4. Make an entry into transaction_payment table for all modes of payment (CASH/CARD/Advance) etc
				//    Then enter card payment detail into corresponding table like cash_register, card_register,
				//	  loan_register, cheque_register, credit_register etc
				//**************************************
				paymentServiceHelperImpl.makePayment(paymentBean, "UPDATE");
				
				
				// 5. Make an entry into retail_payment table
				int tranId = materialTranHrd.getTranId();
				int glTranId = paymentBean.getGlTranId();
				int branchId = uiModel.getBranchBean().getBranchId();
				Date actionDate = paymentBean.getActionDate();
				
				List<TransactionPayment> payments = transactionPaymentRepository.findByBranchIdAndGlTranId(branchId, glTranId);
				BranchMaster branch = retailBranchMasterRepository.findOne(branchId);
				for(TransactionPayment payment : payments) {
					RetailPayment retailPayment = retailPaymentRepository.findByPaymentIdAndTranId(payment.getPaymentId(), tranId);
					if(retailPayment == null) {
						retailPayment = new RetailPayment();
						retailPayment.setTransactionPayment(payment);
						retailPayment.setMaterialTranHrd(materialTranHrd);
						retailPayment.setBranchMaster(branch);
						retailPayment.setActionDate(actionDate);
						retailPayment.setCreateUser(paymentBean.getCreateUser());
						retailPaymentRepository.saveAndFlush(retailPayment);
						if(log.isDebugEnabled()) {
							log.debug("Making an entry into retail_payment table");
						}
					}
				}
			}
		}
	}

	@Transactional(value="retailTransactionManager")
	public UIModel saveStockSales(UIModel uiModel) {
		
		//********************************************************
		// Create a RUNNING customer (if does not exists). Else use the same customer id
		//********************************************************
		if(uiModel.getBranchBean().getCustomers() == null && uiModel.getBranchBean().getCustomers().size() == 0) {
			String errorMsg = "Customer information must be passed to sell a stock";
			log.error(errorMsg);
			throw new RetailException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_SALE);
		}
		customerSetupServiceHelperImpl.saveCustomerAccounts(uiModel);
		// *********************************
		// Update customerId if not available in the request
		//*************************************
		if(uiModel.getBranchBean().getPayments() != null && uiModel.getBranchBean().getPayments().size() > 0) {
			TransactionPaymentBean transactionPaymentBean = uiModel.getBranchBean().getPayments().get(0);
			int customerId = transactionPaymentBean.getCustomerId();
			if(customerId == 0) {
				transactionPaymentBean.setCustomerId(uiModel.getBranchBean().getCustomers().get(0).getCustomerId());
			}
		}
		
		if(uiModel.getBranchBean().getStockSales() != null && uiModel.getBranchBean().getStockSales().size() > 0) {
			//**********************************************
			// Populate all other tables as required. For more details please refer the javadoc of the below method
			//**********************************************
			populateTransactionRelatedTables(uiModel, RetailBusinessConstants.ENTRY_TYPE_SALE, RetailBusinessConstants.DEBIT);
			
			List<MaterialTranHrdBean> materialTranHrdBeans = uiModel.getBranchBean().getMaterialTranHrds();
			if(materialTranHrdBeans == null || materialTranHrdBeans.size() == 0) {
				String errorMsg = "Something went wrong. The material_tran_hrd table is not populated with right data.";
				log.error(errorMsg);
				throw new RetailException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_SALE);
			}
			
			MaterialTranHrdBean materialTranHrdBean = materialTranHrdBeans.get(0);
			int tranId = materialTranHrdBean.getTranId();
			for(MaterialTranDtlBean materialTranDtlBean : uiModel.getBranchBean().getStockSales()) {
				materialTranDtlBean.setTranId(tranId);   // Update tranId from material_tran_hrd table
				MaterialTranDtl materialTranDtl = new MaterialTranDtl();
				retailStockSaleMappingImpl.mapBean(materialTranDtlBean, materialTranDtl);
				retailMaterialTranDtlRepository.saveAndFlush(materialTranDtl);
				if(log.isDebugEnabled()) {
					log.debug("Data is saved into material_tran_dtls table");
				}
			}
		}
		
		return uiModel;
	}
	
	private boolean isYearClosed(RetailStockEntryBean stockBean, Date actionDate) {
		if(actionDate == null) {
			actionDate = new Date();
//			String msg = "Selected date is null";
//			log.error(msg);
//			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		String fy = adminSvcCommonUtil.getFinancialYear(actionDate);
		
		if(log.isDebugEnabled()) {
			log.debug("Selected FY = " + fy);
		}
		
		boolean isYearClosed = financialYearCloseServiceImpl.isFinancialYearClosed(stockBean.getBranchId(), fy);
		
		if(log.isDebugEnabled()) {
			log.debug("Financial year : " + fy + " closed: " + isYearClosed);
		}
		return isYearClosed;
	}

	/**
	 * This method updates material stocks once entered
	 * @param stock
	 */
	private void updateMaterialStocks(StockEntry stock) {
		// RULE: Update total stock and available stock in material_master table
		List<String> updateStockList = new ArrayList<String>();
		updateStockList.add(RetailBusinessConstants.ENTRY_TYPE_NEW);
		updateStockList.add(RetailBusinessConstants.ENTRY_TYPE_OPENING);
		updateStockList.add(RetailBusinessConstants.ENTRY_TYPE_PURCHASED);
		if(updateStockList.contains(stock.getEntryType())) {
			Date actionDate = stock.getActionDate();
			String fy = adminSvcCommonUtil.getFinancialYear(actionDate);
			String start = "01/04/" + fy.split("-")[0];
			String end = "31/03/" + fy.split("-")[1];
			
			Date startDate = adminSvcCommonUtil.getDateFormString(start);
			Date endDate = adminSvcCommonUtil.getDateFormString(end);

			int branchId = stock.getBranchMaster().getBranchId();
			int materialId = stock.getMaterialMaster().getMaterialId();
			
//			MaterialAvailability material = materialAvailabilityRepository.findMaterialStockByBranch(branchId, materialId, startDate, endDate);
//			MaterialAvailability matAvl = materialAvailabilityRepository.findMaterialAvailabilityByBranchIdAndMaterialId(branchId, materialId, startDate, endDate);
//			if(matAvl == null) {
//				matAvl = new MaterialAvailability();
//				matAvl.setFyStartDate(startDate);
//				matAvl.setFyEndDate(endDate);
//			}
//			matAvl.setStockIn(material.getStockIn());
//			matAvl.setStockOut(material.getStockOut());
//			matAvl.setAvailableStock(material.getAvailableStock());
//			matAvl.setBranchMaster(retailBranchMasterRepository.findOne(branchId));
//			matAvl.setMaterialMaster(retailMaterialMasterRepository.findOne(materialId));	
//			materialAvailabilityRepository.saveAndFlush(matAvl);
		}
		
		
		
//		
//		
//		
//		
//		
//		
//		MaterialMaster material = retailMaterialMasterRepository.findOne(stockBean.getMaterialId());
//		if(material == null) {
//			log.error("Material does not exists for materialId: " + stockBean.getMaterialId());
//			throw new RetailStockEntryException("Material does not exists for materialId: " + stockBean.getMaterialId());
//		}
//		BigDecimal stockIn = material.getStockIn();
//		if(stockIn == null) stockIn = new BigDecimal(0);
//		BigDecimal stockOut = material.getStockOut();
//		if(stockOut == null) stockOut = new BigDecimal(0);
//		BigDecimal stockBalance = material.getStockBalance();
//		if(stockBalance == null) stockBalance = new BigDecimal(0);
//		
//		// Below list holds the entry type for which stock gets increased for the branch
//		List<String> stockInTypeList = new ArrayList<String>();
//		stockInTypeList.add(RetailBusinessConstants.ENTRY_TYPE_NEW);
//		stockInTypeList.add(RetailBusinessConstants.ENTRY_TYPE_PURCHASED);
//		stockInTypeList.add(RetailBusinessConstants.ENTRY_TYPE_OPENING);
//		stockInTypeList.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN);
//		
//		// Below list holds the entry type for which stock gets decreased for the branch
//		List<String> stockOutTypeList = new ArrayList<String>();
//		stockOutTypeList.add(RetailBusinessConstants.ENTRY_TYPE_STOCK_RETURN);
//		stockOutTypeList.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER);
//		stockOutTypeList.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN);
//		
//		if(stockBean.getEntryType() != null) {
//			if(stockInTypeList.contains(stockBean.getEntryType())) {
//				// Increase stock and available stock
//				stockIn = stockIn.add(stockBean.getQty());
//				stockBalance = stockBalance.add(stockBean.getQty());
//				material.setStockIn(stockIn);
//				material.setStockBalance(stockBalance);
//			} else if(stockOutTypeList.contains(stockBean.getEntryType())) {
//				stockOut = stockOut.add(stockBean.getQty());
//				stockBalance = stockBalance.subtract(stockBean.getQty());
//				material.setStockOut(stockOut);
//				material.setStockBalance(stockBalance);
//			}
//			retailMaterialMasterRepository.saveAndFlush(material);
//		}
	}

	private void isValidStockInput(RetailStockEntryBean stockBean) throws RetailException {
		int parentStockId = stockBean.getParentStockId();
		int stockId = stockBean.getStockId();
		// VALIDATION 0: Action date can not be null;
		if(stockBean.getActionDate() == null) {
			String msg = "Action date must be selected";
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		
		// VALIDATION 1: ParentStockID > 0 for entry type = PURCHASE_RETURN, ENTRY_RETURN, YR_CLOSE_OPENING, SHORTAGE, TRANSFER, TRANSFER_RETURN
		//			Parent stock id = 0 for entry type = NEW/PURCHASED/OPENING
		if(parentStockId > 0) {
			
			List<String> entryTypeArr = new ArrayList<String>();
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_STOCK_RETURN);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_YR_CLOSE_OPENING);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_SHORTAGE);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN);
			if( !entryTypeArr.contains(stockBean.getEntryType()) )  {
				String msg = "There must be parent stock for the entry type = " + entryTypeArr;
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
			
			// VALIDATION 2: Quantity must be lesser than the available quantity of the parent stock
			StockEntry parentStock = retailStockEntryRepository.findOne(parentStockId);
			if(parentStock == null) {
				String msg = "Parent stock does not exists for the stock Id: " + parentStockId;
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
			stockBean.setChallanDate(parentStock.getChallanDate());
			stockBean.setChallanNo(parentStock.getChallanNo());
			stockBean.setBillNo(parentStock.getBillNo());
			stockBean.setBillDate(parentStock.getBillDate());
			stockBean.setPurchasePrice(parentStock.getPurchasePrice());
			
			if(parentStock.getAvailableQty().compareTo(stockBean.getQty()) < 0) {
				String msg = "Selected quantity " + stockBean.getQty() + " can not be greater than the available quantity " + parentStock.getAvailableQty() + " of the stock id " + parentStockId;
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
		} else if (parentStockId == 0) {
			List<String> entryTypeArr = new ArrayList<String>();
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_OPENING);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_NEW);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_PURCHASED);
			entryTypeArr.add(RetailBusinessConstants.ENTRY_TYPE_MISC);
			if( !(stockBean.getEntryType() == null || entryTypeArr.contains(stockBean.getEntryType())) )  {
				String msg = "parent stock id must be zero for entry type = " + entryTypeArr + " and you have selected different one";
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
			
			// VALIDATION 3: Challan number and entry date can not be null for opening/new/purchased stock
			if(stockId == 0 && stockBean.getEntryType() == null && (stockBean.getChallanNo() == null || stockBean.getChallanDate() == null)) {
				String msg = "Challan number/challan date can not be null";
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
				
			}
			
			// VALIDATION 3.a: Batch number can not be updated if the stock/part of the stock is already sold
			if(stockId > 0) {
				StockEntry stock = retailStockEntryRepository.findOne(stockBean.getStockId());
				if(stock == null) {
					String msg = "stock does not exists for the stock Id: " + stockId;
					log.error(msg);
					throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
				}
				if(stockBean.getBatch() != null && !stock.getBatch().equals(stockBean.getBatch())) {
					if(retailStockEntryRepository.checkIfAnyChildRecordExists(stockId) > 0) {
						String msg = "Batch can not be updated because it is partially/completely sold for stockId: " + stockId;
						log.error(msg);
						throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
					}
				}
				
				stockBean.setChallanDate(stock.getChallanDate());
				stockBean.setChallanNo(stock.getChallanNo());
				if(stock.getBillDate() != null) stockBean.setBillDate(stock.getBillDate());
				if(stock.getBillNo() != null) stockBean.setBillNo(stock.getBillNo());
			}
		}
		
		
		
		// VALIDATION 4: If bill number is specified then bill date and purchased price must not be empty
		if(StringUtils.isNotBlank(stockBean.getBillNo())) {
			if(stockBean.getBillDate() == null || stockBean.getPurchasePrice() == null) {
				String msg = "As bill is received then billDate/purchased price can not be null";
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
		} else {
			// VALIDATION 5: If bill number is not received then bill date must be null
			if(stockBean.getBillDate() != null) {
				String msg = "As bill is not received so bill date must be null";
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
		}
		
		// VALIDATION 6: Entry is not possible for closed financial year
		boolean isYearClosed = isYearClosed(stockBean, stockBean.getActionDate());
		if(isYearClosed) {
			String msg = "Financial year closed for the selected date: " + stockBean.getActionDate();
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		
		// VALIDATION 7A: Action date can not be before the challan date
		if(stockBean.getChallanDate() != null && stockBean.getChallanDate().after(stockBean.getActionDate())) {
			String msg = "Challan date " + stockBean.getChallanDate() + " must be before action date: " + stockBean.getActionDate();
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		// VALIDATION 7B: Bill date can not be before the challan date
		if(stockBean.getBillDate() != null && stockBean.getBillDate().before(stockBean.getChallanDate())) {
			String msg = "Bill date " + stockBean.getBillDate() + " must be after the challan date: " + stockBean.getChallanDate();
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
	}
	
	@Transactional(value="retailTransactionManager")
	public UIModel deleteStockEntries(UIModel uiModel) {
		if(uiModel.getBranchBean().getStockEntries() != null && uiModel.getBranchBean().getStockEntries().size() > 0 ) {
			
			List<RetailStockEntryBean> stocksBean = uiModel.getBranchBean().getStockEntries();
			List<StockEntry> stocks = new ArrayList<StockEntry>();
			for(RetailStockEntryBean stockBean : stocksBean) {
				
				StockEntry stock = validateStockDeleteOperation(stockBean);
				
				// RULE 1: Increase the stock for the following entry types
				List<String> increaseAvailableStock = new ArrayList<String>();
				increaseAvailableStock.add(RetailBusinessConstants.ENTRY_TYPE_STOCK_RETURN);
				increaseAvailableStock.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER);
				
				List<String> decreaseAvailableStock = new ArrayList<String>();
				decreaseAvailableStock.add(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN);
				decreaseAvailableStock.add(RetailBusinessConstants.ENTRY_TYPE_SHORTAGE);
				
				StockEntry parentStock = null;
				if(stock.getParentStockId() > 0) {
					parentStock = retailStockEntryRepository.findOne(stock.getParentStockId());
					if(parentStock == null) {
						String msg = "Parent stock not found for the selected stock: " + stock.getStockId();
						log.error(msg);
						throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
					}
				}
				
				if(increaseAvailableStock.contains(stockBean.getEntryType())) {
					BigDecimal qty = stock.getQty();
					BigDecimal availableQty = parentStock.getAvailableQty();
					availableQty = availableQty.add(qty);
					parentStock.setAvailableQty(availableQty);
					retailStockEntryRepository.saveAndFlush(parentStock);
				} else if(RetailBusinessConstants.ENTRY_TYPE_TRANSFER_RETURN.equals(stockBean.getEntryType())) {
					//
				}
				
				retailStockEntryMappingImpl.mapBean(stockBean, stock);
				stocks.add(stock);
			}
			if(stocks.size() > 0) {
				retailStockEntryRepository.save(stocks);
				if(log.isDebugEnabled()) {
					log.debug("All stocks updated with update user and updated date");
				}
				
				retailStockEntryRepository.delete(stocks);
				if(log.isDebugEnabled()) {
					log.debug("Selected stocks are deleted");
				}
			}
		}		
		return uiModel;
	}

	private StockEntry validateStockDeleteOperation(RetailStockEntryBean stockBean) {
		int stockId = stockBean.getStockId();
		StockEntry stock = null;
		if(stockId == 0) {
			String msg = "StockId can not be zero";
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		} else {
			stock = retailStockEntryRepository.findOne(stockId);
			if(stock == null) {
				String msg = "Incorrect stockId passed: " + stockId;
				log.error(msg);
				throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
			}
		}

		// VALIDATION 1: Stop stock deletion operation if the stock is already returned
		if(retailStockEntryRepository.checkIfAnyChildRecordExists(stockId) > 0) {
			String msg = "Stock can not be deleted because it is partially/completely returned for stockId: " + stockId;
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		
		// VALIDATION 2: If year is closed then previous year stock can not be deleted
		boolean isYearClosed = isYearClosed(stockBean, stockBean.getActionDate());
		if(isYearClosed) {
			String msg = "Financial year closed for the selected date: " + stockBean.getActionDate();
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		
		// VALIDATION 3: Year close opening stock ('YR_CLOSE_OPENING') can not be deleted
		if(RetailBusinessConstants.ENTRY_TYPE_YR_CLOSE_OPENING.equals(stockBean.getEntryType())) {
			String msg = "Year closed opening stocks can not be deleted";
			log.error(msg);
			throw new RetailStockEntryException(msg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_ENTRY);
		}
		return stock;
	}
	
	/**
	 * This method decides the gl_mas_code and call prepareAndSaveGlLedgerDtlsBean() method to save data into gl_ledger_dtls table
	 * @param materialTranHrdBean
	 * @param drCr
	 * @param tranType
	 * @param operation
	 */
	@Transactional(value="retailTransactionManager")
	private void populateGlLedgerDtlsForMaterial(MaterialTranHrdBean materialTranHrdBean, String drCr, String tranType, String operation) {
		
		// 1. gl_ledger_hrd has to be populated before populating gl_ledger_dtl table
		if(materialTranHrdBean.getTranId() == 0) {
			String errorMsg = "material_tran_hrd has to be populated first before populating gl_ledger_dtl";
			log.error(errorMsg);
			throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
		}
		// Find out gl_mas_code at the time of purchase
		if(RetailBusinessConstants.ENTRY_TYPE_PURCHASED.equals(tranType)) {
			Map<String, Integer> ledgerCodeRetailPurchaseMap = new HashMap<String, Integer>();
			List<StockEntry> stockEntries = retailStockEntryRepository.findByBillNo(materialTranHrdBean.getBillNo());
			if(stockEntries != null && stockEntries.size() > 0) {
				
				//***************************************************************** 
				// Find out the gl_mas_code for each vendor and material group combination from ledger_code_retail_purchase table
				//*****************************************************************
				List<LedgerCodeRetailPurchase> ledgerCodeRetailPurchases = retailLedgerCodePurchaseRepository.findAll();
				for(LedgerCodeRetailPurchase ledgerCodeRetailPurchase : ledgerCodeRetailPurchases) {
					int vendorId = ledgerCodeRetailPurchase.getVendorMaster().getVendorId();
					int materialGrpId = ledgerCodeRetailPurchase.getMaterialGroup().getMaterialGrpId();
					ledgerCodeRetailPurchaseMap.put(vendorId + "-" + materialGrpId, ledgerCodeRetailPurchase.getGlMasCode());
				}
				
				for(StockEntry stock : stockEntries) {
					int materialGroupId = stock.getMaterialMaster().getMaterialGroup().getMaterialGrpId();
					int vendorId = stock.getVendorMaster().getVendorId();
					String key = materialGroupId + "-" + vendorId;
					Integer glMasCode = ledgerCodeRetailPurchaseMap.get(key);
					if(glMasCode == null) {
						String errorMsg = "There is no GL_MAS_CODE configred for the vendor Id: " + vendorId;
						log.error(errorMsg);
						throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
					} else {
						// gl_mas_code must be available
						GlMaster glMaster = glMasterRepository.findOne(glMasCode);
						if(glMaster == null) {
							String errorMsg = "The GL_MAS_CODE " + glMasCode + " is not configred in our record";
							log.error(errorMsg);
							throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
						}
					}
					String remarks = stock.getVendorMaster().getVendorName() + "-" + stock.getMaterialMaster().getMaterialGroup().getGroupName() + "-" + stock.getMaterialMaster().getMaterialName();
					BigDecimal amount = (stock.getPurchasePrice() != null) ? stock.getPurchasePrice() : null;
					prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,amount, remarks, glMasCode,null);
				}
			}
			
			if(materialTranHrdBean.getVat() != null) {
				prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,materialTranHrdBean.getVat(), 
							"VAT", RetailBusinessConstants.LEDGER_CODE_VAT_PAID,null);
			}
			if(materialTranHrdBean.getDiscount() != null) {
				prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,materialTranHrdBean.getDiscount(), 
							"DISCOUNT", RetailBusinessConstants.LEDGER_CODE_DISCOUNT_PAID,null);
			}
		} else {
			// Populate all gl_mas_code for sale
			Map<String, Integer> ledgerCodeRetailSaleMap = new HashMap<String, Integer>();
			List<LedgerCodeRetailSale> ledgerCodeRetailSales = ledgerCodeRetailSaleRepository.findAll();
			for(LedgerCodeRetailSale ledgerCodeRetailSale : ledgerCodeRetailSales) {
				ledgerCodeRetailSaleMap.put(ledgerCodeRetailSale.getCustomerType(), ledgerCodeRetailSale.getGlMasCode());
			}
			int customerId = materialTranHrdBean.getCustomerId();
			Customer customer = customerRepository.findOne(customerId);
			if(customer == null) {
				String errorMsg = "Customer does not exists for customer Id: " + customerId;
				log.error(errorMsg);
				throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_STOCK_SALE);
			}
			String customerType = customer.getCustomerType();
			Integer glMasCode = ledgerCodeRetailSaleMap.get(customerType);
			if(glMasCode == null) {
				String errorMsg = "There is no GL_MAS_CODE configred for the customer type: " + customerType;
				log.error(errorMsg);
				throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
			} else {
				// gl_mas_code must be available
				GlMaster glMaster = glMasterRepository.findOne(glMasCode);
				if(glMaster == null) {
					String errorMsg = "The GL_MAS_CODE " + glMasCode + " is not configred in our record";
					log.error(errorMsg);
					throw new PaymentException(errorMsg, RetailBusinessConstants.EXCEPTION_RETAIL_PAYMENT);
				}
			}
			
			if(materialTranHrdBean.getBillAmt() != null) {
				prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,materialTranHrdBean.getBillAmt(), 
							"BILLAMT", glMasCode, null);
			}
			
			if(materialTranHrdBean.getVat() != null) {
				prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,materialTranHrdBean.getVat(), 
							"VAT", RetailBusinessConstants.LEDGER_CODE_VAT_COLLECTION,null);
			}
			if(materialTranHrdBean.getDiscount() != null) {
				prepareAndSaveGlLedgerDtlsBean(materialTranHrdBean, drCr, operation,materialTranHrdBean.getDiscount(), 
							"DISCOUNT", RetailBusinessConstants.LEDGER_CODE_DISCOUNT_COLLECTION,null);
			}
		}
		materialTranHrdBean.getBillAmt();
		
		
	}

	/**
	 * This method saves record into gl_ledger_dtl table
	 * @param materialTranHrdBean
	 * @param drCr
	 * @param operation
	 * @param amount
	 * @param remarks
	 * @param glMasCode
	 * @param paymentType
	 */
	
	@Transactional(value="retailTransactionManager")
	private void prepareAndSaveGlLedgerDtlsBean(
			MaterialTranHrdBean materialTranHrdBean, String drCr,
			String operation, BigDecimal amount, String remarks, Integer glMasCode, String paymentType) {
		// 4. Populate GlLedgerDtlBean data.
		
		GlLedgerDtl glLedgerDtl = new GlLedgerDtl();
		GlLedgerDtlBean glLedgerDtlBean = new GlLedgerDtlBean();
		glLedgerDtlBean.setGlMasCode(glMasCode);
		glLedgerDtlBean.setBranchId(materialTranHrdBean.getBranchId());
		glLedgerDtlBean.setGlTranId(materialTranHrdBean.getGlTranId());
		glLedgerDtlBean.setCreateUser(materialTranHrdBean.getCreateUser());
		glLedgerDtlBean.setDrCr(drCr);
		glLedgerDtlBean.setAmount(amount);
		glLedgerDtlBean.setRemarks(remarks);
		
		if(!"DELETE".equals(operation) && amount != null && amount.doubleValue() > 0) {
			glLedgerMappingImpl.mapGlLedgerDtlBean(glLedgerDtlBean, glLedgerDtl);
			glLedgerDtlRepository.saveAndFlush(glLedgerDtl);
		}
		if(log.isDebugEnabled()) {
			log.debug("Date saved into gl_ledger_dtl table");
		}
	}
}
