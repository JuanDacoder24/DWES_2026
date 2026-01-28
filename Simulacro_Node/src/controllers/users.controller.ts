import { Request, Response } from "express";
import { pool } from "../db";

export const getUsers = async (req: Request, res: Response) => {
  const [rows] = await pool.query("SELECT * FROM users");
  res.json(rows);
};

export const getUsersById = (req: Request, res: Response) =>{
    console.log('usuario por id')
}

export const createUser = (req: Request, res: Response) => {
    console.log('creacion de usaurio')
}

export const updateUser = (req: Request, res: Response) => {
    console.log('usaurio modificado')
}

export const deleteUser = (req: Request, res: Response) => {
    console.log('usuario eliminado')
}
