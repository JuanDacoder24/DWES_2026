export interface Equipos{
    id:number
    nombre: string
    sede: string
}

export interface CrearEquipoDTO{
    nombre: string
    sede: string
}

export interface ActualizarEquipoDTO{
    nombre?: string
    sede?: string
}