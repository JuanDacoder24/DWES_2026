import mysql from "mysql2/promise";
import dotenv from "dotenv";
 
dotenv.config();
 
export const pool = mysql.createPool({
  host: process.env.DB_HOST,
  port: Number(process.env.DB_PORT ?? 3306),
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
 
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
});

export const testConnection = async (): Promise<boolean> => {
  try {
    const connection = await pool.getConnection();
    console.log('Conexión a MySQL exitosa');
    connection.release();
    return true;
  } catch (error: any) {
    console.error('Error al conectar a MySQL:', error.message || error);
    console.error('Comprueba tus credenciales de base de datos y crea un archivo .env con DB_USER y DB_PASSWORD si es necesario.');
    return false;
  }
};