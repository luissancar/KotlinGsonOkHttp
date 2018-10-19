<?php

$server = "localhost";
$user = "root";
$pass = "clave";
$bd = "guitarras";

//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

//generamos la consulta
$sql = "SELECT * FROM guitarra";
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

if(!$result = mysqli_query($conexion, $sql)) die();

$guitars = array(); //creamos un array

while($row = mysqli_fetch_array($result))
{
    $marca=$row['marca'];
    $modelo=$row['modelo'];
    $precio=$row['precio'];


    $guitars[] = array('marca'=> $marca, 'modelo'=> $modelo, 'precio'=> $precio);

}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");


//Creamos el JSON
$json_string = json_encode($guitars);
// Añadimos cabecera y final
// debe ser como el nombre del array en  class GuitarraArray

$json_string = '{ "guitarras":' . $json_string . "}";
echo $json_string;

//Si queremos crear un archivo json, sería de esta forma:
/*
$file = 'clientes.json';
file_put_contents($file, $json_string);
*/


?>