export interface Antiequipos{
    id:number
    nombre: string
    sede: string
}

export interface CrearAntiEquipoDTO{
    nombre: string
    sede: string
}

export interface ActualizarAntiEquipoDTO{
    nombre?: string
    sede?: string
}