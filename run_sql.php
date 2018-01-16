<?php

/**
 * Open a connection via PDO to create a
 * new database and table with structure.
 *
 */

require "config.php";

try 
{
	$connection = new PDO("mysql:host=$host", $username, $password, $options);
	echo "running $argv[1]...\n";
	$sql = file_get_contents($argv[1]);
	$connection->exec($sql);
	
	echo "Sql script ran successfully.\n";
}
catch(PDOException $error)
{
	echo $sql . "<br>" . $error->getMessage();
}

?>