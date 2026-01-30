import { Request, Response } from 'express'
import { pool } from "../config/database";
import { RowDataPacket, ResultSetHeader } from 'mysql2';
import { ActualizarEquipoDTO, CrearEquipoDTO } from '../models/equipos';

export const getAllEquipos = async (req: Request, res: Response): Promise<void> => {
    try{
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM equipos'
        )
        res.json(rows)
    } catch(error){
        console.error('Error al obtener equipos:', error)
        res.status(500).json({ error: 'Error al obtener equipos' })        
    }
}

export const getEquipoById = async (req: Request, res: Response): Promise<void> => {
    try{
        const { id } = req.params
        const [rows] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM equipos WHERE id = ?',
            [id]
        )
        if (rows.length === 0) {
            res.status(404).json({ error: 'equipo no encontrado' })
            return
        }
        res.json(rows[0])
    } catch(error){
        console.error('Error al obtener equipo:', error)
        res.status(500).json({ error: 'Error al obtener equipo' })
    }
}

export const crearequipo = async (req: Request, res: Response): Promise<void> => {
    try{
        const { nombre, sede } : CrearEquipoDTO = req.body

        if(!nombre || !sede){
            res.status(400).json({ error: 'Todos los campos son obligatorios' })
            return
        }

        const [result] = await pool.query<ResultSetHeader>(
            'INSERT INTO equipos(nombre, sede) VALUES (?, ?)',
            [nombre, sede]
        )
        res.status(201).json({
            mensaje: 'equipo creado exitosamente',
        })
    }catch (error:any){
        console.error('Error al crear equio', error)
        res.status(500).json({ error: 'Error al crear equipo' })
    }
}

export const actualizarEquipo = async (req: Request, res: Response): Promise<void> => {
    try{
        const {id} = req.params
        const datosActualizar: ActualizarEquipoDTO = req.body

        const [equipos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM equipos WHERE id = ?',
            [id]
        )

        if (equipos.length === 0) {
            res.status(404).json({ error: 'equipo no encontrado' })
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

        const query = `UPDATE equipos SET ${camposActualizar.join(', ')} WHERE id = ?`

        await pool.query(query, valores)

        const [equipoActualizado] = await pool.query<RowDataPacket[]>(
            'SELECT id, nombre, sede FROM equipos WHERE id = ?',
            [id]
        )

        res.json({
            mensaje: 'equipo actualizado exitosamente',
            usuario: equipoActualizado[0]
        })
    } catch (error){
        console.error('Error al actualizar equipo:', error)
        res.status(500).json({ error: 'Error al actualizar equipo' })
    }
}

export const eliminarEquipo = async (req: Request, res: Response): Promise<void> => {
    try {
        const { id } = req.params

        const [equipos] = await pool.query<RowDataPacket[]>(
            'SELECT id FROM equipos WHERE id = ?',
            [id]
        );

        if (equipos.length === 0) {
            res.status(404).json({ error: 'equipo no encontrado' })
            return
        }
        await pool.query(
            'DELETE equipos WHERE id = ?',
            [id]
        );

        res.json({ mensaje: 'equipo eliminado exitosamente' })
    } catch (error) {
        console.error('Error al eliminar equipo', error);
        res.status(500).json({ error: 'Error al eliminar equipo' })
    }
}