# DataSecurity
Ky eshte nje program ne javafx qe lejon regjistrimin(permes nje sign up forme) ne databaze dhe autentikimin(permes login formes).
Ne klasen SignUpController perfshihet ruajtja e passwordit ku i shtohet salted value dhe behet hash i tyre.
Ne klasen DBUtils perfshihet lidhja me databaze, regjistrimi i user-it dhe log-in i user-it(merret nga databaza psw dhe vlera e salt; 
behet hash psw qe jep user-i me vleren e salt qe ka marrur nga databaza; krahasohen keto dy psw dhe nese jane identike lejohet log-in)
Programi mund te ekzekutohet te klasa App (duke u siguruar qe emri i user-it dhe psw ne databaze te vendosen sipas programit tuaj, si dhe duhet bashkangjitur librarine mysql-connector)
