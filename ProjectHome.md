<h2>GMVC ve MovieClub projesine hosgeldiniz!</h2>

<strong>GMVC ve MovieClub Projesi</strong> acik kaynak teknolojiler kullanilarak Java' da gelistirilmistir.
<br />
<ul>
<li>Bu projenin amaci, GMVC (GWT uzerine MVC) catisini gerceklemektir.</li>
<li>GMVC catisi icerisinde GWT ve GXT ile ilgili karsilasilan bircok sikintinin cozumunu bulabilirsiniz.</li>
<li>GMVC Catisi LGPLV3 ile lisanslanmistir.</li>
<li>MovieClub Projesi GPLV3 ile lisanslanmistir.</li>
</ul>

<h2>Projenin icerisinde bulabilecekleriniz:</h2>
<ul>
<li>Hibernate implementasyonu : Dozer API kullanilarak model siniflar DTO (data transfer object) siniflara cevriliyor.</li>

<li>Login formu ve kullanici hak denetimlerinin yapilmasi : Asenkron cagri sonucunun senkron olarak beklenmesi durumu encok Login formlarinda yasanan sikintidir. Ayrica kullanici hak denetimlerinin nasil yapildigina dair ornek bulabileceksiniz.</li>

<li>Remote Procedure Call ile SuggestionBox bileseni : GWT ve Ext-Gwt bilesenleri icerisinde bulunan SuggestionBox bileseni static data üzerinde calisiyor, burda ise RPC yolu ile dinamik data üzerinde calisan ornegi bulabilirsiniz.</li>

<li>MVC catisi ve cok katmanli programlama : MovieClub Projesinin üzerine bina edildiği temel paterndir. Zaten bu projenin amaci, GMVC modulunun gerceklestirimini yapmaktir. Formlarin ve servislerin kalitim yolu ile edilmesi ve data, kontrol ve gorunumun ayri ayri katmanlarda ele alinmasi ile cok katmanli programlama orneklenmistir.</li>

<li>Jasper Report kullanimi : Raporlama araci olarak JasperReport tercih edildi, tasarimlar icin de iReport kullanildi. Raporlarin farkli formatlara dokumunun orneklerini bulabilirsiniz. <strong>Html, PDF, CVS, Excel gibi.</strong></li>

<li>GWT de Modul yazimi/kullaniminin ornegi : GMVC ayri bir moduldur, bu ornek ile kendi bagimsiz modullerinizi gelistirebilirsiniz.</li>

<li>RPC servislerin kalitimmla elde edilebilmesi : CRUD (Create-Read-Update-Delete) servisleri kalitim yolu ile elde ediliyor.</li>

<li>Olay temelli haberlesme : Siniflarin bagimliliklarini en aza indirmek icin, MVC siniflari ve  aplikasyon idaresinden sorumlu siniflar olay temelli haberlesiyorlar.</li>

<li>Projenin Ant Script ile WAR dosyasinin hazirlanmasi : Uygulamanin sunucu uzerinde deploy edilebilmesi icin gereken WAR dosyasini hazirlayan ant scriptini iceriyor.</li>

</ul>