
import mysql from "mysql2/promise";

export const pool = mysql.createPool({
  host: "localhost",
  user: "root",
  password: "daw12",
  database: "tu_bd",
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
});
