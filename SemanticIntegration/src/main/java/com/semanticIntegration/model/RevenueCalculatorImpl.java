package com.semanticIntegration.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.semanticIntegration.interfaces.RevenueCalculator;

/**
 * 
 * @author oonyimadu
 *
 */
@Component
public class RevenueCalculatorImpl implements RevenueCalculator{

	BigDecimal val;
	@Override
	public BigDecimal calculateRevenue(BigDecimal marginPercentage,
			BigDecimal costOfGoods) 
	{
		
		return calculateRev(marginPercentage,costOfGoods);
			
		
		
	}
	
	/**
	 * 
	 * @param marginPercentage
	 * @param costOfGoods
	 * @return BigDecimal
	 */
	private BigDecimal calculateRev(BigDecimal marginPercentage,
			BigDecimal costOfGoods)
	{
		
		if(marginPercentage!=null && costOfGoods!=null)
		{
			BigDecimal percent = new BigDecimal(100);
			val = (costOfGoods.multiply(percent)).divide(percent.subtract(marginPercentage), 2, RoundingMode.HALF_EVEN);
		}
		return val;
	}
	
	/**
	 * Runs the method when Strings are passed
	 * @param marginPercentage
	 * @param costOfGoods
	 * @return BigDecimal
	 */
	public BigDecimal calculateRev(String marginPercentage,
			String costOfGoods)
	{
	
		if(StringUtils.isNotBlank(costOfGoods)&& StringUtils.isNotBlank(marginPercentage))
		{
			marginPercentage = StringUtils.normalizeSpace(marginPercentage);
			costOfGoods = StringUtils.normalizeSpace(costOfGoods);
			if(NumberUtils.isNumber(marginPercentage) && NumberUtils.isNumber(costOfGoods))
			{
				BigDecimal margin = new BigDecimal(marginPercentage);
				BigDecimal cost = new BigDecimal(costOfGoods);
				val = calculateRev(margin,cost);
			}
		}	
		return val;
	}
	
	
	public static void main(String[] args)
	{
		RevenueCalculatorImpl  rv = new RevenueCalculatorImpl ();
		
		System.out.println(rv.calculateRevenue(new BigDecimal(0), new BigDecimal(10)));
	}
}
