Compiler for simple calculator with Coco/R

Fordító egyszerű számológéphez Coco/R-rel

A cél, hogy egyszerű műveletek elvégzésére képes "számológép" legyen a végeredmény.
Ezek a műveletek:

                - összeadás (+)
                - kivonás   (-)
                - szorzás   (*)
                - osztás    (/)
                - korábbi eredmény előhívása kifejezésen belül   (ans)
                - korábbi eredmény előhívása kifejezésként       ('(ANS)')
                - zárójelek kezelése                             ('(')  (')')
                
Működése

A programot elindítva a következő információk jelennek meg:
- Calculator
- Turn on: ON
- Turn off: OFF

Az ON parancsot begépelve további információk jelennek meg:
- Calculator turned ON
- You can use '+','-','*','/','(',')'
- Use last result in calculation: ans
- Use last result as expression: ANS
- Can not handle order of operations, so it is necessary to use parenthesis

Példa működés:

- Calculator
- Turn on: ON
- Turn off: OFF
-       ON
- Calculator turned ON
- You can use '+','-','*','/','(',')'
- Use last result in calculation: ans
- Use last result as expression: ANS
- Can not handle order of operations, so it is necessary to use parenthesis
-       11+49
- 60.0
- Next calculation
-       ans/2
- 30.0
- Next calculation
-       (ans+20)/(90/18)
- 10.0
- Next calculation
-       (ANS)*(ans*ans)
- 1000.0
- Next calculation
-       OFF
- Calculator turned OFF                


Compiler felépítése

Egy compiler 4 részből áll. Ezek:

    - scanner
    - parser
    - symbol table
    - code generator
    
A scanner és a parser specifikációja is a Grammar.ATG fájlban találhatók.

A scanner két legfontosabb eleme a CHARACTERS, illetve a TOKENS.

A CHARACTERS-ben találhatók a karakterek, karaktercsoportok egy változóhoz rendelve.
A TOKENS-ben az előbb definált karakterekből TOKEN-eket (terminálisokat) szükséges létrehozni, amik használhatók lesznek a szabályokban.

A parserben a szabályokat és a szemantikai műveleteket adjuk hozzá a compilerhez.

Szabályok
- minden nemterminálishoz pontosan egy elem tartozik
- a kezdő szimbólumra mindenképp szükséges, hogy legyen production (a nyelvtan neve)

A kapott inputot a scanner segítségével beolvassuk, majd a parser-rel értelmezzük.

A Compiler neve Calculator, így az első production a Calculator lesz.
Visszatérési értéke az inputból számított érték.
A Calculator 2 féle képpen épülhet fel
- 1 db Expression -> A beírt számot adja vissza
- 1 db ParenthesisExpression és bármennyi Operation-ParenthesisExpression páros

Az Expression második Ident tagja kiértékeli a kapott Operationt és ez alapján elvégzi a szükséges műveletet.

Ez az Expression 3 féle képpen épülhet fel
- 1 db Ident -> A beírt számot adja vissza
- 1 db Ident és bármennyi Operation-Ident páros
- 1db ANS kifejezés

Az Expression második Ident tagja kiértékeli a kapott Operationt és ez alapján elvégzi a szükséges műveletet.

Az Ident két féle képpen épülhet fel:
- 1 db előjeles/előjel nélküli egész/nem egész szám
- 1db ans kifejezés

Az Operation lehet:
- '+'
- '-'
- '*'
- '/'

Minden nemterminális elemhez tartozhat valamilyen szemantikai művelet, ezek segítségével számolódik ki a beírt kifejezés.
A kapott eredményt a Calculator osztály egy metódusa írja ki a console-ra, ekkor tárolódik el az utolsó számolt eredmény az ans változóba, amit ezekután fel lehet használni.

A Coco.jar file ebből a .ATG kiterjesztésű állományból állítja elő a Scanner-t és a Parser-t, amiket felhasználhatunk a Java kódban.
********

    

