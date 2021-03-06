# **Specifikace požadavků**

Program umožňuje z dvojce .csv souborů načíst vozy s informacemi o nich a ceny paliv. Ze získaných dat lze vypočítat náklady na jeden kilometr, dojezd na plnou nádrž, cenu plné nádrže a různé variace těchto dat, jako například cena jednoho kilometru bez započítání cen pohonných hmot. Umožňuje přidat auto do již existujícího výčtu automobilů, vypsat automobily a vypsat aktuální ceny paliv. Dále lze načtené ceny paliva změnit, auta mezi sebou dle různých nákladů porovnávat a dle posledního porovnání ukládá aktuálně načtená auta s výpočty nákladů do textového souboru. Jakožto poslední funkce je možnost odeslat email s individuálním výpočtem nákladů nově zadaného vozu.

# **Návrh řešení**

**Funkční specifikace**

1. Načíst auta
2. Načíst ceny paliv
3. Přidat auto
4. Změnit cenu paliv
5. Seřadit auta (Dle ceny na 1km/ dle dojezdu)
6. Zaslat email s individuálním výpočtem
7. Uložit výpočty aktuálních aut

**Class diagram**

![Class Diagram](https://github.com/pechoutmatej/m20000068_semestralwork/blob/main/img.PNG)

**Popis struktury souboru**

Data jsou uložena ve dvou .csv souborech. V souboru s auty každý řádek reprezentuje jedno auto, struktura řádku je následující.

 &nbsp; _Výrobce, Model, Spotřeba, Velikost nádrže, Ostatní náklady na jízdu, Typ paliva, SPZ_

V souboru s palivy je pouze jeden řádek a jeho struktura je následující.

&nbsp;  _Benzín, Nafta, Ethanol, Benzín 100, LPG_

Ceny paliv jsou načítány z .bin souboru, kde jsou data seřazena za sebou ve stejném pořádí jako v .txt souboru a čtena jsou jako datový typ double

Výstupní textový soubor má na prvním řádku hlavičku:  

 &nbsp; _Značka Model SPZ Cena na Km Cena plné nádrže Typ paliva_  

Pod ní jsou seřazena data všech načtených aut ve stejném pořádí jako hlavička  

Výstupní binární soubor má jednotlivá auta seřazená za sebou:  

 &nbsp; _Výrobce Model Dojezd_


**Testování**

| **Test** | **Očekávaný výsledek** | **Splněno** |
| --- | --- | --- |
| Špatná volba v hl. menu | Upozornění | Ano |
| Neexistující soubor s auty | Upozornění | Ano |
| Neexistující soubor s cenou paliv | Upozornění | Ano |
| Špatný typ při přidávání nového auta | Upozornění | Ano |
| Vypsání seznamu aut před jejich načtením | Upozornění | Ano |
| Vypsání cen paliv před jejich načtením | Upozornění | Ano |
| Změna ceny paliva před jeho načtením | Upozornění | Ano |
| Porovnávání aut před jejich načtením | Upozornění | Ano |
| Načtení aut z existujícího souboru | Proběhne | Ano |
| Porovnání načtených aut | Proběhne | Ano |
