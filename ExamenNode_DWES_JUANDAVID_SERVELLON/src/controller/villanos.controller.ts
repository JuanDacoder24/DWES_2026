import { Request, Response } from 'express'
import { pool } from "../config/database";
import { RowDataPacket, ResultSetHeader } from 'mysql2';
import { ActualizarVillanoDTO, CrearVillanoDTO } from '../models/villanos';

export const getAllVillanos = async (req: Request, res: Response): Promise<void> => {
    try{
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, antiequipo_id FROM villanos'
        )
        res.json(rows)
    } catch(error){
        console.error('Error al obtener villanos:', error)
        res.status(500).json({ error: 'Error al obtener villanos' })        
    }
}

export const getVillanoById = async (req: Request, res: Response): Promise<void> => {
    try{
        const { id } = req.params
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, antiequipo_id FROM villanos WHERE id = ?',
            [id]
        )
        if (rows.length === 0) {
            res.status(404).json({ error: 'villano no encontrado' })
            return
        }
        res.json(rows[0])
    } catch(error){
        console.error('Error al obtener villano:', error)
        res.status(500).json({ error: 'Error al obtener villano' })
    }
}

export const crearVillano = async (req: Request, res: Response): Promise<void> => {
    try{
        const { nombre, antiequipo_id } : CrearVillanoDTO = req.body

        if(!nombre || !antiequipo_id){
            res.status(400).json({ error: 'Todos los campos son obligatorios' })
            return
        }

        const [result] = await pool.query<ResultSetHeader>(
            'INSERT INTO villanos(nombre, antiequipo_id) VALUES (?, ?)',
            [nombre, antiequipo_id]
        )
        res.status(201).json({
            mensaje: 'villano creado exitosamente',
        })
    }catch (error:any){
        console.error('Error al crear villano', error)
        res.status(500).json({ error: 'Error al crear villano' })
    }
}

export const actualizarVillano = async (req: Request, res: Response): Promise<void> => {
    try{
        const {id} = req.params
        const datosActualizar: ActualizarVillanoDTO = req.body

        const [villanos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM villanos WHERE id = ?',
            [id]
        )

        if (villanos.length === 0) {
            res.status(404).json({ error: 'villano no encontrado' })
            return;
        }

        const camposActualizar: string[] = []
        const valores: any[] = [];

        if (datosActualizar.nombre) {
            camposActualizar.push('nombre = ?')
            valores.push(datosActualizar.nombre)
        }

        if (datosActualizar.antiequipo_id) {
            camposActualizar.push('antiequipo_id = ?')
            valores.push(datosActualizar.antiequipo_id)
        }

        valores.push(id);

        const query = `UPDATE villanos SET ${camposActualizar.join(', ')} WHERE id = ?`

        await pool.query(query, valores)

        const [villanoActualizado] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, antiequipo_id FROM villanos WHERE id = ?',
            [id]
        )

        res.json({
            mensaje: 'villano actualizado exitosamente',
            usuario: villanoActualizado[0]
        })
    } catch (error){
        console.error('Error al actualizar villano:', error)
        res.status(500).json({ error: 'Error al actualizar villano' })
    }
}

export const eliminarvillano = async (req: Request, res: Response): Promise<void> => {
    try {
        const { id } = req.params

        const [villanos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM villanos WHERE id = ?',
            [id]
        );

        if (villanos.length === 0) {
            res.status(404).json({ error: 'villano no encontrado' })
            return
        }
        await pool.query(
            'DELETE villanos WHERE id = ?',
            [id]
        );

        res.json({ mensaje: 'villano eliminado exitosamente' })
    } catch (error) {
        console.error('Error al eliminar villano', error);
        res.status(500).json({ error: 'Error al eliminar villano' })
    }
}