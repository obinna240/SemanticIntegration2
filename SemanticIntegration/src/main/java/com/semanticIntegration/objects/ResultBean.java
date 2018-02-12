package com.semanticIntegration.objects;

import java.util.List;

/**
 * 
 * @author oonyimadu
 *
 */
public class ResultBean 
{
	private Integer totalNumberOfResults;
	private Integer pageNumber;
	private Integer start;
	private List<ObjectBean> objectBean;
	private PaginatorBean paginatorBean;
	private QueryBean queryBean;
	
	public Integer getTotalNumberOfResults() {
		return totalNumberOfResults;
	}
	public void setTotalNumberOfResults(Integer totalNumberOfResults) {
		this.totalNumberOfResults = totalNumberOfResults;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public List<ObjectBean> getObjectBean() {
		return objectBean;
	}
	public void setObjectBean(List<ObjectBean> objectBean) {
		this.objectBean = objectBean;
	}
	public PaginatorBean getPaginatorBean() {
		return paginatorBean;
	}
	public void setPaginatorBean(PaginatorBean paginatorBean) {
		this.paginatorBean = paginatorBean;
	}
	public QueryBean getQueryBean() {
		return queryBean;
	}
	public void setQueryBean(QueryBean queryBean) {
		this.queryBean = queryBean;
	}
	
}
