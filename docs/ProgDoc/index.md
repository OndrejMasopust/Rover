# Programátorská dokumentace
[Java dokumentace](JavaDoc/index.html)   
[Python dokumentace](PythonDoc/index.html)   
[C dokumentace](CDoc/index.html)

`Jar` soubor s řídící aplikací je možné najít na [tomto odkazu](FIXME).   
Program pro Raspberry Pi lze nají [zde](FIXME) (složky s názvem `test` nejsou pro běh programu nutné).   
Firmware pro ATMega 168 je na [tomto odkazu](FIXME).

# Popis fungování

## Autíčko
### Úvod
Autíčko je postavené nad platformou Raspberry Pi, která obstarává většinu operací. Jako programovací jazyk pro Raspberry jsem si zvolil Python, jelikož je pro tuto platformu nejvíce podporován.
Pro konverzi analogových dat ze senzoru do digitální podoby je použit mikrokontrolér ATMega 168 od firmy MicroChip.
Obousměrný provoz motorů obstarává integrovaný obvod L293D od firmy Texas Instruments.

### Napájení
Celé autíčko je napájeno baterií typu Li-pol se 3
články a kapacitou 1300mAh. Provozní doba autíčka na jedno nabití by teoreticky mohla být klidně přes 2 hodiny (průměrná spotřeba byla naměřena okolo 500mAh). Konverzi napětí z úrovně baterky na
úroveň "příjemnou" obvodům a motorům obstarávají integrované obvody LM2576 též od firmy Texas Instruments. Jsou použity dvě verze tohoto obvodu. První verze je pro konverzi napěťové úrovně na
fixní napětí 5V pro napájení "logiky". Druhá verze s nastavitelným výstupním napětím dodává 6V pro napájení motorů. Tyto obvody vyžadují několik dalších pasivních součástek pro správné fungování.
Ty jsou na plošném spoji v blízkosti samotných LM2576 (oblasti okolo cívek).

Obvody LM2576 jsou tzv. spínané zdroje napětí. To znamená že výstupní napětí je upravováno pomocí PWM (pulse width modulation). To má za důsledek efektivní konverzi napětí (dokumentace uvádí
účinnost až 77%). Dalším druhem napěťových regulátorů jsou tzv. lineární regulátory, které ovšem fungují v podstatě jako proměnné rezistory a nejsou tudíž moc efektivní. Přebytečnou elektrickou
práci vyzáří ve formě tepla. Proto jsem se rozhodl pro spínaný zdroj oproti lineárnímu regulátoru.

### Zatáčení
Zatáčecí mechanismus je řešen servem od firmy Hitec. Mechanismus zatáčení jsem okopíroval z Lego stavebnice
["Dump Truck"](https://www.lego.com/en-us/service/buildinginstructions/search?initialsearch=8415#?text=8415). Servo je ovládáno z Raspberry Pi pomocí tzv. PWM (pulse width modulation). Jelikož je
pro ovládání serva potřeba velké preciznosti signálu, zvolil jsem pro tento účel knihovnu [pigpio](http://abyz.me.uk/rpi/pigpio/), jejíž autorem je FIXME. Tato knihovna poskytuje API, které umožňuje
snadné ovládání serva zadáním délky služebního cyklu (duty cycle). Pro hodnotu `1500` se ručička serva nastaví do střední polohy. Bezpečné meze otáčení z hlediska serva je interval <1000; 2000>.
Ovšem pro konkrétní potřeby Roveru je tento interval omezen.

### Pohyb
Pohyb autíčka obstarává stejnosměrný motor uložený vedle baterie. K jeho ovládání je použit již zmíněny obvod L293D. Ten přijímá signál PWM (pulse width modulation) od Raspberry a podle toho je
schopen upravit napětí proudící do motoru. Možnosti měnit napětí jdoucí do motoru není ovšem využito. Do motoru tedy jde buď 6V, nebo 0V.

L293D ovšem obstarává ještě jednu důležitou funkci a tou je možnost změny polarity výstupu k motoru a tudíž změna směru otáčení motoru. Toho je docíleno pomocí tzv. "H můstku" (H-bridge)
uvnitř L293D. Pro jistotu je k němu připojen pasivní kovový chladič jako prevence přehřátí.

### Senzor
Jako senzor vzdálenosti jsem použil výrobek firmy Sharp. Konkrétně ten s označením `GP2Y0A710K0F`. Měří vzdálenost pomocí paprsku světla. Jeho nevýhodou ovšem je, že má rozsah jen 100cm až 500cm.
Vzdálenosti menší než 100cm se budou na výstupu jevit jako vyšší a nelze je odlišit. Pro měření vzdáleností kratších než 100cm by bylo potřeba využít jiného senzoru.

Rotace senzoru je řešena pomocí stejnosměrného motoru. Přenos dat z rotujícího senzoru je řešen pomocí tzv. "slip ring". Ten umožňuje elektrický kontakt mezi dvěma navzájem rotujícími objekty.
Jelikož je motor stejnosměrný a není tedy žádná zpětná vazba ohledně jeho aktuální polohy, obsahuje držák senzoru i optozávoru. Ta se stará o informování Raspberry o tom, že senzor vykonal jednu
otáčku. Optozávora se skládá z LED diody a fototranzistoru. Když je paprsek světla z diody do tranzistoru přerušen výstupkem na senzorovém kotouči, tranzistor se uzavře a tím vyšle signál do
Raspberry. To je uvnitř nastaveno tak, aby tyto signály zpracovalo náležitým způsobem.

Aby mohla řídící aplikace správně vizualizovat data, je nutné na začátku měření provést několik "volných otáček", kdy nejsou data ze senzoru vyžadována. Během těchto rotací se motor dostane na
konstantní rychlost otáčení a v Raspberry Pi je změřena doba jedné otáčky na základě prodlevy signálů z optozávory. Ta je poté vydělena dobou jednoho měření senzoru a výsledkem je počet měření
během jedné otáčky. Jedno měření trvá dle dokumentace přibližně 20ms a s touto hodnotou se také pracuje v programu. Jiná možnost, jak být od senzoru upozorněn na novou naměřenou hodnotu není.

Vypočítané rozlišení se poté
pošle řídící aplikaci a slouží ke správnému umístění později přijatých dat na obrazovku. Raspberry také po každém vykonání otáčky senzoru pošle do řídící aplikace příkaz k resetování indexu měření,
aby aplikace znovu začala umisťovat naměřená data od počátečního pozice a nedošlo k pootočení obrazu vykreslovaném v aplikaci (více o tom později).

Výstup
senzoru je řešen analogově. Podle naměřené vzdálenosti se mění úroveň napětí na výstupu. O konverzi toho analogového výstupu do digitální podoby se, jak už jsem zmínil, stará mikrokontrolér ATMega
168.

### ATMega 168
Mikrokontrolér ATMega 168 je naprogramován v jazyce C a k jeho časování je použit krystal o frekvenci 16MHz. Komunikace s Raspberry Pi je řešena pomocí protokolu I<sup>2</sup>C. Raspberry Pi je
v této komunikace v roli tzv. "master" a ATMega jako tzv. "slave". Jelikož ATMega není schopno
vykonávat více procesů najednou, je jeho program řešen pomocí tzv. přerušení (interrupts). Na samotném začátku programu je provedena jedna konverze, která se uloží a ATMega čeká na pokyn od Raspberry. Když
si Raspberry vyžádá data, ATMega mu pošle data z poslední konverze a provede konverzi novou. Poté znovu čeká na vyžádání dat přes I<sup>2</sup>C. Komunikaci přes I<sup>2</sup>C je na straně
Raspberry Pi řešena pomocí knihovny [smbus2](https://pypi.python.org/pypi/smbus2/0.2.0).

Zpracování dat v Raspberry Pi spočívá v přepočtení přijaté relativní hodnoty na volty a poté pomocí dané funkce přepočtení voltů na centimetry. Funkce byla stanovena pomocí
zpracování pokusných naměřených hodnot v programu Microsoft Office Excel. Přepočtená data se posílají přes TCP protokol do řídící aplikace na operátorově počítači, kde se vizualizují.

### Příkazy
Příkazy, které může Rover řídící aplikaci posílat jsou následující:

#### rd
Tento příkaz slouží k informování operátora o tom, že již byly provedeny "volné otáčky". Jde o zkratku slova "ready".

#### ro
Příkaz `ro` je vždy následován celou číslovkou, která označuje počet měření během jedné otáčky. Tento příkaz je spolu s příkazem `rd` posílán vždy po dokončení "volných otáček". Příkaz zvolen
jako zkratka slova "resolution".

#### dt
Příkaz `dt` slouží k přenosu naměřených hodnot. Je následován celou číslovkou označující vzdálenost v centimetrech. `dt` je zkratka slova data.

#### rc
Tento příkaz je poslán Roverem vždy po dokončení jedné otáčky a instruuje řídící apliakci, že následující data má zobrazovat od počátečního bodu s indexem 0. Jde o synchronizaci, aby nedošlo k
pootočení vizualizovaného obrázku (více o tom později). Jde o zkratku slovního spojení "reset dot counter".

#### er
Příkaz `er` je vždy následován chybovou hláškou, která se má zobrazit v konzoli řídící aplikace. Tato zpráva bude v červené bublině, aby se odlišilo, že jde o chybu. `er` je zkratka pro "error".

#### bt
Tento příkaz zatím Rover neposílá, ale řídící aplikace ho umí zpracovat. Jde o změnu ukazatele stavu baterie v řídící aplikaci. Zpětná vazba o stavu baterie není v zadání práce a není zatím funkční.
Příkaz by měl být vždy následován číslem, které se má v ukazateli zobrazit a byl zvolen jako zkratka slova "battery".

## Řídící aplikace
Řídící aplikace je naprogramována v jazyce Java. Grafické rozhraní je vytvořeno pomocí API JavaFx. Neúspěšný pokus o navázání komunikace s Roverem je na začátku běhu programu řešen nekonečnou
smyčkou, kdy se program stále snaží komunikaci navázat. Pokud chce uživatel zastavit pokusy o navázání komunikace, musí sám aplikaci ukončit. Jak již bylo zmíněno v uživatelské dokumentaci,
ovládací aplikace obstarává vizualizaci dat ze senzoru a bezdrátové ovládání pohybu autíčka.

Po připojení k Roveru běží aplikace na 2 vláknech. První se stará o grafické rozhraní a to druhé o přijímání zpráv od Roveru. Pokud přijde zpráva od Roveru, komunikační vlákno ji předá grafickému
vláknu, které poté zprávu buď zobrazí v konzoli na levé straně obrazovky, nebo vykoná daný příkaz. Některé příkazy od Roveru se v konzoli nezobrazují z úsporných důvodů.

### Systém vizualizace dat
Data z měření jsou z Roveru posílána bez informace o jejich úhlové poloze. Jedinou synchronizaci poskytuje příkaz `rc`. Z tohoto důvodu si řídící aplikace spočítá úhel mezi jednotlivými měřeními na
základě přijatého rozlišení (příkaz `ro`) poté, co Rover dokončí "volné otáčky". Tento úhel se nadále používá pro všechna přijatá měření. Řídící aplikace si ukládá vlastní čítač dat, který se
vždy zvýší o jedno s každou přijatou hodnotou.
Na základě tohoto čítače je nastaven úhel zobrazených dat. Čítač je resetován po přijetí příkazu `rc` a následující přijaté měření je zobrazeno pod úhlem 0° (pozice za Roverem).

Pokud je předchozí měření viditelné, je spojeno s aktuálním měřením pomocí úsečky. To pomáhá představě o půdorysu okolí Roveru.

Vizualizovaná data je možné si přiblížit a oddálit pomocí tlačítek "+" a "-" v pravém horním rohu aplikace.

### Ovládání pohybu Roveru
Ovládat pohyb Roveru je možné z řídící aplikace pomocí mačkání šipek na klávesnici. Pokud je ovšem zrovna prováděna akce zápisu do textového pole pro zadávání příkazů, mačkání šipek nebude mít efekt
ovládání pohybu Roveru. Proto je vždy po odeslání příkazu z textového pole zaměření z toho textového pole odňata, aby bylo možné šipkami pohyb Roveru ovládat.

### Příkazy
Příkazy, které budou mít nějaký efekt po jejich zadání v řídící aplikaci jsou zobrazeny v konzoli žlutým fontem a jsou následující:

#### startMeasure
Tento příkaz instruuje Rover o tom, že má inicializovat senzorový motor a začít posílat data z měření.

#### stopMeasure
Příkaz `stopMeasure` naopak měření zastaví.

#### check
Teto příkaz slouží k ověření komunikace mezi Roverem a řídící aplikací. Pokud Rover ihned odpoví zprávou `check`, je spojení v pořádku. Pokud ne, nastala někde chyba.

#### $resPer
Příkaz `$resPer` slouží k resetování stavu baterie na 100%. Dolar na začátku značí, že jde pouze o interní příkaz. Tento příkaz není odesílán autíčku.

### Informační stránka
V aplikaci je v pravém dolním rohu tlačítko s otazníkem. To slouží ke zobrazení stručné informace ohledně příkazů popsaných výše.

# Zdroje

### Knihovny nestandardní pro použité programovací jazyky
- [Pigpio](http://abyz.me.uk/rpi/pigpio/)
- [smbus2](https://pypi.python.org/pypi/smbus2/0.2.0)
- [RPi.GPIO](https://pypi.python.org/pypi/RPi.GPIO)

### Programy
- [Eclipse IDE](https://www.eclipse.org)
- [Scene Builder](http://gluonhq.com/products/scene-builder/)
- [TextWrangler](https://www.barebones.com/products/textwrangler/)
- [Pandoc](https://pandoc.org)
- [Overleaf](https://www.overleaf.com)
- [ShareLaTex](https://cs.sharelatex.com)
- [Doxygen](http://www.stack.nl/~dimitri/doxygen/)
- [Apache Ant](https://ant.apache.org)
- [Lego Digital Designer](https://www.lego.com/en-us/ldd)

### Tutoriály
Uvedeny pouze ty "nejvíce klíčové" pro můj projekt.
- [JavaFx GUI](https://www.youtube.com/watch?v=FLkOX4Eez6o&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)
- [Java multithreading](https://www.itnetwork.cz/java/vlakna)
- [Python networking](https://www.youtube.com/watch?v=XiVVYfgDolU)
- [Python multithreading](https://www.tutorialspoint.com/python/python_multithreading.htm)
- [Python Event()](https://stackoverflow.com/questions/31247820/how-to-use-event-objects-in-python-with-a-thread)

### Barvy grafického rozhraní
- [Blackberry pallete](https://developer.blackberry.com/design/bb10/color.html)

### Obrázky
#### Odesílací šipka v hlavním okně řídící aplikace
<div>Icons made by <a href="https://www.flaticon.com/authors/google" title="Google">Google</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by
<a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
#### Obrázek tanku v hlavní okně řídící aplikace
Autor nevyžaduje být uveden. Uvedu tedy alespoň [odkaz na webovou stránku](http://2dgameartforfree.blogspot.cz/2014/09/top-down-extras-2-tank.html), kde lze obrázek stáhnout.

### "Lidské" zdroje
Se schématem plošného spoje mi pomohl Ondřej Dvorský.   
S vyladěním programu pro Raspberry Pi mi pomohl vedoucí práce Vojtěch Horký.   
Oběma patří velké poděkování.