Para rodar o projeto

Forma 1:
* Importe o projeto maven/Intellij em uma IDE;
* Copie os arquivos csv para a pasta `input`
* Rode o método `main` do arquivo `src/main/java/br.otaviof.czech_accidents/Main.java`
* Os arquivos csv de saida estarão na pasta `output`.

Forma 2:
* Baixe o arquivo da seção *releases*;
* Crie uma pasta `input` na mesma pasta do arquivo jar;
* Copie os arquivos csv para a pasta `input`;
* Execute o arquivo jar na linha de comando: `java -jar czech_road_accidents-1.0-0.jar`
* Os arquivos csv de saída estarão na pasta `output`;

Forma 3:
* Baixe o arquivo da seção *releases*;
* Copie o caminho da pasta que contém os arquivos csv de entrada;
* Copie o caminho da pasta que vai conter os arquivos csv de saída;
* Execute o arquivo jar na linha de comando:

`java -jar czech_road_accidents-1.0-0.jar *pasta de entrada* *pasta de saída*`


Recomendado usar -Xmx2000M
