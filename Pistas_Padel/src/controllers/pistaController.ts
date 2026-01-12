import { Request, Response } from "express";
import Pista from '../models/pista'

export const obtenerPistas = async (req: Request, res: Response) => {
    const pistas = await Pista.findAll()
    res.json(pistas)
}

export const crearPista = async (req: Request, res: Response) => {
    const {id, nombre, tipo} = req.body
    const nuevaPista = await Pista.create({id, nombre, tipo})
    res.status(201).json(nuevaPista)
}