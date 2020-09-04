<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
	    <title>Projeto</title>
	    <link rel="stylesheet" type="text/css" href="../static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css"></link>
		<link rel="stylesheet" type="text/css" href="../static/bootstrap-3.3.5-dist/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap-theme.css"></link>
	</head>
<body>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<s:url value="/" />">Inicio</a>
				</div>
				
				<ul class="nav navbar-nav">
            	
					<li>
						<a href="<s:url value="formPessoa" />" class="btn-lg" title='Pessoa'>
							Adicionar Pessoa
						</a>
					</li>
					
					<li>
						<a href="<s:url value="formProjeto" />" class="btn-lg" title='Projeto'>
							Adicionar Projeto
						</a>
					</li>
					
				</ul>
            </div>
	    </nav>
</body>	
<section class="content">
	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<ol class="breadcrumb" >
			<li class="active"><h4><strong>Projeto</strong></h4></li>
			<li>		
				<a 
					href="<s:url value="/formProjeto" />" 
					class="btn btn-outline-dark"
					title='<fmt:message key="button.add"/>'>
						<fmt:message key="button.add"/> 
				</a>
			</li>
		</ol>
	</div>
	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		
		
		<div class="col-lg-12 col-md-12 col-sm-12  col-xs-12">
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-body">
					
						<form 
							action="<s:url value="/doFind" />"
							method="POST"
							role="form" 
							autocomplete="off">
							
							<div class="row">
		
								<div class="col-lg-4 col-md-8 col-sm-6 col-xs-12">
									<div class="form-group">
										<input 
											type="text" 
											class="form-control" 
											id="searchText" 
											name="filter"
											autofocus="autofocus"
											placeholder="Buscar por nome"
										/>
									</div>
								</div>
								
								<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12">
									<div class="form-group">
										<button class="btn btn-outline-dark" type="submit">
											<span class="glyphicon glyphicon-search"></span> 
											<fmt:message key="button.find"/>
										</button>
									</div>
								</div>
								
							</div>	
							
						</form>
					
					</div>
				</div>
			</div>
		</div>
	
		<div class="col-lg-12 col-md-12 col-sm-12  col-xs-12">
			<div class="row">
			
				<c:if test="${empty listProjeto}">
					<br/>	
					<br/>
					<br/>
					<div class="col-lg-12 col-md-12 col-sm-12  col-xs-12">
						<div class="alert alert-info">
							<fmt:message key="table.result.not.found"/>
						</div>
					</div>
					<br/>
					<br/>
					<br/>
				</c:if>
			
				<c:if test="${not empty listProjeto}">
					
					<table 
						class="table table-striped table-bordered table-hover table-condensed small">
						
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th>Nome</th>
								<th>Data Inicio</th>
								<th>Gerente</th>
								<th>DT.Previsao Termino</th>
								<th>Data Termino</th>
								<th>Orçamento</th>
								<th>Descrição</th>
								<th>Risco</th>
								<th>Status</th>
								<th style="min-width: 10%">Ações</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${listProjeto}" var="item" varStatus="contador">
								<tr>
									<td>${contador.count}</td>
									<td>${item.nome}</td>
									<td><fmt:formatDate value="${item.dataInicio}" pattern="dd/MM/yyyy"/></td>
									<td>${item.pessoa.nome}</td>
									<td><fmt:formatDate value="${item.dataPrevisaoFim}" pattern="dd/MM/yyyy"/></td>
									<td><fmt:formatDate value="${item.dataFim}" pattern="dd/MM/yyyy"/></td>
									<td>${item.orcamento}</td>
									<td>${item.descricao}</td>
									<td>${item.risco}</td>
									<td>${item.status}</td>
									<td>
									
										<a href="<s:url value="/encerraProjeto?idProjeto=${item.id}" />"
												class="btn btn-secondary btn-xs"
												title='<fmt:message key="button.encerrar"/>'>
													<span class="glyphicon glyphicon-ok"></span>&nbsp;<fmt:message key="button.encerrar"/>
										</a>
										<a href="<s:url value="/editProjeto?idProjeto=${item.id}" />"
											class="btn btn-secondary btn-xs"
											title='<fmt:message key="button.edit"/>'>
												<span class="glyphicon glyphicon-pencil"></span>&nbsp;<fmt:message key="button.edit"/>
										</a>
										
										<c:if test="${item.status ne 'INICIADO' && item.status ne 'EM_ANDAMENTO' && item.status ne 'ENCERRADO'}">
											<a href="<s:url value="/deleteProjeto?idProjeto=${item.id}" />"
												class="btn btn-secondary btn-xs"
												title='<fmt:message key="button.delete"/>'>
													<span class="glyphicon glyphicon-trash"></span>&nbsp;<fmt:message key="button.delete"/>
											</a>
										</c:if>	
										<a href="<s:url value="/cancelaProjeto?idProjeto=${item.id}" />"
												class="btn btn-secondary btn-xs"
												title='<fmt:message key="button.cancel"/>'>
													<span class="glyphicon glyphicon-remove-sign"></span>&nbsp;<fmt:message key="button.cancel"/>
										</a>
										<a href="<s:url value="/formMembros?idProjeto=${item.id}" />"
												class="btn btn-secondary btn-xs"
												title='<fmt:message key="button.member"/>'>
													<span class="glyphicon glyphicon glyphicon-plus"></span>&nbsp;<fmt:message key="button.member"/>
										</a>					
									</td>
								</tr>				
							</c:forEach>					
						</tbody>
					</table>		
				</c:if>		
			</div>	
		</div>	
	</div>	
</section>
</html>