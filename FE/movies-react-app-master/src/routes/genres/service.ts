import {BASE_URL} from "../../utils/api/constants";
import {getClient} from "../../utils/api/utils";
import {MovieGenre} from "../movies/movies.typedef";

export async function list(): Promise<MovieGenre[]> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/genres`)).data;
}

export async function upsert(genre: MovieGenre): Promise<any> {
    // @ts-ignore
    return (await getClient().put(`${BASE_URL}/genres`, genre));
}

export async function deleteById(id: number): Promise<any> {
    // @ts-ignore
    return (await getClient().delete(`${BASE_URL}/genres/${id}`));
}

export async function findById(id: number): Promise<MovieGenre> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/genres/${id}`)).data;
}
