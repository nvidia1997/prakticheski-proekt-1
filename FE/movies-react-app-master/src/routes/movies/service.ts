import {BASE_URL} from "../../utils/api/constants";
import {getClient} from "../../utils/api/utils";
import {Movie} from "./movies.typedef";

export async function list(): Promise<Movie[]> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/movies`)).data;
}

export async function upsert(movie: Movie): Promise<any> {
    // @ts-ignore
    return (await getClient().put(`${BASE_URL}/movies`, movie));
}

export async function deleteById(id: number): Promise<any> {
    // @ts-ignore
    return (await getClient().delete(`${BASE_URL}/movies/${id}`));
}

export async function findById(id: number): Promise<Movie> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/movies/${id}`)).data;
}
