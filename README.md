##GMVC ve MovieClub projesine hosgeldiniz!
GMVC (GWT uzerine MVC) catisini ornek bir projeyle gercekler. GMVC ve MovieClub Projesi acik kaynak teknolojiler kullanilarak Java' da gelistirilmistir. Proje icerisinde GWT ve GXT ile ilgili karsilasilan bircok sikintinin cozumunu bulabilirsiniz.

  * *GMVC Catisi LGPLV3 ile lisanslanmistir.*
  * *MovieClub Projesi GPLV3 ile lisanslanmistir.*

**Projenin icerisinde bulabilecekleriniz:**

  * Hibernate implementasyonu : Dozer API kullanilarak model siniflar DTO (data transfer object) siniflara cevriliyor.
  * Login formu ve kullanici hak denetimlerinin yapilmasi : Asenkron cagri sonucunun senkron olarak beklenmesi durumu encok Login formlarinda yasanan sikintidir. Ayrica kullanici hak denetimlerinin nasil yapildigina dair ornek bulabileceksiniz.
  * Remote Procedure Call ile SuggestionBox bileseni : GWT ve Ext-Gwt bilesenleri icerisinde bulunan SuggestionBox bileseni static data üzerinde calisiyor, burda ise RPC yolu ile dinamik data üzerinde calisan ornegi bulabilirsiniz.
  * MVC catisi ve cok katmanli programlama : MovieClub Projesinin üzerine bina edildiği temel paterndir. Zaten bu projenin amaci, GMVC modulunun gerceklestirimini yapmaktir. Formlarin ve servislerin kalitim yolu ile edilmesi ve data, kontrol ve gorunumun ayri ayri katmanlarda ele alinmasi ile cok katmanli programlama orneklenmistir.
  * Jasper Report kullanimi : Raporlama araci olarak JasperReport tercih edildi, tasarimlar icin de iReport kullanildi. Raporlarin farkli formatlara dokumunun orneklerini bulabilirsiniz. Html, PDF, CVS, Excel gibi.
  * GWT de Modul yazimi/kullaniminin ornegi : GMVC ayri bir moduldur, bu ornek ile kendi bagimsiz modullerinizi gelistirebilirsiniz.
  * RPC servislerin kalitimmla elde edilebilmesi : CRUD (Create-Read-Update-Delete) servisleri kalitim yolu ile elde ediliyor.
  * Olay temelli haberlesme : Siniflarin bagimliliklarini en aza indirmek icin, MVC siniflari ve aplikasyon idaresinden sorumlu siniflar olay temelli haberlesiyorlar.
  * Projenin Ant Script ile WAR dosyasinin hazirlanmasi : Uygulamanin sunucu uzerinde deploy edilebilmesi icin gereken WAR dosyasini hazirlayan ant scriptini iceriyor.
