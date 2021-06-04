import axios from "axios";
import {BASE_URL} from "../../utils/api/constants";
import {MovieGenre} from "../movies/movies.typedef";

export async function list(): Promise<MovieGenre[]> {
    // @ts-ignore
    return (await axios.get(`${BASE_URL}/genres`)).data;
}

export async function upsert(genre: MovieGenre): Promise<any> {
    // @ts-ignore
    return (await axios.put(`${BASE_URL}/genres`, {genre}));
}

export async function deleteById(id: number): Promise<any> {
    // @ts-ignore
    return (await axios.put(`${BASE_URL}/genres/${id}`));
}

export async function findById(id: number): Promise<MovieGenre> {
    // @ts-ignore
    return (await axios.put(`${BASE_URL}/genres/${id}`)).data;
}
