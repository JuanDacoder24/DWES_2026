export interface Heroes{
    id: number
    nombre: string
    equipo_id: number
}

export interface CrearHeroeDTO{
    nombre: string
    equipo_id: number
}

export interface ActualizarHeroeDTO{
    nombre?: string
    equipo_id?: number
}