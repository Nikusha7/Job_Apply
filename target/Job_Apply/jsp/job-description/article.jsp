<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="article">

    <div class="article-title">
        <%
            String articleTitle = "დამწყები Java დეველოპერი"; // Replace with your actual content
            request.setAttribute("articleTitle", articleTitle);
        %>
        <h1><%=articleTitle%></h1>
        <div class="job-description">
            <%--since this jsp is included in index.jsp from there it will reach Image folder and its icons(so ignore this warning)--%>
            <img src="Images/location-icon.png" alt="location icon" class="location-icon">
            თბილისი, საქართველო&nbsp;&nbsp;&nbsp;
            <%--since this jsp is included in index.jsp from there it will reach Image folder and its icons(so ignore this warning)--%>
            <img src="Images/suitcase-icon.png" alt="suitcase icon" class="working-hour-icon">
            სრული განაკვეთი
        </div>
    </div>

    <div class="article-description">
        <p><strong>Java დეველოპერი</strong></p>
        <p>საქართველოს ბანკის
            <strong>Agile გუნდში </strong>
            ვეძებთ
            <strong>Java დეველოპერს.</strong>
            თუ ხარ ენერგიული, შემოქმედებითი და შეგიძლია გუნდთან ერთად იმუშაო რთული და ინოვაციური მიზნების
            მისაღწევად,
            მაშინ ჩვენ გთავაზობთ Java დეველოპერის როლს.</p>
        <p></p>
        <p><strong>შენი სამუშაო გარემო:</strong></p>
        <p></p>
        <p>
            შენ გახდები გუნდის წევრი, სადაც სხვადასხვა კომპეტენციის მქონე გამოცდილი პროფესიონალების თვითორგანიზებული
            გუნდი საერთო მიზნების გარშემოა გაერთიანებული.
            გუნდთან ერთად მოიფიქრებ მიზნების მიღწერვის გეგმას და ერთად შეასრულებთ მისი განხორციელებისთვის საჭირო
            ამოცანებს.
        <p>შენ ჩაერთვები ღია და კოლაბორაციულ გარემოში, სადაც გუნდური მუშაობით ვაღწევთ წარმატებას და დაშვებული
            შეცდომები
            გარდაიქმნება ახალ შესაძლებლობებად.
        </p>
        <%--since this jsp is included in index.jsp from there it will reach Image folder and its icons(so ignore this warning)--%>
        <img src="Images/decoration.png" alt="decoration image" class="decoration-image">

        <p><strong>შენი როლით გუნდში:</strong></p>

        <ul>
            <li>მონაწილეობას მიიღებ პროდუქტის შექმნის სრულ ციკლში, როგორიც არის დიზაინის შემუშავება, კოდინგი,
                ხარვეზების
                აღმოფხვრა, დოკუმენტირება
            </li>
            <li> შექმნი მრავალჯერადად გამოყენებად პროგრამულ კომპონენტებს და ბიბლიოთეკას</li>
            <li> ჩაერთვები პრობლემების იდენტიფიცირებისა და აღმოფხვრის პროცესში</li>
            <li> გუნდთან ერთად ჩაერთვები ბიზნეს ამოცანების ანალიზისა და ტექნიკური იმპლემენტაციის გეგმის
                შემუშავებაში
            </li>
        </ul>


        <p><strong>Java დეველოპერობისთვის გჭირდება:</strong></p>
        <ul>
            <li>მინიმუმ ორწლიანი თანამედროვე პროგრამირების ენაზე გამოყენებით კომპლექსური პროგრამული კომპონენტების
                შექმნის გამოცდილება
            </li>
            <li>Spring-ის ტექნოლოგიის გამოყენებით პროგრამული კომპონენტების შექმნისა და განვითარების გამოცდილება
            </li>
            <li>მონაცემთა სტრუქტურების/ ალგორითმების საფუძვლების ცოდნა</li>
            <li>SQL და ORM ტექნოლოგიების მაღალ დონეზე ცოდნა (JPA2, Hibernate)</li>
            <li>MVC, JDBC და RESTful კონცეფციების ღრმა ცოდნა</li>
            <li>პრაქტიკული გამოცდილება Java- ს მიკროსერვისულ არქიტექტურასთან, Spring-Boot– ის გამოყენებით</li>
            <li>SVN, Git პროგრამული კოდის მართვის ინსტრუმენტების ცოდნა</li>
        </ul>

        <p><strong>ჩვენ გთავაზობთ:</strong></p>
        <ul>
            <li> პროფესიული განვითარებისთვის საჭირო ტრენინგებს</li>
            <li>დისტანციურ სამუშაო დღეებს</li>
            <li>ჯანმრთელობის დაზღვევის პაკეტს</li>
            <li>კონკურენტულ საბაზო ხელფასს და შესრულების პრემიას</li>
        </ul>
        <p>
            თუ დაგაინტერესა ჩვენს გუნდში ამ პოზიციამ, შეავსეთ მოთხოვნილი ინფორმაცია, ატვირთეთ რეზიუმე/CV და დააჭირეთ
            ღილაკს „გამოაგზავნეთ განაცხადი“. გამოგზავნის ბოლო ვადა <strong>31/10/2023.</strong>
        </p>
        ველით შენთან შეხვედრას ^_^
    </div>

</div>