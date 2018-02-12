package com.semanticIntegration.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.semanticIntegration.objects.ObjectBean;
import com.semanticIntegration.objects.PaginatorBean;
import com.semanticIntegration.objects.QueryBean;
import com.semanticIntegration.objects.ResultBean;

/**
 * 
 * @author oonyimadu
 *
 */
public class Utils {
	public static ResultBean search(String search,String pp, Map<String,Object> qParamStartRow)
	{
		
		QueryBean qBean = new QueryBean();
		qBean.setQuery(search);
		
		Integer resultSize=3;
		PaginatorBean paginatorBean=null;
		PaginatorBean originalPaginatorBean = null;
		ResultBean resultBean = new ResultBean();
		List<ObjectBean> objectBean = new ArrayList<ObjectBean>();
		
		ObjectBean object1 = new ObjectBean();
		object1.setHref("http://bbc.co.uk");
		object1.setTitle("bbc");
		object1.setSummary("This is the BBC");
		objectBean.add(object1);
		
		ObjectBean object2 = new ObjectBean();
		object2.setHref("http://itv.com");
		object2.setTitle("itv");
		object2.setSummary("This is ITV");
		objectBean.add(object2);
		
		ObjectBean object3 = new ObjectBean();
		object3.setHref("http://CNN.COM");
		object3.setTitle("CNN");
		object3.setSummary("This is CNN");
		objectBean.add(object3);
		
		if(MapUtils.isNotEmpty(qParamStartRow))
		{
			Integer qStart = (Integer)qParamStartRow.get("queryStart");
			Integer qRows = (Integer)qParamStartRow.get("queryRows");
			originalPaginatorBean = (PaginatorBean)qParamStartRow.get("originalPaginatorBean");
			
	
		}
		
		if(originalPaginatorBean!=null)
		{
			paginatorBean = PaginationUtils.doPaginationTest(resultSize, originalPaginatorBean);
			
		}
		
	
		resultBean.setTotalNumberOfResults(resultSize);
		resultBean.setObjectBean(objectBean);
		resultBean.setPaginatorBean(paginatorBean);
		resultBean.setQueryBean(qBean);
		Integer qStart = (Integer)qParamStartRow.get("queryStart");
		resultBean.setStart(qStart);
		return resultBean;
	}
}


															
						
