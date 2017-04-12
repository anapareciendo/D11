<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Sample Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.profile" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/display.do"><spring:message code="master.page.chorbi.display" /></a></li>
					<li><a href="chorbi/edit.do"><spring:message code="master.page.chorbi.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/signin.do"><spring:message code="master.page.signin" /></a></li>
			<li><a class="fNiv" href="aboutUs/acme.do"><spring:message code="master.page.about" /></a></li>
		
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
				<li><a class="fNiv" href="aboutUs/acme.do"><spring:message code="master.page.about" /></a></li>
				
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="chorbi/admin/list.do"><spring:message code="master.page.chorbi.list" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('CHORBI')">
						<li><a href="chorbi/list.do"><spring:message code="master.page.chorbi.list" /></a></li>
						<li><a href="template/search.do"><spring:message code="master.page.template" /></a></li>
						<li><a href="template/result.do"><spring:message code="master.page.template.result" /></a></li>
					</security:authorize>
				</ul>
			</li>
			
			<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.likes" /></a>
				<ul>
					<li><a href="likes/chorbi/listMakeLikes.do"><spring:message code="master.page.likes.chorbi.make" /></a></li>
					<li><a href="likes/chorbi/listReceivedLikes.do"><spring:message code="master.page.likes.chorbi.received" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.chirp" /></a>
				<ul>
					<li><a href="chirp/chorbi/received.do"><spring:message code="master.page.chirp.list" /></a></li>
					<li><a href="chirp/chorbi/sent.do"><spring:message code="master.page.chirp.sent" /></a></li>
				</ul>
			</li>
			</security:authorize>
			
		</security:authorize>
		
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

