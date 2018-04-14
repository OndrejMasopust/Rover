# Programátorská dokumentace
[Java dokumentace](JavaDoc/index.html)   
[Python dokumentace](PythonDoc/index.html)   
[C dokumentace](CDoc/index.html)

Autíčko je postavené nad platformou Raspberry Pi, která obstarává většinu operací. Pro konverzi analogových dat ze senzoru do digitální podoby je použit mikrokontrolér ATMega 168 od firmy AVR.
Oubousměrný provoz motorů obstarává integrovaný obvod L293D od formy Texas Instruments. Celé autíčko je napájeno baterií typu Li-pol se 3 články a kapacitou 1300mAh. Provozní doba autíčka na
jedno nabití by teoreticky mohla být klidně přes 2 hodiny (průměrná spotřeba byla naměřena okolo 500mAh). Zatáčecí mechanismus je řešen servem od firmy Hitec. Mechanismus zatáčení jsem okopíroval z Lego stavebnice
["Dump Truck"](https://www.lego.com/en-us/service/buildinginstructions/search?initialsearch=8415#?text=8415).

Jako senzor vzdálenosti jsem použil výrobek firmy Sharp. Konkrétně ten s označením