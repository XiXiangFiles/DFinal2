<!DOCTYPE html>1
<html>
  <head>
    <style type="text/css">
      html, body, #map-canvas { height: 100%; margin: 0; padding: 0;}
    </style>
    <!-- GOOGLE PLACES -->
    <!-- AIzaSyA_x7tk6jO-xg9jDKMcJoku7K9y_3nqvZ4 -->
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBx72KqxQ51WV58J-KeII4np-Sws3h68ik">
    </script>2
    <script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: { lat: 25.037525, lng: 121.563782},
          zoom: 18
        };4
        var map = new google.maps.Map(
            document.getElementById('map-canvas'),
            mapOptions);5
      }
      google.maps.event.addDomListener(
          window, 'load', initialize);6
    </script>
  </head>
  <body>
<div id="map-canvas"></div>3
  </body>
</html>