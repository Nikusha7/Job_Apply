<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="article-checkbox">
<article>
    <p>
        პერსონალური მონაცემების დამუშავება (მათ შორის, სხვა სახელმწიფოსთვის გადაცემა) განხორციელდება სს
        საქართველოს ბანკის მიერ (ს/კ: 204378869) (მათ შორის, უფლებამოსილი პირის მეშვეობით), რომელიც
        მოქმედებს მონაცემთა დაცვის საერთაშორისო სტანდარტებთან და მოქმედ კანონმდებლობასთან შესაბამისობაში.
        პერსონალური მონაცემების დამუშავების მიზანია კანდიდატის შესაბამისობის დადგენა არსებულ/სამომავლო
        ვაკანსიის მოთხოვნებთან, დასაქმების თაობაზე გადაწყვეტილების მიღების მიზნით და შეინახება არაუმეტეს 10
        წლის ვადისა; პერსონალური მონაცემების ატვირთვა ხდება HireHive LTD-ის (ს/კ: 380350, ირლანდია, ქორქი.)
        ღრუბლოვანი ტექნოლოგიის სისტემაში; სისტემის შესახებ დამატებით ინფორმაცია იხ. hirehive.com. კანდიდატი
        უფლებამოსილია მიიღოს ინფორმაცია მის შესახებ დამუშავებული პერსონალური მონაცემების შესახებ, რომელიც
        თავად მიაწოდა ბანკს და მოითხოვოს აღნიშნული ინფორმაციის გასწორება, განახლება, დამატება, დაბლოკვა,
        წაშლა და/ან განადგურება; ამისთვის გთხოვთ მოგვმართეთ ელ-ფოსტაზე: Careers@bog.ge საქართველოს ბანკი
        დასაქმებისას ეყრდნობა თანაბარი შესაძლებლობების პრინციპს და აღიარებს, რომ სხვადასხვა განათლებისა და
        გამოცდილების მქონე ადამიანებს შეუძლიათ სამუშაო ადგილი გაამდიდრონ მნიშვნელოვანი ღირებულებით. ჩვენი
        მიზანია ვიყოთ ინკლუზიური ორგანიზაცია, სადაც მრავალფეროვნება დაფასებული და წახალისებულია. ბანკი
        მაქსიმალურად უზრუნველყოფს რომ არ მოხდეს პირდაპირი თუ არაპირდაპირი დისკრიმინაცია ნებისმიერი მიზეზით,
        მათ შორის როგორიცაა: რასა; კანის ფერი; ენა; ეთნიკური და სოციალური კუთვნილება; ეროვნება; წარმოშობა;
        ქონებრივი ან წოდებრივი მდგომარეობა; შრომითი ხელშეკრულების სტატუსი; საცხოვრებელი ადგილი; ასაკი;
        სქესი; სექსუალური ორიენტაცია; შეზღუდული შესაძლებლობა; ჯანმრთელობის მდგომარეობა; რელიგია;
        საზოგადოებრივი, პოლიტიკური ან სხვა გაერთიანებებისადმი, მათ შორის, პროფესიული კავშირისადმი
        კუთვნილება; ოჯახური მდგომარეობა; პოლიტიკური ან სხვა შეხედულებები; ან სხვა ნიშანი, რომელიც მიზნად
        ისახავს ან იწვევს დასაქმებასა და პროფესიულ საქმიანობაში თანაბარი შესაძლებლობის ან მოპყრობის უარყოფას
        ან ხელყოფას.
    </p>

    <label for="fileCheckbox">ვეთანხმები</label>
    <input type="checkbox" id="fileCheckbox" name="fileCheckbox"
        <%=(request.getParameter("fileCheckbox") != null ? "checked" : "")%>
           required> <!-- 'required' attribute makes the checkbox mandatory -->

</article>
</div>