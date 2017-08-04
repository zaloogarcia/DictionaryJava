# **Paradigmas de la programacion**

### Laboratorio 2 
## Diccionario Traductor en **Java**

El siguiente laboratorio esta basado en la implementacion de un programa de un  diccionario Traductor Ingles<->Español en el lenguaje orientado a objetos **Java**.

**Como ejecutar el programa:** **make** para compilar y luego **java -jar bin/Translator.jar**.

**NOTA:** El estilo de codigo fue corregido por checkstyle 6.17 de google_check, se corrigio todos los errores pero tuvimos inconvenientes en solucionar el error "Missing a Javadoc comment. [JavadocMethod"
Agregamos los comentarios pero sigue estando los errores.
Se agrego un archivo **manifest.txt** en **/bin** para que el jar sepa donde estan las librerias que usa y que clase ejecutar.
---

### Interfaz

El programa tiene un interfaz para el usuario de la siguiente forma:

 * -i FILE | --input=FILE      : Documento de entrada. (Requerido).
 * -d FILE | --dictionary=FILE : Diccionario de traducción.
 * -g FILE | --ignored=FILE    : Diccionario de palabras ignoradas.
 * -o FILE | --output=FILE     : Archivo de salida.
 * -r      | --reverse  		: Dirección de la traducción.

Donde la opcion "-i" es ingresada junto con un *path* de un archivo .txt, el archivo a traducir, el mismo es guardado en memoria.    
La opcion "-d" es ingresada tambien junto con un *path* de un archivo .txt (No obligatorio), el archivo del diccionario, compuesto por una lista de duplas (Español,Ingles), que tambien es guardado en memoria para luego ser consultado. Si la opcion no fue ingresada junto con el *path* entonces se usara un archivo default (vacio) para alojar las palabras junto con su traduccion y luego sera removido de la memoria.  
La opcion "-g" es ingresada tambien junto con un *path* de un archivo .txt (No obligatorio), el archivo de las palabras a ignorar. Si la opcion no fue ingresada junto con el *path* encontes se usara un archivo default (vacio) para alojar las palabras a ignorar y luego sera removido de la memoria.   
La opcion "-o" es ingresada tambien junto con un *path* de un archivo .txt, el archivo de salida , con el texto ya traducido.
La ultima opcion "-r" modifica la forma de traduccion del texto, si es de Español-> Ingles o Ingles-> Español.  
---
### Clase Cli

El metodo de parseo usado en este laboratorio fue implementado gracias a la ayuda de la Libreria **Cli**, un proyecto de Lenguaje y Ejecucion del Conjunto de Proyectos **Apache Commons** de la fundacion Apache Software.  
La Libreria provee una *Aplicacion de Interface de Programacion* (**API**) para parsear opciones de lineas de comandos para programas. Tambien provee herramientas para imprimir en pantalla mensajes de ayuda para detallar las opciones disponibles.  
La libreria fue descargada de su [**pagina oficial**][CLI] y luego fue descomprimida y guardada (.jar) en la carpeta **/lib** dentro del proyecto e importada en las clases correspondientes.   
Las Opciones son imprimidas y definidas en pantalla gracias a la funcion **addOption** , de Cli, la cual tiene como argumentos, primero un String (por ejemplo "i"), luego un String del segundo elemento a agregar (*path*), como tercer argumento un booleano que define si la opcion no esta acompañada por otro argumento (caso false) o si (caso true), por ejemplo cuando se quiere cargar un diccionario y el ultimo un String de una pequeña reseña de la opcion.     

Los argumentos de las opciones parseadas disponibles fueron alojadas en un arreglo de 4 Strings llamado **Choices**.

 - El primer elemento de Choices aloja el *path* del Archivo a traducir.
 - El segundo elemento aloja o un diccionario .txt default o el *path* del diccionario dado por el usuario.
 - el tercer elemento aloja el *path* de, o archivo .txt default o uno otorgado por el usuario de palabras ignoradas.
 - El ultimo elemento aloja el *path* del archivo de salida.

La ultima opcion disponible a parsear es "-r" que solo activa un flag (reverse).  
Fueron alojadas gracias a la funcion **getOptionValue** que toma como argumento el mismo String del primer argumento de la funcion **addOption** y devuelve el segundo argumento que el usuario ingreso al elegir tal opcion. 
En caso de que la opcion ingresada no es valida imprime un menu de ayuda.
---

### Clase Translator

En esta clase se crea el **main** del programa, donde se nuclea los demas archivos (.java)se ejecuta el programa en su totalidad.   
Primero crea un *Dictionary, IgnoredSet, Cli, TranslationFile* y un *Scanner.*  

- En **Dictionary** (dict) se alojara el diccionario default o el que fue dado por el usuario para cargar, de el dict se consultara si hay una palabra especifica alli o se agregara una junto con su definicion.   
- En **IgnoredSet** (ignored) se alojara la lista default de palabras a ignorar o el que fue dada por el usuario para cargar, de el se consultara si hay una palabra especifica alli o se agregara.
- En **CLi** (cli) se utilizara para parsear los argumentos y obtener las opciones otorgadas por el usuario que luego se alojaran en las variables *userChoice* (String).    
- En **TranslationFile** (memfile) se alojara el archivo a traducir de lo contrario imprimira un mensaje.   
- Y en **Scanner** (scan) , de la libreria **util** de Java, se utilizara como herramienta para escanear el String dado por el usuario (como un Scanf en C) en el proceso de guardar las palabras que quieran ser "Traducidas siempre como" o ser "Ignoradas siempre".   

Una vez definidas estas variables, se invoca el proceso parse() para imprimir el interfaz y luego parsear los argumentos otorgados por el usuario.   
Luego se analizan las opciones y una vez que todas las condiciones se cumplen para traducir, se traduce.   
Se traduce mediante un **for** que avanzara en el archivo a traducir y palabra por palabra, consultando primero en el IgnoreSet , en caso de que este , se omitira la palabra pasando a la siguiente iteracion , luego en el Dictionary y si su traduccion esta, se reemplazara la palabra por su traduccion. Si no esta en ninguno de los dos , se imprimira un menu alternativo en el que se consultara al usuario si quiere ignorarla, una vez o siempre, o traducirla, una vez o siempre.   
En el caso de que se quiera traducirla o ignorarla siempre , se almacenaran luego en el Diccionary o IgnoreSet respectivamente.   

Por ultimo, retornara tanto el diccionario, la lista de palabras ignoradas y el archivo traducido.

---
### Clase Dictionary

La clase **Dictionary** fue definida como la extension de un **Hashmap** de Strings, implementada en la libreria **io** de Java.   
La clase tiene 2 funciones nuevas, load y unload, ambos toman como argumento un String (path) y un boolean reverse. Son las funciones basicas que cargan un diccionario de disco duro y escriben un diccionario a un archivo. Si el usuario usa la opcion *-r* entonces esto se pasa al Dictionary y se carga y descarga al reves.  
Se eligio heredar de **HashMap** por su velocidad para almacenar y acceder pares de llaves y valores.  

---
### Clase TranslationFile

Esta es la clase que representa a un archivo a traducir o traducido en memoria. La funcion para cargar toma un path y carga directamente el texto en el archivo a memoria (en un String de la clase). Se utilizo esta implementacion porque la eleccion de un archivo de entrada no es opcional.  
Tambien contiene un arreglo de Strings, que se consigue dividiendo el String original por palabras y sacando los espacios y los signos de puntuacion. Este arreglo se usa en las funciones *wordNumber(i)* (que devuelve la i-esima palabra) y en *numOfWords* (usada para determinar cuando ya se ha terminado de traducir.  
Por ultimo tenemos la funcion translateWord, que se encarga de reemplazar en el String *fileString* la proxima occurencia de una palabra con la traduccion que le pasamos. De esta forma no hay que crear un nuevo TranslationFile para escribir el archivo de output, sino que se modifica el que ya tenemos hasta que este traducido. Para no reemplazar las palabras que el usuario ha decidido ignorar o traducir una sola vez, se busca el indice en *fileString* de la actual ocurrencia de la palabra a traducir, y se reemplaza esa unica vez, luego se reemplaza en el arreglo de Strings *fileWords*.  

---
### Clase IgnoredSet

Esta clase se encarga de almacenar las palabras que hay que ignorar en la traduccion. Hereda los metodos de **HashSet** y solamente agrega las funciones para cargar y descargar de archivos. Se implementa como un set porque no hay necesidad para tener palabras repetidas, asi que una vez que tenemos una palabra ignorada no puede volver a ser agregada. El **HashSet** se usa en particular por su velocidad.  

---
### Excepciones

En todas las clases que leen o escriben archivos se definieron excepciones (de la clase **FileNotFoundException**) con ciertos mensajes. Las funciones que cargan o descargan de archivos hacen *throw* de estas excepciones, que luego son usadas en Translate para informar al usuario de algun error y terminar el programa.  
