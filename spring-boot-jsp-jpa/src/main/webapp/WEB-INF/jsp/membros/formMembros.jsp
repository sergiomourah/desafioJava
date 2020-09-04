<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
	    <title>Formulario Membro</title>
	    <link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css"></link>
		<link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href=" ../static/bootstrap-3.3.5-dist/css/bootstrap-theme.css"></link>
	</head>
<body>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<s:url value="inicio" />">Inicio</a>
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
	<body>

		<section class="content">
	
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
	
		<div class="panel panel-default">
			<div class="panel-heading">
				<strong>Projeto</strong>
			</div>
			<s:url value="/doFormProjeto" var="acao" />
			<sf:form method="post" modelAttribute="projeto" action="${acao}" >
				<div class="panel-body">
					<div class="column">
							<c:if test="${not empty projeto.id}">
								<sf:hidden path="id" />
							</c:if>
						
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<div class="form-group">
									<label for="idade">Selecionar Pessoa:</label>
									<sf:select path="pessoa.id" cssClass="form-control">
										 <sf:options items="${listPessoas}" itemValue="id" itemLabel="nome"/>
						            </sf:select>
								</div>
							</div>
							</div>				
					</div>
				</div>
				
				<div class="panel-footer text-right">
					<a class="btn btn-default" href="<s:url value="inicio" />" tabindex="-1">
						<fmt:message key="button.cancel"/>
					</a>
	
					<button class="btn btn-default" type="submit">
						<fmt:message key="button.save"/>
					</button>
				</div>
				
			</sf:form>
					
		</div>
	
	</div>
		
</section>
	<script src=" ../static/lib/jquery-1.11.1.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
    <SCRIPT language="javascript">
	    $(document).ready(function () {
	        $('.data').mask('99/99/9999');
	        $('.cpf').mask('999.999.999-99');
	        return false;
	    });
    </SCRIPT>
</html>