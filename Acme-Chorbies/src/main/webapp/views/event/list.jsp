<%--
 * display.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="event" id="event" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="event.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="false" />
	
	<spring:message code="event.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader }" sortable="false" />
	
	<spring:message code="event.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" sortable="true" />
	
	<spring:message code="event.seatsOffered" var="seatsOfferedHeader" />
	<display:column property="seatsOffered" title="${seatsOfferedHeader}" sortable="true" />
	
	<jstl:if test="${available == true }">
	<spring:message code="event.seatsAvailable" var="seatsAvailableHeader" />
	<display:column value="${seatsAvailable}" title="${seatsAvailableHeader}" sortable="true" />
	</jstl:if>
	
	<security:authorize access="hasRole('CHORBI')">
	<jstl:if test="${own == true }">
		<display:column>
	  		<a href="event/chorbi/unregister.do?eventId=${event.id}">
	 			<spring:message code="event.unregister" var="unregisterHeader" />
		  		<jstl:out value="${unregisterHeader}" />
			 </a>
		</display:column>
	</jstl:if>
	<jstl:if test="${all == true }">
		<display:column>
	  		<a href="event/chorbi/register.do?eventId=${event.id}">
	 			<spring:message code="event.register" var="registerHeader" />
		  		<jstl:out value="${registerHeader}" />
			 </a>
		</display:column>
	</jstl:if>
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
	  	<a href="event/manager/edit.do?eventId=${event.id}">
	 			<spring:message code="event.edit" var="editHeader" />
		  		<jstl:out value="${editHeader}" />
		 </a>
	</display:column>
	</security:authorize>
	
</display:table>