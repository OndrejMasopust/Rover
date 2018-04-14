# Rover
Rover je dálkově řízené autíčko s otočným lidarem. Ovládací aplikace umožňuje vizualizaci dat ze senzoru a ovládání autíčka.

## Autíčko
Hlavní konstrukce autíčka je setavená z lego kotiček. Návod na sestavení můžete najít [zde](https://ondrejmasopust.github.io/Rover/HowTo/index.html). K sestavení je nutná znalost pájení.

Důležité upozornění: Nesahejte na jakýkoliv plošný spoj na autíčku po jeho sestavení. Může dojít k poškození statickou elektřinou, zkratu a dalšímu poškození.

Autíčko se zapne přepnutím přepínače vedle baterie. V tu chvíli by měly indikační LED diody na Raspberry Pi začít blikat. Je nutné chvíli počkat, než se Raspberry Pi načte a poté je možné
se k autíčku připojit přes Wi-Fi (stejně, jako byste se připojovali k běžné Wi-Fi např. u vás doma). V tuto chvíli ovšem není ještě možné autíčko ovládat. Je nutné spustit danou aplikaci, která
má běžet na Raspberry Pi. Toho se docílí tak, že se připojíte k Raspberry Pi přes `ssh` pomocí příkazové řádky:
```
ssh pi@raspberrypi.local
```
![ssh](usrMan-imgs/ssh.tiff)   
Budete vyzváni k zadání hesla. Znaky se nebudou zobrazovat na obrazovce během toho, jak je budete zadávat. To je běžné chování a je to tak z bezpečnostních důvodů.

Až se přihlásíte do terminálu Raspberry Pi, přejděte pomocí příkazu `cd` do složky se souborem `Main.py`. V mém případě to znamená:
```
cd Documents/Ondra/Rover/src/com/masopust/ondra/python/main/
```
Nyní můžete spustit program pomocí příkazu:
```
python3 Main.py
```
V tuto chvíli program čeká, až se někdo pokusí s ním navázat komunikaci. Toho se docílí v počítačové aplikaci, která je součástí projektu. Pokud se připojení podaří, v terminálu se ukáže
(IP adresa a číslo portu se mohou lišit):
```
Connection from ('192.168.0.6', 54710) was successful
```

## Aplikace
Ovládací aplikace je napsána v Javě. Pro tvorbu uživatelského rozhraní bylo použito API JavaFx. Aplikace obstarává vizualizaci dat ze senzoru a bezdrátové ovádání pohybu autíčka. Aplikaci
lze spustit dojklikem na soubor `RoverControlPanel.jar`, který lze stáhnout [zde](FIXME). Po spuštění aplikace budete vyzvání k zadání IP adresy autíčka a portu, přes který se chcete zkusit
k Raspberry Pi připojit. Pokud neznáte IP adresu autíčka, lze zadat `raspberrypi.local`. To by mělo též fungovat. Jako port uveďte číslo `5321`, pokud jste toto číslo nezměnili v programu, který
běží na Raspberry Pi. Po zadání hodnot stiskěte tlačítko `Connect`, nebo stiskněte `Enter` na vaší klávesnici. Pokud chcete program zavřít, stiskněte tlačítko `Cancel`, nebo křížek.   
![ipPrompt](usrMan-imgs/ipPrompt.tiff)   
Dále se zobrazí okno, které zobrazuje stav připojování k Roveru. Pokud vše probíhá hladce, neobjeví se žádná hláška v černém obdélníku:   
![connecting-wo-error](usrMan-imgs/connecting-wo-error.tiff)   
Pokud je něco v nepořádku, vypíše se popis chyby v černém obdélníku:
![connecting-w-error](usrMan-imgs/connecting-w-error.tiff)   
Pokud se vám podaří připojit úspěšně k autíčku, dostanete se do hlavního okna a v konzoli se vám objeví zpráva od Roveru, že spojení proběhlo úspěšně:
![mainWindow](usrMan-imgs/mainWindow.tiff)   
Uprostřed okna se zobrazí malý obrázek tanku, který symbolizuje váš Rover.

Nyní můžete ovládat Rover dle libosti. Mačkáním šipek můžete uvádět Rover do pohybu (vzad i vpřed) a zatáčet. Příkazi lze zadávat přes konzoli v levé části okna. Do textového pole v levém dolním
rohu napište zprávu, kterou chcete odeslat a poté stiskněte `Enter`, nebo myší stiskněte šipku vpravo od textového pole. Vámi zadaná zpráva se objeví v konzoli zarovnaná doprava, což značí, že
jste zprávu odelali vy. Pokud se objeví zpráva zarovnaná doleva, je to zpráva od autíčka. Příkazy poslané od vás autíčku, které vyvolají nějakou akci se v bublině zobrazí žlutým fontem. Lze tak
ověřit správné napsání příkazu.

Pokud Roveru odešlte přes konzoli příkaz `startMeasure`, spustí se
snímání okolí senzorem. Snímání lze zastavit odesláním příkazu `stopMeasure` stejným způsobem. Také lze poslat Roveru příkaz `check`. Tím lze ověřit připojení. Pokud Rover ihned odpoví
zprávou se zněním `check`, spojení je v pořádku. Pokud ne, je někde problém a spojení s autíčkem bylo z nějakého důvodu přerušeno. Další funkce jsou popsány na obrázku:   
![mainWindow-w-desc](usrMan-imgs/mainWindow-w-desc.tiff)   
Ukazatel stavu baterie zatím není funkční (není v zadání práce), je zde pouze připravený pro budoucí možné rozšíření.   
Stručná informační stránka vypadá takto:   
![mainWindow-w-info](usrMan-imgs/mainWindow-w-info.tiff)   
Vizualizace dat ze senzoru může vypadat například takto:
![mainWindow-w-data](usrMan-imgs/mainWindow-w-data.tiff)   
Na vizualizaci můžeme jasně rozpoznat roh vpravo, u kterého se Rover nachází.

Aplikaci můžete zavřít křížkem. Program v Roveru se poté sám ukončí, jelikož dojde k přerušení komunikace.