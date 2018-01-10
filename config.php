<?php

/**
 * Configuration for database connection
 *
 */

$host       = "localhost";
$username   = "root";
$password   = "root";
$dbname     = "NickyBot"; // will use later
$dsn        = "mysql:host=$host;dbname=$dbname"; // will use later
$options    = array(
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                PDO::MYSQL_ATTR_LOCAL_INFILE => true,
                PDO::MYSQL_ATTR_USE_BUFFERED_QUERY => true,
                PDO::ATTR_PERSISTENT => false
              );

?>