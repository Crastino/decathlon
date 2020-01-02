Come lanciare l'applicazione:

Il progetto è un classico progetto Maven con l'uso di SpringBoot. E' possibile eseguirlo seguendo la procedura standard per i progetti Maven:

mvn clean install  -> scarica le dipendenze e genera il pacchetto (.jar) nella cartella /target
java -jar nome_pacchetto.jar

Oppure è possibile utilizzare il comando apposito per spring boot:

mvn spring-boot:run

E' inoltre possibile lanciare i test con il comando:

mvn test
