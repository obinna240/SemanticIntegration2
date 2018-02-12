package com.semanticIntegration.controller;


import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.semanticIntegration.model.Queryer;
import com.semanticIntegration.model.RevenueCalculatorImpl;
import com.semanticIntegration.objects.PaginatorBean;
import com.semanticIntegration.objects.ResultBean;
import com.semanticIntegration.utils.PaginationUtils;
import com.semanticIntegration.utils.Utils;



/**
 * RestController which handles requests
 * @author oonyimadu
 *
 */
@RestController
@RequestMapping("/service")
public class AppController
{
	@Autowired
	Queryer queryer;
	@Autowired RevenueCalculatorImpl revCalc;
	private static Log logger = LogFactory.getLog(AppController.class);	

	/**
	 * 
	 * @param name
	 * @return String
	 */
	@RequestMapping(value = "name/{name}", method=RequestMethod.GET)
	public ResponseEntity<String> getAnswerToQuestion(@PathVariable String name)
	{
		String responseAnswer = "";
		logger.info("Getting result for  "+name);
		if(StringUtils.isNotBlank(name))
		{
			name = StringUtils.normalizeSpace(name);
			responseAnswer = queryer.getQueryResult(name);
		}
		else
		{
			responseAnswer = "You will need to append a name i.e. 'blair' to find out when Tony Blair was born or 'cameron' to find out when David Cameron was born";
		}
		
		return new ResponseEntity<String>(responseAnswer,HttpStatus.OK);
	}
	
	@RequestMapping(value = "revenueCalc/{marginPercentage}/{costOfGoods}", method=RequestMethod.GET)
	public ResponseEntity<BigDecimal> getRevenue(@PathVariable String marginPercentage, @PathVariable String costOfGoods)
	{
		
		BigDecimal responseAnswer = revCalc.calculateRev(marginPercentage, costOfGoods);
		
		return new ResponseEntity<BigDecimal>(responseAnswer,HttpStatus.OK);
	}
	
	/**
	 * This method will return only 3 results regardless of the value of search
	 * @param search
	 * @param pp page
	 * @return
	 */
	@RequestMapping(value="/pagination/{search}", method=RequestMethod.GET)
	public ResponseEntity<ResultBean> generalSearch(@PathVariable String search, @RequestParam(required=false) String pp)
	{
		
		PaginatorBean paginatorBean = null;
		Integer pageNumber = null;
		
		if(pp==null)
		{
			paginatorBean = PaginationUtils.checkPagination(null);
		}
		else
		{
			pageNumber =  Integer.parseInt(pp);
			paginatorBean = PaginationUtils.checkPagination(pageNumber);
		}
		
		Map<String,Object> qParamStartRow = PaginationUtils.getQueryParams(paginatorBean);
		ResultBean resultBean = Utils.search(search,pp, qParamStartRow);
		
		return new ResponseEntity<ResultBean>(resultBean,HttpStatus.OK);
	}
}
