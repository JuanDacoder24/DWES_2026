import { Request, Response } from 'express'
import { pool } from "../config/database";
import { RowDataPacket, ResultSetHeader } from 'mysql2';
import { ActualizarAntiEquipoDTO, CrearAntiEquipoDTO } from '../models/antiequipos';

export const getAllAntiEquipos = async (req: Request, res: Response): Promise<void> => {
    try{
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM antiequipos'
        )
        res.json(rows)
    } catch(error){
        console.error('Error al obtener antiequipos:', error)
        res.status(500).json({ error: 'Error al obtener antiequipos' })        
    }
}

export const getAntiEquipoById = async (req: Request, res: Response): Promise<void> => {
    try{
        const { id } = req.params
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM antiequipos WHERE id = ?',
            [id]
        )
        if (rows.length === 0) {
            res.status(404).json({ error: 'antiequipo no encontrado' })
            return
        }
        res.json(rows[0])
    } catch(error){
        console.error('Error al obtener antiequipo:', error)
        res.status(500).json({ error: 'Error al obtener antiequipo' })
    }
}

export const crearAntiequipo = async (req: Request, res: Response): Promise<void> => {
    try{
        const { nombre, sede } : CrearAntiEquipoDTO = req.body

        if(!nombre || !sede){
            res.status(400).json({ error: 'Todos los campos son obligatorios' })
            return
        }

        const [result] = await pool.query<ResultSetHeader>(
            'INSERT INTO antiequipos(nombre, sede) VALUES (?, ?)',
            [nombre, sede]
        )
        res.status(201).json({
            mensaje: 'antiequipo creado exitosamente',
        })
    }catch (error:any){
        console.error('Error al crear antiequipo', error)
        res.status(500).json({ error: 'Error al crear antiequipo' })
    }
}

export const actualizarAntiEquipo = async (req: Request, res: Response): Promise<void> => {
    try{
        const {id} = req.params
        const datosActualizar: ActualizarAntiEquipoDTO = req.body

        const [antiequipos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM antiequipos WHERE id = ?',
            [id]
        )

        if (antiequipos.length === 0) {
            res.status(404).json({ error: 'antiequipo no encontrado' })
            return;
        }

        const camposActualizar: string[] = []
        const valores: any[] = [];

        if (datosActualizar.nombre) {
            camposActualizar.push('nombre = ?')
            valores.push(datosActualizar.nombre)
        }

        if (datosActualizar.sede) {
            camposActualizar.push('sede = ?')
            valores.push(datosActualizar.sede)
        }

        valores.push(id);

        const query = `UPDATE antiequipos SET ${camposActualizar.join(', ')} WHERE id = ?`

        await pool.query(query, valores)

        const [AntiequipoActualizado] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM antiequipos WHERE id = ?',
            [id]
        )

        res.json({
            mensaje: 'antiequipo actualizado exitosamente',
            usuario: AntiequipoActualizado[0]
        })
    } catch (error){
        console.error('Error al actualizar antiequipo:', error)
        res.status(500).json({ error: 'Error al actualizar antiequipo' })
    }
}

export const eliminarAntiEquipo = async (req: Request, res: Response): Promise<void> => {
    try {
        const { id } = req.params

        const [antiequipos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM antiequipos WHERE id = ?',
            [id]
        );

        if (antiequipos.length === 0) {
            res.status(404).json({ error: 'antiequipo no encontrado' })
            return
        }
        await pool.query(
            'DELETE antiequipos WHERE id = ?',
            [id]
        );

        res.json({ mensaje: 'antiequipo eliminado exitosamente' })
    } catch (error) {
        console.error('Error al eliminar antiequipo', error);
        res.status(500).json({ error: 'Error al eliminar antiequipo' })
    }
}