export interface Movie {
    id: number,
    genre:MovieGenre,
    releaseYear:MovieYear,
    posterUrl: string,
    title: string,
    overview: string,
}

export interface MovieGenre {
    id: number,
    name: string
}

export interface MovieYear{
    id: number,
    value: number
}

export interface MoviesQueryParams {
    language?: number,
    sort_by: string
}
