<%--
  Created by IntelliJ IDEA.
  User: hmoralesm
  Date: 11/12/2016
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap/bootstrap.css">
<title>Administrador de Properties</title>

<script type="text/javascript">

	function recuperarNombre(){
      var fullPath = document.getElementById('nombreArchivo').value;
      
      if (fullPath) {
         var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
         var filename = fullPath.substring(startIndex);
         
         if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
            filename = filename.substring(1);
            document.getElementById('nombre').value = filename;
         }	
      } 
   }
   
</script>
</head>
<body>
<body>
   
	<div class="panel panel-default">
		<div class="panel-heading">Subir Archivo</div>
	   <div class="panel-body">
			<form method="POST" action="uploadFile" enctype="multipart/form-data" class="form-horizontal">
			
            <div class="form-group">
               <label class="control-label col-xs-6">Seleccione el archivo</label>
               <div class="col-xs-6">
                  <div class="input-group">						
                     <input	id="nombreArchivo" type="file" class="file" onchange="recuperarNombre();" name="file" >
                  </div>
               </div>
            </div>
            
            <div class="form-group">
               <label class="control-label col-xs-6">Nombre del archivo</label>
               <div class="col-xs-6">
                  <div class="input-group">
                     <input	id="nombre" type="text" name="name" required >
                  </div>
               </div>
            </div>
            
            
            <div class="form-group">
               <label class="control-label col-xs-6">carpeta:</label>
               <div class="col-xs-6">
                  <div class="input-group">
                     <label class="radio-inline">
                           <input type="radio" name="carpeta"  required value="1">config
                     </label>
                     <label class="radio-inline">
                           <input type="radio" name="carpeta"  required value="2">log
                     </label>
                  </div>
               </div>
            </div>
            
            <div class="form-group">
            
               <div class="col-xs-offset-6 col-xs-6">
                  <button type="submit" class="btn btn-primary"> Subir</button>
                     
               </div>
            </div>			
		
	      </form>	   
      </div>
   </div>


	<br>
	<br>
	<br>
	
   	<div class="panel panel-default">
		<div class="panel-heading">Descargar Archivo</div>
	   <div class="panel-body">
			<form method="GET" action="downloadFile" enctype="multipart/form-data" class="form-horizontal">			
           
            <div class="form-group">
               <label class="control-label col-xs-6">Nombre del archivo a descargar</label>
               <div class="col-xs-6">
                  <div class="input-group">
                     <input	type="text" name="name" required>
                  </div>
               </div>
            </div>
            
            
            <div class="form-group">
               <label class="control-label col-xs-6">carpeta:</label>
               <div class="col-xs-6">
                  <div class="input-group">
                     <label class="radio-inline">
                           <input type="radio" name="carpeta"  required value="1">config
                     </label>
                     <label class="radio-inline">
                           <input type="radio" name="carpeta"  required value="2">log
                     </label>
                  </div>
               </div>
            </div>
            
            
            <div class="form-group">
            
               <div class="col-xs-offset-6 col-xs-6">
                  <button type="submit" class="btn btn-primary">Descargar</button>
                     
               </div>
            </div>			
		
	      </form>	   
      </div>
   </div>



</body>

</html>