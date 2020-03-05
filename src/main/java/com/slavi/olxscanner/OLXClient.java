package com.slavi.olxscanner;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* [["Wszystkie kategorie","0"],["Motoryzacja","5"],["Wszystkie w Motoryzacja","5"],["Samochody osobowe","84"],["Motocykle i Skutery","81"],["Dostawcze i Ciężarowe","85"],["Przyczepy i Pojazdy użytkowe","1333"],["Części samochodowe","82"],["Części motocyklowe","1335"],["Opony i Felgi","658"],["Sprzęt car audio","1461"],["Pozostała motoryzacja","80"],["Nieruchomości","3"],["Wszystkie w Nieruchomości","3"],["Mieszkania","1307"],["Domy","1309"],["Działki","709"],["Biura i Lokale","710"],["Garaże i Parkingi","124"],["Noclegi","1289"],["Stancje i Pokoje","11"],["Hale i Magazyny","1321"],["Pozostałe nieruchomości","32"],["Praca","4"],["Wszystkie w Praca","4"],["Administracja biurowa","1421"],["Budowa / remonty","1443"],["Edukacja","53"],["Finanse / księgowość","1423"],["Franczyza / Własna firma","1529"],["Fryzjer / kosmetyczka","1215"],["Gastronomia","1437"],["HR","1425"],["Hostessa","520"],["Hotelarstwo","1439"],["IT / telekomunikacja","56"],["Kasjer / ekspedient","1435"],["Kierowca / kurier","64"],["Magazynier","1445"],["Marketing / PR / media","1429"],["Mechanik / blacharz / lakiernik","1441"],["Obsługa klienta / call center","164"],["Ochrona","1213"],["Opiekunki dla dzieci","111"],["Praca za granicą","1481"],["Produkcja","1447"],["Przedstawiciel handlowy","62"],["Roznoszenie ulotek","1463"],["Sprzątanie","1449"],["Wykładanie towaru / inwentar.","1469"],["Zdrowie i opieka","163"],["Pozostałe oferty pracy","65"],["Praca dodatkowa / sezonowa","1451"],["Praktyki / staże","1453"],["Kadra kierownicza","1538"],["Dom i Ogród","628"],["Wszystkie w Dom i Ogród","628"],["Meble","565"],["Sprzęt AGD","282"],["Zdrowie","732"],["Ogród","584"],["Narzędzia","1193"],["Materiały budowlane","292"],["Ogrzewanie","279"],["Wyposażenie wnętrz","1409"],["Pozostałe dom i ogród","293"],["Elektronika","99"],["Wszystkie w Elektronika","99"],["Telefony komórkowe","101"],["Akcesoria telefoniczne","1407"],["Komputery","443"],["Tablety","1155"],["Telewizory","729"],["Gry i Konsole","93"],["Sprzęt video","726"],["Fotografia","727"],["Sprzęt audio","728"],["Pozostała elektronika","451"],["Moda","87"],["Wszystkie w Moda","87"],["Ubrania","642"],["Buty","224"],["Bielizna","219"],["Odzież ciążowa","1319"],["Dodatki","506"],["Biżuteria","401"],["Torebki","1595"],["Zegarki","626"],["Kosmetyki i perfumy","406"],["Pozostała moda","405"],["Rolnictwo","757"],["Wszystkie w Rolnictwo","757"],["Ciągniki","1175"],["Maszyny rolnicze","759"],["Przyczepy","1177"],["Części do maszyn rolniczych","1265"],["Opony rolnicze","1267"],["Produkty rolne","761"],["Giełda zwierząt","763"],["Ryneczek","1303"],["Pozostałe rolnicze","765"],["Zwierzęta","103"],["Wszystkie w Zwierzęta","103"],["Psy","139"],["Koty","138"],["Ptaki","1455"],["Gryzonie i Króliki","1457"],["Konie","1459"],["Akwarystyka","1293"],["Pozostałe zwierzęta","140"],["Akcesoria dla zwierząt","175"],["Zaginione i znalezione","180"],["Dla Dzieci","88"],["Wszystkie w Dla Dzieci","88"],["Zabawki","231"],["Wózki dziecięce","516"],["Akcesoria dla niemowląt","1602"],["Foteliki - Nosidełka","517"],["Ubranka dla dzieci","230"],["Buciki","734"],["Meble dla dzieci","743"],["Pozostałe dla dzieci","235"],["Sport i Hobby","767"],["Wszystkie w Sport i Hobby","767"],["Bilety","1527"],["Kolekcje","431"],["Militaria","1601"],["Rowery","461"],["Fitness","771"],["Sporty zimowe","769"],["Wędkarstwo","773"],["Turystyka","465"],["Społeczność","6"],["Sporty wodne","1517"],["Odzież sportowa","1523"],["Obuwie sportowe","1525"],["Pozostały sport i hobby","100"],["Akcesoria jeździeckie","1540"],["Skating","1670"],["Muzyka i Edukacja","751"],["Wszystkie w Muzyka i Edukacja","751"],["Książki","453"],["Muzyka","715"],["Filmy","1173"],["Instrumenty","714"],["Sprzęt muzyczny","1169"],["Pozostała muzyka i edukacja","755"],["Materiały językowe","1530"],["Usługi i Firmy","619"],["Wszystkie w Usługi i Firmy","619"],["Usługi","1542"],["Współpraca biznesowa","168"],["Wyposażenie firm","375"],["Ślub i Wesele","49"],["Wszystkie w Ślub i Wesele","49"],["Moda ślubna","1485"],["Usługi ślubne","1487"],["Akcesoria ślubne","1489"],["Oddam za darmo","1151"],["Zamienię","1153"]] */

public class OLXClient {
  WebClient webClient;

  public OLXClient(Vertx vertx) {
    WebClientOptions options = new WebClientOptions();
    options.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36 OPR/66.0.3515.115");
    options.setConnectTimeout(5000);
    options.setIdleTimeout(5);
    options.setSslHandshakeTimeout(5);
    options.setFollowRedirects(true);
    options.setKeepAlive(false);

    webClient = WebClient.create(vertx, options);
  }

  private void request(String method, String endpoint, MultiMap data, Handler<AsyncResult<HttpResponse<Buffer>>> handler) {
    HttpRequest<Buffer> req =
      webClient
        .raw(method, 443, "www.olx.pl", endpoint);
    req.ssl(true);

    req.putHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
    req.putHeader("accept-encoding", "none");
    req.putHeader("accept-language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
    req.putHeader("cache-control", "no-cache");
    req.putHeader("origin", "https://www.olx.pl");
    req.putHeader("pragma", "no-cache");
    req.putHeader("referer", "https://www.olx.pl/");

    if (data == null || data.isEmpty()) {
      req.send(handler);
    } else {
      req.sendForm(data, handler);
    }
  }

  public void search(String keyword, Handler<AsyncResult<OfferListResponse>> handler) {
    MultiMap data = MultiMap.caseInsensitiveMultiMap();
    data.set("q", keyword);
    data.set("search[city_id]", "");
    data.set("search[region_id]", "");
    data.set("search[district_id]", "0");
    data.set("search[dist]", "");
    request("POST", "/oferty/", data, ar -> {
      if (ar.succeeded()) {
        HttpResponse<Buffer> r = ar.result();
        if (r.statusCode() == 301) {
          String locationRaw = r.getHeader("location");
          if (!locationRaw.isEmpty()) {
            try {
              URL location = new URL(locationRaw);
              request("GET", location.getPath(), null, arr -> {
                if (arr.succeeded()) {
                  HttpResponse<Buffer> rr = arr.result();
                  Document doc = Jsoup.parse(rr.bodyAsString());

                  OfferListResponse response = new OfferListResponse();
                  doc.select("tr.wrap td.offer").forEach(el -> {
                    String id = "",
                           title = "",
                           price = "";

                    Element tmp;
                    if ((tmp = el.select("table[data-id]").first()) != null) {
                      id = tmp.attr("data-id");
                    }
                    if ((tmp = el.select("h3 a strong").first()) != null) {
                      title = tmp.text();
                    }
                    if ((tmp = el.select("p.price").first()) != null) {
                      price = tmp.text();
                    }

                    response.addOffer(new Offer(id, title, price));
                  });

                  handler.handle(Future.succeededFuture(response));
                } else {
                  handler.handle(Future.failedFuture("Could not get offers"));
                }
              });
            } catch (MalformedURLException e) {
              handler.handle(Future.failedFuture("Could not parse URL"));
            }
          } else {
            handler.handle(Future.failedFuture("Invalid search response"));
          }
        } else {
          handler.handle(Future.failedFuture("Invalid search response"));
        }
      } else {
        handler.handle(Future.failedFuture("Could not perform a search"));
      }
    });
  }

}
