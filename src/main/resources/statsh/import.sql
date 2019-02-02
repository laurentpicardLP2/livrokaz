#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Gendle
#------------------------------------------------------------

CREATE TABLE Gendle(
        gendleId   Int  Auto_increment  NOT NULL ,
        typeGendle Varchar (50) NOT NULL
	,CONSTRAINT Gendle_PK PRIMARY KEY (gendleId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Publisher
#------------------------------------------------------------

CREATE TABLE Publisher(
        publisherId   Int  Auto_increment  NOT NULL ,
        namePublisher Varchar (50) NOT NULL
	,CONSTRAINT Publisher_PK PRIMARY KEY (publisherId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: GoogleBooks
#------------------------------------------------------------

CREATE TABLE GoogleBooks(
        bookId            Varchar (50) NOT NULL ,
        title             Varchar (50) NOT NULL ,
        publishReleased   Date NOT NULL ,
        codeISBN          Varchar (50) NOT NULL ,
        pageCount         Int NOT NULL ,
        categorie         Varchar (50) NOT NULL ,
        imgThumbnail      Longtext NOT NULL ,
        langage           Varchar (50) NOT NULL ,
        price             Double NOT NULL ,
        textSnippet       Longtext ,
        description       Longtext ,
        isEbook           Bool NOT NULL ,
        availableQuantity Int NOT NULL ,
        gendleId          Int NOT NULL ,
        publisherId       Int NOT NULL
	,CONSTRAINT GoogleBooks_PK PRIMARY KEY (bookId)

	,CONSTRAINT GoogleBooks_Gendle_FK FOREIGN KEY (gendleId) REFERENCES Gendle(gendleId)
	,CONSTRAINT GoogleBooks_Publisher0_FK FOREIGN KEY (publisherId) REFERENCES Publisher(publisherId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Author
#------------------------------------------------------------

CREATE TABLE Author(
        authorId Int  Auto_increment  NOT NULL ,
        fullName Varchar (50) NOT NULL
	,CONSTRAINT Author_PK PRIMARY KEY (authorId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: R_GoogleBooks_Author
#------------------------------------------------------------

CREATE TABLE R_GoogleBooks_Author(
        authorId Int NOT NULL ,
        bookId   Varchar (50) NOT NULL
	,CONSTRAINT R_GoogleBooks_Author_PK PRIMARY KEY (authorId,bookId)

	,CONSTRAINT R_GoogleBooks_Author_Author_FK FOREIGN KEY (authorId) REFERENCES Author(authorId)
	,CONSTRAINT R_GoogleBooks_Author_GoogleBooks0_FK FOREIGN KEY (bookId) REFERENCES GoogleBooks(bookId)
)ENGINE=InnoDB;



#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Adresse
#------------------------------------------------------------

CREATE TABLE Adresse(
        adresseId  Int  Auto_increment  NOT NULL ,
        numVoie    Varchar (50) NOT NULL ,
        nomVoie    Varchar (250) NOT NULL ,
        codePostal BigInt NOT NULL ,
        city       Varchar (50) NOT NULL ,
        country    Varchar (50) NOT NULL
	,CONSTRAINT Adresse_PK PRIMARY KEY (adresseId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: ProfilAccess
#------------------------------------------------------------

CREATE TABLE ProfilAccess(
        profilAccessId Int  Auto_increment  NOT NULL ,
        codeProfil     Int NOT NULL ,
        libelleProfil  Varchar (50) NOT NULL
	,CONSTRAINT ProfilAccess_PK PRIMARY KEY (profilAccessId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: User
#------------------------------------------------------------

CREATE TABLE User(
        userId         Int  Auto_increment  NOT NULL ,
        firstName      Varchar (50) NOT NULL ,
        lastName       Varchar (50) NOT NULL ,
        tel            Varchar (50) NOT NULL ,
        mail           Varchar (50) NOT NULL ,
        civility       Varchar (50) NOT NULL ,
        profilId       Int NOT NULL ,
        dateBirthday   Date NOT NULL ,
        password       Varchar (50) NOT NULL ,
        profilAccessId Int NOT NULL
	,CONSTRAINT User_PK PRIMARY KEY (userId)

	,CONSTRAINT User_ProfilAccess_FK FOREIGN KEY (profilAccessId) REFERENCES ProfilAccess(profilAccessId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Ordering
#------------------------------------------------------------

CREATE TABLE Ordering(
        orderingId             Int  Auto_increment  NOT NULL ,
        dateOrdering           Date NOT NULL ,
        dateDelivery           Date NOT NULL ,
        shippingFees           Double NOT NULL ,
        totalAmount            Double NOT NULL ,
        userId_R_Ordering_User Int NOT NULL
	,CONSTRAINT Ordering_PK PRIMARY KEY (orderingId)

	,CONSTRAINT Ordering_User_FK FOREIGN KEY (userId_R_Ordering_User) REFERENCES User(userId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: R_Ordering_GoogleBooks
#------------------------------------------------------------

CREATE TABLE R_Ordering_GoogleBooks(
        bookId     Varchar (50) NOT NULL ,
        orderingId Int NOT NULL ,
        quantity   Int NOT NULL
	,CONSTRAINT R_Ordering_GoogleBooks_PK PRIMARY KEY (bookId,orderingId)

	,CONSTRAINT R_Ordering_GoogleBooks_GoogleBooks_FK FOREIGN KEY (bookId) REFERENCES GoogleBooks(bookId)
	,CONSTRAINT R_Ordering_GoogleBooks_Ordering0_FK FOREIGN KEY (orderingId) REFERENCES Ordering(orderingId)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: R_User_Adresse
#------------------------------------------------------------

CREATE TABLE R_User_Adresse(
        adresseId Int NOT NULL ,
        userId    Int NOT NULL
	,CONSTRAINT R_User_Adresse_PK PRIMARY KEY (adresseId,userId)

	,CONSTRAINT R_User_Adresse_Adresse_FK FOREIGN KEY (adresseId) REFERENCES Adresse(adresseId)
	,CONSTRAINT R_User_Adresse_User0_FK FOREIGN KEY (userId) REFERENCES User(userId)
)ENGINE=InnoDB;








