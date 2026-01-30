import { Request, Response } from 'express'
import { pool } from "../config/database";
import { RowDataPacket, ResultSetHeader } from 'mysql2';
import { ActualizarHeroeDTO, CrearHeroeDTO } from '../models/heroes';

export const getAllHeroes = async (req: Request, res: Response): Promise<void> => {
    try{
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, equipo_id FROM heroes'
        )
        res.json(rows)
    } catch(error){
        console.error('Error al obtener heroes:', error)
        res.status(500).json({ error: 'Error al obtener heroes' })        
    }
}

export const getHeroeById = async (req: Request, res: Response): Promise<void> => {
    try{
        const { id } = req.params
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, equipo_id FROM heroes WHERE id = ?',
            [id]
        )
        if (rows.length === 0) {
            res.status(404).json({ error: 'Heroe no encontrado' })
            return
        }
        res.json(rows[0])
    } catch(error){
        console.error('Error al obtener heroe:', error)
        res.status(500).json({ error: 'Error al obtener heroe' })
    }
}

export const crearHeroe = async (req: Request, res: Response): Promise<void> => {
    try{
        const { nombre, equipo_id } : CrearHeroeDTO = req.body

        if(!nombre || !equipo_id){
            res.status(400).json({ error: 'Todos los campos son obligatorios' })
            return
        }

        const [result] = await pool.query<ResultSetHeader>(
            'INSERT INTO heroes(nombre, equipo_id) VALUES (?, ?)',
            [nombre, equipo_id]
        )
        res.status(201).json({
            mensaje: 'Heroe creado exitosamente',
        })
    }catch (error:any){
        console.error('Error al crear heroe:', error)
        res.status(500).json({ error: 'Error al crear heroe' })
    }
}

export const actualizarHeroe = async (req: Request, res: Response): Promise<void> => {
    try{
        const {id} = req.params
        const datosActualizar: ActualizarHeroeDTO = req.body

        const [heroes] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM heroes WHERE id = ?',
            [id]
        )

        if (heroes.length === 0) {
            res.status(404).json({ error: 'Usuario no encontrado' })
            return;
        }

        const camposActualizar: string[] = []
        const valores: any[] = [];

        if (datosActualizar.nombre) {
            camposActualizar.push('nombre = ?')
            valores.push(datosActualizar.nombre)
        }

        if (datosActualizar.equipo_id) {
            camposActualizar.push('equipo_id = ?')
            valores.push(datosActualizar.equipo_id)
        }

        valores.push(id);

        const query = `UPDATE heroes SET ${camposActualizar.join(', ')} WHERE id = ?`

        await pool.query(query, valores)

        const [heroeActualizado] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, equipo_id FROM heroes WHERE id = ?',
            [id]
        )

        res.json({
            mensaje: 'Heroe actualizado exitosamente',
            usuario: heroeActualizado[0]
        })
    } catch (error){
        console.error('Error al actualizar heroe:', error)
        res.status(500).json({ error: 'Error al actualizar usuario' })
    }
}

export const eliminarHeroe = async (req: Request, res: Response): Promise<void> => {
    try {
        const { id } = req.params

        const [heroes] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM heroes WHERE id = ?',
            [id]
        );

        if (heroes.length === 0) {
            res.status(404).json({ error: 'Heroe no encontrado' })
            return
        }
        await pool.query(
            'DELETE heroes WHERE id = ?',
            [id]
        );

        res.json({ mensaje: 'Heroe eliminado exitosamente' })
    } catch (error) {
        console.error('Error al eliminar Heroe:', error);
        res.status(500).json({ error: 'Error al eliminar Heroe' })
    }
}