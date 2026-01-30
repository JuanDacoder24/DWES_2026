export interface VillanoS{
    id: number
    nombre: string
    antiequipo_id: number
}

export interface CrearVillanoDTO{
    nombre: string
    antiequipo_id: number
}

export interface ActualizarVillanoDTO{
    nombre?: string
    antiequipo_id: number   
}