<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Reprezentacja Polski na Turniejach</title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>
    <h1><%= "Reprezentacja Polski na dużych Turniejach" %></h1>
    <br/>
    <div class = "text">
      <img alt="imgPol1" id="imgPol1" src="imgs/imgPol1.jpg">
      <p1>
        Reprezentacja Polski w piłce nożnej, założona w 1921 roku,
        zyskała uznanie nie tylko dzięki charakterystycznym czerwono-białym strojom,
        ale także bogatej historii i wielu osiągnięciom na boisku.
      </p1>

      <h3>Występy Reprezentacji Polski na Mistrzostwach Europy</h3>
      <p2>
        Dotychczas Reprezentacja Polski brała udział w czterech edycjach Mistrzostw Europy.
        Najlepszy wynik osiągnęła w 2016 roku, docierając do ćwierćfinału.
        Choć w 2020 roku nie udało się awansować do fazy pucharowej,
        drużyna pokazała swoją siłę w barażach, zyskując uznanie i wsparcie kibiców.
        W 2024 roku ponownie postara się nie zawieść kibiców na turnieju organizowanym w Niemczech.
      </p2>
      <h3>Harmonogram Mistrzostw Europy 2024 Reprezentacji Polski</h3>
      <p3>Reprezentacja Polski rozpoczyna zmagania na Mistrzostwach Europy 2024,
        przygotowując się do serii zaciętych meczów. <br>
        Oto harmonogram spotkań w grupie D:<br>
        16/06: Polska vs Holandia (Hamburg, 15:00)<br>
        21/06: Polska vs Austria (Berlin, 18:00)<br>
        25/06: Francja vs Polska (Dortmund, 18:00)<br>
        Mecze te odbędą się w różnych miastach Niemiec,
        a Kibice Reprezentacji Polski z niecierpliwością oczekują na widowiskowe występy Reprezentacji Polski
        i gotowi są wspierać ich na każdym kroku!
      </p3>

    </div>
    <nav class="container">
      <a href="scores-servlet" id="servletLink">Wyniki Poprzednich Turniejów</a>
      <a href="https://www.laczynaspilka.pl/" id="laczynaspilkaLink">Laczy nas pilka</a>
    </nav>
  </body>
</html>