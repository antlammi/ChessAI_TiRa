### Osa 1

En tiedä vaaditaanko tätä raporttia, mutta kirjoitan silti. Viikon alkuun lähdin työstämään XBoard yhteyttä, jota jo ehdin kutsumaan viime viikolla helpoksi ominaisuudeksi toteuttaa. Käytännössä sen kanssa oli turhankin paljon ongelmia. Ongelmaksi osoittautui XBoard-sovelluksen lähettämät keskeyttämis-signaali. Tähän oli erillinen komento, joka esti näiden lähettämisen. Yksinkertainen korjata, mutta erittäin haastava löytää. Toki, jos olisi tarpeeksi hyvin lukenut dokumentaation läpi, olisi tästä ollut maininta. Sain sen kuitenkin korjattua ja nyt engine toimii XBoardin kanssa Unix-järjestelmillä ja WinBoardin kanssa Windowsilla.

Lähdin loppuviikosta koittamaan optimoida sovelluksen toimintaa, mutta lähdin toteuttamaan sitä hieman turhan kunnianhimoisesti. Periaatteessa ratkaisu, jota lähdin toteuttamaan olisi nopeuttanut siirtojen etsimistä noin 5-10-kertaisesti ja mahdollistanut haun syvyyden nostamisen ainakin yhdellä. Käytännössä se vaati liian monen asian muuttamista, ja sen toimimaan saaminen olisi vaatinut liikaa koodin uudelleenkirjoittamista. Lähinnä aiemmin kirjoitetun koodin sotkuisuus oli esteenä. Käytin tähänkin valitettavan paljon aikaa saamatta mitään aikaan, mutta ei voi mitään.

On monia pieniä asioita, joilla voisin saada siirtojen etsimistä nopeutettua *vähän*, mutta käytännössä tällä ei ole mitään merkitystä kokonaisuuden kannalta. Koitan ehkä hieman muuttaa palasten pistearvoja ja tämän avulla saada enginen vähän paremmaksi. 

Nyt ennen loppupalautusta, koitan vain lähinnä hioa dokumentaation kuntoon ja ehkä tekemään pieniä muutoksia siellä sun täällä.
