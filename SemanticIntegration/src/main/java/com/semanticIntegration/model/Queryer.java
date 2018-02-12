package com.semanticIntegration.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.springframework.stereotype.Component;

/**
 * 
 * @author oonyimadu
 *
 */
@Component
public class Queryer
{
	private Repository sparqlRepository;
	
	/**
	 * 
	 * @param queryType
	 * @return String
	 */
	public String getQueryResult(String queryType)
	{
		String answer =null;
		String query = null;
		query = getQueryType(queryType).get("q");
		
		if(sparqlRepository==null)
		{
			sparqlRepository = makeRepository("http://dbpedia.org/sparql");
			sparqlRepository.initialize();
		}
		
		try (RepositoryConnection conn = sparqlRepository.getConnection()) 
		{
		 
			   TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
			  
			   try (TupleQueryResult result = tupleQuery.evaluate()) 
			   {
			      while (result.hasNext()) 
			      {  // iterate over the result
					   BindingSet bindingSet = result.next();
					   Value value = bindingSet.getValue("answer");
					   answer=  value.stringValue();
					   if(StringUtils.equalsIgnoreCase(queryType, "blair"))
					   {
						   
						   return "Tony Blair was born on "+answer +" . The date format is yyyy-m-d";
						  
						  
					   }
					   else if(StringUtils.equalsIgnoreCase(queryType, "cameron"))
					   {
						 
						  answer = StringUtils.substringAfter(answer, "http://dbpedia.org/resource/");
						  answer = "David Cameron was born in "+answer;
					   }
			      }
			   }
		}
		return answer;
	}
	
	public Repository getSparqlRepository() {
		return sparqlRepository;
	}

	public void setSparqlRepository(Repository sparqlRepository) {
		this.sparqlRepository = sparqlRepository;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Queryer q = new Queryer();
		/**
		Repository sparqlRepository = new SPARQLRepository("http://dbpedia.org/sparql");
		sparqlRepository.initialize();
		try (RepositoryConnection conn = sparqlRepository.getConnection()) {
		   String queryString = q.getBlairQuery();
		   TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		   TupleQueryResult result = tupleQuery.evaluate();
		   try (TupleQueryResult result2 = tupleQuery.evaluate()) 
		   {
		      while (result2.hasNext()) {  // iterate over the result
		   BindingSet bindingSet = result2.next();
		   Value valueOfX = bindingSet.getValue("answer");
		   
		   // do something interesting with the values here...
		   System.out.println(valueOfX.stringValue());
		   System.out.println(StringUtils.substringAfter(valueOfX.stringValue(), "http://dbpedia.org/resource/"));
		      }
		   }}*/
		System.out.println(q.getQueryResult("cameron"));
	}
	
/**
 * 
 * @return StringBuffer
 */
private StringBuffer getPrefix()
{
	StringBuffer sbuff = new StringBuffer();

	sbuff.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>");
	sbuff.append(" ");
	sbuff.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>");
	sbuff.append(" ");
	sbuff.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");sbuff.append(" ");
	sbuff.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>");sbuff.append(" ");
	sbuff.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>");sbuff.append(" ");
	sbuff.append("PREFIX dc: <http://purl.org/dc/elements/1.1/>");sbuff.append(" ");
	sbuff.append("PREFIX : <http://dbpedia.org/resource/>");sbuff.append(" ");
	sbuff.append("PREFIX dbpedia2: <http://dbpedia.org/property/>");sbuff.append(" ");
	sbuff.append("PREFIX dbpedia: <http://dbpedia.org/>");sbuff.append(" ");
	sbuff.append("PREFIX skos: <http://www.w3.org/2004/02/skos/core#>");sbuff.append(" ");
	
	return sbuff;
}

/**
 * Returns SPARQL Tony Blair Query
 * @return
 */
private String getBlairQuery()
{
	StringBuffer buffer = getPrefix();
	
	buffer.append("SELECT  ?x  YEAR(NOW())as ?cu  NOW()-?birthDate as ?age CONCAT(STR(YEAR(?birthDate)),\"-\",STR(MONTH(?birthDate)),\"-\",STR(DAY(?birthDate))) as ?answer NOW() as ?now");
	buffer.append(" ");	buffer.append("WHERE {");buffer.append(" ");
		   
	buffer.append("?x rdf:type foaf:Person. ");
	buffer.append("?x foaf:name \"Tony Blair\"@en . "); 
	buffer.append("?x <http://dbpedia.org/ontology/office> \"Prime Minister of the United Kingdom\". ");
	buffer.append("?x <http://dbpedia.org/ontology/birthDate> ?birthDate ");
	buffer.append("	} ");
	buffer.append("LIMIT 1");
	return buffer.toString();
}

/**
 * 
 * @return
 */
private String getCameronQuery()
{
	StringBuffer buffer = getPrefix();
	
	buffer.append("SELECT  ?x  ?birthPlace as ?answer "); 
	buffer.append("WHERE "); 
	buffer.append("{ ");    
	buffer.append("?x rdf:type foaf:Person. ");
	buffer.append("?x foaf:name \"David Cameron\"@en . ");
	buffer.append("?x <http://dbpedia.org/ontology/office> \"Prime Minister of the United Kingdom\". ");
	buffer.append("?x <http://dbpedia.org/ontology/birthPlace> ?birthPlace "); 
	buffer.append("} ");
	buffer.append("LIMIT 3 ");
	return buffer.toString();
}

/**
 * 
 * @param queryType
 * @return
 */
private Map<String,String> getQueryType(String queryType)
{
	String query = null;
	Map<String,String> map = new HashMap<String,String>();
	if(queryType.equalsIgnoreCase("blair"))
	{
		query = getBlairQuery();
		map.put("qt", "blair");
		map.put("q", query);
	}
	else if(queryType.equalsIgnoreCase("cameron"))
	{
		query = getCameronQuery();
		map.put("qt", "cameron");
		map.put("q", query);
	}
	return map;
}

public Repository makeRepository(String repoURL)
{
	sparqlRepository = new SPARQLRepository(repoURL);
	return sparqlRepository;
}

}