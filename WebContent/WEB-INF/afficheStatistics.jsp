<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3/dist/Chart.min.js"></script>
	<title>Scraping</title>
</head>

<body>
	<h1 style="font-family: Georgia, serif">Les statistiques d'Avito pour Aujourd'hui (2019)</h1>
	<br>
	<!--  <table>		
		<tr>
			<th>Catégorie</th>
			<th>Nombre d'annonces</th>
		</tr>
		<c:forEach items="${requestScope.statistics}" var="statistic" varStatus="boucle">		
			<tr>
				<td>${statistic.key.title}</td>	
				<td>${statistic.value}</td>			
			</tr>
		</c:forEach>
	</table>-->
	<table class="table">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col">Catégorie</th>
		      <th scope="col">Nombre d'annonces</th>
		    </tr>
		  </thead>
		  <tbody>
		  		<c:forEach items="${requestScope.statistics}" var="statistic" varStatus="boucle">		
				    <tr>
				      <td>${statistic.key.title}</td>
				      <td>${statistic.value}</td>
				    </tr>
				</c:forEach>	
		  </tbody>
	</table>
	<canvas id="myChart"></canvas>
		<script>			
			var values = '${requestScope.values}';
			var statisticsValues = [];
			var num ="";
			
			for(var i=0;i<values.length;i++){
				if(values[i]!="["){
					if(values[i]!="," && values[i]!="]"){
						num+=values[i];
						continue;
	
					}
					else{
						statisticsValues.push(parseInt(num,10));
						num = "";
					}
				}
			}
			
			var categories = '${requestScope.categories}';
			var statisticsCategories = [];
			var str = ""

			for(var i=0;i<categories.length;i++){
				if(categories[i]!="[" && categories[i]!=" "){
					if(categories[i]!="," && categories[i]!="]"){
						str+=categories[i];
						continue;
	
					}
					else{
						statisticsCategories.push(str);
						str = "";
					}
				}
			}

			console.log(statisticsValues);	
			console.log(statisticsCategories);
			var colors  = ['#00CED1','#FF1493','#FFD700','#0000CD','#32CD32','#FF6347','#9400D3','#FF0000','#006400'];
			var ctx = document.getElementById('myChart').getContext('2d');
			var chart = new Chart(ctx, {
			    // The type of chart we want to create
			    type: 'doughnut',
	
			    // The data for our dataset
			    data: {
			        labels: statisticsCategories,
			        datasets: [{
			            label: 'Statistics',
			            backgroundColor: colors,
			            borderColor: colors,
			 
			            data: statisticsValues
			        }]
			    },
	
			    // Configuration options go here
			    options: {
			    	responsive: true,
					legend: {
						position: 'top',
					},
					title: {
						display: true,
						text: 'Statistics'
					},
					animation: {
						animateScale: true,
						animateRotate: true
					}
			    }
			});
		</script>
	
</body>
</html>